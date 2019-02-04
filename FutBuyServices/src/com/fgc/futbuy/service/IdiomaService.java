package com.fgc.futbuy.service;

import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Idioma;


public interface IdiomaService {
		
		public Idioma findById(String id) 
				throws InstanceNotFoundException, DataException;
		
	    public List<Idioma> findAll() 
		    	throws DataException; 
}