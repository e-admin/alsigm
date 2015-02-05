package es.ieci.tecdoc.fwktd.core.model;

/*
 * 
 */

import java.io.Serializable;

/**
 * 
 * Interface marcador para objetos que van a contener datos de aplicaciones.
 * Patrón <code>Value Object</code>
 */
public interface ValueObject extends Serializable, Cloneable {
	

	
	/**
	 * Obtiene la representación en XML del <code>VO</code>
	 * @return cadena que contiene la representación en XML del <code>VO</code>
	 */
	String toXML();

}