package ieci.tecdoc.sgm.admin.beans;

import java.io.Serializable;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.admin.interfaces.Perfil;

/*
 * $Id: PerfilImpl.java,v 1.1.2.1 2008/04/15 09:28:03 afernandez Exp $
 */

public class PerfilImpl implements Perfil, Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5844906251517786461L;
	private String idEntidad;
	private String idAplicacion;
	private String idUsuario;
	
	public String getIdAplicacion() {
		return idAplicacion;
	}
	public void setIdAplicacion(String idAplicacion) {
		this.idAplicacion = idAplicacion;
	}
	public String getIdEntidad() {
		return idEntidad;
	}
	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	/**
	 * Recoge los valores de la instancia en una cadena xml
	 * @param header Si se incluye la cabecera
	 * @return los datos en formato xml
	 */   
	public String toXML(boolean header) {
		XmlTextBuilder bdr;
		String tagName = "Perfil";

		bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();

		bdr.addOpeningTag(tagName);

		bdr.addSimpleElement("Aplicacion", idAplicacion);
		bdr.addSimpleElement("Usuario", idUsuario);
		bdr.addSimpleElement("Entidad", idEntidad);

		bdr.addClosingTag(tagName);

		return bdr.getText();
	}

	/**
	 * Devuelve los valores de la instancia en una cadena de caracteres.
	 */
	public String toString() {
		return toXML(false);
	}
	
}
