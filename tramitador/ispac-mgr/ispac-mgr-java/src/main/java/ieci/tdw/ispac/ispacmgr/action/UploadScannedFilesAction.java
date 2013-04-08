package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACVariable;
import ieci.tdw.ispac.api.ITXTransaction;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispactx.TXProcedure;
import ieci.tdw.ispac.ispactx.TXProcedureMgr;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

public class UploadScannedFilesAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception	{

		String docEntidad=(String) request.getSession().getAttribute("docEntidad");
		boolean adjuntarEntidad=false;
		if(StringUtils.isNotEmpty(docEntidad)){
			adjuntarEntidad=true;
		}
		request.getSession().removeAttribute("docEntidad");

		if (FileUpload.isMultipartContent(request)) {

			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(FileTemporaryManager.getInstance()
					.getFileTemporaryPath());

			List fileItems = fileUpload.parseRequest(request);
			Properties parameters = new Properties();
			List files = new ArrayList();

			if (fileItems != null) {

				FileItem fileItem;
				for (int i = 0; i < fileItems.size(); i++) {
					fileItem = (FileItem) fileItems.get(i);
					if (fileItem.isFormField()) {

						// Procesar el parámetro
						parameters.setProperty(fileItem.getFieldName(),
								fileItem.getString());

					} else {

						// Procesar el fichero
						files.add(fileItem);
					}
				}
			}

	        ClientContext cct = session.getClientContext();

			// Se obtiene el estado de tramitación
	        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
	   	    IState currentState = managerAPI.currentState(getStateticket(request));
	   	    int state = currentState.getState();
	   	    MessageResources resources = getResources(request);
			List oks = new ArrayList();
			List errors = new ArrayList();

	   	    // Identificador del tipo de documento
			int docTypeId = TypeConverter.parseInt(
					parameters.getProperty("documentTypeId"), -1);
			int idTaskPcd=0;
			if(StringUtils.isNotEmpty((String) request.getSession().getAttribute("idTaskPcd"))){
				idTaskPcd=Integer.parseInt((String) request.getSession().getAttribute("idTaskPcd"));
			}
			if (docTypeId > -1) {


				// Añadir los documentos
		        for (int i = 0; i < files.size(); i++) {
		        	FileItem fileItem = (FileItem) files.get(i);

					// Añadir el documento
		        	idTaskPcd=uploadFile(session, currentState, resources, fileItem, docTypeId, oks, errors, i+1,  idTaskPcd, true, adjuntarEntidad);

					// Eliminar el fichero temporal
					if (fileItem != null) {
						fileItem.delete();
					}
		        }
			} else {

				// Actualizar con el primer documento
		        for (int i = 0; i < files.size(); i++) {
		        	FileItem fileItem = (FileItem) files.get(i);

		        	if (i == 0) {

		        		uploadFile(session, currentState, resources, fileItem, docTypeId, oks, errors, i+1,idTaskPcd, false, adjuntarEntidad);
		        	}

					// Eliminar el fichero temporal
					if (fileItem != null) {
						fileItem.delete();
					}
		        }
			}

			request.getSession().setAttribute(ActionsConstants.OK_SCANNED_FILES_LIST, oks);
			request.getSession().setAttribute(ActionsConstants.ERROR_SCANNED_FILES_LIST, errors);

			if (state== ManagerState.PROCESSESLIST || state == ManagerState.SUBPROCESSESLIST) {
				cct.deleteSsVariable(ISPACVariable.STAGES_ACTIVITES_DOCUMENTS_GENERATION);
				return mapping.findForward("success");
			} else {

				response.setContentType("text/plain");
				PrintWriter writer = response.getWriter();
				writer.write(getSummaryInfo(oks, errors));
				writer.flush();
				writer.close();
			}
		}

