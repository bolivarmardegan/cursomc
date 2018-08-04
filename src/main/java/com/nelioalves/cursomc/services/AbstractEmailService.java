package com.nelioalves.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.nelioalves.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage messsage = prepareSimpleMailMessageFromPedido(pedido);
		sendEmial(messsage);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(pedido.getCliente().getEmail());
		email.setFrom(sender);
		email.setSubject("Pedido Confirmado! Código: " + pedido.getId());
		email.setSentDate(new Date(System.currentTimeMillis()));
		email.setText(pedido.toString());
		return email;
	}

	protected String htmlFromTemplatePedido(Pedido pedido) {
		Context context = new Context();
		context.setVariable("pedido", pedido);
		return templateEngine.process("email/confirmacaoPedido", context);
	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
		try {
			MimeMessage messsage;
			messsage = prepareMimeMessageFromPedido(pedido);
			sendHtmlEmail(messsage);
		} catch (MessagingException e) {
			this.sendOrderConfirmationEmail(pedido);
		}

	}

	private MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
		MimeMessage message = this.javaMailSender.createMimeMessage();

		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
		mimeMessageHelper.setTo(pedido.getCliente().getEmail());
		mimeMessageHelper.setFrom(this.sender);
		mimeMessageHelper.setSubject("Pedido Confirmado! Código: " + pedido.getId());
		mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
		mimeMessageHelper.setText(this.htmlFromTemplatePedido(pedido), true);
		return message;
	}

}
