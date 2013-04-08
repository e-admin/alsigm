package docvitales.vos;

import java.util.Date;

import common.vos.BaseVO;

/**
 * Información del uso de un documento vital.
 */
public class UsoDocumentoVitalVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del documento vital. */
	private String idDocVit = null;

	/** IDentificador del expediente de backoffice. */
	private String idExp = null;

	/** Identificador del sistema de backoffice. */
	private String idSist = null;

	/** Fecha de uso del documento vital. */
	private Date fechaUso = null;

	/** Usuario que realiza el uso del documento vital. */
	private String usuario = null;

	/**
	 * Constructor.
	 */
	public UsoDocumentoVitalVO() {
	}

	public Date getFechaUso() {
		return fechaUso;
	}

	public void setFechaUso(Date fechaUso) {
		this.fechaUso = fechaUso;
	}

	public String getIdDocVit() {
		return idDocVit;
	}

	public void setIdDocVit(String idDocVit) {
		this.idDocVit = idDocVit;
	}

	public String getIdExp() {
		return idExp;
	}

	public void setIdExp(String idExp) {
		this.idExp = idExp;
	}

	public String getIdSist() {
		return idSist;
	}

	public void setIdSist(String idSist) {
		this.idSist = idSist;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
