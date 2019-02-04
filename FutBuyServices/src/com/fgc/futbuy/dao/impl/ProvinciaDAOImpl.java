package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fgc.futbuy.dao.ProvinciaDAO;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.model.Provincia;

public class ProvinciaDAOImpl implements ProvinciaDAO{

	

	@Override
	public List<Provincia> findByPaisIdioma(Connection connection, Integer idPais, String idIdioma)
					throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			// Create "preparedStatement"       
			String queryString = 
					"SELECT PI.ID_PROVINCIA, PI.NOMBRE_PROVINCIA " + 
					"FROM IDIOMA_PROVINCIA PI  " +
					"INNER JOIN PROVINCIA P "+
						"ON P.ID_PROVINCIA = PI.ID_PROVINCIA " +
					" WHERE P.ID_PAIS = ? AND PI.ID_IDIOMA = ?" +
						"ORDER BY PI.NOMBRE_PROVINCIA ASC";
	

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int i = 1;                
			preparedStatement.setInt(i++, idPais);
			preparedStatement.setString(i++, idIdioma);

			resultSet = preparedStatement.executeQuery();

			// Recupera la pagina de resultados
			List<Provincia> results = new ArrayList<Provincia>();                        
			Provincia p = null;

			while (resultSet.next()) {
				p = loadNext (connection,resultSet);
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
	
	private Provincia loadNext(Connection connection, ResultSet resultSet) throws SQLException, DataException{


		int i = 1;
		Integer idProvincia = resultSet.getInt(i++);	                
		String nombreProvincia = resultSet.getString(i++);


		Provincia p = new Provincia();		
		p.setIdProvincia(idProvincia);
		p.setNombreProvincia(nombreProvincia);
		
		return p;

	}

	

}
