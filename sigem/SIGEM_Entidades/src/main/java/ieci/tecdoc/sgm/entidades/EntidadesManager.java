package ieci.tecdoc.sgm.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.entidades.beans.ComparadorEntidades;
import ieci.tecdoc.sgm.entidades.beans.CriterioBusquedaEntidades;
import ieci.tecdoc.sgm.entidades.beans.Entidad;
import ieci.tecdoc.sgm.entidades.database.EntidadDatos;
import ieci.tecdoc.sgm.entidades.database.EntidadTabla;

import ieci.tecdoc.sgm.entidades.exception.EntidadException;

public class EntidadesManager {

	private static final Logger logger = Logger.getLogger(EntidadesManager.class);
	
	public static Entidad nuevaEntidad(Entidad poEntidad) throws EntidadException{
		if(logger.isDebugEnabled()){
			logger.debug("Creando entidad...");
		}
		if(poEntidad == null){
			if(logger.isDebugEnabled()){
				logger.debug("Error, parámetros incorrectos.");
			}
			throw new EntidadException(EntidadException.EC_BAD_PARAMETERS);
		}
		String identificador = obtenerIdentificadorEntidad();
		poEntidad.setIdentificador(identificador);
		EntidadDatos oDatos = new EntidadDatos(poEntidad);
		try{
			oDatos.add();
			if(logger.isDebugEnabled()){
				logger.debug("Entidad creada.");
			}
		}catch(EntidadException e){
			logger.error("Error creando nueva entidad [nuevaEntidad][EntidadException]", e.fillInStackTrace());
			throw e;
		}catch(Exception e){
			logger.error("Error creando nueva entidad [nuevaEntidad][Exception]", e.fillInStackTrace());
			throw new EntidadException(EntidadException.EC_GENERIC_ERROR, e);
		}
		return poEntidad;
	}

	public static void eliminarEntidad(Entidad poEntidad) throws EntidadException{
		if(logger.isDebugEnabled()){
			logger.debug("Eliminando entidad...");
		}		
		if(poEntidad == null){
			if(logger.isDebugEnabled()){
				logger.debug("Error, parámetros incorrectos.");
			}			
			throw new EntidadException(EntidadException.EC_BAD_PARAMETERS);
		}
		EntidadDatos oDatos = new EntidadDatos(poEntidad);
		try{
			oDatos.delete();
			if(logger.isDebugEnabled()){
				logger.debug("Entidad eliminada.");
			}
		}catch(EntidadException e){
			logger.error("Error eliminando entidad [eliminarEntidad][EntidadException]", e.fillInStackTrace());
		}catch(Exception e){
			logger.error("Error eliminando entidad [eliminarEntidad][Exception]", e.fillInStackTrace());
		}
	}

