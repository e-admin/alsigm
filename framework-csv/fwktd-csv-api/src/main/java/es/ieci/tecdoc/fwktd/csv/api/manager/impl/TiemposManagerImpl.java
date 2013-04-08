package es.ieci.tecdoc.fwktd.csv.api.manager.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.csv.api.manager.TiemposManager;
import es.ieci.tecdoc.fwktd.time.TimeService;
import es.ieci.tecdoc.fwktd.time.exception.TimeException;

/**
 * Implementación del manager de tiempos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TiemposManagerImpl implements TiemposManager {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(TiemposManagerImpl.class);

	/**
	 * Servicio de tiempos.
	 */
	private TimeService servicioTiempos = null;

	/**
	 * Constructor.
	 */
	public TiemposManagerImpl() {
		super();
	}

	public TimeService getServicioTiempos() {
		return servicioTiempos;
	}

	public void setServicioTiempos(TimeService servicioTiempos) {
		this.servicioTiempos = servicioTiempos;
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.csv.api.manager.TiemposManager#getFechaActual()
	 */
	public Date getFechaActual() {

		logger.info("Obteniendo la fecha actual");

		Date fechaActual = null;

		if (getServicioTiempos() != null) {
			try {
				fechaActual = getServicioTiempos().getCurrentDate();
			} catch (TimeException e) {
				logger.error("Error al obtener la fecha actual en el servicio de tiempos", e);
			}
		} else {
			logger.info("No se ha definido el servicio de tiempos. Se obtendrá la fecha instanciando la clase java.util.Date");
		}

		if (fechaActual == null) {
			logger.info("Se obtiene la fecha instanciando la clase java.util.Date");
			fechaActual = new Date();
		}

		return fechaActual;
	}

}
