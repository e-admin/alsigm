package ieci.tecdoc.sgm.admsistema.util;

import ieci.tecdoc.sgm.admsistema.proceso.Proceso;

import java.util.Comparator;

public class ProcesosSorter implements Comparator{
	public int compare(Object o1, Object o2) {
		String str1 = ((Proceso)o1).getNombre();
		String str2 = ((Proceso)o2).getNombre();
		return str1.compareTo(str2);
	}
}
