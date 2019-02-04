package com.fgc.futbuy.model;



public class LineaPedidoId extends AbstractValueObject{
	

	private Integer idPedido = null;
	private Integer idProducto = null;

	
	
	public LineaPedidoId(){}


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

	
}