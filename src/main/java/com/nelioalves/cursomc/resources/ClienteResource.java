package com.nelioalves.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Cliente cliente = clienteService.findById(id);
		return ResponseEntity.ok().body(cliente);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> clientes = clienteService.findAll();
		List<ClienteDTO> clientesDTO = clientes.stream().map(
				cliente -> new ClienteDTO(cliente)).collect(Collectors.toList());
		return ResponseEntity.ok().body(clientesDTO);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO clienteNewDTO){
		Cliente cliente = this.clienteService.fromDTO(clienteNewDTO);
		cliente = this.clienteService.insert(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Cliente> update(@Valid @RequestBody ClienteDTO categoriaDTO, @PathVariable Integer id){
		Cliente cliente = this.clienteService.fromDTO(categoriaDTO);
		cliente.setId(id);
		this.clienteService.update(cliente);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		this.clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24")  Integer linesPerPage, 
			@RequestParam(value="direction", defaultValue="ASC")  String direction,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy) {
			Page<Cliente> listaDePAginas = this.clienteService.findPage(page, linesPerPage, direction, orderBy);
			Page<ClienteDTO> categoriasDTO = listaDePAginas.map(
					categoria -> new ClienteDTO(categoria));
			return ResponseEntity.ok().body(categoriasDTO);
	}

}
