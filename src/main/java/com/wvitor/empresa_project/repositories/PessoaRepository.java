package com.wvitor.empresa_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wvitor.empresa_project.domain.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{
	
	@Query("SELECT pessoa FROM Pessoa pessoa WHERE pessoa.cpf =:cpf")
	Pessoa findByCPF(@Param("cpf") String cpf);

}
