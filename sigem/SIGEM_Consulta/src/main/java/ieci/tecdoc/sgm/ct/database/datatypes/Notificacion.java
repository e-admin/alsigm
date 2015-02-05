package ieci.tecdoc.sgm.ct.database.datatypes;

import java.util.Date;;

/**
 * Interfaz de comportamiento de un objeto representativo 
 * de la Notificación.
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public interface Notificacion {

	/**
	 * Devuelve el id de la notificacion.
	 * @return String id de la notificacion.
	 */
	public abstract String getNotificacionId();


	/**
	 * Devuelve la fecha de la notificacion.
	 * @return String fecha de la notificacion.
	 */   
	public abstract Date getFechaNotificacion();


	/**
	 * Devuelve el DEU.
	 * @return String DEU.
	 */   
	public abstract String getDEU();

	/**
	 * Devuelve el identificador del servicio de notificaciones.
	 * @return String identificador del servicio de notificaciones.
	 */   
	public abstract String getServicioNotificacionesId();

	
	/**
	 * Devuelve el expediente.
	 * @return String expediente.
	 */   
	public abstract String getExpediente();

	
	/**
	 * Devuelve la descripción.
	 * @return String Descripción.
	 */   
	public abstract String getDescripcion();

	
	/**
	 * Establece el identificador del hito.
	 * @param String identificador del hito.
	 */   
	public abstract String getHitoId();


	/**
	 * Establece un Guid de un Hito para el Fichero.
	 * @param guidHito Guid de un Hito. 
	 */	
	public abstract void setNotificacionId(String notificacionId);


	/**
	 * Establece la fecha de la notificacion.
	 * @param String fecha de la notificacion.
	 */   
	public abstract void setFechaNotificacion(Date fechaNotificacion);


	/**
	 * Establece el DEU.
	 * @param String DEU.
	 */   
	public abstract void setDEU(String deu);

	/**
	 * Establece el identificador del servicio de notificaciones.
	 * @param String identificador del servicio de notificaciones.
	 */   
	public abstract void setServicioNotificacionesId(String servicioNotificacionesId);

	
	/**
	 * Establece el expediente.
	 * @param String expediente.
	 */   
	public abstract void setExpediente(String expediente);

	
	/**
	 * Establece la descripción.
	 * @param String Descripción.
	 */   
	public abstract void setDescripcion(String descripcion);

	
	/**
	 * Establece el identificador del hito.
	 * @param String identificador del hito.
	 */   
	public abstract void setHitoId(String hitoId);
	
	
	public abstract String toXML(boolean header);

	/**
	 * Devuelve los valores de la instancia en una cadena de caracteres.
	 */
	
	
	public abstract String toString();

}