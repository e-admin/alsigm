package es.ieci.tecdoc.fwktd.time;

import java.util.Date;

import es.ieci.tecdoc.fwktd.time.exception.TimeException;

/**
 * Servicio de Tiempos para obtener la hora actual
 * @author Iecisa
 * @version $Revision$
 */
public interface TimeService {

		/**
		 * Retorna la hora actual del sistema utilizado como servidor de tiempos
		 * @return Hora actual
		 * @throws TimeException
		 */
		public Date getCurrentDate() throws TimeException;

}
