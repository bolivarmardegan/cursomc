package com.nelioalves.cursomc.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nelioalves.cursomc.domain.ItemPedido;
import com.nelioalves.cursomc.domain.PagamentoComBoleto;
import com.nelioalves.cursomc.domain.Pedido;
import com.nelioalves.cursomc.domain.Produto;
import com.nelioalves.cursomc.domain.enums.EstadoPagamento;
import com.nelioalves.cursomc.repositories.ItemPedidoRepository;
import com.nelioalves.cursomc.repositories.PagamentoRepository;
import com.nelioalves.cursomc.repositories.PedidoRepository;
import com.nelioalves.cursomc.repositories.ProdutoRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public List<Pedido> buscarPedidos(){
		return this.pedidoRepository.findAll();
	}
	
	public Pedido buscarPorId(Integer id) {
		Optional<Pedido> pedido = this.pedidoRepository.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException
				("Objeto não encontrado!" +id + "Tipo: "+ Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(@Valid Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		if(pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto boleto = (PagamentoComBoleto) pedido.getPagamento();
			this.boletoService.preencherPagamentoComBoleto(boleto, pedido.getInstante());
		}
		pedido = this.pedidoRepository.save(pedido);
		this.pagamentoRepository.save(pedido.getPagamento());
		
		for (ItemPedido itemPedido : pedido.getItens()) {
			itemPedido.setDesconto(0.0);
			itemPedido.setPreco(this.produtoRepository.findById(itemPedido.getProduto().getId()).get().getPreco());
			itemPedido.setPedido(pedido);
		}
		this.itemPedidoRepository.saveAll(pedido.getItens());
		return pedido;
	}

}
