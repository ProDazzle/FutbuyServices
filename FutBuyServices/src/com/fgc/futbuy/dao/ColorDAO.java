package com.fgc.futbuy.dao;

import java.sql.Connection;
import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Color;

public interface ColorDAO {
	
	public Color findById(Connection connection, Integer id) 
			throws InstanceNotFoundException, DataException;
	
	public Boolean exists(Connection connection, Integer id) 
    		throws DataException;

	public List<Color> findByProducto(Connection connection, Integer idProducto, String Idioma) 
			throws DataException;
	
    public List<Color> findAll(Connection connection, String Idioma) 
        	throws DataException;
}