package br.com.softplan.sajadv.service;

import br.com.softplan.sajadv.entity.Pessoa;

import java.util.Optional;

public interface IPessoaService extends IBaseService<Pessoa> {

    Optional<Pessoa> findByNomeFoto(String fileName);

    Optional<Pessoa> findByUrlFoto(String urlFile);
}
