package com.github.southAfricanNumbersValidator.service.phone.number.report;

import com.github.southAfricanNumbersValidator.domain.phone.number.PhoneNumber;
import com.github.southAfricanNumbersValidator.domain.phone.number.report.Report;

import java.util.List;

public interface ReportService {
    Report create(List<PhoneNumber> phoneNumbers);

    Report get(Long id);
}
