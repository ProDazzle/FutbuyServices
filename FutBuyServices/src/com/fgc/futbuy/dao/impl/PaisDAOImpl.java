package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.dao.PaisDAO;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.model.Pais;

public class PaisDAOImpl implements PaisDAO{

	@Override
	public List<Pais> findAll(Connection connection, String idIdioma) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Create "preparedStatement"       
			String queryString = 
					"SELECT ID_PAIS, NOMBRE_PAIS " + 
					"FROM IDIOMA_PAIS  " +
					"WHERE ID_IDIOMA = ? "+
					"ORDER BY NOMBRE_PAIS ASC ";
			
			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int i = 1;                
			preparedStatement.setString(i++, idIdioma.toUpperCase());

			resultSet = preparedStatement.executeQuery();
			
			
			// Recupera la pagina de resultados
			List<Pais> results = new ArrayList<Pais>();                        
			Pais p = null;

			while (resultSet.next()) {
				p = loadNext (connection, resultSet);
				results.add(p);
			}
			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	private Pais loadNext(Connection connection, ResultSet resultSet)
			throws SQLException, DataException {

		int i = 1;               
		String idPais = resultSet.getString(i++);	                
		String nombrePais = resultSet.getString(i++);
		//String idIdioma = resultSet.getString(i++);
		
		Pais m = new Pais();		
		m.setIdPais(idPais);
		m.setNombrePais(nombrePais);
		//m.setIdPais(idIdioma);

		return m;
	}
	
}
