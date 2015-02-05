package es.ieci.tecdoc.isicres.admin.core.database;

import es.ieci.tecdoc.isicres.admin.core.beans.ColeccionGeneralImpl;

public class SicresOficinasInformeImpl  extends ColeccionGeneralImpl{
	
	public SicresOficinasInformeImpl get(int index) {
		return (SicresOficinasInformeImpl)lista.get(index);
	}

	public void add(SicresOficinasInformeImpl oficina) {
		lista.add(oficina);
	}
}