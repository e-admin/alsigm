package ieci.tdw.ispac.designer.client.objetos;

import ieci.tdw.ispac.designer.client.diagrama.DiagramBuilderExample;

import java.util.Comparator;

public class ComparadorCampos implements Comparator{

	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		
		CampoEntidad c1= (CampoEntidad) o1;
		CampoEntidad c2= (CampoEntidad) o2;
		
		String descripcionC1=c1.getNombre(); 
		if(c1.getDescripcion()!=null &&  c1.getDescripcion().size()>0)
		descripcionC1=(String) c1.getDescripcion().get(DiagramBuilderExample.locale);
		String descripcionC2=c2.getNombre(); 
		if(c2.getDescripcion()!=null && c2.getDescripcion().size()>0)
		descripcionC2=(String) c2.getDescripcion().get(DiagramBuilderExample.locale);

		return descripcionC1.compareTo(descripcionC2);
	}


}
