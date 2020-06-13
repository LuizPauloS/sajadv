package br.com.portfolio.lsilva.sajadv.resource;

import br.com.portfolio.lsilva.sajadv.entity.Pessoa;
import br.com.portfolio.lsilva.sajadv.exception.ApiValidationException;
import br.com.portfolio.lsilva.sajadv.service.imp.PessoaServiceImp;
import br.com.portfolio.lsilva.sajadv.service.imp.StorageServiceImp;
import br.com.portfolio.lsilva.sajadv.wrapper.ResponseMessage;
import br.com.portfolio.lsilva.sajadv.wrapper.ResponseValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/persons")
public class PessoaResource {

    @Autowired
    private PessoaServiceImp pessoaServiceImp;

    @Autowired
    private StorageServiceImp storageServiceImp;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<?> findAll(Pageable pageable) {
        return ResponseEntity.ok(pessoaServiceImp.findAll(pageable));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(pessoaServiceImp.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity<?> save(@RequestBody Pessoa pessoa) {
        try {
            return ResponseEntity.ok(pessoaServiceImp.save(pessoa));
        } catch (ApiValidationException e) {
            return ResponseEntity.badRequest().body(ResponseValidation.builder()
                    .response(ResponseMessage.builder().mensagem(e.getMessage()).build())
                    .validacoes(e.getMessages()).build());
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Pessoa pessoa, @PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok(pessoaServiceImp.update(id, pessoa));
        } catch (ApiValidationException e) {
            return ResponseEntity.badRequest().body(ResponseValidation.builder()
                    .response(ResponseMessage.builder().mensagem(e.getMessage()).build())
                    .validacoes(e.getMessages()).build());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        this.pessoaServiceImp.delete(id);
        return ResponseEntity.ok(ResponseMessage.builder()
                .mensagem("Registro [id = " + id + "] deletado com sucesso.")
                .build());
    }

    @PostMapping(value = "/upload/{id}")
    public ResponseEntity<String> upload(@PathVariable("id") Integer id,
                                         @RequestParam("file") MultipartFile file) {
        String url = storageServiceImp.saveFile(file, id);
        if(!StringUtils.isEmpty(url)){
            return ResponseEntity.ok(url);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body("Ocorreu um erro ao salvar foto!");
    }

    @GetMapping("/recover/{id}")
    public ResponseEntity<byte[]> recover(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(storageServiceImp.readFile(id));
    }

}
