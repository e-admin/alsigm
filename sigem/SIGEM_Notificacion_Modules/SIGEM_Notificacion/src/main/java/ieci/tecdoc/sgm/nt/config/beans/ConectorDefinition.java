package ieci.tecdoc.sgm.nt.config.beans;

import ieci.tecdoc.sgm.nt.conectores.ConectorNotificacion;

public class ConectorDefinition {
	private String nombre;
	private String claseJava;
	private String actualizacionEstado;
	private String user;
	private String password;
	private String url;
	private String tipoCorrespondencia;
	private String organismo;
	
	private ConectorNotificacion conector;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getClaseJava() {
		return claseJava;
	}
	public void setClaseJava(String claseJava) {
		this.claseJava = claseJava;
	}
	public String getActualizacionEstado() {
		return actualizacionEstado;
	}
	public boolean getActualizacionEstadoBool() {
		return new Boolean(actualizacionEstado).booleanValue();
	}
	public void setActualizacionEstado(String actualizacionEstado) {
		this.actualizacionEstado = actualizacionEstado;
	}
	public ConectorNotificacion getConector() {
		return conector;
	}
	public void setConector(ConectorNotificacion conector) {
		this.conector = conector;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTipoCorrespondencia() {
		return tipoCorrespondencia;
	}
	public void setTipoCorrespondencia(String tipoCorrespondencia) {
		this.tipoCorrespondencia = tipoCorrespondencia;
	}
	public String getOrganismo() {
		return organismo;
	}
	public void setOrganismo(String organismo) {
		this.organismo = organismo;
	}
	
	public String toString(){
		StringBuffer cad=new StringBuffer();
		cad.append("\n------- Conector Notificacion "+nombre+" -------\n");
		cad.append("Clase Java: "+claseJava+"\n");
		cad.append("Actualizacion Estado: "+actualizacionEstado+"\n");
		cad.append("Usuario: "+nombre+"\n");
		cad.append("Contraseña: "+password+"\n");
		cad.append("Url: "+url+"\n");
		cad.append("Tipo Correspondencia: "+tipoCorrespondencia+"\n");
		cad.append("Organismo: "+organismo+"\n");
		cad.append("------- Fin Conector Notificacion "+nombre+" -------\n");
		return cad.toString();
	}
}
