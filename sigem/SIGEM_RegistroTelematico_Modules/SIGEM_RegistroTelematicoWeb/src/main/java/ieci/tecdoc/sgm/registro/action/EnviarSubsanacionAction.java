package ieci.tecdoc.sgm.registro.action;

import ieci.tecdoc.sgm.base.base64.Base64Util;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;
import ieci.tecdoc.sgm.base.xml.core.XmlDocument;
import ieci.tecdoc.sgm.base.xml.core.XmlElement;
import ieci.tecdoc.sgm.base.xml.core.XmlTransformer;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catalogo.DocumentoExtendido;
import ieci.tecdoc.sgm.core.services.catalogo.Documentos;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.catalogo.Tramite;
import ieci.tecdoc.sgm.core.services.catalogo.TramiteConsulta;
import ieci.tecdoc.sgm.core.services.catalogo.Tramites;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.telematico.PeticionDocumento;
import ieci.tecdoc.sgm.core.services.telematico.PeticionDocumentos;
import ieci.tecdoc.sgm.core.services.telematico.Registro;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;
import ieci.tecdoc.sgm.core.web.locale.LocaleFilterHelper;
import ieci.tecdoc.sgm.registro.form.FormularioSubsanacionForm;
import ieci.tecdoc.sgm.registro.util.Definiciones;
import ieci.tecdoc.sgm.registro.utils.Defs;
import ieci.tecdoc.sgm.registro.utils.Misc;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.util.MessageResources;

public class EnviarSubsanacionAction extends EnviarSolicitudAction {

	private static final Logger logger = Logger.getLogger(EnviarSubsanacionAction.class);

	private final static String DESCRIPCION 	= "Fichero Anexo Subsanacion #";

