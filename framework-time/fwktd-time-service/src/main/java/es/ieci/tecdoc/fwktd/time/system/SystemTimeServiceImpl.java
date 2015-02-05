package es.ieci.tecdoc.fwktd.time.system;

import java.util.Calendar;

import java.util.Date;

import es.ieci.tecdoc.fwktd.time.TimeService;

/**
 * Implementación del servicio de tiempos para obtener la hora actual del sistema
 * @author Iecisa
 * @version $Revision$
 *
 */
public class SystemTimeServiceImpl implements TimeService {

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.time.TimeService#getCurrentDate()
	 */
	public Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

}
