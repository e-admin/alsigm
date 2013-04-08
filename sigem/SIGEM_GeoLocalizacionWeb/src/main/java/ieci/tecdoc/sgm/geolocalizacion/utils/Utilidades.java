package ieci.tecdoc.sgm.geolocalizacion.utils;

import java.util.StringTokenizer;

import ieci.tecdoc.sgm.core.services.ConstantesServicios;

public class Utilidades {
	
	public static boolean esVacio(String cadena){
		return (cadena == null || VACIO.equals(cadena));
	}
	
	public static long formarCodigo(long codigo){
		return new Long("" + ConstantesServicios.SERVICE_GEOLOCALIZACION_ERROR_PREFIX + codigo).longValue(); 
	}
	
	public static String formarNombreBusqueda(String nombre) {
		StringTokenizer st = new StringTokenizer(nombre, " ");
		StringBuffer sb = new StringBuffer("");
		String aux = "";
		while (st.hasMoreElements()) {
			aux = st.nextToken();
			if (!esVacio(aux)) {
				sb.append(aux).append("*");
			}
		}
		return sb.toString();
	}
	
	private static final String VACIO = "";
}
