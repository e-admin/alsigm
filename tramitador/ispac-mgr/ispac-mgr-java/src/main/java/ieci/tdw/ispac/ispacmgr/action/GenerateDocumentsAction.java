package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACVariable;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTemplate;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.EntityDocumentsForm;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

public class GenerateDocumentsAction extends BaseAction
{

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response,
	SessionAPI session) throws Exception
	{
		MessageResources messages = this.getResources(request);

		// Contexto de tramitación
		ClientContext cct = session.getClientContext();
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState state = managerAPI.currentState(getStateticket(request));

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IGenDocAPI genDocAPI = invesFlowAPI.getGenDocAPI();

		EntityDocumentsForm documentsForm = (EntityDocumentsForm) form;
		IItem entity = null;
		IItemCollection templates = null;

		ArrayList list = new ArrayList();
		ArrayList messageList = new ArrayList();
		ArrayList errorList = new ArrayList();

		String sParameter = documentsForm.getDocumentId();

		if (sParameter == null)
		{
			return null;
		}

		int documentId = Integer.parseInt(sParameter);

		sParameter = documentsForm.getTemplate();

		if (sParameter == null)
		{
			return null;
		}

		int templateId = Integer.parseInt(sParameter);
		String extension = getTemplateExtension(session, templateId);

		String sFileTemplate = documentsForm.getFile();

		sParameter = documentsForm.getSave();

		boolean saveDocuments = false;

		if (sParameter != null)
		{
			saveDocuments = true;
		}

		sParameter = documentsForm.getPrint();

		boolean printDocuments = false;

		if (sParameter != null)
		{
			printDocuments = true;
		}

		int []stagesId = null;
		int []tasksId = null;
		int []keysId = null;

		// Entidad seleccionada
		sParameter = documentsForm.getEntity();

		int entityId = 0;

		if (sParameter != null)
		{
			entityId = Integer.parseInt( sParameter);
		}
		// Registros de la entidad seleccionada
		String[] key = documentsForm.getMultikey();
		// No hay entidad relacionada
		if (key == null)
		{
			entityId = 0;
			keysId = new int[1];
			keysId[0] = 0;
		}
		else
		{
			keysId = new int[key.length];
			for (int i = 0; i < key.length; i++)
				keysId[i] = Integer.parseInt(key[i]);
		}

		switch(state.getState())
		{
			case ManagerState.EXPEDIENT :
			case ManagerState.TASK :
			{
				stagesId = new int[1];
				stagesId[0] = state.getStageId();
				tasksId = new int[1];
				tasksId[0] = state.getTaskId();
				break;
			}
			case ManagerState.SUBPROCESS :
			{
				stagesId = new int[1];
				stagesId[0] = state.getActivityId();
				tasksId = new int[1];
				tasksId[0] = state.getTaskId();
				break;
			}
			case ManagerState.PROCESSESLIST :
			{
				entityId = state.getEntityId();
				keysId = new int[1];
				keysId[0] = state.getEntityRegId();

				String []stages = documentsForm.getMultibox();
				stagesId = new int[stages.length];
				for (int i = 0; i < stages.length; i++)
				{
					stagesId[i] = Integer.parseInt(stages[i]);
				}
				break;
			}
			case ManagerState.BATCHTASK:
			case ManagerState.TASKSLIST :
			{
				entityId = state.getEntityId();
				keysId = new int[1];
				keysId[0] = state.getEntityRegId();

				String []tasks = documentsForm.getMultibox();
				tasksId = new int[tasks.length];
				for (int i = 0; i < tasks.length; i++)
				{
					tasksId[i] = Integer.parseInt(tasks[i]);
				}
				break;
			}
		}

		// Añade la variable de sesión que indica que se están
		// generando los documentos en BATCH
		cct.setSsVariable( ISPACVariable.BATCH_DOCUMENT_GENERATION, "true");
		Object connectorSession = null;
		try {
			connectorSession = genDocAPI.createConnectorSession();

			switch(state.getState())
			{
				case ManagerState.EXPEDIENT :
				case ManagerState.PROCESSESLIST :
				{
					IItem templateEntity;
					for (int i = 0; i < stagesId.length; i++)
					{
						String expedient = state.getNumexp();
						if (StringUtils.isBlank(expedient)) {
							expedient = invesFlowAPI.getStage(stagesId[i]).getString("NUMEXP");
						}
	
						try
						{
							// Comprueba que la plantilla está asociada a la fase
							templates = genDocAPI.getTemplatesInStage(documentId,stagesId[i]);
							Map mapTemplates = templates.toMapStringKey();
							if (!mapTemplates.containsKey(String.valueOf(templateId)))
								continue;
							// Para cada registro de la entidad seleccionada
							for (int k = 0; k < keysId.length; k++)
							{
								if (saveDocuments)
								{
									entity = genDocAPI.createStageDocument(stagesId[i],documentId);
	
									list.add( new ItemBean(entity));
	
									templateEntity = genDocAPI.attachStageTemplate(connectorSession, stagesId[i],entity.getKeyInt(),templateId,sFileTemplate,entityId, keysId[k]);
									templateEntity.set("EXTENSION", extension);
									templateEntity.store(cct);
								}
								else
								{
									list.add( genDocAPI.createStageDocument(stagesId[i],documentId,templateId,sFileTemplate,entityId,keysId[k]));
								}
							}
	
							messageList.add( expedient);
						}
						catch(Exception e)
						{
						    errorList.add( messages.getMessage("error.document", expedient, e.getMessage()));
						}
	
					}
					break;
				}
				case ManagerState.SUBPROCESS :
				{
					IItem templateEntity;
					int taskId = state.getTaskId();
					// TODO taskPcdId a cero para detectar un documento a nivel de actividad
					// int taskPcdId = state.getTaskPcdId();
					int taskPcdId = 0;
					for (int i = 0; i < stagesId.length; i++)
					{
						String expedient = state.getNumexp();
						if (StringUtils.isBlank(expedient)) {
							expedient = invesFlowAPI.getStage(stagesId[i]).getString("NUMEXP");
						}
	
						try
						{
							// Comprueba que la plantilla está asociada a la actividad
							templates = genDocAPI.getTemplatesInStage(documentId,stagesId[i]);
							Map mapTemplates = templates.toMapStringKey();
							if (!mapTemplates.containsKey(String.valueOf(templateId)))
								continue;
							// Para cada registro de la entidad seleccionada
							for (int k = 0; k < keysId.length; k++)
							{
								if (saveDocuments)
								{
									entity = genDocAPI.createActivityDocument(stagesId[i], taskId, taskPcdId, documentId);
	
									list.add( new ItemBean(entity));
	
									templateEntity = genDocAPI.attachActivityTemplate(connectorSession, stagesId[i], taskId, taskPcdId, entity.getKeyInt(), templateId, sFileTemplate, entityId, keysId[k]);
									templateEntity.set("EXTENSION", extension);
									templateEntity.store(cct);
								}
								else
								{
									list.add( genDocAPI.createActivityDocument(stagesId[i], taskId, taskPcdId, documentId, templateId, sFileTemplate, entityId, keysId[k]));
								}
							}
	
							messageList.add( expedient);
						}
						catch(Exception e)
						{
						    errorList.add( messages.getMessage("error.document", expedient, e.getMessage()));
						}
	
					}
					break;
				}
				case ManagerState.BATCHTASK:
				case ManagerState.TASK :
				case ManagerState.TASKSLIST :
				{
					IItem templateEntity;
					for (int i = 0; i < tasksId.length; i++)
					{
						String expedient = state.getNumexp();
						if (StringUtils.isBlank(expedient)) {
							expedient = invesFlowAPI.getStage(stagesId[i]).getString("NUMEXP");
						}
	
						try
						{
							// Comprueba que la plantilla está asociada a la tarea
							templates = genDocAPI.getTemplatesInTask(documentId,tasksId[i]);
							Map mapTemplates = templates.toMapStringKey();
							if (!mapTemplates.containsKey(String.valueOf(templateId)))
								continue;
							// Para cada registro de la entidad seleccionada
							for (int k = 0; k < keysId.length; k++)
							{
								if (saveDocuments)
								{
									entity = genDocAPI.createTaskDocument(tasksId[i],documentId);
	
									list.add( new ItemBean(entity));
	
									templateEntity = genDocAPI.attachTaskTemplate(connectorSession, tasksId[i],entity.getKeyInt(),templateId, sFileTemplate,entityId, keysId[k]);
									templateEntity.set("EXTENSION", extension);
									templateEntity.store(cct);
								}
								else
								{
									list.add( genDocAPI.createTaskDocument(tasksId[i],documentId,templateId, sFileTemplate,entityId, keysId[k]));
								}
							}
	
							messageList.add( expedient);
						}
						catch(Exception e)
						{
						    errorList.add( messages.getMessage("error.document", expedient, e.getMessage()));
						}
					}
					break;
				}
			}
		}finally {
			if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
    	}				

		// Elimina la variable de sesión que indica que se están
		// generando los documentos en BATCH
		cct.deleteSsVariable( ISPACVariable.BATCH_DOCUMENT_GENERATION);

		// Borra el fichero intermedio que contiene la plantilla editada.
		if (sFileTemplate != null)
		{
			FileTemporaryManager temporaryManager = null;

			temporaryManager = FileTemporaryManager.getInstance();

			temporaryManager.delete(sFileTemplate);
		}

		if (printDocuments)
		{
			if (saveDocuments)
			{
				request.setAttribute("DocumentsList", list);
			}
			else
			{
				request.setAttribute("FilesList", list);
			}
		}

		request.setAttribute("MessageList", messageList);
		request.setAttribute("ErrorList", errorList);

		// Formateador
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		//Cambiamos el formateador a usar
		//BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/documentsformatter.xml"));
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/docsgeneratesformatter.xml"));
		request.setAttribute("Formatter", formatter);

		return mapping.findForward("success");
	}
	
	private String getTemplateExtension(SessionAPI session, int templateId) 
			throws ISPACException {
		String extension = null;
		
		ITemplateAPI templateAPI = session.getAPI().getTemplateAPI();
		CTTemplate template = templateAPI.getTemplate(templateId);
		if (template != null) {
			String mimetype = template.getMimetype();
			extension = MimetypeMapping.getExtension(mimetype);
		}
		
		return extension;
	}
}
