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
import com.nelioalves.cursomc.domain.Produto;
import com.nelioalves.cursomc.dto.ProdutoDTO;
import com.nelioalves.cursomc.resources.utils.URL;
import com.nelioalves.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ProdutoDTO>> findAll() {
		List<Produto> produtos = produtoService.findAll();
		List<ProdutoDTO> produtosDTO = produtos.stream().map(
				produto -> new ProdutoDTO(produto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(produtosDTO);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Produto produto = produtoService.findById(id);
		return ResponseEntity.ok().body(produto);
	}
	
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		this.produtoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24")  Integer linesPerPage, 
			@RequestParam(value="direction", defaultValue="ASC")  String direction,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy) {
			List<Integer> idsCategorias = URL.decodeIntList(categorias);
			String nomeDecode = URL.decodeParam(nome);
			Page<Produto> listaDePaginas = this.produtoService.search(nomeDecode, idsCategorias, page, linesPerPage, direction, orderBy);
			Page<ProdutoDTO> produtosDTO = listaDePaginas.map(
					produto -> new ProdutoDTO(produto));
			return ResponseEntity.ok().body(produtosDTO);
	}
}
