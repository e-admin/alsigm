package ieci.tecdoc.sgm.ct.database.datatypes;

/**
 * Interfaz de comportamiento de un objeto representativo 
 * de la Subsanación.
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public interface Subsanacion {
	
	public abstract String toXML(boolean header);

	/**
	 * Devuelve los valores de la instancia en una cadena de caracteres.
	 */
	
	
	public abstract String toString();

}