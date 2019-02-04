package com.fgc.futbuy.service;

import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Oferta;

public interface OfertaService {
	
	public Oferta findById(Integer id) 
    		throws InstanceNotFoundException, DataException;
	
	public List<Oferta> findByCriteria(OfertaCriteria Oferta)
	   	    throws InstanceNotFoundException,DataException;
	
    public Boolean exists(Integer id) 
    		throws DataException;

    public List<Oferta> findAll() 
    		 throws DataException;
     
    public Integer countAll() 
     		throws DataException;          
    
    public Oferta create(Oferta o) 
    		throws DuplicateInstanceException, DataException;

    public void update(Oferta o) 
    		throws InstanceNotFoundException, DataException;
        
    public Integer delete(Integer id) 
    		throws InstanceNotFoundException, DataException;
}
