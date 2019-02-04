package com.fgc.futbuy.service;

import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Producto;

public interface ProductoService {
	
	    public List<Producto> findByCriteria(ProductoCriteria Producto, String idioma)
	   	    throws DataException;
	    
	    public List<Producto> findAll(String idioma) 
	    	throws DataException;  
	    
	    public Producto findById(Integer id, String idioma) 
	    	throws InstanceNotFoundException, DataException;

		public Boolean exists(Integer id) 
		    throws DataException;

		public Integer countAll() 
	     	throws DataException;  

	    public Producto create(Producto p) 
	    	throws DuplicateInstanceException, DataException;

	    public void update(Producto p) 
	    	throws InstanceNotFoundException, DataException;
	        
	    public Integer delete(Integer integer) 
	    	throws InstanceNotFoundException, DataException;
}
