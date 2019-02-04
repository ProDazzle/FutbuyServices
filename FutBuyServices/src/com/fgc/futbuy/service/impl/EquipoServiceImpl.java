package com.fgc.futbuy.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fgc.futbuy.dao.EquipoDAO;
import com.fgc.futbuy.dao.impl.EquipoDAOImpl;
import com.fgc.futbuy.dao.util.ConnectionManager;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Equipo;
import com.fgc.futbuy.service.EquipoService;

public class EquipoServiceImpl implements EquipoService {

	private EquipoDAO dao = null;
	
	public EquipoServiceImpl() {
		dao = new EquipoDAOImpl();
	}
	
	public Equipo findById(Integer id) 
			throws InstanceNotFoundException, DataException {
				
		Connection connection = null;
		
		try {
			
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);
			
			return dao.findById(connection, id);	
			
		} catch (SQLException e){
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
		
	}

	public List<Equipo> findAll() 
			throws DataException {
			
		Connection connection = null;
		
		try {
			
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);
			
			return dao.findAll(connection);	
			
		} catch (SQLException e){
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
		
	}
	
	public Boolean exists(Integer id) 
			throws DataException {
				
		Connection connection = null;
		
		try {
			
			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);
			
			return dao.exists(connection, id);
			
		} catch (SQLException e){
			throw new DataException(e);
		} finally {
			JDBCUtils.closeConnection(connection);
		}
		
	}

}