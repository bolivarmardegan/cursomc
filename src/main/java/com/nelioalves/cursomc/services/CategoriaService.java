package com.nelioalves.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
import com.nelioalves.cursomc.services.exceptions.DataIntegretyException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<Categoria> findAll(){
		return this.categoriaRepository.findAll();
	}
	
	public Categoria findById(Integer id) {
		Optional<Categoria> categoria = this.categoriaRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException
				("Objeto não encontrado!" +id + "Tipo: "+ Categoria.class.getName()));
	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return this.categoriaRepository.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		this.findById(categoria.getId());
		return this.categoriaRepository.save(categoria);
		
	}

	public void delete(Integer id) {
		this.findById(id);
		try {
			this.categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegretyException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return this.categoriaRepository.findAll(pageRequest);
	}


}


















