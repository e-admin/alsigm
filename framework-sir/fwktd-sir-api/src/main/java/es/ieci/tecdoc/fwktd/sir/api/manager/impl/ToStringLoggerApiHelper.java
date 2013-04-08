package es.ieci.tecdoc.fwktd.sir.api.manager.impl;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import es.ieci.tecdoc.fwktd.sir.api.vo.FicheroIntercambioVO;
import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;

/**
 * Clase auxiliar para generar cadenas de mensajes a utilizar en procesos de logger 
 * 
 * @author IECISA
 *
 *
 */
public class ToStringLoggerApiHelper {
	
	public static String toStringLogger(MensajeVO mensaje){
		String[] excludeFields = null;
		return new ReflectionToStringBuilder(mensaje, null, null, MensajeVO.class, false,false).setExcludeFieldNames(excludeFields).toString();
	}
	
	public static String toStringLogger(FicheroIntercambioVO fichero){
		String[] excludeFields = null;
		return new ReflectionToStringBuilder(fichero, null, null, FicheroIntercambioVO.class, false,false).setExcludeFieldNames(excludeFields).toString();
		
	}
	
	

}
