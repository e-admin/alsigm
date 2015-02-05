package ieci.tecdoc.sgm.entidades.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Entidades extends RetornoServicio{

	private Entidad[] entidades;

	public Entidad[] getEntidades() {
		return entidades;
	}

	public void setEntidades(Entidad[] entidades) {
		this.entidades = entidades;
	}
}
