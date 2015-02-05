package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class DepartamentosLista extends RetornoServicio {

	private Departamento[] departamentos;
	
	public Departamento[] getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(Departamento[] departamentos) {
		this.departamentos = departamentos;
	}
	
}
