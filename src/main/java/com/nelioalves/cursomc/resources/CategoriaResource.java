package com.nelioalves.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nelioalves.cursomc.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Categoria> 	 listar() {
		List<Categoria> categorias = new ArrayList<>();
		Categoria informatica = new Categoria(1, "Informática");
		Categoria escritorio = new Categoria(2,"Escritório");
		categorias.add(escritorio);
		categorias.add(informatica);
		return categorias;
	}

}
