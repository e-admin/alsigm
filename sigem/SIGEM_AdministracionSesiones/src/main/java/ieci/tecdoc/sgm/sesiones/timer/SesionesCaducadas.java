package ieci.tecdoc.sgm.sesiones.timer;

import ieci.tecdoc.sgm.sesiones.datatype.Sesiones;

import java.util.TimerTask;

public class SesionesCaducadas extends TimerTask {
	private String key;
	
	public SesionesCaducadas(String key) {
		super();
		this.key = key;
	}
	
	public void run() {
		 Sesiones.getSesiones().remove(key);
		 System.out.println("Sesion " + key + " ha sido eliminada");
	}
	
}
