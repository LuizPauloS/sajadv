package br.com.softplan.sajadv.service.imp;

import br.com.softplan.sajadv.entity.Pessoa;
import br.com.softplan.sajadv.exception.ApiValidationException;
import br.com.softplan.sajadv.exception.PessoaExcepion;
import br.com.softplan.sajadv.repository.PessoaRepository;
import br.com.softplan.sajadv.service.IPessoaService;
import br.com.softplan.sajadv.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaServiceImp implements IPessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public Pessoa save(Pessoa pessoa) throws ApiValidationException {
        if (pessoa != null) {
            ValidatorService.validarAtributosEntidade(pessoa);
            return this.pessoaRepository.save(pessoa);
        }
        throw new PessoaExcepion("Informações devem ser preenchidas!");
    }

    @Override
    public Pessoa update(Pessoa pessoa) {
        return null;
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
