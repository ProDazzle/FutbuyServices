
package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fgc.futbuy.dao.LineaPedidoDAO;
import com.fgc.futbuy.dao.PedidoDAO;
import com.fgc.futbuy.model.LineaPedido;
import com.fgc.futbuy.model.Pedido;
import com.fgc.futbuy.service.PedidoCriteria;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;


public class PedidoDAOImpl implements PedidoDAO {
	
	private static Logger logger = LogManager.getLogger(PedidoDAOImpl.class);
	
	private LineaPedidoDAO lineaPedidoDAO = null;

	public PedidoDAOImpl() {
		lineaPedidoDAO  = new LineaPedidoDAOImpl();
	}

	@Override
	public Pedido findById(Connection connection, Integer id) 
			throws InstanceNotFoundException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Id= "+id);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT ID_PEDIDO, ID_USUARIO, TOTAL, FECHA_PEDIDO, FECHA_LLEGADA " +
							"FROM PEDIDO " +
							"WHERE ID_PEDIDO = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			logger.debug(queryString);

			int i = 1;                
			preparedStatement.setInt(i++, id);

			resultSet = preparedStatement.executeQuery();

			Pedido p = null;

			if (resultSet.next()) {
				p = loadNext(connection, resultSet);				
			} else {
				throw new InstanceNotFoundException("Pedido with id " + id + 
						"not found", Pedido.class.getName());
			}

