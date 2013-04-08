package es.ieci.tecdoc.isicres.admin.core.beans;


public class SicresOrganizacionesImpl  extends ColeccionGeneralImpl{

	public SicresOrganizacionImpl get(int index) {
		return (SicresOrganizacionImpl)lista.get(index);
	}

	public void add(SicresOrganizacionImpl organizacion) {
		lista.add(organizacion);
	}
}
