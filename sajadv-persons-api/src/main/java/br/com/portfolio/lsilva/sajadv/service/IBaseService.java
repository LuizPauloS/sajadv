package br.com.portfolio.lsilva.sajadv.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IBaseService<T> {

    T save(T t);

    T update(Integer id, T t);

    void delete(Integer id);

    Page<T> findAll(Pageable pageable);

    T findById(Integer id);
}
