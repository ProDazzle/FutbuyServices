package com.fgc.futbuy.model;


public class Idioma implements ValueObject{

	private String idIdioma;
	private String nombreIdioma;
	
	public Idioma() {
		
	}

	public String getIdIdioma() {
		return idIdioma;
	}

	public void setIdIdioma(String idIdioma) {
		this.idIdioma = idIdioma;
	}

	public String getNombreIdioma() {
		return nombreIdioma;
	}

	public void setNombreIdioma(String nombreIdioma) {
		this.nombreIdioma = nombreIdioma;
	}
	
}
