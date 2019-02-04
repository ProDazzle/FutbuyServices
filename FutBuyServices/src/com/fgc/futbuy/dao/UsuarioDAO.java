package com.fgc.futbuy.dao;

import java.sql.Connection;
import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Usuario;

public interface UsuarioDAO {

	public Usuario findById(Connection connection, Integer id) throws InstanceNotFoundException, DataException;
	
	public Usuario findByEmail(Connection connection, String email) throws DataException;

	public List<Usuario> findAll(Connection connection) throws DataException;	
	
	public Usuario create(Connection connection, Usuario u) throws DuplicateInstanceException, DataException;

	public void update(Connection connection, Usuario u) throws InstanceNotFoundException, DataException;

	public Integer delete(Connection connection, Integer id) throws InstanceNotFoundException, DataException;

	public Boolean exists(Connection connection, Integer id) throws DataException;
}
