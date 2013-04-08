package ieci.tecdoc.sgm.core.services.rpadmin;

/**
 * Extensión de tipos de asuntos para la gestión de Tipo de Asunto, Documentos y
 * Oficinas relacionados
 * 
 * @see ieci.tecdoc.sgm.core.services.rpadmin.TipoAsunto
 */
public class TipoAsuntoBean extends TipoAsunto {

	/**
	 * Nombre de la Unidad Administrativa
	 */
	private String nombreOrg;

	/**
	 * Código de la Unidad Administrativa
	 */
	private String codigoOrg;

	/**
	 * Oficinas Asociadas al Asunto
	 */
	private OficinasTipoAsuntoBean oficinas;
	private OficinasTipoAsuntoBean oficinasEliminadas;

	private DocumentosTipoAsuntoBean documentos;
	private DocumentosTipoAsuntoBean documentosEliminados;

	/**
	 * Información Auxiliar del Asunto
	 */
	private String informacionAuxiliar;

	/**
	 * @return
	 */
	public String getInformacionAuxiliar() {
		return informacionAuxiliar;
	}

	/**
	 * @param informacionAuxiliar
	 */
	public void setInformacionAuxiliar(String informacionAuxiliar) {
		this.informacionAuxiliar = informacionAuxiliar;
	}

	/**
	 * @param codigoOrg
	 */
	public void setCodigoOrg(String codigoOrg) {
		this.codigoOrg = codigoOrg;
	}

	/**
	 * @return
	 */
	public String getCodigoOrg() {
		return codigoOrg;
	}

	/**
	 * @param nombreOrg
	 */
	public void setNombreOrg(String nombreOrg) {
		this.nombreOrg = nombreOrg;
	}

	/**
	 * @return
	 */
	public String getNombreOrg() {
		return nombreOrg;
	}

	/**
	 * @return
	 */
	public OficinasTipoAsuntoBean getOficinas() {
		return oficinas;
	}

	/**
	 * @param oficinas
	 */
	public void setOficinas(OficinasTipoAsuntoBean oficinas) {
		this.oficinas = oficinas;
	}

	/**
	 * @return
	 */
	public DocumentosTipoAsuntoBean getDocumentos() {
		return documentos;
	}

	/**
	 * @param documentos
	 */
	public void setDocumentos(DocumentosTipoAsuntoBean documentos) {
		this.documentos = documentos;
	}

	/**
	 * @return
	 */
	public OficinasTipoAsuntoBean getOficinasEliminadas() {
		return oficinasEliminadas;
	}

	/**
	 * @param oficinasEliminadas
	 */
	public void setOficinasEliminadas(OficinasTipoAsuntoBean oficinasEliminadas) {
		this.oficinasEliminadas = oficinasEliminadas;
	}

	/**
	 * @return
	 */
	public DocumentosTipoAsuntoBean getDocumentosEliminados() {
		return documentosEliminados;
	}

	/**
	 * @param documentosEliminados
	 */
	public void setDocumentosEliminados(
			DocumentosTipoAsuntoBean documentosEliminados) {
		this.documentosEliminados = documentosEliminados;
	}
}
