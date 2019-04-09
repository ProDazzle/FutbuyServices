package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fgc.futbuy.dao.TallaDAO;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Talla;

public class TallaDAOImpl implements TallaDAO {
		
	private static Logger logger = LogManager.getLogger(TallaDAOImpl.class);
	
		public TallaDAOImpl() {
		}
		
		@Override
		public Talla findById(Connection connection, Integer id, String idioma) 
				throws InstanceNotFoundException, DataException {
			
			if(logger.isDebugEnabled()) {
				logger.debug("Id= "+id);
			}

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {          

				String queryString = 
								"SELECT C.ID_TALA, CI.NOMBRE_TALLA " + 
								"FROM TALLA C  " +
								"INNER JOIN IDIOMA_TALLA CI ON C.ID_TALLA = CI.ID_TALLA " +
								"WHERE C.ID_TALLA = ? AND CI.ID_IDIOMA LIKE ?";


				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				logger.debug(queryString);

				int i = 1;                
				preparedStatement.setInt(i++, id);
				preparedStatement.setString(i++, idioma);

				// Execute query            
				resultSet = preparedStatement.executeQuery();

				Talla e = null;

				if (resultSet.next()) {
					e = loadNext(resultSet);				
				} else {
					throw new InstanceNotFoundException("Categories with id " + id + 
							"not found", Talla.class.getName());
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
		public List<Talla> findAll(Connection connection, String idioma,int startIndex, int count) 
				throws DataException {
			
			if(logger.isDebugEnabled()) {
				logger.debug("Idioma= "+idioma);
			}

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {

			String queryString = 
					"SELECT C.ID_TALLA, CI.ID_TALLA " + 
							"FROM TALLA C  " +
							"INNER JOIN IDIOMA_TALLA CI ON C.ID_TALLA = CI.ID_TALLA " +
							"WHERE CI.ID_IDIOMA LIKE ? ";

				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				logger.debug(queryString);
				
				int i = 1;                
				preparedStatement.setString(i++, idioma);
				
				resultSet = preparedStatement.executeQuery();

				List<Talla> results = new ArrayList<Talla>();                        
				Talla t = null;
				int currentCount = 0;

				if ((startIndex >=1) && resultSet.absolute(startIndex)) {
					do {
						t = loadNext( resultSet);
						results.add(t);               	
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
						"SELECT C.ID_TALA, CI.NOMBRE_TALLA " + 
								"FROM TALLA C  " +
								"INNER JOIN IDIOMA_TALLA CI ON C.ID_TALLA = CI.ID_TALLA " +
								"WHERE C.ID_TALLA = ?";


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

		public List<Talla> findByProducto(Connection connection, Integer idProducto, String idioma,int startIndex, int count) 
				throws DataException {
			
			if(logger.isDebugEnabled()) {
				logger.debug("Id= "+idProducto+ " , Idioma="+idioma);
			}
			
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {

				String queryString = (
										"SELECT PC.ID_TALLA, CI.NOMBRE_TALLA " +
										"FROM PRODUCTO_TALLA PC " +
										"INNER JOIN TALLA C ON PC.ID_TALLA=C.ID_TALLA " +
										"INNER JOIN IDIOMA_TALLA CI ON CI.ID_TALLA=C.ID_TALLA " +
										"WHERE PC.ID_TALLA = ? AND CI.ID_IDIOMA= ? ");


				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				logger.debug(queryString);

				int i = 1;
				preparedStatement.setInt(i++, idProducto);
				preparedStatement.setString(i++, idioma);

				resultSet = preparedStatement.executeQuery();

				// Recupera la pagina de resultados
				List<Talla> results = new ArrayList<Talla>();                        
				Talla e = null;
				int currentCount = 0;

				if ((startIndex >=1) && resultSet.absolute(startIndex)) {
					do {
						e = loadNext( resultSet);
						results.add(e);               	
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

		
		private Talla loadNext(ResultSet resultSet) 
				throws SQLException, DataException {

			int i = 1;
			Integer idTalla = resultSet.getInt(i++);
			String nombreTalla = resultSet.getString(i++);

			Talla c = new Talla();
			c.setIdTalla(idTalla);
			c.setNombreTalla(nombreTalla);

			return c;
		}

}
