package com.fgc.futbuy.model;


public class Liga extends AbstractValueObject{
	
	private Integer idLiga = null;
	private String nombreLiga = null;
	
	
	public Liga() {
		
	}

	public Integer getIdLiga() {
		return idLiga;
	}

	public void setIdLiga(Integer idLiga) {
		this.idLiga = idLiga;
	}

	public String getNombreLiga() {
		return nombreLiga;
	}

	public void setNombreLiga(String nombreLiga) {
		this.nombreLiga = nombreLiga;
	}

	
}
