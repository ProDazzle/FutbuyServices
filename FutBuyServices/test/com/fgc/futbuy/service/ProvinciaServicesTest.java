package com.fgc.futbuy.service;

import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.model.Provincia;
import com.fgc.futbuy.service.impl.ProvinciaServiceImpl;

public class ProvinciaServicesTest {

	public static List<Provincia> findByIdiomaPais (Integer idPais, String idIdioma) throws DataException{
		ProvinciaServices provinciaServices = new ProvinciaServiceImpl();
		
		return provinciaServices.findByPaisIdioma(idPais, idIdioma);
	}
	
	public static void main(String[] args) throws DataException {

		List<Provincia> provincias = findByIdiomaPais(3, "es");
		
		for(Provincia p: provincias) {
			System.out.println("" + p);
		}
			
	}
	
}
