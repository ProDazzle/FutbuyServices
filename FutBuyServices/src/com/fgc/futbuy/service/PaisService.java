package com.fgc.futbuy.service;

import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.model.Pais;

public interface PaisService {

	public List<Pais> findByIdioma (String idIdioma) throws DataException;
	
}
