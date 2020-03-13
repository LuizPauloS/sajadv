package br.com.softplan.sajadv.exception;

import lombok.Data;
import java.util.List;

@Data
public class ApiValidationException extends Exception {

    private List<String> messages;

    public ApiValidationException(String message, List<String> messages){
        super(message);
        this.messages = messages;
    }
}
