package ieci.tecdoc.sgm.publicador.ws.server.dto;


import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ListaReglas extends RetornoServicio  {

	Regla[] reglas = null;

	public Regla[] getReglas() {
		return reglas;
	}

	public void setReglas(Regla[] reglas) {
	
		this.reglas = reglas;
	}
	
}
