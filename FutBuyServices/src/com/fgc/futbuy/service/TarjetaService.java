
package com.fgc.futbuy.service;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.ServiceException;
import com.fgc.futbuy.model.Tarjeta;

public interface TarjetaService {
	
	public boolean checkCard(Tarjeta tarjeta)
			throws DataException, ServiceException;
}