	private final static String TAG_SPECIFIC 	= "<"+Definiciones.SPECIFIC_DATA+" />";
	private final static String TAG_SIGNED 		= "</"+Definiciones.SIGNED_DATA+">";

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) {

    	HttpSession session = request.getSession();

	    try {
	    	Boolean maxLengthExceeded = (Boolean)request.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);
	    	if(maxLengthExceeded!=null && maxLengthExceeded.booleanValue()) {

		    	MessageResources resources = getResources(request);

	    		request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_ENVIO_SOLICITUD);
		    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, resources.getMessage(Defs.MENSAJE_ERROR_DOCUMENT_MAX_LENGTH));

		   		return mapping.findForward("failure");
	    	}

	    	String separador = System.getProperty("file.separator");
	    	String sessionId = (String)session.getAttribute(Defs.SESION_ID);
	    	String tramiteId = (String)session.getAttribute(Defs.TRAMITE_ID);

	    	Entidad entidad = Misc.obtenerEntidad(request);
	    	Locale idioma = LocaleFilterHelper.getCurrentLocale(request);
		   	if (idioma == null || idioma.getLanguage() == null) {
		   		idioma = request.getLocale();
		   	}

		   	// Obtener la ruta de la xsl del formulario relleno
		   	String xslPath = getResourceTramitePath(session, entidad.getIdentificador(), tramiteId, idioma, "formulario_relleno_", "xsl");
		   	if (xslPath == null) {

		   		request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_ENVIO_SOLICITUD);
		    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, "Error: No se ha encontrado el formulario para este trámite");
		   		return mapping.findForward("failure");
		   	}

	    	String refresco = (String)request.getParameter(Defs.REFRESCO);
	    	boolean bRefresco = true;
	    	if (refresco !=null && !"".equals(refresco))
	    		bRefresco = new Boolean(refresco).booleanValue();

	    	if (bRefresco) {

	        	FormularioSubsanacionForm formulario = (FormularioSubsanacionForm)form;

		        ServicioCatalogoTramites oServicioCatalogoTramites = LocalizadorServicios.getServicioCatalogoTramites();
	        	ServicioRegistroTelematico oServicioRegistroTelematico = LocalizadorServicios.getServicioRegistroTelematico();

		    	// Obtener los documentos anexados a la subsanacion
		    	PeticionDocumentos peticionDocumentos = processDocuments(request, session, formulario, null, sessionId, separador);
		    	session.setAttribute(Defs.DOCUMENTOS_REQUEST, peticionDocumentos);

		    	String numRegistro = (String)session.getAttribute(Defs.NUMERO_REGISTRO);
		    	if (StringUtils.isNotBlank(numRegistro)) {

		    		// Registro telemático que generó el expediente
			    	Registro registro = oServicioRegistroTelematico.obtenerRegistro("", numRegistro, entidad);

			    	// Oficina de registro la misma que la del registro de la solicitud
			    	session.setAttribute(Defs.OFICINA, registro.getOficina());

			    	// Obtener el trámite del registro de la solicitud
			        TramiteConsulta tramiteConsulta = new TramiteConsulta();
			        tramiteConsulta.setTopic(registro.getTopic());
			        Tramites tramites = oServicioCatalogoTramites.query(tramiteConsulta, entidad);
			        Tramite tramite = tramites.get(0);

			        // La subsanación se firma o no en función de la configuración
			        // establecida para el trámite en la opción de Firmar solicitud
			    	boolean firma = tramite.getFirma();
			    	if (firma) {
			    		session.setAttribute(Defs.FIRMAR_SOLICITUD, "1");
			    	} else {
			    		session.setAttribute(Defs.FIRMAR_SOLICITUD, "0");
			    	}
		    	} else {
			    	// Cuando no existe el registro inicial
		    		// obtener la oficina de registro y si hay que firmar de la configuración

		    		// Código de oficina de registro
		    		String codigoOficina = (String) session.getServletContext().getAttribute(Defs.PLUGIN_SUBSANACIONSINNUMEROREGISTROINICIAL_CODIGO_OFICINA);
		    		if (StringUtils.isNotBlank(codigoOficina)) {
		    			session.setAttribute(Defs.OFICINA, codigoOficina);
		    		} else {
		    			logger.error("En la configuracion del Registro Telematico no se ha establecido una oficina de registro para las subsanaciones sin registro inicial");

		    			MessageResources resources = getResources(request);
			   			request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_ENVIO_SOLICITUD);
			   			request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, resources.getMessage(Defs.MENSAJE_ERROR_SUBSANACION_SIN_OFICINA_REGISTRO));
			   			return mapping.findForward("failure");
		    		}

		    		try {
		    			boolean firmarSolicitud = new Boolean((String) session.getServletContext().getAttribute(Defs.PLUGIN_SUBSANACIONSINNUMEROREGISTROINICIAL_FIRMAR_SOLICITUD)).booleanValue();
				    	if (firmarSolicitud) {
				    		session.setAttribute(Defs.FIRMAR_SOLICITUD, "1");
				    	} else {
				    		session.setAttribute(Defs.FIRMAR_SOLICITUD, "0");
				    	}
		    		} catch (Exception e) {
		    			// Si no se ha definido en la configuración
		    			// por defecto firmar la solicitud y no generar error
		    			session.setAttribute(Defs.FIRMAR_SOLICITUD, "1");
		    		}
		    	}

		    	String strDocs = oServicioRegistroTelematico.establecerDocumentosSubsanacion(sessionId, getDocumentos(peticionDocumentos), peticionDocumentos, entidad);

		    	String xmlData = (String)session.getAttribute(Defs.XML_DATA);
		    	int index = xmlData.indexOf(TAG_SPECIFIC);
		    	String xmlDocs = xmlData.substring(0, index + TAG_SPECIFIC.length());
		    	xmlDocs += strDocs;
		    	int indexa = xmlData.indexOf(TAG_SIGNED);
		    	xmlDocs += xmlData.substring(index+TAG_SPECIFIC.length(), indexa+TAG_SIGNED.length()) +
		    			   "<"+Definiciones.SIGNATURE+"></"+Definiciones.SIGNATURE+">" +
		    			   xmlData.substring(indexa+TAG_SIGNED.length(), xmlData.length());

			   	XmlDocument xmlDoc = new XmlDocument();
			   	xmlDoc.createFromStringText(xmlDocs);

			   	// Establecer si los documentos anexados a la solicitud tienen virus o no
			   	processVirus(session, xmlDocs, xmlDoc);

			   	// Eliminar de la solicitud la información del registro original
			   	XmlElement rootElement = xmlDoc.getRootElement();
			   	XmlElement registryDataElement = rootElement.getChildElement(Definiciones.REGISTRY_DATA);
			   	if (registryDataElement != null) {

				   	registryDataElement.getChildElement(Definiciones.REGISTRY_NUMBER).setValue("");
				   	registryDataElement.getChildElement(Definiciones.REGISTRY_DATE).setValue("");
				   	registryDataElement.getChildElement(Definiciones.REGISTRY_HOUR).setValue("");
			   	}

			   	String strSolicitud = xmlDoc.getStringText(false);

			   	// Combinar la solicitud con el XSL de presentación
			   	String xml_w_xsl = XmlTransformer.transformXmlDocumentToHtmlStringTextUsingXslFile(xmlDoc, xslPath);
			   	session.setAttribute(Defs.SOLICITUD_REGISTRO, xml_w_xsl);
			   	session.setAttribute(Defs.REQUEST, Base64Util.encode(Goodies.fromStrToUTF8(strSolicitud)));

			   	// Realizar la misma operación de obtener el texto de la solicitud de registro que
			   	// al registrar cuando se incrusta la firma en la solicitud de registro para que
			   	// se genere la misma cadena de texto y la firma se pueda validar correctamente
			   	strSolicitud = new String(xmlDoc.getStringText(false));

			   	// Generar el fichero temporal que en el paso siguiente permite ver la solicitud
			   	generateXmlRequestTmpFile(request, session, separador, tramiteId, sessionId, strSolicitud);

			   	int index1 = strSolicitud.indexOf("<"+Definiciones.SIGNED_DATA+">");
			   	int index2 = strSolicitud.indexOf("</"+Definiciones.SIGNED_DATA+">");
			   	String strSolicitudFirma = strSolicitud.substring(index1 + ("<"+Definiciones.SIGNED_DATA+">").length(), index2);
			   	session.setAttribute(Defs.DATOS_A_FIRMAR, formatear(Base64Util.encode(Goodies.fromStrToUTF8(strSolicitudFirma))));
	    	}
	    	else {
			   	byte[] solicitud = Base64Util.decode((String)session.getAttribute(Defs.REQUEST));

			   	XmlDocument xmlDoc = new XmlDocument();
			   	xmlDoc.createFromStringText(Goodies.fromUTF8ToStr(solicitud));

			   	String xml_w_xsl = XmlTransformer.transformXmlDocumentToHtmlStringTextUsingXslFile(xmlDoc, xslPath);
			   	session.setAttribute(Defs.SOLICITUD_REGISTRO, xml_w_xsl);
	    	}
	    }
	    catch (Exception e) {

	    	request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_ENVIO_SOLICITUD);
	    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());

	    	return mapping.findForward("failure");
   		}

	    return mapping.findForward("success");
   	}

	private Documentos getDocumentos(PeticionDocumentos pDocs) {

		Documentos docs = new Documentos();

		for(int i=0; i<pDocs.count(); i++) {

			DocumentoExtendido doc = new DocumentoExtendido();
			PeticionDocumento pDoc = pDocs.get(i);

			doc.setCode(pDoc.getCode());
			doc.setDescription(DESCRIPCION + (i+1));
			doc.setExtension(pDoc.getExtension());
			doc.setId(pDoc.getExtension());
			doc.setMandatory(false);
			doc.setSignatureHook("");
			doc.setValidationHook("");
			docs.add(doc);
		}

		return docs;
	}

}