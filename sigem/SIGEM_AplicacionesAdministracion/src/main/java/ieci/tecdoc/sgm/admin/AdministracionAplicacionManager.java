package ieci.tecdoc.sgm.admin;

import java.util.MissingResourceException;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.admin.AdministracionException;
import ieci.tecdoc.sgm.admin.beans.Aplicaciones;
import ieci.tecdoc.sgm.admin.database.AplicacionDatos;
import ieci.tecdoc.sgm.admin.interfaces.Aplicacion;


/**
 * $Id: AdministracionAplicacionManager.java,v 1.1.2.2 2008/04/22 18:25:40 afernandez Exp $
 */

public class AdministracionAplicacionManager {

	static final Logger logger = Logger.getLogger(AdministracionAplicacionManager.class);
	static Aplicaciones aplicaciones = null;
	private AdministracionAplicacionManager(){		
	}
	static{
		try{
			obtenerAplicaciones();
		}catch(AdministracionException e){
			MissingResourceException e2 = new MissingResourceException(
					e.getMessage(),
					Aplicacion.class.getName(), "Aplicaciones.xml");
			throw (RuntimeException) e2;			
		}
	}

	public static synchronized Aplicaciones obtenerAplicaciones()  throws AdministracionException{
		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo Aplicaciones...");
		}				
		AplicacionDatos oDatos = new AplicacionDatos();
		try{
			aplicaciones = oDatos.loadAll();
			if(logger.isDebugEnabled()){
				logger.debug("Aplicaciones obtenida.");
			}
		}catch(AdministracionException e) {
			if(logger.isDebugEnabled()) {
				logger.debug("Error obteniendo Aplicaciones.", e);
			}
			throw e;
		}catch(Exception e) {
			logger.error("Error obteniendo Aplicaciones.", e);
			throw new AdministracionException(AdministracionException.EC_GENERIC_ERROR, e);
		}
		return aplicaciones;
	}

	public static Aplicacion obtenerAplicacion(String identificador) throws AdministracionException {
		Aplicacion aplicacion = null;
		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo Aplicacion...");
		}				
		if(identificador == null){
			if(logger.isDebugEnabled()){
				logger.debug("Error, parámetros incorrectos.");
			}					
			throw new AdministracionException(AdministracionException.EC_BAD_PARAMETERS);
		}
		try{
			for (int i = 0; i < aplicaciones.count();i++){
				if (identificador.equals(aplicaciones.get(i).getIdentificador())){
					aplicacion = aplicaciones.get(i);
					break;
				}
			}
			if(logger.isDebugEnabled()){
				logger.debug("Aplicacion obtenida.");
			}
		}catch(Exception e) {
			logger.error("Error obteniendo Aplicacion.", e);
			throw new AdministracionException(AdministracionException.EC_GENERIC_ERROR, e);
		}
		return aplicacion;
	}




}
