package ieci.tecdoc.sgm.admin.beans;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.admin.interfaces.Usuario;

import java.io.Serializable;
import java.util.Date;

/*
 * $Id: UsuarioImpl.java,v 1.1.2.1 2008/04/15 09:28:03 afernandez Exp $
 */

public class UsuarioImpl implements Serializable, Usuario{


	/**
	 * 
	 */
	private static final long serialVersionUID = 6890077441562503049L;

	private String usuario; //PK
	private String password;
	private String nombre; 
	private String apellidos;
	private Date fechaAlta;
	
	/* (non-Javadoc)
	 * @see ieci.tecdoc.sgm.admin.beans.Usuario#getUsuario()
	 */
	public String getUsuario() {
		return usuario;
	}
	/* (non-Javadoc)
	 * @see ieci.tecdoc.sgm.admin.beans.Usuario#setUsuario(java.lang.String)
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/* (non-Javadoc)
	 * @see ieci.tecdoc.sgm.admin.beans.Usuario#getPassword()
	 */
	public String getPassword() {
		return password;
	}
	/* (non-Javadoc)
	 * @see ieci.tecdoc.sgm.admin.beans.Usuario#setPassword(java.lang.String)
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see ieci.tecdoc.sgm.admin.beans.Usuario#getNombre()
	 */
	public String getNombre() {
		return nombre;
	}
	/* (non-Javadoc)
	 * @see ieci.tecdoc.sgm.admin.beans.Usuario#setNombre(java.lang.String)
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/* (non-Javadoc)
	 * @see ieci.tecdoc.sgm.admin.beans.Usuario#getApellidos()
	 */	
	public String getApellidos() {
		return this.apellidos;
	}
	/* (non-Javadoc)
	 * @see ieci.tecdoc.sgm.admin.beans.Usuario#setApellidos(java.lang.String)
	 */

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
		
	}
	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
		
	}


	/* (non-Javadoc)
	 * @see ieci.tecdoc.sgm.admin.beans.Usuario#toXML(boolean)
	 */   
	public String toXML(boolean header) {
		XmlTextBuilder bdr;
		String tagName = "Usuario";

		bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();

		bdr.addOpeningTag(tagName);

		bdr.addSimpleElement("Usuario", usuario);
		bdr.addSimpleElement("Nombre", nombre);
		bdr.addSimpleElement("Apellidos", apellidos);
		bdr.addSimpleElement("Fecha Alta", fechaAlta.toString());

		bdr.addClosingTag(tagName);

		return bdr.getText();
	}

	/* (non-Javadoc)
	 * @see ieci.tecdoc.sgm.admin.beans.Usuario#toString()
	 */
	public String toString() {
		return toXML(false);
	}

	
}
