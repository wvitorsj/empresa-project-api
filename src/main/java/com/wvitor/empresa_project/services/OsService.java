package com.wvitor.empresa_project.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wvitor.empresa_project.domain.Cliente;
import com.wvitor.empresa_project.domain.OS;
import com.wvitor.empresa_project.domain.Tecnico;
import com.wvitor.empresa_project.domain.enums.Prioridade;
import com.wvitor.empresa_project.domain.enums.Status;
import com.wvitor.empresa_project.dtos.OSDTO;
import com.wvitor.empresa_project.repositories.OSRepository;
import com.wvitor.empresa_project.services.exceptions.ObjectNotFoundException;

@Service
public class OsService {

	@Autowired
	private OSRepository osRepository;

	@Autowired
	private TecnicoService tecnicoService;

	@Autowired
	private ClienteService clienteService;

	public OS findById(Integer id) {
		Optional<OS> os = osRepository.findById(id);
		return os.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não Encontrado! ID: " + id + ", Tipo: " + OS.class.getName()));
	}

	public List<OS> findAll() {
		return osRepository.findAll();
	}

	public OS create(@Valid OSDTO osDTO) {
		return fromDTO(osDTO);
	}

	public OS upadate(@Valid OSDTO osDTO) {
		findById(osDTO.getId());
		return fromDTO(osDTO);
	}

	private OS fromDTO(OSDTO osDTO) {
		OS newOS = new OS();
		newOS.setId(osDTO.getId());
		newOS.setObservacoes(osDTO.getObservacoes());
		newOS.setPrioridade(Prioridade.toEnum(osDTO.getPrioridade()));
		newOS.setStatus(Status.toEnum(osDTO.getStatus()));

		Tecnico tec = tecnicoService.findById(osDTO.getTecnico());
		Cliente cli = clienteService.findById(osDTO.getCliente());

		newOS.setTecnico(tec);
		newOS.setCliente(cli);
		
		if(newOS.getStatus().getCod().equals(2)) {
			newOS.setDataFechamento(LocalDateTime.now());
		}

		return osRepository.save(newOS);
	}
}





