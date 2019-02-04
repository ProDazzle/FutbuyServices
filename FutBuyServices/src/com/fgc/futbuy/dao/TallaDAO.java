package com.fgc.futbuy.dao;

import java.sql.Connection;
import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Talla;

public interface TallaDAO {
	
	public Talla findById(Connection connection, Integer id) 
			throws InstanceNotFoundException, DataException;
	
	public Boolean exists(Connection connection, Integer id) 
    		throws DataException;

	public List<Talla> findByProducto(Connection connection, Integer idProducto, String Idioma) 
			throws DataException;
	
    public List<Talla> findAll(Connection connection, String Idioma) 
        	throws DataException;
}