package ieci.tecdoc.sgm.core.services.estructura_organizativa;

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
