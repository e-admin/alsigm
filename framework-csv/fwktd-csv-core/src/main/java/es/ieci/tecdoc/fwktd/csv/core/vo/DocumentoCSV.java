package es.ieci.tecdoc.fwktd.csv.core.vo;

/**
 * Información completa del documento.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DocumentoCSV extends InfoDocumentoCSV {

	private static final long serialVersionUID = 8713453823707913820L;

	/**
	 * Contenido del documento.
	 */
	private byte[] contenido = null;

	/**
	 * Constructor.
	 */
	public DocumentoCSV() {
		super();
	}

	public byte[] getContenido() {
		return contenido;
	}

	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}
}
