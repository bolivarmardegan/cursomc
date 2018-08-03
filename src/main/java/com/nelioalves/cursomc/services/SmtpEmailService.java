package com.nelioalves.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService{
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Autowired
	private MailSender maislSender;
	
	@Override
	public void sendEmial(SimpleMailMessage simpleEmail) {
		LOG.info("Simulando envio de email...");
		maislSender.send(simpleEmail);
		LOG.info("email Enviado...");
		
	}

}
