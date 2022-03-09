package com.wvitor.empresa_project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wvitor.empresa_project.domain.OS;

@Repository
public interface OSRepository extends JpaRepository<OS, Integer>{

}
