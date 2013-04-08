package ieci.tecdoc.sgm.certificacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Certificaciones extends RetornoServicio {
	
	private Certificacion[] certificaciones;
	
	public Certificacion[] getCertificaciones(){
		return certificaciones;
	}
	
	public void setCertificaciones(Certificacion[] certificaciones){
		this.certificaciones = certificaciones;
	}
    
}