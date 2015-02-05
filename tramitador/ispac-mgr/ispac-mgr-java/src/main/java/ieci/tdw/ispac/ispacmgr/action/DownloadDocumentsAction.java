package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacmgr.action.form.DocumentsForm;
import ieci.tdw.ispac.ispacmgr.mgr.DocumentsZipMgr;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

public class DownloadDocumentsAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

		DocumentsForm frm = (DocumentsForm) form;

		String[] documentIds = frm.getMultibox();

		// Nombre del zip
		String zipFileName = getZipFileName(session, request);

		// Lista de documentos
		List documents = getDocuments(session, documentIds);

		File zipFile = null;
		
		try {
			// Crear el zip con los documentos
			zipFile = DocumentsZipMgr.createDocumentsZipFile(session, documents);
			
			// Devolver el zip al navegador
	    	response.setHeader("Pragma", "public");
	    	response.setHeader("Cache-Control", "max-age=0");
	        response.setHeader("Content-Transfer-Encoding", "binary");
	    	response.setHeader("Content-Disposition","attachment; filename=\"" + zipFileName + "\"");
	    	response.setContentType("application/zip");
	    	response.setContentLength((int)zipFile.length());
	    	
	        ServletOutputStream out = response.getOutputStream();
	    	FileUtils.copy(zipFile, out);
	    	out.flush();
	    	out.close();
		}
		finally {
        
			if (zipFile != null) {
		        // Eliminar el fichero temporal del zip
		        FileUtils.deleteFile(zipFile);
			}
		}

		return null;
	}
	
	private List getDocuments(SessionAPI session, String[] documentIds) 
			throws ISPACException {
		
		// Lista de documentos
		List documents = new ArrayList();

		if (!ArrayUtils.isEmpty(documentIds)) {
			
			// API de entidades
			IEntitiesAPI entitiesAPI = session.getAPI().getEntitiesAPI();
			
			// Obtener los documentos
			IItem doc;
			int docId;
			for (int i = 0; i < documentIds.length; i++) {
				
				// Identificador del documento
				docId = TypeConverter.parseInt(documentIds[i], -1); 
				if (docId > 0) {
					
					// Obtener la información del documento
					doc = entitiesAPI.getDocument(docId);
					if (doc != null) {
						documents.add(doc);
					}
				}
			}
		}

		return documents;
	}
	
	private String getZipFileName(SessionAPI session, HttpServletRequest request) 
			throws ISPACException {
		
		String zipFileName = null;

		ClientContext cct = session.getClientContext();
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
    	IState state = managerAPI.currentState(getStateticket(request));
		
		if (state != null) {
			String numExp = state.getNumexp();
			
			if (StringUtils.isNotBlank(numExp)) {
				MessageResources resources = getResources(request);
				zipFileName = resources.getMessage(cct.getLocale(),
						"forms.listdoc.descargarDocumentos.zip");
			}
		}
		
		if (StringUtils.isBlank(zipFileName)) {
			zipFileName = "documentos.zip";
		}
		
		return zipFileName;
	}
}
