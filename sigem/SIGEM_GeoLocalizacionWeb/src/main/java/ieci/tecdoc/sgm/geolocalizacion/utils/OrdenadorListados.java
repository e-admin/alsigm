package ieci.tecdoc.sgm.geolocalizacion.utils;

import ieci.tecdoc.sgm.core.services.geolocalizacion.Portal;
import ieci.tecdoc.sgm.core.services.geolocalizacion.Via;

import java.util.Comparator;

public class OrdenadorListados implements Comparator{
	private String tipo = null;
	
	public static final String TIPO_VIA = "via";
	public static final String TIPO_PORTAL = "portal";
	
	public int compare(Object o1, Object o2) {
		if (TIPO_VIA.equals(tipo)) {
			String descripcion1 = ((Via)o1).getNombreVia();
			String descripcion2 = ((Via)o2).getNombreVia();
			return descripcion1.compareTo(descripcion2);
		} else if (TIPO_PORTAL.equals(tipo)) {
			int num1, num2;
			String loc1 = "", loc2 = "";
			try {
				num1 = new Integer(((Portal)o1).getNumPortal()).intValue();
			} catch(Exception e) {
				num1 = obtenerNumero(((Portal)o1).getNumPortal());
				loc1 = obtenerLocalizador(((Portal)o1).getNumPortal());
			}
			try {
				num2 = new Integer(((Portal)o2).getNumPortal()).intValue();
			} catch(Exception e) {
				num2 = obtenerNumero(((Portal)o2).getNumPortal());
				loc2 = obtenerLocalizador(((Portal)o2).getNumPortal());
			}
			if (num1 < num2) return -1;
			else if (num1 > num2) return 1;
			else return loc1.compareTo(loc2);
		}
		return 0;
	}

	private int obtenerNumero(String cadena){
		String num = "";
		for (int i=0; i<cadena.length(); i++){
			if (Character.isDigit(cadena.charAt(i))) {
				num += cadena.charAt(i);
			} else break;
		}
		if (!"".equals(num))
			return new Integer(num).intValue();
		else return 0;
	}
	
	private String obtenerLocalizador(String cadena){
		String loc = "";
		int index = 0;
		for (int i=0; i<cadena.length(); i++){
			if (!Character.isDigit(cadena.charAt(i))) {
				index = i;
			}
		}
		for(int j=index; j<cadena.length(); j++)
			loc += cadena.charAt(j);
		return loc;
	}

	public OrdenadorListados(String tipo) {
		super();
		this.tipo = tipo;
	}

	
}
