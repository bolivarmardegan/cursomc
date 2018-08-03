package com.nelioalves.cursomc.services;


import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmial(SimpleMailMessage simpleEmail) {
		LOG.info("Simulando envio de email...");
		LOG.info(simpleEmail.toString());
		LOG.info("email Enviado...");
		
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Simulando envio de email HTM...");
		LOG.info(msg.toString());
		LOG.info("email HTML Enviado...");
	}

}
