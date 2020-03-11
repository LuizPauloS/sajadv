package br.com.softplan.sajadv.wrapper;

import lombok.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseValidationMessage {

    String mensagem;
    List<String> validacoes;
}
