package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacmgr.action.form.DocumentsForm;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowDocumentsAction extends BaseAction
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
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState state = managerAPI.currentState(getStateticket(request));

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();

		DocumentsForm documentsForm = (DocumentsForm) form;
	
		List documentList = new ArrayList();

		String sParameter;

		int documentId = 0;

		sParameter = documentsForm.getDocumentId();
		
		if (sParameter != null)
		{
			documentId = Integer.parseInt( sParameter);
		}
		
	    // Contexto de la cabecera
		IItemCollection collection;
		
		//Formateador
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/documentslistformatter.xml"));
		request.setAttribute("Formatter", formatter);

//	    MessageResources messages = this.getResources( request);
	    
	    switch(state.getState())
		{
			case ManagerState.PROCESSESLIST :
			{
			    String []stages = documentsForm.getMultibox();
				
				if (stages != null)
				{
					for (int i = 0; i < stages.length; i++)
					{
						int stageId = Integer.parseInt(stages[i]);
						String expedient = invesFlowAPI.getStage(stageId).getString("NUMEXP");
						collection = entitiesAPI.getSchemeStageDocuments(expedient,stageId,documentId);
						documentList.addAll( CollectionBean.getBeanList(collection));
					}
				}
				break;
			}
			case ManagerState.TASKSLIST :
			{
				String []tasks = documentsForm.getMultibox();
				
				if (tasks != null)
				{
					for (int i = 0; i < tasks.length; i++)
					{
						int taskId = Integer.parseInt(tasks[i]);
						String expedient = invesFlowAPI.getTask(taskId).getString("NUMEXP");
						collection = entitiesAPI.getSchemeTaskDocuments(expedient,taskId,documentId);
						documentList.addAll( CollectionBean.getBeanList(collection));
					}
				}
				break;
			}
			default :
				return null;
		}

	    request.setAttribute("DocumentsList", documentList);
	    
		return mapping.findForward("success");
	}
}
