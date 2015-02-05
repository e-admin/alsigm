package ieci.tdw.ispac.services.dto;

import java.io.Serializable;

/**
 * Información básica de un expediente.
 */
public class InfoBExpediente implements Serializable {

	private static final long serialVersionUID = -7068638324069200197L;

	/** Identificador del expediente. */
	private String id = null;

	/** Número de expediente. */
	private String numExp = null;

	/** Datos que identifican al expediente. */
	private String datosIdentificativos = null;

	/**
	 * Constructor.
	 */
	public InfoBExpediente() {
		super();
	}

	/**
	 * Obtiene los datos que identifican al expediente.
	 * @return Datos que identifican al expediente.
	 */
	public String getDatosIdentificativos() {
		return datosIdentificativos;
	}

	/**
	 * Establece los datos que identifican al expediente.
	 * @param datosIdentificativos Datos que identifican al expediente.
	 */
	public void setDatosIdentificativos(String datosIdentificativos) {
		this.datosIdentificativos = datosIdentificativos;
	}

	/**
	 * Obtiene el identificador del expediente.
	 * @return Identificador del expediente.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador del expediente.
	 * @param id Identificador del expediente.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Obtiene el número de expediente.
	 * @return Número de expediente.
	 */
	public String getNumExp() {
		return numExp;
	}

	/**
	 * Establece el número de expediente.
	 * @param numExp Número de expediente.
	 */
	public void setNumExp(String numExp) {
		this.numExp = numExp;
	}

}
