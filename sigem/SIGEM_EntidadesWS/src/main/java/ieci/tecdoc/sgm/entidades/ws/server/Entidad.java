package ieci.tecdoc.sgm.entidades.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Entidad extends RetornoServicio{

	private String identificador;
	private String nombreCorto;
	private String nombreLargo;
	private String codigoINE;
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public String getNombreCorto() {
		return nombreCorto;
	}
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	public String getNombreLargo() {
		return nombreLargo;
	}
	public void setNombreLargo(String nombreLargo) {
		this.nombreLargo = nombreLargo;
	}
	public String getCodigoINE() {
		return codigoINE;
	}
	public void setCodigoINE(String codigoINE) {
		this.codigoINE = codigoINE;
	}
}
