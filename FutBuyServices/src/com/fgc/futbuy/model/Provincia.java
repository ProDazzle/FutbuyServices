package com.fgc.futbuy.model;

public class Provincia extends AbstractValueObject{
	
	private Integer idProvincia = null;
	private String nombreProvincia = null;

	
	public Provincia() {}

	public Integer getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(Integer idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getNombreProvincia() {
		return nombreProvincia;
	}

	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}


	
	
	
	

}
