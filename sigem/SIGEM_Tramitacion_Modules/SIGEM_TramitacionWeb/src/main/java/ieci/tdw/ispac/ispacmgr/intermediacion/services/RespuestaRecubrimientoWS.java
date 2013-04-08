package ieci.tdw.ispac.ispacmgr.intermediacion.services;

public class RespuestaRecubrimientoWS {

	String codigoEstado;
	String literalError;
	String pdfBase64;
	byte[] pdf;
	
	public final static	String CODIGO_ESTADO_OK = "0003";

	public String getCodigoEstado() {
		return codigoEstado;
	}
	public void setCodigoEstado(String codigoEstado) {
		this.codigoEstado = codigoEstado;
	}
	public String getLiteralError() {
		return literalError;
	}
	public void setLiteralError(String literalError) {
		this.literalError = literalError;
	}
	public String getPdfBase64() {
		return pdfBase64;
	}
	public void setPdfBase64(String pdfBase64) {
		this.pdfBase64 = pdfBase64;
	}
	public byte[] getPdf() {
		return pdf;
	}
	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}	
}
