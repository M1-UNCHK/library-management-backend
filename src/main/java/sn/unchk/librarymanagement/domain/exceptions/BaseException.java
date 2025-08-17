package sn.unchk.librarymanagement.domain.exceptions;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    private int code;
    private String field;
    private String message;

    private BaseException(String message) {
        super(message);
    }
    public BaseException(int code, String field, String message) {
        this.code = code;
        this.field = field;
        this.message = message;
    }

}