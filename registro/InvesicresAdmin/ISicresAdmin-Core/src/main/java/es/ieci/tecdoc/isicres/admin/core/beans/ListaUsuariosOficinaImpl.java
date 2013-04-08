package es.ieci.tecdoc.isicres.admin.core.beans;

public class ListaUsuariosOficinaImpl extends ColeccionGeneralImpl {
	
	public SicresUsuarioOficinaImpl get(int index) {
		return (SicresUsuarioOficinaImpl)lista.get(index);
	}

	public void add(SicresUsuarioOficinaImpl userOfic) {
		lista.add(userOfic);
	}
}
