package ieci.tecdoc.sgm.core.services.notificaciones;

import java.util.ArrayList;
import java.util.List;

public class Notificaciones {

    private List notificaciones;

    public Notificaciones(){
    	notificaciones = new ArrayList();
    }
	public List getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List notificaciones) {
		this.notificaciones = notificaciones;
	}
}
