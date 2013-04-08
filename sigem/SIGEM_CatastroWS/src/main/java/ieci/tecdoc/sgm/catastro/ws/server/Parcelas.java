package ieci.tecdoc.sgm.catastro.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Contenedor de parcelas
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Parcelas extends RetornoServicio{
	
	private Parcela[]  parcelas;

	public Parcela[] getParcelas() {
		return parcelas;
	}

	public void setParcelas(Parcela[] parcelas) {
		this.parcelas = parcelas;
	}
}
