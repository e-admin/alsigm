package ieci.tecdoc.isicres.rpadmin.struts.forms;

import ieci.tecdoc.isicres.rpadmin.struts.beans.Distribucion;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorActionForm;

import es.ieci.tecdoc.isicres.admin.beans.Distribuciones;

public class DistribucionUnidadForm extends ValidatorActionForm {

	private static final long serialVersionUID = 4353035975351667585L;
	
private List distribuciones;
	
	public DistribucionUnidadForm(){
		distribuciones = new ArrayList();
	}
	
	public int count() {
		return distribuciones.size();
	}

	public Distribuciones getDistribuciones(int index) {
		if(index>=distribuciones.size()) {
			distribuciones.add(index, new Distribuciones());
		}
		return (Distribuciones)distribuciones.get(index);
	}

	public void setDistribuciones(int index, Distribucion distribucion) {
		if(index<distribuciones.size()) {
			distribuciones.set(index, distribucion);
		} else {
			distribuciones.add(index, distribucion);
		}
	}

	public void add(Distribucion distribucion) {
		distribuciones.add(distribucion);
	}
	
	public List getLista() {
		return distribuciones;
	}

	public void setLista(List distribucion) {
		this.distribuciones = distribucion;
	}
	
}
