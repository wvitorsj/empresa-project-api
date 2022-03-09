package com.wvitor.empresa_project.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wvitor.empresa_project.domain.Pessoa;
import com.wvitor.empresa_project.domain.Tecnico;
import com.wvitor.empresa_project.dtos.TecnicoDTO;
import com.wvitor.empresa_project.repositories.PessoaRepository;
import com.wvitor.empresa_project.repositories.TecnicoRepository;
import com.wvitor.empresa_project.services.exceptions.DataIntegratyViolationException;
import com.wvitor.empresa_project.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	/*
	 * BUSCA TODOS OS TÉCNICOS NA BASE DE DADOS
	 */
	public Tecnico findById(Integer id) {
		Optional<Tecnico> tecnico = tecnicoRepository.findById(id);
		return tecnico.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não Encontrado! ID: " + id + ", Tipo: " + Tecnico.class.getName()));
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}
	
	/*
	 * CRIA UM TÉCNICO
	 */
	public Tecnico create(TecnicoDTO tecnicoDTO) {
		if (findByCPF(tecnicoDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de Dados!");
		}
		return tecnicoRepository
				.save(new Tecnico(null, tecnicoDTO.getNome(), tecnicoDTO.getCpf(), tecnicoDTO.getTelefone()));
	}
	
	/*
	 * ATUALIZA UM TÉCNICO
	 */
	public Tecnico update(Integer id, @Valid TecnicoDTO tecnicoDTO) {
		Tecnico oldTecnico = findById(id);

		if (findByCPF(tecnicoDTO) != null && findByCPF(tecnicoDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de Dados!");
		}

		oldTecnico.setNome(tecnicoDTO.getNome());
		oldTecnico.setCpf(tecnicoDTO.getCpf());
		oldTecnico.setTelefone(tecnicoDTO.getTelefone());
		return tecnicoRepository.save(oldTecnico);
	}
	
	/*
	 * DELETA UM TÉCNICO PELO ID
	 */
	public void delete(Integer id) {
		Tecnico tecnico = findById(id);
		if(tecnico.getList().size() > 0) {
			throw new DataIntegratyViolationException("Técnico possui Ordens de Serviço, não pode ser Deletado!");
		}
		tecnicoRepository.deleteById(id);
	}
	
	/*
	 * BUSCA TÉCNICO PELO CPF
	 */
	private Pessoa findByCPF(TecnicoDTO tecnicoDTO) {
		Pessoa tecnico = pessoaRepository.findByCPF(tecnicoDTO.getCpf());
		if (tecnico != null) {
			return tecnico;
		}
		return null;
	}

}
