package com.fgc.futbuy.model;

import java.util.Date;

public class Oferta extends AbstractValueObject{

	private Integer idOferta = null;
	private Double precioDescuento = null;
	private Date fechaCreacion = null;


	public Oferta() {}


	public Integer getIdOferta() {
		return idOferta;
	}


	public void setIdOferta(Integer idOferta) {
		this.idOferta = idOferta;
	}


	public Double getPrecioDescuento() {
		return precioDescuento;
	}


	public void setPrecioDescuento(Double precioDescuento) {
		this.precioDescuento = precioDescuento;
	}


	public Date getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	



}
