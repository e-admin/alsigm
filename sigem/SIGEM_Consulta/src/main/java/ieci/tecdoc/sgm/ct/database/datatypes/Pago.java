package ieci.tecdoc.sgm.ct.database.datatypes;

/**
 * Interfaz de comportamiento de un objeto representativo 
 * del Pago.
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public interface Pago {

	/**
	 * Devuelve el id de la entidad emisora.
	 * @return String id de la entidad emisora.
	 */
	public abstract String getEntidadEmisoraId();


	/**
	 * Devuelve el id de la autoliquidacion.
	 * @return String id de la autoliquidacion.
	 */   
	public abstract String getAutoliquidacionId();


	/**
	 * Devuelve el importe.
	 * @return String importe.
	 */   
	public abstract String getImporte();


	/**
	 * Establece el id de la entidad emisora.
	 * @param entidadEmisora id de la entidad emisora. 
	 */	
	public abstract void setEntidadEmisoraId(String entidadEmisoraId);


	/**
	 * Establece el id de la autoliquidacion.
	 * @param autoliquidacionId id de la autoliquidacion.
	 */   
	public abstract void setAutoliquidacionId(String autoliquidacionId);

	
	/**
	 * Establece el importe.
	 * @param Importe importe.
	 */   
	public abstract void setImporte(String importe);

	
	public abstract String toXML(boolean header);

	/**
	 * Devuelve los valores de la instancia en una cadena de caracteres.
	 */
	
	
	public abstract String toString();

}