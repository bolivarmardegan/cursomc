package com.nelioalves.cursomc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Produto;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
import com.nelioalves.cursomc.repositories.ProdutoRepository;
import com.nelioalves.cursomc.services.exceptions.DataIntegretyException;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<Produto> findAll(){
		return this.produtoRepository.findAll();
	}
	
	public Page<Produto> search(String nome, List<Integer> categoriasId,Integer page, Integer linesPage, String direction, String orderBy){
		PageRequest paginator = PageRequest.of(page, linesPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = this.categoriaRepository.findAllById(categoriasId);
		return this.produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, paginator);
	}
	
	public Produto findById(Integer id) {
		Optional<Produto> produto = this.produtoRepository.findById(id);
		return produto.orElseThrow(() -> new ObjectNotFoundException
				("Objeto não encontrado!" +id + "Tipo: "+ Produto.class.getName()));
	}

	public Produto insert(Produto produto) {
		produto.setId(null);
		return this.produtoRepository.save(produto);
	}

	public Produto update(Produto produtoOld) {
		Produto produto = this.findById(produtoOld.getId());
		this.update(produto, produtoOld);
		return this.produtoRepository.save(produto);
	}
	
	private void update(Produto cliente, Produto produtoOld) {
		cliente.setNome(produtoOld.getNome());
		
	}

	public void delete(Integer id) {
		this.findById(id);
		try {
			this.produtoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegretyException("Não é possível excluir uma produto que possui produtos");
		}
	}
	

	public Page<Produto> findPage(Integer page, Integer linesPage, String direction, String orderBy){
		PageRequest paginator = PageRequest.of(page, linesPage, Direction.valueOf(direction), orderBy);
		return this.produtoRepository.findAll(paginator);
	}
	
//	public Produto fromDTO(ProdutoDTO produtoDTO) {
//		return new Produto(produtoDTO.getId(), produtoDTO.getNome());
//	}

}


















