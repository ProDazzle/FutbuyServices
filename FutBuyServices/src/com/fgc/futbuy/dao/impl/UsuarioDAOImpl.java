package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fgc.exceptions.PasswordEncryptionUtil;
import com.fgc.futbuy.dao.DireccionDAO;
import com.fgc.futbuy.dao.UsuarioDAO;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Direccion;
import com.fgc.futbuy.model.Usuario;


public class UsuarioDAOImpl implements UsuarioDAO{

	private DireccionDAO direccionDAO = null;

	public UsuarioDAOImpl () {
		direccionDAO = new DireccionDAOImpl();

	}

	@Override
	public Usuario findById(Connection connection, Integer id) 
			throws InstanceNotFoundException, DataException{
		Usuario u = null;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			String sql;
			sql =  "SELECT ID_USUARIO, EMAIL, CONTRASENHA, NOMBRE_USUARIO, NOMBRE, APELLIDO1, APELLIDO2,  FECHA_NACIMIENTO, GENERO "
					+"FROM USUARIO "
					+"WHERE ID_USUARIO = ? ";

			// Preparar a query
			System.out.println("Creating statement...");
			preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Establece os parámetros
			int i = 1;
			preparedStatement.setInt(i++, id);


			resultSet = preparedStatement.executeQuery();			
			//STEP 5: Extract data from result set			

			if (resultSet.next()) {
				u =  loadNext(connection, resultSet);			
				//System.out.println("Cargado "+u);
			} else {
				throw new InstanceNotFoundException("Non se atopou usuario con id = "+id, Usuario.class.getName());
			}

			return u;

		} catch (SQLException ex) {
			throw new DataException(ex);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  	


	}

