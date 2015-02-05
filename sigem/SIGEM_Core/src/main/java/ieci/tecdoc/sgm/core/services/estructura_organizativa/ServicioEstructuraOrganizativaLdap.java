package ieci.tecdoc.sgm.core.services.estructura_organizativa;

import ieci.tecdoc.sgm.core.services.gestion_backoffice.DatosUsuario;

public interface ServicioEstructuraOrganizativaLdap {
	
	public DatosUsuario getDatosUsuarioServicio(String id, String user) throws EstructuraOrganizativaException;
	public boolean esEntidadLdap( String entidad ) throws EstructuraOrganizativaException;
}
