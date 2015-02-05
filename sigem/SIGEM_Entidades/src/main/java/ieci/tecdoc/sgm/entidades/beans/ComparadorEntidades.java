package ieci.tecdoc.sgm.entidades.beans;

import java.util.Comparator;

public class ComparadorEntidades implements Comparator {
	
	public int compare(Object o1, Object o2) {
		return ((Entidad)o1).getIdentificador().compareToIgnoreCase(((Entidad)o2).getIdentificador());
	}
	
}
