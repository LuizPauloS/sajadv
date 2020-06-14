package br.com.portfolio.lsilva.sajadv.resource;

import br.com.portfolio.lsilva.sajadv.entity.Pessoa;
import br.com.portfolio.lsilva.sajadv.service.imp.PessoaServiceImp;
import br.com.portfolio.lsilva.sajadv.service.imp.StorageServiceImp;
import br.com.portfolio.lsilva.sajadv.wrapper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/persons")
public class PessoaResource {

    private final PessoaServiceImp pessoaServiceImp;
    private final StorageServiceImp storageServiceImp;

    @Autowired
    public PessoaResource(PessoaServiceImp pessoaServiceImp, StorageServiceImp storageServiceImp) {
        this.pessoaServiceImp = pessoaServiceImp;
        this.storageServiceImp = storageServiceImp;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<?> findAll(Pageable pageable) {
        return ResponseEntity.ok(pessoaServiceImp.findAll(pageable));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(pessoaServiceImp.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ResponseEntity<?> save(@Valid @RequestBody Pessoa pessoa) {
        return ResponseEntity.ok(pessoaServiceImp.save(pessoa));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Pessoa pessoa, @PathVariable("id") Integer id) {
        return ResponseEntity.ok(pessoaServiceImp.update(id, pessoa));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        this.pessoaServiceImp.delete(id);
        return ResponseEntity.ok(ResponseMessage.builder()
                .mensagem("Registro [id = " + id + "] deletado com sucesso.")
                .build());
    }

    @PostMapping(value = "/upload/{id}")
    public ResponseEntity<?> upload(@PathVariable("id") Integer id,
                                         @RequestParam("file") MultipartFile file) {
        String urlImage = storageServiceImp.saveFile(file, id);
        return ResponseEntity.ok(ResponseMessage.builder()
                .mensagem("Foto adicionada com sucesso [URL = " + urlImage + "].")
                .build());
    }

    @GetMapping("/recover/{id}")
    public ResponseEntity<byte[]> recover(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(storageServiceImp.readFile(id));
    }

}
