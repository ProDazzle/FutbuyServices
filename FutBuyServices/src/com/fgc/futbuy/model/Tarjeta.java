
package com.fgc.futbuy.model;

public class Tarjeta extends AbstractValueObject{
	
	private String titular = null;
	private String ccv = null;
	private String numero = null;
	private String caducidad = null;
	
	public String getTitular() {
		return titular;
	}
	
	public void setTitular(String titular) {
		this.titular = titular;
	}
	
	public String getCcv() {
		return ccv;
	}
	
	public void setCcv(String ccv) {
		this.ccv = ccv;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getCaducidad() {
		return caducidad;
	}
	
	public void setCaducidad(String caducidad) {
		this.caducidad = caducidad;
	}
	
	
}