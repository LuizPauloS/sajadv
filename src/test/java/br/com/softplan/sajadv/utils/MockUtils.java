package br.com.softplan.sajadv.utils;

import br.com.softplan.sajadv.entity.Pessoa;
import java.time.LocalDateTime;

public class MockUtils {

    public static Pessoa buildPessoa(Integer id, String nome, String cpf, String email,
                                     LocalDateTime dataNascimento, byte[] foto) {
        return new Pessoa(id, nome, cpf, email, dataNascimento, foto);
    }
}
