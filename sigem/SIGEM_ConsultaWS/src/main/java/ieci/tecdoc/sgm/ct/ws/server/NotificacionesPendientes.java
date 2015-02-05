package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: NotificacionesPendientes.java,v 1.1.2.1 2008/01/28 11:20:47 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class NotificacionesPendientes extends RetornoServicio {

	private String notificacionesPendientes;

	public String getNotificacionesPendientes() {
		return notificacionesPendientes;
	}

	public void setNotificacionesPendientes(String notificacionesPendientes) {
		this.notificacionesPendientes = notificacionesPendientes;
	}
}
