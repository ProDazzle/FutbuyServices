package com.fgc.futbuy.dao;

import java.sql.Connection;
import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Direccion;

public interface DireccionDAO {

	public Direccion create(Connection connection, Direccion d) throws DuplicateInstanceException, DataException;
	
	public Integer delete(Connection connection, Integer id) throws InstanceNotFoundException, DataException;
	
	public Direccion findByUsuario(Connection connection, Integer id) throws DataException;
	
	public List<Direccion> findAll(Connection connection) throws DataException;
	
	public Direccion findById(Connection connection, Integer id) throws InstanceNotFoundException, DataException;
	
	public void update (Connection connection, Direccion d) throws InstanceNotFoundException, DataException;
}
