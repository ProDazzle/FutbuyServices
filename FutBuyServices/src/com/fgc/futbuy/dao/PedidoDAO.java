
package com.fgc.futbuy.dao;

import java.sql.Connection;
import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Pedido;
import com.fgc.futbuy.service.PedidoCriteria;


public interface PedidoDAO {
	
	public Pedido findById(Connection connection, Integer id) 
    		throws InstanceNotFoundException, DataException;
	
    public List<Pedido> findByCriteria(Connection connection, PedidoCriteria pedido,int startIndex, int count)
        	throws DataException;
    
    public List<Pedido> findAll(Connection connection,int startIndex, int count) 
        	throws DataException;
    
    public List<Pedido> findAllUsuario(Connection connection, Integer id,int startIndex, int count) 
        	throws DataException;
         
	public Boolean exists(Connection connection, Integer id) 
    		throws DataException;

	public Integer countAll(Connection connection) 
     		throws DataException;   
     
    public Pedido create(Connection connection, Pedido pedido) 
    		throws DuplicateInstanceException, DataException;

    public void update(Connection connection, Pedido pedido) 
    		throws InstanceNotFoundException, DataException;
        
    public Integer delete(Connection connection, Integer id) 
    		throws InstanceNotFoundException, DataException;

}
