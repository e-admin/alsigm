package ieci.tecdoc.sgm.registro.action;

import ieci.tecdoc.sgm.base.xml.core.XmlDocument;
import ieci.tecdoc.sgm.base.xml.core.XmlElement;
import ieci.tecdoc.sgm.base.xml.core.XmlTransformer;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.web.locale.LocaleFilterHelper;
import ieci.tecdoc.sgm.registro.form.FormularioSolicitudForm;
import ieci.tecdoc.sgm.registro.terceros.connector.ServicioRegistroTelematicoTercerosConnector;
import ieci.tecdoc.sgm.registro.terceros.connector.config.ConnectorConfiguration;
import ieci.tecdoc.sgm.registro.terceros.connector.factory.ServicioRegistroTelematicoTercerosConnectorFactory;
import ieci.tecdoc.sgm.registro.terceros.connector.vo.DireccionTerceroVO;
import ieci.tecdoc.sgm.registro.terceros.connector.vo.TerceroVO;
import ieci.tecdoc.sgm.registro.utils.Defs;
import ieci.tecdoc.sgm.registro.utils.Misc;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PrepararSolicitudRegistroAction extends RegistroWebAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) {

    	HttpSession session = request.getSession();

	    try {
	    	String tramiteId = (String)session.getAttribute(Defs.TRAMITE_ID);
		   	if (tramiteId == null || tramiteId.equals("")) {

		   		request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_PREPARAR_SOLICITUD);
		    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, "Error: Trámite no especificado");

		   		return mapping.findForward("failure");
		   	}

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

		   	String xmlData = (String)session.getAttribute(Defs.XML_DATA);
		   	String xmlDataSpecific = (String)session.getAttribute(Defs.DATOS_ESPECIFICOS);
		   	String xmlTercero = null;
		   	ServicioRegistroTelematicoTercerosConnector servicioTerceros = ServicioRegistroTelematicoTercerosConnectorFactory.getServicioRegistroTelematicoTercerosConnector(new ConnectorConfiguration(entidad.getIdentificador()));

		   	if(servicioTerceros!=null)
		   	{
		   		String identificadorSolicitante = (String)session.getAttribute(Defs.SENDER_NIF);
		   		TerceroVO tercero = servicioTerceros.buscarTerceroPorEntidad(entidad.getIdentificador(),identificadorSolicitante);
		   		if(tercero!=null)
		   		{
		   			xmlTercero = createXmlInfoTercero(tercero);
		   			xmlData=putXMLDataInfoTercero(xmlData, xmlTercero);
		   		}
		   	}

		   	// Se añade al xml de registro los datos específicos de la solicitud,
		   	// para que el usuario pueda corregir el formulario
		   	if( xmlDataSpecific != null ) {
		   		xmlData = putXMLDataSpecif(xmlData, form, xmlDataSpecific);
		   	} else {
		   		// Se añade el xml de documentos a anexar en caso de que el
		   		// formulario lo requiera
		   		xmlData = putXMLDataDocuments(xmlData, form);
		   	}


		   	XmlDocument xmlDoc = new XmlDocument();
		   	xmlDoc.createFromStringText(xmlData);

		   	// Combinar la solicitud con el XSL de presentación
		   	String xml_w_xsl = XmlTransformer.transformXmlDocumentToHtmlStringTextUsingXslFile(xmlDoc, xslPath);
		   	session.setAttribute(Defs.FORMULARIO, xml_w_xsl);

		   	String fecha = (new java.text.SimpleDateFormat("dd-MM-yyyy")).format(new Date());
		   	request.setAttribute(Defs.FECHA, fecha);
	    }
	    catch(Exception e) {

	    	request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_PREPARAR_SOLICITUD);
	    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());

	    	return mapping.findForward("failure");
   		}

	    return mapping.findForward("success");
   	}

    private String putXMLDataInfoTercero(String xmlData, String xmlTercero) {
    	xmlData = xmlData.replaceAll("</" + Defs.TAG_DATOS_REGISTRO + ">", xmlTercero + "</" + Defs.TAG_DATOS_REGISTRO + ">");
    	return xmlData;
	}

	private String createXmlInfoTercero(TerceroVO tercero) {
		XmlTextBuilder bdr;

		bdr = new XmlTextBuilder();

        bdr.addOpeningTag("Datos_Tercero");
        bdr.addSimpleElement("ID_Tercero", tercero.getIdentificador(), true);
        bdr.addSimpleElement("Identificador", tercero.getIdentificador(), true);
        bdr.addSimpleElement("Nombre", tercero.getNombre(), true);
        bdr.addSimpleElement("Primer_Apellido", tercero.getPrimerApellido(), true);
        bdr.addSimpleElement("Segundo_Apellido", tercero.getSegundoApellido()
        		, true);

        if(tercero.getDirecciones()!=null && tercero.getDirecciones().size()>0)
        {
        	bdr.addOpeningTag("Direcciones");
        	DireccionTerceroVO direccion = null;
	        for (int i=0; i<tercero.getDirecciones().size();i++)
	        {
	        	direccion = (DireccionTerceroVO) tercero.getDirecciones().get(i);
				bdr.addOpeningTag("Direccion");
				bdr.addSimpleElement("ID_Direccion", direccion.getIdDireccion());
				bdr.addSimpleElement("Tipo_Via", direccion.getTipoVia(),true);
				bdr.addSimpleElement("Via", direccion.getVia(),true);
				bdr.addSimpleElement("Portal", direccion.getPortal(), true);
				bdr.addSimpleElement("Piso", direccion.getPiso(), true);
				bdr.addSimpleElement("Puerta", direccion.getPuerta(), true);
				bdr.addSimpleElement("Localidad", direccion.getLocalidad(), true);
				bdr.addSimpleElement("Codigo_Postal", direccion.getCodigoPostal(), true);
				bdr.addSimpleElement("Municipio", direccion.getMunicipio(), true);
				bdr.addSimpleElement("Provincia", direccion.getProvincia(), true);
				bdr.addSimpleElement("Pais", direccion.getPais(), true);
				bdr.addSimpleElement("Direccion_Completa", direccion.getDireccionCompleta(), true);
				bdr.addSimpleElement("Por_Defecto", Boolean.toString(direccion.isPorDefecto()));
				bdr.addClosingTag("Direccion");
			}
	        bdr.addClosingTag("Direcciones");
        }
        bdr.addClosingTag("Datos_Tercero");

        return bdr.getText();
	}

	/**
     * Método que modifica el email del remitente por el que se
     * ha introducido en el formulario
     * @param xmlData - Xml de la solicitud
     * @param xmlDataSpecif - Xml de los datos específicos
     * @return String - XML con los datos modificados
     * @throws Exception - Exception
     */
    private String putXMLDataSpecif(String xmlData, ActionForm form, String xmlDataSpecif) throws Exception{

    	XmlDocument xmlDoc = new XmlDocument();
    	xmlDataSpecif = "<" + Defs.DATOS_ESPECIFICOS.toLowerCase() + ">" + xmlDataSpecif + "</" + Defs.DATOS_ESPECIFICOS.toLowerCase() + ">";
	   	xmlDoc.createFromStringText(xmlDataSpecif);
    	// Obtenemos el mail de los datos específicos introducidos por el usuario
	   	String email = getValueEmail(xmlDoc);

	   	if(email != null) {
	   		xmlData = modificarEmail(xmlData, email);
	   	}

	   	//FormularioSolicitudForm formulario = (FormularioSolicitudForm)form;
    	//String datosEspecificos = null;
    	//if( formulario != null ) {
    	//	datosEspecificos = formulario.getDatosEspecificos();
    	//	if( datosEspecificos == null || datosEspecificos.equals("")) {
    			// Añadimos los datos específicos al xml de la solicitud
    	    	xmlData = xmlData.replaceAll("</" + Defs.TAG_DATOS_REGISTRO + ">", xmlDataSpecif + "</" + Defs.TAG_DATOS_REGISTRO + ">");
    	//	}
    	//}

        // Comprobamos si se pueden añadir documentos anexos
    	return putXMLDataDocuments(xmlData, form);
    }

    /**
     * Método que obtiene el valor del email del xml de datos específicos
     * @param doc - Xml de datos específicos
     * @return String - Valor del email
     */
    private String getValueEmail(XmlDocument doc) {
    	XmlElement element = doc.getRootElement();
    	XmlElement email = element.getDescendantElement(Defs.TAG_ED_EMAIL_SOLICITANTE);
    	return email==null?null:email.getValue();
    }

    /**
     * Método que establece un nuevo valor para el mail del remitente
     * @param doc - Xml de la solicitud de registro
     * @param newVal - Nuevo valor establecido para el mail del remitente
     */
    private void setValueEmailUser(XmlDocument doc, String newVal) {
    	XmlElement element = doc.getRootElement();
    	XmlElement email = element.getDescendantElement(Defs.TAG_ED_REMITENTE + "/" + Defs.TAG_ED_CORREO_ELECTRONICO);
    	if(email!=null)
    		email.setValue(newVal);
    }

    /**
     * Método que modifica el email del xml de datos
     * del remitente por el email que se pasa como parámetro
     * @param xmlData - Xml de la solicitadu de registro
     * @param email - Nuevo valor establecido para el mail del remitente
     * @return - Xml con los datos recargados en pantalla
     * @throws Exception - Exception
     */
    private String modificarEmail(String xmlData, String email) throws Exception {

    	XmlDocument xmlDocData = new XmlDocument();
   		xmlDocData.createFromStringText(xmlData);
   		// Modificamos el valor del mail en el xml del remitente
   		setValueEmailUser(xmlDocData, email);
   		return xmlDocData.getStringText(false);
    }

    //TODO ESTO CREO Q NO SE EJECUTA NUNCA!!!!!!! POR EL FORMULARIO: NO EXISTE!!!! PQ ESTA CREADO EN REQUEST
    /**
     * Método que añade o elimina documentos anexos en el
     * formulario de solicitud
     * @param xmlData - Xml con los datos del remitente
     * @param form - Formulario
     * @return - Xml con los datos recargados en pantalla
     * @throws Exception - Exception
     */
    private String putXMLDataDocuments( String xmlData, ActionForm form) throws Exception{
    	FormularioSolicitudForm formulario = (FormularioSolicitudForm)form;
    	String datosEspecificos = null;
    	String xmlDataDocument = "";
    	if( formulario != null ) {
    		datosEspecificos = formulario.getDatosEspecificos();
    	}
    	if( datosEspecificos != null) {

    		XmlDocument xmlDoc = new XmlDocument();
        	String xmlDataSpecif = "<" + Defs.DATOS_ESPECIFICOS.toLowerCase() + ">" + datosEspecificos + "</" + Defs.DATOS_ESPECIFICOS.toLowerCase() + ">";
    	   	xmlDoc.createFromStringText(xmlDataSpecif);
    	   	XmlElement element = xmlDoc.getRootElement();
        	XmlElement documentos = element.getDescendantElement(Defs.TAG_ED_INFORMACION_DOCUMENTOS + "/" + Defs.TAG_ED_DOCUMENTOS_ANEXADOS);
        	if( documentos != null) {
        		int i = 1;
        		XmlElement tramite = element.getDescendantElement(Defs.TAG_ED_INFORMACION_DOCUMENTOS + "/" + Defs.TAG_ED_TRAMITE);
        		XmlElement email = element.getDescendantElement(Defs.TAG_ED_EMAIL_SOLICITANTE);
	        	if( email != null ) {
	        		xmlData = modificarEmail(xmlData, email.getValue());
	        	}

        		xmlDataDocument += "<" + Defs.TAG_ED_DOCUMENTOS + ">";
	        	for( i = 1; i < Integer.parseInt(documentos.getValue()); i++) {
	        		xmlDataDocument += "<" + Defs.TAG_ED_DOCUMENTO + ">";
	        		xmlDataDocument += "<" + Defs.TAG_ED_NOMBRE + ">" + tramite.getValue() + (i+1) + "</" + Defs.TAG_ED_NOMBRE + ">";
	        		xmlDataDocument += "<" + Defs.TAG_ED_EXTENSION + ">pdf</" + Defs.TAG_ED_EXTENSION + ">";
	        		xmlDataDocument += "<" + Defs.TAG_ED_DESCRIPCION + ">Documento PDF</" + Defs.TAG_ED_DESCRIPCION + ">";
	        		xmlDataDocument += "<" + Defs.TAG_ED_NUMERO_DOCUMENTO + ">" + (i+1) + "</" + Defs.TAG_ED_NUMERO_DOCUMENTO + ">";
	        		xmlDataDocument += "</" + Defs.TAG_ED_DOCUMENTO + ">";
	        	}
	        	xmlDataDocument += "<" + Defs.TAG_ED_SIZE + ">"+(i)+"</Size>";
	        	xmlDataDocument += "</" + Defs.TAG_ED_DOCUMENTOS + ">";

        		xmlDataDocument = "<" + Defs.DATOS_ESPECIFICOS.toLowerCase() + ">" + datosEspecificos + xmlDataDocument + "</" + Defs.DATOS_ESPECIFICOS.toLowerCase() + ">";
        		// Añadimos los documents anexos al xml de la solicitud
        		return xmlData.replaceAll("</" + Defs.TAG_DATOS_REGISTRO + ">", xmlDataDocument + "</" + Defs.TAG_DATOS_REGISTRO + ">");

        	}
    	}

    	return xmlData;
    }
}