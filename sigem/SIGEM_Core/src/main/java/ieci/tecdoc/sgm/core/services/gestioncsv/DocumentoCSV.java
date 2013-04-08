/**
 * 
 */
package ieci.tecdoc.sgm.core.services.gestioncsv;

/**
 * @author IECISA
 * 
 * Información de un documento con su CSV generado y su contenido
 *
 */
public class DocumentoCSV extends InfoDocumentoCSV{
	
	
	/**
	 * Array de bytes del contenido del documento
	 */
	private byte[] contenido;

	/**
	 * @return el contenido del documento
	 */
	public byte[] getContenido() {
		return contenido;
	}

	/**
	 * @param contenido el contenido a fijar
	 */
	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}
	
	
	

}
