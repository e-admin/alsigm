package es.ieci.tecdoc.isicres.admin.estructura.beans;

public class Departamentos {
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
