package br.com.portfolio.lsilva.sajadv.service.imp;

import br.com.portfolio.lsilva.sajadv.entity.Pessoa;
import br.com.portfolio.lsilva.sajadv.exception.BadRequestExcepion;
import br.com.portfolio.lsilva.sajadv.exception.NotModifiedException;
import br.com.portfolio.lsilva.sajadv.repository.PessoaRepository;
import br.com.portfolio.lsilva.sajadv.service.IPessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@CacheConfig(cacheNames = "personsCache")
public class PessoaServiceImp implements IPessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaServiceImp(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    @Cacheable(cacheNames = "findAllPersonsCache")
    public Page<Pessoa> findAll(Pageable pageable) {
        return this.pessoaRepository.findAll(pageable);
    }

    @Override
    @Cacheable(cacheNames = "findPersonByIdCache")
    public Optional<Pessoa> findById(Integer id) {
        if (id != null) {
            return this.pessoaRepository.findById(id);
        }
        throw new BadRequestExcepion("Ocorreu um erro ao buscar o cadastro de pessoa pelo id. " +
                "Verifique os dados e tente novamente.");
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "findAllPersonsCache", allEntries = true),
            @CacheEvict(cacheNames = "findPersonByIdCache", allEntries = true)
    })
    public void delete(Integer id) {
        if (id != null) {
            this.pessoaRepository.findById(id).ifPresent(pessoa -> {
                if (pessoa.isAtivo()) {
                    pessoa.setAtivo(false);
                    pessoaRepository.save(pessoa);
                    return;
                }
                throw new NotModifiedException("Registro já deletado da base de dados!");
            });
        }
    }

    @Override
    @Caching(evict = {
        @CacheEvict(cacheNames = "findAllPersonsCache", allEntries = true),
        @CacheEvict(cacheNames = "findPersonByIdCache", allEntries = true)
    })
    public Pessoa save(Pessoa pessoa) {
        if (pessoa != null) {
            return this.pessoaRepository.save(pessoa);
        }
        throw new BadRequestExcepion("Ocorreu um erro ao registrar um cadastro de pessoa. " +
                "Verifique os dados e tente novamente.");
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "findAllPersonsCache", allEntries = true),
            @CacheEvict(cacheNames = "findPersonByIdCache", allEntries = true)
    })
    public Pessoa update(Integer id, Pessoa pessoa) {
        Optional<Pessoa> optional;
        if (pessoa != null && id != null) {
            optional = this.pessoaRepository.findById(id);
            optional.ifPresent(value -> setValueDataPessoa(pessoa, value));
            return this.pessoaRepository.save(optional.get());
        }
        throw new BadRequestExcepion("Ocorreu um erro ao atualizar dados referentes a pessoa. " +
                "Verifique se os dados e tente novamente.");
    }

    private void setValueDataPessoa(Pessoa pessoa, Pessoa pessoaDB) {
        pessoaDB.setCpf(pessoa.getCpf() != null && !pessoaDB.getCpf().equals(pessoa.getCpf()) ?
                pessoa.getCpf() : pessoaDB.getCpf());
        pessoaDB.setNome(pessoa.getNome() != null ?
                pessoa.getNome() : pessoaDB.getNome());
        pessoaDB.setEmail(pessoa.getEmail() != null ?
                pessoa.getEmail() : pessoaDB.getEmail());
        pessoaDB.setDataNascimento(pessoa.getDataNascimento() != null ?
                pessoa.getDataNascimento() : pessoaDB.getDataNascimento());
    }
}
