package ieci.tecdoc.sgm.core.services.administracion;

import java.util.Date;

/*
 * $Id: Usuario.java,v 1.1.2.1 2008/04/14 09:11:07 afernandez Exp $
 */

public class Usuario {

	private String usuario;// PK
	private String password;
	private String nombre; 
	private String apellidos;
	private Date fechaAlta;
	

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	

	
}
