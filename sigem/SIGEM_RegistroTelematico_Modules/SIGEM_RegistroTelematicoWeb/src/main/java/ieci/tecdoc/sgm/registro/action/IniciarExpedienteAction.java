package ieci.tecdoc.sgm.registro.action;

import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tecdoc.sgm.base.xml.core.XmlDocument;
import ieci.tecdoc.sgm.base.xml.core.XmlElement;
import ieci.tecdoc.sgm.base.xml.core.XmlElements;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.catalogo.Tramite;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.mensajes_cortos.ServicioMensajesCortos;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentoInfo;
import ieci.tecdoc.sgm.core.services.repositorio.ServicioRepositorioDocumentosTramitacion;
import ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento;
import ieci.tecdoc.sgm.core.services.telematico.RegistroDocumentos;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;
import ieci.tecdoc.sgm.core.services.tramitacion.ServicioTramitacion;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.DatosComunesExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.DocumentoExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InteresadoExpediente;
import ieci.tecdoc.sgm.registro.util.Definiciones;
import ieci.tecdoc.sgm.registro.utils.Defs;
import ieci.tecdoc.sgm.registro.utils.Misc;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class IniciarExpedienteAction extends RegistroWebAction {

	private static final Logger logger = Logger.getLogger(IniciarExpedienteAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	        HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();

		XmlElement root, genericData, specificData, registryData;
		String numRegistro = null;

		String sessionId = (String)session.getAttribute(Defs.SESION_ID);
		String tramiteId = (String)session.getAttribute(Defs.TRAMITE_ID);
		String justificante = (String)session.getAttribute(Defs.JUSTIFICANTE_REGISTRO);

		//Metemos en el ThreadLocal los datos de ip y host del usuario para la auditoría
    	setAuditContext(request);

		try{
			XmlDocument xmlDoc = new XmlDocument();
			xmlDoc.createFromStringText(justificante);
			root = xmlDoc.getRootElement();

			ServicioRepositorioDocumentosTramitacion oServicioRde = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
			ServicioRegistroTelematico oServicioRegistro = LocalizadorServicios.getServicioRegistroTelematico();
			ServicioCatalogoTramites oServicioCatalogo = LocalizadorServicios.getServicioCatalogoTramites();

			Entidad entidad = Misc.obtenerEntidad(request);

			/***** ANEXADO DE FICHEROS EN UN EXPEDIENTE YA CREADO: SUBSANACION ******/
			if (tramiteId != null && "SUBSANACION".equals(tramiteId)) {

				ServicioTramitacion oServicio = LocalizadorServicios.getServicioTramitacion();
				String numExpediente = (String)session.getAttribute(Defs.NUMERO_EXPEDIENTE);
				ArrayList docs = new ArrayList();

				registryData = root.getDescendantElement(Definiciones.XPATH_REGISTRY_DATA);
		        numRegistro = registryData.getChildElement(Definiciones.REGISTRY_NUMBER).getValue();

				// Obtener los documentos del justificante
				Map docsMap = new HashMap();

				XmlElement docsElement = root.getDescendantElement(Definiciones.XPATH_DOCUMENTS);
				XmlElements xmlElements = docsElement.getChildElements();
				for(int i = 1; i <= xmlElements.getCount(); i++) {

					XmlElement docElement = xmlElements.getItem(i);

					String code = docElement.getChildElement(Definiciones.CODE).getValue();
					String name = docElement.getChildElement(Definiciones.NAME).getValue()
								+ " (" + docElement.getChildElement(Definiciones.DESCRIPTION).getValue() + ")";
					docsMap.put(code, name);
				}

				RegistroDocumentos reg = oServicioRegistro.obtenerDocumentosRegistro(numRegistro, entidad);
				String idDocumento = (String)session.getAttribute("idDocumento");

				for(int i=0; i<reg.count(); i++) {

					RegistroDocumento rd = (RegistroDocumento)reg.get(i);
					String codigo = rd.getCode();
					DocumentoInfo di = oServicioRde.retrieveDocument(sessionId, rd.getGuid(), entidad);

					if (!codigo.equals(Definiciones.REGISTRY_RECEIPT_CODE) &&
							!codigo.equals(Definiciones.REGISTRY_REQUEST_CODE) &&
							!codigo.equals(Definiciones.REGISTRY_REQUEST_NOTSIGNED_CODE)){

						DocumentoExpediente docExpediente = new DocumentoExpediente();

						docExpediente.setCode("Anexo a Solicitud");
						docExpediente.setContent(di.getContent());
						docExpediente.setExtension(di.getExtension());
						docExpediente.setId(idDocumento);
						docExpediente.setLenght(di.getContent().length);
						docExpediente.setName((String) docsMap.get(codigo));

						docs.add(docExpediente);
					}
				}

				DocumentoExpediente[] docsExpediente = new DocumentoExpediente[docs.size()];
				for(int i=0; i<docs.size(); i++)
					docsExpediente[i] = (DocumentoExpediente)docs.get(i);

				if(oServicio.anexarDocsExpediente(entidad.getIdentificador(), numExpediente, numRegistro, new Date(), docsExpediente))
					return mapping.findForward("success");
				else
					return mapping.findForward("failure");
			}

			Tramite tramite = oServicioCatalogo.getProcedure(tramiteId, false, entidad);
			if (tramite.getIdProcedimiento() != null && !"".equals(tramite.getIdProcedimiento())) {

				boolean hayVirus = ((Boolean)session.getAttribute(Defs.HAY_VIRUS)).booleanValue();

				boolean bIniciar = false;
				boolean bIniciarExpediente = false;
				try {
					bIniciar = new Boolean((String)session.getServletContext().getAttribute(Defs.PLUGIN_INICIAREXPEDIENTECONVIRUS)).booleanValue();
					bIniciarExpediente = new Boolean((String)session.getServletContext().getAttribute(Defs.PLUGIN_INICIAREXPEDIENTE)).booleanValue();
				} catch(Exception e) { }


				//si no hay virus o si hay virus pero hay que iniciar expediente
				if (!hayVirus || (hayVirus && bIniciar)) {


					if (bIniciarExpediente) {

						// -------------------------------------------------------------
						// Misma implementación que en RegistroManager.iniciarExpediente
						// -------------------------------------------------------------

				        genericData = root.getDescendantElement(Definiciones.XPATH_SENDER_DATA);
				        String name = genericData.getChildElement(Definiciones.SENDER_NAME).getValue();
	   			        String mail = genericData.getChildElement(Definiciones.SENDER_EMAIL).getValue();
				        //String surname = genericData.getChildElement(Definiciones.SENDER_SURNAME).getValue();
				        genericData = root.getDescendantElement(Definiciones.XPATH_SENDER_DATA + "/" + Definiciones.ID);
				        String id = genericData.getChildElement(Definiciones.SENDER_ID).getValue();
				        specificData = root.getDescendantElement(Definiciones.XPATH_SPECIFIC_DATA);

				        String domicilio="", localidad="", provincia="", pais = "", telefono = "", movil = "", cp="", solicitar = "", deu = "";
				        boolean presencial = true;
				        try{
				        	if (specificData.getChildElement(Definiciones.DOMICILIO_NOTIFICACION) != null)
				        		domicilio = specificData.getChildElement(Definiciones.DOMICILIO_NOTIFICACION).getValue();
				        	if (specificData.getChildElement(Definiciones.LOCALIDAD) != null)
				        		localidad = specificData.getChildElement(Definiciones.LOCALIDAD).getValue();
				        	if (specificData.getChildElement(Definiciones.PROVINCIA) != null)
				        		provincia = specificData.getChildElement(Definiciones.PROVINCIA).getValue();
				        	if (specificData.getChildElement(Definiciones.PAIS) != null) {
				        		pais = specificData.getChildElement(Definiciones.PAIS).getValue();
				        	}
				        	else {
				        		pais = "España";
				        	}
				        	if (specificData.getChildElement(Definiciones.TELEFONO) != null)
				        		telefono = specificData.getChildElement(Definiciones.TELEFONO).getValue();
				        	if (specificData.getChildElement(Definiciones.TELEFONO_MOVIL) != null)
				        		movil = specificData.getChildElement(Definiciones.TELEFONO_MOVIL).getValue();
				        	if (specificData.getChildElement(Definiciones.SOLICITAR_ENVIO) != null)
				        		solicitar = specificData.getChildElement(Definiciones.SOLICITAR_ENVIO).getValue();
				        	if (specificData.getChildElement(Definiciones.DEU) != null)
				        		deu = specificData.getChildElement(Definiciones.DEU).getValue();
				        	if (specificData.getChildElement(Definiciones.CP) != null)
				        		cp = specificData.getChildElement(Definiciones.CP).getValue();
				        	if (solicitar.equals("Si") && !isBlank(deu))
				        		presencial = false;
				        }catch(Exception ex){
				        	presencial = false;
				        }

				        InteresadoExpediente[] interested = new InteresadoExpediente[1];
				        InteresadoExpediente interestedPerson;
				        //InterestedPerson interestedPerson;
				        interestedPerson = new InteresadoExpediente();
				        interestedPerson.setIndPrincipal(InteresadoExpediente.IND_PRINCIPAL);
			        	//interestedPerson.setName(name.trim() + " " + surname);
				        interestedPerson.setName(name);
			        	interestedPerson.setNifcif(id);
			        	interestedPerson.setThirdPartyId(null);
			        	if (movil != null && !"".equals(movil))
			        		interestedPerson.setMobilePhone(movil);
			        	if (telefono != null && !"".equals(telefono))
			        		interestedPerson.setPhone(telefono);

				        if (!presencial){
				           	interestedPerson.setNotificationAddressType(InteresadoExpediente.IND_TELEMATIC);
				        	interestedPerson.setTelematicAddress(deu);
				        }else{
				        	interestedPerson.setNotificationAddressType(InteresadoExpediente.IND_POSTAL);
				        	if (mail != null && !"".equals(mail))
				        		interestedPerson.setTelematicAddress(mail);

				        }

				        interestedPerson.setPlaceCity(localidad);
				        interestedPerson.setPostalCode(cp);
				        interestedPerson.setPostalAddress(domicilio);
				        if (isBlank(pais)) {
				        	interestedPerson.setRegionCountry(provincia);
				        }
				        else {
				        	interestedPerson.setRegionCountry(provincia + " / " + pais);
				        }

						interested[0] = interestedPerson;

						genericData = root.getDescendantElement(Definiciones.XPATH_GENERIC_DATA + "/" + Definiciones.TOPIC);
				        String asunto = genericData.getChildElement(Definiciones.DESCRIPTION).getValue();
				        registryData = root.getDescendantElement(Definiciones.XPATH_REGISTRY_DATA);
				        String numero_registro = registryData.getChildElement(Definiciones.REGISTRY_NUMBER).getValue();
				        String fecha = registryData.getChildElement(Definiciones.REGISTRY_DATE).getValue() + " " +
				        				registryData.getChildElement(Definiciones.REGISTRY_HOUR).getValue();
				        String pattern = Definiciones.DEFAULT_DATE_FORMAT + " " + Definiciones.DEFAULT_HOUR_FORMAT;
				        SimpleDateFormat myDateFormat = new SimpleDateFormat(pattern);
				        Date date = myDateFormat.parse(fecha);

				        String cod = tramite.getIdProcedimiento();
				        DatosComunesExpediente commonData = new DatosComunesExpediente();
				        commonData.setFechaRegistro(date);
				        commonData.setInteresados(interested);
				        commonData.setNumeroRegistro(numero_registro);
				        commonData.setTipoAsunto(cod);

						genericData = root.getDescendantElement(Definiciones.XPATH_GENERIC_DATA + "/" + Definiciones.ADDRESSEE);

						// Datos específicos del procedimiento
						String specificDataXML = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>" +
											"<" + Definiciones.TAG_INICIAR_EXPEDIENTE_DATOS_ESPECIFICOS + ">" +
											(String)session.getAttribute(Defs.DATOS_ESPECIFICOS) +
											"</" + Definiciones.TAG_INICIAR_EXPEDIENTE_DATOS_ESPECIFICOS + ">";

						// Obtener los documentos del justificante
						Map docsMap = new HashMap();

						XmlElement docsElement = root.getDescendantElement(Definiciones.XPATH_DOCUMENTS);
						XmlElements xmlElements = docsElement.getChildElements();
						for(int i = 1; i <= xmlElements.getCount(); i++) {

							XmlElement docElement = xmlElements.getItem(i);

							String code = docElement.getChildElement(Definiciones.CODE).getValue();
							String docName = docElement.getChildElement(Definiciones.NAME).getValue();
							docsMap.put(code, docName);
						}

						// Lista de documentos del expediente
						List documents = new ArrayList();

						// Si en el trámite existe la solicitud firmada (requerida), con este documento se iniciará el expediente,
						// y si al contrario, el trámite no ha requerido que se firme la solicitud, se enviará la solicitud sin firmar
						boolean solicitudFirmada = false;
						DocumentoExpediente solicitudNoFirmada = null;

						RegistroDocumentos rds = oServicioRegistro.obtenerDocumentosRegistro(numero_registro, Misc.obtenerEntidad(request));
						for(int i=0; i<rds.count(); i++) {

							RegistroDocumento rd = (RegistroDocumento)rds.get(i);
							String codigo = rd.getCode();
							DocumentoInfo di = oServicioRde.retrieveDocument(sessionId, rd.getGuid(), entidad);
							String extension = di.getExtension();

							DocumentoExpediente document = new DocumentoExpediente();

							if (codigo.equals(Definiciones.REGISTRY_RECEIPT_CODE)) {
								codigo = "Justificante";
							}
							else if (codigo.equals(Definiciones.REGISTRY_REQUEST_CODE)) {
								codigo = "Solicitud Registro";
								solicitudFirmada = true;
							}
							else if (codigo.equals(Definiciones.REGISTRY_REQUEST_NOTSIGNED_CODE)) {
								codigo = "Solicitud Registro";
								solicitudNoFirmada = document;
							}
							else {
								codigo = "Anexo a Solicitud";
							}

							if (extension.equalsIgnoreCase("xml"))
								extension = "txt";

							document.setCode(codigo);
							document.setContent(di.getContent());
							document.setExtension(extension);
							document.setId(null);
							document.setLenght(di.getContent().length);

							String docName = (String) docsMap.get(rd.getCode());
							if (docName != null) {
								document.setName(docName);
							}
							else {
								document.setName(asunto);
							}

							documents.add(document);
						}

						// Si la solicitud firmada existe
						// eliminar la solicitud no firmada de los documentos que se envían para iniciar expediente
						if (solicitudFirmada) {
							documents.remove(solicitudNoFirmada);
						}

						// Servicio de tramitación para iniciar expediente
						ServicioTramitacion oServicioTramitacion = LocalizadorServicios.getServicioTramitacion();

						DocumentoExpediente[] documentsExpediente = new DocumentoExpediente[documents.size()];
						for(int j=0; j<documents.size(); j++)
							documentsExpediente[j] = (DocumentoExpediente)documents.get(j);

						oServicioTramitacion.iniciarExpediente(entidad.getIdentificador(), commonData, specificDataXML,	documentsExpediente);
					}
				}
			}
		}
		catch (Exception e) {

			boolean bIniciarExpedienteErrorRegistroEstadoError = false;
			try {
				bIniciarExpedienteErrorRegistroEstadoError = new Boolean((String) session.getServletContext().getAttribute(Defs.PLUGIN_INICIAREXPEDIENTEERRORREGISTROESTADOERROR)).booleanValue();
			}
			catch (Exception e1) {
			}

			if (tramiteId != null) {

				if (!tramiteId.equals("SUBSANACION")) {

					String errorMessage = "Error al iniciar expediente para el tramite [" + tramiteId + "] con el justificante [" + justificante + "]";
					logger.error(errorMessage , e.getCause());

					// Enviar el mensaje con la excepción si se ha configurado así
					sendErrorEmail(session.getServletContext(), errorMessage, e);

					if (bIniciarExpedienteErrorRegistroEstadoError) {

						try {

							if (numRegistro == null) {

								XmlDocument xmlDoc = new XmlDocument();
								xmlDoc.createFromStringText(justificante);
								root = xmlDoc.getRootElement();

								registryData = root.getDescendantElement(Definiciones.XPATH_REGISTRY_DATA);
								numRegistro = registryData.getChildElement(Definiciones.REGISTRY_NUMBER).getValue();
							}

							ServicioRegistroTelematico oServicioRegistro = LocalizadorServicios.getServicioRegistroTelematico();

							// Marcar el registro como erróneo y eliminar los documentos
							oServicioRegistro.deshacerRegistro(sessionId, numRegistro, Misc.obtenerEntidad(request));
						}
						catch (Exception e2) {

							logger.error("Error al deshacer el registro [" + numRegistro + "]", e2.getCause());
						}
					}
					else {

						return mapping.findForward("success");
					}
				}
				else {

					//Error de subsanación
					String numExpediente = (String)session.getAttribute(Defs.NUMERO_EXPEDIENTE);
					if (numExpediente == null){
						numExpediente="";
					}
					String errorMessage = "Error en la subsanación para el justificante [" + justificante + "] y el número de expediente ["+numExpediente+"].";
					sendErrorEmail(session.getServletContext(), errorMessage, e);
					logger.error(errorMessage, e.getCause());
				}
			}

			session.removeAttribute(Defs.JUSTIFICANTE_REGISTRO);
			session.removeAttribute(Defs.DATOS_ESPECIFICOS);

			session.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_INICIO_EXPEDIENTE);
			session.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.getMessage());

			return mapping.findForward("failure");
		}

		return mapping.findForward("success");
	}


	private void sendErrorEmail(ServletContext servletContext,String message, Exception e) {
		String from = (String) servletContext.getAttribute(
				Defs.PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_ORIGEN);
		String to = (String) servletContext.getAttribute(
				Defs.PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_DESTINO);
		String subject = (String) servletContext.getAttribute(
				Defs.PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_ASUNTO);

		if (!isBlank(from) && !isBlank(to) && !isBlank(subject)) {
			StringBuffer content = new StringBuffer();
			content.append(message+"\n");
			content.append("Excepción: \n");
			content.append(e.getMessage()+"\n");
			content.append(e.getCause());
			content.append(Misc.getStackTrace(e));
			if (logger.isInfoEnabled()) {
				logger.info("Se procede a enviar la notificación del error en el inicio del expediente.\n E-mail Origen: "
						+ from
						+ "\n E-mail Destino: "
						+ to
						+ "\n Asunto E-mail: "
						+ subject
						+ "\n Contenido E-mail:"
						+ content.toString());
			}
			try {
				String[] toArray = { to };
				ServicioMensajesCortos servicioMensajesCortos = LocalizadorServicios
						.getServicioMensajesCortos();
				servicioMensajesCortos.sendMail(from, toArray, null, null, content.toString(),
						message, null);
			} catch (SigemException e1) {

				logger.error("Error al enviar el mensaje corto para la notificación del error en el inicio del expediente a la dirección: " + to, e1);
			}
		}else{
			if (logger.isDebugEnabled()) {
				logger.debug("No se envía el e-mail de notificación del error en el inicio de expediente al dejar vacía alguna de las propiedades de configuracion de 'iniciarExpendienteError.enviarEmail.origen, 'iniciarExpendienteError.enviarEmail.destino' o 'iniciarExpendienteError.enviarEmail.asunto'");
			}
		}
	}



	public boolean isBlank(String cadena){
		if (cadena == null || cadena.equals(""))
			return true;
		return false;
	}

	/**
	 * Establecer el contexto de auditoría para la tramitación de expedientes.
	 *
	 * @param request
	 */
	private void setAuditContext(HttpServletRequest request) {

		// Auditoría
		AuditContext auditContext = new AuditContext();

		auditContext.setUserHost(request.getRemoteHost());
		auditContext.setUserIP(request.getRemoteAddr());
		auditContext.setUser("REGISTRO_TELEMATICO");
		auditContext.setUserId("SYSTEM");

		// Añadir en el ThreadLocal el objeto AuditContext
		AuditContextHolder.setAuditContext(auditContext);
	}

}