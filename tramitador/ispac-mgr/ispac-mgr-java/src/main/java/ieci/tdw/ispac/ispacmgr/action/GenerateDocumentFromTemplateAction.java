package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACVariable;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.ispacweb.util.DocumentUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sun.star.connection.NoConnectException;
import com.sun.star.lang.DisposedException;

public class GenerateDocumentFromTemplateAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception	{
		
	    ClientContext cct = session.getClientContext();

		///////////////////////////////////////////////
		// Se obtiene el estado de tramitación
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState currentState = managerAPI.currentState(getStateticket(request));
		
  	    IInvesflowAPI invesFlowAPI = session.getAPI();
		IGenDocAPI genDocAPI = invesFlowAPI.getGenDocAPI();
		
		int state = currentState.getState();
		int stageId = currentState.getStageId();
		int taskId = currentState.getTaskId();
		int taskPcdId = currentState.getTaskPcdId();
		int activityId = currentState.getActivityId();
		
		int documentTypeId = Integer.parseInt(request.getParameter("documentTypeId"));
		int templateId = Integer.parseInt(request.getParameter("templateId"));
		
		int documentId = 0;
		IItem entityDocument = null;
		
		String ids[] = null;
		List oks = new ArrayList();
		List errors = new ArrayList();
//		if (currentState.getState() == ManagerState.PROCESSESLIST || currentState.getState() == ManagerState.SUBPROCESSESLIST){
//			String  idStr = cct.getSsVariable(ISPACVariable.STAGES_ACTIVITES_DOCUMENTS_GENERATION);
//			cct.deleteSsVariable(ISPACVariable.STAGES_ACTIVITES_DOCUMENTS_GENERATION);
//			ids = StringUtils.split(idStr, '-');
//		}else if (currentState.getState() == ManagerState.EXPEDIENT){
//			ids = new String[]{String.valueOf(stageId)};
//		}else if (currentState.getState() == ManagerState.SUBPROCESS){
//			ids = new String[]{String.valueOf(activityId)};
//		}else if (currentState.getState() == ManagerState.TASK){
//			ids = new String[]{String.valueOf(taskId)};
//		}
		switch (state) {
			case ManagerState.PROCESSESLIST :
			case ManagerState.SUBPROCESSESLIST:
				String  idStr = cct.getSsVariable(ISPACVariable.STAGES_ACTIVITES_DOCUMENTS_GENERATION);
				cct.deleteSsVariable(ISPACVariable.STAGES_ACTIVITES_DOCUMENTS_GENERATION);
				ids = StringUtils.split(idStr, '-');
			break;
			
			case ManagerState.EXPEDIENT:
				ids = new String[]{String.valueOf(stageId)};
			break;
			case ManagerState.SUBPROCESS:
				ids = new String[]{String.valueOf(activityId)};
			break;
			case ManagerState.TASK:
				ids = new String[]{String.valueOf(taskId)};
			break;
		}
		
