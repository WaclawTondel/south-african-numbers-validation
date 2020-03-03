package com.github.southAfricanNumbersValidator.domain.phone.number;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.southAfricanNumbersValidator.domain.phone.number.validation.Status;
import com.github.southAfricanNumbersValidator.domain.phone.number.validation.Validation;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class PhoneNumber {
    public final static String NATIONAL_PREFIX = "27";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    @JsonProperty("sms_phone")
    private String number;
    @OneToOne(cascade = CascadeType.ALL)
    private Validation validation = new Validation(Status.VALID);
    @JsonProperty("id")
    private Long sourceId;

    PhoneNumber(Long id, String number, Validation validation, Long sourceId) {
        this.id = id;
        this.number = number;
        this.validation = validation;
        this.sourceId = sourceId;
    }

    public PhoneNumber() {
    }

    public PhoneNumber(String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Validation getValidation() {
        return validation;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void validate() {
        this.validation.validate(this);
    }

    public void addPrefix() {
        this.number = NATIONAL_PREFIX + this.number;
    }

    public void handleDeletedNumber() {
        this.number = number.substring(Validation.DELETED_SUBSTRING.length());
    }

    public String handleUpdatedNumber() {
        String[] numbers = this.number.split(Validation.DELETED_SUBSTRING);
        this.number = numbers[0];
        return numbers[1];
    }

    public String toCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append("id, sms_phone, status\n");
        sb.append(toCsvRow());
        return sb.toString();
    }

    public String toCsvRow() {
        StringBuilder sb = new StringBuilder();
        sb.append(sourceId)
                .append(",")
                .append(number)
                .append(",")
                .append(validation.getStatus())
                .append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(number, that.number) &&
                Objects.equals(validation, that.validation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, validation);
    }
}
