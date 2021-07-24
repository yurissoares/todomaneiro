package br.com.yuri.todomaneiro.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthorizationException extends RuntimeException {
    private static final long serialVersionUID = 123182301803L;

    private final HttpStatus httpStatus;

    public AuthorizationException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
