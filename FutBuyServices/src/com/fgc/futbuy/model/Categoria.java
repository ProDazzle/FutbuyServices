package com.fgc.futbuy.model;



public class Categoria extends AbstractValueObject{

	private Integer idCategoria = null;
	private String nombreCategoria = null;
	private String descripcion = null;
	private Integer idCategoriaPadre = null;


	public Categoria() {}


	public Integer getIdCategoria() {
		return idCategoria;
	}


	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}


	public String getNombreCategoria() {
		return nombreCategoria;
	}


	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Integer getIdCategoriaPadre() {
		return idCategoriaPadre;
	}


	public void setIdCategoriaPadre(Integer idCategoriaPadre) {
		this.idCategoriaPadre = idCategoriaPadre;
	}



}
