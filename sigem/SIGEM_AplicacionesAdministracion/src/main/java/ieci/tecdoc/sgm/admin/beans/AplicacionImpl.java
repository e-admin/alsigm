package ieci.tecdoc.sgm.admin.beans;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.admin.interfaces.Aplicacion;

import java.io.Serializable;

/**
 * $Id: AplicacionImpl.java,v 1.1.2.1 2008/04/15 09:28:03 afernandez Exp $
 */

public class AplicacionImpl implements Aplicacion, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6136655132386982914L;
	private String identificador;
	private String definicion;
	private String contextoApp;
	private String servidor;
	private String puertoApp;
	private String protocolo;

	public String getContextoApp() {
		return contextoApp;
	}
	public void setContextoApp(String contextoApp) {
		this.contextoApp = contextoApp;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getServidor() {
		return servidor;
	}
	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public String getPuertoApp() {
		return puertoApp;
	}
	public void setPuertoApp(String puertoApp) {
		this.puertoApp = puertoApp;
	}
	
	/**
	 * Recoge los valores de la instancia en una cadena xml
	 * @param header Si se incluye la cabecera
	 * @return los datos en formato xml
	 */   
	public String toXML(boolean header) {
		XmlTextBuilder bdr;
		String tagName = "Aplicación";

		bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();

		bdr.addOpeningTag(tagName);

		bdr.addSimpleElement("Identificador", identificador);
		bdr.addSimpleElement("Definicion", definicion);
		bdr.addSimpleElement("Contexto App", contextoApp);
		bdr.addSimpleElement("Servidor", servidor);
		bdr.addSimpleElement("Puerto App", puertoApp);
		bdr.addSimpleElement("Protocolo", protocolo);

		bdr.addClosingTag(tagName);

		return bdr.getText();
	}

	/**
	 * Devuelve los valores de la instancia en una cadena de caracteres.
	 */
	public String toString() {
		return toXML(false);
	}
	public String getDefinicion() {
		return definicion;
	}
	public void setDefinicion(String definicion) {
		this.definicion = definicion;
	}
	public String getProtocolo() {
		return protocolo;
	}
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}		

}
