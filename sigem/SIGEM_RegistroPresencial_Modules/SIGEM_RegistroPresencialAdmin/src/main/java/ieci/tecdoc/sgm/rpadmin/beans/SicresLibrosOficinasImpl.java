package ieci.tecdoc.sgm.rpadmin.beans;


public class SicresLibrosOficinasImpl  extends ColeccionGeneralImpl{

	public SicresLibroOficinaImpl get(int index) {
		return (SicresLibroOficinaImpl)lista.get(index);
	}

	public void add(SicresLibroOficinaImpl oficina) {
		lista.add(oficina);
	}
}
