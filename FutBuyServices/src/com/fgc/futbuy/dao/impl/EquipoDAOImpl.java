package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fgc.futbuy.dao.EquipoDAO;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Equipo;


public class EquipoDAOImpl implements EquipoDAO {
		
	private static Logger logger = LogManager.getLogger(EquipoDAOImpl.class);
	
		public EquipoDAOImpl() {
		}
		
		@Override
		public Equipo findById(Connection connection, Integer id) 
				throws InstanceNotFoundException, DataException {
			
			if(logger.isDebugEnabled()) {
				logger.debug("Id= "+id);
			}

			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {          

				String queryString = 
								"SELECT ID_EQUIPO, NOMBRE_EQUIPO " + 
								"FROM EQUIPO  " +
								"WHERE ID_EQUIPO = ?";


				preparedStatement = connection.prepareStatement(queryString,
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				logger.debug(queryString);

				int i = 1;                
				preparedStatement.setInt(i++, id);

				// Execute query            
				resultSet = preparedStatement.executeQuery();

				Equipo e = null;

				if (resultSet.next()) {
					e = loadNext(resultSet);				
				} else {
					throw new InstanceNotFoundException("Equipo with id " + id + 
							"not found", Equipo.class.getName());
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
		public List<Equipo> findAll(Connection connection,int startIndex, int count) 
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
				
				logger.debug(queryString);

				resultSet = preparedStatement.executeQuery();

				List<Equipo> results = new ArrayList<Equipo>();                        
				Equipo m = null;
				int currentCount = 0;

				if ((startIndex >=1) && resultSet.absolute(startIndex)) {
					do {
						m = loadNext( resultSet);
						results.add(m);               	
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
						"SELECT ID_EQUIPO, NOMBRE_EQUIPO " + 
						"FROM EQUIPO  " +
						"WHERE ID_EQUIPO = ?";


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

	

		
		private Equipo loadNext(ResultSet resultSet) 
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
