package br.com.portfolio.lsilva.sajadv.wrapper;

import lombok.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseValidation {

    ResponseMessage response;
    List<String> validacoes;
}
