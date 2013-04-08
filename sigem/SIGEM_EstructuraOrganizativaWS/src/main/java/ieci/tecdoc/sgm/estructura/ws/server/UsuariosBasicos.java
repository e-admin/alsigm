package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class UsuariosBasicos extends RetornoServicio{

	private UsuarioBasico[] usuariosBasicos;
	
	public UsuarioBasico[] getUsuariosBasicos() {
		return usuariosBasicos;
	}

	public void setUsuariosBasicos(UsuarioBasico[] usuariosBasicos) {
		this.usuariosBasicos = usuariosBasicos;
	}
	
}
