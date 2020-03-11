package br.com.softplan.sajadv.service.imp;

import br.com.softplan.sajadv.entity.Pessoa;
import br.com.softplan.sajadv.exception.ApiValidationException;
import br.com.softplan.sajadv.exception.BadRequestExcepion;
import br.com.softplan.sajadv.exception.NotModifiedException;
import br.com.softplan.sajadv.repository.PessoaRepository;
import br.com.softplan.sajadv.service.IPessoaService;
import br.com.softplan.sajadv.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaServiceImp implements IPessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

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
        Optional<Pessoa> optionalPessoa;
        if (pessoa != null && id != null) {
            optionalPessoa = this.pessoaRepository.findById(id);
            ValidatorService.validarAtributosEntidade(optionalPessoa.get());
            optionalPessoa.ifPresent(pessoaAtualizada -> {
                pessoaAtualizada.setCpf(pessoaAtualizada.getCpf() == null ? pessoa.getCpf() :
                        pessoaAtualizada.getCpf());
                pessoaAtualizada.setNome(pessoaAtualizada.getNome() == null ? pessoa.getNome() :
                        pessoaAtualizada.getNome());
                pessoaAtualizada.setCpf(pessoaAtualizada.getEmail() == null ? pessoa.getEmail() :
                        pessoaAtualizada.getEmail());
                pessoaAtualizada.setDataNascimento(pessoaAtualizada.getDataNascimento() == null ?
                        pessoa.getDataNascimento() : pessoaAtualizada.getDataNascimento());
                pessoaAtualizada.setAtivo(!pessoaAtualizada.isAtivo() ? pessoa.isAtivo() :
                        pessoaAtualizada.isAtivo());
                this.pessoaRepository.save(pessoaAtualizada);
            });
            return optionalPessoa.get();
        }
        throw new BadRequestExcepion("Ocorreu um erro ao atualizar dados referentes a pessoa. " +
                "Verifique se os dados e tente novamente.");
    }
}
