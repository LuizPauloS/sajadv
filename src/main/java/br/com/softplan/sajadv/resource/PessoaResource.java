package br.com.softplan.sajadv.resource;

import br.com.softplan.sajadv.entity.Pessoa;
import br.com.softplan.sajadv.exception.ApiValidationException;
import br.com.softplan.sajadv.service.imp.PessoaServiceImp;
import br.com.softplan.sajadv.wrapper.ResponseValidationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaServiceImp pessoaServiceImp;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Pessoa pessoa) {
        try {
            return ResponseEntity.ok(pessoaServiceImp.save(pessoa));
        } catch (ApiValidationException e) {
            return ResponseEntity.badRequest().body(ResponseValidationMessage.builder()
                    .mensagem(e.getMessage()).validacoes(e.getMessages()).build());
        }
    }
}
