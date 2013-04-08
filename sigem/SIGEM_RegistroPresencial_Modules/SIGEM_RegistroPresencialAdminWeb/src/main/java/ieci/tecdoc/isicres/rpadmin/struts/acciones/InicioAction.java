package ieci.tecdoc.isicres.rpadmin.struts.acciones;

import ieci.tecdoc.isicres.rpadmin.struts.util.PermisosInternosUtils;
import ieci.tecdoc.isicres.rpadmin.struts.util.SesionHelper;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.admsesion.administracion.ServicioAdministracionSesionesAdministrador;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;
import ieci.tecdoc.sgm.sesiones.administrador.ws.client.Sesion;

import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.hibernate.Session;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.Configurator;
import com.ieci.tecdoc.common.utils.ISicresGenPerms;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.utils.HibernateUtil;

import es.ieci.tecdoc.isicres.admin.core.manager.DBSessionManager;

/*$Id*/

public class InicioAction extends RPAdminWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String entidad = SesionHelper.obtenerEntidad(request)
				.getIdentificador();
		String caseSensitive = DBSessionManager.getDBCaseSensitive(entidad);

		SesionHelper.guardarCaseSensitive(request, caseSensitive);
		Session session = HibernateUtil.currentSession(entidad);

		Sesion sesion = AutenticacionAdministracion.obtenerDatos(request);
		String datosEspecificos = sesion.getDatosEspecificos();
		datosEspecificos = "<datos>"+datosEspecificos+"</datos>";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(datosEspecificos)));
		String idUsuario = null;
		boolean isSuperUser =false;
		NodeList nodos = document.getElementsByTagName("IdUsuario");
		if(nodos.getLength()==1)
		{
			idUsuario = nodos.item(0).getTextContent();
		}
		ISicresGenPerms genPerms = new ISicresGenPerms();
		//SI idUsuario==null es que es usuario ADMINISTRADOR DE SIGEM
		//Por tanto no hace falta ocmprobar permisos internos

		String authenticationPolicyType = Configurator
				.getInstance()
				.getProperty(
						ConfigurationKeys.KEY_SERVER_AUTHENTICATION_POLICY_TYPE);

		boolean isLdapAuthenticationPolicy = false;

		if (StringUtils.equalsIgnoreCase(authenticationPolicyType,
				Keys.AUTHENTICATION_POLICY_TYPE_LDAP)) {
			isLdapAuthenticationPolicy = true;
		}

		boolean enabledIntercambioRegistral = Configurator
				.getInstance()
				.getPropertyBoolean(
						ConfigurationKeys.KEY_INTERCAMBIO_ENABLE_INTERCAMBIO_REGISTRAL);


		if(idUsuario!=null)
		{
			if(PermisosInternosUtils.isAdminInterno(session, idUsuario))
			{
				request.getSession().setAttribute("isSuperuser", true);
				isSuperUser=true;
				genPerms = PermisosInternosUtils.setAdminPermisions(genPerms);
			}
			else{
				genPerms = PermisosInternosUtils.getGenPerms(session, idUsuario);
			}

			if(!PermisosInternosUtils.tienePermisosAdministrador(genPerms))
			{
				//Limpiamos sesion para que no haga "bucle" al volver a inicio
		    	String key_entidad = request.getParameter(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD);
		    	if(StringUtils.isEmpty(key_entidad))
		    	{
		    		key_entidad = (String) request.getSession().getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD);
		    	}
				ServicioAdministracionSesionesAdministrador oServicio;
				oServicio = LocalizadorServicios.getServicioAdministracionSesionesAdministrador();
				oServicio.caducarSesion(sesion.getIdSesion());
				oServicio.caducarSesionEntidad(key_entidad);
				ActionErrors errores = new ActionErrors();
				ActionError error = new ActionError("errors.detail", "El usuario no tiene permisos");
				errores.add("Error interno", error);
				saveErrors(request, errores);
				return mapping.findForward("error");

			}
		}
		else
		{
			request.getSession().setAttribute("isSuperuser", true);
			isSuperUser=true;
			genPerms = PermisosInternosUtils.setAdminPermisions(genPerms);

		}

		request.getSession()
				.setAttribute(
						ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM,
						sesion.getUsuario());

		request.getSession().setAttribute("enabledIntercambioRegistral",enabledIntercambioRegistral);

		request.getSession().setAttribute("isLdapAuthenticationPolicy", isLdapAuthenticationPolicy);
		request.getSession().setAttribute(ServerKeys.GENPERMS_USER, genPerms);
		return mapping.findForward(PermisosInternosUtils.calculateSuccess(isSuperUser, genPerms));
	}



}
