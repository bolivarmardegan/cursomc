package com.nelioalves.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public PagamentoComBoleto preencherPagamentoComBoleto(PagamentoComBoleto boleto, Date instante) {
		Calendar dataVencimento = Calendar.getInstance();
		dataVencimento.setTime(instante);
		dataVencimento.add(Calendar.DAY_OF_MONTH, 7);
		boleto.setDataVencimento(dataVencimento.getTime());
		return boleto;
	}
	
	

}
