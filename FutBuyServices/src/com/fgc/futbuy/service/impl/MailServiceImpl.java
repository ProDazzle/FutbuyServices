package com.fgc.futbuy.service.impl;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

import com.fgc.futbuy.exceptions.MailException;
import com.fgc.futbuy.service.MailService;

public class MailServiceImpl implements MailService{
	
	@Override
	public void sendMail(String mensajeHTMl, String subject, String... to) throws MailException{

		
		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("futbuyweb", PASSWORD));
			email.setSSLOnConnect(true);
			email.setFrom("futbuyweb@gmail.com");
			email.setSubject(subject);
			email.setHtmlMsg(mensajeHTMl);
			email.addTo(to);
			email.send();
			
		} catch (Exception e) {
			throw new MailException ("Trying to send email "
					+ " from futbuy "
					+ " to " + to, e);
		}
	}
	

	
	
	
	
	
	public static final String PASSWORD ="futbuy123abc";
}