		Object connectorSession = null;
		try {
			connectorSession = genDocAPI.createConnectorSession();
		
			for (int i = 0; i < ids.length; i++) {
				
				int currentId = Integer.parseInt(ids[i]);
				
				// Ejecución en un contexto transaccional
				boolean bCommit = false;
				
				try {	
					// Abrir transacción para que no se pueda generar un documento sin fichero
			        cct.beginTX();
			        
					// Si se recibe el documentId significa que se va a sustituir el fichero del documento ya existente,
					// en caso contrario, se creará un nuevo documento cuyo fichero se genera desde una plantilla
					String sParameter = request.getParameter("documentId");
					
					if (StringUtils.isEmpty(sParameter)) {
						
						// Crear el documento
						switch (state) {
						
							case ManagerState.EXPEDIENT: 
							case ManagerState.PROCESSESLIST: 
								entityDocument = genDocAPI.createStageDocument(currentId, documentTypeId);
								break;
								
							case ManagerState.SUBPROCESS :
							case ManagerState.SUBPROCESSESLIST:
								entityDocument = genDocAPI.createActivityDocument(currentId, taskId, taskPcdId, documentTypeId);
								break;
								
							case ManagerState.TASK :
								entityDocument = genDocAPI.createTaskDocument(currentId, documentTypeId);
								break;
								
							default :						
								return null;
						}
		
						documentId = entityDocument.getKeyInt();
					}
					else {
						// Sustituir el fichero del documento
						documentId = Integer.parseInt(sParameter);
					}
					
					// Generar el documento a partir la plantilla seleccionada
					IItem entityTemplate = null;
		
					switch (state) {
					
						case ManagerState.EXPEDIENT :
						case ManagerState.PROCESSESLIST:
							entityTemplate = genDocAPI.attachStageTemplate(connectorSession, currentId, documentId, templateId);
							break;
							
						case ManagerState.SUBPROCESS :
						case ManagerState.SUBPROCESSESLIST :
							entityTemplate = genDocAPI.attachActivityTemplate(connectorSession, currentId, taskId, taskPcdId, documentId, templateId);
							break;
							
						case ManagerState.TASK :
							entityTemplate = genDocAPI.attachTaskTemplate(connectorSession, currentId, documentId, templateId);
							break;
							
						default :					
							return null;
					}
							
					// Referencia al fichero del documento en el gestor documental
					String docref = entityTemplate.getString("INFOPAG");
					String sMimetype = genDocAPI.getMimeType(connectorSession, docref);
					entityTemplate.set("EXTENSION", 
							MimetypeMapping.getExtension(sMimetype));
					entityTemplate.store(cct);
					
					//Si solo se genera un documento se muestra
					if (ids.length == 1){
						DocumentUtil.viewDocument(getServlet().getServletContext(),
												  request,
												  response,
												  "storage",
												  session,
												  String.valueOf(documentId),
												  docref,
												  sMimetype,
												  null,
												  false, true);
					}
		
					// Si todo ha sido correcto se hace commit de la transacción
					bCommit = true;
				}
				catch (Throwable e) {
					
					// Si se produce algún error
					// el nuevo documento se borra para que no haya documentos sin ficheros
					/*
					if (entityDocument != null) {
						entityDocument.delete(cct);
					}
					*/
					String message = "exception.documents.generate";
					String extraInfo = null;
					if (e instanceof ISPACException) {
						if (e.getCause() instanceof NoConnectException) {
							extraInfo = "exception.extrainfo.documents.openoffice.off"; 
						}else if (e.getCause() instanceof DisposedException) {
							extraInfo = "exception.extrainfo.documents.openoffice.stop"; 
						}else if (e.getCause() != null) {
							extraInfo = e.getCause().getMessage();
						}else {
							extraInfo = ((ISPACException)e).getExtendedMessage(request.getLocale());
						}
					}else if (e instanceof DisposedException) {
						//message = message + " - El servicio de OpenOffice ha sido parado";
						extraInfo = "exception.extrainfo.documents.openoffice.stop";
					}else {
						//message = message + " - " + e.getMessage();
						extraInfo = e.getMessage();
					}			
					switch (state) {
					case ManagerState.PROCESSESLIST :
					case ManagerState.SUBPROCESSESLIST:
						IStage stage = cct.getAPI().getStage(currentId);
						errors.add(stage.getString("NUMEXP") + "<br/>("+  extraInfo+")");						
						break;
					default:
						logger.error(message + " >>" +  extraInfo);
						throw new ISPACInfo(message, extraInfo,false);
					}
					continue;
				}
				finally {
					cct.endTX(bCommit);
				}
				
				if (state== ManagerState.PROCESSESLIST || state == ManagerState.SUBPROCESSESLIST) {
					IStage stage = cct.getAPI().getStage(currentId);
					oks.add(stage.getString("NUMEXP"));						
				}			
			}
		}
		finally {
			if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
    	}
		
		if (state== ManagerState.PROCESSESLIST || state == ManagerState.SUBPROCESSESLIST) {
			request.setAttribute(ActionsConstants.OK_UPLOAD_FILES_LIST, oks);
			request.setAttribute(ActionsConstants.ERROR_UPLOAD_FILES_LIST, errors);
			return mapping.findForward("summary");
		}
		
		return null;
	}
	
}