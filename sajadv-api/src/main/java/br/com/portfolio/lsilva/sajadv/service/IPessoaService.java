package br.com.portfolio.lsilva.sajadv.service;

import br.com.portfolio.lsilva.sajadv.entity.Pessoa;

import java.util.Optional;

public interface IPessoaService extends IBaseService<Pessoa> {

    Optional<Pessoa> findByNomeFoto(String fileName);

    Optional<Pessoa> findByUrlFoto(String urlFile);
}
