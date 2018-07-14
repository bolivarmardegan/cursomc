package com.nelioalves.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Estado;
import com.nelioalves.cursomc.domain.Produto;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
import com.nelioalves.cursomc.repositories.CidadeRepository;
import com.nelioalves.cursomc.repositories.EstadoRepository;
import com.nelioalves.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria informatica = new Categoria(null, "Informática");
		Categoria escritorio = new Categoria(null,"Escritório");
		
		Produto p1 = new Produto(null, "Cmputador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		informatica.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		escritorio.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(informatica));
		p2.getCategorias().addAll(Arrays.asList(informatica,escritorio));
		p3.getCategorias().addAll(Arrays.asList(informatica));
		
		this.categoriaRepository.saveAll(Arrays.asList(informatica, escritorio));
		this.produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado minasGerais = new Estado(null,"Minas Gerais");
		Estado saoPaulo = new Estado(null, "São Paulo");
		
		Cidade uberlandia = new Cidade(null, "Uberlândia", minasGerais);
		Cidade spcity = new Cidade(null, "São Paulo", saoPaulo);
		Cidade campinas = new Cidade(null, "Campinas", saoPaulo);
		
		saoPaulo.getCidades().addAll(Arrays.asList(spcity,campinas));
		minasGerais.getCidades().addAll(Arrays.asList(uberlandia));
		
		this.estadoRepository.saveAll(Arrays.asList(minasGerais, saoPaulo));
		this.cidadeRepository.saveAll(Arrays.asList(uberlandia,spcity,campinas));
		
	}
}


















