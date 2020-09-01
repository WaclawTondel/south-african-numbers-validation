package com.github.southAfricanNumbersValidator.domain.phone.number.validation;

import com.github.southAfricanNumbersValidator.domain.phone.number.PhoneNumber;
import com.github.southAfricanNumbersValidator.domain.phone.number.PhoneNumberBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationTest {

    private static final String VALID_PHONE_NUMBER = "27711123456";
    private static final String VALID_PHONE_NUMBER_2 = "27123456789";
    private static final String NOT_SOUTH_AFRICAN_PHONE_NUMBER = "12345678911";


    @Test
    void validateValidPhoneNumber() {
        //Given
        PhoneNumber phoneNumber = new PhoneNumberBuilder().validPhoneNumber().build();
        Validation validation = phoneNumber.getValidation();

        //When
        validation.validate(phoneNumber.getNumber());

        //Then
        assertEquals(Status.VALID, validation.getStatus());
    }

    @Test
    void validateNotANumberPhoneNumber() {
        //Given
        PhoneNumber phoneNumber = new PhoneNumberBuilder().validPhoneNumber()
                .withNumber("wrong")
                .build();
        Validation validation = phoneNumber.getValidation();

        //When
        validation.validate(phoneNumber.getNumber());

        //Then
        assertEquals(Status.INVALID, validation.getStatus());
        assertTrue(validation.getComments().stream()
                .anyMatch(validationComment -> validationComment.getComment().equals(Validation.NOT_A_NUMBER)));
    }

    @Test
    void validateDeletedPhoneNumber() {
        //Given
        PhoneNumber phoneNumber = new PhoneNumberBuilder()
                .validPhoneNumber()
                .withNumber(Validation.DELETED_SUBSTRING + VALID_PHONE_NUMBER)
                .build();
        Validation validation = phoneNumber.getValidation();

        //When
        validation.validate(phoneNumber.getNumber());

        //Then
        assertEquals(Status.INVALID, validation.getStatus());
        assertTrue(validation.getComments().stream()
                .anyMatch(validationComment -> validationComment.getComment().equals(Validation.DELETED_MESSAGE)));
    }

    @Test
    void validateReplacedPhoneNumber() {
        //Given
        PhoneNumber phoneNumber = new PhoneNumberBuilder()
                .validPhoneNumber()
                .withNumber(VALID_PHONE_NUMBER + Validation.DELETED_SUBSTRING + VALID_PHONE_NUMBER_2)
                .build();
        Validation validation = phoneNumber.getValidation();

        //When
        validation.validate(phoneNumber.getNumber());

        //Then
        assertEquals(Status.FIXED, validation.getStatus());
        assertTrue(validation.getComments().stream()
                .anyMatch(validationComment -> validationComment.getComment().equals(Validation.REPLACED_MESSAGE + VALID_PHONE_NUMBER_2)));
    }

    @Test
    void validatePhoneNumberWithoutPrefix() {
        //Given
        PhoneNumber phoneNumber = new PhoneNumberBuilder()
                .validPhoneNumber()
                .withNumber(VALID_PHONE_NUMBER.substring(2))
                .build();
        Validation validation = phoneNumber.getValidation();

        //When
        validation.validate(phoneNumber.getNumber());

        //Then
        assertEquals(Status.FIXED, validation.getStatus());
        assertTrue(validation.getComments().stream()
                .anyMatch(validationComment -> validationComment.getComment().equals(Validation.PREFIX_ADDED_MESSAGE)));
    }

    @Test
    void validateNotSouthAfricanPhoneNumber() {
        //Given
        PhoneNumber phoneNumber = new PhoneNumberBuilder()
                .validPhoneNumber()
                .withNumber(NOT_SOUTH_AFRICAN_PHONE_NUMBER)
                .build();
        Validation validation = phoneNumber.getValidation();

        //When
        validation.validate(phoneNumber.getNumber());

        //Then
        assertEquals(Status.INVALID, validation.getStatus());
        assertTrue(validation.getComments().stream()
                .anyMatch(validationComment -> validationComment.getComment().equals(Validation.WRONG_NATIONAL_NUMBER)));
        assertTrue(validation.getComments().stream()
                .anyMatch(validationComment -> validationComment.getComment().equals(Validation.NOT_A_SOUTH_AFRICAN_NUMBER)));
    }

    @Test
    void validateTooLongPhoneNumber() {
        //Given
        PhoneNumber phoneNumber = new PhoneNumberBuilder()
                .validPhoneNumber()
                .withNumber(VALID_PHONE_NUMBER + VALID_PHONE_NUMBER_2)
                .build();
        Validation validation = phoneNumber.getValidation();

        //When
        validation.validate(phoneNumber.getNumber());

        //Then
        assertEquals(Status.INVALID, validation.getStatus());
        assertTrue(validation.getComments().stream()
                .anyMatch(validationComment -> validationComment.getComment().equals(Validation.TOO_LONG_NUMBER)));
    }

    @Test
    void validateTooShortPhoneNumber() {
        //Given
        PhoneNumber phoneNumber = new PhoneNumberBuilder()
                .validPhoneNumber()
                .withNumber(VALID_PHONE_NUMBER.substring(5))
                .build();
        Validation validation = phoneNumber.getValidation();

        //When
        validation.validate(phoneNumber.getNumber());

        //Then
        assertEquals(Status.INVALID, validation.getStatus());
        assertTrue(validation.getComments().stream()
                .anyMatch(validationComment -> validationComment.getComment().equals(Validation.TOO_SHORT_NUMBER)));
    }
}