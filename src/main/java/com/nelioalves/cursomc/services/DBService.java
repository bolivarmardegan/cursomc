package com.nelioalves.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Endereco;
import com.nelioalves.cursomc.domain.Estado;
import com.nelioalves.cursomc.domain.ItemPedido;
import com.nelioalves.cursomc.domain.PagamentoComBoleto;
import com.nelioalves.cursomc.domain.PagamentoComCartao;
import com.nelioalves.cursomc.domain.Pedido;
import com.nelioalves.cursomc.domain.Produto;
import com.nelioalves.cursomc.domain.enums.EstadoPagamento;
import com.nelioalves.cursomc.domain.enums.TipoCliente;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
import com.nelioalves.cursomc.repositories.CidadeRepository;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.repositories.EnderecoRepository;
import com.nelioalves.cursomc.repositories.EstadoRepository;
import com.nelioalves.cursomc.repositories.ItemPedidoRepository;
import com.nelioalves.cursomc.repositories.PagamentoRepository;
import com.nelioalves.cursomc.repositories.PedidoRepository;
import com.nelioalves.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	
	public void instanteateTestDataBase() throws ParseException {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		Categoria cat3 = new Categoria(null,"Cozinha");
		Categoria cat4 = new Categoria(null,"Banheiro");
		Categoria cat5 = new Categoria(null,"Quarto");
		Categoria cat6 = new Categoria(null,"Garagem");
		Categoria cat7 = new Categoria(null,"Jardim");
						
		Produto p1 = new Produto(null, "Computador", 2000.00); 
 		Produto p2 = new Produto(null, "Impressora", 800.00); 
 		Produto p3 = new Produto(null, "Mouse", 80.00); 
		Produto p4 = new Produto(null, "Mesa de escritório", 300.00); 
		Produto p5 = new Produto(null, "Toalha", 50.00); 
		Produto p6 = new Produto(null, "Colcha", 200.00); 
		Produto p7 = new Produto(null, "TV true color", 1200.00); 
		Produto p8 = new Produto(null, "Roçadeira", 800.00); 
		Produto p9 = new Produto(null, "Abajour", 100.00); 
		Produto p10 = new Produto(null, "Pendente", 180.00); 
		Produto p11 = new Produto(null, "Shampoo", 90.00); 

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3)); 
		cat2.getProdutos().addAll(Arrays.asList(p2)); 

		p1.getCategorias().addAll(Arrays.asList(cat1)); 
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2)); 
		p3.getCategorias().addAll(Arrays.asList(cat1)); 

		cat2.getProdutos().addAll(Arrays.asList(p2, p4)); 
		cat3.getProdutos().addAll(Arrays.asList(p5, p6)); 
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7)); 
		cat5.getProdutos().addAll(Arrays.asList(p8)); 
		cat6.getProdutos().addAll(Arrays.asList(p9, p10)); 
		cat7.getProdutos().addAll(Arrays.asList(p11)); 

		p1.getCategorias().addAll(Arrays.asList(cat1, cat4)); 
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4)); 
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4)); 
		p4.getCategorias().addAll(Arrays.asList(cat2)); 
		p5.getCategorias().addAll(Arrays.asList(cat3)); 
		p6.getCategorias().addAll(Arrays.asList(cat3)); 
		p7.getCategorias().addAll(Arrays.asList(cat4)); 
		p8.getCategorias().addAll(Arrays.asList(cat5)); 
		p9.getCategorias().addAll(Arrays.asList(cat6)); 
		p10.getCategorias().addAll(Arrays.asList(cat6)); 
		p11.getCategorias().addAll(Arrays.asList(cat7));		 

		this.categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7)); 
		this.produtoRepository.saveAll(Arrays.asList(p1, p2, p3)); 
		this.produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11)); 
		
		Estado minasGerais = new Estado(null,"Minas Gerais");
		Estado saoPaulo = new Estado(null, "São Paulo");
		
		Cidade uberlandia = new Cidade(null, "Uberlândia", minasGerais);
		Cidade spcity = new Cidade(null, "São Paulo", saoPaulo);
		Cidade campinas = new Cidade(null, "Campinas", saoPaulo);
		
		saoPaulo.getCidades().addAll(Arrays.asList(spcity,campinas));
		minasGerais.getCidades().addAll(Arrays.asList(uberlandia));
		
		this.estadoRepository.saveAll(Arrays.asList(minasGerais, saoPaulo));
		this.cidadeRepository.saveAll(Arrays.asList(uberlandia,spcity,campinas));
		
		Cliente bolivarMardegan = new Cliente(null, "Maria Antonieta", "bolivar.android@gmail.com", 
				"311.555.999-90", TipoCliente.PESSOAFISICA, pe.encode("123"));
		bolivarMardegan.getTelefones().addAll(Arrays.asList("11-9898-6565","11-9995-8956"));
		Endereco endMaria = new Endereco(null, "Rua Flores", "300", "Apartamento 303", "Jardim", "03663015", bolivarMardegan, uberlandia);
		Endereco endMaria2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "036777777", bolivarMardegan, spcity);
		bolivarMardegan.getEnderecos().addAll(Arrays.asList(endMaria, endMaria2));
		
		this.clienteRepository.saveAll(Arrays.asList(bolivarMardegan));
		this.enderecoRepository.saveAll(Arrays.asList(endMaria,endMaria2));
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, format.parse("30/09/2017 19:32"),  bolivarMardegan, endMaria);
		Pedido ped2 = new Pedido(null, format.parse("10/10/2017 19:32"),  bolivarMardegan, endMaria2);
		
		PagamentoComCartao pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgto1);
		
		PagamentoComBoleto pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, 
				format.parse("20/10/2017 19:32"), format.parse("15/10/2017 19:32"));
		ped2.setPagamento(pgto2);
		
		bolivarMardegan.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		this.pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		this.pagamentoRepository.saveAll(Arrays.asList(pgto1,pgto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 800.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 80.00);
		
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		this.itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
	}

}
