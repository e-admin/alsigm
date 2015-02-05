package ieci.tecdoc.sgm.registro.action;

import ieci.tecdoc.sgm.autenticacion.AutenticacionManager;
import ieci.tecdoc.sgm.autenticacion.util.SesionInfo;
import ieci.tecdoc.sgm.autenticacion.util.Solicitante;
import ieci.tecdoc.sgm.autenticacion.util.TipoAutenticacionCodigos;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catalogo.Conector;
import ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.catalogo.TipoConector;
import ieci.tecdoc.sgm.core.services.catalogo.Tramite;
import ieci.tecdoc.sgm.registro.form.SolicitudesRegistroForm;
import ieci.tecdoc.sgm.registro.util.Definiciones;
import ieci.tecdoc.sgm.registro.utils.Defs;
import ieci.tecdoc.sgm.registro.utils.Misc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RealizarSolicitudRegistroAction extends RegistroWebAction{

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		//ActionErrors errors = null;
		SolicitudesRegistroForm solicitudBean = (SolicitudesRegistroForm)form;
		String tramiteId = null;
		if (solicitudBean != null)
			tramiteId = solicitudBean.getSelTramiteId();
		if (tramiteId == null || tramiteId.equals(""))
			tramiteId = request.getParameter(Defs.TRAMITE_ID);

		// este numero de expediente va al XML de la solicitud
		String numExp = request.getParameter(Defs.NUMERO_EXPEDIENTE);
		if (numExp == null)
			numExp = new String("");

		String sessionIdIni = null;
		SesionInfo sessionInfo = null;
		String sessionId = null;
		boolean certificado = true;
		HttpSession session = request.getSession();
		session.removeAttribute(Defs.DATOS_ESPECIFICOS);

		try{
			ServicioCatalogoTramites oServicioCatalogo = LocalizadorServicios.getServicioCatalogoTramites();
			Tramite tram = oServicioCatalogo.getProcedure(tramiteId, false, Misc.obtenerEntidad(request));
			if (tram == null || tram.getId().equals("")){
				request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_REALIZAR_SOLICITUD);
		    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, Defs.MENSAJE_ERROR_NO_EXISTE);
		   		return mapping.findForward("failure");
			}

			session.setAttribute(Defs.TRAMITE_ID, tramiteId);
			OrganoDestinatario org = oServicioCatalogo.getAddressee(tram.getAddressee(), Misc.obtenerEntidad(request));
			session.setAttribute(Defs.ID_ORGANO, org.getId());
			session.setAttribute(Defs.DESC_ORGANO, org.getDescription());
			session.setAttribute(Defs.NUMERO_EXPEDIENTE, numExp);
			sessionIdIni = (String)request.getParameter(Defs.SESION_ID);
			if(sessionIdIni == null || sessionIdIni.equals("") || sessionIdIni.equals("null")){
				sessionIdIni = (String)session.getAttribute(Defs.SESION_ID);
				if(sessionIdIni == null || sessionIdIni.equals("") || sessionIdIni.equals("null")){
					return mapping.findForward("error_login");
				}
			}
			sessionInfo = AutenticacionManager.getLogin(sessionIdIni, tramiteId, Misc.obtenerEntidad(request).getIdentificador());
			if (sessionInfo != null)
				sessionId = sessionInfo.getSessionId();
			if (sessionId == null || sessionId.equals(""))
				return mapping.findForward("error_login");

			/*############### FALTA COMPROBAR SI EL CONECTOR ES VÁLIDO ########*/
			session.setAttribute(Defs.SESION_ID, sessionId);
			session.setAttribute(Defs.SENDER_NIF, sessionInfo.getSender().getId());
			String xml = prepararSolicitud(sessionInfo.getSender());
			session.setAttribute(Defs.XML_DATA, xml);

			//conector seleccionado por el usuario
			Conector hook = oServicioCatalogo.getHook(sessionInfo.getHookId(), Misc.obtenerEntidad(request));
			if (hook.getType() == TipoConector.WEB_USER_AUTH || hook.getType() == TipoConector.CERTIFICATE_WEB_AUTH){
				certificado = false;
			}
	   	}catch(Exception e){
	   		request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_REALIZAR_SOLICITUD);
	    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());
	   		return mapping.findForward("failure");
	   	}

	   	/*String puertoServer = ""+request.getServerPort();
	   	if (certificado){
	   		session.setAttribute(Defs.ACCESO_SEL, ""+TipoAutenticacionCodigos.X509_CERTIFICATE);
	   		String puertoSeguro = (String)request.getSession().getServletContext().getAttribute(Defs.PLUGIN_HTTPS_PORT);
	   		if (puertoServer.equals(puertoSeguro))
	   			return mapping.findForward("success");
	   		else return mapping.findForward("success_change_port");
	   	}else{
	   		session.setAttribute(Defs.ACCESO_SEL, ""+TipoAutenticacionCodigos.WEB_USER);
	   		String puertoSeguro = (String)request.getSession().getServletContext().getAttribute(Defs.PLUGIN_HTTPS_USER_PORT);
	   		if (puertoServer.equals(puertoSeguro))
	   			return mapping.findForward("success_web");
	   		else return mapping.findForward("success_web_change_port");
	   	}*/

	   	// TODO UNIR RedirecionUsuario.jsp y RedirecionCertificados.jsp usando
		// como discriminate el atributo pasado por session!!!!
	   	if (certificado){
	   		session.setAttribute(Defs.ACCESO_SEL, ""+TipoAutenticacionCodigos.X509_CERTIFICATE);
	   		return mapping.findForward("success_change_port");
	   	}else{
	   		session.setAttribute(Defs.ACCESO_SEL, ""+TipoAutenticacionCodigos.WEB_USER);
	   		return mapping.findForward("success_web_change_port");
	   	}
   		//return mapping.findForward("success_web_change_port");
	}

	private String prepararSolicitud(Solicitante solicitante){

		XmlTextBuilder bdr = new XmlTextBuilder();

        bdr.setStandardHeader();
        bdr.addOpeningTag(Definiciones.REGISTRY_DATA);

        // Incluir en el XML de la solicitud
        // la información de los datos del solicitante
        Misc.addInfoSolicitante(bdr, solicitante);

        bdr.addClosingTag(Definiciones.REGISTRY_DATA);

        return bdr.getText();
	}
}
