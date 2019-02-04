package com.fgc.futbuy.model;

public class Equipo extends AbstractValueObject{

	private Integer idEquipo = null;
	private String nombreEquipo = null;

	public Equipo() {}

	public Integer getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(Integer idEquipo) {
		this.idEquipo = idEquipo;
	}


	public String getNombreEquipo() {
		return nombreEquipo;
	}

	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}


}
