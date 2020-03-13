package br.com.softplan.sajadv.service.imp;

import br.com.softplan.sajadv.entity.Pessoa;
import br.com.softplan.sajadv.exception.BadRequestExcepion;
import br.com.softplan.sajadv.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.FileSystems.getDefault;

@Service
public class StorageServiceImp implements IStorageService {

    private Path path;
    private final PessoaServiceImp pessoaServiceImp;
    private String pathFiles = "/home/img/";

    @Autowired
    public StorageServiceImp(PessoaServiceImp pessoaServiceImp) {
        this.path = getDefault().getPath(pathFiles);
        createDir();
        this.pessoaServiceImp = pessoaServiceImp;
    }

    @Override
    public byte[] readFile(String urlFile) {
        String url;
        Optional<Pessoa> optional = pessoaServiceImp.findByUrlFoto(urlFile);
        try {
            if (optional.isPresent()) {
                url = optional.get().getUrlFoto();
                return Files.readAllBytes(path.resolve(url));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestExcepion("Ocorreu um erro ao recuperar arquivo.");
        }
        return null;
    }

    @Override
    public String saveFile(MultipartFile file, Integer id) {
        String urlFile = "";
        String nameFile = file.getOriginalFilename();
        Optional<Pessoa> optional = pessoaServiceImp.findById(id);
        try {
            if (optional.isPresent()) {
                urlFile = path.toAbsolutePath().toString() + getDefault().getSeparator() + nameFile;
                file.transferTo(new File(urlFile));
                optional.get().setUrlFoto(urlFile);
                pessoaServiceImp.save(optional.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestExcepion("Ocorreu um erro ao salvar arquivo.");
        }
        return urlFile;
    }

    private void createDir() {
        try {
            File theDir = new File(path.toString());
            if (!theDir.exists()) {
                System.out.println(String.format("Criando diretório de arquivos - %s",
                        path.toString()));
                theDir.mkdirs();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar diretório de arquivos.", e);
        }
    }

}
