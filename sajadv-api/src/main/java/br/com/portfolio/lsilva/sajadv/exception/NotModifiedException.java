package br.com.portfolio.lsilva.sajadv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_MODIFIED)
public class NotModifiedException extends RuntimeException {

    public NotModifiedException(String message) {
        super(message);
    }
}
