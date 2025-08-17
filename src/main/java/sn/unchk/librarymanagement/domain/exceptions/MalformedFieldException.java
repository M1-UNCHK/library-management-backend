package sn.unchk.librarymanagement.domain.exceptions;

import org.springframework.http.HttpStatus;

public class MalformedFieldException extends BaseException {
    public MalformedFieldException(String field, String message) {
        super(HttpStatus.BAD_REQUEST.value(), field, message);
    }
}