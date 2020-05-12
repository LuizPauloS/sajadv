package br.com.portfolio.lsilva.sajadv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestExcepion extends RuntimeException {

    public BadRequestExcepion(String message) {
        super(message);
    }
}
