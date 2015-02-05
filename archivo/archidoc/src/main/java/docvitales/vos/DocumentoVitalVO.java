package docvitales.vos;

import common.view.POUtils;

/**
 * Información de un documento vital.
 */
public class DocumentoVitalVO extends InfoBDocumentoVitalVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Contenido del fichero del documento vital. */
	private byte[] contenido = null;

	/**
	 * Constructor.
	 */
	public DocumentoVitalVO() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param infoB
	 *            Información básica.
	 */
	public DocumentoVitalVO(InfoBDocumentoVitalVO infoB) {
		super();
		if (infoB != null)
			POUtils.copyVOProperties(this, infoB);
	}

	/**
	 * Constructor.
	 * 
	 * @param doc
	 *            Información extendida del documento vital.
	 */
	public DocumentoVitalVO(DocumentoVitalExtVO doc) {
		super();
		if (doc != null)
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
