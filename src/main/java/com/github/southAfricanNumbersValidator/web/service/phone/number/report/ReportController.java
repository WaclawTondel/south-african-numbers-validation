package com.github.southAfricanNumbersValidator.web.service.phone.number.report;

import com.github.southAfricanNumbersValidator.domain.phone.number.PhoneNumber;
import com.github.southAfricanNumbersValidator.service.phone.number.report.ReportService;
import com.github.southAfricanNumbersValidator.utils.CsvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/phone-numbers/reports")
public class ReportController {

    private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public String uploadMultipart(@RequestParam("file") MultipartFile file) throws IOException {
        return reportService.create(CsvUtils.read(PhoneNumber.class, file.getInputStream())).toCsv();
    }

    @GetMapping(value = "/{id}")
    public String getReport(@PathVariable Long id) {
        return reportService.get(id).toCsv();
    }
}
