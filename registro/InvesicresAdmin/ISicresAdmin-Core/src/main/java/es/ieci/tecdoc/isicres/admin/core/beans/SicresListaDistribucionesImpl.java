package es.ieci.tecdoc.isicres.admin.core.beans;


public class SicresListaDistribucionesImpl  extends ColeccionGeneralImpl{

	public SicresListaDistribucionImpl get(int index) {
		return (SicresListaDistribucionImpl)lista.get(index);
	}

	public void add(SicresListaDistribucionImpl oficina) {
		lista.add(oficina);
	}
}
