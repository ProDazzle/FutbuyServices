package com.fgc.futbuy.model;

public class Talla extends AbstractValueObject{

	private Integer idTalla = null;
	private String nombreTalla = null;
	
	public Talla() {}

	public Integer getIdTalla() {
		return idTalla;
	}

	public void setIdTalla(Integer idTalla) {
		this.idTalla = idTalla;
	}

	public String getNombreTalla() {
		return nombreTalla;
	}

	public void setNombreTalla(String nombreTalla) {
		this.nombreTalla = nombreTalla;
	}
	
	
}
