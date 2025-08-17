package sn.unchk.librarymanagement.presentation.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.Set;

public class ConstraintValidationException extends ConstraintViolationException {
    public ConstraintValidationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(constraintViolations);
    }
}
