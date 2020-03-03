package com.github.southAfricanNumbersValidator.domain.phone.number;

import com.github.southAfricanNumbersValidator.domain.phone.number.validation.Status;
import com.github.southAfricanNumbersValidator.domain.phone.number.validation.Validation;

public class PhoneNumberBuilder {

    private Long id;
    private String number;
    private Validation validation = new Validation(Status.VALID);
    private Long sourceId;

    public PhoneNumberBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public PhoneNumberBuilder withNumber(String number) {
        this.number = number;
        return this;
    }

    public PhoneNumberBuilder withValidation(Validation validation) {
        this.validation = validation;
        return this;
    }

    public PhoneNumberBuilder withSourceId(Long sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public PhoneNumber build() {
        return new PhoneNumber(id, number, validation, sourceId);
    }

    public PhoneNumberBuilder validPhoneNumber() {
        this.id = 1L;
        this.number = "27711111111";
        this.validation = new Validation(Status.VALID);
        this.sourceId = 1L;
        return this;
    }

    public PhoneNumberBuilder tooLongPhoneNumber() {
        this.id = 1L;
        this.number = "2771111111111111111";
        this.validation = new Validation(Status.VALID);
        this.sourceId = 1L;
        return this;
    }
}
