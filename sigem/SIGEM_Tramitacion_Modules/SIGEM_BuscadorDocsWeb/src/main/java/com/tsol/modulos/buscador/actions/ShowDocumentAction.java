package com.tsol.modulos.buscador.actions;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowDocumentAction extends Action {

	private static final Logger LOGGER = Logger.getLogger(ShowDocumentAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// Identificador del documento
        String documentId = request.getParameter("id");

		// API de sesión de iSPAC
        SessionAPI sessionAPI = SessionAPIFactory.getSessionAPI(request, response);
        IInvesflowAPI invesflowAPI = sessionAPI.getAPI();
        IEntitiesAPI entitiesAPI = invesflowAPI.getEntitiesAPI();

        // Contexto para la auditoría
		setAuditContext(request, sessionAPI);

        // Obtener la información del documento
		IItem documentItem = entitiesAPI.getDocument(TypeConverter.parseInt(documentId, 0));
        if (documentItem == null) {
            return null;
        }

    	// Obtener el GUID del documento en el repositorio de documentos
    	String documentGUID = documentItem.getString("INFOPAG_RDE");
    	if (StringUtils.isBlank(documentGUID)) {
    		documentGUID = documentItem.getString("INFOPAG");
    	}

        if (StringUtils.isBlank(documentGUID)) {
        	return null;
        }

    	ServletOutputStream out = response.getOutputStream();
    	IGenDocAPI genDocAPI = invesflowAPI.getGenDocAPI();
		Object connectorSession = null;

		try {

			connectorSession = genDocAPI.createConnectorSession();

			if (!genDocAPI.existsDocument(connectorSession, documentGUID)) {
				//Se saca el mensaje de error en la propia ventana, que habra sido lanzada con un popup
				response.setContentType("text/html");
				LOGGER.error("No se ha encontrado el documento físico con identificador: '"+documentGUID+"' en el repositorio de documentos");
				String message = new ISPACInfo("exception.documents.notExists").getExtendedMessage(sessionAPI.getClientContext().getLocale());
				out.write(message.getBytes());
				out.close();
				return null;
			}

	    	response.setHeader("Pragma", "public");
	    	response.setHeader("Cache-Control", "max-age=0");
            response.setHeader("Content-Transfer-Encoding", "binary");

            String mimetype = genDocAPI.getMimeType(connectorSession, documentGUID);
            response.setContentType(mimetype);

            // Extensión del documento
            String extension = getDocumentExtension(documentItem);
			if ("application/pdf".equalsIgnoreCase(mimetype)
					|| StringUtils.isBlank(extension)) {
         		StringBuffer name = new StringBuffer(documentId);
         		if (StringUtils.isNotBlank(extension)){
         			name.append(".").append(extension);
         		}
         		response.setHeader("Content-Disposition", "attachment; filename=\"" + name.toString() + "\"");
         	} else {
            	response.setHeader("Content-Disposition", "inline; filename=\"" + documentId + "." + extension + "\"");
            }

            response.setContentLength(genDocAPI.getDocumentSize(connectorSession, documentGUID));

            try {
            	genDocAPI.getDocument(connectorSession, documentGUID, out);
            }
            catch(ISPACException e){
            	//Se saca el mensaje de error en la propia ventana, que habra sido lanzada con un popup
            	response.setContentType("text/html");
            	out.write(e.getCause().getMessage().getBytes());
            }
            finally{
            	out.close();
            }

		} catch(Exception e) {

        	LOGGER.error("Error al descargar el documento", e);

        	//Se saca el mensaje de error en la propia ventana, que habra sido lanzada con un popup
        	response.setContentType("text/html");
        	out.write(e.getCause().getMessage().getBytes());

        } finally {
			if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
		}

        return null;
    }


	/**
	 * Obtiene el GUID del documento en el repositorio de documentos.
	 *
	 * @param document
	 *            Información del documento
	 * @return GUID del documento
	 * @throws ISPACException
	 */
	protected String getDocumentGUID(IItem document) throws ISPACException {

		String guid = document.getString("INFOPAG_RDE");
		if (StringUtils.isNotBlank(guid)) {
			return guid;
		}

		return document.getString("INFOPAG");
	}

    /**
     * Obtiene la extensión del documento a descargar
	 * @param document
	 *            Información del documento
     * @return Extensión del documento
     * @throws ISPACException
     */
    protected String getDocumentExtension(IItem document) throws ISPACException {

    	// Obtener la extensión del documento del repositorio de documentos electronicos
    	String guid = document.getString("INFOPAG_RDE");
    	if (StringUtils.isNotBlank(guid)) {
    		return document.getString("EXTENSION_RDE");
    	} else {
    		return document.getString("EXTENSION");
    	}
    }

	/**
	 * @param request
	 */
    protected void setAuditContext(HttpServletRequest request, SessionAPI session) {
		AuditContext auditContext = new AuditContext();
		auditContext.setUserHost(request.getRemoteHost());
		auditContext.setUserIP(request.getRemoteAddr());
		auditContext.setUser(session.getUserName());
		auditContext.setUserId(session.getClientContext().getUser().getUID());
		AuditContextHolder.setAuditContext(auditContext);
	}
}