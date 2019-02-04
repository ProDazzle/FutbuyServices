package com.fgc.futbuy.model.comparator;

import java.util.Comparator;

import com.fgc.futbuy.model.Usuario;

public class UsuarioComparatorByFechaNacimiento  implements Comparator<Usuario>{
	public UsuarioComparatorByFechaNacimiento() {}
	
	@Override
	public int compare(Usuario u1, Usuario u2) {
		
		return u1.getFechaNacimiento().compareTo(u2.getFechaNacimiento());
	}
}
