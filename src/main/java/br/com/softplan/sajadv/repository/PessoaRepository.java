package br.com.softplan.sajadv.repository;

import br.com.softplan.sajadv.entity.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends PagingAndSortingRepository<Pessoa, Integer> {

    @Override
    @Query(value = "SELECT p FROM Pessoa p WHERE p.ativo = true")
    Page<Pessoa> findAll(Pageable pageable);

    Optional<Pessoa> findByNomeFoto(String fileName);

    Optional<Pessoa> findByUrlFoto(String urlFile);
}
