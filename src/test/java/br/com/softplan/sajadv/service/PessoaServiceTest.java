package br.com.softplan.sajadv.service;

import br.com.softplan.sajadv.entity.Pessoa;
import br.com.softplan.sajadv.exception.ApiValidationException;
import br.com.softplan.sajadv.repository.PessoaRepository;
import br.com.softplan.sajadv.service.imp.PessoaServiceImp;
import br.com.softplan.sajadv.utils.MockUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static br.com.softplan.sajadv.utils.MockUtils.*;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private ValidatorService validatorService;

    @InjectMocks
    private PessoaServiceImp pessoaServiceImp;

    private Pessoa pessoa;

    @Before
    public void init() {
        pessoa = buildPessoa(1,"Luiz Silva", "157.091.860-07", "luiz@teste.com",
                LocalDateTime.now(), null);
    }

    @Test
    public void deveCadastrarPessoa() throws ApiValidationException {
        Mockito.when(this.pessoaRepository.save(pessoa)).thenReturn(pessoa);

        Pessoa pessoaAdicionada = this.pessoaServiceImp.save(pessoa);
        Assert.assertNotNull(pessoaAdicionada);
    }
}
