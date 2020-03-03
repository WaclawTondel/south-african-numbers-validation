package com.github.southAfricanNumbersValidator.domain.phone.number.report;

import com.github.southAfricanNumbersValidator.domain.phone.number.PhoneNumber;
import com.github.southAfricanNumbersValidator.domain.phone.number.validation.Status;

import javax.persistence.*;
import java.util.List;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_id")
    private List<PhoneNumber> phoneNumbers;

    Report(Long id, List<PhoneNumber> phoneNumbers) {
        this.id = id;
        this.phoneNumbers = phoneNumbers;
    }

    public Report() {
    }

    public Report(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Long getId() {
        return id;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public String toCsv() {
        StringBuilder sb = new StringBuilder();
        sb.append("report id: ").append(id).append("\n");
        sb.append("id, sms_phone, status\n");
        phoneNumbers.forEach(phoneNumber -> sb.append(phoneNumber.toCsvRow()));
        sb.append("Total: \n");
        sb.append("Valid: ").append(countValid());
        sb.append(" Invalid: ").append(countInvalid());
        sb.append(" Fixed: ").append(countFixed());
        return sb.toString();
    }

    private Long countValid() {
        return phoneNumbers.stream()
                .filter(phoneNumber -> phoneNumber.getValidation().getStatus().equals(Status.VALID))
                .count();
    }

    private Long countInvalid() {
        return phoneNumbers.stream()
                .filter(phoneNumber -> phoneNumber.getValidation().getStatus().equals(Status.INVALID))
                .count();
    }

    private Long countFixed() {
        return phoneNumbers.stream()
                .filter(phoneNumber -> phoneNumber.getValidation().getStatus().equals(Status.FIXED))
                .count();
    }
}
