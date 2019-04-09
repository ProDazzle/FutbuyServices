package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fgc.futbuy.dao.FavoritoDAO;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.FavoritoValoracion;

public class FavoritoDAOImpl implements FavoritoDAO{
	
	private static Logger logger = LogManager.getLogger(FavoritoDAOImpl.class);

	@Override
	public List<FavoritoValoracion> findByFavoritosUsuario(Connection c, Integer idUsuario) 
			throws InstanceNotFoundException, DataException{
		
		if(logger.isDebugEnabled()) {
			logger.debug("Id= "+idUsuario);
		}
		
		List<FavoritoValoracion> favoritos = new ArrayList<FavoritoValoracion>();
		FavoritoValoracion f = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			String sql;
			sql =  "SELECT ID_PRODUCTO, ID_USUARIO, VALORACION, FAVORITO "
				  +" FROM FAVORITO_VALORACION "
				  +" WHERE ID_USUARIO = ? ";
			
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			logger.debug(sql);
			
			int i = 1;
			preparedStatement.setInt(i++, idUsuario);
			
			resultSet = preparedStatement.executeQuery();	
			
			if (resultSet.next()) {				
				do{
					f = loadNext(resultSet);
					favoritos.add(f);
				}while(!resultSet.isLast()); 
			} else {
				throw new InstanceNotFoundException(idUsuario, "FavoritoDAOImpl.findByFavoritosUsuario");
			}				
			return favoritos;
		} 
		catch (SQLException ex) {
			logger.error(ex.getMessage(),ex);
			throw new DataException(ex);
		} 
		finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  	
	}

	@Override
	public List<FavoritoValoracion> findByValoracionesUsuario(Connection c, Integer idUsuario) 
			throws InstanceNotFoundException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Id= "+idUsuario);
		}
		
		List<FavoritoValoracion> favoritos = new ArrayList<FavoritoValoracion>();
		FavoritoValoracion f = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			String sql;
			sql =  "SELECT ID_PRODUCTO, ID_USUARIO, VALORACION, FAVORITO "
				  +" FROM FAVORITO_VALORACION "
				  +" WHERE ID_USUARIO = ? ";
			
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			logger.debug(sql);
			
			int i = 1;
			preparedStatement.setInt(i++, idUsuario);
			
			resultSet = preparedStatement.executeQuery();	
			
			if (resultSet.next()) {				
				do{
					f = loadNext(resultSet);
					favoritos.add(f);
				}while(!resultSet.isLast());
			} else {
				throw new InstanceNotFoundException(idUsuario,"FavoritoDAOImpl.findValoracionesUsuario");
			}				
			return favoritos;
		} 
		catch (SQLException ex) {
			logger.error(ex.getMessage(),ex);
			throw new DataException(ex);
		} 
		finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  	
	}

	@Override
	public Double countFavoritos(Connection c, Integer idProducto) throws InstanceNotFoundException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Id= "+idProducto);
		}
		
		Double f = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			String sql;
			sql =  "SELECT COUNT(FAVORITO) NUMERO "
				  +" FROM FAVORITO_VALORACION "
				  +" WHERE ID_PRODUCTO = ? AND FAVORITO IS NOT NULL ";
			
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			logger.debug(sql);
			
			int i = 1;
			preparedStatement.setInt(i++, idProducto);
			
			resultSet = preparedStatement.executeQuery();	
			
			if (resultSet.next()) {				
				f = loadNextP(resultSet);
			} else {
				throw new InstanceNotFoundException(idProducto, "FavoritoDAOImpl.countFavoritos");
			}				
			return f;
		} 
		catch (SQLException ex) {
			logger.error(ex.getMessage(),ex);
			throw new DataException(ex);
		} 
		finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  	
	}

	@Override
	public List<Double> findValoracionesByProducto(Connection c, Integer idProducto) 
			throws InstanceNotFoundException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Id= "+idProducto);
		}
		
		Double f = null;
		List<Double> fs = new ArrayList<Double>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			String sql;
			sql =  "SELECT COUNT(VALORACION) NUMERO "
				  +" FROM FAVORITO_VALORACION "
				  +" WHERE ID_PRODUCTO = ? AND VALORACION IS NOT NULL ";
			
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			logger.debug(sql);
			
			int i = 1;
			preparedStatement.setInt(i++, idProducto);
			
			resultSet = preparedStatement.executeQuery();	
			
			if (resultSet.next()) {				
				while(resultSet.next()) {	
					f = loadNextP(resultSet);
					fs.add(f);
				}
			} else {
				throw new InstanceNotFoundException(idProducto, "FavoritoDAOImpl.findValoracionesByProducto");
			}				
			return fs;
		} 
		catch (SQLException ex) {
			logger.error(ex.getMessage(),ex);
			throw new DataException(ex);
		} 
		finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  	
	}

	@Override
	public Double mediaValoraciones(Connection c, Integer idProducto) throws InstanceNotFoundException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Id= "+idProducto);
		}
		
		Double f = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			String sql;
			sql =  "SELECT AVG(VALORACION) NUMERO "
				  +" FROM FAVORITO_VALORACION "
				  +" WHERE ID_PRODUCTO = ? AND VALORACION IS NOT NULL ";
			
			preparedStatement = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			logger.debug(sql);
			
			int i = 1;
			preparedStatement.setInt(i++, idProducto);
			
			resultSet = preparedStatement.executeQuery();	
			
			if (resultSet.next()) {				
					f = loadNextP(resultSet);
			} else {
				throw new InstanceNotFoundException(idProducto, "FavoritoDAOImpl.mediaValoraciones");
			}				
			return f;
		} 
		catch (SQLException ex) {
			logger.error(ex.getMessage(),ex);
			throw new DataException(ex);
		} 
		finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  	
	}

	@Override
	public void create(Connection c, FavoritoValoracion favorito, Integer idUsuario) throws DuplicateInstanceException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Id= "+idUsuario+" , Favorito = "+favorito);
		}
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			String sql;
			sql =  "INSERT INTO FAVORITO_VALORACION (ID_PRODUCTO, ID_USUARIO, VALORACION, FAVORITO "
				  +" VALUES (?, ?, ?, ?) ";
			
			preparedStatement = c.prepareStatement(sql);
			
			logger.debug(sql);
			
			int i = 1;
			preparedStatement.setInt(i++, favorito.getIdProducto());
			preparedStatement.setInt(i++, idUsuario);
			preparedStatement.setDouble(i++, favorito.getValoracion());
			preparedStatement.setBoolean(i++, favorito.getFavorito());
			
			int insertedRows = preparedStatement.executeUpdate();	
			
			if(insertedRows == 0) {
				throw new DuplicateInstanceException(favorito, "FavoritoDAOImpl.create");
			}
		} 
		catch (SQLException ex) {
			logger.error(ex.getMessage(),ex);
			throw new DataException(ex);
		} 
		finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  	
	}

	@Override
	public void update(Connection c, FavoritoValoracion favorito, Integer idUsuario, Integer idProducto) 
			throws InstanceNotFoundException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Id= "+idUsuario+" , IdProducto= "+idProducto+" , Favorito = "+favorito);
		}
		
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			String sql;
			sql =  "UPDATE `FAVORITO_VALORACION` "
					+"SET `VALORACION` = ?, `FAVORITO` = ? "
					+" WHERE ID_PRODUCTO = ? AND ID_USUARIO = ?";
			
			preparedStatement = c.prepareStatement(sql);
			
			logger.debug(sql);
			
			int i = 1;
			preparedStatement.setDouble(i++, favorito.getValoracion());
			preparedStatement.setBoolean(i++, favorito.getFavorito());
			preparedStatement.setInt(i++, idProducto);
			preparedStatement.setInt(i++, idUsuario);	
			
			int insertedRows = preparedStatement.executeUpdate();	
			
			if(insertedRows == 0) {
				throw new InstanceNotFoundException(favorito, "FavoritoDAOImpl.update");
			}
			
			resultSet = preparedStatement.getResultSet();
			if (resultSet.next()) {	
				favorito = loadNext(resultSet);
			}
		} 
		catch (SQLException ex) {
			logger.error(ex.getMessage(),ex);
			throw new DataException(ex);
		} 
		finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  	
	}

	@Override
	public void delete(Connection c, FavoritoValoracion favorito, Integer idUsuario, Integer idProducto) 
			throws InstanceNotFoundException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Id= "+idUsuario+" , IdProducto= "+idProducto+" , Favorito = "+favorito);
		}
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			String sql;
			sql =  "DELETE FROM `FAVORITO_VALORACION`"
				  +" WHERE ID_PRODUCTO = ? AND ID_USUARIO = ?";
			
			preparedStatement = c.prepareStatement(sql);
			
			logger.debug(sql);
			
			int i = 1;
			preparedStatement.setInt(i++, idProducto);
			preparedStatement.setInt(i++, idUsuario);
			int deletedRows = preparedStatement.executeUpdate();
			
			if(deletedRows == 0) {
				throw new InstanceNotFoundException(favorito, "FavoritoDAOImpl.delete");
			}
		} 
		catch (SQLException ex) {
			logger.error(ex.getMessage(),ex);
			throw new DataException(ex);
		} 
		finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  	
	}
	

	private FavoritoValoracion loadNext(ResultSet resultSet) throws SQLException{
		
		FavoritoValoracion f = new FavoritoValoracion();
		
		int i = 1;
		Integer idProducto = resultSet.getInt(i++);
		Integer idUsuario = resultSet.getInt(i++);
		Double valoracion = resultSet.getDouble(i++);
		Boolean favorito = resultSet.getBoolean(i++);

		f.setIdProducto(idProducto);
		f.setIdUsuario(idUsuario);
		f.setValoracion(valoracion);
		f.setFavorito(favorito);
		
		return f;
	}
	
	private Double loadNextP(ResultSet resultSet) throws SQLException{
		
		int i = 1;
		Double f = resultSet.getDouble(i++);
		
		return f;
		
	}

}
