package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Departamentos extends RetornoServicio{
	
	private DepartamentosLista departamentosLista;
	
	public Departamentos() {
		departamentosLista=new DepartamentosLista();
	}

	public DepartamentosLista getDepartamentosLista() {
		return departamentosLista;
	}

	public void setDepartamentosLista(DepartamentosLista departamentosLista) {
		this.departamentosLista = departamentosLista;
	}
	
}
