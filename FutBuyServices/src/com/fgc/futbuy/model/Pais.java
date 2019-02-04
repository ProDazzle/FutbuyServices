package com.fgc.futbuy.model;

public class Pais extends AbstractValueObject{

	private String idPais = null;
	private String nombrePais = null;
	
	public Pais() {
		
	}

	public String getIdPais() {
		return idPais;
	}

	public void setIdPais(String idPais) {
		this.idPais = idPais;
	}

	public String getNombrePais() {
		return nombrePais;
	}

	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}

	

	
}
