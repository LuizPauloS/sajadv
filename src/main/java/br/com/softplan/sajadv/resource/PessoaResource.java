package br.com.softplan.sajadv.resource;

import br.com.softplan.sajadv.entity.Pessoa;
import br.com.softplan.sajadv.exception.ApiValidationException;
import br.com.softplan.sajadv.service.imp.PessoaServiceImp;
import br.com.softplan.sajadv.service.imp.StorageServiceImp;
import br.com.softplan.sajadv.wrapper.ResponseMessage;
import br.com.softplan.sajadv.wrapper.ResponseValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaServiceImp pessoaServiceImp;

    @Autowired
    private StorageServiceImp storageServiceImp;

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
            return ResponseEntity.badRequest().body(ResponseValidation.builder()
                    .response(ResponseMessage.builder().mensagem(e.getMessage()).build())
                    .validacoes(e.getMessages()).build());
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<?> update(@RequestBody Pessoa pessoa, @PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok(pessoaServiceImp.update(id, pessoa));
        } catch (ApiValidationException e) {
            return ResponseEntity.badRequest().body(ResponseValidation.builder()
                    .response(ResponseMessage.builder().mensagem(e.getMessage()).build())
                    .validacoes(e.getMessages()).build());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        this.pessoaServiceImp.delete(id);
        return ResponseEntity.ok(ResponseMessage.builder()
                .mensagem("Registro [id = " + id + "] deletado com sucesso.")
                .build());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload/{id}")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        this.pessoaServiceImp.findById(id).ifPresent(pessoa -> {
            try {
                pessoa.setFoto(file.getBytes());
                pessoa.setNomeFoto(fileName);
                pessoaServiceImp.save(pessoa);
            } catch (IOException | ApiValidationException e) {
                e.printStackTrace();
            }
        });
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download/")
                .path(fileName).path("/db")
                .toUriString();
        return ResponseEntity.ok(fileDownloadUri);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/download/{fileName:.*}")
    public ResponseEntity<?> download(@PathVariable String fileName) {
        Optional<Pessoa> optionalPessoa = pessoaServiceImp.findByNomeFoto(fileName);
        if (optionalPessoa.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(optionalPessoa.get().getFoto());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/foto/{id}")
    public ResponseEntity<String> uploadFotoPerfil(@PathVariable("id") Integer id,
                                                   @RequestParam("file") MultipartFile file) {
        String url = storageServiceImp.saveFile(file, id);
        if(!StringUtils.isEmpty(url)){
            return ResponseEntity.ok(url);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body("Ocorreu um erro ao salvar foto!");
    }

    @GetMapping("/recuperar")
    public ResponseEntity<byte[]> recuperar(@RequestParam String url) {
        return ResponseEntity.ok(storageServiceImp.readFile(url));
    }

}
