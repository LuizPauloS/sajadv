package br.com.portfolio.lsilva.sajadv.service;

import br.com.portfolio.lsilva.sajadv.entity.Pessoa;
import br.com.portfolio.lsilva.sajadv.exception.ApiValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IBaseService<T> {

    Page<T> findAll(Pageable pageable);

    Optional<T> findById(Integer id);

    void delete(Integer id);

    T save(T t) throws ApiValidationException;

    T update(Integer id, Pessoa pessoa) throws ApiValidationException;
}
