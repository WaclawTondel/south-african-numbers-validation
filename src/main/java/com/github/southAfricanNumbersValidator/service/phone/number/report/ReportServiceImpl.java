package com.github.southAfricanNumbersValidator.service.phone.number.report;

import com.github.southAfricanNumbersValidator.domain.phone.number.PhoneNumber;
import com.github.southAfricanNumbersValidator.domain.phone.number.report.Report;
import com.github.southAfricanNumbersValidator.repositories.phone.number.report.ReportRepository;
import com.github.southAfricanNumbersValidator.service.phone.number.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private ReportRepository reportRepository;
    private PhoneNumberService phoneNumberService;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, PhoneNumberService phoneNumberService) {
        this.reportRepository = reportRepository;
        this.phoneNumberService = phoneNumberService;
    }

    public Report create(List<PhoneNumber> phoneNumbers) {
        phoneNumberService.validateNumbers(phoneNumbers);
        Report report = new Report(phoneNumbers);
        return reportRepository.save(report);
    }

    public Report get(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("There is no report with id: " + id));
    }
}
