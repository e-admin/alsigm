package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: Notificaciones.java,v 1.1.2.1 2008/01/28 11:20:47 jconca Exp $
 */
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
