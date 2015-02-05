package es.ieci.plusvalias.util;

import es.ieci.plusvalias.api.Adquiriente;
import es.ieci.plusvalias.api.Transmitente;
import es.ieci.plusvalias.exception.ErrorCode;
import es.ieci.plusvalias.exception.PlusvaliaException;

public class CalculoPlusvaliaValidatorHelper {

	public void validarDerecho(Adquiriente[] adquirientes){
		for (int i = 0; i < adquirientes.length; i++)
		{
			// En caso de que el adquiriente tenga derecho número 6
			if (adquirientes[i].getNumDerecho() == 6)
			{
				throw new PlusvaliaException(ErrorCode.NOT_VALID_DERECHO);
			}
		}		
	}
	
	private boolean isDerechosCompatible(Transmitente transmitente, Adquiriente adquiriente){
		boolean ok = false;
		
		if (transmitente.getNumDerecho() == adquiriente.getNumDerecho() || adquiriente.getNumDerecho() == 1)
		{
			ok = true;
		}
		
		return ok;
	}

}
