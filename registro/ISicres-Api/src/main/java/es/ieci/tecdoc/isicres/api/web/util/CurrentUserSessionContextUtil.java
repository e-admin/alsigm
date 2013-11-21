package es.ieci.tecdoc.isicres.api.web.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.keys.IDocKeys;
import com.ieci.tecdoc.common.utils.ISicresAPerms;
import com.ieci.tecdoc.common.utils.ISicresGenPerms;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;

import es.ieci.tecdoc.isicres.api.business.dao.legacy.impl.LibroAdapter;
import es.ieci.tecdoc.isicres.api.business.manager.ContextoAplicacionManager;
import es.ieci.tecdoc.isicres.api.business.manager.ContextoAplicacionManagerFactory;
import es.ieci.tecdoc.isicres.api.business.manager.impl.mapper.ScrOficToOficinaVOMapper;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.ConfiguracionUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;
import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosAplicacionVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;

public class CurrentUserSessionContextUtil {

	/**
	 * helper para obtener datos del modelo de objetos antiguo
	 */
	protected CurrentUserSessionContextUtilHelper currentUserSessionContextUtilHelper= new CurrentUserSessionContextUtilHelper();

	/**
	 * Metodo que obtiene el {@link ContextoAplicacionVO} actual y lo setea en la varibale threadlocal correspondiente
	 *
	 * @param request
	 * @return
	 * @throws SessionException
	 * @throws TecDocException
	 */
	public ContextoAplicacionVO getContextoAplicacionActual(HttpServletRequest request) throws SessionException, TecDocException {

		ContextoAplicacionVO result =null;

		//idEntidad
		String idEntidadActual=getEntidadActual(request);

		//usuario actual
		UsuarioVO usuario=getUsuarioActual(request);

		//libro actual
		BaseLibroVO libroActual = getLibroActual(request);

		//oficina actual
		OficinaVO oficinaActual = getOficinaActual(request);

		//registroActual
		BaseRegistroVO registroActual = getRegistroActual(request);

		//seteamos la vble threadlocal
		MultiEntityContextHolder.setEntity(idEntidadActual);
		ContextoAplicacionManager contextoAplicacionManager = getContextoAplicacionManager();
		contextoAplicacionManager.setUsuarioActual(usuario);
		contextoAplicacionManager.setOficinaActual(oficinaActual);
		contextoAplicacionManager.setLibroActual(libroActual);
		contextoAplicacionManager.setRegistroActual(registroActual);

		result=contextoAplicacionManager.getContextoAplicacionVO();

		return result;
	}

	/**
	 * Obtiene el identificador del libro que esta actualmente en la session
	 * @param request
	 * @return
	 */
	public Integer getIdLibroActual(HttpServletRequest request){
		Integer result=getCurrentUserSessionContextUtilHelper().getIdLibro(request);
		return result;
	}

	/**
	 * Obtiene la entidad actual de la aplicacion
	 * @param request
	 * @return
	 */
	public String getEntidadActual(HttpServletRequest request){
		String result=getCurrentUserSessionContextUtilHelper().getIdEntidad(request);
		return result;
	}

	/**
	 * Obtiene la oficina actual del usuario
	 * @param request
	 * @return
	 * @throws SessionException
	 * @throws TecDocException
	 */
	public OficinaVO getOficinaActual(HttpServletRequest request) throws SessionException, TecDocException{
		OficinaVO result=null;
		String sessionID=getSessionIdActual(request);
		ScrOfic scrOfic = getCurrentUserSessionContextUtilHelper().getScrOfic(sessionID);
		result=new ScrOficToOficinaVOMapper().map(scrOfic);
		return result;

	}

	/**
	 * Obtiene el registro actual con el que esta trabajando el usuario
	 * Por ahora solo contiene el idLibro e idRegistro
	 * @param request
	 * @return
	 * @throws TecDocException
	 * @throws SessionException
	 */
	public BaseRegistroVO getRegistroActual(HttpServletRequest request) throws SessionException, TecDocException{
		BaseRegistroVO registroActual = null;

		Integer idRegistro = getCurrentUserSessionContextUtilHelper().getIdRegistro(request);
		if (idRegistro!=null){
			registroActual = new BaseRegistroVO();

			Integer idLibro = getCurrentUserSessionContextUtilHelper().getIdLibro(request);

			IdentificadorRegistroVO identificadorRegistro= new IdentificadorRegistroVO();
			identificadorRegistro.setIdLibro(idLibro.toString());
			identificadorRegistro.setIdRegistro(idRegistro.toString());
			registroActual.setId(identificadorRegistro);
		}

		return registroActual;

	}

	/**
	 * Obitene la SessionId actual que se usara para obtener otros objetos de la session actual
	 * @param request
	 * @return
	 */
	public String getSessionIdActual(HttpServletRequest request){
		String result=getCurrentUserSessionContextUtilHelper().getSessionId(request);
		return result;
	}

