package com.nelioalves.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.services.exceptions.DataIntegretyException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<Cliente> buscarCategorias(){
		return this.clienteRepository.findAll();
	}
	
	public Cliente findById(Integer id) {
		Optional<Cliente> cliente = this.clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException
				("Objeto não encontrado!" +id + "Tipo: "+ Cliente.class.getName()));
	}
	
	public List<Cliente> findAll(){
		return this.clienteRepository.findAll();
	}
	
	public Cliente insert(Cliente categoria) {
		categoria.setId(null);
		return this.clienteRepository.save(categoria);
	}

	public Cliente update(Cliente clienteOld) {
		Cliente cliente = this.findById(clienteOld.getId());
		update(cliente, clienteOld);
		return this.clienteRepository.save(cliente);
		
	}

	private void update(Cliente cliente, Cliente clienteOld) {
		cliente.setNome(clienteOld.getNome());
		cliente.setEmail(clienteOld.getEmail());
		
	}

	public void delete(Integer id) {
		try {
			this.clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegretyException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	

	public Page<Cliente> findPage(Integer page, Integer linesPage, String direction, String orderBy){
		PageRequest paginator = PageRequest.of(page, linesPage, Direction.valueOf(direction), orderBy);
		return this.clienteRepository.findAll(paginator);
	}
	
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(),clienteDTO.getNome(),clienteDTO.getEmail(), null, null);
	}

}
