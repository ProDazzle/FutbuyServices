package com.fgc.futbuy.service;

import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Pedido;


public interface PedidoService {
	
		public Pedido findById(Integer id) 
	   		throws InstanceNotFoundException, DataException;
		 
	    public List<Pedido> findByCriteria(PedidoCriteria pedido)
	   	    throws DataException;
	    
	    public List<Pedido> findAll() 
	    	throws DataException; 
	    
	    public List<Pedido> findAllUsuario(Integer id) 
		    	throws DataException; 
	    
		public Boolean exists(Integer id) 
		    throws DataException;

		public Integer countAll() 
	     	throws DataException;  

	    public Pedido create(Pedido p) 
	    	throws DuplicateInstanceException, DataException;

	    public void update(Pedido p) 
	    	throws InstanceNotFoundException, DataException;
	        
	    public long delete(Integer id) 
	    	throws InstanceNotFoundException, DataException;
}