	public static Entidad actualizarEntidad(Entidad poEntidad) throws EntidadException{
		if(logger.isDebugEnabled()){
			logger.debug("Actualizando entidad...");
		}				
		if(poEntidad == null){
			if(logger.isDebugEnabled()){
				logger.debug("Error, parámetros incorrectos.");
			}					
			throw new EntidadException(EntidadException.EC_BAD_PARAMETERS);
		}
		EntidadDatos oDatos = new EntidadDatos(poEntidad);
		try{
			oDatos.update();
			if(logger.isDebugEnabled()){
				logger.debug("Entidad actualizada.");
			}
		}catch(EntidadException e){
			logger.error("Error actualizando entidad [actualizarEntidad][EntidadException]", e.fillInStackTrace());
			throw e;
		}catch(Exception e){
			logger.error("Error actualizando entidad [actualizarEntidad][Exception]", e.fillInStackTrace());
			throw new EntidadException(EntidadException.EC_GENERIC_ERROR, e);
		}
		return poEntidad;
	}

	
	public static Entidad obtenerEntidad(String identificador) throws EntidadException{
		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo entidad...");
		}				
		if(identificador == null){
			if(logger.isDebugEnabled()){
				logger.debug("Error, parámetros incorrectos.");
			}					
			throw new EntidadException(EntidadException.EC_BAD_PARAMETERS);
		}
		EntidadDatos oDatos = new EntidadDatos();
		try{
			oDatos.load(identificador);
			if(logger.isDebugEnabled()){
				logger.debug("Entidad obtenida.");
			}
			
		}catch(EntidadException e){
			logger.error("Error obteniendo entidad [obtenerEntidad][EntidadException]", e.fillInStackTrace());
			throw e;
		}catch(Exception e){
			logger.error("Error obteniendo entidad [obtenerEntidad][Exception]", e.fillInStackTrace());
			throw new EntidadException(EntidadException.EC_GENERIC_ERROR, e);
		}
		return obtenerEntidad(oDatos);
	}

	
	public static List buscarEntidades(CriterioBusquedaEntidades poCriterio) throws EntidadException{
		if(logger.isDebugEnabled()){
			logger.debug("Buscando entidades...");
		}				
		if(poCriterio == null){
			if(logger.isDebugEnabled()){
				logger.debug("Error, parámetros incorrectos.");
			}					
			throw new EntidadException(EntidadException.EC_BAD_PARAMETERS);
		}
		List oDatos = null;
		try{
			oDatos = new EntidadTabla().buscarEntidades(poCriterio);
			if(logger.isDebugEnabled()){
				logger.debug("Entidades obtenidas.");
			}
			
		}catch(EntidadException e){
			logger.error("Error buscando entidades [buscarEntidades][EntidadException]", e.fillInStackTrace());
			throw e;
		}catch(Exception e){
			logger.error("Error buscando entidades [buscarEntidades][Exception]", e.fillInStackTrace());
			throw new EntidadException(EntidadException.EC_GENERIC_ERROR, e);
		}
		return obtenerEntidades(oDatos);
	}

	
	public static List obtenerEntidades() throws EntidadException{
		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo entidades...");
		}				
		List oDatos = null;
		try{
			oDatos = new EntidadTabla().obtenerEntidades();
			if(logger.isDebugEnabled()){
				logger.debug("Entidades obtenidas.");
			}
			
		}catch(EntidadException e){
			logger.error("Error obteniendo entidades [obtenerEntidades][EntidadException]", e.fillInStackTrace());
			throw e;
		}catch(Exception e){
			logger.error("Error obteniendo entidades [obtenerEntidades][Exception]", e.fillInStackTrace());
			throw new EntidadException(EntidadException.EC_GENERIC_ERROR, e);
		}
		return obtenerEntidades(oDatos);
	}
	
	public static String obtenerIdentificadorEntidad () throws EntidadException{
		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo identificador de entidad...");
		}				
		String identificador;
		try{
			List entidades = obtenerEntidades();
			if (entidades.size() > 0) {
				Entidad[] entidadesArray = new Entidad[entidades.size()];
				for(int ind=0; ind<entidades.size(); ind++)
					entidadesArray[ind] = (Entidad)entidades.get(ind);
				Arrays.sort(entidadesArray, new ComparadorEntidades());
				Entidad entidad = entidadesArray[entidadesArray.length-1];
				String aux =new String ("" + (new Integer(entidad.getIdentificador()).intValue() + 1));
				for (int i=aux.length(); i<3; i++) 
					aux = "0" + aux;
				identificador = aux;
			} else identificador = "001";
		}catch(EntidadException e){
			logger.error("Error obteniendo identificador de entidad [obtenerIdentificadorEntidad][EntidadException]", e.fillInStackTrace());
			throw e;
		}catch(Exception e){
			logger.error("Error obteniendo identificador de entidad [obtenerIdentificadorEntidad][Exception]", e.fillInStackTrace());
			throw new EntidadException(EntidadException.EC_GENERIC_ERROR, e);
		}
		return identificador;
	}
	
	private static Entidad obtenerEntidad(EntidadDatos poEntidad){
		if(poEntidad == null){
			return null;
		}
		Entidad oEntidad = new Entidad();
		oEntidad.setIdentificador(poEntidad.getIdentificador());
		oEntidad.setNombreCorto(poEntidad.getNombreCorto());
		oEntidad.setNombreLargo(poEntidad.getNombreLargo());
		oEntidad.setCodigoINE(poEntidad.getCodigoINE());
		
		return oEntidad;
	}
	
	private static List obtenerEntidades(List poEntidades){
		if(poEntidades == null){
			return null;
		}
		List oEntidades = new ArrayList(poEntidades.size());
		Iterator oIterador = poEntidades.iterator();
		while(oIterador.hasNext()){
			oEntidades.add(obtenerEntidad((EntidadDatos)oIterador.next()));
		}
		return oEntidades;
	}
	
}
