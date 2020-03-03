package com.github.southAfricanNumbersValidator.service.phone.number;

import com.github.southAfricanNumbersValidator.domain.phone.number.PhoneNumber;
import com.github.southAfricanNumbersValidator.domain.phone.number.PhoneNumberBuilder;
import com.github.southAfricanNumbersValidator.domain.phone.number.validation.Status;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PhoneNumberServiceTest {

    private PhoneNumberService testedService = new PhoneNumberServiceImpl();

    @Test
    void validateNumbers() {
        //Given
        List<PhoneNumber> phoneNumbers = Arrays.asList(
                new PhoneNumberBuilder().validPhoneNumber().build(),
                new PhoneNumberBuilder().tooLongPhoneNumber().build()
        );
        //When
        testedService.validateNumbers(phoneNumbers);
        //Then
        assertTrue(phoneNumbers.stream()
                .anyMatch(phoneNumber -> phoneNumber.getValidation().getStatus().equals(Status.VALID)));
        assertTrue(phoneNumbers.stream()
                .anyMatch(phoneNumber -> phoneNumber.getValidation().getStatus().equals(Status.INVALID)));
    }

    @Test
    void validateNumber() {
        //Given
        String phoneNumber = "27711234567";
        //When
        PhoneNumber result = testedService.validateNumber(phoneNumber);
        //Then
        assertEquals(phoneNumber, result.getNumber());
        assertEquals(Status.VALID, result.getValidation().getStatus());
    }
}