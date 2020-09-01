package com.github.southAfricanNumbersValidator.domain.phone.number;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.southAfricanNumbersValidator.domain.phone.number.validation.Status;
import com.github.southAfricanNumbersValidator.domain.phone.number.validation.Validation;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class PhoneNumber {

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

    public void validate() {
        this.validation.validate(this.number);
    }

    public String toCsv() {
        return "id, sms_phone, status\n" +
                toCsvRow();
    }

    public String toCsvRow() {
        return sourceId + "," +
                number + "," +
                validation.getStatus() + "\n";
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
