package com.wvitor.empresa_project.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wvitor.empresa_project.domain.Cliente;
import com.wvitor.empresa_project.dtos.ClienteDTO;
import com.wvitor.empresa_project.services.ClienteService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;
	
	/*
	 * BUSCA CLIENTE PELO ID
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
		ClienteDTO clienteDTO = new ClienteDTO(clienteService.findById(id));
		return ResponseEntity.ok().body(clienteDTO);
	}
	
	/*
	 * LISTA TODOS OS CLIENTES NA BASE DE DADOS
	 */
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {

		List<ClienteDTO> listDTO = clienteService.findAll().stream().map(cliente -> new ClienteDTO(cliente))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(listDTO);
	}
	
	/*
	 * CRIA UM NOVO CLIENTE
	 */
	@PostMapping
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO clienteDTO){
		Cliente newCliente = clienteService.create(clienteDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(newCliente.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	/*
	 * ATUALIZA UM CLIENTE
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO clienteDTO){
		ClienteDTO newCliente = new ClienteDTO(clienteService.update(id, clienteDTO));
		return ResponseEntity.ok().body(newCliente);
	}
	
	/*
	 * DELETA UM CLIENTE
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}

}








