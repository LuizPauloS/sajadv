package br.com.portfolio.lsilva.sajadv.service.imp;

import br.com.portfolio.lsilva.sajadv.entity.Pessoa;
import br.com.portfolio.lsilva.sajadv.exception.BadRequestExcepion;
import br.com.portfolio.lsilva.sajadv.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static java.nio.file.FileSystems.getDefault;

@Service
public class StorageServiceImp implements IStorageService {

    @Value("${storage.local.images}")
    private String pathFiles;

    private Path path;
    private final PessoaServiceImp pessoaServiceImp;

    @Autowired
    public StorageServiceImp(PessoaServiceImp pessoaServiceImp) {
        this.pessoaServiceImp = pessoaServiceImp;
    }

    @Override
    public byte[] readFile(Integer id) {
        verifyDirCreated();
        String url;
        Pessoa pessoa = pessoaServiceImp.findById(id);
        try {
            if (pessoa != null) {
                url = pessoa.getArquivo();
                return Files.readAllBytes(path.resolve(url));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequestExcepion("Ocorreu um erro ao recuperar arquivo.");
        }
        return null;
    }

    @Override
    @Caching(put = @CachePut("findPersonByIdCache"),
            evict = @CacheEvict(cacheNames = "findAllPersonsCache", allEntries = true)
    )
    public String saveFile(MultipartFile file, Integer id) {
        verifyDirCreated();
        String urlFile = "";
        Pessoa pessoa = pessoaServiceImp.findById(id);
        try {
            if (pessoa != null) {
                deleteImageExisting(pessoa);
                urlFile = addImageDir(file, pessoa);
                pessoaServiceImp.save(pessoa);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestExcepion("Ocorreu um erro ao salvar arquivo.");
        }
        return urlFile;
    }

    private void deleteImageExisting(Pessoa pessoa) throws IOException {
        if (pessoa.getArquivo() != null) {
            System.out.println("Deletando imagem existente: " +
                    Files.deleteIfExists(Paths.get(path.toAbsolutePath().toString() + getDefault().getSeparator() +
                            pessoa.getArquivo())));
        }
    }

    private String addImageDir(MultipartFile file, Pessoa pessoa) throws IOException {
        String urlFile;
        urlFile = path.toAbsolutePath().toString() + getDefault().getSeparator() + file.getOriginalFilename();
        file.transferTo(new File(urlFile));
        pessoa.setArquivo(urlFile);
        return urlFile;
    }

    private void verifyDirCreated() {
        this.path = getDefault().getPath(pathFiles);
        createDir();
    }

    private void createDir() {
        try {
            File theDir = new File(path.toString());
            if (!theDir.exists()) {
                System.out.println(String.format("Criando diretório de arquivos - %s",
                        path.toString()));
                theDir.mkdirs();
            } else {
                System.out.println(String.format("Diretório de arquivos existente - %s",
                        path.toString()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar diretório de arquivos.", e);
        }
    }

}
