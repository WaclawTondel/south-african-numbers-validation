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

    final static String DELETED_SUBSTRING = "_DELETED_";
    final static String NOT_A_NUMBER = "Not a number";
    final static String DELETED_MESSAGE = "Number has been deleted";
    final static String REPLACED_MESSAGE = "Number is replacement of: ";
    final static String PREFIX_ADDED_MESSAGE = "Prefix has been added";
    final static String WRONG_NATIONAL_NUMBER = "Wrong National Prefix";
    final static String TOO_LONG_NUMBER = "Number is too long";
    final static String TOO_SHORT_NUMBER = "Number is to short";
    final static String NOT_A_SOUTH_AFRICAN_NUMBER = "Not a South African number";
    private final static Integer PHONE_NUMBER_LENGTH = 11;
    private final static Integer WITHOUT_PREFIX_LENGTH = 9;
    private final static String NATIONAL_PREFIX = "27";
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

    public void validate(String phoneNumber) {
        setValid();
        phoneNumber = validateNumerical(phoneNumber);
        phoneNumber = validateNationalPrefix(phoneNumber);
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

    private String validateNumerical(String phoneNumber) {
        Pattern pattern = Pattern.compile("^\\d*");
        if (!pattern.matcher(phoneNumber).matches()) {
            if (phoneNumber.startsWith(DELETED_SUBSTRING)) {
                setInvalid(DELETED_MESSAGE);
                return handleDeletedNumber(phoneNumber);
            } else if (phoneNumber.contains(DELETED_SUBSTRING)) {
                String[] phoneNumbers = handleUpdatedNumber(phoneNumber);
                setFixed(REPLACED_MESSAGE + phoneNumbers[1]);
                return phoneNumbers[0];
            } else {
                setInvalid(NOT_A_NUMBER);
                return phoneNumber;
            }
        }
        return phoneNumber;
    }

    private String validateNationalPrefix(String phoneNumber) {
        if (phoneNumber.length() == WITHOUT_PREFIX_LENGTH
                && !phoneNumber.startsWith(NATIONAL_PREFIX)) {
            setFixed(PREFIX_ADDED_MESSAGE);
            return addPrefix(phoneNumber);
        }
        if (!phoneNumber.startsWith(NATIONAL_PREFIX)) {
            setInvalid(WRONG_NATIONAL_NUMBER);
            return phoneNumber;
        } else {
            return phoneNumber;
        }
    }

    private void validateNumberLength(String phoneNumber) {
        if (phoneNumber.length() > PHONE_NUMBER_LENGTH) {
            setInvalid(TOO_LONG_NUMBER);
        }
        if (phoneNumber.length() < PHONE_NUMBER_LENGTH) {
            setInvalid(TOO_SHORT_NUMBER);
        }
    }

    private void validateSouthAfricanPrefixes(String phoneNumber) {
        if (!validateSouthAfricanFourDigitPrefixes(phoneNumber)
                && !validateSouthAfricanFiveDigitPrefixes(phoneNumber)) {
            setInvalid(NOT_A_SOUTH_AFRICAN_NUMBER);
        }
    }

    private boolean validateSouthAfricanFourDigitPrefixes(String phoneNumber) {
        return FOUR_DIGIT_PREFIXES.stream().anyMatch(phoneNumber::startsWith);
    }

    private boolean validateSouthAfricanFiveDigitPrefixes(String phoneNumber) {
        return FIVE_DIGIT_PREFIXES.stream().anyMatch(phoneNumber::startsWith);
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

    private String addPrefix(String number) {
        return NATIONAL_PREFIX + number;
    }

    private String handleDeletedNumber(String number) {
        return number.substring(Validation.DELETED_SUBSTRING.length());
    }

    private String[] handleUpdatedNumber(String number) {
        return number.split(Validation.DELETED_SUBSTRING);
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
