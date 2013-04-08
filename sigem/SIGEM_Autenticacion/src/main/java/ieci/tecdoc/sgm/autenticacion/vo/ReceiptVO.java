/**
 * 
 */
package ieci.tecdoc.sgm.autenticacion.vo;

/**
 * @author IECISA
 * 
 * Almacena el contenido de un documento y el CSV asociado a dicho documento.
 *
 */
public class ReceiptVO {

	/**
	 * Código Seguro de Verificación del justificante
	 */
	private String csv;
	
	/**
	 * Array de bytes con el contenido del documento
	 */
	private byte[] content;

	/**
	 * @return el csv
	 */
	public String getCsv() {
		return csv;
	}

	/**
	 * @param csv el csv a fijar
	 */
	public void setCsv(String csv) {
		this.csv = csv;
	}

	/**
	 * @return el content
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * @param content el content a fijar
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

}
