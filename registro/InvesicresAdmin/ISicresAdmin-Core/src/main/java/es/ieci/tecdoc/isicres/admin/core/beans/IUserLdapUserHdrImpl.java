package es.ieci.tecdoc.isicres.admin.core.beans;

/*$Id*/

public class IUserLdapUserHdrImpl {
	
	private int id;
	private String ldapguid;
	private String ldapfullname;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLdapguid() {
		return ldapguid;
	}
	public void setLdapguid(String ldapguid) {
		this.ldapguid = ldapguid;
	}
	public String getLdapfullname() {
		return ldapfullname;
	}
	public void setLdapfullname(String ldapfullname) {
		this.ldapfullname = ldapfullname;
	}
	
}
