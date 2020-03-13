package br.com.softplan.sajadv.service;

import br.com.softplan.sajadv.entity.Pessoa;
import br.com.softplan.sajadv.exception.ApiValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBaseService<T> {

    Page<T> findAll(Pageable pageable);

    Optional<T> findById(Integer id);

    void delete(Integer id);

    T save(T t) throws ApiValidationException;

    T update(Integer id, Pessoa pessoa) throws ApiValidationException;
}
