package com.nelioalves.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.nelioalves.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{
	
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage messsage = prepareSimpleMailMessageFromPedido(pedido);
		sendEmial(messsage);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(pedido.getCliente().getEmail());
		email.setFrom(sender);
		email.setSubject("Pedido Confirmado! Código: "+pedido.getId());
		email.setSentDate(new Date(System.currentTimeMillis()));
		email.setText(pedido.toString());
		return email;
	}

}
