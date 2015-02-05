package ieci.tecdoc.idoc.admin.api;

import ieci.tecdoc.idoc.admin.api.user.UsuarioPermisosBackOffice;
import ieci.tecdoc.idoc.admin.internal.UsuarioPermisosBackOfficeImpl;
import ieci.tecdoc.idoc.admin.internal.UsuariosPermisosBackOfficeImpl;
import ieci.tecdoc.sgm.core.services.permisos_backoffice.CriterioBusqueda;
import ieci.tecdoc.sgm.core.services.permisos_backoffice.PermisosBackOfficeException;

import java.util.ArrayList;

public class PermisosBackOfficeManager {

	public PermisosBackOfficeManager() {
	}
	
	public ArrayList searchUserBackOffice(CriterioBusqueda criterio, String entidad) throws PermisosBackOfficeException{
		UsuariosPermisosBackOfficeImpl usuarios=null;
		try{
			usuarios=new UsuariosPermisosBackOfficeImpl();
			usuarios.searchUsuariosPermisosBackOffice(criterio, entidad);
		}catch (Exception e) {
			throw new PermisosBackOfficeException(e);
		}
		return usuarios.get_list();
	}
	
	public UsuariosPermisosBackOfficeImpl getUsuarioPermisosBackOffice(int idUsuario, String entidad) throws PermisosBackOfficeException{
		UsuariosPermisosBackOfficeImpl usuariosId=new UsuariosPermisosBackOfficeImpl();
		try{
			usuariosId.loadUsuariosPermisosBackOffice(idUsuario, entidad);
		}catch (Exception e) {
			throw new PermisosBackOfficeException(e);
		}
		return usuariosId;
	}
	
	public UsuariosPermisosBackOfficeImpl getUsuariosPermisosBackOffice(String entidad) throws PermisosBackOfficeException{
		UsuariosPermisosBackOfficeImpl usuarios=null;
		try{
			usuarios=new UsuariosPermisosBackOfficeImpl();
			usuarios.loadUsuariosPermisosBackOffice(entidad);
		}catch (Exception e) {
			throw new PermisosBackOfficeException(e);
		}
		return usuarios;
	}
	
}
