package com.fgc.futbuy.model;



public class LineaPedido extends AbstractValueObject{
	
	private Integer numeroLinea = null;
	private Integer idPedido = null;
	private Integer idProducto = null;
	private Double precioUnitario = null;
	private Double precioTotal = null;
	private Integer cantidad = null;
	
	
	public LineaPedido(){}



	public Integer getNumeroLinea() {
		return numeroLinea;
	}


	public void setNumeroLinea(Integer numeroLinea) {
		this.numeroLinea = numeroLinea;
	}


	public Integer getIdPedido() {
		return idPedido;
	}


	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}


	public Integer getIdProducto() {
		return idProducto;
	}


	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}


	public Double getPrecioUnitario() {
		return precioUnitario;
	}


	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}


	public Double getPrecioTotal() {
		return precioTotal;
	}


	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}


	public Integer getCantidad() {
		return cantidad;
	}


	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	
}

