package com.github.southAfricanNumbersValidator.domain.phone.number.validation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class ValidationComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String comment;

    public ValidationComment() {
    }

    ValidationComment(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    String getComment() {
        return comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationComment that = (ValidationComment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comment);
    }
}
