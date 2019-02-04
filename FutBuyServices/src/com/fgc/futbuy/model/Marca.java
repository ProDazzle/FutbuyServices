package com.fgc.futbuy.model;

public class Marca extends AbstractValueObject{

	private Integer idMarca = null;
	private String nombreMarca = null;

	public Marca() {}

	public Integer getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}

	public String getNombreMarca() {
		return nombreMarca;
	}

	public void setNombreMarca(String nombreMarca) {
		this.nombreMarca = nombreMarca;
	}

}
