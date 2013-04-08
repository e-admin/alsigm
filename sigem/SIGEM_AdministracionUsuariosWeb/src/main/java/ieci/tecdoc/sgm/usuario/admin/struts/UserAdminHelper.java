package ieci.tecdoc.sgm.usuario.admin.struts;

import ieci.tecdoc.sgm.core.admin.web.SesionAdminHelper;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.autenticacion.DatosUsuario;
import ieci.tecdoc.sgm.core.services.autenticacion.AutenticacionUsuarioException;
import ieci.tecdoc.sgm.core.services.autenticacion.ServicioAutenticacionUsuarios;
import ieci.tecdoc.sgm.core.services.autenticacion.CriterioBusquedaUsuarios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.validator.DynaValidatorForm;

public class UserAdminHelper {

	public static List findUsers(HttpServletRequest request, DynaValidatorForm poForm) throws AutenticacionUsuarioException{
		List oLista = null;
		DatosUsuario[] aLista = null;
		try {
			aLista = getUserAuthService().findUsers(obtenerCriterioBusqueda(poForm), obtenerEntidad(request));
			oLista = Arrays.asList(aLista);
		} catch (AutenticacionUsuarioException e) {
			throw e;
		}
		return oLista;
	}
	

	public static DatosUsuario getUser(HttpServletRequest request, DynaValidatorForm poForm) throws AutenticacionUsuarioException{
		String cIdUser = (String)poForm.get(Constants.USUARIO_USUARIO_FIELD);
		if(cIdUser != null){
			return getUser(request, cIdUser);
		}else{
			return null;
		}
	}

	public static DatosUsuario getUser(HttpServletRequest request, String idUser) throws AutenticacionUsuarioException{
		DatosUsuario ouser = new DatosUsuario();
		ouser.setUser(idUser);
		try {
			ouser = getUserAuthService().getUser(ouser, obtenerEntidad(request));
		} catch (AutenticacionUsuarioException e) {
			throw e;
		}
		return ouser;		
	}

	public static void updateUser(HttpServletRequest request, DynaValidatorForm poForm) throws AutenticacionUsuarioException{
		updateUser(request, getUsuario(poForm));
	}

	public static void updateUser(HttpServletRequest request,DatosUsuario pcUser) throws AutenticacionUsuarioException{
		try {
			getUserAuthService().updateUser(pcUser, obtenerEntidad(request));
		} catch (AutenticacionUsuarioException e) {
			throw e;
		}		
	}
	
	private static DatosUsuario getUsuario(DynaValidatorForm poForm){
		DatosUsuario oUsuario = new DatosUsuario();
		oUsuario.setLastname((String)poForm.get(Constants.USUARIO_APELLIDOS_FIELD));
		oUsuario.setEmail((String)poForm.get(Constants.USUARIO_EMAIL_FIELD));
		oUsuario.setId((String)poForm.get(Constants.USUARIO_ID_FIELD));
		oUsuario.setName((String)poForm.get(Constants.USUARIO_NOMBRE_FIELD));
		oUsuario.setPassword((String)poForm.get(Constants.USUARIO_PASSWORD_FIELD));
		oUsuario.setUser((String)poForm.get(Constants.USUARIO_USUARIO_FIELD));
		return oUsuario;
	}
	
	private static CriterioBusquedaUsuarios obtenerCriterioBusqueda(DynaValidatorForm oForm){
		CriterioBusquedaUsuarios oCriterio = new CriterioBusquedaUsuarios();
		oCriterio.setUser((String)oForm.get(Constants.BUSQUEDA_USUARIOS_USER_FIELD));
		return oCriterio;
	}
	
	public static void createUser(HttpServletRequest request, DynaValidatorForm poForm) throws AutenticacionUsuarioException{
		createUser(request, getUsuario(poForm));
	}

	public static void deleteUser(HttpServletRequest request, String idUser) throws AutenticacionUsuarioException{
		DatosUsuario oUser = new DatosUsuario();
		oUser.setUser(idUser);
		try {
			getUserAuthService().deleteUser(oUser, obtenerEntidad(request));
		} catch (AutenticacionUsuarioException e) {
			throw e;
		}		
	}
	
	public static void deleteUser(HttpServletRequest request, DynaValidatorForm poForm) throws AutenticacionUsuarioException{
		String cIdUser = (String)poForm.get(Constants.USUARIO_USUARIO_FIELD);
		deleteUser(request, cIdUser);
	}
	

	private static void createUser(HttpServletRequest request, DatosUsuario pcUser) throws AutenticacionUsuarioException{
		try{ 
			getUserAuthService().createUser(pcUser, obtenerEntidad(request));
		} catch (AutenticacionUsuarioException e) {
			throw e;
		}				
	}
	
	private static ServicioAutenticacionUsuarios getUserAuthService() throws AutenticacionUsuarioException{
		try {
			return Configuracion.getUserAuthService();
		} catch (SigemException e) {
			throw new AutenticacionUsuarioException(AutenticacionUsuarioException.EXC_GENERIC_EXCEPCION, e);
		}
	}
	
	private static Entidad obtenerEntidad(HttpServletRequest request){
		Entidad oEntidad = new Entidad();
		oEntidad.setIdentificador(SesionAdminHelper.obtenerIdentificadorEntidad(request));
		return oEntidad;
	}
}
