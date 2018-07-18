package com.nelioalves.cursomc.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<Cliente> buscarCategorias(){
		return this.clienteRepository.findAll();
	}
	
	public Cliente buscarPorId(Integer id) {
		Optional<Cliente> cliente = this.clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException
				("Objeto não encontrado!" +id + "Tipo: "+ Cliente.class.getName()));
	}

}
