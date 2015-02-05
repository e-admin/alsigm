package docvitales.webservice.vos;

import common.view.POUtils;

import docvitales.vos.DocumentoVitalExtVO;

/**
 * Información de un documento vital.
 */
public class DocumentoVital extends InfoBDocumentoVital {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Contenido del fichero del documento vital. */
	private byte[] contenido = null;

	/**
	 * Constructor.
	 */
	public DocumentoVital() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param infoB
	 *            Información básica.
	 */
	public DocumentoVital(DocumentoVitalExtVO doc) {
		this();
		POUtils.copyVOProperties(this, doc);
	}

	/**
	 * Obtener el contenido del fichero del documento vital.
	 * 
	 * @return Contenido del fichero del documento vital.
	 */
	public byte[] getContenido() {
		return contenido;
	}

	/**
	 * Establece el contenido del fichero del documento vital.
	 * 
	 * @param contenido
	 *            Contenido del fichero del documento vital.
	 */
	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}
}
