package com.fgc.futbuy.service;

import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.model.Pais;
import com.fgc.futbuy.service.impl.PaisServicesImpl;
import com.fgc.futbuy.service.PaisService;

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
			
	}
	
}
