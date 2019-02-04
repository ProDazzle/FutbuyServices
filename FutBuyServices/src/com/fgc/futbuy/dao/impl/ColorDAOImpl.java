package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import com.fgc.futbuy.dao.ColorDAO;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Color;

public class ColorDAOImpl implements ColorDAO {
		

	
		public ColorDAOImpl() {
		}
		
		@Override
		public Color findById(Connection connection, Integer id) 
				throws InstanceNotFoundException, DataException {

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {          

				String queryString = 
								"SELECT C.ID_COLOR, CI.NOMBRE_COLOR " + 
								"FROM COLOR C  " +
								"INNER JOIN IDIOMA_COLOR CI ON C.ID_COLOR = CI.ID_COLOR " +
								"WHERE C.ID_COLOR = ?";


				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				int i = 1;                
				preparedStatement.setInt(i++, id);

				// Execute query            
				resultSet = preparedStatement.executeQuery();

				Color e = null;

				if (resultSet.next()) {
					e = loadNext(connection, resultSet);				
				} else {
					throw new InstanceNotFoundException("Categories with id " + id + 
							"not found", Color.class.getName());
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
		public List<Color> findAll(Connection connection, String idioma) 
				throws DataException {

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {

			String queryString = 
					"SELECT C.ID_COLOR, CI.NOMBRE_COLOR " + 
							"FROM COLOR C  " +
							"INNER JOIN IDIOMA_COLOR CI ON C.ID_COLOR = CI.ID_COLOR " +
							"WHERE CI.ID_IDIOMA LIKE ? ";

				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				int i = 1;                
				preparedStatement.setString(i++, idioma);
				
				resultSet = preparedStatement.executeQuery();

				List<Color> results = new ArrayList<Color>();                        
				Color t = null;
				while(resultSet.next()) {
					t = loadNext(connection, resultSet);
					results.add(t);               	
				}

				return results;

			} catch (SQLException ex) {
				throw new DataException(ex);
			} finally {            
				JDBCUtils.closeResultSet(resultSet);
				JDBCUtils.closeStatement(preparedStatement);
			}  	
		}
		
		@Override
		public Boolean exists(Connection connection, Integer id) 
				throws DataException {
			
			boolean exist = false;

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {

				String queryString = 
						"SELECT C.ID_COLOR, CI.NOMBRE_COLOR " + 
								"FROM COLOR C  " +
								"INNER JOIN IDIOMA_COLOR CI ON C.ID_COLOR = CI.ID_COLOR " +
								"WHERE C.ID_COLOR = ?";


				preparedStatement = connection.prepareStatement(queryString);

				int i = 1;
				preparedStatement.setInt(i++, id);

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

		public List<Color> findByProducto(Connection connection, Integer idProducto, String idioma) 
				throws DataException {
			
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {

				String queryString = (
										"SELECT PC.ID_COLOR, CI.NOMBRE_COLOR " +
										"FROM PRODUCTO_COLOR PC " +
										"INNER JOIN COLOR C ON PC.ID_COLOR=C.ID_COLOR " +
										"INNER JOIN IDIOMA_COLOR CI ON CI.ID_COLOR=C.ID_COLOR " +
										"WHERE PC.ID_COLOR = ? AND CI.ID_IDIOMA= ? ");


				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				int i = 1;
				preparedStatement.setInt(i++, idProducto);
				preparedStatement.setString(i++, idioma);

				resultSet = preparedStatement.executeQuery();

				// Recupera la pagina de resultados
				List<Color> results = new ArrayList<Color>();                        
				Color e = null;
				while(resultSet.next()) {
					e = loadNext(connection, resultSet);
					results.add(e);               	
				}

				return results;

			} catch (SQLException ex) {
				throw new DataException(ex);
			} finally {            
				JDBCUtils.closeResultSet(resultSet);
				JDBCUtils.closeStatement(preparedStatement);
			}  	
		}

		
		private Color loadNext(Connection connection, ResultSet resultSet) 
				throws SQLException, DataException {

			int i = 1;
			Integer idColor = resultSet.getInt(i++);
			String nombreColor = resultSet.getString(i++);

			Color c = new Color();
			c.setIdColor(idColor);
			c.setNombreColor(nombreColor);

			return c;
		}

}


