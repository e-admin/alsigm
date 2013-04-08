package ieci.tecdoc.sgm.calendario.ws.server; 

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Contenedor de dias festivos
 *
 */
public class CalendarioDias extends RetornoServicio
{
	
	private CalendarioDia[] dias;

	public CalendarioDia[] getDias() {
		return dias;
	}

	public void setDias(CalendarioDia[] dias) {
		this.dias = dias;
	}
	
}

