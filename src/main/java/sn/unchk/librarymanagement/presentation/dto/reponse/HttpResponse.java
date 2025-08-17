package sn.unchk.librarymanagement.presentation.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import sn.unchk.librarymanagement.domain.exceptions.BaseException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class HttpResponse {
    public String error;
    public int code;
    public String message;

    public static HttpResponse success(String message) {
        return HttpResponse.builder()
                .code(HttpStatus.OK.value())
                .message(message)
                .build();
    }

    public static HttpResponse error(BaseException e) {
        return HttpResponse.builder()
                .code(e.getCode())
                .error(e.getField())
                .message(e.getMessage())
                .build();
    }

    public static HttpResponse error(int code, String message) {
        return HttpResponse.builder()
                .code(code)
                .message(message)
                .build();
    }
}
