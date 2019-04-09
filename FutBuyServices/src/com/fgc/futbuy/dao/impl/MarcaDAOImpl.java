package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fgc.futbuy.dao.MarcaDAO;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Marca;


public class MarcaDAOImpl implements MarcaDAO {
	
	private static Logger logger = LogManager.getLogger(MarcaDAOImpl.class);
		
	
		public MarcaDAOImpl() {
		}
		
		@Override
		public Marca findById(Connection connection, Integer id) 
				throws InstanceNotFoundException, DataException {
			
			if(logger.isDebugEnabled()) {
				logger.debug("Id= "+id);
			}

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {          

				String queryString = 
								"SELECT ID_MARCA, NOMBRE_MARCA " + 
								"FROM MARCA  " +
								"WHERE ID_MARCA = ?";


				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				logger.debug(queryString);

				int i = 1;                
				preparedStatement.setInt(i++, id);

				// Execute query            
				resultSet = preparedStatement.executeQuery();

				Marca e = null;

				if (resultSet.next()) {
					e = loadNext(resultSet);				
				} else {
					throw new InstanceNotFoundException("Marca with id " + id + 
							"not found", Marca.class.getName());
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
		public List<Marca> findAll(Connection connection,int startIndex, int count) 
				throws DataException {

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {

				String queryString = 
						"SELECT ID_MARCA, NOMBRE_MARCA " + 
						"FROM MARCA  " +
						"ORDER BY NOMBRE_MARCA ASC";

				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				logger.debug(queryString);

				resultSet = preparedStatement.executeQuery();

				List<Marca> results = new ArrayList<Marca>();                        
				Marca m = null;
				int currentCount = 0;

				if ((startIndex >=1) && resultSet.absolute(startIndex)) {
					do {
						m = loadNext( resultSet);
						results.add(m);               	
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
						"SELECT ID_MARCA, NOMBRE_MARCA " + 
						"FROM MARCA  " +
						"WHERE ID_MARCA = ?";


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

	

		
		private Marca loadNext(ResultSet resultSet) 
				throws SQLException, DataException {

			int i = 1;
			Integer idMarca = resultSet.getInt(i++);
			String nombreMarca = resultSet.getString(i++);

			Marca c = new Marca();
			c.setIdMarca(idMarca);
			c.setNombreMarca(nombreMarca);
			return c;
		}

}
