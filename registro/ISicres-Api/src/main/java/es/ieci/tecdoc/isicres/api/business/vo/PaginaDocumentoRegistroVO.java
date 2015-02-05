package es.ieci.tecdoc.isicres.api.business.vo;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public class PaginaDocumentoRegistroVO extends BaseIsicresApiVO {

	private static final long serialVersionUID = 7179446924786951220L;

	protected String id;

	/**
	 * identificador del documente al que pertenece la pagina
	 */
	protected String idDocumentoRegistro;

	protected String name;

	protected DocumentoFisicoVO documentoFisico;

	protected Integer numeroPagina;

	public Integer getNumeroPagina() {
		return numeroPagina;
	}

	public void setNumeroPagina(Integer numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public String getIdDocumentoRegistro() {
		return idDocumentoRegistro;
	}

	public void setIdDocumentoRegistro(String idDocumentoRegistro) {
		this.idDocumentoRegistro = idDocumentoRegistro;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DocumentoFisicoVO getDocumentoFisico() {
		return documentoFisico;
	}

	public void setDocumentoFisico(DocumentoFisicoVO documentoFisico) {
		this.documentoFisico = documentoFisico;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
