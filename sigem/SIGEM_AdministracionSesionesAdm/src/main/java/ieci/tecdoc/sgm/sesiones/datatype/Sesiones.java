package ieci.tecdoc.sgm.sesiones.datatype;

import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Timer;

public class Sesiones {
	
	private static final String KEY_UNIDADES = "sesion.unidades";
	private static final String KEY_DURACION = "sesion.duracion";
	private static final String KEY_VALUE_MINUTOS = "M";
	private static final String KEY_VALUE_HORAS = "H";
	
	private static HashMap sesiones = new HashMap();
	
	private static long duracionSesion;
	
	private static Timer timerCaducadas = new Timer();
	
	static {
		ResourceBundle rb = ResourceBundle.getBundle("ieci.tecdoc.sgm.sesiones.resources.configuracion");
		String unidades = (String)rb.getObject(KEY_UNIDADES);
		String duracion = (String)rb.getObject(KEY_DURACION);
		
		if (KEY_VALUE_HORAS.equalsIgnoreCase(unidades)) {
			duracionSesion = new Long(duracion).longValue() * 1000 * 60 * 60;
		} else if (KEY_VALUE_MINUTOS.equalsIgnoreCase(unidades)) {
			duracionSesion = new Long(duracion).longValue() * 1000 * 60;
		} 
	}
	
	public static HashMap getSesiones() {
		return sesiones;
	}

	public static long getDuracionSesion() {
		return duracionSesion;
	}
	
	public static Timer getTimerCaducadas() {
		return timerCaducadas;
	}
}
