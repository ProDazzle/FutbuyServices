
package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fgc.futbuy.dao.OfertaDAO;
import com.fgc.futbuy.model.Oferta;
import com.fgc.futbuy.service.OfertaCriteria;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;


public class OfertaDAOImpl implements OfertaDAO{
	
	private static Logger logger = LogManager.getLogger(OfertaDAOImpl.class);
	
	public OfertaDAOImpl() {
		
	}
	
	@Override
	public Oferta findById(Connection connection, Integer id) 
			throws InstanceNotFoundException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Id= "+id);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT ID_OFERTA, PRECIO_DESCUENTO ,FECHA_CREACION " + 
							"FROM OFERTA  " +
							"WHERE ID_OFERTA = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			logger.debug(queryString);

			int i = 1;                
			preparedStatement.setInt(i++, id);

			resultSet = preparedStatement.executeQuery();

			Oferta e = null;

			if (resultSet.next()) {
				e = loadNext(resultSet);				
			} else {
				throw new InstanceNotFoundException("Customer with id " + id + 
						"not found", Oferta.class.getName());
			}

			return e;

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
					"SELECT ID_OFERTA " + 
							"FROM OFERTA  " +
							"WHERE ID_OFERTA = ? ";

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
	public List<Oferta> findAll(Connection connection,int startIndex, int count) 
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					"SELECT ID_OFERTA, PRECIO_DESCUENTO, FECHA_CREACION " + 
					"FROM OFERTA  " +
					"ORDER BY FECHA_CREACION ASC";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			logger.debug(queryString);

			resultSet = preparedStatement.executeQuery();

			List<Oferta> results = new ArrayList<Oferta>();                        
			Oferta e = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					e = loadNext( resultSet);
					results.add(e);               	
					currentCount++;                	
				} while ((currentCount < count) && resultSet.next()) ;
			}

			return results;

		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DataException(e);
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
					" SELECT COUNT*) "
							+ " FROM OFERTA";

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
	public List<Oferta> findByCriteria(Connection connection, OfertaCriteria oc,int startIndex, int count)
			throws InstanceNotFoundException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("OfertaCriteria= "+oc);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
    
			queryString = new StringBuilder(
					" SELECT ID_OFERTA,PRECIO_DESCUENTO,FECHA_CREACION" + 
					" FROM OFERTA ");
			
			
			boolean first = true;
			
			if (oc.getIdOferta()!=null) {
				addClause(queryString, first, " ID_OFERTA LIKE ? ");
				first = false;
			}
			
			if (oc.getPrecioDescuento()!=null) {
				addClause(queryString, first, " PRECIO_DESCUENTO LIKE ? ");
				first = false;
			}
			
			if (oc.getFechaCreacion()!=null) {
				addClause(queryString, first, " FECHA_CREACION LIKE ? ");
				first = false;
			}

						
			
			
			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			logger.debug(queryString);

			int i = 1;       
			
			if (oc.getIdOferta()!=null) 
				preparedStatement.setString(i++, "%" + oc.getIdOferta() + "%");
			if (oc.getPrecioDescuento()!=null) 
				preparedStatement.setString(i++, "%" + oc.getPrecioDescuento() + "%");
			if (oc.getFechaCreacion()!=null) 
				preparedStatement.setString(i++, "%" + oc.getFechaCreacion() + "%");
			
			

			resultSet = preparedStatement.executeQuery();
			
			List<Oferta> results = new ArrayList<Oferta>();                        
			Oferta e = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					e = loadNext( resultSet);
					results.add(e);               	
					currentCount++;                	
				} while ((currentCount < count) && resultSet.next()) ;
			}

			return results;
	} catch (SQLException e) {
		logger.error(e.getMessage(),e);
		throw new DataException("Hemos encontrado un problema" + e);
	} finally {
		JDBCUtils.closeResultSet(resultSet);
		JDBCUtils.closeStatement(preparedStatement);
	}
}
	


	@Override
	public Oferta create(Connection connection, Oferta o) 
			throws DuplicateInstanceException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Oferta= "+o);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {          

			// Create the "preparedStatement"
			String queryString = "INSERT INTO OFERTA(PRECIO_DESCUENTO,FECHA_CREACION) "
					+ "VALUES (?, ?)";

			preparedStatement = connection.prepareStatement(queryString,
									Statement.RETURN_GENERATED_KEYS);
			
			logger.debug(queryString);

			// Fill the "preparedStatement"
			int i = 1;             
			
			preparedStatement.setDouble(i++, o.getPrecioDescuento());
			preparedStatement.setDate(i++, new java.sql.Date(o.getFechaCreacion().getTime()));
			// Execute query
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Oferta'");
			} 
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Integer pk = resultSet.getInt(1); 
				o.setIdOferta(pk);
			} else {
				throw new DataException("Unable to fetch autogenerated primary key");
			}

			// Return the DTO
			return o;

		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public void update(Connection connection, Oferta o) 
			throws InstanceNotFoundException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Oferta= "+o);
		}
		
		PreparedStatement preparedStatement = null;
		try {

			String queryString = 
					"UPDATE OFERTA " +
					"SET PRECIO_DESCUENTO = ? , FECHA_CREACION = ? " +
					"WHERE ID_OFERTA = ? ";

			preparedStatement = connection.prepareStatement(queryString);
			
			logger.debug(queryString);

			int i = 1;
			
			preparedStatement.setDouble(i++, o.getPrecioDescuento());
			preparedStatement.setDate(i++, new java.sql.Date(o.getFechaCreacion().getTime()));
			preparedStatement.setInt(i++, o.getIdOferta());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(o.getIdOferta(), Oferta.class.getName());
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						o.getIdOferta() + "' in table 'Oferta'");
			}     


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

			String queryString =	
					  "DELETE FROM OFERTA " 
					+ "WHERE ID_OFERTA = ? ";
			
			preparedStatement = connection.prepareStatement(queryString);
			
			logger.debug(queryString);

			int i = 1;
			preparedStatement.setInt(i++, id);

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(id,Oferta.class.getName());
			} 
			
			return removedRows;

		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" WHERE ": " AND ").append(clause);
	}
	
	private Oferta loadNext(ResultSet resultSet)
		throws SQLException, DataException {

			int i = 1;
			Integer idOferta = resultSet.getInt(i++);	  
			Double precioDescuento = resultSet.getDouble(i++);
			Timestamp fechaCreacion = resultSet.getTimestamp(i++);	                
			

	
			Oferta o = new Oferta();		
			o.setIdOferta(idOferta);
			o.setPrecioDescuento(precioDescuento);
			o.setFechaCreacion(fechaCreacion);
			

			return o;
		}
		
}
