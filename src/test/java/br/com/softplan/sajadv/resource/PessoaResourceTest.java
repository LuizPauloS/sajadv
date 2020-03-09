package br.com.softplan.sajadv.resource;

import br.com.softplan.sajadv.entity.Pessoa;
import br.com.softplan.sajadv.exception.ApiValidationException;
import br.com.softplan.sajadv.service.imp.PessoaServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static br.com.softplan.sajadv.util.MockUtils.buildPessoaTest;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(PessoaResource.class)
public class PessoaResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PessoaServiceImp pessoaServiceImp;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void cadastrarPessoaComSucesso() throws Exception {
        Pessoa pessoa = buildPessoaTest(1, "Luiz Silva", "157.091.860-07", "luiz@teste.com",
                LocalDate.now(), null, true);

        when(this.pessoaServiceImp.save(pessoa)).thenReturn(pessoa);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/pessoas/")
                .content(objectMapper.writeValueAsString(pessoa))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$['id']", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$['nome']", Matchers.
                        equalToIgnoringCase("Luiz Silva")))
                .andExpect(MockMvcResultMatchers.jsonPath("$['cpf']", Matchers.is("157.091.860-07")))
                .andExpect(MockMvcResultMatchers.jsonPath("$['email']", Matchers.is("luiz@teste.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$['dataNascimento']", Matchers.is(LocalDate.now().
                        format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
                .andExpect(MockMvcResultMatchers.jsonPath("$['ativo']", Matchers.is(true)));
    }

    @Test
    public void cadastrarPessoaComErro() throws Exception {
        Pessoa pessoa = buildPessoaTest(1, "Luiz Silva", "157.091.860-00", "luiz@teste.com",
                LocalDate.now(), null, true);

        when(this.pessoaServiceImp.save(pessoa)).thenThrow(ApiValidationException.class);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/pessoas/")
                .content(objectMapper.writeValueAsString(pessoa))
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
