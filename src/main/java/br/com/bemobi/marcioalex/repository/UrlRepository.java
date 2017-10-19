package br.com.bemobi.marcioalex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bemobi.marcioalex.modelo.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Integer>{

	Url findByAlias(String alias);
	
}
