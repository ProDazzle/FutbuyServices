package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fgc.futbuy.dao.LigaDAO;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Liga;


public class LigaDAOImpl implements LigaDAO {
		
	
		public LigaDAOImpl() {
		}
		
		@Override
		public Liga findById(Connection connection, Integer id) 
				throws InstanceNotFoundException, DataException {

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {          

				String queryString = 
								"SELECT ID_LIGA, NOMBRE_LIGA " + 
								"FROM LIGA  " +
								"WHERE ID_LIGA = ?";


				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				int i = 1;                
				preparedStatement.setInt(i++, id);

				// Execute query            
				resultSet = preparedStatement.executeQuery();

				Liga e = null;

				if (resultSet.next()) {
					e = loadNext(connection, resultSet);				
				} else {
					throw new InstanceNotFoundException("Liga with id " + id + 
							"not found", Liga.class.getName());
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
		public List<Liga> findAll(Connection connection) 
				throws DataException {

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {

				String queryString = 
						"SELECT ID_LIGA, NOMBRE_LIGA " + 
						"FROM LIGA  " +
						"ORDER BY NOMBRE_LIGA ASC";

				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				resultSet = preparedStatement.executeQuery();

				List<Liga> results = new ArrayList<Liga>();                        
				Liga m = null;
				while(resultSet.next()) {
					m = loadNext(connection, resultSet);
					results.add(m);               	
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
						"SELECT ID_LIGA, NOMBRE_LIGA " + 
						"FROM LIGA  " +
						"WHERE ID_LIGA = ?";


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

	

		
		private Liga loadNext(Connection connection, ResultSet resultSet) 
				throws SQLException, DataException {

			int i = 1;
			Integer idLiga = resultSet.getInt(i++);
			String nombreLiga = resultSet.getString(i++);

			Liga c = new Liga();
			c.setIdLiga(idLiga);
			c.setNombreLiga(nombreLiga);
			return c;
		}

}