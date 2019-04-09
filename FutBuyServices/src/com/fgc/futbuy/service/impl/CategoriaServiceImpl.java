package com.fgc.futbuy.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fgc.futbuy.cache.Cache;
import com.fgc.futbuy.cache.CacheManager;
import com.fgc.futbuy.dao.CategoriaDAO;
import com.fgc.futbuy.dao.impl.CategoriaDAOImpl;
import com.fgc.futbuy.dao.util.ConnectionManager;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Categoria;
import com.fgc.futbuy.service.CacheNames;
import com.fgc.futbuy.service.CategoriaService;

public class CategoriaServiceImpl implements CategoriaService {
	
	private static Logger logger = LogManager.getLogger(CategoriaServiceImpl.class);

	private CategoriaDAO dao = null;
	
	public CategoriaServiceImpl() {
		dao = new CategoriaDAOImpl();
	}
	
	@Override
	public Categoria findById(Integer id, String idioma) 
			throws InstanceNotFoundException, DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("id= "+id+" , idioma = "+idioma);
		}
		
		Cache<Integer, Categoria> cache =
				CacheManager.getInstance().getCache(CacheNames.CATEGORIAS, Integer.class, Categoria.class);
		
		Categoria c = cache.get(id);
		
		if (c!=null) {
			if(logger.isDebugEnabled()) {
				logger.debug("Acierto cache: {}", id);
			}
		}else {
			if(logger.isDebugEnabled()) {
				logger.debug("Fallo cache: {}", id);
			}
			Connection connection = null;
			
			try {
				
				connection = ConnectionManager.getConnection();
				connection.setAutoCommit(true);
				
				c = dao.findById(connection, id, idioma);	
				
			} catch (SQLException e){
				logger.error(e.getMessage(),e);
				throw new DataException(e);
			} finally {
				JDBCUtils.closeConnection(connection);
			}
			cache.put(id, c);
		}
		
		return c;
		
	}
	@Override
	public List<Categoria> findAll(String idioma) 
			throws DataException {
		if(logger.isDebugEnabled()) {
			logger.debug("Idioma = "+idioma);
		}
		
		Cache<String, List> cacheCategoria= CacheManager.getInstance().getCache(CacheNames.CATEGORIAS_LISTAS, String.class, List.class);
		
		List<Categoria> categoria=cacheCategoria.get(idioma);
		
		
		if(categoria!=null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Acierto cache: {}", idioma);
			}
		}else {
			if (logger.isDebugEnabled()) {
				logger.debug("Fallo cache: {}", idioma);
			}
			Connection c=null;
			try {
				c=ConnectionManager.getConnection();
				c.setAutoCommit(false);
				
				categoria=dao.findAll(c, idioma);
				
				cacheCategoria.put(idioma, categoria);
			
			} catch (SQLException e){
				logger.error(e.getMessage(),e);
				throw new DataException(e);
			}finally {
				JDBCUtils.closeConnection(c);
			}
		}
		return categoria;
	}
	@Override
	public Boolean exists(Integer id) 
			throws DataException {
		
		if(logger.isDebugEnabled()) {
			logger.debug("id= "+id);
		}
				
		Connection connection = null;
		
		try {
			
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);
			
			return dao.exists(connection, id);
			
		} catch (SQLException e){
			logger.error(e.getMessage(),e);
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
		
	}

}