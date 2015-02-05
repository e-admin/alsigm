package es.ieci.tecdoc.fwktd.sir.api.manager;

import java.util.Date;

/**
 * Interfaz para los gestores de fechas.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface FechaManager {

	/**
	 * Obtiene la fecha y hora actuales.
	 * @return
	 */
	public Date getFechaActual();
}
