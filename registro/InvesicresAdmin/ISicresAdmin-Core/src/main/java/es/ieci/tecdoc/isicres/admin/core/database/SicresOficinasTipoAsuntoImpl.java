package es.ieci.tecdoc.isicres.admin.core.database;

import es.ieci.tecdoc.isicres.admin.core.beans.ColeccionGeneralImpl;
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaTipoAsuntoImpl;

public class SicresOficinasTipoAsuntoImpl extends ColeccionGeneralImpl{
	
	public SicresOficinasTipoAsuntoImpl get(int index) {
		return (SicresOficinasTipoAsuntoImpl)lista.get(index);
	}

	public void add(SicresOficinaTipoAsuntoImpl oficina) {
		lista.add(oficina);
	}
}
