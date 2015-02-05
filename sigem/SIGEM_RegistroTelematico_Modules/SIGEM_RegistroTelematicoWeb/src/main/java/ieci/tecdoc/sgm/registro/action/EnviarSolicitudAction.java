package ieci.tecdoc.sgm.registro.action;

import ieci.tecdoc.sgm.base.base64.Base64Util;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;
import ieci.tecdoc.sgm.base.xml.core.XmlDocument;
import ieci.tecdoc.sgm.base.xml.core.XmlTransformer;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catalogo.OrganoDestinatario;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.catalogo.Tramite;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;
import ieci.tecdoc.sgm.core.services.telematico.PeticionDocumento;
import ieci.tecdoc.sgm.core.services.telematico.PeticionDocumentos;
import ieci.tecdoc.sgm.core.services.telematico.RegistroPeticion;
import ieci.tecdoc.sgm.core.services.telematico.RegistroTelematicoException;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;
import ieci.tecdoc.sgm.core.web.locale.LocaleFilterHelper;
import ieci.tecdoc.sgm.rde.database.util.Utilities;
import ieci.tecdoc.sgm.registro.form.FormularioSolicitudForm;
import ieci.tecdoc.sgm.registro.util.Definiciones;
import ieci.tecdoc.sgm.registro.utils.Defs;
import ieci.tecdoc.sgm.registro.utils.Ficheros;
import ieci.tecdoc.sgm.registro.utils.Misc;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.util.MessageResources;

public class EnviarSolicitudAction extends RegistroWebAction {

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
	    	String numExp = (String)session.getAttribute(Defs.NUMERO_EXPEDIENTE);

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

	    		FormularioSolicitudForm formulario = (FormularioSolicitudForm)form;

	    		ServicioCatalogoTramites oServicioCatalogoTramites = LocalizadorServicios.getServicioCatalogoTramites();
		    	ServicioRegistroTelematico oServicioRegistroTelematico = LocalizadorServicios.getServicioRegistroTelematico();
			   	ServicioEntidades oServicioEntidades = LocalizadorServicios.getServicioEntidades();

		    	// Obtener el trámite de la solicitud
		    	Tramite tramite = oServicioCatalogoTramites.getProcedure(tramiteId, false, entidad);

		    	OrganoDestinatario datosOrgano = null;
		    	RegistroPeticion requestInfo = new RegistroPeticion();

		    	String addressee = formulario.getOrganoDestinatario();
		    	if (addressee == null || addressee.equals("")) {

		    		requestInfo.setAddressee(tramite.getAddressee());
		    		datosOrgano = oServicioCatalogoTramites.getAddressee(tramite.getAddressee(), entidad);
		    	}
		    	else {
		    		requestInfo.setAddressee(addressee);
		    		datosOrgano = oServicioCatalogoTramites.getAddressee(addressee, entidad);
		    	}

		        // La solicitud se firma o no en función de la configuración
		        // establecida para el trámite en la opción de Firmar solicitud
		    	boolean firma = tramite.getFirma();
		    	if (firma) {
		    		session.setAttribute(Defs.FIRMAR_SOLICITUD, "1");
		    	} else {
		    		session.setAttribute(Defs.FIRMAR_SOLICITUD, "0");
		    	}

		    	String oficina = tramite.getOficina();
		    	session.setAttribute(Defs.OFICINA, oficina);

		    	requestInfo.setEmail(formulario.getEmailSolicitante());
		    	requestInfo.setProcedureId(tramiteId);

		    	requestInfo.setSenderIdType("");

		    	String datosEspecificos = formulario.getDatosEspecificos();
		    	String datosIdiomas = null;
		    	if (datosEspecificos.indexOf("Idioma_Documentos") != -1) {

		    		int ind_idiomas1 = datosEspecificos.indexOf("Idioma_Documentos");
		    		int ind_idiomas2 = datosEspecificos.indexOf("Idioma_Documentos", ind_idiomas1 + 1);
		    		datosIdiomas = datosEspecificos.substring(ind_idiomas1 -1, ind_idiomas2 + "Idioma_Documentos".length() + 1);
		    		datosEspecificos = datosEspecificos.substring(0, ind_idiomas1 - 1);
		    	}