        return null;
	}

	private static String getSummaryInfo(List oks, List errors) {
		return new StringBuffer()
			.append("#\n")
			.append("#Resultado de la carga de documentos\n")
			.append("#\n")
			.append("oks=").append(getFileNames(oks)).append("\n")
			.append("errors=").append(getFileNames(errors)).append("\n")
			.toString();
	}

	protected static String getFileNames(List fileList) {

		StringBuffer names = new StringBuffer();

		if (!fileList.isEmpty()) {
			for (int i = 0; i < fileList.size(); i++) {
				if (i > 0) {
					names.append(",");
				}

				Map infoMap = (Map) fileList.get(i);
				if (infoMap != null) {
					String filename = (String) infoMap.get("FILE_NAME");
					if (StringUtils.isNotBlank(filename)) {
						names.append("\"").append(filename).append("\"");
					}
				}
			}
		}

		return names.toString();
	}

	protected int uploadFile(SessionAPI session, IState currentState,
				MessageResources resources, FileItem file, int docTypeId, List oks, List errors, int contador, int taskPcdId, boolean crear, boolean anexoEntidad)
			throws ISPACException {

		ClientContext cct = session.getClientContext();


		if (file == null) {
			//throw new ISPACInfo(resources.getMessage(cct.getLocale(), "exception.message.errorAttach"));
			Map infoMap = new HashMap();
			infoMap.put("FILE_NAME", file.getName());
			infoMap.put("ERROR_MESSAGE", resources.getMessage(cct.getLocale(), "exception.message.errorAttach"));
			errors.add(infoMap);
			return taskPcdId;
		}

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IGenDocAPI gendocAPI = invesFlowAPI.getGenDocAPI();

		// Validar la extensión del fichero
		String fileName = null;
		try {
			fileName = URLDecoder.decode(file.getFieldName(), "ISO-8859-15");
		} catch (UnsupportedEncodingException e) {
			logger.warn("Error al decodificar el nombre del fichero", e);
			fileName = file.getFieldName();
		}

		int index = fileName.lastIndexOf(".");
		String sExtension = fileName.substring(index + 1, fileName.length());

		String sMimeType = MimetypeMapping.getMimeType(sExtension);

		if (sMimeType == null) {
		    //throw new ISPACInfo(resources.getMessage(cct.getLocale(), "exception.message.typeNotAttach"));
			Map infoMap = new HashMap();
			infoMap.put("FILE_NAME", file.getName());
			infoMap.put("ERROR_MESSAGE", resources.getMessage(cct.getLocale(), "exception.message.typeNotAttach"));
			errors.add(infoMap);
			return taskPcdId;
		}

		int state = currentState.getState();
		int stageId = currentState.getStageId();
		int taskId = currentState.getTaskId();
		if(!crear && taskPcdId==0){
				taskPcdId= currentState.getTaskPcdId();
		}
		int regId, entidadId;
		regId=entidadId=0;
		if(anexoEntidad){
			regId=currentState.getEntityRegId();
			entidadId=currentState.getEntityId();
		}
		int activityId = currentState.getActivityId();
		int keyId = 0;

		IItem entityDocument = null;

		String ids[] = null;
		switch (state) {
			case ManagerState.PROCESSESLIST :
			case ManagerState.SUBPROCESSESLIST:
				String  idStr = cct.getSsVariable(ISPACVariable.STAGES_ACTIVITES_DOCUMENTS_GENERATION);
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
			default :
				return taskPcdId;
		}
		Object connectorSession = null;
		for (int i = 0; i < ids.length; i++) {

			int currentId = Integer.parseInt(ids[i]);

			// Ejecución en un contexto transaccional
			boolean bCommit = false;

			try {
				connectorSession = gendocAPI.createConnectorSession();

				// Abrir transacción para que no se pueda generar un documento sin fichero
		        cct.beginTX();

		        if (docTypeId > -1) {
					switch (state) {

						case ManagerState.EXPEDIENT :
						case ManagerState.PROCESSESLIST:
							if(taskPcdId!=0 && contador==1 && crear){
							 //Crear el tramite
								IInvesflowAPI invesflowAPI = session.getAPI();
								ITXTransaction tx = invesflowAPI.getTransactionAPI();
								int pcdId=currentState.getPcdId();
								TXProcedure procedure = TXProcedureMgr.getInstance().getProcedure(cct, pcdId);
								IItem taskPcd = procedure.getTask(taskPcdId);
								String idSubProcess = taskPcd.getString("ID_PCD_SUB");
								//Comprobamos si el tramite a crear es simple o complejo
								if (StringUtils.isNotEmpty(idSubProcess) && !StringUtils.equals(idSubProcess, "0") ){
									//creacion en el subproceso
									taskPcdId= tx.createTask(pcdId, stageId, taskPcdId, currentState.getNumexp());

								}
								else {
									//creacion de tramite normal
									taskPcdId=tx.createTask(stageId, taskPcdId);
								}
							}
							if(taskPcdId!=0){

								entityDocument = gendocAPI.createTaskDocument(taskPcdId, docTypeId, entidadId, regId);
							}
							else{
							entityDocument = gendocAPI.createStageDocument(currentId, docTypeId, entidadId, regId);
							}
							break;

						case ManagerState.SUBPROCESS :
						case ManagerState.SUBPROCESSESLIST:
							entityDocument = gendocAPI.createActivityDocument(currentId, taskId, taskPcdId, docTypeId, entidadId, regId);
							break;

						case ManagerState.TASK :
							entityDocument = gendocAPI.createTaskDocument(currentId, docTypeId, entidadId, regId);
							break;

						default :
							return taskPcdId;
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
				InputStream in = file.getInputStream();
				int length = new Long(file.getSize()).intValue();

				switch (state) {

					case ManagerState.EXPEDIENT :
					case ManagerState.PROCESSESLIST:
						//gendocAPI.attachStageInputStream(connectorSession, currentId, keyId, in, length, sMimeType, entityDocument.getString("NOMBRE"));
						gendocAPI.attachStageInputStream(connectorSession, currentId, keyId, in, length, sMimeType, fileName);
						break;

					case ManagerState.SUBPROCESS:
					case ManagerState.SUBPROCESSESLIST:
						//gendocAPI.attachStageInputStream(connectorSession, currentId,  keyId, in, length, sMimeType, entityDocument.getString("NOMBRE"));
						gendocAPI.attachStageInputStream(connectorSession, currentId,  keyId, in, length, sMimeType, fileName);
						break;

					case ManagerState.TASK :
						//gendocAPI.attachTaskInputStream(connectorSession, currentId, keyId, in, length, sMimeType, entityDocument.getString("NOMBRE"));
						gendocAPI.attachTaskInputStream(connectorSession, currentId, keyId, in, length, sMimeType, fileName);
						break;
				}

				// Si todo ha sido correcto se hace commit de la transacción
				bCommit = true;

			}
			catch (Exception e) {

				logger.error("Error al subir el fichero", e);

				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				e.printStackTrace(printWriter);
				printWriter.flush();
				printWriter.close();

				Map infoMap = new HashMap();
				infoMap.put("FILE_NAME", file.getName());
				infoMap.put("ERROR_MESSAGE", e.getLocalizedMessage());
				infoMap.put("ERROR_EXCEPTION", stringWriter.toString());
				infoMap.put("FILE_NUM", String.valueOf(contador));

				switch (state) {
					case ManagerState.PROCESSESLIST :
					case ManagerState.SUBPROCESSESLIST:
						IStage stage = cct.getAPI().getStage(currentId);
						infoMap.put("NUMEXP", stage.getString("NUMEXP"));

						break;
					default:
					break;
				}

				errors.add(infoMap);

				continue;
			}
			finally {
				cct.endTX(bCommit);

				if (connectorSession != null) {
					gendocAPI.closeConnectorSession(connectorSession);
				}
	    	}

			Map infoMap = new HashMap();
			infoMap.put("FILE_NAME", file.getName());
			infoMap.put("FILE_NUM", String.valueOf(contador));

			if (state== ManagerState.PROCESSESLIST || state == ManagerState.SUBPROCESSESLIST) {
				IStage stage = cct.getAPI().getStage(currentId);
				infoMap.put("NUMEXP", stage.getString("NUMEXP"));
			}

			oks.add(infoMap);
		}
		return taskPcdId;
	}

}