
package com.fgc.futbuy.dao;

import java.sql.Connection;
import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Idioma;

public interface IdiomaDAO {
	
	public Idioma findById(Connection connection, String id) 
    		throws InstanceNotFoundException, DataException;
	
	public List<Idioma> findAll(Connection connection) 
        	throws DataException;
}