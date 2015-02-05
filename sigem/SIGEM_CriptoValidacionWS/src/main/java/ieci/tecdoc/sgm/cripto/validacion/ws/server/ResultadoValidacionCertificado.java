package ieci.tecdoc.sgm.cripto.validacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ResultadoValidacionCertificado extends RetornoServicio {

	private String resultadoValidacion;
	
	private String mensajeValidacion;

	private InfoCertificado certificado;

	public InfoCertificado getCertificado() {
		return certificado;
	}

	public void setCertificado(InfoCertificado certificado) {
		this.certificado = certificado;
	}

	public String getMensajeValidacion() {
		return mensajeValidacion;
	}

	public void setMensajeValidacion(String mensajeValidacion) {
		this.mensajeValidacion = mensajeValidacion;
	}

	public String getResultadoValidacion() {
		return resultadoValidacion;
	}

	public void setResultadoValidacion(String resultadoValidacion) {
		this.resultadoValidacion = resultadoValidacion;
	}

	
}
