package com.fgc.futbuy.service;

import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.model.Provincia;

public interface ProvinciaServices {
	
	public List<Provincia> findByPaisIdioma (Integer idPais, String idIdioma)
			throws DataException;

}
