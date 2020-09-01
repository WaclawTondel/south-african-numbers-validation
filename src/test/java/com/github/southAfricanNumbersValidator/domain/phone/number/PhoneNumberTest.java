package com.github.southAfricanNumbersValidator.domain.phone.number;

import com.github.southAfricanNumbersValidator.domain.phone.number.validation.Status;
import com.github.southAfricanNumbersValidator.domain.phone.number.validation.Validation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PhoneNumberTest {

    @Test
    void validateValidPhoneNumber() {
        //Given
        PhoneNumber phoneNumber = new PhoneNumberBuilder().validPhoneNumber().build();
        //When
        phoneNumber.validate();
        //Then
        assertEquals(Status.VALID, phoneNumber.getValidation().getStatus());
    }

    @Test
    void validateTooLongPhoneNumber() {
        //Given
        PhoneNumber phoneNumber = new PhoneNumberBuilder().tooLongPhoneNumber().build();
        //When
        phoneNumber.validate();
        //Then
        assertEquals(Status.INVALID, phoneNumber.getValidation().getStatus());
    }
}