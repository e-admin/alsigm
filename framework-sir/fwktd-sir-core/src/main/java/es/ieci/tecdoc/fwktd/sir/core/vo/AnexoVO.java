package es.ieci.tecdoc.fwktd.sir.core.vo;

import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ValidezDocumentoEnum;

/**
 * Información de un anexo de un asiento registral.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AnexoVO extends Entity {

	private static final long serialVersionUID = -8849901644287847321L;

	/**
	 * Identificador del asiento registral.
	 */
	private String idAsientoRegistral = null;

	/**
	 * Identificador del fichero intercambiado.
	 */
	private String identificadorFichero = null;
	
	/**
	 * Nombre del fichero original.
	 */
	private String nombreFichero;

	/**
	 * Validez del documento.
	 */
	private ValidezDocumentoEnum validezDocumento;

	/**
	 * Tipo de documento.
	 */
	private TipoDocumentoEnum tipoDocumento;

	/**
	 * Certificado público del fichero anexo.
	 */
	private byte[] certificado;

	/**
	 * Firma electrónica del fichero anexo.
	 */
	private byte[] firma;

	/**
	 * Sello de tiempo del fichero anexo.
	 */
	private byte[] timestamp;

	/**
	 * Validación OCSP del certificado.
	 */
	private byte[] validacionOCSPCertificado;

	/**
	 * Huella binaria del fichero anexo.
	 */
	private byte[] hash;

	/**
	 * Tipo MIME del fichero anexo.
	 */
	private String tipoMIME;

//	/**
//	 * UID del contenido del anexo en el gestor documental.
//	 */
//	private String uidGestorDocumental;

	/**
	 * Identificador interno del documento firmado. Si el anexo es firma de otro
	 * documento, se especifica el identificador del anexo objeto de firma.
	 * Este campo tomará el valor de sí mismo para indicar que contiene la firma
	 * embebida.
	 */
	private String identificadorFicheroFirmado;

	/**
	 * Identificador (identificadorFichero) del documento firmado. Si el anexo es firma de otro
	 * documento, se especifica el identificador de del fichero objeto de firma.
	 * Este campo tomará el valor de sí mismo para indicar que contiene la firma
	 * embebida.
	 */
	private String identificadorDocumentoFirmado;

	/**
	 * Observaciones del fichero adjunto.
	 */
	private String observaciones;

	/**
	 * Constructor.
	 */
	public AnexoVO() {
		super();
	}

	public String getIdAsientoRegistral() {
		return idAsientoRegistral;
	}

	public void setIdAsientoRegistral(String idAsientoRegistral) {
		this.idAsientoRegistral = idAsientoRegistral;
	}

	public String getIdentificadorFichero() {
		return identificadorFichero;
	}

	public void setIdentificadorFichero(String identificadorFichero) {
		this.identificadorFichero = identificadorFichero;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public ValidezDocumentoEnum getValidezDocumento() {
		return validezDocumento;
	}

	public void setValidezDocumento(ValidezDocumentoEnum validezDocumento) {
		this.validezDocumento = validezDocumento;
	}

	public TipoDocumentoEnum getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumentoEnum tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public byte[] getCertificado() {
		return certificado;
	}

	public void setCertificado(byte[] certificado) {
		this.certificado = certificado;
	}

	public byte[] getFirma() {
		return firma;
	}

	public void setFirma(byte[] firma) {
		this.firma = firma;
	}

	public byte[] getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(byte[] timestamp) {
		this.timestamp = timestamp;
	}

	public byte[] getValidacionOCSPCertificado() {
		return validacionOCSPCertificado;
	}

	public void setValidacionOCSPCertificado(byte[] validacionOCSPCertificado) {
		this.validacionOCSPCertificado = validacionOCSPCertificado;
	}

	public byte[] getHash() {
		return hash;
	}

	public void setHash(byte[] hash) {
		this.hash = hash;
	}

	public String getTipoMIME() {
		return tipoMIME;
	}

	public void setTipoMIME(String tipoMIME) {
		this.tipoMIME = tipoMIME;
	}

	public String getIdentificadorFicheroFirmado() {
		return identificadorFicheroFirmado;
	}

	public void setIdentificadorFicheroFirmado(
			String identificadorFicheroFirmado) {
		this.identificadorFicheroFirmado = identificadorFicheroFirmado;
	}

	/**
	 * @return El valor de identificadorDocumentoFirmado
	 */
	public String getIdentificadorDocumentoFirmado() {
		return identificadorDocumentoFirmado;
	}

	/**
	 * @param identificadorDocumentoFirmado El valor de identificadorDocumentoFirmado
	 */
	public void setIdentificadorDocumentoFirmado(
			String identificadorDocumentoFirmado) {
		this.identificadorDocumentoFirmado = identificadorDocumentoFirmado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

}
