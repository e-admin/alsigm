package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowComponentsUseListAction extends BaseAction
{
 	public ActionForward executeAction(
 	ActionMapping mapping,
 	ActionForm form,
 	HttpServletRequest request,
 	HttpServletResponse response,
 	SessionAPI session)
 	throws Exception
	{
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();
        IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

		// Formulario asociado a la acción
		EntityForm defaultForm = (EntityForm) form;

		// Entidad
		String entity = defaultForm.getEntity();
		if (StringUtils.isBlank(entity)) {
			entity = request.getParameter("entityId");
		}
		int entityId = Integer.parseInt(entity);

		// Registro de la entidad
		String key = defaultForm.getKey();
		if (StringUtils.isBlank(key)) {
			key = request.getParameter("regId");

		}
		int keyId = Integer.parseInt(key);

		IItem item = catalogAPI.getCTEntity( entityId, keyId);

        IItemCollection collection = null;
        List list = null;
        BeanFormatter formatter = null;
        String action = null;
	    String title = null;
	    String argument = null;
	    boolean inUse = false;

   	 	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
	    //MessageResources messages = this.getResources( request);

		switch (entityId)
		{
			case ICatalogAPI.ENTITY_CT_ENTITY :
			{
				action = new StringBuffer("/showCTEntityToManage.do?method=init&entityId=")
					.append(entityId).append("&regId=").append(keyId)
					.toString();

				ItemBean entityBean = (ItemBean) request.getSession().getAttribute(ShowCTEntityToManageAction.ENTITY_BEAN_KEY);
				if (entityBean != null) {
					argument = entityBean.getString("ETIQUETA");
				}
				else if (StringUtils.isNotBlank(item.getString("DESCRIPCION"))) {
					argument = item.getString("DESCRIPCION");
				}
				else {
					argument = item.getString("NOMBRE");
				}

				collection = procedureAPI.getEntityProceduresUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmproceduresformatter.xml"));
					request.setAttribute("proceduresformatter", formatter);
		        	request.setAttribute("procedures", list);
		        	inUse = true;
		        }

		        /*
				collection = procedureAPI.getEntityStagesUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmstagesformatter.xml"));
					request.setAttribute("stagesformatter", formatter);
		        	request.setAttribute("stages", list);
		        	inUse = true;
		        }
				collection = procedureAPI.getEntityTasksUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmtasksformatter.xml"));
					request.setAttribute("tasksformatter", formatter);
		        	request.setAttribute("tasks", list);
		        	inUse = true;
		        }
		        */

	        	title = (inUse ? "component.entity.title" : "component.entity.title.notInUse");

				break;
			}
			case ICatalogAPI.ENTITY_CT_APP :
			{
				//action = "/showCTAplicationsList.do";
				/*
				action = new StringBuffer("/showCTEntity.do?entityId=")
					.append(entityId).append("&regId=").append(keyId)
					.toString();
				*/
				action = new StringBuffer("/showCTEntityToManage.do?method=form")
					.append("&regId=").append(keyId)
					.toString();
				argument = item.getString("NOMBRE");

				collection = procedureAPI.getApplicationEntitiesUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmentitiesformatter.xml"));
					request.setAttribute("entitiesformatter", formatter);
		        	request.setAttribute("entities", list);
		        	inUse = true;
		        }
				collection = procedureAPI.getApplicationProceduresUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmproceduresformatter.xml"));
					request.setAttribute("proceduresformatter", formatter);
		        	request.setAttribute("procedures", list);
		        	inUse = true;
		        }
				collection = procedureAPI.getApplicationStagesUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmstagesformatter.xml"));
					request.setAttribute("stagesformatter", formatter);
		        	request.setAttribute("stages", list);
		        	inUse = true;
		        }
				collection = procedureAPI.getApplicationTasksUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmtasksformatter.xml"));
					request.setAttribute("tasksformatter", formatter);
		        	request.setAttribute("tasks", list);
		        	inUse = true;
		        }

				title = (inUse ? "component.form.title" : "component.form.title.notInUse");

		        break;
			}
			case ICatalogAPI.ENTITY_CT_RULE :
			{
				//action = "/showCTRulesList.do";
				action = new StringBuffer("/showCTEntity.do?entityId=")
					.append(entityId).append("&regId=").append(keyId)
					.toString();
				argument = item.getString("NOMBRE");

				collection = procedureAPI.getRuleProceduresUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmproceduresformatter.xml"));
					request.setAttribute("proceduresformatter", formatter);
		        	request.setAttribute("procedures", list);
		        	inUse = true;
		        }
				collection = procedureAPI.getRuleStagesUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmstagesformatter.xml"));
					request.setAttribute("stagesformatter", formatter);
		        	request.setAttribute("stages", list);
		        	inUse = true;
		        }
				collection = procedureAPI.getRuleTasksUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmtasksformatter.xml"));
					request.setAttribute("tasksformatter", formatter);
		        	request.setAttribute("tasks", list);
		        	inUse = true;
		        }
				collection = procedureAPI.getRuleFlowsUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmflowsformatter.xml"));
					request.setAttribute("flowsformatter", formatter);
		        	request.setAttribute("flows", list);
		        	inUse = true;
		        }
				collection = procedureAPI.getRuleProcedureEntitiesUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmprocedureentitiesformatter.xml"));
					request.setAttribute("procedureentitiesformatter", formatter);
		        	request.setAttribute("procedureentities", list);
		        	inUse = true;
		        }
				collection = procedureAPI.getRuleProcedureEntityFormsUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmprocedureentitiesformatter.xml"));
					request.setAttribute("procedureentityformsformatter", formatter);
		        	request.setAttribute("procedureentityforms", list);
		        	inUse = true;
		        }
				collection = procedureAPI.getRuleStageEntityFormsUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmstageentityformsformatter.xml"));
					request.setAttribute("stageentityformsformatter", formatter);
		        	request.setAttribute("stageentityforms", list);
		        	inUse = true;
		        }
				collection = procedureAPI.getRuleTaskEntityFormsUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmtaskentityformsformatter.xml"));
					request.setAttribute("taskentityformsformatter", formatter);
		        	request.setAttribute("taskentityforms", list);
		        	inUse = true;
		        }
				collection = procedureAPI.getRuleProcedureEntityVisibleUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmprocedureentitiesformatter.xml"));
					request.setAttribute("procedureentityvisibleformatter", formatter);
		        	request.setAttribute("procedureentityvisible", list);
		        	inUse = true;
		        }
				collection = procedureAPI.getRuleStageEntityVisibleUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmstageentityformsformatter.xml"));
					request.setAttribute("stageentityvisibleformatter", formatter);
		        	request.setAttribute("stageentityvisible", list);
		        	inUse = true;
		        }
				collection = procedureAPI.getRuleTaskEntityVisibleUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmtaskentityformsformatter.xml"));
					request.setAttribute("taskentityvisibleformatter", formatter);
		        	request.setAttribute("taskentityvisible", list);
		        	inUse = true;
		        }

				title = (inUse ? "component.rule.title" : "component.rule.title.notInUse");

				break;
			}
			case ICatalogAPI.ENTITY_CT_PROCEDURE :
			{
				action = "/showCTProceduresList.do";
				argument = item.getString("NOMBRE");

				collection = procedureAPI.getApplicationEntitiesUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmproceduresformatter.xml"));
					request.setAttribute("proceduresformatter", formatter);
					request.setAttribute("procedures", list);
		        	inUse = true;
		        }

				title = (inUse ? "component.procedure.title" : "component.procedure.title.notInUse");

				break;
			}
			case ICatalogAPI.ENTITY_CT_STAGE :
			{
				//action = "/showCTStagesList.do";
				action = new StringBuffer("/showCTStage.do?entityId=")
					.append(entityId).append("&regId=").append(keyId)
					.toString();

				argument = item.getString("NOMBRE");

				collection = procedureAPI.getStageUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmproceduresformatter.xml"));
					request.setAttribute("proceduresformatter", formatter);
					request.setAttribute("procedures", list);
		        	inUse = true;
		        }

				title = (inUse ? "component.stage.title" : "component.stage.title.notInUse");

				break;
			}
			case ICatalogAPI.ENTITY_CT_TASK :
			{
				//action = "/showCTTasksList.do";
				action = new StringBuffer("/showCTTask.do?entityId=")
					.append(entityId).append("&regId=").append(keyId)
					.toString();

				argument = item.getString("NOMBRE");

				// para mostrar las fases
				collection = procedureAPI.getStagesTaskUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
		        	formatter = factory.getFormatter(getISPACPath("/formatters/cmstagestaskformatter.xml"));
					request.setAttribute("stagesformatter", formatter);
					request.setAttribute("stages", list);
		        	inUse = true;
		        }

		        // para mostrar los procedimientos
				collection = procedureAPI.getTaskUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmstagesformatter.xml"));
					request.setAttribute("proceduresformatter", formatter);
					request.setAttribute("procedures", list);
		        	inUse = true;
		        }

				title = (inUse ? "component.task.title" : "component.task.title.notInUse");

				break;
			}
			case ICatalogAPI.ENTITY_CT_TYPEDOC :
			{
				//action = "/showCTTPDocsList.do";
				action = new StringBuffer("/showCTEntity.do?entityId=")
					.append(entityId).append("&regId=").append(keyId)
					.toString();

				argument = item.getString("NOMBRE");

				// Fases que utilizan el tipo de documento
				collection = procedureAPI.getDocumentStagesUse(keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmstagesformatter.xml"));
					request.setAttribute("stagesformatter", formatter);
					request.setAttribute("stages", list);
		        	inUse = true;
		        }

				// Trámites que utilizan el tipo de documento
		        collection = procedureAPI.getDocumentTasksUse(keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmcttasksformatter.xml"));
					request.setAttribute("cttasksformatter", formatter);
					request.setAttribute("cttasks", list);
		        	inUse = true;
		        }

				// Actividades que utilizan el tipo de documento
				collection = procedureAPI.getDocumentActivitiesUse(keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmactivitiesformatter.xml"));
					request.setAttribute("activitiesformatter", formatter);
					request.setAttribute("activities", list);
		        	inUse = true;
		        }

				title = (inUse ? "component.document.title" : "component.document.title.notInUse");

				break;
			}
			case ICatalogAPI.ENTITY_SIGNPROCESS_HEADER :
			{
				//action = "/showCTStagesList.do";
				action = new StringBuffer("/showCTEntity.do?entityId=")
					.append(entityId).append("&regId=").append(keyId)
					.toString();

				argument = item.getString("DESCRIPCION");

				collection = procedureAPI.getSignProcessUse( keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmproceduresformatter.xml"));
					request.setAttribute("proceduresformatter", formatter);
					request.setAttribute("procedures", list);
		        	inUse = true;
		        }

		        collection= procedureAPI.getSignProcessInUse(keyId);
		        list = CollectionBean.getBeanList(collection);
		     	if (!list.isEmpty())
		     	{
		     		formatter = factory.getFormatter(getISPACPath("/formatters/signprocess/circuitsInstanceformatter.xml"));
		     		request.setAttribute("circuitsIntanceformatter", formatter);
		     		request.setAttribute("circuits", list);
		     		inUse = true;
		     	}
				title = (inUse ? "component.signprocess.title" : "component.signprocess.title.notInUse");

				break;
			}

			case ICatalogAPI.ENTITY_CT_INFORMES:
			{
				action = new StringBuffer("/showCTEntityUp.do?entityId=")
					.append(entityId).append("&regId=").append(keyId)
					.toString();

				argument = item.getString("NOMBRE");

				// Procedimientos relacionados con el informe
				collection = procedureAPI.getReportProceduresUse(keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmproceduresformatter.xml"));
					request.setAttribute("proceduresformatter", formatter);
					request.setAttribute("procedures", list);
		        	inUse = true;
		        }

				// Fases relacionadas con el informe
				collection = procedureAPI.getReportStagesUse(keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmstagesformatter.xml"));
					request.setAttribute("stagesformatter", formatter);
					request.setAttribute("stages", list);
		        	inUse = true;
		        }

				// Trámites relacionados con el informe
				collection = procedureAPI.getReportTasksUse(keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmtasksformatter.xml"));
					request.setAttribute("tasksformatter", formatter);
		        	request.setAttribute("tasks", list);
		        	inUse = true;
		        }

				// Actividades relacionadas con el informe
				collection = procedureAPI.getReportActivitiesUse(keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmactivitiesformatter.xml"));
					request.setAttribute("activitiesformatter", formatter);
					request.setAttribute("activities", list);
		        	inUse = true;
		        }

		        //Formularios de busqueda relacionadas con el informe
		        collection = procedureAPI.getReportSearchForm(keyId);
		        list = CollectionBean.getBeanList(collection);
		        if (!list.isEmpty())
		        {
					formatter = factory.getFormatter(getISPACPath("/formatters/cmsearchformformatter.xml"));
					request.setAttribute("searchformformatter", formatter);
					request.setAttribute("searchforms", list);
		        	inUse = true;
		        }

				title = (inUse ? "component.report.title" : "component.report.title.notInUse");

				break;
			}
		}

		// Título
		request.setAttribute("title", title);
		request.setAttribute("argument", argument);

		// Acción de vuelta
		request.setAttribute("action", request.getContextPath() + action);
   	 	return mapping.findForward("success");
	}
}