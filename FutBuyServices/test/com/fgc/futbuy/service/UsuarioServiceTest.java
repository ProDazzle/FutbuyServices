package com.fgc.futbuy.service;

import java.util.Date;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.InstanceNotFoundException;
import com.fgc.futbuy.model.Direccion;
import com.fgc.futbuy.model.Usuario;
import com.fgc.futbuy.service.impl.UsuarioServiceImpl;

public class UsuarioServiceTest {

	private UsuarioService usuarioService = null;

	
	public UsuarioServiceTest() {
		usuarioService = new UsuarioServiceImpl();
	}

	public void testFindById() 
			throws Exception {
		Usuario u = usuarioService.findById(1);
		System.out.println(u);
	}

	public void testCreate() 
			throws Exception{

		Direccion d = new Direccion();
		d.setCalle("Calle la prueba2");
		d.setNombreCiudad("PruebaCity2");
		d.setCodigoPostal(27702);
		d.setIdProvincia(27);
		d.setLetra("B");
		d.setNumero(12);
		d.setPiso(4);

		Usuario u = new Usuario();
		u.setEmail("prueba@estovaiii.com");
		u.setNombre("Pepee");
		u.setApellido1("Pruebaa");
		u.setApellido2("Probandoo");
		u.setContrasenha("holaa");
		u.setFechaNacimiento(new Date());
		u.setNombreUsuario("prueba");
		u.setGenero("H");
		u.setDireccion(d);

		usuarioService.create(u);
		System.out.println(u);

	}

	public void testLogin() throws Exception{
		usuarioService.login("prueba@estovaii.com", "hola");
	}

	public void testUpdate() throws Exception{


		Usuario u = new Usuario();
		u.setIdUsuario(2);
		u.setEmail("prueba@foi.com");
		u.setNombre("Pepee");
		u.setApellido1("Pruebaa");
		u.setApellido2("Pruebame");
		u.setContrasenha("aloo");
		u.setFechaNacimiento(new Date());
		u.setNombreUsuario("pruebame");
		u.setGenero("H");

		usuarioService.update(u);

	}
	
	public void testUpdateDireccion() throws InstanceNotFoundException, DataException {

		Usuario u = usuarioService.findById(3);
		
		Direccion d = new Direccion();
		d.setIdDireccion(1);
		d.setCalle("Calle foi");
		d.setNombreCiudad("Foi");
		d.setCodigoPostal(27702);
		d.setIdProvincia(27);
		d.setLetra("B");
		d.setNumero(12);
		d.setPiso(4);
		d.setIdUsuario(3);

		usuarioService.editDireccion(d, u);
		
	}
	
	
	public void testDelete() throws DataException {
		usuarioService.delete(1);
	}
	
	public void testDeleteApuestas() {
		
	}

	public static void main(String args[]){

		UsuarioServiceTest test = new UsuarioServiceTest();

		try {

			//test.testFindById();
			test.testCreate();
			//test.testLogin(); //ok
			//test.testUpdate(); //ok --cambia datos de usuario, non direccion
			//test.testUpdateDireccion(); //ok --cambia direccion
			//test.testDelete(); //ok

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
