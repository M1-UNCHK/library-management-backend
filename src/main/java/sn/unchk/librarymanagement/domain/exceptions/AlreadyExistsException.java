package sn.unchk.librarymanagement.domain.exceptions;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends BaseException {
    public AlreadyExistsException(String field) {
        super(HttpStatus.CONFLICT.value(), field, String.format("%s already exists ", field));
    }
}
