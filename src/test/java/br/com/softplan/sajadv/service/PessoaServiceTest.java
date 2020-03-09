package br.com.softplan.sajadv.service;

import br.com.softplan.sajadv.entity.Pessoa;
import br.com.softplan.sajadv.exception.ApiValidationException;
import br.com.softplan.sajadv.repository.PessoaRepository;
import br.com.softplan.sajadv.service.imp.PessoaServiceImp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaServiceImp pessoaServiceImp;

    @Test
    public void deveCadastrarPessoaComDadosValidos() throws ApiValidationException {
        Pessoa pessoa = Pessoa.builder().nome("Luiz Silva").cpf("157.091.860-07").email("luiz@teste.com")
                .dataNascimento(LocalDate.of(1980, 12, 2)).foto(null).build();
        when(this.pessoaRepository.save(pessoa)).thenReturn(pessoa);

        Pessoa pessoaAdicionada = this.pessoaServiceImp.save(pessoa);

        assertNotNull(pessoaAdicionada);
        assertEquals(pessoaAdicionada.getCpf(), pessoa.getCpf());
        assertEquals(pessoaAdicionada.getEmail(), pessoa.getEmail());
        assertEquals(pessoaAdicionada.getDataNascimento(), pessoa.getDataNascimento());
    }

    @Test(expected = ApiValidationException.class)
    public void naoDeveCadastrarPessoaComCPFInvalido() throws ApiValidationException {
        Pessoa pessoaCpfInvalido = Pessoa.builder().nome("José Santos").cpf("111.111.111-11").email("jose@teste.com")
                .dataNascimento(LocalDate.of(1988, 1, 25)).foto(null).build();

        this.pessoaServiceImp.save(pessoaCpfInvalido);
    }

    @Test(expected = ApiValidationException.class)
    public void naoDeveCadastrarPessoaComEmailInvalido() throws ApiValidationException {
        Pessoa pessoaEmailInvalido = Pessoa.builder().nome("João Carvalho").cpf("14964166007").email("joao.teste.com")
                .dataNascimento(LocalDate.of(1988, 1, 25)).foto(null).build();

        this.pessoaServiceImp.save(pessoaEmailInvalido);
    }

    @Test
    public void deveAtualizarPessoaComDadosValidos() throws ApiValidationException {
        final int id = 1;
        Pessoa pessoa = Pessoa.builder().nome("Luiz Silva").cpf("157.091.860-07").email("luiz@teste.com")
                .dataNascimento(LocalDate.of(1980, 12, 2)).foto(null).build();

        Pessoa pessoaAlterada = Pessoa.builder().nome("Luiz Paulo da Silva").cpf("665.617.950-91")
                .email("luiz.paulo.09@hotmail.com").dataNascimento(LocalDate.of(1991, 12, 2))
                .build();

        when(this.pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));
        when(this.pessoaRepository.save(pessoaAlterada)).thenReturn(pessoaAlterada);

        Pessoa pessoaAdicionada = this.pessoaServiceImp.update(id, pessoaAlterada);

        assertNotNull(pessoaAdicionada);
        assertEquals(pessoaAdicionada.getCpf(), pessoa.getCpf());
        assertEquals(pessoaAdicionada.getEmail(), pessoa.getEmail());
        assertEquals(pessoaAdicionada.getDataNascimento(), pessoa.getDataNascimento());
    }
}
