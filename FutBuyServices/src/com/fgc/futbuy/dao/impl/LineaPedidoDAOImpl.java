package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fgc.futbuy.dao.LineaPedidoDAO;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.LineaPedido;
import com.fgc.futbuy.model.LineaPedidoId;
import com.fgc.futbuy.model.Usuario;


/**
 * @author hector.ledo.doval
 *
 */
public class LineaPedidoDAOImpl implements LineaPedidoDAO{

public LineaPedidoDAOImpl() {}
	
	@Override
	public LineaPedido findById(Connection connection, LineaPedidoId id) 
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT ID_PEDIDO, ID_PRODUCTO, PRECIO_UNITARIO, PRECIO_TOTAL, CANTIDAD " + 
							"FROM LINEA_PEDIDO  " +
							"WHERE ID_PEDIDO = ? AND ID_PRODUCTO = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setInt(i++, id.getIdPedido());
			preparedStatement.setInt(i++, id.getIdProducto());

			resultSet = preparedStatement.executeQuery();

			LineaPedido lp = null;

			if (resultSet.next()) {
				lp = loadNext(connection, resultSet);				
			} else {
				throw new InstanceNotFoundException("PedidoDetails not found", Usuario.class.getName());
			}

			return lp;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  
	}
	
	@Override
	public Boolean exists(Connection connection, LineaPedidoId id) 
			throws DataException {
		
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT ID_PEDIDO, ID_PRODUCTO " + 
							"FROM LINEA_PEDIDO " +
							"WHERE ID_PEDIDO = ? AND ID_PRODUCTO = ? ";

			preparedStatement = connection.prepareStatement(queryString);
			
			int i = 1;
			preparedStatement.setInt(i++, id.getIdPedido());
			preparedStatement.setInt(i++, id.getIdProducto());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				exist = true;
			}

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}

		return exist;
	}


	@Override
	public List<LineaPedido> findByPedido (Connection connection, Integer idPedido)
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT L.ID_PEDIDO, L.ID_PRODUCTO, L.PRECIO_UNITARIO, L.PRECIO_TOTAL, L.CANTIDAD " + 
					"FROM LINEA_PEDIDO L " +
						"INNER JOIN PEDIDO P "+
						"ON L.ID_PEDIDO = P.ID_PEDIDO AND P.ID_PEDIDO = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setInt(i++, idPedido);

			resultSet = preparedStatement.executeQuery();

			List<LineaPedido> results = new ArrayList<LineaPedido>();  
			
			LineaPedido lp = null;
			
			while (resultSet.next()) {
				lp = loadNext (connection, resultSet);
				results.add(lp);
			}
			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	

	@Override
	public LineaPedido create(Connection connection, LineaPedido lp) 
			throws DuplicateInstanceException, DataException {

		PreparedStatement preparedStatement = null;
		
		LineaPedidoId id = new LineaPedidoId();
		id.setIdPedido(lp.getIdPedido());
		id.setIdProducto(lp.getIdProducto());
		
		try {          
			
			if (exists(connection, id)) {
				throw new DuplicateInstanceException(id, LineaPedido.class.getName());
			}
						
			String queryString = "INSERT INTO LINEA_PEDIDO (ID_PEDIDO, ID_PRODUCTO, PRECIO_UNITARIO, PRECIO_TOTAL, CANTIDAD) "
								+"VALUES (?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString);
			
			int i = 1;     
			preparedStatement.setInt(i++,lp.getIdPedido());
			preparedStatement.setInt(i++,lp.getIdProducto());
			preparedStatement.setDouble(i++,lp.getPrecioUnitario());
			preparedStatement.setDouble(i++,lp.getPrecioTotal());
			preparedStatement.setDouble(i++,lp.getCantidad());
			
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Pedido'");
			}
			
			return lp;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}


	@Override
	public Integer delete(Connection connection, LineaPedidoId id) 
			throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;

		try {

			String queryString =	
					  "DELETE FROM LINEA_PEDIDO " 
					+ "WHERE ID_PEDIDO = ? AND ID_PRODUCTO = ? ";
			
			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setInt(i++, id.getIdPedido());
			preparedStatement.setInt(i++,id.getIdProducto());

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(id,LineaPedido.class.getName());
			} 
					
			return removedRows;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
	
	private LineaPedido loadNext(Connection connection, ResultSet resultSet)
		throws SQLException, DataException {

			int i = 1;
			Integer idPedido = resultSet.getInt(i++);	                
			Integer idProducto = resultSet.getInt(i++);	                
			Double precioUnitario = resultSet.getDouble(i++);
			Double precioTotal = resultSet.getDouble(i++); 
			Integer cantidad = resultSet.getInt(i++);
	
			LineaPedido lp = new LineaPedido();		
			lp.setIdPedido(idPedido);
			lp.setIdProducto(idProducto);
			lp.setPrecioUnitario(precioUnitario);
			lp.setPrecioTotal(precioTotal);
			lp.setCantidad(cantidad);
			return lp;
		}

}