package com.fgc.exceptions;



import org.jasypt.util.password.StrongPasswordEncryptor;


public class PasswordEncryptionUtil {
	
	private static final StrongPasswordEncryptor PASSWORD_ENCRYPTOR = new StrongPasswordEncryptor();	
	
	public static final String encryptPassword(String password) {
		return PASSWORD_ENCRYPTOR.encryptPassword(password);
	}

	public static final boolean checkPassword(String plainPassword, String encryptedPassword) {
		if (PASSWORD_ENCRYPTOR.checkPassword(plainPassword, encryptedPassword)) {
			return true;

		} else {

			return false;
		}
	}


}
