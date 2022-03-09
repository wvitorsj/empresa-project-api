package com.wvitor.empresa_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wvitor.empresa_project.domain.Tecnico;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{
	
	@Query("SELECT tecnico FROM Tecnico tecnico WHERE tecnico.cpf =:cpf")
	Tecnico findByCPF(@Param("cpf") String cpf);

}
