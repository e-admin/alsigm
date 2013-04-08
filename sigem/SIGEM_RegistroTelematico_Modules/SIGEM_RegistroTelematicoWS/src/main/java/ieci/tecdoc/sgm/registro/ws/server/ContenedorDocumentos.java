package ieci.tecdoc.sgm.registro.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Contenedor de documentos asociados a un registro.
 * 
 * @author IECISA
 *
 */
public class ContenedorDocumentos extends RetornoServicio {
	
	ContenedorDocumento[] contenedorDocumentos;
	
	public ContenedorDocumento[] getContenedorDocumentos() {
		return contenedorDocumentos;
	}

	public void setContenedorDocumentos(ContenedorDocumento[] contenedorDocumentos) {
		this.contenedorDocumentos = contenedorDocumentos;
	}
	
}
    
