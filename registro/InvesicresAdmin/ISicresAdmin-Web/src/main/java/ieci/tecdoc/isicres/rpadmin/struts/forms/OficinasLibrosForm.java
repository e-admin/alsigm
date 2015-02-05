package ieci.tecdoc.isicres.rpadmin.struts.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.struts.validator.ValidatorActionForm;

import es.ieci.tecdoc.isicres.admin.beans.PermisoSicres;

public class OficinasLibrosForm extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;
	private List ids;
	private List permisos;
	
	public OficinasLibrosForm(){
		ids = new ArrayList();
		permisos = new Vector();
	}
	
	/* Metodos para controlar las oficinas seleccionadas */
	public int countOficinas() {
		return ids.size();
	}

	public String getIds(int index) {
		if(index>=ids.size()) {
			for(int i=ids.size();i<=index;i++){
				ids.add(new String());
			}
		}
		return (String)ids.get(index);
	}

	public void setIds(int index, String id) {
		ids.add(id);
	}
	
	/* Metodos para controlar los permisos */
	public int countPermisos() {
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
