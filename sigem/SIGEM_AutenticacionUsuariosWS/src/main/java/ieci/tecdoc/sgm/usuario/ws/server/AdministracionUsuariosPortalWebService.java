package ieci.tecdoc.sgm.usuario.ws.server;

import ieci.tecdoc.sgm.core.config.impl.spring.DefaultConfiguration;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.autenticacion.AutenticacionUsuarioException;
import ieci.tecdoc.sgm.core.services.autenticacion.ServicioAutenticacionUsuarios;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;

import javax.xml.soap.SOAPException;

public class AdministracionUsuariosPortalWebService {

	private static final String SERVICE_NAME = ConstantesServicios.SERVICE_USER_AUTHENTICATION;
	
	public RetornoServicio crearUsuario(Usuario user, Entidad entidad){
		try {
			getUserAuthService().createUser(getServiceUser(user), getEntidadServicio(entidad));
			return ServiciosUtils.createReturnOK();
		} catch(AutenticacionUsuarioException e){
			return ServiciosUtils.createReturnError(getErrorCode(e));
		} catch (Throwable e){
			return ServiciosUtils.createReturnError();
		}
	}

	public RetornoServicio eliminarUsuario(Usuario user, Entidad entidad) {
		try {
			getUserAuthService().deleteUser(getServiceUser(user), getEntidadServicio(entidad));
			return ServiciosUtils.createReturnOK();
		} catch(AutenticacionUsuarioException e){
			return ServiciosUtils.createReturnError(getErrorCode(e));
		} catch (Throwable e){
			return ServiciosUtils.createReturnError();
		}
	}

	public ListaUsuarios buscarUsuarios(CriterioBusquedaUsuario criteria, Entidad entidad) {
		ListaUsuarios oList = new ListaUsuarios();
		try {
			ieci.tecdoc.sgm.core.services.autenticacion.DatosUsuario oUsers[] = getUserAuthService().findUsers(getServiceSearchCriteria(criteria), getEntidadServicio(entidad));
			oList.setUsers(getWSUsers(oUsers));
			return (ListaUsuarios)ServiciosUtils.completeReturnOK((RetornoServicio)oList);
		} catch(AutenticacionUsuarioException e){
			return (ListaUsuarios)ServiciosUtils.completeReturnError((RetornoServicio)oList, getErrorCode(e));
		} catch (Throwable e){
			return (ListaUsuarios)ServiciosUtils.completeReturnError((RetornoServicio)oList);
		}
	}

	public Usuario autenticarUsuario(Usuario user, Entidad entidad) {
		try {
			Usuario oUser = getWSServiceUser(getUserAuthService().authenticateUser(getServiceUser(user), getEntidadServicio(entidad)));
			return (Usuario)ServiciosUtils.completeReturnOK((RetornoServicio) oUser);
		} catch(AutenticacionUsuarioException e){
			return (Usuario)ServiciosUtils.completeReturnError(user, getErrorCode(e));
		} catch (Throwable e){
			return (Usuario)ServiciosUtils.completeReturnError(user);
		}
	}

	public Usuario obtenerUsuario(Usuario user, Entidad entidad) {
		try {
			Usuario oUser =  getWSServiceUser(getUserAuthService().getUser(getServiceUser(user), getEntidadServicio(entidad)));
			return (Usuario)ServiciosUtils.completeReturnOK((RetornoServicio) oUser);
		} catch(AutenticacionUsuarioException e){
			return (Usuario)ServiciosUtils.completeReturnError(user, getErrorCode(e));
		} catch (Throwable e){
			return (Usuario)ServiciosUtils.completeReturnError(user);
		}
	}

	public RetornoServicio actualizarUsuario(Usuario user, Entidad entidad) {
		try {
			getUserAuthService().updateUser(getServiceUser(user), getEntidadServicio(entidad));
			return ServiciosUtils.createReturnOK();
		} catch(AutenticacionUsuarioException e){
			return ServiciosUtils.createReturnError(getErrorCode(e));
		} catch (Throwable e){
			return ServiciosUtils.createReturnError();
		}
	}
	
	private ServicioAutenticacionUsuarios getUserAuthService() throws SOAPException, SigemException{
		String cImpl = UtilAxis.getImplementacion(org.apache.axis.MessageContext.getCurrentContext());
		if((cImpl == null) ||("".equals(cImpl))){
			return LocalizadorServicios.getServicioAutenticacionUsuarios(DefaultConfiguration.getConfiguration());
		}else{
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".").append(cImpl);
			return LocalizadorServicios.getServicioAutenticacionUsuarios(DefaultConfiguration.getConfiguration(), sbImpl.toString());
		}
	}

	private String getErrorCode(AutenticacionUsuarioException peException){
		if(peException == null){
			return ConstantesServicios.SERVICE_RETURN_UNKNOWN_ERROR;
		}
		return String.valueOf(peException.getErrorCode());
	}
	
	private ieci.tecdoc.sgm.core.services.autenticacion.DatosUsuario getServiceUser(Usuario poUser){
		ieci.tecdoc.sgm.core.services.autenticacion.DatosUsuario oServiceUser = new ieci.tecdoc.sgm.core.services.autenticacion.DatosUsuario();
		oServiceUser.setEmail(poUser.getEmail());
		oServiceUser.setId(poUser.getId());
		oServiceUser.setLastname(poUser.getLastname());
		oServiceUser.setName(poUser.getName());
		oServiceUser.setPassword(poUser.getPassword());
		oServiceUser.setUser(poUser.getUser());
		return oServiceUser;
	}

	private Usuario getWSServiceUser(ieci.tecdoc.sgm.core.services.autenticacion.DatosUsuario poUser){
		Usuario oServiceUser = new Usuario();
		oServiceUser.setEmail(poUser.getEmail());
		oServiceUser.setId(poUser.getId());
		oServiceUser.setLastname(poUser.getLastname());
		oServiceUser.setName(poUser.getName());
		oServiceUser.setPassword(poUser.getPassword());
		oServiceUser.setUser(poUser.getUser());
		return oServiceUser;
	}
	
	private ieci.tecdoc.sgm.core.services.autenticacion.CriterioBusquedaUsuarios getServiceSearchCriteria(CriterioBusquedaUsuario poCriteria){
		ieci.tecdoc.sgm.core.services.autenticacion.CriterioBusquedaUsuarios oCriteria = new ieci.tecdoc.sgm.core.services.autenticacion.CriterioBusquedaUsuarios();
		oCriteria.setUser(poCriteria.getUser());
		return oCriteria;
	}
	
	private Usuario[] getWSUsers(ieci.tecdoc.sgm.core.services.autenticacion.DatosUsuario[] poUsers){
		if(poUsers == null){
			return null;
		}
		Usuario[] oUsers = new Usuario[poUsers.length];
		for(int eCounter = 0; eCounter < poUsers.length; eCounter++){
			oUsers[eCounter] = getWSServiceUser(poUsers[eCounter]);
		}
		return oUsers;
	}
	
	private ieci.tecdoc.sgm.core.services.dto.Entidad getEntidadServicio(Entidad poEntidad){
		if(poEntidad == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.dto.Entidad entidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
		entidad.setIdentificador(poEntidad.getIdentificador());
		return entidad;
	}

}
