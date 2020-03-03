package com.github.southAfricanNumbersValidator.web.service.phone.number;

import com.github.southAfricanNumbersValidator.service.phone.number.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneNumberController {

    private PhoneNumberService phoneNumberService;

    @Autowired
    public PhoneNumberController(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @GetMapping(value = "/phone-numbers/validate")
    public String validatePhoneNumber(@RequestParam("number") String number) {
        return phoneNumberService.validateNumber(number).toCsv();
    }
}
