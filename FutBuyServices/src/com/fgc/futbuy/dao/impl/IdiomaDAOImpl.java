package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fgc.futbuy.dao.IdiomaDAO;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Idioma;



public class IdiomaDAOImpl implements IdiomaDAO{
	

	
	public IdiomaDAOImpl() {
	}
	

	@Override
	public Idioma findById(Connection connection, String id) 
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {          
			String queryString = 
					"SELECT ID_IDIOMA, NOMBRE_IDIOMA " + 
							"FROM IDIOMA  " +
							"WHERE ID_IDIOMA = ? ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setString(i++, id);

			resultSet = preparedStatement.executeQuery();

			Idioma e = null;

			if (resultSet.next()) {
				e = loadNext(connection, resultSet);				
			} else {
				throw new InstanceNotFoundException("Language with id " + id + 
						"not found", Idioma.class.getName());
			}

			return e;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  
	}

	@Override
	public List<Idioma> findAll(Connection connection) 
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

			resultSet = preparedStatement.executeQuery();

			List<Idioma> results = new ArrayList<Idioma>();                        
			Idioma t = null;
			while(resultSet.next()) {
				t = loadNext(connection, resultSet);
				results.add(t);               	
			}

			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}
	
		
	private Idioma loadNext(Connection connection, ResultSet resultSet)
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
