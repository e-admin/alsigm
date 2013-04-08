/**
 * 
 */
package ieci.tecdoc.sgm.gestioncsv.ws.server.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * @author IECISA
 *
 */
public class BooleanRetorno extends RetornoServicio{
	
	private boolean valor;

	/**
	 * @return el valor
	 */
	public boolean isValor() {
		return valor;
	}

	/**
	 * @param valor el valor a fijar
	 */
	public void setValor(boolean valor) {
		this.valor = valor;
	}
	
	

}
