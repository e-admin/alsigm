package ieci.tecdoc.sgm.backoffice.action;

import ieci.tecdoc.core.base64.Base64Util;
import ieci.tecdoc.sgm.backoffice.form.LoginAccesoForm;
import ieci.tecdoc.sgm.backoffice.utils.Defs;
import ieci.tecdoc.sgm.backoffice.utils.Utilidades;
import ieci.tecdoc.sgm.core.admin.web.AdministracionHelper;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.admsesion.backoffice.ServicioAdministracionSesionesBackOffice;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.ServicioEstructuraOrganizativa;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.ServicioEstructuraOrganizativaLdap;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.CriterioBusquedaUsuarios;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.DatosUsuario;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ServicioGestionUsuariosBackOffice;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LoginAction extends Action{

	private static final Logger logger = Logger.getLogger(LoginAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		try{
			LoginAccesoForm formLogin =  (LoginAccesoForm)form;

			HttpSession session = request.getSession();
			String idEntidad = null, idAplicacion = null, usuario = null, password = null;

			idEntidad = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD);
			if (Utilidades.isNuloOVacio(idEntidad)) {
				idEntidad = new String("");
			}

			idAplicacion = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_APLICACION);
			if (Utilidades.isNuloOVacio(idAplicacion)) {
				idAplicacion = new String("");
			}

			if (Utilidades.isNuloOVacio(idEntidad)){
				List oLista = AdministracionHelper.obtenerListaEntidades();
				if(oLista.size()==1) {
					Entidad oEntidad = (Entidad)oLista.get(0);
					session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD, oEntidad.getIdentificador());
				} else {
					request.setAttribute("entidades", oLista);
			   		return mapping.findForward("entidades");
				}
			}

			usuario = formLogin.getUsername();
			if (Utilidades.isNuloOVacio(usuario)) {
				usuario = new String("");
			}

			password = formLogin.getPassword();
			if (Utilidades.isNuloOVacio(password)) {
				password = new String("");
			}

			ServicioGestionUsuariosBackOffice oServicio = LocalizadorServicios.getServicioAutenticacionUsuariosBackOffice();
			DatosUsuario user = new DatosUsuario();
			Entidad entidad = new Entidad();
			entidad.setIdentificador(idEntidad);

			String singleSignOn = (String) session.getServletContext().getAttribute(Defs.PLUGIN_SINGLE_SIGN_ON);
			if ((singleSignOn != null) && (singleSignOn.equalsIgnoreCase("true"))) {

				// Autenticación Single-Sign On
				usuario = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_USUARIO);
				if (!Utilidades.isNuloOVacio(usuario)) {

					ServicioEstructuraOrganizativaLdap oServicioEstOrgLdap = LocalizadorServicios.getServicioEstructuraOrganizativaLdap();
					DatosUsuario[] users = null;
					if (oServicioEstOrgLdap.esEntidadLdap(entidad.getIdentificador())) {

						ServicioEstructuraOrganizativa oServicioEstOrg = LocalizadorServicios.getServicioEstructuraOrganizativa();
						UsuarioLdap usuarioLdap = oServicioEstOrg.getUsuarioLdapByFullName(usuario, entidad.getIdentificador());
						DatosUsuario datoUser = oServicioEstOrgLdap.getDatosUsuarioServicio(String.valueOf(usuarioLdap.get_id()), usuarioLdap.get_ldapfullname());
						users = new DatosUsuario[]{datoUser};
					}else{
						CriterioBusquedaUsuarios criteriobusquedausuarios = new CriterioBusquedaUsuarios();
						criteriobusquedausuarios.setUser(usuario);
						users = oServicio.findUsers(criteriobusquedausuarios, entidad);
					}

					if ((users != null) && (users.length > 0)) {
						user = users[0];
						if(oServicioEstOrgLdap.esEntidadLdap(entidad.getIdentificador())) {
							// Usuario de LDAP
							user.setAuthenticationType(DatosUsuario.AUTHENTICATION_TYPE_LDAP);
							ServicioEstructuraOrganizativa oServicioEstOrg = LocalizadorServicios.getServicioEstructuraOrganizativa();
							String ldapGuid = oServicioEstOrg.getUsuarioLdapBasicById(Integer.parseInt(user.getId()), entidad.getIdentificador());
							user.setLdapGuid(ldapGuid);
						} else {
			                // Si el usuario es de Invesdoc
							user.setAuthenticationType(DatosUsuario.AUTHENTICATION_TYPE_INVESDOC);
						}

						return createSession(mapping, request, oServicio, user, idEntidad, idAplicacion);
					} else {
						request.setAttribute(Defs.MENSAJE_LOGIN, "mensaje_error.sso.validar_usuario");
						return mapping.findForward("login");
					}
				} else {
					request.setAttribute(Defs.MENSAJE_LOGIN, "mensaje_error.sso.no_usuario");
					return mapping.findForward("login");
				}
			} else {

				// Autenticación mediante Usuario / Password
				if (!Utilidades.isNuloOVacio(usuario) && !Utilidades.isNuloOVacio(password)) {
					user.setUser(usuario);
					user.setPassword(password);
					user = oServicio.authenticateUser(user, entidad);
					if (user != null) {
						return createSession(mapping, request, oServicio, user, idEntidad, idAplicacion);
					} else {
						request.setAttribute("invalid_user", "true");
						return mapping.findForward("login");
					}
				} else {
					return mapping.findForward("login");
				}
			}

		} catch(Exception e){
			logger.error("Se ha producido un error en el login", e.fillInStackTrace());
			request.setAttribute("invalid_user", "true");
			return mapping.findForward("login");
	   	}
	}

	protected ActionForward createSession(ActionMapping mapping,
			  HttpServletRequest request,
			  ServicioGestionUsuariosBackOffice oServicio,
			  DatosUsuario user,
			  String idEntidad,
			  String idAplicacion) throws Exception {

		HttpSession session = request.getSession();
		ServicioAdministracionSesionesBackOffice oClient = LocalizadorServicios.getServicioAdministracionSesionesBackOffice();

		String key = oClient.nuevaSesion(user.getUser(), idEntidad);

		// SLuna-20081217-I

		// Introducimos una nueva 'llave' en la sesión que sólo se
		// usará cuando entremos en el módulo de Registro
		// Presencial. Se crea una 'llave' con el usuario codificado
		// en BASE64 por si su nombre de usuario contiene algún
		// carácter no ANSI.
		String keyForRP = null;

		String usuarioBase64 = Base64Util.encodeString(user.getUser());
		StringBuffer buffer = new StringBuffer();

		buffer.append(usuarioBase64).append("##CODE##");
		keyForRP = oClient
				.nuevaSesion(buffer.toString(), idEntidad);
		session.setAttribute("keySesionUsuarioRP", keyForRP);

		// SLuna-20081217-F

		String datosSesion = "<IdUsuario>"+user.getId()+"</IdUsuario>"+"<TipoAutenticacion>"+user.getAuthenticationType()+"</TipoAutenticacion>";
		String tipoAutenticacion = user.getAuthenticationType();
		if ((!"".equals(tipoAutenticacion))&&(tipoAutenticacion.equals(DatosUsuario.AUTHENTICATION_TYPE_LDAP))) {
			datosSesion += "<LdapGuid>"+user.getLdapGuid()+"</LdapGuid>";
		}

		session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO, key);
		oClient.modificarDatosSesion(key, datosSesion);
		if (!Utilidades.isNuloOVacio(idAplicacion)){
			// Si no es la aplicación de Archivo
			if (!ConstantesGestionUsuariosBackOffice.APLICACION_ARCHIVO.equals(idAplicacion)) {
				String url = oServicio.obtenerDireccionAplicacion(idAplicacion);
				url = AutenticacionBackOffice.comprobarURL(request, url);
				request.setAttribute(Defs.PARAMETRO_URL, url);
				return mapping.findForward("success");
			}
		}

		// Aplicación de Archivo
		List oLista = Utilidades.obtenerListaAplicaciones(request, key);
		request.setAttribute("aplicaciones", oLista);
		return mapping.findForward("aplicaciones");
	}

}
