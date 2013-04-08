package es.ieci.tecdoc.isicres.admin.core.beans;

public class IUserObjsPermsImpl  extends ColeccionGeneralImpl{

	public IUserObjPermImpl get(int index) {
		return (IUserObjPermImpl)lista.get(index);
	}

	public void add(IUserObjPermImpl tipo) {
		lista.add(tipo);
	}
}
