package com.github.southAfricanNumbersValidator.web.service.phone.number;

import com.github.southAfricanNumbersValidator.domain.phone.number.PhoneNumber;
import com.github.southAfricanNumbersValidator.domain.phone.number.PhoneNumberBuilder;
import com.github.southAfricanNumbersValidator.service.phone.number.PhoneNumberService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PhoneNumberController.class)
class PhoneNumberControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PhoneNumberService phoneNumberService;


    @Test
    void validatePhoneNumber() throws Exception {
        PhoneNumber phoneNumber = new PhoneNumberBuilder().validPhoneNumber().build();

        given(phoneNumberService.validateNumber(any(String.class))).willReturn(phoneNumber);

        mvc.perform(get("/phone-numbers/validate?number=" + phoneNumber.getNumber())
                .contentType(MediaType.ALL))
                .andExpect(status().isOk());
    }
}