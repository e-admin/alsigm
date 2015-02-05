package ieci.tdw.ispac.services.ws.server.dto;

import java.io.Serializable;
import java.util.Date;

public class DatosComunesExpediente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String idOrganismo;
	
	private String tipoAsunto;

	private String numeroRegistro;

	private Date fechaRegistro;

	private InteresadoExpediente[] interesados;

	public DatosComunesExpediente() {
		super();
	}

	/**
	 * @return Returns the registerDate.
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param registerDate The registerDate to set.
	 */
	public void setFechaRegistro(Date registerDate) {
		this.fechaRegistro = registerDate;
	}

	/**
	 * @return Returns the registerNumber.
	 */
	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	/**
	 * @param registerNumber The registerNumber to set.
	 */
	public void setNumeroRegistro(String registerNumber) {
		this.numeroRegistro = registerNumber;
	}

	/**
	 * @return Returns the subjectType.
	 */
	public String getTipoAsunto() {
		return tipoAsunto;
	}

	/**
	 * @param subjectType The subjectType to set.
	 */
	public void setTipoAsunto(String subjectType) {
		this.tipoAsunto = subjectType;
	}

	/**
	 * @return Returns the interested.
	 */
	public InteresadoExpediente[] getInteresados() {
		return interesados;
	}

	/**
	 * @param interested The interested to set.
	 */
	public void setInteresados(InteresadoExpediente[] interesados) {
		this.interesados = interesados;
	}

	public String getIdOrganismo() {
		return idOrganismo;
	}

	public void setIdOrganismo(String idOrganismo) {
		this.idOrganismo = idOrganismo;
	}

}