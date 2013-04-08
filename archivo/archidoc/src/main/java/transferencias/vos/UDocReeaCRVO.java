/**
 *
 */
package transferencias.vos;

import common.vos.BaseVO;
import common.vos.IKeyId;

/**
 * Unidades Documentales de una Transferencia Entre Archivos Con Reencajado
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class UDocReeaCRVO extends BaseVO implements IKeyId {

	private static final long serialVersionUID = 1L;

	/**
	 * Identificador único
	 */
	private String id = new String();

	/**
	 * Identificador de la Relación de Entrega
	 */
	private String idRelEntrega = new String();

	/**
	 * Identificador de la Unidad Documental en Depósito
	 */
	private String idUnidadDoc = new String();

	/**
	 * Signatura de la unidad documental en el depósito
	 */
	private String signaturaUDoc = new String();

	/**
	 * Identificador de la unidad de instalación a la que está asignada
	 */
	private String idUIDeposito = new String();

	/**
	 * Signatura de la Unidad de Instalación
	 */
	private String signaturaUI = new String();

	public UDocReeaCRVO(String idRelEntrega, String idUnidadDoc,
			String signaturaUDoc, String idUIDeposito, String signaturaUI) {
		super();
		this.idRelEntrega = idRelEntrega;
		this.idUnidadDoc = idUnidadDoc;
		this.signaturaUDoc = signaturaUDoc;
		this.idUIDeposito = idUIDeposito;
		this.signaturaUI = signaturaUI;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdRelEntrega() {
		return idRelEntrega;
	}

	public void setIdRelEntrega(String idRelEntrega) {
		this.idRelEntrega = idRelEntrega;
	}

	public String getIdUnidadDoc() {
		return idUnidadDoc;
	}

	public void setIdUnidadDoc(String idUnidadDoc) {
		this.idUnidadDoc = idUnidadDoc;
	}

	public String getSignaturaUDoc() {
		return signaturaUDoc;
	}

	public void setSignaturaUDoc(String signaturaUDoc) {
		this.signaturaUDoc = signaturaUDoc;
	}

	public String getIdUIDeposito() {
		return idUIDeposito;
	}

	public void setIdUIDeposito(String idUIDeposito) {
		this.idUIDeposito = idUIDeposito;
	}

	public String getSignaturaUI() {
		return signaturaUI;
	}

	public void setSignaturaUI(String signaturaUI) {
		this.signaturaUI = signaturaUI;
	}
}
