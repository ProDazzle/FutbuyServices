
package com.fgc.futbuy.service;

import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Jugador;


public interface JugadorService {
	
	public Jugador findById(Integer id) 
			throws InstanceNotFoundException, DataException;

	public Boolean exists(Integer id) 
			throws DataException;

	public List<Jugador> findByProducto(Integer id, int startIndex, int count) 
			throws DataException;
	
    public List<Jugador> findAll(int startIndex, int count) 
	    	throws DataException; 

}