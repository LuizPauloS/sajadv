package br.com.softplan.sajadv.service;

import br.com.softplan.sajadv.entity.Pessoa;
import br.com.softplan.sajadv.exception.ApiValidationException;
import br.com.softplan.sajadv.exception.BadRequestExcepion;
import br.com.softplan.sajadv.repository.PessoaRepository;
import br.com.softplan.sajadv.service.imp.PessoaServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static br.com.softplan.sajadv.util.MockBuildUtils.buildListPessoasTest;
import static br.com.softplan.sajadv.util.MockBuildUtils.buildPessoaTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaServiceImp pessoaServiceImp;

    private Pessoa pessoa;

    @Before
    public void init() {
        this.pessoa = buildPessoaTest(1,"Luiz Silva", "157.091.860-07",
                "luiz@teste.com", LocalDate.of(1980, 12, 2), null, true);
    }

    @Test
    public void deveCadastrarPessoaComDadosValidos() throws ApiValidationException {
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
        Pessoa pessoaEmailInvalido = buildPessoaTest(1,"João Carvalho", "14964166007",
                "joao.teste.com", LocalDate.of(1988, 1, 25), null, true);

        this.pessoaServiceImp.save(pessoaEmailInvalido);
    }

    @Test
    public void deveAtualizarPessoaComDadosValidos() throws ApiValidationException {
        final int id = 1;
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

    @Test
    public void deveBuscarTodasAsPessoasCadastradas() {
        Pageable pageable = PageRequest.of(0, 20);

        List<Pessoa> listPessoas = buildListPessoasTest(pessoa);

        when(this.pessoaRepository.findAll(pageable)).thenReturn(new PageImpl<>(listPessoas));

        Page<Pessoa> pessoas = this.pessoaServiceImp.findAll(pageable);

        assertNotNull(pessoas);
        assertFalse(pessoas.getContent().isEmpty());
        assertTrue(pessoas.getTotalElements() > 0);
        assertEquals(pessoas.getSize(), listPessoas.size());
        assertEquals(pessoas.getContent().get(0), listPessoas.get(0));
        assertEquals(pessoas.getContent().get(1).getId(), listPessoas.get(1).getId());
        assertEquals(pessoas.getContent().get(1).getCpf(), listPessoas.get(1).getCpf());
    }

    @Test
    public void deveBuscarPessoaCadastradaPorId() {
        final int id = 1;

        when(this.pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));

        Optional<Pessoa> optionalPessoa = this.pessoaServiceImp.findById(id);

        assertTrue(optionalPessoa.isPresent());
        assertEquals(optionalPessoa.get().getId(), pessoa.getId());
        assertEquals(optionalPessoa.get().getCpf(), pessoa.getCpf());
        assertEquals(optionalPessoa.get().getEmail(), pessoa.getEmail());
    }

    @Test
    public void naoDeveBuscarPessoaCadastradaIdNaoEncontrado() {
        final int id = 3;

        when(this.pessoaRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Pessoa> optionalPessoa = this.pessoaServiceImp.findById(id);

        assertTrue(optionalPessoa.isEmpty());
    }

    @Test(expected = BadRequestExcepion.class)
    public void naoDeveBuscarPessoaCadastradaErroPorNaoInformarId() {
        Integer id = null;
        this.pessoaServiceImp.findById(id);
    }
}