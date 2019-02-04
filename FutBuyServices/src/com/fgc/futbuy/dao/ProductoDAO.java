
package com.fgc.futbuy.dao;

import java.sql.Connection;
import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Producto;
import com.fgc.futbuy.service.ProductoCriteria;


public interface ProductoDAO {
	
    public List<Producto> findByCriteria(Connection connection, ProductoCriteria producto, String idioma)
        	throws DataException;

    public List<Producto> findAll(Connection connection, String idioma) 
    	throws DataException;
     
	public Boolean exists(Connection connection, Integer id) 
    		throws DataException;
	
	public Producto findById(Connection connection, Integer id, String idioma) 
			throws InstanceNotFoundException, DataException;

	public Integer countAll(Connection connection) 
     		throws DataException;   
     
    public Producto create(Connection connection, Producto producto) 
    		throws DuplicateInstanceException, DataException;

    public void update(Connection connection, Producto producto) 
    		throws InstanceNotFoundException, DataException;
        
    public Integer delete(Connection connection, Integer id) 
    		throws InstanceNotFoundException, DataException;

}