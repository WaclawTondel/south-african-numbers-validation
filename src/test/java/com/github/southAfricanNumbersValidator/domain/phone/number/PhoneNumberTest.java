package com.github.southAfricanNumbersValidator.domain.phone.number;

import com.github.southAfricanNumbersValidator.domain.phone.number.validation.Status;
import com.github.southAfricanNumbersValidator.domain.phone.number.validation.Validation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PhoneNumberTest {

    private static final String VALID_PHONE_NUMBER = "27711123456";
    private static final String VALID_PHONE_NUMBER_2 = "27123456789";

    @Test
    void validate() {
        //Given
        PhoneNumber phoneNumber = new PhoneNumberBuilder().validPhoneNumber().build();
        //When
        phoneNumber.validate();
        //Then
        assertEquals(Status.VALID, phoneNumber.getValidation().getStatus());
    }

    @Test
    void addPrefix() {
        //Given
        PhoneNumber phoneNumber = new PhoneNumberBuilder().validPhoneNumber().withNumber("711123456").build();
        //When
        phoneNumber.addPrefix();
        //Then
        assertEquals(Validation.PHONE_NUMBER_LENGTH, phoneNumber.getNumber().length());
        assertTrue(phoneNumber.getNumber().startsWith(PhoneNumber.NATIONAL_PREFIX));
    }

    @Test
    void handleDeletedNumber() {
        //Given
        String expectedPhoneNumber = VALID_PHONE_NUMBER;
        PhoneNumber phoneNumber = new PhoneNumberBuilder()
                .withNumber(Validation.DELETED_SUBSTRING + expectedPhoneNumber)
                .build();
        //When
        phoneNumber.handleDeletedNumber();
        //Then
        assertEquals(Validation.PHONE_NUMBER_LENGTH, phoneNumber.getNumber().length());
        assertEquals(expectedPhoneNumber, phoneNumber.getNumber());
    }

    @Test
    void handleUpdatedNumber() {
        //Given
        String newNumber = VALID_PHONE_NUMBER;
        String oldNumber = VALID_PHONE_NUMBER_2;
        PhoneNumber phoneNumber = new PhoneNumberBuilder()
                .withNumber(newNumber + Validation.DELETED_SUBSTRING + oldNumber)
                .build();
        //When
        String result = phoneNumber.handleUpdatedNumber();
        //Then
        assertEquals(oldNumber, result);
        assertEquals(newNumber, phoneNumber.getNumber());
    }
}