package com.fgc.futbuy.model;

public class Color extends AbstractValueObject{

	private Integer idColor = null;
	private String nombreColor = null;
	
	public Color() {}

	public Integer getIdColor() {
		return idColor;
	}

	public void setIdColor(Integer idColor) {
		this.idColor = idColor;
	}

	public String getNombreColor() {
		return nombreColor;
	}

	public void setNombreColor(String nombreColor) {
		this.nombreColor = nombreColor;
	}

	
	
	
}