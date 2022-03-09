package com.wvitor.empresa_project.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wvitor.empresa_project.domain.Cliente;
import com.wvitor.empresa_project.domain.OS;
import com.wvitor.empresa_project.domain.Tecnico;
import com.wvitor.empresa_project.domain.enums.Prioridade;
import com.wvitor.empresa_project.domain.enums.Status;
import com.wvitor.empresa_project.repositories.ClienteRepository;
import com.wvitor.empresa_project.repositories.OSRepository;
import com.wvitor.empresa_project.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private OSRepository osRepository;

	
	public void instaciaDB() {

		Tecnico t1 = new Tecnico(null, "Welton Vitor", "458.522.390-89", "(11) 98888-9999");
		Tecnico t2 = new Tecnico(null, "Rodrigo Gael", "768.911.760-00", "(11) 94444-2222");
		Tecnico t3 = new Tecnico(null, "Bernardo Ferreira", "824.885.730-19", "(11) 93636-0507");
		Cliente c1 = new Cliente(null, "Janaina Frazão", "721.897.390-63", "(11) 97777-5555");

		OS os1 = new OS(null, Prioridade.ALTA, "Substituir Fonte do Notebook", Status.ANDAMENTO, t2, c1);

		tecnicoRepository.saveAll(Arrays.asList(t1,t2,t3));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));

	}

}
