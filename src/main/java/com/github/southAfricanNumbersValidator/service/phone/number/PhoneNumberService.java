package com.github.southAfricanNumbersValidator.service.phone.number;

import com.github.southAfricanNumbersValidator.domain.phone.number.PhoneNumber;

import java.util.List;

public interface PhoneNumberService {
    PhoneNumber validateNumber(String number);

    void validateNumbers(List<PhoneNumber> phoneNumbers);
}
