package es.ieci.tecdoc.fwktd.sir.core.vo;

import es.ieci.tecdoc.fwktd.core.model.BaseValueObject;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;
import es.ieci.tecdoc.fwktd.sir.core.types.ValidezDocumentoEnum;

/**
 * Información inicial para crear un anexo de un asiento registral.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AnexoFormVO extends BaseValueObject {

	private static final long serialVersionUID = -638250132630292312L;

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

	/**
	 * Identificador del documento firmado. Si el anexo es firma de otro
	 * documento, se especifica el "identificador de fichero" objeto de firma.
	 * Este campo tomará el valor de sí mismo para indicar que contiene la firma
	 * embebida.
	 */
	private String identificadorFicheroFirmado;

	/**
	 * Código de fichero.
	 */
	private String codigoFichero;

	/**
	 * Código del fichero firmado. Si el anexo es firma de otro
	 * documento, se especifica el "código de fichero" objeto de firma.
	 * Este campo tomará el valor de sí mismo para indicar que contiene la firma
	 * embebida.
	 */
	private String codigoFicheroFirmado;

	/**
	 * Observaciones del fichero adjunto.
	 */
	private String observaciones;

	/**
	 * Contenido del anexo.
	 */
	private byte[] contenido = null;

	/**
	 * Constructor.
	 */
	public AnexoFormVO() {
		super();
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

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public byte[] getContenido() {
		return contenido;
	}

	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}

	public String getCodigoFichero() {
		return codigoFichero;
	}

	public void setCodigoFichero(String codigoFichero) {
		this.codigoFichero = codigoFichero;
	}

	public String getCodigoFicheroFirmado() {
		return codigoFicheroFirmado;
	}

	public void setCodigoFicheroFirmado(String codigoFicheroFirmado) {
		this.codigoFicheroFirmado = codigoFicheroFirmado;
	}
}
