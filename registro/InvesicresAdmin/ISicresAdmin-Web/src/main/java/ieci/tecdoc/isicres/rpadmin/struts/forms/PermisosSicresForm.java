package ieci.tecdoc.isicres.rpadmin.struts.forms;



import java.util.List;
import java.util.Vector;

import org.apache.struts.validator.ValidatorActionForm;

import es.ieci.tecdoc.isicres.admin.beans.PermisoSicres;

public class PermisosSicresForm extends ValidatorActionForm{
	

	private static final long serialVersionUID = 1L;
	private List permisos;
	
	public PermisosSicresForm(){
		permisos = new Vector();
	}
	
	public int count() {
		return permisos.size();
	}

	public PermisoSicres getPermisos(int index) {
		if(index>=permisos.size()) {
			for(int i=permisos.size();i<=index;i++){
				permisos.add(new PermisoSicres());
			}
		}
		return (PermisoSicres)permisos.get(index);
	}

	public List getLista() {
		return permisos;
	}

	public void setLista(List permisos) {
		this.permisos = permisos;
	}
}
