package com.nelioalves.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService{
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Autowired
	private MailSender maislSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendEmial(SimpleMailMessage simpleEmail) {
		LOG.info("Simulando envio de email...");
		this.maislSender.send(simpleEmail);
		LOG.info("email Enviado...");
		
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Simulando envio de email HTML...");
		this.javaMailSender.send(msg);
		LOG.info("email HTML Enviado...");
		
	}

}
