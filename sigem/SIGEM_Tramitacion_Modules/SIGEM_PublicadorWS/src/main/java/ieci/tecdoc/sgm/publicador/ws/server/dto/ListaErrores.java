package ieci.tecdoc.sgm.publicador.ws.server.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ListaErrores extends RetornoServicio  {

	Error[] errores = null;

	public Error[] getErrores() {
		return errores;
	}

	public void setErrores(Error[] errores) {
		this.errores = errores;
	}
	
}
