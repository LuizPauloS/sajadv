package br.com.softplan.sajadv.service;

import br.com.softplan.sajadv.exception.ApiValidationException;

import java.util.List;

public interface IBaseService<T> {

    T save(T t) throws ApiValidationException;

    T update(T t);

    List<T> findAll();

    T findById(Integer id);
}
