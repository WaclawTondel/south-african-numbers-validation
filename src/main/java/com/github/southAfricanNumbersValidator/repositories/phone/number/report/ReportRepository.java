package com.github.southAfricanNumbersValidator.repositories.phone.number.report;

import com.github.southAfricanNumbersValidator.domain.phone.number.report.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends CrudRepository<Report, Long> {
}
