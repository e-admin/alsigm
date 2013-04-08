package ieci.tecdoc.sgm.registro.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Contenedor de documentos adjuntos a la solicitud de registro.
 * 
 * @author IECISA
 *
 */
public class PeticionDocumentos extends RetornoServicio
{
	private PeticionDocumento[] peticionDocumentos;

	public PeticionDocumento[] getPeticionDocumentos() {
		return peticionDocumentos;
	}

	public void setPeticionDocumentos(PeticionDocumento[] peticionDocumentos) {
		this.peticionDocumentos = peticionDocumentos;
	}
    
}