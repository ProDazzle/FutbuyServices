package com.fgc.futbuy.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;



import com.fgc.futbuy.service.UsuariosUtils;
import com.fgc.futbuy.service.impl.MailServiceImpl;

import com.fgc.exceptions.PasswordEncryptionUtil;
import com.fgc.futbuy.dao.DireccionDAO;
import com.fgc.futbuy.dao.UsuarioDAO;
import com.fgc.futbuy.dao.impl.DireccionDAOImpl;
import com.fgc.futbuy.dao.impl.UsuarioDAOImpl;
import com.fgc.futbuy.dao.util.ConnectionManager;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.exceptions.MailException;
import com.fgc.futbuy.model.Direccion;
import com.fgc.futbuy.model.Usuario;
import com.fgc.futbuy.service.MailService;
import com.fgc.futbuy.service.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService{
	
	UsuarioDAO usuarioDAO = null;
	DireccionDAO direccionDAO = null;
	private MailService mailService = new MailServiceImpl();
	
	public UsuarioServiceImpl() {
		usuarioDAO = new UsuarioDAOImpl();
		direccionDAO = new DireccionDAOImpl();
	}

	@Override
	public Usuario findById(Integer id) throws InstanceNotFoundException, DataException {
		Connection c = null;
		
	try {
		
		c = ConnectionManager.getConnection();
		c.setAutoCommit(true);
		
		Usuario u = usuarioDAO.findById(c,id); 
		
		return u;
		
	} catch (SQLException e){
		throw new DataException(e);
	} finally {
		JDBCUtils.closeConnection(c);
	}
		
	}
	
	@Override
	public Boolean exists(Integer id) 
			throws DataException {
				
		Connection c = null;
		
		try {
			
			c = ConnectionManager.getConnection();
			c.setAutoCommit(true);
			
			
			
			return usuarioDAO.exists(c, id);
			
		} catch (SQLException e){
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(c);
		}
		
	}

	@Override
	public Usuario create(Usuario u)
			throws  DuplicateInstanceException, DataException, MailException {
		boolean commit = false;
		Connection c = null;
		MailService mailService = null;
		
	try {
		mailService = new MailServiceImpl();
		c = ConnectionManager.getConnection();
		c.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		c.setAutoCommit(false);
		
		
		
		u = usuarioDAO.create(c,u); 
		mailService.sendMail("Bienvenido a FutBuy", "Bienvenido "+u.getNombre(), "futbuyweb@gmail.com");
		
		
		commit=true;
		
		return u;
		
	} catch (SQLException e){
		throw new DataException(e);
	} finally {
		JDBCUtils.closeConnection(c,commit);
	}
	}
	
	@Override
	public Usuario login(String email, String password) throws DataException {
		Connection connection = null;
		
	try {
		connection = ConnectionManager.getConnection();
		connection.setAutoCommit(true);
		
		
		
		if( email == null ) {
			return null;
		}
		
		if( password == null ) {
			return null;
		}
		
		Usuario u = usuarioDAO.findByEmail(connection, email);
		
		if(u == null) {
			return u;
		} 
		
		if(PasswordEncryptionUtil.checkPassword(password, u.getContrasenha())) {
			System.out.println("Usuario " + u.getEmail() + " autenticado a las " + new Date());
			return u;
		}else {
			throw new DataException("Hemos detetado un problema, comprueba los datos introducidos");
		}
		
//		if(u.getContrasenha().equals(password)) {
//			System.out.println("Usuario "+u.getEmail()+" autenticado!");
//			return u;
//		} else {
//			return null;
//		}
		
				
			} catch (SQLException e){
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
		
	}

	@Override
	public void update(Usuario u) 
			throws InstanceNotFoundException, DataException {
		
	    Connection connection = null;
        boolean commit = false;

        try {
          
            connection = ConnectionManager.getConnection();

            connection.setTransactionIsolation(
                    Connection.TRANSACTION_READ_COMMITTED);

            connection.setAutoCommit(false);

            usuarioDAO.update(connection,u);
            commit = true;
            
        } catch (SQLException e) {
            throw new DataException(e);

        } finally {
        	JDBCUtils.closeConnection(connection, commit);
        }
	}

	@Override
	public Integer delete(Integer id) throws InstanceNotFoundException, DataException {
		Connection connection = null;
        boolean commit = false;
        Integer result = null;
 

        try {
          
            connection = ConnectionManager.getConnection();

            connection.setTransactionIsolation(
                    Connection.TRANSACTION_READ_COMMITTED);

            connection.setAutoCommit(false);
            
 
            
            result = usuarioDAO.delete(connection, id);   
            
            
            commit = true;            
            return result;
            
        } catch (SQLException e) {
            throw new DataException(e);

        } finally {
        	JDBCUtils.closeConnection(connection, commit);
        }	
	}
	
	@Override
	public Usuario findByEmailToRecovery(String email) 
			throws MailException, DataException {
		Connection c = null;
		try {
			c = ConnectionManager.getConnection();
			c.setAutoCommit(true);
			Usuario e = new Usuario();
			e = usuarioDAO.findByEmail(c, email);
			setCodigo(e);
			return e;

		}catch (SQLException ex) {
			throw new DataException("Hemos encontrado alg�n problema, por favor comprueba los datos");
		} finally {   
			JDBCUtils.closeConnection(c);
		} 
	}

	public void cambiarContra(Integer codigo, String email, String psswd) throws DataException {
		Connection c = null;
		boolean commit = false;
		try {
		c = ConnectionManager.getConnection();
		Usuario u = new Usuario();
		Usuario cambio = new Usuario();
		
		UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
		
		u = usuarioDAO.findByEmail(c, email);
		c.setAutoCommit(false);
			if(codigo.equals(u.getCodigoDeRecuperacion())) {
				cambio.setEmail(u.getEmail());
				cambio.setContrasenha(psswd);
				cambio.setCodigoDeRecuperacion(0);
				
				usuarioDAO.update(c, cambio);
				commit = true;
			}else {
				throw new DataException("El c�digo introducido no coincide, compruebe el c�digo");
			}
		}catch (SQLException ex) {
			throw new DataException(ex);
		}finally {
			JDBCUtils.closeConnection(c, commit);
		}
	}
	
	@Override
	public void editDireccion(Direccion direccion, Usuario u) throws InstanceNotFoundException, DataException {
		  Connection connection = null;
	        boolean commit = false;
	        Direccion d = null;

	        try {
	          
	            connection = ConnectionManager.getConnection();

	            connection.setTransactionIsolation(
	                    Connection.TRANSACTION_READ_COMMITTED);

	            connection.setAutoCommit(false);

	            d = u.getDireccion(); 
	            
	            direccionDAO.delete(connection, d.getIdDireccion());
	            commit = true;
	            
	            u.setDireccion(direccion);
	            direccionDAO.create(connection, direccion);
	            
	        } catch (SQLException e) {
	            throw new DataException(e);

	        } finally {
	        	JDBCUtils.closeConnection(connection, commit);
	        }
	}

	public void setCodigo (Usuario u) 
			throws MailException, DataException{
		Connection c = null;
		boolean commit = false;
		try {
			c = ConnectionManager.getConnection();
			u.setCodigoDeRecuperacion(UsuariosUtils.codRecuperacion());

			String mssg = "Hola "
			+ " Introduce este c�digo para poder cambiar tu contrase�a: " + u.getCodigoDeRecuperacion();

			c.setAutoCommit(false);
			usuarioDAO.update(c, u);
			mailService.sendMail(u.getEmail(), "Restablecer contrase�a", mssg);
			commit = true;
		}catch (SQLException ex) {
			throw new DataException(ex);
		}finally {
			JDBCUtils.closeConnection(c, commit);
		}
	}

	
	

	
	
}


