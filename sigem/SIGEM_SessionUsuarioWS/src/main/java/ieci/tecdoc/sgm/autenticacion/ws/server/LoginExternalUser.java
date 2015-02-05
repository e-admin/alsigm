package ieci.tecdoc.sgm.autenticacion.ws.server;

public class LoginExternalUser {

	private String actSessionId;
	private String nombre;
	private String apellidos;
	private String senderId;
	private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getActSessionId() {
		return actSessionId;
	}
	public void setActSessionId(String actSessionId) {
		this.actSessionId = actSessionId;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}	
}