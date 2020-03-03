package com.github.southAfricanNumbersValidator.domain.phone.number.validation;

public enum Status {
    VALID("Valid"),
    INVALID("Invalid"),
    FIXED("Fixed");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
