package ieci.tecdoc.isicres.rpadmin.struts.acciones;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;

import ieci.tecdoc.isicres.rpadmin.struts.util.MvcDefs;
import ieci.tecdoc.isicres.rpadmin.struts.util.SesionHelper;
import ieci.tecdoc.sbo.idoc.login.LoginMethod;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class RPAdminWebAction extends Action {
	private static final Logger logger = Logger.getLogger(RPAdminWebAction.class);

	public static final String KEY_LISTA_OFICINAS_ASUNTOS = "listaOficinasAsuntos";
	public static final String KEY_LISTA_DOCUMENTOS_ASUNTOS = "listaDocumentosAsuntos";
	public static final String KEY_LISTA_OFICINAS_ELIMINADAS_ASUNTOS = "listaOficinasElimniadasAsuntos";
	public static final String KEY_LISTA_DOCUMENTOS_ELIMINADOS_ASUNTOS = "listaDocumentosEliminadosAsuntos";
	public static final String KEY_LISTA_OFICINAS_INFORMES = "listaOficinasInformes";
	public static final String KEY_LISTA_OFICINAS_ELIMINADAS_INFORMES = "listaOficinasElimniadasInformes";

	public void setInSession(HttpServletRequest request, String name, Object value){
		request.getSession(false).setAttribute(name, value);
	}

	public List getListaFromSession(HttpServletRequest request, String name){
		List lista = (List) request.getSession(false).getAttribute(name);
		if(lista == null) lista = new ArrayList();
		return lista;
	}


	protected ServletContext ctx      =  null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

	      if(ctx == null)
	      {
	      	ctx  =  request.getSession(false).getServletContext();
	      }

		ActionForward forward = null;
		try{

			logger.info("PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD Del request parameter: " +  request.getParameter(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD));
			logger.info("PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD Del request session: " + request.getSession().getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM_ENTIDAD));

			if(!SesionHelper.authenticate(request)){
				response.sendRedirect(SesionHelper.getWebAuthURL(request));
				return null;
			}

			forward = executeAction(mapping, form, request, response);

		}catch(Exception e){
			logger.error("Error en la aplicacion", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			errores.add("Error interno", error);
			saveErrors(request, errores);
			forward = mapping.findForward("error");
		}

		return forward;
	}

	/**
	 * Permite saber si la entidad pasada como parámetro es de tipo ldap
	 * @param entidad Id de entidad
	 * @return si la entidad pasada como parámetro es de tipo ldap
	 */
	protected boolean isLdapMethod(String entidad) {
		boolean isLdap = false;

		HashMap hash = (HashMap) ctx.getAttribute(MvcDefs.TOKEN_LOGIN_METHOD);

		if (hash != null) {
			Integer loginMethod = (Integer) hash.get(entidad);
			if (loginMethod != null
					&& (loginMethod.intValue() == LoginMethod.LDAP || loginMethod
							.intValue() == LoginMethod.SSO_LDAP))
				isLdap = true;
		}
		return isLdap;
	}

	/**
	 * Método específico que se ejecuta en cada acción.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public abstract ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
