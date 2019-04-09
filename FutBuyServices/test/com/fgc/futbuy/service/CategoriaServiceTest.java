
package com.fgc.futbuy.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fgc.futbuy.model.Categoria;
import com.fgc.futbuy.service.impl.CategoriaServiceImpl;
import com.fgc.futbuy.util.ToStringUtil;


public class CategoriaServiceTest {

	private static Logger logger = LogManager.getLogger(CategoriaServiceTest.class.getName());

	private CategoriaService categoriaService = null;
	
	public CategoriaServiceTest() {
		categoriaService = new CategoriaServiceImpl();
	}
	
	protected void testFindById() {
		logger.info("Testing findById ...");
		
		Integer id = (Integer) 1;
		
		try {			
			Categoria cat = categoriaService.findById(id);			
			logger.info("Found: "+ToStringUtil.toString(cat));
			
		} catch (Throwable t) {
			logger.error("id = "+id, t);
		}
		
		logger.info("Test testFindById finished.\n");		
	}
	
	protected void testFindAll() {
		logger.info("Testing findAll ...");
		int pageSize = 10; 	
		
		try {
			String idioma = "EN";
			List<Categoria> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = categoriaService.findAll(idioma);
				if (results.size()>0) {
					logger.info("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Categoria p: results) {
						total++;
						logger.info("Result "+total+": "+ToStringUtil.toString(p));
					}
					startIndex = startIndex + pageSize;
				}
				
			} while (results.size()==pageSize);
			
			logger.info("Found "+total+" results.");
						
		} catch (Throwable c) {
			c.printStackTrace();
		}
		logger.info("Test testFindAll finished.\n");
	}
	
	protected void testExists() {
		logger.info("Testing exists ...");

		Integer id = (Integer) 1;
		
		try {			
			Boolean exists = categoriaService.exists(id);			
			logger.info("Exists: "+id+" -> "+exists);
			
		} catch (Throwable t) {
			logger.error("id = "+id, t);
		}
		
		logger.info("Test exists finished.\n");		
	}
	
	
	public static void main(String args[]) {
		CategoriaServiceTest test = new CategoriaServiceTest();
		test.testFindById();
//		test.testExists();
//		test.testFindAll();
//		test.testFindByProducto();
	}
}

