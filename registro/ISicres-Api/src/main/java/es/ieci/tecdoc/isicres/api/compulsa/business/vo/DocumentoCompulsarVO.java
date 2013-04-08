package es.ieci.tecdoc.isicres.api.compulsa.business.vo;

import java.io.Serializable;

import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;

/**
 * Clase para pasar los datos necesarios para deseencadenar el proceso de compulsa
 * @author IECISA
 *
 */
public class DocumentoCompulsarVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3476818629786051501L;

	/**
	 * identificador del registro al que se le va añadir la compulsa 
	 */
	protected IdentificadorRegistroVO identificadorRegistro;
	
	/**
	 * documento original compulsado
	 */
	protected DocumentoFisicoCompulsaVO documentoOriginal;
	
	/**
	 * firma del documento original compulsado 
	 */
	protected DocumentoFisicoCompulsaVO firma;
	
	/**
	 * posibles datos especificos de algun proceso de compulsa, punto de extension
	 */
	protected DatosEspecificosCompulsaVO datosEspecificos;

	public IdentificadorRegistroVO getIdentificadorRegistro() {
		return identificadorRegistro;
	}

	public void setIdentificadorRegistro(
			IdentificadorRegistroVO identificadorRegistro) {
		this.identificadorRegistro = identificadorRegistro;
	}

	public DocumentoFisicoCompulsaVO getDocumentoOriginal() {
		return documentoOriginal;
	}

	public void setDocumentoOriginal(DocumentoFisicoCompulsaVO documentoOriginal) {
		this.documentoOriginal = documentoOriginal;
	}

	public DocumentoFisicoCompulsaVO getFirma() {
		return firma;
	}

	public void setFirma(DocumentoFisicoCompulsaVO firma) {
		this.firma = firma;
	}

	public DatosEspecificosCompulsaVO getDatosEspecificos() {
		return datosEspecificos;
	}

	public void setDatosEspecificos(DatosEspecificosCompulsaVO datosEspecificos) {
		this.datosEspecificos = datosEspecificos;
	}
	 
	
	

}
