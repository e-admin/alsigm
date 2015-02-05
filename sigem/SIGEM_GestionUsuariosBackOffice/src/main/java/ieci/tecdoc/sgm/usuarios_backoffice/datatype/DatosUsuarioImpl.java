package ieci.tecdoc.sgm.usuarios_backoffice.datatype;


/**
 * Interfaz de comportamiento de un elemento mime.
 * 
 * @author IECISA
 *
 */
public class DatosUsuarioImpl
{
	
	private String user;
	private String password;
	private String email;
	private String id;
	private String name;
	private String lastname;
	
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
	
	
	
}