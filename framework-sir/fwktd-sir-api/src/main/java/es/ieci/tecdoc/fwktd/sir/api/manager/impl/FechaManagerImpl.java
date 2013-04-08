package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.sir.api.manager.FechaManager;
import es.ieci.tecdoc.fwktd.time.TimeService;
import es.ieci.tecdoc.fwktd.time.exception.TimeException;

/**
 * Implementación del gestor de fechas que utiliza el módulo de tiempos del
 * framework-td.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class FechaManagerImpl implements FechaManager {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(FechaManagerImpl.class);

	/**
	 * Servicio de tiempos.
	 */
	private TimeService timeService = null;

	/**
	 * Constructor.
	 */
	public FechaManagerImpl() {
		super();
	}

	public TimeService getTimeService() {
		return timeService;
	}

	public void setTimeService(TimeService timeService) {
		this.timeService = timeService;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.sir.api.manager.FechaManager#getFechaActual()
	 */
	public Date getFechaActual() {

		Date fechaActual = null;

		logger.info("Obteniendo la fecha actual");

    	if (getTimeService() != null) {
			try {
				fechaActual = getTimeService().getCurrentDate();
			} catch (TimeException e) {
				logger.warn("Error al obtener la fecha actual a partir del servicio de tiempos", e);
			}
		}

    	if (fechaActual == null) {
			logger.info("Obteniendo la fecha del sistema");
			fechaActual = new Date();
    	}

    	logger.info("Fecha actual obtenida: {}", fechaActual);

		return fechaActual;
	}

}
