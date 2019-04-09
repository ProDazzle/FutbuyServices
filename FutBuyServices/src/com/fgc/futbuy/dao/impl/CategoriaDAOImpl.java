package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fgc.futbuy.dao.CategoriaDAO;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Categoria;


public class CategoriaDAOImpl implements CategoriaDAO {
		
	private static Logger logger = LogManager.getLogger(CategoriaDAOImpl.class);
	
		public CategoriaDAOImpl() {
		}
		
		@Override
		public Categoria findById(Connection connection, Integer id, String idioma) 
				throws InstanceNotFoundException, DataException {
			
			if(logger.isDebugEnabled()) {
				logger.debug("Id= "+id+" , Idioma = "+idioma);
			}

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {          

				String queryString = 
								"SELECT C.ID_CATEGORIA, CI.NOMBRE_CATEGORIA, DESCRIPCION " + 
								"FROM CATEGORIA C  " +
								"INNER JOIN IDIOMA_CATEGORIA CI ON C.ID_CATEGORIA = CI.ID_CATEGORIA " +
								"WHERE C.ID_CATEGORIA = ? AND CI.ID_IDIOMA LIKE ? ";


				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				logger.debug(queryString);

				int i = 1;                
				preparedStatement.setInt(i++, id);
				preparedStatement.setString(i++, idioma);

				// Execute query            
				resultSet = preparedStatement.executeQuery();

				Categoria e = null;

				if (resultSet.next()) {
					e = loadNext(resultSet);				
				} else {
					throw new InstanceNotFoundException("Categories with id " + id + 
							"not found", Categoria.class.getName());
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
		public List<Categoria> findAll(Connection connection, String idioma) 
				throws DataException {
			
			if(logger.isDebugEnabled()) {
				logger.debug("Idioma = "+idioma);
			}

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {

				String queryString = 
						"SELECT C.ID_CATEGORIA, CI.NOMBRE_CATEGORIA, DESCRIPCION " + 
						"FROM CATEGORIA C  " +
						"INNER JOIN IDIOMA_CATEGORIA CI ON C.ID_CATEGORIA = CI.ID_CATEGORIA " +
							"WHERE CI.ID_IDIOMA LIKE ? " +
						"ORDER BY CI.NOMBRE_CATEGORIA ASC";

				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				logger.debug(queryString);
				
				int i = 1;                
				preparedStatement.setString(i++, idioma);
				
				resultSet = preparedStatement.executeQuery();

				List<Categoria> results = new ArrayList<Categoria>();                        
				Categoria t = null;
			

				while(resultSet.next()) {
					t = loadNext(resultSet);
					results.add(t);               	
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
		public Boolean exists(Connection connection, Integer id) 
				throws DataException {
			
			if(logger.isDebugEnabled()) {
				logger.debug("Id = "+id);
			}
			
			boolean exist = false;

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {

				String queryString = 
						"SELECT C.ID_CATEGORIA, CI.NOMBRE_CATEGORIA, DESCRIPCION " + 
								"FROM CATEGORIA C  " +
								"INNER JOIN IDIOMA_CATEGORIA CI ON C.ID_CATEGORIA = CI.ID_CATEGORIA " +
								"WHERE C.ID_CATEGORIA = ?";


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

	

		
		private Categoria loadNext(ResultSet resultSet) 
				throws SQLException, DataException {

			int i = 1;
			Integer idCategoria = resultSet.getInt(i++);
			String nombreCategoria = resultSet.getString(i++);
			String descripcion = resultSet.getString(i++);

			Categoria c = new Categoria();
			c.setIdCategoria(idCategoria);
			c.setNombreCategoria(nombreCategoria);
			c.setDescripcion(descripcion);
			return c;
		}

}
//@Override
//public Results<Categoria> findAll(Connection connection, String idioma,
//	 	int startIndex, int count) 
//				throws DataException {
//
//	PreparedStatement preparedStatement = null;
//	ResultSet resultSet = null;
//
//	try {
//
//		String queryString = 
//				"SELECT C.ID_CATEGORIA, CI.NOMBRE_CATEGORIA, DESCRIPCION " + 
//				"FROM CATEGORIA C  " +
//				"INNER JOIN IDIOMA_CATEGORIA CI ON C.ID_CATEGORIA = CI.ID_CATEGORIA " +
//					"WHERE CI.ID_IDIOMA LIKE ? " +
//				"ORDER BY CI.NOMBRE_CATEGORIA ASC";
//
//		preparedStatement = connection.prepareStatement(queryString,
//				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//		
//		resultSet = preparedStatement.executeQuery();
//
//		List<Categoria> pageEntites = new ArrayList<Categoria>();                        
//		Categoria e = null;
//		int currentCount = 0;
//
//		if ((startIndex >=1) && resultSet.absolute(startIndex)) {
//			do {
//				e = loadNext(resultSet);
//				pageEntites.add(e);               	
//				currentCount++;                	
//			} while ((currentCount < count) && resultSet.next()) ;
//		}
//		
//		
//		int totalRows = JDBCUtils.getTotalRows(resultSet);
//		
//		if (logger.isDebugEnabled()) {
//			logger.debug("Total rows: "+ totalRows + ". Query: "+queryString);
//		}
//		
//		Results<Categoria> results = new Results<Categoria>(pageEntites, startIndex, totalRows);
//
//		return results;
//	
//	} catch (SQLException e) {
//		logger.error("startIndex: "+startIndex + ", count: "+count, e);
//		throw new DataException(e);
//	} finally {
//		JDBCUtils.closeResultSet(resultSet);
//		JDBCUtils.closeStatement(preparedStatement);
//	}
//}
