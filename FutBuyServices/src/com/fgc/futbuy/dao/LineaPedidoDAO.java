
package com.fgc.futbuy.dao;

import java.sql.Connection;
import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.LineaPedido;
import com.fgc.futbuy.model.LineaPedidoId;



public interface LineaPedidoDAO {
	
	public LineaPedido findById(Connection connection, LineaPedidoId id) 
        	throws DataException;
	
	public Boolean exists(Connection connection, LineaPedidoId id) 
    		throws DataException;
	    
    public List<LineaPedido> findByPedido(Connection connection, Integer idPedido) 
        	throws DataException;
  
    public LineaPedido create(Connection connection, LineaPedido c) 
    		throws DuplicateInstanceException, DataException;
        
    public Integer delete(Connection connection, LineaPedidoId id) 
    		throws InstanceNotFoundException, DataException;

}