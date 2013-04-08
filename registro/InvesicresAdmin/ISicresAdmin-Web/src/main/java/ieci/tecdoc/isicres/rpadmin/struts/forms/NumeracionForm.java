package ieci.tecdoc.isicres.rpadmin.struts.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorActionForm;

import es.ieci.tecdoc.isicres.admin.beans.Contador;
import es.ieci.tecdoc.isicres.admin.beans.Contadores;

public class NumeracionForm extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String central;
	private String anyo;
	private List contadores;
	
	public NumeracionForm(){
		contadores = new ArrayList();
	}

	public int count() {
		return contadores.size();
	}
	
	public Contador getContadores(int index) {
		if(index>=contadores.size()) {
			for(int i=contadores.size();i<=index;i++){
				contadores.add(new Contador());
			}
		}
		return (Contador)contadores.get(index);
	}
	
	public void add(Contador contador) {
		contadores.add(contador);
	}

	public List getLista() {
		return contadores;
	}
	
	public void setLista(List contadores) {
		this.contadores = contadores;
	}
	
	public Contadores getContadoresOficina() {
		Contadores contadoresOficina = new Contadores();
		for( int i = 0; i < contadores.size(); i++) {
			contadoresOficina.add((Contador)contadores.get(i));
		}
		return contadoresOficina;
	}
	
	public void clear() {
		contadores = new ArrayList();
	}
	
	public String getCentral() {
		return central;
	}
	public void setCentral(String central) {
		this.central = central;
	}

	public String getAnyo() {
		return anyo;
	}

	public void setAnyo(String anyo) {
		this.anyo = anyo;
	}
}
