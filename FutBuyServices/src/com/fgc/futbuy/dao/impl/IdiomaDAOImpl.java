package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fgc.futbuy.dao.IdiomaDAO;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Idioma;



public class IdiomaDAOImpl implements IdiomaDAO{
	
	private static Logger logger = LogManager.getLogger(IdiomaDAOImpl.class);
	
	public IdiomaDAOImpl() {
	}
	

	@Override
	public Idioma findById(Connection connection, String id) 
			throws InstanceNotFoundException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Id= "+id);
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT ID_IDIOMA, NOMBRE_IDIOMA " + 
							"FROM IDIOMA  " +
							"WHERE ID_IDIOMA = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			logger.debug(queryString);

			int i = 1;                
			preparedStatement.setString(i++, id);

			resultSet = preparedStatement.executeQuery();

			Idioma e = null;

			if (resultSet.next()) {
				e = loadNext(resultSet);				
			} else {
				throw new InstanceNotFoundException("Language with id " + id + 
						"not found", Idioma.class.getName());
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
	public List<Idioma> findAll(Connection connection,int startIndex, int count) 
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

		String queryString = 
				"SELECT ID_IDIOMA, NOMBRE_IDIOMA " +
						"FROM IDIOMA " +
						"ORDER BY NOMBRE_IDIOMA DESC";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			logger.debug(queryString);

			resultSet = preparedStatement.executeQuery();

			List<Idioma> results = new ArrayList<Idioma>();                        
			Idioma t = null;
			int currentCount = 0;

			if ((startIndex >=1) && resultSet.absolute(startIndex)) {
				do {
					t = loadNext( resultSet);
					results.add(t);               	
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
	
		
	private Idioma loadNext(ResultSet resultSet)
		throws SQLException, DataException {

			int i = 1;
			String idIdioma = resultSet.getString(i++);	                
			String nombreIdioma = resultSet.getString(i++);	                

	
			Idioma idioma = new Idioma();		
			idioma.setIdIdioma(idIdioma);
			idioma.setNombreIdioma(nombreIdioma);

			
			return idioma;
		}		

}
