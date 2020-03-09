package br.com.softplan.sajadv.util;

import br.com.softplan.sajadv.entity.Pessoa;
import java.time.LocalDate;

public class MockUtils {

    public static Pessoa buildPessoaTest(Integer id, String nome, String cpf, String email,
                                         LocalDate dataNascimento, byte[] foto, boolean ativo) {
        Pessoa pessoa = Pessoa.builder().nome(nome).cpf(cpf).email(email).ativo(ativo)
                .dataNascimento(dataNascimento).foto(foto).build();
        pessoa.setId(id);
        return pessoa;
    }
}
