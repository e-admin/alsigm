package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

import com.ieci.tecdoc.common.repository.vo.ISRepositoryRetrieveDocumentVO;

import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.DocumentoElectronicoAnexoDatosFirmaVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.TipoDocumentoAnexoEnumVO;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.TipoValidezDocumentoAnexoEnumVO;

public class InfoRegistroPageRepositoryVO {

	private String idLibro;
	private String idRegistro;
	private String idPageh;


	private InfoRegistroInfoDocumentoVO infoDocumento;
	private ISRepositoryRetrieveDocumentVO documentoFisico;


	/**
	 * Referencia al documento en gestor documental
	 */
	private String docUID;
	/**
	 *  contenido binario
	 */
	private byte[] content;


	/**
	 * campo compentario u observaciones del documetno
	 */
	protected String comentario;

	/**
	 * tipo mime del documento
	 */
	protected String mimeType;


	/**
	 * nombre codificado segun la norma de intercambio registral , obligatorio en el intercambio registral
	 * Se compondrá siguiendo la normalización definida en el apartado VII.2. de la norma
	 */
	protected String codeName;

	/**
	 * Campo OBLIGATORIO
	 * Tipo del documento:
	 *
	 * Indica el tipo de documento:
		- ‘01’ = Formulario (el documento adjunto es un formulario con campos rellenos por el ciudadano remitente).
		- ‘02’ = Documento adjunto al formulario (además del formulario, otro documento es adjuntado, acompañando al formulario).
		- ‘03’ = Fichero técnico interno (el documento adjunto es un fichero interno. Por lo general, estos ficheros pueden resultar útiles para la Entidad Registral de Destino, pero no son ficheros para
	 *
	 */
	protected TipoDocumentoAnexoEnumVO tipoDocumentoAnexo;

	/**
	 * Campo opcional
	 * Indica la categoría de autenticidad del documento:
		- ‘01’ = Copia (el documento adjunto en el proceso de intercambio es una copia del original sin estar cotejada por ningún organismo oficial y por tanto, sin validez jurídica).
		- ‘02’ = Copia compulsada (el documento adjunto en el proceso de intercambio es una copia del original y cotejada por un organismo oficial, y por tanto, con validez jurídica).
		- ‘03’ = Copia original (el documento adjunto en el proceso de intercambio es una copia del documento pero con exactamente la misma validez jurídica que el original).
		- ‘04’ = Original (el documento adjunto en el proceso de intercambio es original electrónico).
	 */
	protected TipoValidezDocumentoAnexoEnumVO tipoValidez;


	protected DocumentoElectronicoAnexoDatosFirmaVO datosFirma;

//	private InfoRegistroInfoDocumentoVO infoDocumento;
//	private ISRepositoryRetrieveDocumentVO documentoFisico;

	public String getIdLibro() {
		return idLibro;
	}
	public void setIdLibro(String idLibro) {
		this.idLibro = idLibro;
	}
	public String getIdRegistro() {
		return idRegistro;
	}
	public void setIdRegistro(String idRegistro) {
		this.idRegistro = idRegistro;
	}
	public String getIdPageh() {
		return idPageh;
	}
	public void setIdPageh(String idPageh) {
		this.idPageh = idPageh;
	}
	public String getDocUID() {
		return docUID;
	}
	public void setDocUID(String docUID) {
		this.docUID = docUID;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public TipoDocumentoAnexoEnumVO getTipoDocumentoAnexo() {
		return tipoDocumentoAnexo;
	}
	public void setTipoDocumentoAnexo(TipoDocumentoAnexoEnumVO tipoDocumentoAnexo) {
		this.tipoDocumentoAnexo = tipoDocumentoAnexo;
	}
	public TipoValidezDocumentoAnexoEnumVO getTipoValidez() {
		return tipoValidez;
	}
	public void setTipoValidez(TipoValidezDocumentoAnexoEnumVO tipoValidez) {
		this.tipoValidez = tipoValidez;
	}
	public DocumentoElectronicoAnexoDatosFirmaVO getDatosFirma() {
		return datosFirma;
	}
	public void setDatosFirma(DocumentoElectronicoAnexoDatosFirmaVO datosFirma) {
		this.datosFirma = datosFirma;
	}
//	public InfoRegistroInfoDocumentoVO getInfoDocumento() {
//		return infoDocumento;
//	}
//	public void setInfoDocumento(InfoRegistroInfoDocumentoVO infoDocumento) {
//		this.infoDocumento = infoDocumento;
//	}
	public ISRepositoryRetrieveDocumentVO getDocumentoFisico() {
		return documentoFisico;
	}
	public void setDocumentoFisico(ISRepositoryRetrieveDocumentVO documentoFisico) {
		this.documentoFisico = documentoFisico;
	}
	public InfoRegistroInfoDocumentoVO getInfoDocumento() {
		return infoDocumento;
	}
	public void setInfoDocumento(InfoRegistroInfoDocumentoVO infoDocumento) {
		this.infoDocumento = infoDocumento;
	}

}