		    	if (datosOrgano != null) {

		    		datosEspecificos += "<cod_organo>" + datosOrgano.getId() + "</cod_organo>";
		    		datosEspecificos += "<descr_organo><![CDATA[" + datosOrgano.getDescription() + "]]></descr_organo>";
		    	}
		    	requestInfo.setSpecificData(datosEspecificos);

		    	//requestInfo.setSpecificData(Base64Util.decodeToString(formulario.getDatosEspecificos()));
		    	requestInfo.setFolderId("");
		    	session.setAttribute(Defs.DATOS_ESPECIFICOS, formulario.getDatosEspecificos());
		    	//session.setAttribute(Defs.DATOS_ESPECIFICOS, Base64Util.decodeToString(formulario.getDatosEspecificos()));

		    	// Obtener los documentos anexados a la solicitud
		    	PeticionDocumentos peticionDocumentos = processDocuments(request, session, formulario, datosIdiomas, sessionId, separador);
		    	session.setAttribute(Defs.DOCUMENTOS_REQUEST, peticionDocumentos);

		    	if (peticionDocumentos != null && peticionDocumentos.count() > 0) {
		    		requestInfo.setDocuments(peticionDocumentos);
		    	} else {
		    		requestInfo.setDocuments(null);
		    	}

			   	String organismo = oServicioEntidades.obtenerEntidad(entidad.getIdentificador()).getNombreLargo();
			    //String organismo = entidad.getNombre();//(String)session.getServletContext().getAttribute(Defs.PLUGIN_ORGANISMO);

		    	// Crear la petición de registro
		    	byte[] solicitud = oServicioRegistroTelematico.crearPeticionRegistro(sessionId, requestInfo, idioma.getLanguage(), organismo, numExp, entidad);

			   	XmlDocument xmlDoc = new XmlDocument();
			   	String strSolicitud = Goodies.fromUTF8ToStr(solicitud);
			   	xmlDoc.createFromStringText(strSolicitud);

			   	// Establecer si los documentos anexados a la solicitud tienen virus o no
			   	processVirus(session, strSolicitud, xmlDoc);

