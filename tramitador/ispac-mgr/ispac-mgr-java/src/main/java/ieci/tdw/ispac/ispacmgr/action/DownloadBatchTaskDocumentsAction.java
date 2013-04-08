package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTemplate;
import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacmgr.action.form.BatchTaskForm;
import ieci.tdw.ispac.ispacmgr.mgr.DocumentsZipMgr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DownloadBatchTaskDocumentsAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		BatchTaskForm frm = (BatchTaskForm) form;

		String[] stagesIds = frm.getMultibox();
		String[] tasksIds =  frm.getTaskIds();

		// Lista de documentos
		List documents = getDocuments(session, stagesIds, tasksIds);
		if (CollectionUtils.isEmpty(documents)) {
			throw new ISPACInfo(getResources(request)
					.getMessage("error.download.noDocuments"), true);
		}
		
		// Nombre del zip
		String zipFileName = getZipFileName(session, frm);

		// Crear el zip con los documentos
		File zipFile = DocumentsZipMgr.createDocumentsZipFile(session, documents);
		
		// Devolver el zip al navegador
        ServletOutputStream out = response.getOutputStream();
    	response.setHeader("Pragma", "public");
    	response.setHeader("Cache-Control", "max-age=0");
    	response.setContentType("application/zip");
        response.setHeader("Content-Transfer-Encoding", "binary");
    	response.setHeader("Content-Disposition", 
    			"attachment; filename=\"" + zipFileName + "\"");
    	FileUtils.copy(zipFile, out);
        out.close();
        
        // Eliminar el zip
        FileUtils.deleteFile(zipFile);

		return null;
	}
	
	private List getDocuments(SessionAPI session, String[] stagesIds, 
			String[] tasksIds) throws ISPACException {
		
		// Lista de documentos
		List documents = new ArrayList();

		// Obtener los documentos
		IItem doc;
		int taskId;
		for (int i = 0; i < stagesIds.length; i++) {
			
			// Identificador del trámite
			taskId = -1;
			
			for (int j = 0; (taskId < 0) && (j < tasksIds.length); j++) {
				String[] pairStageIdTaskId = tasksIds[j].split(":");
				String strStageId = pairStageIdTaskId[0];
				String strTaskId = pairStageIdTaskId[1];
				if (stagesIds[i].equalsIgnoreCase(strStageId)){
					taskId = TypeConverter.parseInt(strTaskId);
				}
			}
			
			if (taskId > 0) {
				
				// Información del trámite
				ITask task = session.getAPI().getTask(taskId);
				
	    		// Comprobar si se tiene responsabilidad sobre el trámite
	    		String idResp = task.getString("ID_RESP");
	    		if (session.getAPI().getWorkListAPI().isInResponsibleList(idResp, ISecurityAPI.SUPERV_ANY , task)) {

		 			// Obtener la información del documento
					doc = getDocumentInfo(session, 
							TypeConverter.parseInt(stagesIds[i]), 
							taskId);
		
					if (doc != null) {
						documents.add(doc);
					}
	    		}
			}
		}

		return documents;
	}
	
	private IItem getDocumentInfo(SessionAPI session, int stageId, int taskId) 
			throws ISPACException {
		
		IEntitiesAPI entitiesAPI = session.getAPI().getEntitiesAPI();
		IItemCollection col = entitiesAPI.queryEntities(
				ISPACEntities.DT_ID_DOCUMENTOS,
				new StringBuffer("WHERE ID=(SELECT MAX(ID) FROM SPAC_DT_DOCUMENTOS WHERE ID_FASE=")
					.append(stageId)
					.append(" AND ID_TRAMITE=")
					.append(taskId)
					.append(")")
					.toString());
		
		if (col.next()) {
			return col.value();
		}
		
		return null;
	}
	
	private String getZipFileName(SessionAPI session, BatchTaskForm form) 
			throws ISPACException {
		
		String zipFileName = null;
		
		// Obtener el nombre de la plantilla
		int templateId = TypeConverter.parseInt(form.getTemplate(), -1);
		if (templateId > 0) {
			ITemplateAPI templateAPI = session.getAPI().getTemplateAPI();
			CTTemplate template = templateAPI.getTemplate(templateId);
			if (template != null) {
				zipFileName = template.getName();
				if (StringUtils.isNotBlank(zipFileName)) {
					zipFileName += ".zip";
				}
			}
		}
		
		if (StringUtils.isBlank(zipFileName)) {
			zipFileName = "documentos.zip";
		}
		
		return zipFileName;
	}
}
