package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacmgr.action.form.EntityDocumentsForm;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EntityDocumentsAction extends BaseAction
{
	public ActionForward executeAction(
	ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response,
	SessionAPI session)
	throws Exception {

		// Contexto de tramitación
		ClientContext cct = session.getClientContext();
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState state = managerAPI.currentState(getStateticket(request));

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IGenDocAPI documentsAPI = invesFlowAPI.getGenDocAPI();

		EntityDocumentsForm documentsForm = (EntityDocumentsForm) form;

		int procedureId = state.getPcdId();
		int stageId = state.getStageId();
		int taskPcdId = state.getTaskPcdId();
		int taskId = state.getTaskId();
		int activityId = state.getActivityId();
		String expedient = state.getNumexp();

		IItemCollection entities = null;
		IItemCollection elements = null;
		IItemCollection templates = null;

		String sParameter;

		int entityId = ISPACEntities.DT_ID_INTERVINIENTES;

		sParameter = documentsForm.getEntity();

		if (sParameter != null)
		{
			entityId = Integer.parseInt( sParameter);
		}

		int documentId = 0;

		sParameter = documentsForm.getDocumentId();

		if (sParameter != null)
		{
			documentId = Integer.parseInt( sParameter);
		}

		int templateId = 0;

		sParameter = documentsForm.getTemplate();

		if (sParameter != null)
		{
			templateId = Integer.parseInt( sParameter);
		}

		sParameter = documentsForm.getSave();

		if (sParameter == null)
		{
			sParameter = documentsForm.getPrint();
			if (sParameter == null)
				documentsForm.setSave( "true");
		}

		sParameter = documentsForm.getAction();

		if (sParameter != null  && sParameter.equalsIgnoreCase("edit") )
		{
			String file = documentsForm.getFile();

			if (file == null || file.length() == 0)
			{
				String sTemplateName = documentsAPI.getTemporaryTemplate( templateId);
			  	documentsForm.setFile( sTemplateName);
			}
		}

		switch(state.getState())
		{
			case ManagerState.EXPEDIENT :
				
			    //[ildfns] No se utilizan
				// Entidades asociadas a la fase
			    //entities = entitiesAPI.getStageEntities(procedureId, stagePcdId);

			    //[ildfns] Añadimos un atributo para que no aparezcan los checkbox de salvar e imprimir
			    //que se seleccione imprimir, como campo oculto.
			    request.setAttribute("onlyPrint","true");			    
			    
				// Plantillas asociadas al tipo de documento en la fase
				templates = documentsAPI.getTemplatesInStage(documentId,stageId);
				// Datos de los registros de la entidad
				if (entityId != ISPACEntities.DT_ID_DOCUMENTOS)
				{
					elements = entitiesAPI.getSchemeEntities(entityId,expedient);
				}
				break;
			case ManagerState.SUBPROCESS :
				
			    //[ildfns] No se utilizan
				// Entidades asociadas a la fase
			    //entities = entitiesAPI.getStageEntities(procedureId, stagePcdId);

			    //[ildfns] Añadimos un atributo para que no aparezcan los checkbox de salvar e imprimir
			    //que se seleccione imprimir, como campo oculto.
			    request.setAttribute("onlyPrint","true");			    
			    
				// Plantillas asociadas al tipo de documento en la actividad
				templates = documentsAPI.getTemplatesInStage(documentId,activityId);

				break;
			case ManagerState.TASK :
				// Entidades asociadas a la tarea
				entities = entitiesAPI.getTaskEntities(procedureId, taskPcdId);
				// Plantillas asociadas al tipo de documento en la tarea
				templates = documentsAPI.getTemplatesInTask(documentId,taskId);
				// Datos de los registros de la entidad
				if (entityId != ISPACEntities.DT_ID_DOCUMENTOS)
				{
					elements = entitiesAPI.getSchemeEntities(entityId,expedient);
				}
				break;
			case ManagerState.PROCESSESLIST :
			{
				String []stages = documentsForm.getMultibox();

				if (stages != null)
				{
					request.setAttribute("multibox",stages);

					int []stagesId = new int[stages.length];
					for (int i = 0; i < stages.length; i++)
					{
						stagesId[i] = Integer.parseInt(stages[i]);
					}

					templates = documentsAPI.getTemplatesInStages(documentId,stagesId);
				}
				break;
			}
			case ManagerState.BATCHTASK:
			case ManagerState.TASKSLIST :
			{
				String []tasks = documentsForm.getMultibox();

				if (tasks != null)
				{
					request.setAttribute("multibox",tasks);

					int []tasksId = new int[tasks.length];
					for (int i = 0; i < tasks.length; i++)
					{
						tasksId[i] = Integer.parseInt(tasks[i]);
					}

					templates = documentsAPI.getTemplatesInTasks(documentId,tasksId);
				}
				break;
			}
			default :
				return null;
		}

		// Entidades relacionadas

		List list;

		if (entities != null)
		{
			ArrayList array = new ArrayList();

			Iterator iterator = entities.iterator();

			while (iterator.hasNext())
			{
				IItem item = (IItem) iterator.next();
				int entity = item.getInt("ID_ENT");

				if (entity != ISPACEntities.DT_ID_DOCUMENTOS)
				{
					ItemBean entityBean = new ItemBean(entitiesAPI.getCatalogEntity(entity));
					array.add(entityBean);
				}
			}

			request.setAttribute("EntitiesList", array);

				list = CollectionBean.getBeanList(elements);
				request.setAttribute("ElementsEntity",list);
		}

		// Plantillas asociadas al tipo de documento
		if (templates != null)
		{
			list = CollectionBean.getBeanList(templates);
			request.setAttribute("DocumentTemplates",list);
		}

		// Tipo de documento
		ItemBean itemBean = new ItemBean(documentsAPI.getDocumentType(documentId));
		request.setAttribute("DocumentType",itemBean);

		return mapping.findForward("success");
	}
}
