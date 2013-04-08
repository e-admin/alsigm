package ieci.tecdoc.sgm.usuario.ws.client;

import java.rmi.RemoteException;

import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.autenticacion.AutenticacionUsuarioException;
import ieci.tecdoc.sgm.core.services.autenticacion.CriterioBusquedaUsuarios;
import ieci.tecdoc.sgm.core.services.autenticacion.DatosUsuario;
import ieci.tecdoc.sgm.core.services.autenticacion.ServicioAutenticacionUsuarios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;

public class ServicioAutenticacionUsuariosRemoteClient implements
		ServicioAutenticacionUsuarios {

	private AdministracionUsuariosPortalWebService service;
	
	public AdministracionUsuariosPortalWebService getService() {
		return service;
	}

	public void setService(AdministracionUsuariosPortalWebService service) {
		this.service = service;
	}

	/**
	 * Método que obtiene los datos de un determinado usuario.
	 * Comprueba que el nombre de usuario y contraseña sean correctos.
	 * @param usuario Nombre de usuario
	 * @param password Contraseña.
	 * @return User Datos del usuario.
	 * @throws AutenticacionUsuarioException En caso de producirse alguna excepción.
	 */	
	public DatosUsuario authenticateUser(DatosUsuario user, Entidad entidad)
			throws AutenticacionUsuarioException {
		try{
			Usuario oUser = getService().autenticarUsuario(getUserWS(user), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oUser)){
				return getUserServicio(oUser);
			}else{
				throw getAutenticacionUsuarioException((IRetornoServicio)oUser);
			}
		} catch (RemoteException e) {
			throw new AutenticacionUsuarioException(AutenticacionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}		
	}

	/**
	 * Método que da da alta en el sistema un nuevo usuario.
	 * @param user Datos de usuario.
	 * @throws AutenticacionUsuarioException En caso de producirse algún error.
	 */
	public void createUser(DatosUsuario user, Entidad entidad)
			throws AutenticacionUsuarioException {
		try{
			RetornoServicio oRetorno = getService()
					.crearUsuario(getUserWS(user), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getAutenticacionUsuarioException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new AutenticacionUsuarioException(AutenticacionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}		
	}

	/**
	 * Elimina la información de un usuario
	 * @param user Usuario de acceso.
	 * @return User Datos de usuario.
	 * @throws AutenticacionUsuarioException Si se produce algún error.
	 */
	public void deleteUser(DatosUsuario user, Entidad entidad)
			throws AutenticacionUsuarioException {
		try{
			RetornoServicio oRetorno = getService()
					.eliminarUsuario(getUserWS(user), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getAutenticacionUsuarioException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new AutenticacionUsuarioException(AutenticacionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}		
	}

	
	/**
	 * Devuelve una lista de usuarios que cumplen el criterio de búsqueda que llega como parámetro
	 * @param criteria Criterio de búsqueda.
	 * @return Array de DatosUsuario.
	 * @throws AutenticacionUsuarioException En caso de producirse algún error.
	 */
	public DatosUsuario[] findUsers(CriterioBusquedaUsuarios criteria, Entidad entidad)
			throws AutenticacionUsuarioException {
		try{
			ListaUsuarios oUsers = getService().buscarUsuarios(getCriterioWS(criteria), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oUsers)){
				return getDatosUsuariosServicio(oUsers);
			}else{
				throw getAutenticacionUsuarioException((IRetornoServicio)oUsers);
			}
		} catch (RemoteException e) {
			throw new AutenticacionUsuarioException(AutenticacionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}		
	}

	/**
	 * Método que recupera los datos de un usuario.
	 * @param user Nombre de usuario.
	 * @return Datos del usuario
	 * @throws AutenticacionUsuarioException En caso de producirse algún error.
	 */
	public DatosUsuario getUser(DatosUsuario user, Entidad entidad)
			throws AutenticacionUsuarioException {
		try{
			Usuario oUser = getService().obtenerUsuario(getUserWS(user), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oUser)){
				return getUserServicio(oUser);
			}else{
				throw getAutenticacionUsuarioException((IRetornoServicio)oUser);
			}
		} catch (RemoteException e) {
			throw new AutenticacionUsuarioException(AutenticacionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}		
	}

	/**
	 * Actualiza los datos de un usuario.
	 * @param user Datos del usuario.
	 * @throws AutenticacionUsuarioException En caso de producirse algún error.
	 */
	public void updateUser(DatosUsuario user, Entidad entidad)
			throws AutenticacionUsuarioException {
		try{
			RetornoServicio oRetorno = getService()
					.actualizarUsuario(getUserWS(user), getEntidadWS(entidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getAutenticacionUsuarioException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new AutenticacionUsuarioException(AutenticacionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}		
	}

	private AutenticacionUsuarioException getAutenticacionUsuarioException(IRetornoServicio oReturn){
		return new AutenticacionUsuarioException(Long.valueOf(oReturn.getErrorCode()).longValue());
	}

	private Usuario getUserWS(DatosUsuario poUser){
		if(poUser == null){
			return null;
		}
		Usuario oUser = new Usuario();
		oUser.setEmail(poUser.getEmail());
		oUser.setId(poUser.getId());
		oUser.setLastname(poUser.getLastname());
		oUser.setName(poUser.getName());
		oUser.setPassword(poUser.getPassword());
		oUser.setUser(poUser.getUser());
		return oUser;
	}
	
	private DatosUsuario getUserServicio(Usuario poUser){
		if(poUser == null){
			return null;
		}
		DatosUsuario oUser = new DatosUsuario();
		oUser.setEmail(poUser.getEmail());
		oUser.setId(poUser.getId());
		oUser.setLastname(poUser.getLastname());
		oUser.setName(poUser.getName());
		oUser.setPassword(poUser.getPassword());
		oUser.setUser(poUser.getUser());
		return oUser;
	}
	
	private CriterioBusquedaUsuario getCriterioWS(CriterioBusquedaUsuarios poCriterio){
		if(poCriterio == null){
			return null;
		}
		CriterioBusquedaUsuario oCriterio = new CriterioBusquedaUsuario();
		oCriterio.setUser(poCriterio.getUser());
		return oCriterio;
	}
	
	private DatosUsuario[] getDatosUsuariosServicio(ListaUsuarios poUsers){
		if(poUsers == null){
			return null;
		}
		DatosUsuario[] oLista = null;
		if(poUsers.getUsers() != null ){
			oLista = new DatosUsuario[poUsers.getUsers().length];
			for(int i=0; i<poUsers.getUsers().length; i++){
				oLista[i] = getUserServicio(poUsers.getUsers()[i]);
			}
		}
		return oLista;
	}
	
	private ieci.tecdoc.sgm.usuario.ws.client.Entidad getEntidadWS(Entidad poEntidad){
		if(poEntidad == null){
			return null;
		}
		ieci.tecdoc.sgm.usuario.ws.client.Entidad oEntidad = new ieci.tecdoc.sgm.usuario.ws.client.Entidad();
		oEntidad.setIdentificador(poEntidad.getIdentificador());
		return oEntidad;
	}
	
}
