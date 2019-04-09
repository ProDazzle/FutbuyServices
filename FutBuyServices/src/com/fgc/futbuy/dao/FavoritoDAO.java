package com.fgc.futbuy.dao;

import java.sql.Connection;
import java.util.List;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.FavoritoValoracion;

public interface FavoritoDAO {
	
	public List<FavoritoValoracion> findByFavoritosUsuario(Connection c, Integer idUsuario)
		throws InstanceNotFoundException, DataException;

	public List<FavoritoValoracion> findByValoracionesUsuario(Connection c, Integer idUsuario)
		throws InstanceNotFoundException, DataException;
	
	public Double countFavoritos(Connection c, Integer idProducto)
		throws InstanceNotFoundException, DataException;
	
	public List<Double> findValoracionesByProducto(Connection c, Integer idProducto)
		throws InstanceNotFoundException, DataException;
	
	public Double mediaValoraciones(Connection c, Integer idProducto)
		throws InstanceNotFoundException, DataException;
	
	public void create(Connection c, FavoritoValoracion favorito, Integer idUsuario)
		throws DuplicateInstanceException, DataException;
	
	public void update(Connection c, FavoritoValoracion favorito, Integer idUsuario, Integer idProducto)
		throws InstanceNotFoundException, DataException;
	
	public void delete(Connection c, FavoritoValoracion favorito, Integer idUsuario, Integer idProducto)
		throws InstanceNotFoundException, DataException;
}
