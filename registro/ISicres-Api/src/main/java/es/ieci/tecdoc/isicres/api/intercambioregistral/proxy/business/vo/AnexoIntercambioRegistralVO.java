package es.ieci.tecdoc.isicres.api.intercambioregistral.proxy.business.vo;

//TODO mapea a AnexoFormDTO
public class AnexoIntercambioRegistralVO {
	
	protected byte[] certificado;
	protected String codigoFichero;
	protected String codigoFicheroFirmado;
	protected byte[] contenido;
	protected byte[] firma;
	protected String identificadorFicheroFirmado;
	protected String nombreFichero;
	protected String observaciones;
	protected byte[] timestamp;
	protected String tipoDocumento;
	protected String tipoMIME;
	protected byte[] validacionOCSPCertificado;
	protected String validezDocumento;
	
	public byte[] getCertificado() {
		return certificado;
	}
	public void setCertificado(byte[] certificado) {
		this.certificado = certificado;
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
	public byte[] getContenido() {
		return contenido;
	}
	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}
	public byte[] getFirma() {
		return firma;
	}
	public void setFirma(byte[] firma) {
		this.firma = firma;
	}
	public String getIdentificadorFicheroFirmado() {
		return identificadorFicheroFirmado;
	}
	public void setIdentificadorFicheroFirmado(String identificadorFicheroFirmado) {
		this.identificadorFicheroFirmado = identificadorFicheroFirmado;
	}
	public String getNombreFichero() {
		return nombreFichero;
	}
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public byte[] getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(byte[] timestamp) {
		this.timestamp = timestamp;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getTipoMIME() {
		return tipoMIME;
	}
	public void setTipoMIME(String tipoMIME) {
		this.tipoMIME = tipoMIME;
	}
	public byte[] getValidacionOCSPCertificado() {
		return validacionOCSPCertificado;
	}
	public void setValidacionOCSPCertificado(byte[] validacionOCSPCertificado) {
		this.validacionOCSPCertificado = validacionOCSPCertificado;
	}
	public String getValidezDocumento() {
		return validezDocumento;
	}
	public void setValidezDocumento(String validezDocumento) {
		this.validezDocumento = validezDocumento;
	}


}