	@Override
	public Usuario findByEmail(Connection connection, String email) throws DataException {

		Usuario u = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{

			String sql;
			sql =    "SELECT ID_USUARIO, EMAIL, CONTRASENHA, NOMBRE_USUARIO, NOMBRE, APELLIDO1, APELLIDO2,  FECHA_NACIMIENTO, GENERO " 
					+" FROM USUARIO "
					+" WHERE "
					+"	UPPER(EMAIL) LIKE ?";


			// Preparar a query
			System.out.println("Creating statement...");
			preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			// Establece os parámetros
			int i = 1;
			preparedStatement.setString(i++, "%"+email.toUpperCase()+"%");

			resultSet = preparedStatement.executeQuery();			
			//STEP 5: Extract data from result set			

			if (resultSet.next()) {
				u =  loadNext(connection, resultSet);			

			} 

			return u;

		} catch (SQLException ex) {
			throw new DataException(ex);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public List<Usuario> findAll(Connection connection)
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			String sql;
			sql =  "SELECT ID_USUARIO, EMAIL, CONTRASENHA, NOMBRE_USUARIO, NOMBRE, APELLIDO1, APELLIDO2,  FECHA_NACIMIENTO, GENERO "
					+" FROM USUARIO ";

			// Preparar a query
			System.out.println("Creating statement...");
			preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = preparedStatement.executeQuery();			
			//STEP 5: Extract data from result set			

			List<Usuario> results = new ArrayList<Usuario>();                        
			Usuario u = null;


			while(resultSet.next()) {
				u = loadNext(connection, resultSet);
				results.add(u);               	
			}

			return results;

		} catch (SQLException ex) {
			throw new DataException(ex);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  	
	}


	private Usuario loadNext(Connection connection, ResultSet resultSet) throws SQLException, DataException{


		Usuario u = new Usuario();
		int i = 1;
		Integer idUsuario = resultSet.getInt(i++);
		String email = resultSet.getString(i++);
		String contrasenha = resultSet.getString(i++);
		String nombreUsuario = resultSet.getString(i++);
		String nombre = resultSet.getString(i++);
		String apellido1 = resultSet.getString(i++);
		String apellido2 = resultSet.getString(i++); 
		Date fechaNacimiento = resultSet.getDate(i++);
		String genero = resultSet.getString(i++);

		u.setIdUsuario(idUsuario);
		u.setEmail(email);
		u.setContrasenha(contrasenha);
		u.setNombreUsuario(nombreUsuario);
		u.setNombre(nombre);
		u.setApellido1(apellido1);
		u.setApellido2(apellido2);
		u.setFechaNacimiento(fechaNacimiento);
		u.setGenero(genero);

		Direccion d = direccionDAO.findByUsuario(connection, idUsuario);
		u.setDireccion(d);

		return u;

	}

	@Override
	public Usuario create(Connection connection, Usuario u)
			throws DuplicateInstanceException, DataException{

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {          

			String queryString = "INSERT INTO USUARIO(EMAIL,CONTRASENHA,NOMBRE_USUARIO,NOMBRE,APELLIDO1,APELLIDO2,FECHA_NACIMIENTO,GENERO) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString, Statement.RETURN_GENERATED_KEYS);

			int i = 1;     			
			preparedStatement.setString(i++, u.getEmail());
			preparedStatement.setString(i++, PasswordEncryptionUtil.encryptPassword(u.getContrasenha()));
			preparedStatement.setString(i++, u.getNombreUsuario());
			preparedStatement.setString(i++, u.getNombre());
			preparedStatement.setString(i++, u.getApellido1());
			preparedStatement.setString(i++, u.getApellido2());
			preparedStatement.setDate(i++, new java.sql.Date(u.getFechaNacimiento().getTime()));
			preparedStatement.setString(i++, u.getGenero());

			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Usuarios'");
			}

			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Integer idUsuario = resultSet.getInt(1);
				u.setIdUsuario(idUsuario);	

				//Creamos a direccion do usuario

				Direccion d = u.getDireccion();
				d.setIdUsuario(idUsuario);
				direccionDAO.create(connection, d);
				u.setDireccion(d);

			} else {
				throw new DataException("Unable to fetch autogenerated primary key");
			}


			return u;					

		} catch (SQLException ex) {
			throw new DataException(ex);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);			
		}

	}

	@Override
	public void update(Connection connection, Usuario u) 
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	

			queryString = new StringBuilder(
					" UPDATE USUARIO" 
					);

			boolean first = true;

			if (u.getNombreUsuario()!=null) {
				addUpdate(queryString, first, " NOMBRE_USUARIO = ? ");
				first = false;
			}

			if (u.getNombre()!=null) {
				addUpdate(queryString, first, "NOMBRE = ? ");
				first = false;
			}

			if (u.getApellido1()!=null) {
				addUpdate(queryString, first, " APELLIDO1 = ? ");
				first = false;
			}

			if (u.getApellido2()!=null) {
				addUpdate(queryString, first, " APELLIDO2 = ? ");
				first = false;
			}

			if (u.getFechaNacimiento()!=null) {
				addUpdate(queryString, first, " FECHA_NACIMIENTO = ? ");
				first = false;
			}

			if (u.getGenero()!=null) {
				addUpdate(queryString, first, " GENERO = ? ");
				first = false;
			}


			queryString.append("WHERE ID_USUARIO = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());


			int i = 1;
			if (u.getNombreUsuario()!=null) 
				preparedStatement.setString(i++,u.getNombreUsuario());

			if (u.getNombre()!=null) 
				preparedStatement.setString(i++,u.getNombre());

			if (u.getApellido1()!=null) 
				preparedStatement.setString(i++,u.getApellido1());

			if (u.getApellido2()!=null) 
				preparedStatement.setString(i++,u.getApellido2());

			if (u.getFechaNacimiento()!=null) 
				preparedStatement.setDate(i++, new java.sql.Date(u.getFechaNacimiento().getTime()));

			if (u.getGenero()!=null) 
				preparedStatement.setString(i++,u.getGenero());

			preparedStatement.setLong(i++, u.getIdUsuario());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(u.getIdUsuario(), Usuario.class.getName());
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						u.getIdUsuario() + "' in table 'Usuario'");
			}     

		} catch (SQLException e) {
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}              		
	}

	@Override
	public Integer delete(Connection connection, Integer id)
			throws InstanceNotFoundException, DataException{

		PreparedStatement preparedStatement = null;

		try {

			//Temos que borrar a direccion do usuario
			Direccion d = direccionDAO.findByUsuario(connection, id);
			direccionDAO.delete(connection, d.getIdUsuario());


			String queryString =	
					"DELETE FROM USUARIO " 
							+ "WHERE ID_USUARIO = ? ";


			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setInt(i++, id);

			int removedRows = preparedStatement.executeUpdate();

			return removedRows;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
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
					"SELECT ID_USUARIO, EMAIL, NOMBRE_USUARIO, NOMBRE, APELLIDO1, APELLIDO2 " + 
							"FROM USUARIO  " +
							"WHERE ID_USUARIO = ? ";

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

	private void addUpdate(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first?" SET ": " , ").append(clause);
	}

	
}
