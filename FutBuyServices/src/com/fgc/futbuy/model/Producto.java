package com.fgc.futbuy.model;

import java.util.ArrayList;
import java.util.List;


public class Producto extends AbstractValueObject{

	private Integer idProducto = null;
	private Integer idCategoria = null;
	private Integer idOferta = null;
	private String nombreProducto = null;
	private String descripcion = null;
	private Double precio = null;
	private Integer unidadesStock = null;
	private Double valoracionMedia = null;
	private Integer idLiga = null;
	private Integer idEquipo = null;
	private Integer idMarca = null;
	

	private List<Talla> tallas = null;
	private List<Color> colores = null;
	private List<Jugador> jugadores = null;
	
	public Producto() {
		tallas = new ArrayList<Talla>();
		colores = new ArrayList<Color>();
		jugadores = new ArrayList<Jugador>();
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Integer getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(Integer idOferta) {
		this.idOferta = idOferta;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Integer getUnidadesStock() {
		return unidadesStock;
	}

	public void setUnidadesStock(Integer unidadesStock) {
		this.unidadesStock = unidadesStock;
	}

	public Double getValoracionMedia() {
		return valoracionMedia;
	}

	public void setValoracionMedia(Double valoracionMedia) {
		this.valoracionMedia = valoracionMedia;
	}

	public Integer getIdLiga() {
		return idLiga;
	}

	public void setIdLiga(Integer idLiga) {
		this.idLiga = idLiga;
	}

	public Integer getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(Integer idEquipo) {
		this.idEquipo = idEquipo;
	}

	public Integer getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}

	public List<Talla> getTallas() {
		return tallas;
	}

	public void setTallas(List<Talla> tallas) {
		this.tallas = tallas;
	}

	public List<Color> getColores() {
		return colores;
	}

	public void setColores(List<Color> colores) {
		this.colores = colores;
	}

	public List<Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	@Override
	public boolean equals(Object o) {
		
		if(o ==null|| !(o instanceof Producto)) {
			return false;
		}
		Producto other = (Producto) o;
		if (this.getIdProducto()==null && other.getIdProducto()==null) {
			return false;
		}
		if(!this.getIdProducto().equals(other.getIdProducto())) {
			return false;
		}
		
		return true;
		
	}
	@Override
	public int hashCode() {
		if(idProducto == null) return Integer.MAX_VALUE;
		
		return idProducto.hashCode();
	}

	
}


