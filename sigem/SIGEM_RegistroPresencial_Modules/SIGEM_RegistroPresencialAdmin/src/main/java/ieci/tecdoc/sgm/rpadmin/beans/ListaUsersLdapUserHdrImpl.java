package ieci.tecdoc.sgm.rpadmin.beans;

public class ListaUsersLdapUserHdrImpl extends ColeccionGeneralImpl {
	
	public IUserLdapUserHdrImpl get(int index) {
		return (IUserLdapUserHdrImpl)lista.get(index);
	}

	public void add(IUserLdapUserHdrImpl userLdap) {
		lista.add(userLdap);
	}
}
