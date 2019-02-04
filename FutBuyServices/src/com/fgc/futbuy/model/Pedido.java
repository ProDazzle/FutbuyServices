package com.fgc.futbuy.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido extends AbstractValueObject{
	
	private Integer idPedido = null;
	private Integer idUsuario = null;
	private Double total = null;
	private Date fechaPedido = null;
	private Date fechaLlegada = null;
	
	private List<LineaPedido> lineaspedido = null;
	
	public Pedido() {
		lineaspedido = new ArrayList<LineaPedido>();
	}


	public Integer getIdPedido() {
		return idPedido;
	}


	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}


	public Integer getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}


	public Double getTotal() {
		return total;
	}


	public void setTotal(Double total) {
		this.total = total;
	}


	public Date getFechaPedido() {
		return fechaPedido;
	}


	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}


	public Date getFechaLlegada() {
		return fechaLlegada;
	}


	public void setFechaLlegada(Date fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}


	public List<LineaPedido> getLineaspedido() {
		return lineaspedido;
	}


	public void setLineaspedido(List<LineaPedido> lineaspedido) {
		this.lineaspedido = lineaspedido;
	}

	
}

