package es.ieci.tecdoc.sgm.fwktd.sir.api.job;


import java.util.Iterator;
import java.util.List;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.fwktd.sir.core.service.ServicioIntercambioRegistral;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;

/**
 * Job que comprueba el time-out de la recepción de mensajes de ACK o ERROR en los envíos
 * de ficheros de datos de intercambio.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class SigemComprobarTimeOutEnviosJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory
			.getLogger(SigemComprobarTimeOutEnviosJob.class);

	/**
	 * Servicio de intercambio registral.
	 */
	private ServicioIntercambioRegistral servicioIntercambioRegistral = null;

	/**
	 * Constructor.
	 */
	public SigemComprobarTimeOutEnviosJob() {
		super();
	}

	public ServicioIntercambioRegistral getServicioIntercambioRegistral() {
		return servicioIntercambioRegistral;
	}

	public void setServicioIntercambioRegistral(
			ServicioIntercambioRegistral servicioIntercambioRegistral) {
		this.servicioIntercambioRegistral = servicioIntercambioRegistral;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {

		logger.debug("Inicio del job para la comprobación del time-out en los envíos");

		try {

			ServicioEntidades servicio = LocalizadorServicios.getServicioEntidades();
			
			// Procesar los ficheros
			List<Entidad> listaEntidades = servicio.obtenerEntidades();
			
			
			logger.debug("Numero Entidades recuperadas:"+listaEntidades.size());
			
			for (Iterator iterator = listaEntidades.iterator(); iterator
					.hasNext();) {
				Entidad entidad = (Entidad) iterator.next();
				
				try{
					entidad.getCodigoINE();
					logger.debug("entdidad:"+entidad.getIdentificador());
					MultiEntityContextHolder.setEntity(entidad.getIdentificador());
					getServicioIntercambioRegistral().comprobarTimeOutEnvios();
					context.setResult("Ok");
				
				} catch (Throwable e) {
					logger.error("Error al lanzar la comprobación del time-out en los envíos para la entidad:"+entidad.getIdentificador(), e);
					context.setResult("Error al lanzar la comprobación del time-out en los envíos para la entidad:"+entidad.getIdentificador() 
							+ e.toString());
					throw new JobExecutionException(
							"Error al lanzar la comprobación del time-out en los envíospara la entidad:"+entidad.getIdentificador(), e);
				} finally {
					logger.info("Fin del job para la comprobación del time-out en los envíos para la entidad:"+entidad.getIdentificador());
				}
			}
			
			

		} catch (Throwable e) {
			logger.error("Error al lanzar la comprobación del time-out en los envíos", e);
			context.setResult("Error al lanzar la comprobación del time-out en los envíos: "
					+ e.toString());
			throw new JobExecutionException(
					"Error al lanzar la comprobación del time-out en los envíos", e);
		} finally {
			logger.info("Fin del job para la comprobación del time-out en los envíos");
		}
	}
	
	
	
	

}

