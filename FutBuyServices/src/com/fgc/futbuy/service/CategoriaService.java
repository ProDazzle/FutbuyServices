
package com.fgc.futbuy.service;

import java.sql.SQLException;
import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Categoria;

public interface CategoriaService {
	
	public Categoria findById(Integer id, String idioma) 
			throws InstanceNotFoundException, DataException;

	public Boolean exists(Integer id) 
			throws DataException;
	
    public List<Categoria> findAll(String idioma) 
	    	throws DataException; 

}