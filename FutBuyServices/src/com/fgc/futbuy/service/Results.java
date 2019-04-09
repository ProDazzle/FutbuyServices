package com.fgc.futbuy.service;

import java.util.List;

/**
 * DTO, wrapper, o como se le quiera llamar, para los resultados
 * de una consulta, y el total de resultados.
 * 
 * Orientado a paginación.
 * 
 * Si estamos en la capa de datos e integración deberiamos llamarle
 * ResultsDTO o similar, pero realmente vale para calquier capa... 
 * 
 * @author https://www.linkedin.com/in/joseantoniolopezperez
 * @version 0.2
 */
public class Results<T> {
	
	private List<T> page = null;
	private int startIndex = 0;
	private int total = 0;
	// Preguntas para los alumnos:
	// - Por qué no está el count para el tamaño de la pagina?
	// - Por qué está el start index, si realmente es un parametro que se
	//   indicara en la peticion?
	
	public Results(List<T> page, int startIndex, int total) {
		setPage(page);
		setStartIndex(startIndex);
		setTotal(total);		
	}

	public List<T> getPage() {
		return page;
	}

	public void setPage(List<T> page) {
		this.page = page;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
