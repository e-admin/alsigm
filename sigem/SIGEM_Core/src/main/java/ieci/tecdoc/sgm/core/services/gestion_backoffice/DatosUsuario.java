package ieci.tecdoc.sgm.core.services.gestion_backoffice;


/**
 * Interfaz de comportamiento de un elemento mime.
 * 
 * @author IECISA
 *
 */
public class DatosUsuario
{
	
	public final static String AUTHENTICATION_TYPE_INVESDOC = "0";
	public final static String AUTHENTICATION_TYPE_LDAP = "1";
	
	private String user;
	private String password;
	private String email;
	private String id;
	private String name;
	private String lastname;
	private String authenticationType;
	private String ldapGuid;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getAuthenticationType() {
		return authenticationType;
	}
	public void setAuthenticationType(String authenticationType) {
		this.authenticationType = authenticationType;
	}
	public String getLdapGuid() {
		return ldapGuid;
	}
	public void setLdapGuid(String ldapGuid) {
		this.ldapGuid = ldapGuid;
	}
	

}