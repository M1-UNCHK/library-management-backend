package sn.unchk.librarymanagement.presentation.exceptions;

import org.springframework.http.HttpStatus;
import sn.unchk.librarymanagement.domain.exceptions.BaseException;

import java.util.Objects;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException(String field, String message) {
        super(HttpStatus.FORBIDDEN.value(), field, Objects.toString(message, "Access denied. You are not allowed to access this resource."));
    }
}
