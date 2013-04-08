package ieci.tecdoc.sgm.registro.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class RegistroDocumentos extends RetornoServicio {

	private RegistroDocumento[] registroDocumentos;
	
	public RegistroDocumento[] getRegistroDocumentos(){
		return registroDocumentos;
	}
	
	public void serRegistroDocumentos(RegistroDocumento[] registroDocumentos){
		this.registroDocumentos = registroDocumentos;
	}
	
}
    
