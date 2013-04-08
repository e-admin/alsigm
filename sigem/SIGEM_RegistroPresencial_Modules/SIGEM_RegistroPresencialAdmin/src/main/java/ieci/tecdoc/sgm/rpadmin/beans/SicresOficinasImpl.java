package ieci.tecdoc.sgm.rpadmin.beans;


public class SicresOficinasImpl  extends ColeccionGeneralImpl{

	public SicresOficinaImpl get(int index) {
		return (SicresOficinaImpl)lista.get(index);
	}

	public void add(SicresOficinaImpl oficina) {
		lista.add(oficina);
	}
}
