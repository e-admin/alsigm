package ieci.tecdoc.sgm.core.admin.web;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import ieci.tecdoc.idoc.admin.api.PermisosBackOfficeManager;
import ieci.tecdoc.idoc.admin.internal.UsuariosPermisosBackOfficeImpl;
import ieci.tecdoc.sgm.core.services.permisos_backoffice.CriterioBusqueda;

public class PermisosBackOfficeAdministradores {

	private static final Logger logger = Logger.getLogger(PermisosBackOfficeAdministradores.class);
			
	public static ArrayList busquedaUsuarios(CriterioBusqueda criterio, String entidad) {
		try {
			PermisosBackOfficeManager manager = new PermisosBackOfficeManager();
			return manager.searchUserBackOffice(criterio, entidad);
		} catch(Exception e) {
			logger.error("Error al buscar usuarios de backoffice administradores [busquedaUsuariosBackOfficeAdministradores][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
			return null;
		}
	}
	
	
	public static UsuariosPermisosBackOfficeImpl obtenerUsuarios(String entidad) {
		try {
			PermisosBackOfficeManager manager = new PermisosBackOfficeManager();
			return manager.getUsuariosPermisosBackOffice(entidad);
		} catch(Exception e) {
			logger.error("Error al buscar usuarios de backoffice administradores [busquedaUsuariosBackOfficeAdministradores][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
			return null;
		}
	}
	
	
	public static UsuariosPermisosBackOfficeImpl obtenerUsuario(int idUsuario, String entidad) {
		try {
			PermisosBackOfficeManager manager = new PermisosBackOfficeManager();
			return manager.getUsuarioPermisosBackOffice(idUsuario, entidad);
		} catch(Exception e) {
			logger.error("Error al buscar usuarios de backoffice administradores [busquedaUsuariosBackOfficeAdministradores][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
			return null;
		}
	}
}
