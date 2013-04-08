package docvitales.vos;

import common.view.POUtils;

/**
 * Información de un documento vital.
 */
public class DocumentoVitalExtVO extends InfoBDocumentoVitalExtVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Contenido del fichero del documento vital. */
	private byte[] contenido = null;

	/**
	 * Constructor.
	 */
	public DocumentoVitalExtVO() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param infoB
	 *            Información básica.
	 */
	public DocumentoVitalExtVO(InfoBDocumentoVitalExtVO infoB) {
		super();
		if (infoB != null)
			POUtils.copyVOProperties(this, infoB);
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
