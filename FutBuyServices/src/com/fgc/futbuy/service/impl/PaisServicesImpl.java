package com.fgc.futbuy.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fgc.futbuy.dao.impl.PaisDAOImpl;
import com.fgc.futbuy.dao.util.ConnectionManager;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.dao.PaisDAO;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.model.Pais;
import com.fgc.futbuy.service.PaisService;

public class PaisServicesImpl implements PaisService{

private PaisDAO paisDAO = null;
	
	public PaisServicesImpl() {
		paisDAO = new PaisDAOImpl();
	}
	
	public List<Pais> findByIdioma (String idIdioma) 
		throws DataException{
		Connection c = null;
		try {
			c = ConnectionManager.getConnection();
			c.setAutoCommit(true);
			return paisDAO.findAll(c, idIdioma);

		}catch (SQLException ex) {
			throw new DataException(ex);
		} finally {   
			JDBCUtils.closeConnection(c);
		} 
	}
	
}
