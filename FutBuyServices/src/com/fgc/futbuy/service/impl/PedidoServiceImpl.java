
package com.fgc.futbuy.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fgc.futbuy.dao.PedidoDAO;
import com.fgc.futbuy.dao.impl.PedidoDAOImpl;
import com.fgc.futbuy.dao.util.ConnectionManager;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Pedido;
import com.fgc.futbuy.service.PedidoCriteria;
import com.fgc.futbuy.service.PedidoService;

public class PedidoServiceImpl implements PedidoService{
	
	private static Logger logger = LogManager.getLogger(PedidoServiceImpl.class);

		private PedidoDAO dao = null;
		
		public PedidoServiceImpl() {
			dao = new PedidoDAOImpl();
		}
		@Override
		public Pedido findById(Integer id) 
				throws InstanceNotFoundException, DataException {
			
			if(logger.isDebugEnabled()) {
				logger.debug("id= "+id);
			}
					
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				return dao.findById(connection, id);	
				
			} catch (SQLException e){
				logger.error(e.getMessage(),e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
			
		}
		@Override
		public List<Pedido> findAll( int startIndex, int count) 
				throws DataException {
				
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				return dao.findAll(connection,1,10);	
				
			} catch (SQLException e){
				logger.error(e.getMessage(),e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
			
		}
		@Override
		public Boolean exists(Integer id) 
				throws DataException {
			
			if(logger.isDebugEnabled()) {
				logger.debug("id= "+id);
			}
					
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				return dao.exists(connection, id);
				
			} catch (SQLException e){
				logger.error(e.getMessage(),e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
			
		}
		@Override
		public Integer countAll() 
				throws DataException {
					
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				return dao.countAll(connection);		
				
			} catch (SQLException e){
				logger.error(e.getMessage(),e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
			
		}
		
	     public List<Pedido> findByCriteria(PedidoCriteria pedido, int startIndex, int count)
				throws DataException {
	    	 if(logger.isDebugEnabled()) {
	 			logger.debug("PedidoCriteria= "+pedido);
	 		}
				
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				return dao.findByCriteria(connection, pedido,1,10);
				
			} catch (SQLException e){
				logger.error(e.getMessage(),e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
		}
	     @Override
		public Pedido create(Pedido p) 
				throws DuplicateInstanceException, DataException {
	    	 if(logger.isDebugEnabled()) {
	 			logger.debug("Pedido= "+p);
	 		}
			
		    Connection connection = null;
	        boolean commit = false;

	        try {
	          
	            connection = ConnectionManager.getConnection();

	            connection.setTransactionIsolation(
	                    Connection.TRANSACTION_READ_COMMITTED);

	            connection.setAutoCommit(false);

	            // Execute action
	            Pedido result = dao.create(connection, p);
	            commit = true;
	            
	            return result;

	        } catch (SQLException e) {
	        	logger.error(e.getMessage(),e);
	            throw new DataException(e);

	        } finally {
	        	JDBCUtils.closeConnection(connection, commit);
	        }		
		}
	     @Override
		public void update(Pedido p) 
				throws InstanceNotFoundException, DataException {
	    	 if(logger.isDebugEnabled()) {
	 			logger.debug("Pedido= "+p);
	 		}
			
		    Connection connection = null;
	        boolean commit = false;

	        try {
	          
	            connection = ConnectionManager.getConnection();

	            connection.setTransactionIsolation(
	                    Connection.TRANSACTION_READ_COMMITTED);

	            connection.setAutoCommit(false);

	            dao.update(connection,p);
	            commit = true;
	            
	        } catch (SQLException e) {
	        	logger.error(e.getMessage(),e);
	            throw new DataException(e);

	        } finally {
	        	JDBCUtils.closeConnection(connection, commit);
	        }
		}
	     @Override
		public long delete(Integer id) 
				throws InstanceNotFoundException, DataException {
	    	 if(logger.isDebugEnabled()) {
	 			logger.debug("id= "+id);
	 		}
			
		    Connection connection = null;
	        boolean commit = false;

	        try {
	          
	            connection = ConnectionManager.getConnection();

	            connection.setTransactionIsolation(
	                    Connection.TRANSACTION_READ_COMMITTED);

	            connection.setAutoCommit(false);

	            Integer result = dao.delete(connection, id);            
	            commit = true;            
	            return result;
	            
	        } catch (SQLException e) {
	        	logger.error(e.getMessage(),e);
	            throw new DataException(e);

	        } finally {
	        	JDBCUtils.closeConnection(connection, commit);
	        }		
		}

		@Override
		public List<Pedido> findAllUsuario(Integer id, int startIndex, int count) throws DataException {
			
			if(logger.isDebugEnabled()) {
				logger.debug("id= "+id);
			}
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				return dao.findAllUsuario(connection, id,1,10);	
				
			} catch (SQLException e){
				logger.error(e.getMessage(),e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
			
		}
	}