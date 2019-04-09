
package com.fgc.futbuy.dao;

import java.sql.Connection;
import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Oferta;
import com.fgc.futbuy.service.OfertaCriteria;




public interface OfertaDAO {
	
	public Oferta findById(Connection connection, Integer id) 
    		throws InstanceNotFoundException, DataException;

	public Boolean exists(Connection connection, Integer id) 
    		throws DataException;

    public List<Oferta> findAll(Connection connection,int startIndex, int count) 
    	throws DataException;
    
    public Integer countAll(Connection connection) 
     		throws DataException;   
     
    public List<Oferta> findByCriteria(Connection connection, OfertaCriteria oc,int startIndex, int count)
    	throws InstanceNotFoundException,DataException;
    
    public Oferta create(Connection connection, Oferta o) 
    		throws DuplicateInstanceException, DataException;

    public void update(Connection connection, Oferta o) 
    		throws InstanceNotFoundException, DataException;
        
    public Integer delete(Connection connection, Integer id) 
    		throws InstanceNotFoundException, DataException;
}