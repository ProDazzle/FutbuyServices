package com.fgc.futbuy.dao;

import java.sql.Connection;
import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Categoria;

public interface CategoriaDAO {
	
	public Categoria findById(Connection connection, Integer id, String idioma) 
			throws InstanceNotFoundException, DataException;
	
	public Boolean exists(Connection connection, Integer id) 
    		throws DataException;
	
    public List<Categoria> findAll(Connection connection, String Idioma) 
        	throws DataException;
}