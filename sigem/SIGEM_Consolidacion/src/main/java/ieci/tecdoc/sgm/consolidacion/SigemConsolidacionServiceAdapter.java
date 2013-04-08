package ieci.tecdoc.sgm.consolidacion;

import ieci.tecdoc.sgm.consolidacion.mgr.ConsolidacionManager;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.consolidacion.ConsolidacionException;
import ieci.tecdoc.sgm.core.services.consolidacion.ServicioConsolidacion;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * Implementación del servicio de consolidación de SIGEM.
 *
 */
public class SigemConsolidacionServiceAdapter implements ServicioConsolidacion {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(SigemConsolidacionServiceAdapter.class);
	
	/**
	 * Manager con la lógica de consolidación.
	 */
	private ConsolidacionManager manager = null;
	
	
	/**
	 * Constructor.
	 */
	public SigemConsolidacionServiceAdapter() {
		super();
	}
	
	public ConsolidacionManager getManager() {
		return manager;
	}

	public void setManager(ConsolidacionManager manager) {
		this.manager = manager;
	}

	/**
	 * Realiza la consolidación de registros telemáticos en todas las entidades definidas.
	 * @throws ConsolidacionException si ocurre algún error.
	 */
	public void consolida() throws ConsolidacionException {
		
		try {
			
			// Obtener la lista de entidades
			ServicioEntidades servicioEntidades = LocalizadorServicios.getServicioEntidades();
			List entidades = servicioEntidades.obtenerEntidades();
			
			if (entidades != null) {
		      	for (int i = 0; i < entidades.size(); i++) {
		      		Entidad entidad = (Entidad) entidades.get(i);
		      		try {
			      		if (entidad != null) {
			      			consolida(entidad.getIdentificador());
			      		}
		    		} catch (Throwable e) {
		    			logger.warn("Error capturado en la consolidación de la entidad "
		    					+ entidad.getIdentificador(), e);
		    		}
		      	}
			}
			
		} catch (ConsolidacionException e){
			logger.error("Error inesperado en la consolidación", e);
			throw e;
		} catch (Throwable e){
			logger.error("Error inesperado en la consolidación", e);
			throw new ConsolidacionException(e);
		}
	}

	/**
	 * Realiza la consolidación de registros telemáticos en la entidad.
	 * @param idEntidad Identificador de la entidad.
	 * @throws ConsolidacionException si ocurre algún error.
	 */
	public void consolida(String idEntidad) throws ConsolidacionException {
		
		if (logger.isInfoEnabled()) {
			logger.info("Consolidando la entidad " + idEntidad);
		}

		try {
		
			manager.consolidaEntidad(idEntidad);

      		if (logger.isInfoEnabled()) {
      			logger.info("Consolidación de la entidad " + idEntidad + " finalizada correctamente");
      		}
      		
		} catch (Throwable e) {
			logger.info("Consolidación de la entidad " + idEntidad + " finalizada con errores", e);
			//throw new ConsolidacionException(e);
		}
	}

}
