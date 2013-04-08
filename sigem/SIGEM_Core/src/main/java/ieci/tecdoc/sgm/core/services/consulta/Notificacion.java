package ieci.tecdoc.sgm.core.services.consulta;

public class Notificacion {

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
	public String getFechaNotificacion() {
		return fechaNotificacion;
	}
	public void setFechaNotificacion(String fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}
	public String getNotificacionId() {
		return notificacionId;
	}
	public void setNotificacionId(String notificacionId) {
		this.notificacionId= notificacionId;
	}
	public String getHitoId() {
		return hitoId;
	}
	public void setHitoId(String hitoId) {
		this.hitoId = hitoId;
	}
	public String getServicioNotificionesId() {
		return servicioNotificionesId;
	}
	public void setServicioNotificionesId(
			String servicioNotificionesId) {
		this.servicioNotificionesId = servicioNotificionesId;
	}
	
	
}
