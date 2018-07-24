package com.nelioalves.cursomc;

import static org.hamcrest.CoreMatchers.describedAs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Endereco;
import com.nelioalves.cursomc.domain.Estado;
import com.nelioalves.cursomc.domain.ItemPedido;
import com.nelioalves.cursomc.domain.Pagamento;
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
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ArrayList<Categoria> categorias = new ArrayList<>();
		Categoria informatica = new Categoria(null, "Informática");
		Categoria escritorio = new Categoria(null,"Escritório");
		categorias.addAll(Arrays.asList(informatica,escritorio));
		for(int i=0; i<999;i++) {
			Categoria categoria = new Categoria(null,"Categoria: "+i);
			categorias.add(categoria);
		}
		
		Produto p1 = new Produto(null, "Cmputador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		informatica.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		escritorio.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(informatica));
		p2.getCategorias().addAll(Arrays.asList(informatica,escritorio));
		p3.getCategorias().addAll(Arrays.asList(informatica));
		
		this.categoriaRepository.saveAll(categorias);
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
		
		Cliente maria = new Cliente(null, "Maria Antonieta", "maria@gmail.com", "311.555.999-90", TipoCliente.PESSOAFISICA);
		maria.getTelefones().addAll(Arrays.asList("11-9898-6565","11-9995-8956"));
		Endereco endMaria = new Endereco(null, "Rua Flores", "300", "Apartamento 303", "Jardim", "03663015", maria, uberlandia);
		Endereco endMaria2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "036777777", maria, spcity);
		maria.getEnderecos().addAll(Arrays.asList(endMaria, endMaria2));
		
		this.clienteRepository.saveAll(Arrays.asList(maria));
		this.enderecoRepository.saveAll(Arrays.asList(endMaria,endMaria2));
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, format.parse("30/09/2017 19:32"),  maria, endMaria);
		Pedido ped2 = new Pedido(null, format.parse("10/10/2017 19:32"),  maria, endMaria2);
		
		PagamentoComCartao pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgto1);
		
		PagamentoComBoleto pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, 
				format.parse("20/10/2017 19:32"), format.parse("15/10/2017 19:32"));
		ped2.setPagamento(pgto2);
		
		maria.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
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


