 	/**
 	 * Obtiene el libro actual con el que esta trabajando el usuario
 	 * @param request
 	 * @return
 	 * @throws SessionException
 	 * @throws TecDocException
 	 */
 	public BaseLibroVO getLibroActual(HttpServletRequest request) throws SessionException, TecDocException{

		BaseLibroVO result=null;
		String sessionID=getSessionIdActual(request);
		Integer idLibro=getIdLibroActual(request);
		if (idLibro!=null){
			Idocarchdet idocarchdetFldDef=getCurrentUserSessionContextUtilHelper().getIdocarchdetFldDef(sessionID, idLibro);
			Idocarchdet idocarchdetValidDef =getCurrentUserSessionContextUtilHelper().getIdocarchdetValidDef(sessionID, idLibro);
			ScrRegstate scrregstate=getCurrentUserSessionContextUtilHelper().getScrRegstate(sessionID, idLibro);
			//libro actual
			LibroAdapter libroAdapter= new LibroAdapter();
			result = libroAdapter.libroAdapter(scrregstate, idocarchdetFldDef, idocarchdetValidDef);
		}
		return result;
	}

	/**
	 * Obtiene el usuario actual que esta logado en el sistema
	 * @param request
	 * @return
	 * @throws SessionException
	 * @throws TecDocException
	 */
	public UsuarioVO getUsuarioActual(HttpServletRequest request) throws SessionException, TecDocException{

		String sessionID=getCurrentUserSessionContextUtilHelper().getSessionId(request);

		UsuarioVO usuario= new UsuarioVO();
		AuthenticationUser user = getCurrentUserSessionContextUtilHelper().getAuthenticationUser(sessionID);

		usuario.setId(user.getId().toString());

		//login de usuario
		String loginName=user.getName();
		usuario.setLoginName(loginName);

		//nombre completo de usuario
		String fullName=(String) request.getSession(true).getAttribute(Keys.J_USERNAME);
		usuario.setFullName(fullName);

		ConfiguracionUsuarioVO configuracionUsuario=getConfiguracionUsuario(request);

		//configuracion de usuario
		usuario.setConfiguracionUsuario(configuracionUsuario);

		//permisos de usuario
		PermisosUsuarioVO permisosUsuario = getPermisosUsuarioActual(request);
		usuario.setPermisos(permisosUsuario);

		return usuario;
	}

	/**Obtiene la configuracion del usuario que esta logado en el sistema
	 * @param request
	 * @return
	 * @throws SessionException
	 * @throws TecDocException
	 */
	public ConfiguracionUsuarioVO getConfiguracionUsuario(HttpServletRequest request) throws SessionException, TecDocException{

		ConfiguracionUsuarioVO result= new ConfiguracionUsuarioVO();

		String sessionID=getSessionIdActual(request);

		//sessionID
		result.setSessionID(sessionID);

		UseCaseConf useCaseConf = getCurrentUserSessionContextUtilHelper().getUseCaseConf(request);

		//datos de la oficina actual
		OficinaVO oficinaActual = getOficinaActual(request);
		result.setOficina(oficinaActual);

		//entidad
		String idEntidad = useCaseConf.getEntidadId();
		result.setIdEntidad(idEntidad);

		//locale
		Locale locale = useCaseConf.getLocale();
		result.setLocale(locale);

		//profile
		String profile=getCurrentUserSessionContextUtilHelper().getProfile(sessionID);
		result.setProfile(profile);

		return result;

	}

	/**
	 * Obtiene los permisos  del usuario actual
	 * @param request
	 * @return
	 * @throws SessionException
	 * @throws TecDocException
	 */
	public PermisosUsuarioVO getPermisosUsuarioActual(HttpServletRequest request) throws SessionException, TecDocException{

		PermisosUsuarioVO result = new PermisosUsuarioVO();
		String sessionID= getSessionIdActual(request);
		Integer idLibro=getIdLibroActual(request);


		//permisos a nivel de aplicacion
		PermisosAplicacionVO permisosAplicacion=getPermisosAplicacionActual(request);
		result.setPermisosAplicacion(permisosAplicacion);

		//permisos sobre el libro actual
		if (idLibro!=null){
			PermisosLibroVO permisoLibro=getPermisosLibroActual(request);
			result.getPermisosLibros().put(idLibro, permisoLibro);
		}

		return result;
	}

