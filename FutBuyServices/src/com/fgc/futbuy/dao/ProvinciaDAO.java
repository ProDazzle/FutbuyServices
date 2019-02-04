package com.fgc.futbuy.dao;

import java.sql.Connection;
import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.model.Provincia;

public interface ProvinciaDAO {
	
	public List<Provincia> findByPaisIdioma (Connection connection, Integer idPais, String idIdioma)
			throws DataException;
	

}



	


