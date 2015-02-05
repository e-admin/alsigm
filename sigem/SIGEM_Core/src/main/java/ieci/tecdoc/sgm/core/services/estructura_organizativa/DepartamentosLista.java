package ieci.tecdoc.sgm.core.services.estructura_organizativa;

import java.util.ArrayList;

public class DepartamentosLista {

	private ArrayList listaDepartamentos;
	
	public DepartamentosLista() {
		listaDepartamentos=new ArrayList();
	}
	
	public void add(Departamento departamento){
		listaDepartamentos.add(departamento);
	}
	
	public Departamento get(int index){
		return (Departamento) listaDepartamentos.get(index);
	}
	
	public int count(){
		return listaDepartamentos.size();
	}
	
	public void remove(int index) {
		listaDepartamentos.remove(index);
	}
}
