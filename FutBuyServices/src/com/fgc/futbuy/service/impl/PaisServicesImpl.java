package com.fgc.futbuy.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fgc.futbuy.dao.impl.PaisDAOImpl;
import com.fgc.futbuy.dao.util.ConnectionManager;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.dao.PaisDAO;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.model.Pais;
import com.fgc.futbuy.service.PaisService;

public class PaisServicesImpl implements PaisService{
	
	private static Logger logger = LogManager.getLogger(PaisServicesImpl.class);

private PaisDAO paisDAO = null;
	
	public PaisServicesImpl() {
		paisDAO = new PaisDAOImpl();
	}
	@Override
	public List<Pais> findByIdioma (String idIdioma) 
		throws DataException{
		
		if(logger.isDebugEnabled()) {
			logger.debug("IdIdioma= "+idIdioma);
		}
		Connection c = null;
		try {
			c = ConnectionManager.getConnection();
			c.setAutoCommit(true);
			return paisDAO.findAll(c, idIdioma);

		}catch (SQLException ex) {
			logger.error(ex.getMessage(),ex);
			throw new DataException(ex);
		} finally {   
			JDBCUtils.closeConnection(c);
		} 
	}
	
}
