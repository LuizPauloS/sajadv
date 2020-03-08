package br.com.softplan.sajadv.repository;

import br.com.softplan.sajadv.entity.Pessoa;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends PagingAndSortingRepository<Pessoa, Integer> {

}
