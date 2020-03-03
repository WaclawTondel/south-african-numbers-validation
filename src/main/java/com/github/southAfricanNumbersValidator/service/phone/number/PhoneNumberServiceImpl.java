package com.github.southAfricanNumbersValidator.service.phone.number;

import com.github.southAfricanNumbersValidator.domain.phone.number.PhoneNumber;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    public void validateNumbers(List<PhoneNumber> phoneNumbers) {
        phoneNumbers.forEach(PhoneNumber::validate);
    }

    public PhoneNumber validateNumber(String number) {
        PhoneNumber phoneNumber = new PhoneNumber(number);
        phoneNumber.validate();
        return phoneNumber;
    }
}
