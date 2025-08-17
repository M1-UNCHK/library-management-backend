package sn.unchk.librarymanagement.domain.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException{

    public NotFoundException(String field, String message) {
        super(HttpStatus.NOT_FOUND.value(), field, message);
    }
}
