package com.fgc.futbuy;

import com.fgc.futbuy.dao.UsuarioDAO;
import com.fgc.futbuy.dao.impl.UsuarioDAOImpl;

public class MyServiceTest {
	
	//private MAilService mailService = null;
	
	private UsuarioDAO dao = null;
	
	public MyServiceTest() {
		//mailService = new
		dao = new UsuarioDAOImpl();
		
	}
	
	public void miMetodo() throws Exception{
		
		dao.findById(null, 1);
	}

	public static void main(String[] args) {
		
		MyServiceTest t = new MyServiceTest();
		
		try {
		t.miMetodo();
		} catch(Exception e) {
			System.out.println("Petou: "+e.getMessage());
			e.printStackTrace();
		}
	}

}
