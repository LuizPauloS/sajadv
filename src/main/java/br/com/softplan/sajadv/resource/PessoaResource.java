package br.com.softplan.sajadv.resource;

import br.com.softplan.sajadv.entity.Pessoa;
import br.com.softplan.sajadv.exception.ApiValidationException;
import br.com.softplan.sajadv.service.imp.PessoaServiceImp;
import br.com.softplan.sajadv.wrapper.ResponseValidationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaServiceImp pessoaServiceImp;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findAll(Pageable pageable) {
        return ResponseEntity.ok(pessoaServiceImp.findAll(pageable));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(pessoaServiceImp.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Pessoa pessoa) {
        try {
            return ResponseEntity.ok(pessoaServiceImp.save(pessoa));
        } catch (ApiValidationException e) {
            return ResponseEntity.badRequest().body(ResponseValidationMessage.builder()
                    .mensagem(e.getMessage()).validacoes(e.getMessages()).build());
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<?> update(@RequestBody Pessoa pessoa, @PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok(pessoaServiceImp.update(id, pessoa));
        } catch (ApiValidationException e) {
            return ResponseEntity.badRequest().body(ResponseValidationMessage.builder()
                    .mensagem(e.getMessage()).validacoes(e.getMessages()).build());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        this.pessoaServiceImp.delete(id);
        return ResponseEntity.ok("Registro de id" + id + "deletado com sucesso.");
    }

}
