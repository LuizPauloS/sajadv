package br.com.portfolio.lsilva.sajadv.service.imp;

import br.com.portfolio.lsilva.sajadv.entity.Pessoa;
import br.com.portfolio.lsilva.sajadv.exception.ApiValidationException;
import br.com.portfolio.lsilva.sajadv.exception.BadRequestExcepion;
import br.com.portfolio.lsilva.sajadv.exception.NotModifiedException;
import br.com.portfolio.lsilva.sajadv.repository.PessoaRepository;
import br.com.portfolio.lsilva.sajadv.service.IPessoaService;
import br.com.portfolio.lsilva.sajadv.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaServiceImp implements IPessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaServiceImp(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Page<Pessoa> findAll(Pageable pageable) {
        return this.pessoaRepository.findAll(pageable);
    }

    @Override
    public Optional<Pessoa> findById(Integer id) {
        if (id != null) {
            return this.pessoaRepository.findById(id);
        }
        throw new BadRequestExcepion("Ocorreu um erro ao buscar o cadastro de pessoa pelo id. " +
                "Verifique os dados e tente novamente.");
    }

    @Override
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
    public Pessoa save(Pessoa pessoa) throws ApiValidationException {
        if (pessoa != null) {
            ValidatorService.validarAtributosEntidade(pessoa);
            return this.pessoaRepository.save(pessoa);
        }
        throw new BadRequestExcepion("Ocorreu um erro ao registrar um cadastro de pessoa. " +
                "Verifique os dados e tente novamente.");
    }

    @Override
    public Pessoa update(Integer id, Pessoa pessoa) throws ApiValidationException {
        Optional<Pessoa> optional;
        if (pessoa != null && id != null) {
            optional = this.pessoaRepository.findById(id);
            ValidatorService.validarAtributosEntidade(pessoa);
            if (optional.isPresent()) {
                setValueDataPessoa(pessoa, optional);
            }
            return this.pessoaRepository.save(optional.get());
        }
        throw new BadRequestExcepion("Ocorreu um erro ao atualizar dados referentes a pessoa. " +
                "Verifique se os dados e tente novamente.");
    }

    private void setValueDataPessoa(Pessoa pessoa, Optional<Pessoa> optional) {
        optional.get().setCpf(pessoa.getCpf() != null && !optional.get().getCpf().equals(pessoa.getCpf()) ?
                pessoa.getCpf() : optional.get().getCpf());
        optional.get().setNome(pessoa.getNome() != null ?
                pessoa.getNome() : optional.get().getNome());
        optional.get().setEmail(pessoa.getEmail() != null ?
                pessoa.getEmail() : optional.get().getEmail());
        optional.get().setDataNascimento(pessoa.getDataNascimento() != null ?
                pessoa.getDataNascimento() : optional.get().getDataNascimento());
    }
}
