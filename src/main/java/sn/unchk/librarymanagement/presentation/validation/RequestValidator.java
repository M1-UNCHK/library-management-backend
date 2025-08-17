package sn.unchk.librarymanagement.presentation.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sn.unchk.librarymanagement.domain.validation.ValidationGroup;
import sn.unchk.librarymanagement.presentation.exceptions.ConstraintValidationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class RequestValidator {
    private final Validator validator;


    public <T> void assertValidity(T object, Class<? extends ValidationGroup> groups) {
        Set<ConstraintViolation<T>> violations = validator.validate(object, groups);
        if (!violations.isEmpty()) {
            throw new ConstraintValidationException(violations);
        }
    }

    public <T> void assertValidity(List<T> objects, Class<? extends ValidationGroup> groups) {
        Set<ConstraintViolation<T>> violations = new HashSet<>();
        if (nonNull(objects) && !objects.isEmpty()) {
            objects.forEach(o -> violations.addAll(validator.validate(o)));
        }
        if (!violations.isEmpty()){
            throw new ConstraintValidationException(violations);
        }
    }

}
