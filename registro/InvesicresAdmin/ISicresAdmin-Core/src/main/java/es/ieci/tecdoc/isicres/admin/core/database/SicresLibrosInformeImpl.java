package es.ieci.tecdoc.isicres.admin.core.database;

import es.ieci.tecdoc.isicres.admin.core.beans.ColeccionGeneralImpl;

public class SicresLibrosInformeImpl extends ColeccionGeneralImpl{
	public SicresLibrosInformeImpl get(int index) {
		return (SicresLibrosInformeImpl)lista.get(index);
	}

	public void add(SicresLibrosInformeImpl libro) {
		lista.add(libro);
	}
}
