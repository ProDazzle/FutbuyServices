package com.fgc.futbuy.service;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.model.Pais;
import com.fgc.futbuy.service.impl.PaisServicesImpl;

public class PaisServicesTest {

	public static List<Pais> findByIdioma (String idIdioma) throws DataException{
		PaisService paisServices = new PaisServicesImpl();
		
		return paisServices.findByIdioma(idIdioma);
	}
	
	public static void main(String[] args) throws DataException {

		List<Pais> paises =	findByIdioma("EN");
		
		for(Pais p: paises) {
			System.out.println("" + p);
		}
		
		Properties systemProperties = System.getProperties();
		String key = null;
		for (Enumeration keys = systemProperties.keys(); keys.hasMoreElements();) {
			key = (String) keys.nextElement();
			System.out.println(key+"="+System.getProperty(key));
		}
			
	}
	
}
