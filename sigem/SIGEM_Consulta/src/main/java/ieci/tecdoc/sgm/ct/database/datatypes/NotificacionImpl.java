package ieci.tecdoc.sgm.ct.database.datatypes;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.util.Date;


/**
 * Bean con las propiedades de la Notificación.
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 17-may-2007
 */
public class NotificacionImpl implements Notificacion {

	protected String notificacionId;

	protected Date fechaNotificacion;

	protected String deu;
	
	protected String servicioNotificacionesId;
	
	protected String expediente;
	
	protected String descripcion;
	
	protected String hitoId;

	/**
	 * Constructor de clase
	 */
	public NotificacionImpl(){
		
		notificacionId = null;
		fechaNotificacion = null;
		deu = null;
		servicioNotificacionesId = null;
		expediente = null;
		descripcion = null;
		hitoId = null;
	}
	

	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getDEU() {
		return deu;
	}


	public void setDEU(String deu) {
		this.deu = deu;
	}


	public String getExpediente() {
		return expediente;
	}


	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}


	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}


	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}


	public String getHitoId() {
		return hitoId;
	}


	public void setHitoId(String hitoId) {
		this.hitoId = hitoId;
	}


	public String getNotificacionId() {
		return notificacionId;
	}


	public void setNotificacionId(String notificacionId) {
		this.notificacionId = notificacionId;
	}


	public String getServicioNotificacionesId() {
		return servicioNotificacionesId;
	}


	public void setServicioNotificacionesId(String servicioNotificacionesId) {
		this.servicioNotificacionesId = servicioNotificacionesId;
	}

	/**
	 * Recoge los valores de la instancia en una cadena xml
	 * @param header Si se incluye la cabecera
	 * @return los datos en formato xml
	 */
	public String toXML(boolean header) {
		XmlTextBuilder bdr;
		String tagName = "Notificacion";

		bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();

		bdr.addOpeningTag(tagName);

		bdr.addSimpleElement("ID", notificacionId);
		bdr.addSimpleElement("Fecha", fechaNotificacion.toString());
		bdr.addSimpleElement("DEU", deu);
		bdr.addSimpleElement("Servicio_Notificaciones", servicioNotificacionesId);
		bdr.addSimpleElement("Expediente", expediente);
		bdr.addSimpleElement("Descripcion", descripcion);
		bdr.addSimpleElement("Hito", hitoId);
		
		bdr.addClosingTag(tagName);

		return bdr.getText();
	}

	/**
	 * Devuelve los valores de la instancia en una cadena de caracteres.
	 */
	public String toString() {
		return toXML(false);
	}

}