package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACVariable;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.UploadForm;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispactx.TXProcedure;
import ieci.tdw.ispac.ispactx.TXProcedureMgr;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class UploadFileAction extends BaseAction {

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
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IGenDocAPI genDocAPI = invesFlowAPI.getGenDocAPI();

       //Si en la sesion tenemos informado el idTask quiere decir que tenemos que crear el tramite y asociar
        // el documento a crear a ese tramite
        String idTask=(String) request.getSession().getAttribute("idTaskPcd");
    	int state = currentState.getState();
		int stageId = currentState.getStageId();
		int taskId = currentState.getTaskId();

		int activityId = currentState.getActivityId();

		String docEntidad=(String) request.getSession().getAttribute("docEntidad");

		int entityId, regId;
		entityId=regId=0;
		if(StringUtils.isNotEmpty(docEntidad)){
			entityId=currentState.getEntityId();
			regId=currentState.getEntityRegId();
		}
		request.getSession().removeAttribute("docEntidad");

		int pcdId=currentState.getPcdId();
		int taskPcdId = currentState.getTaskPcdId();

		// Obtener el fichero a subir
		UploadForm uploadForm = (UploadForm) form;
		FormFile formFile = uploadForm.getTheFile();

		// Comprobar si ha llegado el fichero
		if (formFile == null) {
			throw new ISPACInfo(getResources(request).getMessage("exception.message.errorAttach"), false);
		}

		// Comprobar si el fichero tiene contenido
		if (formFile.getFileSize() == 0) {
			throw new ISPACInfo(getResources(request).getMessage("exception.message.attachFileEmpty"), false);
		}

		// Validar la extensión del fichero
		// String sFile = formFile.getFileName();
		// Obtener el nombre del fichero del campo oculto
		// evitando la mala codificación del nombre en el FormFile
		String sFileName = uploadForm.getTheFileName();
		String sExtension = FileUtils.getFileExtension(sFileName);
		String sMimeType = MimetypeMapping.getMimeType(sExtension);

		if (sMimeType == null) {

			//sMimeType = formFile.getContentType();
		    //throw new ISPACInfo("Tipo de fichero no permitido");
		    throw new ISPACInfo(getResources(request).getMessage("exception.message.typeNotAttach"),false);
		}



		int keyId = 0;

		IItem entityDocument = null;

		String ids[] = null;
		List oks = new ArrayList();
		List errors = new ArrayList();
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


		for (int i = 0; i < ids.length; i++) {

	int currentId = Integer.parseInt(ids[i]);

			// Ejecución en un contexto transaccional
			boolean bCommit = false;

			try {
		        // Abrir transacción para que no se pueda generar un documento sin fichero
		        cct.beginTX();
				String sParameter = request.getParameter("documentTypeId");

				if (sParameter != null) {

					// Crear el documento del tipo recibido
					int documentTypeId = Integer.parseInt(sParameter);

					switch (state) {

						case ManagerState.EXPEDIENT :
						case ManagerState.PROCESSESLIST:
							//Si recibmos el parametro idTask entonces creamos el tramite
							if(StringUtils.isNotBlank(idTask)){
								IInvesflowAPI invesflowAPI = session.getAPI();
						    		ITXTransaction tx = invesflowAPI.getTransactionAPI();
						    		TXProcedure procedure = TXProcedureMgr.getInstance().getProcedure(cct, pcdId);
						    		IItem taskPcd = procedure.getTask(Integer.parseInt(idTask));
						    		String idSubProcess = taskPcd.getString("ID_PCD_SUB");

						    		//Comprobamos si el tramite a crear es simple o complejo
						    		if (StringUtils.isNotEmpty(idSubProcess) && !StringUtils.equals(idSubProcess, "0") ){
						    			//creacion en el subproceso
						    			taskPcdId= tx.createTask(pcdId, stageId, Integer.parseInt(idTask), currentState.getNumexp());

						    		}
						    		else {
						    			//creacion de tramite normal
						    			taskPcdId=tx.createTask(stageId, Integer.parseInt(idTask));
						    		}

								entityDocument = genDocAPI.createTaskDocument(taskPcdId, documentTypeId, entityId, regId);
							}
							else{
							entityDocument = genDocAPI.createStageDocument(currentId, documentTypeId, entityId,regId);
							}
							break;

						case ManagerState.SUBPROCESS :
						case ManagerState.SUBPROCESSESLIST:
							entityDocument = genDocAPI.createActivityDocument(currentId, taskId, taskPcdId, documentTypeId , entityId, regId);
							break;

						case ManagerState.TASK :
							entityDocument = genDocAPI.createTaskDocument(currentId, documentTypeId, entityId, regId);
							break;

						default :
							return null;
					}

					keyId = entityDocument.getKeyInt();
				}
				else {
					// Sustituir el fichero del documento
					keyId = currentState.getEntityRegId();

					// Al sustituir anexando
					// obtener el documento para establecer la nueva extensión
					entityDocument = entitiesAPI.getDocument(keyId);
				}

				// Establecer la extensión del documento para mostrar
				// un icono descriptivo del tipo de documento en la lista de documentos
				entityDocument.set("EXTENSION", sExtension);
				entityDocument.store(cct);

				// Fichero a anexar
				InputStream in = formFile.getInputStream();
				int length = formFile.getFileSize();

				Object connectorSession = null;
				try {
					connectorSession = genDocAPI.createConnectorSession();

					switch (state) {

						case ManagerState.EXPEDIENT :
						case ManagerState.PROCESSESLIST:
							genDocAPI.attachStageInputStream(connectorSession, currentId, keyId, in, length, sMimeType, sFileName);
							break;

						case ManagerState.SUBPROCESS:
						case ManagerState.SUBPROCESSESLIST:
							genDocAPI.attachStageInputStream(connectorSession, currentId, keyId, in, length, sMimeType, sFileName);
							break;

						case ManagerState.TASK :
							genDocAPI.attachTaskInputStream(connectorSession, currentId, keyId, in, length, sMimeType, sFileName);
							break;

						default :
							return null;
					}
				}
				finally {
					if (connectorSession != null) {
						genDocAPI.closeConnectorSession(connectorSession);
					}
		    	}

				// Si todo ha sido correcto se hace commit de la transacción
				bCommit = true;
			}
			catch (Exception e) {

				// Si se produce algún error
				// el nuevo documento se borra para que no haya documentos sin ficheros
				/*
				if (entityDocument != null) {
					entityDocument.delete(cct);
				}
				*/
				switch (state) {
					case ManagerState.PROCESSESLIST :
					case ManagerState.SUBPROCESSESLIST:
						IStage stage = cct.getAPI().getStage(currentId);
						errors.add(stage.getString("NUMEXP"));
						break;
					default:
						throw new ISPACInfo(getResources(request).getMessage("exception.message.canNotAttach")+ ": " + e.getLocalizedMessage(), false);
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
		if (state== ManagerState.PROCESSESLIST || state == ManagerState.SUBPROCESSESLIST) {
			request.setAttribute(ActionsConstants.OK_UPLOAD_FILES_LIST, oks);
			request.setAttribute(ActionsConstants.ERROR_UPLOAD_FILES_LIST, errors);
		}

		request.getSession().removeAttribute("idTaskPcd");
		return mapping.findForward("success");
	}

}
