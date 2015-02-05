package ieci.tecdoc.sgm.rpadmin.beans;

public class SicresTiposOrganizacionesImpl  extends ColeccionGeneralImpl{

	public SicresTipoOrganizacionImpl get(int index) {
		return (SicresTipoOrganizacionImpl)lista.get(index);
	}

	public void add(SicresTipoOrganizacionImpl tipo) {
		lista.add(tipo);
	}
}
