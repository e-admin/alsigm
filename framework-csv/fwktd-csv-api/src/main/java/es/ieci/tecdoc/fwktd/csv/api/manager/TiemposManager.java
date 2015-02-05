package es.ieci.tecdoc.fwktd.csv.api.manager;

import java.util.Date;

/**
 * Interfaz para el gestor de tiempos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface TiemposManager {

	/**
	 * Obtiene la fecha actual.
	 *
	 * @return Fecha actual
	 */
	public Date getFechaActual();
}
