package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



import com.fgc.futbuy.dao.DireccionDAO;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Direccion;


public class DireccionDAOImpl implements DireccionDAO{

	@Override
	public Direccion findByUsuario(Connection connection, Integer id) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			String sql;
			sql =  "SELECT ID_DIRECCION,ID_PROVINCIA,ID_USUARIO,NOMBRE_CIUDAD,CODIGO_POSTAL,CALLE,NUMERO,PISO,LETRA "
					+"FROM DIRECCION "
					+"WHERE ID_USUARIO = ? ";
			
			// Preparar a query
			System.out.println("Creating statement...");
			preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Establece os parámetros
			int i = 1;
			preparedStatement.setInt(i++, id);


			resultSet = preparedStatement.executeQuery();			
			//STEP 5: Extract data from result set			

			Direccion d = null;

			if (resultSet.next()) {
				d =  loadNext(connection, resultSet);			
	
			} 

			return d;
			
		} catch (SQLException ex) {
			throw new DataException(ex);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  	
	}

	@Override
	public List<Direccion> findAll(Connection connection) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			String sql;
			sql =  "SELECT ID_DIRECCION,ID_PROVINCIA,ID_USUARIO,NOMBRE_CIUDAD,CODIGO_POSTAL,CALLE,NUMERO,PISO,LETRA "
					+"FROM DIRECCION ";

			// Preparar a query
			System.out.println("Creating statement...");
			preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();			
			//STEP 5: Extract data from result set			

			List<Direccion> results = new ArrayList<Direccion>();                        
			Direccion d = null;


			while(resultSet.next()) {
				d = loadNext(connection, resultSet);
				results.add(d);               	
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
	public Direccion findById(Connection connection, Integer id) throws InstanceNotFoundException, DataException {
		Direccion d = null;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
				
			String sql;
			sql =  "SELECT ID_DIRECCION,ID_PROVINCIA,ID_USUARIO,NOMBRE_CIUDAD,CODIGO_POSTAL,CALLE,NUMERO,PISO,LETRA "
					+"FROM DIRECCION "
					+"WHERE ID_DIRECCION = ? ";

			// Preparar a query
			System.out.println("Creating statement...");
			preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Establece os parámetros
			int i = 1;
			preparedStatement.setInt(i++, id);


			resultSet = preparedStatement.executeQuery();			
			//STEP 5: Extract data from result set			

			if (resultSet.next()) {
				d =  loadNext(connection, resultSet);			
				//System.out.println("Cargado "+u);
			} else {
				throw new InstanceNotFoundException("Non se atopou direccion con id = "+id, Direccion.class.getName());
			}


		} catch (SQLException ex) {
			throw new DataException(ex);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  	

		return d;
	}


	private Direccion loadNext(Connection connection, ResultSet resultSet) throws SQLException{


		Direccion d = new Direccion();
		int i = 1;
		Integer idDireccion = resultSet.getInt(i++);
		Integer idProvincia = resultSet.getInt(i++);
		Integer idUsuario = resultSet.getInt(i++);
		String nombreCiudad = resultSet.getString(i++);
		Integer codigoPostal = resultSet.getInt(i++);
		String calle = resultSet.getString(i++);
		Integer numero = resultSet.getInt(i++);
		Integer piso = resultSet.getInt(i++);
		String letra = resultSet.getString(i++);

		d.setIdDireccion(idDireccion);
		d.setIdProvincia(idProvincia);
		d.setIdUsuario(idUsuario);
		d.setNombreCiudad(nombreCiudad);
		d.setCodigoPostal(codigoPostal);
		d.setCalle(calle);
		d.setNumero(numero);
		d.setPiso(piso);
		d.setLetra(letra);

		
		return d;

	}
	
	@Override
	public Direccion create(Connection connection, Direccion d) throws DuplicateInstanceException, DataException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {          

			String queryString = "INSERT INTO DIRECCION(ID_PROVINCIA,ID_USUARIO,NOMBRE_CIUDAD,CODIGO_POSTAL,CALLE,NUMERO,PISO,LETRA) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);

			int i = 1;     			
			
			preparedStatement.setLong(i++, d.getIdProvincia());
			preparedStatement.setLong(i++, d.getIdUsuario());
			preparedStatement.setString(i++, d.getNombreCiudad());
			preparedStatement.setInt(i++, d.getCodigoPostal());
			preparedStatement.setString(i++, d.getCalle());
			preparedStatement.setInt(i++, d.getNumero());
			preparedStatement.setInt(i++, d.getPiso());
			preparedStatement.setString(i++, d.getLetra());
			

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Direccion'");
			}

			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Integer idDireccion = resultSet.getInt(1);
				d.setIdDireccion(idDireccion);				
			} else {
				throw new DataException("Unable to fetch autogenerated primary key");
			}


			return d;
			
		} catch (SQLException ex) {
			throw new DataException(ex);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);			
		}
	}


	@Override
	public Integer delete(Connection connection, Integer id) throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;

		try {

			String queryString ="DELETE FROM DIRECCION " 
							+ "WHERE ID_DIRECCION = ? ";


			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setInt(i++, id);

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException("Non se atopou direccion: "+id,Direccion.class.getName());
			} 
			
			return removedRows;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}

	}

	@Override
	public void update(Connection connection, Direccion d) throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	

			queryString = new StringBuilder(
					" UPDATE DIRECCION" 
					);

			boolean first = true;

			if (d.getIdProvincia()!=null) {
				addUpdate(queryString, first, " ID_PROVINCIA = ? ");
				first = false;
			}

			if (d.getNombreCiudad()!=null) {
				addUpdate(queryString, first, "NOMBRE_CIUDAD = ? ");
				first = false;
			}

			if (d.getCodigoPostal()!=null) {
				addUpdate(queryString, first, " CODIGO_POSTAL = ? ");
				first = false;
			}

			if (d.getCalle()!=null) {
				addUpdate(queryString, first, " CALLE = ? ");
				first = false;
			}

			if (d.getNumero()!=null) {
				addUpdate(queryString, first, " NUMERO = ? ");
				first = false;
			}
			
			if (d.getPiso() !=null) {
				addUpdate(queryString, first, " PISO = ? ");
				first = false;
			}

			if (d.getLetra()!=null) {
				addUpdate(queryString, first, " LETRA = ? ");
				first = false;
			}


			queryString.append("WHERE ID_DIRECCION = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());


			int i = 1;
			if (d.getIdProvincia()!=null) 
				preparedStatement.setInt(i++,d.getIdProvincia());

			if (d.getNombreCiudad()!=null) 
				preparedStatement.setString(i++,d.getNombreCiudad());

			if (d.getCodigoPostal()!=null) 
				preparedStatement.setInt(i++,d.getCodigoPostal());

			if (d.getCalle()!=null) 
				preparedStatement.setString(i++,d.getCalle());

			if (d.getNumero()!=null) 
				preparedStatement.setInt(i++, d.getNumero());

			if (d.getPiso()!=null) 
				preparedStatement.setInt(i++,d.getPiso());
			
			if (d.getLetra()!=null) 
				preparedStatement.setString(i++,d.getLetra());

			preparedStatement.setLong(i++, d.getIdDireccion());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(d.getIdDireccion(), Direccion.class.getName());
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						d.getIdDireccion() + "' in table 'Direcion'");
			}     

		} catch (SQLException e) {
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}

		
}
	private void addUpdate(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" SET ": " , ").append(clause);
	}
}
