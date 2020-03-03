package com.github.southAfricanNumbersValidator.web.service.phone.number.report;

import com.github.southAfricanNumbersValidator.domain.phone.number.PhoneNumberBuilder;
import com.github.southAfricanNumbersValidator.domain.phone.number.report.Report;
import com.github.southAfricanNumbersValidator.domain.phone.number.report.ReportBuilder;
import com.github.southAfricanNumbersValidator.service.phone.number.report.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReportController.class)
class ReportControllerTest {

    private static final Report testReport = new ReportBuilder()
            .withId(1L)
            .withPhoneNumbers(Collections.singletonList(new PhoneNumberBuilder()
                    .validPhoneNumber()
                    .build()))
            .build();
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ReportService reportService;

    @Test
    void uploadMultipart() throws Exception {
        given(reportService.create(any())).willReturn(testReport);
        MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "multipart/form-data", ("id,sms_phone\n" +
                "103343262,6478342944\n" +
                "103426540,84528784843\n" +
                "103278808,263716791426\n" +
                "103426733,27736529279").getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/phone-numbers/reports//upload")
                .file(file))
                .andExpect(status().isOk());
    }

    @Test
    void getReport() throws Exception {
        given(reportService.get(any(Long.class))).willReturn(testReport);

        mvc.perform(get("/phone-numbers/reports/" + testReport.getId())
                .contentType(MediaType.ALL))
                .andExpect(status().isOk());
    }
}
