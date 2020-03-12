package br.com.softplan.sajadv.resource;

import br.com.softplan.sajadv.entity.Pessoa;
import br.com.softplan.sajadv.exception.ApiValidationException;
import br.com.softplan.sajadv.service.imp.PessoaServiceImp;
import br.com.softplan.sajadv.service.imp.StorageServiceImp;
import br.com.softplan.sajadv.wrapper.ResponseValidation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static br.com.softplan.sajadv.util.MockBuildUtils.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(PessoaResource.class)
public class PessoaResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PessoaServiceImp pessoaServiceImp;

    @MockBean
    private StorageServiceImp storageServiceImp;

    private Pessoa pessoa;
    private Pageable pageable;
    private ObjectMapper objectMapper;

    @Before
    public void init() {
        this.objectMapper = new ObjectMapper();
        this.pageable = PageRequest.of(0, 20);
        this.pessoa = buildPessoaTest(1, "Luiz Silva", "157.091.860-07", "luiz@teste.com",
                LocalDate.now(), null, true);
    }

    @Test
    public void deveCadastrarPessoaComSucesso() throws Exception {
        when(this.pessoaServiceImp.save(pessoa)).thenReturn(pessoa);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/pessoas/")
                .content(objectMapper.writeValueAsString(pessoa))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$['id']", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$['nome']", Matchers
                        .equalToIgnoringCase("Luiz Silva")))
                .andExpect(MockMvcResultMatchers.jsonPath("$['cpf']", Matchers.is("157.091.860-07")))
                .andExpect(MockMvcResultMatchers.jsonPath("$['email']", Matchers.is("luiz@teste.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$['dataNascimento']", Matchers.is(LocalDate.now()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
                .andExpect(MockMvcResultMatchers.jsonPath("$['ativo']", Matchers.is(true)));
    }

    @Test
    public void naoDeveCadastrarPessoaDeveRetornarErroDeValidacao() throws Exception {
        ResponseValidation responseValidationMessage = buildValidationMessage("Campos inválidos",
                List.of("CPF - Campo deve ser válido."));

        when(this.pessoaServiceImp.save(pessoa)).thenThrow(new ApiValidationException(
                responseValidationMessage.getResponse().getMensagem(),
                responseValidationMessage.getValidacoes()));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/pessoas/")
                .content(objectMapper.writeValueAsString(pessoa))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$['response']['mensagem']",
                        Matchers.equalToIgnoringCase("Campos inválidos")))
                .andExpect(MockMvcResultMatchers.jsonPath("$['validacoes'][0]",
                        Matchers.equalToIgnoringCase("CPF - Campo deve ser válido.")));
    }

    @Test
    public void deveAtualizarPessoaComSucesso() throws Exception {
        when(this.pessoaServiceImp.update(pessoa.getId(), pessoa)).thenReturn(pessoa);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/pessoas/1")
                .content(objectMapper.writeValueAsString(pessoa))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$['id']", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$['nome']", Matchers
                        .equalToIgnoringCase("Luiz Silva")))
                .andExpect(MockMvcResultMatchers.jsonPath("$['cpf']", Matchers.is("157.091.860-07")))
                .andExpect(MockMvcResultMatchers.jsonPath("$['email']", Matchers.is("luiz@teste.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$['dataNascimento']", Matchers.is(LocalDate.now()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
                .andExpect(MockMvcResultMatchers.jsonPath("$['ativo']", Matchers.is(true)));
    }

    @Test
    public void deveBuscarTodasAsPessoasCadastradasERetornarListaComSucesso() throws Exception {
        List<Pessoa> listPessoas = buildListPessoasTest(pessoa);

        when(this.pessoaServiceImp.findAll(this.pageable)).thenReturn(new PageImpl<>(listPessoas));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/pessoas/")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$['content'][0].['id']", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$['content'][0].['nome']", Matchers
                        .equalToIgnoringCase("Luiz Silva")))
                .andExpect(MockMvcResultMatchers.jsonPath("$['content'][0].['cpf']", Matchers.is("157.091.860-07")))
                .andExpect(MockMvcResultMatchers.jsonPath("$['content'][0].['email']", Matchers.is("luiz@teste.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$['content'][0].['dataNascimento']", Matchers.is(LocalDate.now()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
                .andExpect(MockMvcResultMatchers.jsonPath("$['content'][0].['ativo']", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$['totalElements']", Matchers.is(listPessoas.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$['totalPages']", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$['empty']", Matchers.is(false)));
    }

    @Test
    public void deveBuscarTodasAsPessoasCadastradasENaoRetornarListaComSucesso() throws Exception {
        List<Pessoa> listPessoas = new ArrayList<>();

        when(this.pessoaServiceImp.findAll(this.pageable)).thenReturn(new PageImpl<>(listPessoas));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/pessoas/")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$['totalElements']", Matchers.is(listPessoas.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$['empty']",  Matchers.is(true)));
    }
}
