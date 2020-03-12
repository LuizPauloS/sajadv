package br.com.softplan.sajadv.util;

import br.com.softplan.sajadv.entity.Pessoa;
import br.com.softplan.sajadv.wrapper.ResponseMessage;
import br.com.softplan.sajadv.wrapper.ResponseValidation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockBuildUtils {

    public static Pessoa buildPessoaTest(Integer id, String nome, String cpf, String email,
                                         LocalDate dataNascimento, byte[] foto, boolean ativo) {
        Pessoa pessoa = Pessoa.builder().nome(nome).cpf(cpf).email(email).ativo(ativo)
                .dataNascimento(dataNascimento).foto(foto).build();
        pessoa.setId(id);
        return pessoa;
    }

    public static ResponseValidation buildValidationMessage(String mensagem,
                                                            List<String> validacoes) {
        return ResponseValidation.builder()
                .response(ResponseMessage.builder().mensagem(mensagem).build())
                .validacoes(validacoes).build();
    }

    public static List<Pessoa> buildListPessoasTest(Pessoa pessoa) {
        List<Pessoa> pessoasList = new ArrayList<>();
        pessoa.setId(1);
        pessoasList.add(pessoa);
        for (int indice = pessoa.getId(); indice <= 9; indice++) {
            Pessoa pessoaClone = buildPessoaTest((pessoa.getId() + indice),
                    pessoa.getNome() + " " + indice, buildCpfsValidos().get(indice),
                    pessoa.getEmail(), pessoa.getDataNascimento(), null, true );
            pessoasList.add(pessoaClone);
        }
        return pessoasList;
    }

    private static List<String> buildCpfsValidos() {
        return List.of("955.297.930-70", "728.829.510-94", "356.196.930-58", "565.373.790-91",
                "492.518.610-80", "093.575.950-66", "569.497.780-74", "049.172.060-22", "293.276.070-07",
                "059.876.770-33");
    }
}
