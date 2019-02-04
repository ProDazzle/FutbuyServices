/**
 * 
 */
package com.fgc.futbuy.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



import com.fgc.futbuy.dao.ProductoDAO;
import com.fgc.futbuy.dao.util.JDBCUtils;
import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.DuplicateInstanceException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Talla;
import com.fgc.futbuy.model.Color;
import com.fgc.futbuy.model.Jugador;
import com.fgc.futbuy.model.Producto;
import com.fgc.futbuy.service.TallaService;
import com.fgc.futbuy.service.ColorService;
import com.fgc.futbuy.service.JugadorService;
import com.fgc.futbuy.service.ProductoCriteria;
import com.fgc.futbuy.service.impl.TallaServiceImpl;
import com.fgc.futbuy.service.impl.ColorServiceImpl;
import com.fgc.futbuy.service.impl.JugadorServiceImpl;


public class ProductoDAOImpl implements ProductoDAO{

	public ProductoDAOImpl() {
	}
	public Boolean exists(Connection connection, Integer id) 
			throws DataException {
		boolean exist = false;

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					" SELECT P.ID_PRODUCTO, P.ID_CATEGORIA, P.ID_OFERTA, PI.NOMBRE_PRODUCTO, PI.DESCRIPCION, P.PRECIO, P.UNIDADES_STOCK, P.VALORACION_MEDIA, P.ID_LIGA, P.ID_EQUIPO, P.ID_MARCA " + 
							" FROM PRODUCTO P " +
							" INNER JOIN IDIOMA_PRODUCTO PI ON P.ID_PRODUCTO = PI.ID_PRODUCTO "+
							" WHERE P.ID_PRODUCTO = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setInt(i++, id);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				exist = true;
			}

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}

		return exist;
	}


	@Override
	public Integer countAll(Connection connection) 
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			String queryString = 
					" SELECT COUNT(*) "
							+ " FROM Producto";

			preparedStatement = connection.prepareStatement(queryString);

			resultSet = preparedStatement.executeQuery();

			int i=1;
			if (resultSet.next()) {
				return resultSet.getInt(i++);
			} else {
				throw new DataException("Unexpected condition trying to retrieve count value");
			}

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}	    	 
	}

	@Override
	public Producto findById(Connection connection, Integer id, String idioma) 
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		TallaService tallaService = new TallaServiceImpl();
		ColorService colorService = new ColorServiceImpl();
		JugadorService jugadorService = new JugadorServiceImpl();

		try {          

			String queryString = 
					" SELECT P.ID_PRODUCTO, P.ID_CATEGORIA, P.ID_OFERTA, PI.NOMBRE_PRODUCTO, PI.DESCRIPCION, P.PRECIO, P.UNIDADES_STOCK, P.VALORACION_MEDIA, P.ID_LIGA, P.ID_EQUIPO, P.ID_MARCA " + 
							" FROM PRODUCTO P " +
							" INNER JOIN IDIOMA_PRODUCTO PI ON P.ID_PRODUCTO = PI.ID_PRODUCTO "+
							" WHERE P.ID_PRODUCTO = ? AND PI.ID_IDIOMA = ? ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;                
			preparedStatement.setInt(i++, id);
			preparedStatement.setString(i++, idioma);


			// Execute query      
			resultSet = preparedStatement.executeQuery();

			Producto e = null;

			if (resultSet.next()) {
				e = loadNext(connection, resultSet);				
			} else {
				throw new InstanceNotFoundException("Products with id " + id + 
						"not found", Producto.class.getName());
			}
			//Buscamos las categorias del producto
			List<Talla> tallas = tallaService.findByProducto(id, idioma);
			if (!tallas.isEmpty())
				e.setTallas(tallas);

			List<Color> colores = colorService.findByProducto(id, idioma);
			if (!colores.isEmpty())
				e.setColores(colores);

			List<Jugador> jugadores = jugadorService.findByProducto(id);
			if (!jugadores.isEmpty())
				e.setJugadores(jugadores);

			return e;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {            
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}  
	}


	@Override
	public List<Producto> findByCriteria(Connection connection, ProductoCriteria producto, String idioma)
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuilder queryString = null;

		try {
			queryString = new StringBuilder(
					" SELECT P.ID_PRODUCTO, P.ID_CATEGORIA, P.ID_OFERTA, PI.NOMBRE_PRODUCTO, PI.DESCRIPCION, P.PRECIO, P.UNIDADES_STOCK, P.VALORACION_MEDIA, P.ID_LIGA, P.ID_EQUIPO, P.ID_MARCA " + 
							" FROM PRODUCTO P " +
					" INNER JOIN IDIOMA_PRODUCTO PI ON P.ID_PRODUCTO = PI.ID_PRODUCTO " );

			// Marca (flag) de primera clausula, que se desactiva en la primera
			boolean first = true;

			if (!producto.getTallas().isEmpty()) {
				queryString.append("INNER JOIN PRODUCTO_TALLA PT ON P.ID_PRODUCTO = PT.ID_PRODUCTO INNER JOIN TALLA T ON PT.ID_TALLA=P.ID_TALLA ");	
			}

			if (!producto.getColores().isEmpty()) {
				queryString.append("INNER JOIN PRODUCTO_COLOR PC ON P.ID_PRODUCTO = PC.ID_PRODUCTO INNER JOIN COLOR C ON PC.ID_COLOR=C.ID_COLOR ");	
			}

			if (!producto.getJugadores().isEmpty()) {
				queryString.append("INNER JOIN PRODUCTO_JUGADOR PJ ON P.ID_PRODUCTO = PJ.ID_PRODUCTO INNER JOIN JUGADOR J ON PJ.ID_JUGADOR = J.ID_JUGADOR ");	
			}

			if (producto.getIdProducto()!=null) {
				addClause(queryString, first, " P.ID_PRODUCTO LIKE ? ");
				first = false;
			}

			if (producto.getIdCategoria()!=null) {
				addClause(queryString, first, " P.ID_CATEGORIA LIKE ? ");
				first = false;
			}

			if (producto.getIdOferta()!=null) {
				addClause(queryString, first, " P.ID_OFERTA IS NOT NULL ");
				first = false;
			}

			if (producto.getPrecio()!=null) {
				addClause(queryString, first, " P.PRECIO LIKE ? ");
				first = false;
			}			

			if (producto.getUnidadesStock()!=null) {
				addClause(queryString, first, " P.UNIDADES_STOCK LIKE ? ");
				first = false;
			}	

			if (producto.getValoracionMedia()!=null) {
				addClause(queryString, first, " P.VALORACION_MEDIA IS NOT NULL ");
				first = false;
			}

			if (producto.getIdLiga()!=null) {
				addClause(queryString, first, " P.ID_LIGA IS NOT NULL ");
				first = false;
			}

			if (producto.getIdEquipo()!=null) {
				addClause(queryString, first, " P.ID_EQUIPO IS NOT NULL ");
				first = false;
			}

			if (producto.getIdMarca()!=null) {
				addClause(queryString, first, " P.ID_MARCA IS NOT NULL ");
				first = false;
			}

			if (idioma!=null) {
				addClause(queryString, first, " PI.ID_IDIOMA LIKE ? ");
				first = false;
			}

			if (!producto.getTallas().isEmpty()) {
				addClause(queryString, first,addTalla(producto.getTallas()).toString());	
				first = false;
			}

			if (!producto.getColores().isEmpty()) {
				addClause(queryString, first,addColor(producto.getColores()).toString());	
				first = false;
			}

			if (!producto.getJugadores().isEmpty()) {
				addClause(queryString, first,addJugador(producto.getJugadores()).toString());	
				first = false;
			}

			preparedStatement = connection.prepareStatement(queryString.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i = 1;       

			if (producto.getIdProducto()!=null) 
				preparedStatement.setString(i++, "%" + producto.getIdProducto() + "%");
			if (producto.getIdCategoria()!=null) 
				preparedStatement.setString(i++, "%" + producto.getIdCategoria() + "%");
			if (producto.getIdOferta()!=null) 
				preparedStatement.setString(i++, "%" + producto.getIdOferta() + "%");
			if (producto.getPrecio()!=null)
				preparedStatement.setString(i++, "%" + producto.getPrecio() + "%");
			if (producto.getUnidadesStock()!=null) 
				preparedStatement.setString(i++, "%" + producto.getUnidadesStock() + "%");
			if (producto.getValoracionMedia()!=null) 
				preparedStatement.setString(i++, "%" + producto.getValoracionMedia() + "%");
			if (producto.getIdLiga()!=null) 
				preparedStatement.setString(i++, "%" + producto.getIdLiga() + "%");
			if (producto.getIdEquipo()!=null) 
				preparedStatement.setString(i++, "%" + producto.getIdEquipo() + "%");
			if (producto.getIdMarca()!=null) 												//Pode ser posible que de un error
				preparedStatement.setString(i++, "%" + producto.getIdMarca() + "%");		//Lineas: esta e superior

			if (idioma!=null) 
				preparedStatement.setString(i++,idioma);


			resultSet = preparedStatement.executeQuery();

			List<Producto> results = new ArrayList<Producto>();                        
			Producto e = null;
			while (resultSet.next()) {
				e = loadNext(connection, resultSet);						
				results.add(e);
			}

			if (results.isEmpty()) {
				throw new DataException("No se han encontrado resultados");
			}

			return results;
		} catch (SQLException e) {
			throw new DataException("Hemos encontrado un problema" + e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public List<Producto> findAll(Connection connection,String idioma) 
			throws DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		TallaService tallaService = new TallaServiceImpl();
		ColorService colorService = new ColorServiceImpl();
		JugadorService jugadorService = new JugadorServiceImpl();

		try {

			// Create "preparedStatement"       
			String queryString = 
					" SELECT P.ID_PRODUCTO, P.ID_CATEGORIA, P.ID_OFERTA, PI.NOMBRE_PRODUCTO, PI.DESCRIPCION, P.PRECIO, P.UNIDADES_STOCK, P.VALORACION_MEDIA, P.ID_LIGA, P.ID_EQUIPO, P.ID_MARCA " + 
							" FROM PRODUCTO P " +
							" INNER JOIN IDIOMA_PRODUCTO PI ON P.ID_PRODUCTO = PI.ID_PRODUCTO "+
							" WHERE PI.ID_IDIOMA = ? " +
							" ORDER BY PI.NOMBRE_PRODUCTO ASC ";

			preparedStatement = connection.prepareStatement(queryString,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			preparedStatement.setString(i++, idioma);

			// Execute query.
			resultSet = preparedStatement.executeQuery();

			// Recupera la pagina de resultados
			List<Producto> results = new ArrayList<Producto>();                        
			Producto e = null;

			while (resultSet.next()) {
				e = loadNext(connection, resultSet);						
				results.add(e);
				//Buscamos LAS TALLAS DE CADA PRODUCTO
				List<Talla> tallas = tallaService.findByProducto(e.getIdProducto(), idioma);
				if (!tallas.isEmpty())
					e.setTallas(tallas);

				List<Color> colores = colorService.findByProducto(e.getIdProducto(), idioma);
				if (!colores.isEmpty())
					e.setColores(colores);

				List<Jugador> jugadores = jugadorService.findByProducto(e.getIdProducto());
				if (!jugadores.isEmpty())
					e.setJugadores(jugadores);
			}

			if (results.isEmpty()) {
				throw new DataException("No se han encontrado resultados");
			}

			return results;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public Producto create(Connection connection, Producto p) 
			throws DuplicateInstanceException, DataException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {          

			// Creamos el preparedstatement
			String queryString = "INSERT INTO PRODUCTO(ID_CATEGORIA,ID_OFERTA,PRECIO,UNIDADES_STOCK,VAROLACION_MEDIA,ID_LIGA,ID_EQUIPO,ID_MARCA) "
					+ "VALUES (?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(queryString,
					Statement.RETURN_GENERATED_KEYS);

			// Fill the "preparedStatement"
			int i = 1;             
			preparedStatement.setInt(i++, p.getIdCategoria());
			
			if (p.getIdOferta()!=null)
				preparedStatement.setInt(i++, p.getIdOferta());
			else
				preparedStatement.setNull(i++,java.sql.Types.NULL);
			
			preparedStatement.setDouble(i++, p.getPrecio());
			preparedStatement.setInt(i++, p.getUnidadesStock());
			
			if (p.getValoracionMedia()!=null)
				preparedStatement.setDouble(i++, p.getValoracionMedia());
			else
				preparedStatement.setNull(i++,java.sql.Types.NULL);
			
			preparedStatement.setInt(i++, p.getIdLiga());
			
			if (p.getIdEquipo()!=null)
				preparedStatement.setInt(i++, p.getIdEquipo());
			else
				preparedStatement.setNull(i++,java.sql.Types.NULL);
			
			if (p.getIdMarca()!=null)
				preparedStatement.setInt(i++, p.getIdMarca());
			else
				preparedStatement.setNull(i++,java.sql.Types.NULL);
			

			// Execute query
			int insertedRows = preparedStatement.executeUpdate();

			if (insertedRows == 0) {
				throw new SQLException("Can not add row to table 'Producto'");
			}

			// Recuperamos la PK generada
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				Integer id = resultSet.getInt(1); 
				p.setIdProducto(id);
			} else {
				throw new DataException("Unable to fetch autogenerated primary key");
			}

			// Return the DTO
			return p;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeResultSet(resultSet);
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	@Override
	public void update(Connection connection, Producto producto) 
			throws InstanceNotFoundException, DataException {

		PreparedStatement preparedStatement = null;
		StringBuilder queryString = null;
		try {	

			queryString = new StringBuilder(
					" UPDATE PRODUCTO" 
					);

			boolean first = true;

			if (producto.getIdCategoria()!=null) {
				addUpdate(queryString, first, " ID_CATEGORIA = ? ");
				first = false;
			}

			if (producto.getIdOferta()!=null) {
				addUpdate(queryString, first, " ID_OFERTA = ? ");
				first = false;
			}

			if (producto.getPrecio()!=null) {
				addUpdate(queryString, first, " PRECIO = ? ");
				first = false;
			}

			if (producto.getUnidadesStock()!=null) {
				addUpdate(queryString, first, " UNIDADES_STOCK = ? ");
				first = false;
			}

			if (producto.getValoracionMedia()!=null) {
				addUpdate(queryString, first, " VALORACION_MEDIA = ? ");
				first = false;
			}
			
			if (producto.getIdLiga()!=null) {
				addUpdate(queryString, first, " ID_LIGA = ? ");
				first = false;
			}
			
			if (producto.getIdEquipo()!=null) {
				addUpdate(queryString, first, " ID_EQUIPO = ? ");
				first = false;
			}
			
			if (producto.getIdMarca()!=null) {
				addUpdate(queryString, first, " ID_MARCA = ? ");
				first = false;
			}

			queryString.append("WHERE ID_PRODUCTO = ?");

			preparedStatement = connection.prepareStatement(queryString.toString());


			int i = 1;
			if (producto.getIdCategoria()!=null) 
				preparedStatement.setInt(i++,producto.getIdCategoria());

			if (producto.getIdOferta()!=null) 
				preparedStatement.setInt(i++,producto.getIdOferta());	
			
			if (producto.getPrecio()!=null) 
				preparedStatement.setDouble(i++,producto.getPrecio());

			if (producto.getUnidadesStock()!=null) 
				preparedStatement.setInt(i++,producto.getUnidadesStock());	
		
			if (producto.getValoracionMedia()!=null) 
				preparedStatement.setDouble(i++,producto.getValoracionMedia());
			
			if (producto.getIdLiga()!=null) 
				preparedStatement.setInt(i++,producto.getIdLiga());

			if (producto.getIdEquipo()!=null) 
				preparedStatement.setInt(i++,producto.getIdEquipo());
			
			if (producto.getIdMarca()!=null) 
				preparedStatement.setInt(i++,producto.getIdMarca());
			


			preparedStatement.setInt(i++, producto.getIdProducto());

			int updatedRows = preparedStatement.executeUpdate();

			if (updatedRows == 0) {
				throw new InstanceNotFoundException(producto.getIdProducto(), Producto.class.getName());
			}

			if (updatedRows > 1) {
				throw new SQLException("Duplicate row for id = '" + 
						producto.getIdProducto() + "' in table 'Producto'");
			}     

		} catch (SQLException e) {
			throw new DataException(e);    
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}              		
	}

	@Override
	public Integer delete(Connection connection, Integer id) 
			throws InstanceNotFoundException, DataException {
		PreparedStatement preparedStatement = null;

		try {
			String queryString =	
					"DELETE FROM PRODUCTO " 
							+ "WHERE ID_PRODUCTO = ? ";

			preparedStatement = connection.prepareStatement(queryString);

			int i = 1;
			preparedStatement.setInt(i++, id);

			int removedRows = preparedStatement.executeUpdate();

			if (removedRows == 0) {
				throw new InstanceNotFoundException(id,Producto.class.getName());
			} 


			return removedRows;

		} catch (SQLException e) {
			throw new DataException(e);
		} finally {
			JDBCUtils.closeStatement(preparedStatement);
		}
	}

	private void addClause(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first? "WHERE ": " AND ").append(clause);
	}

	private void addUpdate(StringBuilder queryString, boolean first, String clause) {
		queryString.append(first? " SET ": " , ").append(clause);
	}

	private StringBuilder addTalla(List<Talla> tallas) {
		
		boolean inner = true;
		StringBuilder lista = new StringBuilder();
		for (Talla c : tallas) {
			lista.append(inner ? " (T.ID_TALLA LIKE "+c.getIdTalla() : " OR " + c.getIdTalla());
			inner=false;	
		}
		lista.append(" ) ");
		return lista;
	}
	private StringBuilder addColor(List<Color> colores) {
		
		boolean inner = true;
		StringBuilder lista = new StringBuilder();
		for (Color n : colores) {
			lista.append(inner ? " (C.ID_COLOR LIKE "+n.getIdColor() : " OR " + n.getIdColor());
			inner=false;	
		}
		lista.append(" ) ");
		return lista;
	}
	private StringBuilder addJugador(List<Jugador> jugadores) {
		
		boolean inner = true;
		StringBuilder lista = new StringBuilder();
		for (Jugador i : jugadores) {
			lista.append(inner ? " (J.ID_JUGADOR LIKE "+ i.getIdJugador() : " OR " + i.getIdJugador());
			inner=false;	
		}
		lista.append(" ) ");
		return lista;
	}

	private Producto loadNext(Connection connection, ResultSet resultSet)
			throws SQLException, DataException {
		int i = 1;

		Integer idProducto = resultSet.getInt(i++);
		Integer idCategoria = resultSet.getInt(i++);
		Integer idOferta = resultSet.getInt(i++);   
		Double precio = resultSet.getDouble(i++);
		Integer unidadesStock = resultSet.getInt(i++);
		Double valoracionMedia = resultSet.getDouble(i++);
		Integer idLiga = resultSet.getInt(i++);
		Integer idEquipo = resultSet.getInt(i++);
		Integer idMarca = resultSet.getInt(i++); 
		String nombreProducto = resultSet.getString(i++);
		String descripcion = resultSet.getString(i++);
		
		Producto p = new Producto();
		p.setIdProducto(idProducto);
		p.setIdCategoria(idCategoria);
		p.setIdOferta(idOferta);
		p.setPrecio(precio);
		p.setUnidadesStock(unidadesStock);
		p.setValoracionMedia(valoracionMedia);
		p.setIdLiga(idLiga);
		p.setIdEquipo(idEquipo);
		p.setIdMarca(idMarca);
		p.setNombreProducto(nombreProducto);
		p.setDescripcion(descripcion);

		return p;
	}

}