			return p;

		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  
	}

	@Override
	public Boolean exists(Connection connection, Integer id) 
			throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Id= "+id);
		}
		
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT ID_PEDIDO " + 
					"FROM PEDIDO  " +
					"WHERE ID_PEDIDO = ? ";

			preparedStatement = connection.prepareStatement(queryString);
			
			logger.debug(queryString);

			int i = 1;
			preparedStatement.setInt(i++, id);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				exist = true;
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}

		return exist;
	}

	@Override
	public List<Pedido> findAll(Connection connection,int startIndex, int count) 
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

		String queryString = 
				"SELECT ID_PEDIDO, ID_USUARIO, TOTAL, FECHA_PEDIDO, FECHA_LLEGADA " +
						"FROM PEDIDO  " +
						"ORDER BY FECHA_PEDIDO DESC";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			logger.debug(queryString);

			resultSet = preparedStatement.executeQuery();

			List<Pedido> results = new ArrayList<Pedido>();                        
			Pedido p = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					p = loadNext(connection, resultSet);
					results.add(p);               	
					currentCount++;                	
				} while ((currentCount < count) && resultSet.next()) ;
			}

			return results;

		} catch (SQLException ex) {
			logger.error(ex.getMessage(),ex);
			throw new DataException(ex);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  	
	}
	
	@Override
	public Integer countAll(Connection connection) 
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					" SELECT COUNT(*) "
							+ " FROM PEDIDO ";

			preparedStatement = connection.prepareStatement(queryString);
			
			logger.debug(queryString);

						resultSet = preparedStatement.executeQuery();

			int i=1;
			if (resultSet.next()) {
				return resultSet.getInt(i++);
			} else {
				throw new DataException("Unexpected condition trying to retrieve count value");
			}

		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}	    	 
	}
	
	@Override
	public List<Pedido> findByCriteria(Connection connection, PedidoCriteria pedido,int startIndex, int count)
			throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("PedidoCriteria= "+pedido);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
    
			queryString = new StringBuilder(
					"SELECT ID_PEDIDO, ID_USUARIO, TOTAL, FECHA_PEDIDO, FECHA_LLEGADA " +
					"FROM PEDIDO ");
			
			boolean first = true;
			
			if (pedido.getIdPedido()!=null) {
				addClause(queryString, first, " ID_PEDIDO LIKE ? ");
				first = false;
			}
				
			if (pedido.getIdUsuario()!=null) {
				addClause(queryString, first, " ID_USUARIO LIKE ? ");
				first = false;
			}
			
			if (pedido.getTotal()!=null) {
				addClause(queryString, first, " TOTAL LIKE ? ");
				first = false;
			}

			if (pedido.getFechaPedido()!=null) {
				addClause(queryString, first, " FECHA_PEDIDO LIKE ? ");
				first = false;
			}			
			
			if (pedido.getFechaLlegada()!=null) {
				addClause(queryString, first, " FECHA_LLEGADA LIKE ? ");
				first = false;
			}
				
			
			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			logger.debug(queryString);

			int i = 1;  
			
			if (pedido.getIdPedido()!=null) 
				preparedStatement.setInt(i++,pedido.getIdPedido());
			if (pedido.getIdUsuario() !=null) 
				preparedStatement.setInt(i++, pedido.getIdUsuario());
			if (pedido.getTotal()!=null) 
				preparedStatement.setDouble(i++,pedido.getTotal());
			if (pedido.getFechaPedido()!=null)
				preparedStatement.setDate(i++,(java.sql.Date) pedido.getFechaPedido());
			if (pedido.getFechaLlegada()!=null) 
				preparedStatement.setDate(i++,(java.sql.Date) pedido.getFechaLlegada());


			resultSet = preparedStatement.executeQuery();
			
			List<Pedido> pedidos = new ArrayList<Pedido>();                        
			Pedido p = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					p = loadNext(connection, resultSet);
					pedidos.add(p);               	
					currentCount++;                	
				} while ((currentCount < count) && resultSet.next()) ;
			}

			return pedidos;
			
	} catch (SQLException e) {
		logger.error(e.getMessage(),e);
		throw new DataException("Hemos encontrado un problema" + e);
	} finally {
		JDBCUtils.closeResultSet(resultSet);
		JDBCUtils.closeStatement(preparedStatement);
	}
}
	
	
	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
	}
	
	private Pedido loadNext(Connection connection, ResultSet resultSet)
		throws SQLException, DataException {
			Pedido p = new Pedido();
			int i = 1;
			Integer idPedido = resultSet.getInt(i++);
			Integer idUsuario = resultSet.getInt(i++);
			Double total = resultSet.getDouble(i++);
			Date fechaPedido = resultSet.getDate(i++);
			Date fechaLlegada = resultSet.getDate(i++);	                
		
			

			p = new Pedido();		
			p.setIdPedido(idPedido);
			p.setIdUsuario(idUsuario);
			p.setTotal(total);
			p.setFechaPedido(fechaPedido);
			p.setFechaLlegada(fechaLlegada);

			
			
			List<LineaPedido> lineasPedido = lineaPedidoDAO.findByPedido(connection, idPedido);
			p.setLineaspedido(lineasPedido);

			return p;
		}
	

	@Override
	public Pedido create(Connection connection, Pedido p) 
			throws DuplicateInstanceException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Pedido= "+p);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {          
			
			String queryString = "INSERT INTO PEDIDO( ID_USUARIO, TOTAL, FECHA_PEDIDO, FECHA_LLEGADA ) " +
					"VALUES (?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString,
					Statement.RETURN_GENERATED_KEYS);
			
			logger.debug(queryString);
			
			int i = 1;   
			preparedStatement.setInt(i++,p.getIdUsuario());
			preparedStatement.setDouble(i++, p.getTotal());
			preparedStatement.setDate(i++, new java.sql.Date(p.getFechaPedido().getTime()));
			preparedStatement.setDate(i++, new java.sql.Date(p.getFechaLlegada().getTime()));

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Pedido'");
			} 
			
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Integer pk = resultSet.getInt(1); 
				p.setIdPedido(pk);
			} else {
				throw new DataException("Unable to fetch autogenerated primary key");
			}
			
			List<LineaPedido> lineas = p.getLineaspedido();

			for (LineaPedido lp: lineas) {
				lp.setNumeroLinea(p.getIdPedido()); 
				//lp.setIdPedido(p.getIdPedido()); PREGUNTAR CAL E CORRECTO
				lineaPedidoDAO.create(connection, lp);
			}
			
			return p;

		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	public void update(Connection connection, Pedido p) 
			throws InstanceNotFoundException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Pedido= "+p);
		}
		PreparedStatement preparedStatement = null;
		try {
			
			deleteLineasPedido(connection, p.getIdPedido());
			
			String queryString = 
					"UPDATE PEDIDO " +
					"SET ID_USUARIO = ?, TOTAL = ?, FECHA_PEDIDO = ? ,FECHA_LLEGADA = ? " +
					"WHERE ID_PEDIDO = ? ";

			preparedStatement = connection.prepareStatement(queryString);
			
			logger.debug(queryString);

			int i = 1;
			preparedStatement.setInt(i++, p.getIdUsuario());
			preparedStatement.setDouble(i++, p.getTotal());
			preparedStatement.setDate(i++, new java.sql.Date(p.getFechaPedido().getTime()));
			preparedStatement.setDate(i++, new java.sql.Date(p.getFechaLlegada().getTime()));
			
			preparedStatement.setInt(i++, p.getIdPedido());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(p.getIdPedido(), Pedido.class.getName());
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						p.getIdPedido() + "' in table 'Pedido'");
			}     
			
			createLineasPedido(connection, p.getIdPedido(), p.getLineaspedido());

		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}              		
	}

	@Override
	public Integer delete(Connection connection, Integer id) 
			throws InstanceNotFoundException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Id= "+id);
		}
		
		PreparedStatement preparedStatement = null;

		try {
			
			deleteLineasPedido(connection, id);

			String queryString =	
					  "DELETE FROM PEDIDO " 
					+ "WHERE ID_PEDIDO = ? ";
			
			preparedStatement = connection.prepareStatement(queryString);
			
			logger.debug(queryString);

			int i = 1;
			preparedStatement.setLong(i++, id);

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(id,Pedido.class.getName());
			} 
			return removedRows;

		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}

	}
	
	protected void deleteLineasPedido(Connection c, Integer idPedido)
			throws SQLException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("IdPedido= "+idPedido);
		}

			PreparedStatement preparedStatement = null;

			try {

				String queryString =	
						  "DELETE FROM LINEAPEDIDO " 
						+ "WHERE ID_PEDIDO = ? ";
				
				preparedStatement = c.prepareStatement(queryString);

				int i = 1;
				preparedStatement.setInt(i++, idPedido);

				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				logger.error(e.getMessage(),e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeStatement(preparedStatement);
			}
		}
	
	protected void createLineasPedido(Connection connection, Integer idPedido,  List<LineaPedido> lineas)
			throws SQLException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Id= "+idPedido);
		}

		PreparedStatement preparedStatement = null;
		try {          
			String queryString = null;
			int i;
			for (LineaPedido lt: lineas ) {
				queryString = "INSERT INTO LINEAPEDIDO (ID_PEDIDO, ID_PRODUCTO, PRECIO_UNITARIO, PRECIO_TOTAL, CANTIDAD) VALUES (?, ?, ?, ?, ?)";
				preparedStatement = connection.prepareStatement(queryString);
				
				logger.debug(queryString);

				i = 1;     
				preparedStatement.setInt(i++, idPedido);
				preparedStatement.setInt(i++, lt.getIdProducto());
				preparedStatement.setDouble(i++, lt.getPrecioUnitario());
				preparedStatement.setDouble(i++, lt.getPrecioTotal());
				preparedStatement.setInt(i++, lt.getCantidad());

				int insertedRows = preparedStatement.executeUpdate();

				if (insertedRows == 0) {
					throw new SQLException("Can not add row to table 'LINEAPEDIDO'");
				}	

				JDBCUtils.closeStatement(preparedStatement);
			} 
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public List<Pedido> findAllUsuario(Connection connection, Integer id,int startIndex, int count) throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Id= "+id);
		}
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

		String queryString = 
				"SELECT ID_PEDIDO, ID_USUARIO, TOTAL, FECHA_PEDIDO, FECHA_LLEGADA " +
						"FROM PEDIDO  " +
						" WHERE ID_USUARIO = ? " +
						" ORDER BY FECHA_PEDIDO DESC";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			logger.debug(queryString);

			resultSet = preparedStatement.executeQuery();

			List<Pedido> results = new ArrayList<Pedido>();                        
			Pedido p = null;
			
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					p = loadNext(connection, resultSet);
					results.add(p);               	
					currentCount++;                	
				} while ((currentCount < count) && resultSet.next()) ;
			}

			return results;

		} catch (SQLException ex) {
			logger.error(ex.getMessage(),ex);
			throw new DataException(ex);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  	
	}

}