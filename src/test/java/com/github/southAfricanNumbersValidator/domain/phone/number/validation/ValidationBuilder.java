package com.github.southAfricanNumbersValidator.domain.phone.number.validation;

import java.util.Collections;
import java.util.List;

public class ValidationBuilder {
    private Long id;
    private Status status;
    private List<ValidationComment> comments;

    public Validation build() {
        return new Validation(id, status, comments);
    }

    public ValidationBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ValidationBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    public ValidationBuilder withComments(List<ValidationComment> comments) {
        this.comments = comments;
        return this;
    }

    public ValidationBuilder ValidValidation() {
        this.status = Status.VALID;
        this.comments = Collections.emptyList();
        return this;
    }
}
