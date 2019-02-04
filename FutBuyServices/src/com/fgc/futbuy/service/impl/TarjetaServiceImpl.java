
package com.fgc.futbuy.service.impl;

import com.fgc.futbuy.exceptions.DataException;
import com.fgc.futbuy.exceptions.ServiceException;
import com.fgc.futbuy.model.Tarjeta;
import com.fgc.futbuy.service.TarjetaService;

public class TarjetaServiceImpl implements TarjetaService{
	
	private Tarjeta recibirTarjeta = null;

	public TarjetaServiceImpl() {		
	}

	@Override
	public boolean checkCard(Tarjeta tarjeta) 
			throws DataException, ServiceException {
		Boolean salida = false;
		
		recibirTarjeta = new Tarjeta();
				
		if (comprobarNumero(tarjeta.getNumero())) {
			salida=true;
			recibirTarjeta.setNumero(tarjeta.getNumero());		
			recibirTarjeta.setTitular(tarjeta.getTitular());
			recibirTarjeta.setCaducidad(tarjeta.getCaducidad());
			recibirTarjeta.setCcv(tarjeta.getCcv());
		}		
		return salida;
	}
	
    protected static boolean comprobarNumero(String numeroTarjeta) {
        int digits = numeroTarjeta.length();
        int oddOrEven = digits & 1;
        long sum = 0;
        for (int count = 0; count < digits; count++) {
            int digit = 0;
            try {
                digit = Integer.parseInt(numeroTarjeta.charAt(count) + "");
            } catch(NumberFormatException e) {
                return false;
            }

            if (((count & 1) ^ oddOrEven) == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }

        return (sum == 0) ? false : (sum % 10 == 0);
    }
}
