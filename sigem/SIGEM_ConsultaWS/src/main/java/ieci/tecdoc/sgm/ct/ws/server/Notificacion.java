package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: Notificacion.java,v 1.1.2.1 2008/01/28 11:20:47 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Notificacion extends RetornoServicio {

	private String notificacionId;
	private String fechaNotificacion;
	private String deu;
	private String servicioNotificionesId;
	private String expediente;
	private String descripcion;
	private String hitoId;
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDeu() {
		return deu;
	}
	public void setDeu(String deu) {
		this.deu = deu;
	}
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public String getFechaNotificacion() {
		return fechaNotificacion;
	}
	public void setFechaNotificacion(String fechaNotificacion) {
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
	public String getServicioNotificionesId() {
		return servicioNotificionesId;
	}
	public void setServicioNotificionesId(String servicioNotificionesId) {
		this.servicioNotificionesId = servicioNotificionesId;
	}
}
