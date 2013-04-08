package ieci.tdw.ispac.designer.client.objetos;

import ieci.tdw.ispac.designer.client.diagrama.DiagramBuilderExample;

import java.util.Comparator;

public class ComparadorEntidades implements Comparator{

	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		
		Entidad e1= (Entidad) o1;
		Entidad e2= (Entidad) o2;
		
		String descripcionE1=e1.getNombre(); 
		if(e1.getDescripcion()!=null && e1.getDescripcion().size()>0)
		descripcionE1=(String) e1.getDescripcion().get(DiagramBuilderExample.locale);
		String descripcionE2=e2.getNombre(); 
		if(e2.getDescripcion()!=null && e2.getDescripcion().size()>0)
		descripcionE2=(String) e2.getDescripcion().get(DiagramBuilderExample.locale);
        if(descripcionE1==null ) descripcionE1=(String) e1.getDescripcion().get(DiagramBuilderExample.localePorDefecto);
        if(descripcionE2==null) descripcionE2=(String) e1.getDescripcion().get(DiagramBuilderExample.localePorDefecto);
		return descripcionE1.compareTo(descripcionE2);
	}


}
