package es.ieci.tecdoc.isicres.api.documento.electronico.business.vo;

import es.ieci.tecdoc.isicres.api.business.vo.BaseIsicresApiVO;

/**
 * Clase que almacenará la información relacionada con la firma de un Documento Electrónico Anexo
 * @author Iecisa
 *@see DocumentoElectronicoAnexoVO
 */
public class DocumentoElectronicoAnexoDatosFirmaVO extends BaseIsicresApiVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5908499792114401967L;
	
	/**
	 * identificador interno de bd
	 */
	protected Long id;
	/**
	 * Campo opcional. certificado del fichero anexo (parte publica)
	 */
	protected String certificado;
	
	/**
	 * Campo opcional. firma electronica del documento
	 */
	protected String firma;
	
	/**
	 * Campo opcional. Algoritmo usado para la firma 
	 */
	protected String algFirma;
	
	/**
	 * Campo opcional. formato de la firma
	 */
	protected String formatoFirma;
	
	/**
	 * Campo opcional. sellado de tiempo
	 */
	protected String selloTiempo;
	
	/**
	 * Campo opcional. validacion ocsp del certificado
	 */
	protected String ocspValidation;
	
	/**
	 * Obligatorio. Huella binaria del fichero Anexo que garantiza la integridad de los archivos enviados. 
	 */
	protected String hash;
	
	/**
	 * Campo opcional. algoritmo hash empleado
	 */
	protected String hashAlg;
	
	/**
	 * Campo opcional.
	 * Si el Anexo es firma de otro documento, se especifica el id objeto de la firma. Este campo tomará el valor de sí mismo para indicar que contiene firma embebida
	 */
	protected Long idAttachmentFirmado;
	
	/**
	 * identificador del attachment al que se refiere
	 */
	protected Long idAttachment;

	public Long getIdAttachmentFirmado() {
		return idAttachmentFirmado;
	}

	public void setIdAttachmentFirmado(Long idAttachmentFirmado) {
		this.idAttachmentFirmado = idAttachmentFirmado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public String getAlgFirma() {
		return algFirma;
	}

	public void setAlgFirma(String algFirma) {
		this.algFirma = algFirma;
	}

	public String getFormatoFirma() {
		return formatoFirma;
	}

	public void setFormatoFirma(String formatoFirma) {
		this.formatoFirma = formatoFirma;
	}

	public String getSelloTiempo() {
		return selloTiempo;
	}

	public void setSelloTiempo(String selloTiempo) {
		this.selloTiempo = selloTiempo;
	}

	public String getOcspValidation() {
		return ocspValidation;
	}

	public void setOcspValidation(String ocspValidation) {
		this.ocspValidation = ocspValidation;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getHashAlg() {
		return hashAlg;
	}

	public void setHashAlg(String hashAlg) {
		this.hashAlg = hashAlg;
	}

	public Long getIdAttachment() {
		return idAttachment;
	}

	public void setIdAttachment(Long idAttachment) {
		this.idAttachment = idAttachment;
	}
		
	
	
	
	
	
	
	
}
