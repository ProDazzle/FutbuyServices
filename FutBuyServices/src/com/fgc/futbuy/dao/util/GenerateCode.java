package com.fgc.futbuy.dao.util;

public class GenerateCode {

	public GenerateCode() {
		
	}
	
	public static int GenerarNumero () {
		return (int) Math.floor(Math.random()*(100000-999999+1)+999999);
	}
	
}
