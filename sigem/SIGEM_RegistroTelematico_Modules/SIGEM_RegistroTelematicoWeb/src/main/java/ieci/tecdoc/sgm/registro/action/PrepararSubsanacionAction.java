package ieci.tecdoc.sgm.registro.action;

import ieci.tecdoc.sgm.autenticacion.AutenticacionManager;
import ieci.tecdoc.sgm.autenticacion.util.SesionInfo;
import ieci.tecdoc.sgm.autenticacion.util.Solicitante;
import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;
import ieci.tecdoc.sgm.base.xml.core.XmlDocument;
import ieci.tecdoc.sgm.base.xml.core.XmlElement;
import ieci.tecdoc.sgm.base.xml.core.XmlTransformer;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;
import ieci.tecdoc.sgm.core.services.repositorio.ServicioRepositorioDocumentosTramitacion;
import ieci.tecdoc.sgm.core.services.telematico.Registro;
import ieci.tecdoc.sgm.core.services.telematico.RegistroTelematicoException;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;
import ieci.tecdoc.sgm.core.services.tramitacion.ServicioTramitacion;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.Expediente;
import ieci.tecdoc.sgm.core.web.locale.LocaleFilterHelper;
import ieci.tecdoc.sgm.registro.exception.RegistroCodigosError;
import ieci.tecdoc.sgm.registro.util.Definiciones;
import ieci.tecdoc.sgm.registro.utils.Defs;
import ieci.tecdoc.sgm.registro.utils.Misc;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PrepararSubsanacionAction extends PrepararSolicitudRegistroAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) {

    	HttpSession session = request.getSession();

	    try {
			String sessionId = request.getParameter(Defs.SESION_ID);
		   	if (sessionId == null || sessionId.equals("")) {

		   		sessionId = (String)session.getAttribute(Defs.SESION_ID);
		   	}
		   	session.setAttribute(Defs.SESION_ID, sessionId);

	    	String tramiteId = request.getParameter(Defs.TRAMITE_ID);
		   	if (tramiteId == null || tramiteId.equals("")) {

		   		tramiteId = (String)session.getAttribute(Defs.TRAMITE_ID);
		   		if (tramiteId == null || tramiteId.equals("")) {

		   			request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_PREPARAR_SOLICITUD);
		   			request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, "Error: Trámite no especificado");

		   			return mapping.findForward("failure");
		   		}
		   	}
		   	session.setAttribute(Defs.TRAMITE_ID, tramiteId);

		   	String numExpediente = request.getParameter(Defs.NUMERO_EXPEDIENTE);
		   	if (numExpediente == null || numExpediente.equals("")) {

		   		numExpediente = (String)session.getAttribute(Defs.NUMERO_EXPEDIENTE);
		   		if (numExpediente == null || numExpediente.equals("")) {

		   			request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_PREPARAR_SOLICITUD);
		   			request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, "Error: Número de expediente no especificado");

		   			return mapping.findForward("failure");
		   		}
		   	}
		   	session.setAttribute(Defs.NUMERO_EXPEDIENTE, numExpediente);

		   	String numRegistro = request.getParameter(Defs.NUMERO_REGISTRO);
		   	if (numRegistro == null || numRegistro.equals("")) {

		   		numRegistro = (String)session.getAttribute(Defs.NUMERO_REGISTRO);

		   		/*
		   		if (numRegistro == null || numRegistro.equals("")) {

		   			request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_PREPARAR_SOLICITUD);
		   			request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, "Error: Número de registro no especificado");

		   			return mapping.findForward("failure");
		   		}
		   		*/
		   	}
		   	session.setAttribute(Defs.NUMERO_REGISTRO, numRegistro);

		   	String idDocumento = request.getParameter("idDocumento");
		   	if (idDocumento == null || idDocumento.equals("")) {

		   		idDocumento = (String)session.getAttribute("idDocumento");

		   		/*
		   		if (idDocumento == null || idDocumento.equals("")){
		   			request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_PREPARAR_SOLICITUD);
		   			request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, "Error: Documento no especificado");
		   			return mapping.findForward("failure");
		   		}
		   		*/
		   	}
		   	session.setAttribute("idDocumento", idDocumento);

		   	Entidad entidad = Misc.obtenerEntidad(request);
	    	Locale idioma = LocaleFilterHelper.getCurrentLocale(request);
		   	if (idioma == null || idioma.getLanguage() == null) {
		   		idioma = request.getLocale();
		   	}

		   	// Obtener la ruta de la xsl del formulario vacío
		   	String xslPath = getResourceTramitePath(session, entidad.getIdentificador(), tramiteId, idioma, "formulario_vacio_", "xsl");
		   	if (xslPath == null) {

		   		request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_PREPARAR_SOLICITUD);
		    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, "Error: No se ha encontrado el formulario para este trámite");
		   		return mapping.findForward("failure");
		   	}

		   	ServicioRepositorioDocumentosTramitacion oServicioRepositorioDocumentosTramitacion = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
		   	ServicioEntidades oServicioEntidades = LocalizadorServicios.getServicioEntidades();

		   	oServicioRepositorioDocumentosTramitacion.deleteDocumentTmp(sessionId, entidad);

		   	//String organismo = (String)session.getServletContext().getAttribute(Defs.PLUGIN_ORGANISMO);
		   	String organismo = oServicioEntidades.obtenerEntidad(entidad.getIdentificador()).getNombreLargo();

		   	SesionInfo sessionInfo = AutenticacionManager.getLogin(sessionId, tramiteId, Misc.obtenerEntidad(request).getIdentificador());

		   	String xmlData = prepararSubsanacionXML(request, numExpediente, numRegistro, organismo, entidad, sessionInfo.getSender());
		   	session.setAttribute(Defs.XML_DATA, xmlData);

		   	XmlDocument xmlDoc = new XmlDocument();
		   	xmlDoc.createFromStringText(xmlData);

		   	// Combinar la solicitud con el XSL de presentación
		   	String xml_w_xsl = XmlTransformer.transformXmlDocumentToHtmlStringTextUsingXslFile(xmlDoc, xslPath);
		   	session.setAttribute(Defs.FORMULARIO, xml_w_xsl);
	    }
	    catch(Exception e) {

	    	request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_PREPARAR_SOLICITUD);
	    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());

	    	return mapping.findForward("failure");
   		}

	    return mapping.findForward("success");
   	}

    public String prepararSubsanacionXML(HttpServletRequest request,
    									 String numExpediente,
    									 String numRegistro,
    									 String organismo,
    									 Entidad entidad,
    									 Solicitante solicitante) {

    	XmlTextBuilder bdr;
    	Registro registro = null;
    	Expediente expediente = null;
    	byte[] solicitud = null;

    	try {

    		if (StringUtils.isNotBlank(numRegistro)) {

    			// Obtener el número de registro telemático que generó el expediente
    			ServicioRegistroTelematico oServicioRegistroTelematico = LocalizadorServicios.getServicioRegistroTelematico();

    			try {
    				registro = oServicioRegistroTelematico.obtenerRegistro("", numRegistro, entidad);

    		    	// Solicitud del registro
    				// Obtener la solicitud no firmada ya que siempre existe para todos los trámites
    				// y la solicitud firmada sólo existe en los trámites que requieren firma
    		    	// byte[] solicitud = oServicioRegistroTelematico.obtenerPeticionRegistro(numRegistro, entidad);
    				solicitud = oServicioRegistroTelematico.obtenerDocumento(numRegistro, Definiciones.REGISTRY_REQUEST_NOTSIGNED_CODE, entidad);

    			} catch (RegistroTelematicoException rte) {

    				StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_EREGISTRY_ERROR_PREFIX);
    				cCodigo.append(String.valueOf(RegistroCodigosError.EC_BAD_REGISTRY_NUMBER));

    				// Comprobar si el error es porque el número de registro telemático no existe
    				if (rte.getErrorCode() == Long.valueOf(cCodigo.toString()).longValue()) {

    					// Obtener el expediente en tramitación
    					ServicioTramitacion oServicioTramitacion = LocalizadorServicios.getServicioTramitacion();

    					expediente = oServicioTramitacion.getExpediente(entidad.getIdentificador(), numExpediente);
    				} else {
    					throw rte;
    				}
    			}
    		}

	        bdr = new XmlTextBuilder();
	        bdr.setStandardHeader();

	        bdr.addOpeningTag(Definiciones.REQUEST_HEADER);
	        bdr.addSimpleElement(Definiciones.VERSION, Definiciones.VERSION_NUMBER);

	        String numRegistroInicial = "";
	        String fechaRegistroInicial = "";
	        String horaRegistroInicial = "";

	        if (registro != null) {

	        	// Obtener el número de registro telemático que generó el expediente
	        	numRegistroInicial = registro.getRegistryNumber();
	        	fechaRegistroInicial = DateTimeUtil.getDateTime(registro.getRegistryDate(), "dd-MM-yyyy");
	        	horaRegistroInicial = DateTimeUtil.getDateTime(registro.getRegistryDate(), "HH:mm:ss");
	        }

	        bdr.addOpeningTag(Definiciones.REGISTRY_DATA);
	        bdr.addSimpleElement(Definiciones.REGISTRY_NUMBER, numRegistroInicial);
	        bdr.addSimpleElement(Definiciones.REGISTRY_DATE, fechaRegistroInicial);
	        bdr.addSimpleElement(Definiciones.REGISTRY_HOUR, horaRegistroInicial);
	        bdr.addSimpleElement(Definiciones.REGISTRY_EFFECTIVE_DATE, "");
	        bdr.addSimpleElement(Definiciones.REGISTRY_EFFECTIVE_HOUR, "");
	        bdr.addClosingTag(Definiciones.REGISTRY_DATA);


	        bdr.addOpeningTag(Definiciones.SIGNED_DATA);
	        bdr.addOpeningTag(Definiciones.GENERIC_DATA);
	        bdr.addSimpleElement(Definiciones.ORG, organismo, true);

	        Locale idioma = LocaleFilterHelper.getCurrentLocale(request);
		   	if (idioma == null || idioma.getLanguage() == null) {
		   		idioma = request.getLocale();
		   	}

	        bdr.addSimpleElement(Definiciones.IDIOM, idioma.getLanguage());

	        // Incluir en el XML de la solicitud de subsanación
	        // la información de los datos del solicitante
	        Misc.addInfoSolicitante(bdr, solicitante);

        	String description = "Subsanación";

	        if (solicitud != null) {

	        	XmlDocument xmlDoc = new XmlDocument();
	        	xmlDoc.createFromStringText(Goodies.fromUTF8ToStr(solicitud));
	        	XmlElement rootElement =  xmlDoc.getRootElement();

	        	XmlElement descElement = rootElement.getDescendantElement(Definiciones.XPATH_GENERIC_DATA + "/" +
	        															  Definiciones.TOPIC + "/" +
	        															  Definiciones.DESCRIPTION);
	        	description += " " + descElement.getValue();

	        } else if (expediente != null) {

	        	description += " " + expediente.getAsunto();
	        }

	        bdr.addOpeningTag(Definiciones.TOPIC);
	        bdr.addSimpleElement(Definiciones.CODE, "");
	        bdr.addSimpleElement(Definiciones.DESCRIPTION, description, true);
	        bdr.addClosingTag(Definiciones.TOPIC);

	        // Destino
	        String addressee = "";
	        if (registro != null) {
	        	addressee = registro.getAddressee();
	        }
	        bdr.addOpeningTag(Definiciones.ADDRESSEE);
	        bdr.addSimpleElement(Definiciones.CODE, addressee);
	        bdr.addClosingTag(Definiciones.ADDRESSEE);

	        bdr.addSimpleElement(Definiciones.FOLDER_ID, numExpediente);
	        bdr.addClosingTag(Definiciones.GENERIC_DATA);
	        bdr.addSimpleElement(Definiciones.SPECIFIC_DATA, "");
	        bdr.addClosingTag(Definiciones.SIGNED_DATA);
	        bdr.addClosingTag(Definiciones.REQUEST_HEADER);

    	} catch (Exception e) {

	        bdr = new XmlTextBuilder();
	        bdr.setStandardHeader();
	        bdr.addOpeningTag(Definiciones.REQUEST_HEADER);
	        bdr.addClosingTag(Definiciones.REQUEST_HEADER);
    	}

    	return bdr.getText();
    }
}