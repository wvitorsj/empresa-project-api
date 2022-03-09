package com.wvitor.empresa_project.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wvitor.empresa_project.domain.Pessoa;
import com.wvitor.empresa_project.domain.Cliente;
import com.wvitor.empresa_project.dtos.ClienteDTO;
import com.wvitor.empresa_project.repositories.PessoaRepository;
import com.wvitor.empresa_project.repositories.ClienteRepository;
import com.wvitor.empresa_project.services.exceptions.DataIntegratyViolationException;
import com.wvitor.empresa_project.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	/*
	 * BUSCA TODOS OS CLIENTES NA BASE DE DADOS
	 */
	public Cliente findById(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto Não Encontrado! ID: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	/*
	 * CRIA UM CLIENTE
	 */
	public Cliente create(ClienteDTO clienteDTO) {
		if (findByCPF(clienteDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de Dados!");
		}
		return clienteRepository
				.save(new Cliente(null, clienteDTO.getNome(), clienteDTO.getCpf(), clienteDTO.getTelefone()));
	}
	
	/*
	 * ATUALIZA UM CLIENTE
	 */
	public Cliente update(Integer id, @Valid ClienteDTO clienteDTO) {
		Cliente oldCliente = findById(id);

		if (findByCPF(clienteDTO) != null && findByCPF(clienteDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de Dados!");
		}

		oldCliente.setNome(clienteDTO.getNome());
		oldCliente.setCpf(clienteDTO.getCpf());
		oldCliente.setTelefone(clienteDTO.getTelefone());
		return clienteRepository.save(oldCliente);
	}
	
	/*
	 * DELETA UM CLIENTE PELO ID
	 */
	public void delete(Integer id) {
		Cliente cliente = findById(id);
		if(cliente.getList().size() > 0) {
			throw new DataIntegratyViolationException("Cliente possui Ordens de Serviço, não pode ser Deletado!");
		}
		clienteRepository.deleteById(id);
	}
	
	/*
	 * BUSCA CLIENTE PELO CPF
	 */
	private Pessoa findByCPF(ClienteDTO clienteDTO) {
		Pessoa cliente = pessoaRepository.findByCPF(clienteDTO.getCpf());
		if (cliente != null) {
			return cliente;
		}
		return null;
	}

}
