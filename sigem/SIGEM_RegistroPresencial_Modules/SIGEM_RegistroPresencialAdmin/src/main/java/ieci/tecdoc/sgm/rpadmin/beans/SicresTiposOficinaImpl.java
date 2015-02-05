package ieci.tecdoc.sgm.rpadmin.beans;

public class SicresTiposOficinaImpl  extends ColeccionGeneralImpl{

	public SicresTipoOficinaImpl get(int index) {
		return (SicresTipoOficinaImpl)lista.get(index);
	}

	public void add(SicresTipoOficinaImpl tipo) {
		lista.add(tipo);
	}
}
