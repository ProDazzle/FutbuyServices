package com.fgc.futbuy.dao;

import java.sql.Connection;
import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Equipo;

public interface EquipoDAO {
	
	public Equipo findById(Connection connection, Integer id) 
			throws InstanceNotFoundException, DataException;
	
	public Boolean exists(Connection connection, Integer id) 
    		throws DataException;
	
    public List<Equipo> findAll(Connection connection,int startIndex, int count) 
        	throws DataException;
}