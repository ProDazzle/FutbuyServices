package com.fgc.futbuy.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fgc.futbuy.dao.ProvinciaDAO;
import com.fgc.futbuy.dao.impl.ProvinciaDAOImpl;
import com.fgc.futbuy.dao.util.ConnectionManager;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.model.Provincia;
import com.fgc.futbuy.service.ProvinciaServices;

public class ProvinciaServiceImpl implements ProvinciaServices{

	private ProvinciaDAO provinciaDao = null;

	public ProvinciaServiceImpl() {
		provinciaDao = new ProvinciaDAOImpl();
	}

	public List<Provincia> findByPaisIdioma (Integer idPais, String idIdioma)
			throws DataException{
		Connection connection = null;

		try {

			connection = ConnectionManager.getConnection();
			connection.setAutoCommit(true);

			return provinciaDao.findByPaisIdioma(connection, idPais, idIdioma);

		} catch (SQLException e){
			throw new DataException(e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new DataException(e);
				}
			}
		}

	}

}
