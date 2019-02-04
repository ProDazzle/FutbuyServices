package com.fgc.futbuy.dao;

import java.sql.Connection;
import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.model.Pais;

public interface PaisDAO {
	
	public List<Pais> findAll (Connection connection, String idIdioma) 
			throws DataException;
}
