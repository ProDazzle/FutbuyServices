package com.fgc.futbuy.dao;

/**
 * 
 */

import java.sql.Connection;
import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Jugador;

public interface JugadorDAO {
	
	public Jugador findById(Connection connection, Integer id) 
			throws InstanceNotFoundException, DataException;
	
	public Boolean exists(Connection connection, Integer id) 
    		throws DataException;

	public List<Jugador> findByProducto(Connection connection, Integer idProducto) 
			throws DataException;
	
    public List<Jugador> findAll(Connection connection) 
        	throws DataException;
}