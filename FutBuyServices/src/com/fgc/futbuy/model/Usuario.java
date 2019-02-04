package com.fgc.futbuy.model;

import java.util.Date;

import com.fgc.futbuy.model.Usuario;


public class Usuario extends AbstractValueObject implements Comparable<Usuario>{

	private Integer idUsuario = null;
	private String email = null;
	private String contrasenha = null;
	private String nombreUsuario = null;
	private String nombre = null;
	private String apellido1 = null;
	private String apellido2 = null;
	private Date fechaNacimiento = null;
	private String genero = null;
	private Direccion direccion = null;
	private Integer codigoDeRecuperacion = null;

	public Usuario() {}

	public Integer getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getContrasenha() {
		return contrasenha;
	}


	public void setContrasenha(String contrasenha) {
		this.contrasenha = contrasenha;
	}


	public String getNombreUsuario() {
		return nombreUsuario;
	}


	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido1() {
		return apellido1;
	}


	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}


	public String getApellido2() {
		return apellido2;
	}


	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}


	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public String getGenero() {
		return genero;
	}


	public void setGenero(String genero) {
		this.genero = genero;
	}
	

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	

	public Integer getCodigoDeRecuperacion() {
		return codigoDeRecuperacion;
	}

	public void setCodigoDeRecuperacion(Integer codigoDeRecuperacion) {
		this.codigoDeRecuperacion = codigoDeRecuperacion;
	}

	public boolean equals(Object o) {
		String otroEmail=((Usuario) o).getEmail();
		if(this.getEmail()==null && otroEmail==null) {
			return true;
		}
		if(this.getEmail()==null || otroEmail==null) {
			return false;
		}
		
		return getEmail().equalsIgnoreCase(otroEmail);
	}
	
	@Override
	public final int compareTo(Usuario u) {
		if(u.getNombre()==null && this.getNombre()==null) {
			return 0;
		}
		if(u.getNombre()==null) return 1;
		if(this.getNombre()==null)return -1;
		
		return this.getNombre().compareTo(u.getNombre());
		
	}
	
}