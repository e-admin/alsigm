package es.ieci.tecdoc.isicres.api.documento.electronico.business.vo;

import java.util.List;

import es.ieci.tecdoc.isicres.api.business.vo.BaseIsicresApiVO;

/**
 * Clase que representará a un documento electrónico anexo a un registro, contendrá información adicional acerca de su firma
 * @author Iecisa
 *
 */
public class DocumentoElectronicoAnexoVO extends BaseIsicresApiVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7722557760685726927L;
	
	
	/**
	 * identificador del documento electronico anexo
	 */
	protected IdentificadorDocumentoElectronicoAnexoVO id;
	
	/**
	 * campo compentario u observaciones del documetno
	 */
	protected String comentario;
	
	/**
	 * tipo mime del documento
	 */
	protected String mimeType;
	
	protected String extension;
	
	/**
	 * Nombre del fichero original campo obligatorio
	 */
	protected String name;
	
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
	
	/**
	 * campo que contendrá el contenido del documento fisico en si, puede ser un docuid o bien el byte[]
	 */
	protected DocumentoElectronicoAnexoContenidoVO contenido;
		
	/**
	 * datos relacionados con la firma del documento. Es obligatorio ya que todo documento electrónico tiene que tener su hash
	 */
	protected DocumentoElectronicoAnexoDatosFirmaVO datosFirma;
	
	/**
	 * un documento eletronico puede tener asociado n firmas, que cada una de ellas sera considerada como un documento electronico
	 */
	protected List<DocumentoElectronicoAnexoVO> firmas;

	public List<DocumentoElectronicoAnexoVO> getFirmas() {
		return firmas;
	}

	public void setFirmas(List<DocumentoElectronicoAnexoVO> firmas) {
		this.firmas = firmas;
	}

	public DocumentoElectronicoAnexoDatosFirmaVO getDatosFirma() {
		return datosFirma;
	}

	public void setDatosFirma(DocumentoElectronicoAnexoDatosFirmaVO datosFirma) {
		this.datosFirma = datosFirma;
	}

	public IdentificadorDocumentoElectronicoAnexoVO getId() {
		return id;
	}

	public void setId(IdentificadorDocumentoElectronicoAnexoVO id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public DocumentoElectronicoAnexoContenidoVO getContenido() {
		return contenido;
	}

	public void setContenido(DocumentoElectronicoAnexoContenidoVO contenido) {
		this.contenido = contenido;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}  
	

	

}
