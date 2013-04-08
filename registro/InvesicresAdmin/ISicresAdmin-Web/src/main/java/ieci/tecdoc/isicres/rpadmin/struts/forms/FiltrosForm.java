package ieci.tecdoc.isicres.rpadmin.struts.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorActionForm;

import es.ieci.tecdoc.isicres.admin.beans.Filtro;

public class FiltrosForm extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;
	private List filtros;
	private String tipoCampo;
	
	public String getTipoCampo() {
		return tipoCampo;
	}

	public void setTipoCampo(String tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	public FiltrosForm(){
		filtros = new ArrayList();
	}

	public int count() {
		return filtros.size();
	}
	
	public Filtro getFiltros(int index) {
		if(index>=filtros.size()) {
			for(int i=filtros.size();i<=index;i++){
				filtros.add(new Filtro());
			}
		}
		return (Filtro)filtros.get(index);
	}
	
	public void add(Filtro filtro) {
		filtros.add(filtro);
	}
	
	public void remove(int index) {
		filtros.remove(index);
	}

	public List getLista() {
		return filtros;
	}
	
	public void setLista(List filtros) {
		this.filtros = filtros;
	}
	
	public void setFiltro(int index, Filtro filtro) {
		this.filtros.set(index, filtro);
	}
	
}
