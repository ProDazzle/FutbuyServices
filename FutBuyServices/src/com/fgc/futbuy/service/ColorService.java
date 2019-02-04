
package com.fgc.futbuy.service;

import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Color;


public interface ColorService {
	
	public Color findById(Integer id) 
			throws InstanceNotFoundException, DataException;

	public Boolean exists(Integer id) 
			throws DataException;

	public List<Color> findByProducto(Integer id, String idioma) 
			throws DataException;
	
    public List<Color> findAll(String idioma) 
	    	throws DataException; 

}