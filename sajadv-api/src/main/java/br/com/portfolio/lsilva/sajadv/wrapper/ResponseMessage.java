package br.com.portfolio.lsilva.sajadv.wrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseMessage {

    String mensagem;
}
