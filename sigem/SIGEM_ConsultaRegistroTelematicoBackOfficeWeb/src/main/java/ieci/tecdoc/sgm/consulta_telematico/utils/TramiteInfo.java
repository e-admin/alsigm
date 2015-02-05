package ieci.tecdoc.sgm.consulta_telematico.utils;

import ieci.tecdoc.sgm.core.services.catalogo.Tramite;

public class TramiteInfo {
	
	private String idTramite;
	private String descripcionTramite;
	
	public TramiteInfo() {
		this.idTramite = "";
		this.descripcionTramite = "";
	}
	
	public TramiteInfo(Tramite tramite) {
		this.idTramite = tramite.getTopic();
		this.descripcionTramite = tramite.getDescription();
	}
	
	public String getDescripcionTramite() {
		return descripcionTramite;
	}
	
	public void setDescripcionTramite(String descripcionTramite) {
		this.descripcionTramite = descripcionTramite;
	}
	
	public String getIdTramite() {
		return idTramite;
	}
	
	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}
}
