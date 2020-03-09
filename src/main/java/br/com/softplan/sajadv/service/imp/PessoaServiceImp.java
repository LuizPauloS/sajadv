package br.com.softplan.sajadv.service.imp;

import br.com.softplan.sajadv.entity.Pessoa;
import br.com.softplan.sajadv.exception.ApiValidationException;
import br.com.softplan.sajadv.exception.PessoaExcepion;
import br.com.softplan.sajadv.repository.PessoaRepository;
import br.com.softplan.sajadv.service.IPessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.softplan.sajadv.service.ValidatorService.*;

@Service
public class PessoaServiceImp implements IPessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public Pessoa save(Pessoa pessoa) throws ApiValidationException {
        if (pessoa != null) {
            validarAtributosEntidade(pessoa);
            return this.pessoaRepository.save(pessoa);
        }
        throw new PessoaExcepion("Ocorreu um erro ao registrar um cadastro de pessoa. " +
                "Verifique os dados e tente novamente.");
    }

    @Override
    public Pessoa update(Integer id, Pessoa pessoa) throws ApiValidationException {
        Optional<Pessoa> optionalPessoa;
        if (pessoa != null && id != null) {
            optionalPessoa = this.pessoaRepository.findById(id);
            validarAtributosEntidade(optionalPessoa.get());
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
        throw new PessoaExcepion("Ocorreu um erro ao atualizar dados referentes a pessoa. " +
                "Verifique se os dados e tente novamente.");
    }

    @Override
    public List<Pessoa> findAll() {
        return null;
    }

    @Override
    public Pessoa findById(Integer id) {
        return null;
    }
}
