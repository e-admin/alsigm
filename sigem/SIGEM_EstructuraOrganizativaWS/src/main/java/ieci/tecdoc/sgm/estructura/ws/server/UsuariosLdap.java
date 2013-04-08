package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class UsuariosLdap extends RetornoServicio{
	
	private UsuarioLdap[] usuariosLdap;
	
	public UsuarioLdap[] getUsuariosLdap() {
		return usuariosLdap;
	}

	public void setUsuariosLdap(UsuarioLdap[] usuariosLdap) {
		this.usuariosLdap = usuariosLdap;
	}
		
}
