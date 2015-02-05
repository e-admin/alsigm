package ieci.tecdoc.sgm.admin.beans;

import ieci.tecdoc.sgm.admin.interfaces.AccionMultientidad;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;

public class AccionMultientidadImpl implements Serializable, AccionMultientidad{
	
	private static final long serialVersionUID = -5359662167185447903L;
	
	private String identificador;// PK
	private String nombre;
	private String claseConfiguradora; 
	private String claseEjecutora;
	private String infoAdicional;
	
	/**
	 * {@inheritDoc}
	 * @see ieci.tecdoc.sgm.admin.interfaces.AccionMultientidad#getIdentificador()
	 */
	public String getIdentificador() {
		return identificador;
	}
	/**
	 * {@inheritDoc}
	 * @see ieci.tecdoc.sgm.admin.interfaces.AccionMultientidad#setIdentificador(java.lang.String)
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	/**
	 * {@inheritDoc}
	 * @see ieci.tecdoc.sgm.admin.interfaces.AccionMultientidad#getNombre()
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * {@inheritDoc}
	 * @see ieci.tecdoc.sgm.admin.interfaces.AccionMultientidad#setNombre(java.lang.String)
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * {@inheritDoc}
	 * @see ieci.tecdoc.sgm.admin.interfaces.AccionMultientidad#getClaseConfiguradora()
	 */
	public String getClaseConfiguradora() {
		return claseConfiguradora;
	}
	/**
	 * {@inheritDoc}
	 * @see ieci.tecdoc.sgm.admin.interfaces.AccionMultientidad#setClaseConfiguradora(java.lang.String)
	 */
	public void setClaseConfiguradora(String claseConfiguradora) {
		this.claseConfiguradora = claseConfiguradora;
	}
	
	/**
	 * {@inheritDoc}
	 * @see ieci.tecdoc.sgm.admin.interfaces.AccionMultientidad#getClaseEjecutora()
	 */
	public String getClaseEjecutora() {
		return claseEjecutora;
	}
	/**
	 * {@inheritDoc}
	 * @see ieci.tecdoc.sgm.admin.interfaces.AccionMultientidad#setClaseEjecutora(java.lang.String)
	 */
	public void setClaseEjecutora(String claseEjecutora) {
		this.claseEjecutora = claseEjecutora;
	}
	
	/**
	 * {@inheritDoc}
	 * @see ieci.tecdoc.sgm.admin.interfaces.AccionMultientidad#getInfoAdicional()
	 */
	public String getInfoAdicional() {
		return infoAdicional;
	}
	/**
	 * {@inheritDoc}
	 * @see ieci.tecdoc.sgm.admin.interfaces.AccionMultientidad#setInfoAdicional(java.lang.String)
	 */
	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}
	
	/**
	 * @param header
	 * @return
	 */
	public String toXML(boolean header) {
		XmlTextBuilder bdr;
		String tagName = "AccionMultientidad";

		bdr = new XmlTextBuilder();
		if (header)
			bdr.setStandardHeader();

		bdr.addOpeningTag(tagName);

		bdr.addSimpleElement("Id", identificador);
		bdr.addSimpleElement("Nombre", nombre);
		bdr.addSimpleElement("Clase Configuradora", claseConfiguradora);
		bdr.addSimpleElement("Clase Ejecutora", claseEjecutora);
		bdr.addSimpleElement("Informacion Adicional", infoAdicional);

		bdr.addClosingTag(tagName);

		return bdr.getText();
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return toXML(false);
	}
	
}
