package com.fgc.futbuy.model;

public class Jugador extends AbstractValueObject{

	private Integer idJugador = null;
	private String nombreNumeroJugador = null;

	public Jugador() {}

	public Integer getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(Integer idJugador) {
		this.idJugador = idJugador;
	}

	public String getNombreNumeroJugador() {
		return nombreNumeroJugador;
	}

	public void setNombreNumeroJugador(String nombreNumeroJugador) {
		this.nombreNumeroJugador = nombreNumeroJugador;
	}



}
