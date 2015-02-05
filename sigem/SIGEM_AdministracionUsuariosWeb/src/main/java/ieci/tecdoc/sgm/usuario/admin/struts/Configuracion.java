package ieci.tecdoc.sgm.usuario.admin.struts;

import ieci.tecdoc.sgm.core.config.impl.spring.Config;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.autenticacion.ServicioAutenticacionUsuarios;

public class Configuracion {

	private static Config configuracion;
	
	static{
		configuracion = new Config();
	}
	
	public static Config getConfiguracion(){
		return configuracion;
	}
	
	public static ServicioAutenticacionUsuarios getUserAuthService() throws SigemException{
		return LocalizadorServicios.getServicioAutenticacionUsuarios(configuracion);
	}
}
