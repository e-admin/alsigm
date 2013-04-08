package ieci.tecdoc.sgm.autenticacion.utils;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.autenticacion.DatosUsuario;
import ieci.tecdoc.sgm.core.services.autenticacion.AutenticacionUsuarioException;
import ieci.tecdoc.sgm.core.services.dto.Entidad;

public class UserAuthServiceHelper {

	public static DatosUsuario get(String pcNombre, String pcPassword, String entidad) throws AutenticacionUsuarioException, SigemException{
		DatosUsuario oUser = new DatosUsuario();
		oUser.setUser(pcNombre);
		oUser.setPassword(pcPassword);
		Entidad oEntidad = new Entidad();
		oEntidad.setIdentificador(entidad);
		return Configuracion.getUserAuthService().authenticateUser(oUser, oEntidad);
	}
	 
}
