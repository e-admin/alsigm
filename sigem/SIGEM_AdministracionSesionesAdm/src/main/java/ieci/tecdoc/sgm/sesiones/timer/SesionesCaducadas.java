package ieci.tecdoc.sgm.sesiones.timer;

import ieci.tecdoc.sgm.sesiones.SesionManager;
import java.util.TimerTask;

public class SesionesCaducadas extends TimerTask {
	private String key;
	
	public SesionesCaducadas(String key) {
		super();
		this.key = key;
	}
	
	public void run() {
		SesionManager.caducarSesion(key);
		System.out.println("Sesion de administracion " + key + " ha sido eliminada");
	}
	
}
