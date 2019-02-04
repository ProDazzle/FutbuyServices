package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import com.fgc.futbuy.dao.JugadorDAO;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Jugador;


public class JugadorDAOImpl implements JugadorDAO {
		

	
		public JugadorDAOImpl() {
		}
		
		@Override
		public Jugador findById(Connection connection, Integer id) 
				throws InstanceNotFoundException, DataException {

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {          

				String queryString = 
								"SELECT ID_JUGADOR, NOMBRE_NUMERO_JUGADOR " + 
								"FROM JUGADOR  " +
								"WHERE ID_JUGADOR = ?";


				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				int i = 1;                
				preparedStatement.setInt(i++, id);

				// Execute query            
				resultSet = preparedStatement.executeQuery();

				Jugador e = null;

				if (resultSet.next()) {
					e = loadNext(connection, resultSet);				
				} else {
					throw new InstanceNotFoundException("Categories with id " + id + 
							"not found", Jugador.class.getName());
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
		public List<Jugador> findAll(Connection connection) 
				throws DataException {

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {

				String queryString = 
						"SELECT ID_JUGADOR, NOMBRE_NUMERO_JUGADOR " + 
						"FROM JUGADOR  " +
						"ORDER BY NOMBRE_ ASC";

				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				resultSet = preparedStatement.executeQuery();

				List<Jugador> results = new ArrayList<Jugador>();                        
				Jugador m = null;
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
						"SELECT ID_JUGADOR, NOMBRE_NUMERO_JUGADOR " + 
								"FROM JUGADOR  " +
								"WHERE ID_JUGADOR = ?";


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

		public List<Jugador> findByProducto(Connection connection, Integer idProducto) 
				throws DataException {
			
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {

				String queryString = (
										"SELECT J.ID_JUGADOR, J.NOMBRE_NUMERO_JUGADOR " +
										"FROM JUGADOR J " +
										"INNER JOIN PRODUCTO_JUGADOR PJ ON J.ID_JUGADOR=PJ.ID_JUGADOR " +
										"WHERE PJ.ID_PRODUCTO = ? ");


				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				int i = 1;
				preparedStatement.setInt(i++, idProducto);

				resultSet = preparedStatement.executeQuery();

				// Recupera la pagina de resultados
				List<Jugador> results = new ArrayList<Jugador>();                        
				Jugador e = null;
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

		
		private Jugador loadNext(Connection connection, ResultSet resultSet) 
				throws SQLException, DataException {

			int i = 1;
			Integer idJugador = resultSet.getInt(i++);
			String nombreNumeroJugador = resultSet.getString(i++);

			Jugador c = new Jugador();
			c.setIdJugador(idJugador);
			c.setNombreNumeroJugador(nombreNumeroJugador);

			return c;
		}

		

}
