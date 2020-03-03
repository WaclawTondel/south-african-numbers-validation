package com.github.southAfricanNumbersValidator.domain.phone.number.validation;

import com.github.southAfricanNumbersValidator.domain.phone.number.PhoneNumber;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Entity
public class Validation {

    public final static String DELETED_SUBSTRING = "_DELETED_";
    public final static Integer PHONE_NUMBER_LENGTH = 11;
    final static String NOT_A_NUMBER = "Not a number";
    final static String DELETED_MESSAGE = "Number has been deleted";
    final static String REPLACED_MESSAGE = "Number is replacement of: ";
    final static String PREFIX_ADDED_MESSAGE = "Prefix has been added";
    final static String WRONG_NATIONAL_NUMBER = "Wrong National Prefix";
    final static String TOO_LONG_NUMBER = "Number is too long";
    final static String TOO_SHORT_NUMBER = "Number is to short";
    final static String NOT_A_SOUTH_AFRICAN_NUMBER = "Not a South African number";
    private final static Integer WITHOUT_PREFIX_LENGTH = 9;
    private final static List<String> FIVE_DIGIT_PREFIXES = Arrays.asList("27710",
            "27711",
            "27712",
            "27713",
            "27714",
            "27715",
            "27716",
            "27717",
            "27718",
            "27719");
    private final static List<String> FOUR_DIGIT_PREFIXES = Arrays.asList("2772",
            "2773",
            "2774",
            "2776",
            "2778",
            "2779",
            "2781",
            "2782",
            "2783");
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated
    private Status status;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id", nullable = false)
    private List<ValidationComment> comments = new ArrayList<>();

    Validation(Long id, Status status, List<ValidationComment> comments) {
        this.id = id;
        this.status = status;
        this.comments = comments;
    }

    public Validation() {
    }

    public Validation(Status status) {
        this.status = status;
    }

    public void validate(PhoneNumber phoneNumber) {
        setValid();
        validateNumerical(phoneNumber);
        validateNationalPrefix(phoneNumber);
        validateNumberLength(phoneNumber);
        validateSouthAfricanPrefixes(phoneNumber);
    }


    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    List<ValidationComment> getComments() {
        return comments;
    }

    private void validateNumerical(PhoneNumber phoneNumber) {
        Pattern pattern = Pattern.compile("^\\d*");
        if (!pattern.matcher(phoneNumber.getNumber()).matches()) {
            if (phoneNumber.getNumber().startsWith(DELETED_SUBSTRING)) {
                phoneNumber.handleDeletedNumber();
                setInvalid(DELETED_MESSAGE);
            } else if (phoneNumber.getNumber().contains(DELETED_SUBSTRING)) {
                setFixed(REPLACED_MESSAGE + phoneNumber.handleUpdatedNumber());

            } else {
                setInvalid(NOT_A_NUMBER);
            }
        }
    }

    private void validateNationalPrefix(PhoneNumber phoneNumber) {
        if (phoneNumber.getNumber().length() == WITHOUT_PREFIX_LENGTH
                && !phoneNumber.getNumber().startsWith(PhoneNumber.NATIONAL_PREFIX)) {
            phoneNumber.addPrefix();
            setFixed(PREFIX_ADDED_MESSAGE);
        }
        if (!phoneNumber.getNumber().startsWith(PhoneNumber.NATIONAL_PREFIX)) {
            setInvalid(WRONG_NATIONAL_NUMBER);
        }
    }

    private void validateNumberLength(PhoneNumber phoneNumber) {
        if (phoneNumber.getNumber().length() > PHONE_NUMBER_LENGTH) {
            setInvalid(TOO_LONG_NUMBER);
        }
        if (phoneNumber.getNumber().length() < PHONE_NUMBER_LENGTH) {
            setInvalid(TOO_SHORT_NUMBER);
        }
    }

    private void validateSouthAfricanPrefixes(PhoneNumber phoneNumber) {
        if (!validateSouthAfricanFourDigitPrefixes(phoneNumber)
                && !validateSouthAfricanFiveDigitPrefixes(phoneNumber)) {
            setInvalid(NOT_A_SOUTH_AFRICAN_NUMBER);
        }
    }

    private boolean validateSouthAfricanFourDigitPrefixes(PhoneNumber phoneNumber) {
        return FOUR_DIGIT_PREFIXES.stream().anyMatch((prefix) -> phoneNumber.getNumber().startsWith(prefix));
    }

    private boolean validateSouthAfricanFiveDigitPrefixes(PhoneNumber phoneNumber) {
        return FIVE_DIGIT_PREFIXES.stream().anyMatch((prefix) -> phoneNumber.getNumber().startsWith(prefix));
    }

    private void setValid() {
        this.status = Status.VALID;
    }

    private void setInvalid(String comment) {
        this.status = Status.INVALID;
        this.comments.add(new ValidationComment(comment));
    }

    private void setFixed(String comment) {
        this.status = Status.FIXED;
        this.comments.add(new ValidationComment(comment));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Validation that = (Validation) o;
        return Objects.equals(id, that.id) &&
                status == that.status &&
                Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, comments);
    }
}
