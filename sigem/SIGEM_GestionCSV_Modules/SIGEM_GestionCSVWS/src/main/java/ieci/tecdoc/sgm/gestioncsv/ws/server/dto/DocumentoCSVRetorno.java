/**
 * 
 */
package ieci.tecdoc.sgm.gestioncsv.ws.server.dto;

/**
 * @author IECISA
 * 
 *
 */
public class DocumentoCSVRetorno extends InfoDocumentoCSVRetorno{
	
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