	/**
	 * Obtiene los permisos a nivel de aplicacion que tiene el usuario actual
	 * @param request
	 * @return
	 * @throws SessionException
	 * @throws TecDocException
	 */
	public PermisosAplicacionVO getPermisosAplicacionActual(HttpServletRequest request) throws SessionException, TecDocException{
		//permisos de aplicacion
		PermisosAplicacionVO result = new PermisosAplicacionVO();

		String sessionID = getSessionIdActual(request);
		ISicresGenPerms permsApli = getCurrentUserSessionContextUtilHelper().getISicresGenPerms(sessionID);


		// superusuario
		Iuserusertype userType = getCurrentUserSessionContextUtilHelper().getIuserusertype(sessionID);
		boolean superUsuario = userType.getType() == IDocKeys.IUSERUSERTYPE_USER_TYPE_ADMIN;
		result.setSuperUsuario(superUsuario);

		// terceros
		boolean altaTerceros = permsApli.canCreatePersons();
		result.setAltaTerceros(altaTerceros);

		boolean modificacionTerceros = permsApli.canUpdatePersons();
		result.setModificacionTerceros(modificacionTerceros);

		// distribucion
		boolean distribucionManual = permsApli.isCanDistRegisters();
		result.setDistribucionManual(distribucionManual);

		boolean aceptarDistribucion = permsApli.isCanAcceptRegisters();
		result.setAceptarDistribucion(aceptarDistribucion);

		boolean rechazarDistribucion = permsApli.isCanRejectRegisters();
		result.setRechazarDistribucion(rechazarDistribucion);

		boolean archivarDistribucion = permsApli.isCanArchiveRegisters();
		result.setArchivarDistribucion(archivarDistribucion);

		boolean cambiarDestinoDistribucion = permsApli.isCanChangeDestRegisters();
		result.setCambiarDestinoDistribucion(cambiarDestinoDistribucion);

		boolean cambiarDestinoDistribucionRechazada = permsApli.isCanChangeDestRejectRegisters();
		result.setCambiarDestinoDistribucionRechazada(cambiarDestinoDistribucionRechazada);

		// fechas de registro
		boolean altaFechaRegistro = permsApli.canIntroRegDate();
		result.setAltaFechaRegistro(altaFechaRegistro);

		boolean modificarFechaRegistro = permsApli.canUpdateRegDate();
		result.setModificarFechaRegistro(modificarFechaRegistro);

		// actualizar campos protegidos
		boolean modificarCamposProtegidos = permsApli.canUpdateProtectedFields();
		result.setModificarCamposProtegidos(modificarCamposProtegidos);

		// consultar documentacion anexa
		boolean consultarDocuAnexa = permsApli.isCanShowDocuments();
		result.setConsultarDocuAnexa(consultarDocuAnexa);

		// borrado de documentos anexos;
		boolean borrarDocuAnexa = permsApli.isCanDeleteDocuments();
		result.setBorrarDocuAnexa(borrarDocuAnexa);
		// Permisos administrativos
		// Gestion de las unidades administrativas
		boolean gestionUnidadesAdministrativas = permsApli.getCanModifyAdminUnits();
		result.setGestionUnidadesAdministrativas(gestionUnidadesAdministrativas);

		// Gestion de los informes;
		boolean gestionInformes = permsApli.getCanModifyReports();
		result.setGestionInformes(gestionInformes);

		// Gestion de tipos de asunto;
		boolean gestionTiposAsunto = permsApli.getCanModifyIssueTypes();
		result.setGestionTiposAsunto(gestionTiposAsunto);

		// Gestion de usuarios;
		boolean gestionUsuarios = permsApli.getCanModifyUsers();
		result.setGestionUsuarios(gestionUsuarios);

		// Gestion de tipos de transporte;
		boolean gestionTiposTransporte = permsApli.getCanModifyTransportTypes();
		result.setGestionTiposTransporte(gestionTiposTransporte);
		//

		// intercambio registral
		boolean operacionesIntercambioRegistral = permsApli.canAccessRegInterchange();
		result.setOperacionesIntercambioRegistral(operacionesIntercambioRegistral);

		return result;

	}

	/**
	 * Obtiene los permisos a nivel del libro actual que tiene el usuario actual
	 * @param request
	 * @return
	 * @throws SessionException
	 * @throws TecDocException
	 */
	public PermisosLibroVO getPermisosLibroActual(HttpServletRequest request) throws SessionException, TecDocException{
		PermisosLibroVO permisoLibro= null;

		Integer idLibroActual = getIdLibroActual(request);
		if (idLibroActual!=null){
			ISicresAPerms permsLibro = getCurrentUserSessionContextUtilHelper().getISicresAPerms(getSessionIdActual(request), getIdLibroActual(request));
			if (permsLibro!=null){
				permisoLibro = new PermisosLibroVO();
				permisoLibro.setIdLibro(idLibroActual.toString());
				permisoLibro.setAdministrador(permsLibro.isBookAdmin());
				permisoLibro.setConsulta(permsLibro.canQuery());
				permisoLibro.setCreacion(permsLibro.canCreate());
				permisoLibro.setModificacion(permsLibro.canModify());
			}
		}

		return permisoLibro;
	}


	protected ContextoAplicacionManager getContextoAplicacionManager() {
		return ContextoAplicacionManagerFactory.getInstance();
	}

	public CurrentUserSessionContextUtilHelper getCurrentUserSessionContextUtilHelper() {
		return currentUserSessionContextUtilHelper;
	}

	public void setCurrentUserSessionContextUtilHelper(
			CurrentUserSessionContextUtilHelper currentUserSessionContextUtilHelper) {
		this.currentUserSessionContextUtilHelper = currentUserSessionContextUtilHelper;
	}

}
