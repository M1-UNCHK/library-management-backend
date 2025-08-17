package sn.unchk.librarymanagement.presentation.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sn.unchk.librarymanagement.domain.exceptions.*;
import sn.unchk.librarymanagement.presentation.dto.reponse.HttpResponse;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<HttpResponse> handleNotFoundException(BaseException e) {
        log.error("NOT_FOUND - FIELD:{} :: {}:", e.getField(), e.getMessage());
        HttpResponse errorResponse = HttpResponse.error(e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler({MalformedFieldException.class, PasswordNotMatchException.class})
    public ResponseEntity<HttpResponse> handleBadRequestException(BaseException e) {
        log.error("BAD_REQUEST - FIELD:{} :: {}", e.getField(), e.getMessage());
        HttpResponse errorResponse = HttpResponse.error(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<HttpResponse> handleForbiddenException(BaseException e) {
        log.error("FORBIDDEN - FIELD:{} :: {}", e.getField(), e.getMessage());
        HttpResponse errorResponse = HttpResponse.error(e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler({AlreadyExistsException.class})
    public ResponseEntity<HttpResponse> handleConflictException(BaseException e) {
        log.error("CONFLICT - FIELD:{} :: {}", e.getField(), e.getMessage());
        HttpResponse errorResponse = HttpResponse.error(e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<HttpResponse> handleGenericException(RuntimeException e) {
        log.error("RUNTIME EXCEPTION:: {}", e.getMessage());
        HttpResponse errorResponse = HttpResponse.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
