package br.com.softplan.sajadv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PessoaExcepion extends RuntimeException {

    public PessoaExcepion(String message) {
        super(message);
    }
}
