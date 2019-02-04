package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fgc.futbuy.dao.EquipoDAO;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Equipo;


public class EquipoDAOImpl implements EquipoDAO {
		
	
		public EquipoDAOImpl() {
		}
		
		@Override
		public Equipo findById(Connection connection, Integer id) 
				throws InstanceNotFoundException, DataException {

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {          

				String queryString = 
								"SELECT ID_EQUIPO, NOMBRE_EQUIPO " + 
								"FROM EQUIPO  " +
								"WHERE ID_EQUIPO = ?";


				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				int i = 1;                
				preparedStatement.setInt(i++, id);

				// Execute query            
				resultSet = preparedStatement.executeQuery();

				Equipo e = null;

				if (resultSet.next()) {
					e = loadNext(connection, resultSet);				
				} else {
					throw new InstanceNotFoundException("Equipo with id " + id + 
							"not found", Equipo.class.getName());
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
		public List<Equipo> findAll(Connection connection) 
				throws DataException {

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {

				String queryString = 
						"SELECT ID_EQUIPO, NOMBRE_EQUIPO " + 
						"FROM EQUIPO  " +
						"ORDER BY NOMBRE_EQUIPO ASC";

				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				resultSet = preparedStatement.executeQuery();

				List<Equipo> results = new ArrayList<Equipo>();                        
				Equipo m = null;
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
						"SELECT ID_EQUIPO, NOMBRE_EQUIPO " + 
						"FROM EQUIPO  " +
						"WHERE ID_EQUIPO = ?";


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

	

		
		private Equipo loadNext(Connection connection, ResultSet resultSet) 
				throws SQLException, DataException {

			int i = 1;
			Integer idEquipo = resultSet.getInt(i++);
			String nombreEquipo = resultSet.getString(i++);

			Equipo c = new Equipo();
			c.setIdEquipo(idEquipo);
			c.setNombreEquipo(nombreEquipo);
			return c;
		}

}
