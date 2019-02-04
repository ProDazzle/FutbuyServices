
package com.fgc.futbuy.service;

import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Marca;

public interface MarcaService {
	
	public Marca findById(Integer id) 
			throws InstanceNotFoundException, DataException;

	public Boolean exists(Integer id) 
			throws DataException;
	
    public List<Marca> findAll() 
	    	throws DataException; 

}