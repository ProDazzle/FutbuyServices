package com.fgc.futbuy.service;

import com.fgc.futbuy.exceptions.MailException;

public interface MailService {

	public  void sendMail(String mensajeHTMl, String subject, String... to) throws MailException;


}