			   	// Combinar la solicitud con el XSL de presentación
			   	String xml_w_xsl = XmlTransformer.transformXmlDocumentToHtmlStringTextUsingXslFile(xmlDoc, xslPath);
			   	session.setAttribute(Defs.SOLICITUD_REGISTRO, xml_w_xsl);
			   	session.setAttribute(Defs.REQUEST, Base64Util.encode(solicitud));

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
	    catch(Exception e) {

	    	request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_ENVIO_SOLICITUD);
	    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.getMessage());

	    	return mapping.findForward("failure");
   		}

	    return mapping.findForward("success");
   	}

    protected PeticionDocumentos processDocuments(HttpServletRequest request,
    											  HttpSession session,
    											  ActionForm formulario,
    											  String datosIdiomas,
    											  String sessionId,
    											  String separador) throws Exception {

    	PeticionDocumentos peticionDocumentos = new PeticionDocumentos();

    	String tmpUploadPath = session.getServletContext().getRealPath("")
    						 + separador
    						 + (String)session.getServletContext().getAttribute(Defs.PLUGIN_TMP_PATH_UPLOAD);

    	MultipartRequestHandler mprh = formulario.getMultipartRequestHandler();
    	if (mprh != null) {

	    	Hashtable ht = mprh.getFileElements();

	    	int numFicheros = ht.size();
	    	FormFile[] ficheros = new FormFile[numFicheros];
	    	String[] codigos = new String[numFicheros];
	    	String[] idiomas = new String[numFicheros];

	    	int cont = 0;
	    	for (Enumeration enumer = ht.keys() ; enumer.hasMoreElements() ;) {

	    		String key = (String)enumer.nextElement();
	    		FormFile fichero = (FormFile)ht.get(key);
	    		if (fichero.getFileSize() > 0) {

	    			codigos[cont] = key;
	    			ficheros[cont] = fichero;
	    			idiomas[cont] = Misc.obtenerIdioma(datosIdiomas, key);
	    			cont++;
	    		}
	        }

	    	Map hashDocumentos = new HashMap();

	    	for(int i=0; i<cont; i++) {

	    		PeticionDocumento aux = addDocumento(request, tmpUploadPath, separador, ficheros[i], codigos[i], idiomas[i], sessionId, hashDocumentos);
	    		peticionDocumentos.add(aux);
	    	}
    	}

    	return peticionDocumentos;
    }

	protected PeticionDocumento addDocumento(HttpServletRequest request,
											 String tmpUploadPath,
											 String separador,
											 FormFile formFile,
											 String code,
											 String idioma,
											 String sessionId,
											 Map hashDocumentos) throws Exception {

		String fileName = "";
		byte fileData[] = null;

		PeticionDocumento document = new PeticionDocumento();

		fileName = formFile.getFileName();
		document.setFileName(fileName);
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

		Calendar calendar = Calendar.getInstance();
		fileName = tmpUploadPath +  separador + sessionId + "_" + String.valueOf(calendar.getTimeInMillis()) + "_" + formFile.getFileName();
		fileData = formFile.getFileData();

		Ficheros.storeFile(fileName, fileData);

		String hash = Utilities.getHash(fileData);
		if (hashDocumentos.containsKey(hash)) {
			String fileNameCopia = (String) hashDocumentos.get(hash);

			MessageResources resources = getResources(request);
			throw new RegistroTelematicoException(resources.getMessage(Defs.MENSAJE_ERROR_ENVIO_SOLICITUD_DOCUMENTOS_DUPLICADOS, new String[] {formFile.getFileName(), fileNameCopia}));
		}
		else {
			hashDocumentos.put(hash, formFile.getFileName());
		}

		document.setCode(code);
		document.setExtension(ext);
		document.setLocation(fileName);
		document.setIdioma(idioma);

		return document;
	}

	protected void generateXmlRequestTmpFile(HttpServletRequest request,
											 HttpSession session,
											 String separador,
											 String tramiteId,
											 String sessionId,
											 String solicitud) throws Exception {

    	String tmpXmlPath = (String)session.getServletContext().getAttribute(Defs.PLUGIN_TMP_PATH_XML);

	   	String xmlPath = session.getServletContext().getRealPath("")
	   				   + separador + tmpXmlPath
	   				   + separador + sessionId + "_" + tramiteId + ".xml";

	  BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(xmlPath), "UTF8"));

		out.write(solicitud);

		out.close();

	   	String tmpXmlPathChange = tmpXmlPath;
	   	while (tmpXmlPathChange.indexOf("\\")!=-1)
	   		tmpXmlPathChange = tmpXmlPathChange.replace('\\', '/');

	   	session.setAttribute(Defs.XML_REQUEST_FILE, request.getContextPath() + "/" + tmpXmlPathChange + "/" + sessionId + "_" + tramiteId + ".xml");
	}

	protected void processVirus(HttpSession session,
								String solicitud,
								XmlDocument xmlDoc) {

	   	if (solicitud.indexOf("<" + Definiciones.ANTIVIRUS + ">" + Definiciones.ANTIVIRUS_ERROR + "</" + Definiciones.ANTIVIRUS + ">") == -1) {

	   		// Documentos anexados sin virus
	   		session.setAttribute(Defs.HAY_VIRUS, new Boolean(false));
	   	}
	   	else {
	   		// Alguno de los documentos anexados contiene virus
	   		session.setAttribute(Defs.HAY_VIRUS, new Boolean(true));
	   		session.setAttribute(Defs.HAY_VIRUS_DOCUMENTOS, Misc.processDocumentsWithVirus(xmlDoc));
	   	}
	}

	protected String formatear(String datos) {

		String d = "";

		for(int i=0; i<datos.length(); i++) {

			if ((datos.charAt(i) != '\r') && (datos.charAt(i) != '\n') && (datos.charAt(i) != ' '))
				d += datos.charAt(i);
		}

		return d;
	}

}