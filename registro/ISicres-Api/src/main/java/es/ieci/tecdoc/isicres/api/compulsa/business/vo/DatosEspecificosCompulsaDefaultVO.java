package es.ieci.tecdoc.isicres.api.compulsa.business.vo;

public class DatosEspecificosCompulsaDefaultVO implements
		DatosEspecificosCompulsaVO {
	
	protected String usuario;
	protected String certificado;
	protected String firma;
	protected String formatoFirma;
	protected String algoritmoFirma;
	
	
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
	public String getFormatoFirma() {
		return formatoFirma;
	}
	public void setFormatoFirma(String formatoFirma) {
		this.formatoFirma = formatoFirma;
	}
	public String getAlgoritmoFirma() {
		return algoritmoFirma;
	}
	public void setAlgoritmoFirma(String algoritmoFirma) {
		this.algoritmoFirma = algoritmoFirma;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	

}
