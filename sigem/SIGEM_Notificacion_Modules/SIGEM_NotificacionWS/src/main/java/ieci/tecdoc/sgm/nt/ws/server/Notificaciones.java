package ieci.tecdoc.sgm.nt.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Notificaciones extends RetornoServicio {

	private Notificacion[] notificaciones;

	public Notificacion[] getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(Notificacion[] notificaciones) {
		this.notificaciones = notificaciones;
	}
}
