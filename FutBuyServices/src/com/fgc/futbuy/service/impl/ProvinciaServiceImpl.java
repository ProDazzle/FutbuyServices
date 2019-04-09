package com.fgc.futbuy.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fgc.futbuy.dao.ProvinciaDAO;
import com.fgc.futbuy.dao.impl.ProvinciaDAOImpl;
import com.fgc.futbuy.dao.util.ConnectionManager;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.model.Provincia;
import com.fgc.futbuy.service.ProvinciaServices;

public class ProvinciaServiceImpl implements ProvinciaServices{
	
	private static Logger logger = LogManager.getLogger(ProvinciaServiceImpl.class);

	private ProvinciaDAO provinciaDao = null;

	public ProvinciaServiceImpl() {
		provinciaDao = new ProvinciaDAOImpl();
	}
	@Override
	public List<Provincia> findByPaisIdioma (Integer idPais, String idIdioma)
			throws DataException{
		
		if(logger.isDebugEnabled()) {
			logger.debug("id= "+idPais+" , idioma = "+idIdioma);
		}
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return provinciaDao.findByPaisIdioma(connection, idPais, idIdioma);

		} catch (SQLException e){
			logger.error(e.getMessage(),e);
			throw new DataException(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(),e);
					throw new DataException(e);
				}
			}
		}

	}

}
