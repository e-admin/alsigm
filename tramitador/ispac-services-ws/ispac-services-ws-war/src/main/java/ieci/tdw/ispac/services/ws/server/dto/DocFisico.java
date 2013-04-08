package ieci.tdw.ispac.services.ws.server.dto;

import java.io.Serializable;

/**
 * Información de un documento físico.
 */
public class DocFisico implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** Tipo de documento. */
	protected String tipoDocumento = null;

	/** Asunto del documento. */
	protected String asunto = null;

	/**
	 * Constructor.
	 */
	public DocFisico() {
		super();
	}

	/**
	 * Obtiene el asunto del documento.
	 * @return Asunto del documento.
	 */
	public String getAsunto() {
		return asunto;
	}

	/**
	 * Establece el asunto del documento.
	 * @param asunto Asunto del documento.
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	/**
	 * Obtiene el tipo de documento.
	 * @return Tipo de documento.
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * Establece el tipo de documento.
	 * @param tipoDocumento Tipo de documento.
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
}
