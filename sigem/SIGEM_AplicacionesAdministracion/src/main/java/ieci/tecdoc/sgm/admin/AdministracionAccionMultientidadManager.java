package ieci.tecdoc.sgm.admin;

import ieci.tecdoc.sgm.admin.beans.AccionesMultientidad;
import ieci.tecdoc.sgm.admin.database.AccionMultientidadDatos;
import ieci.tecdoc.sgm.admin.interfaces.AccionMultientidad;

import org.apache.log4j.Logger;

public class AdministracionAccionMultientidadManager {

	static final Logger logger = Logger.getLogger(AdministracionAccionMultientidadManager.class);
	
	private AdministracionAccionMultientidadManager(){		
	}

	public static synchronized AccionesMultientidad obtenerAccionesMultientidad()  throws AdministracionException{
		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo Acciones de multientidad...");
		}				
		AccionMultientidadDatos oDatos = new AccionMultientidadDatos();
		AccionesMultientidad accionesMultientidad = null;
		try{
			accionesMultientidad = oDatos.load();
			if(logger.isDebugEnabled()){
				logger.debug("Acciones de multientidad obtenidas.");
			}
		}catch(AdministracionException e) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error obteniendo Acciones de multientidad.", e);
			}
			throw e;
		}catch(Exception e) {
			logger.error("Error obteniendo Acciones de multientidad.", e);
			throw new AdministracionException(AdministracionException.EC_GENERIC_ERROR, e);
		}
		return accionesMultientidad;
	}

	public static AccionMultientidad obtenerAccionMultientidad(String identificador) throws AdministracionException {
		AccionMultientidad accionMultientidad = null;
		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo Accion de multientidad...");
		}				
		if(identificador == null){
			if(logger.isDebugEnabled()){
				logger.debug("Error, parámetros incorrectos.");
			}					
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}
		try{
			AccionMultientidadDatos oDatos = new AccionMultientidadDatos();
			accionMultientidad = oDatos.load(identificador);
			if(logger.isDebugEnabled()){
				logger.debug("Accion de multientidad obtenida.");
			}
		}catch(Exception e) {
			logger.error("Error obteniendo Accion de multientidad.", e);
			throw new AdministracionException(AdministracionException.EC_GENERIC_ERROR, e);
		}
		return accionMultientidad;
	}

}
