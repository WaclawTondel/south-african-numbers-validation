package com.github.southAfricanNumbersValidator.domain.phone.number.report;

import com.github.southAfricanNumbersValidator.domain.phone.number.PhoneNumber;

import java.util.List;

public class ReportBuilder {
    private Long id;
    private List<PhoneNumber> phoneNumbers;

    public Report build() {
        return new Report(id, phoneNumbers);
    }

    public ReportBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ReportBuilder withPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
        return this;
    }

}
