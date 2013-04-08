package es.ieci.tecdoc.isicres.admin.core.database;

import es.ieci.tecdoc.isicres.admin.core.beans.ColeccionGeneralImpl;

public class SicresPerfilInformesImpl   extends ColeccionGeneralImpl{
	
	public SicresPerfilInformesImpl get(int index) {
		return (SicresPerfilInformesImpl)lista.get(index);
	}

	public void add(SicresPerfilInformesImpl perfil) {
		lista.add(perfil);
	}
}