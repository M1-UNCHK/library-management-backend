package sn.unchk.librarymanagement.presentation.exceptions;

import org.springframework.http.HttpStatus;
import sn.unchk.librarymanagement.domain.exceptions.BaseException;

import java.util.Objects;

public class PasswordNotMatchException extends BaseException {
    public PasswordNotMatchException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), "password", Objects.toString(message, "Old Password not match with current password"));
    }
}
