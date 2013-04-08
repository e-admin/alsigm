package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
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

public class SelectTemplateDocumentTypeAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping,
	    							   ActionForm form,
	    							   HttpServletRequest request,
	    							   HttpServletResponse response,
	    							   SessionAPI session) throws Exception {

        ClientContext cct = session.getClientContext();

		///////////////////////////////////////////////
		// Se obtiene el estado de tramitación
        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
   	    IState currentState = managerAPI.currentState(getStateticket(request));

  	    IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IGenDocAPI gendocAPI = invesFlowAPI.getGenDocAPI();

		List list = new ArrayList();

   	    // Obtener el tipo de documento
        String field = request.getParameter("field");
        String selectParticipantes="";
        if(StringUtils.isNotBlank(request.getParameter("selectParticipantes"))){
        	selectParticipantes="1";
        }
        String documentTypeId = request.getParameter(field);

        // Si se recibe el tipo de documento es que se está sustituyendo la plantilla de un documento
        // y sólo se cargarán las plantillas para ese tipo de documento
        if (documentTypeId != null) {

        	IItem item = entitiesAPI.getEntity(SpacEntities.SPAC_CT_TPDOC, Integer.parseInt(documentTypeId));
        	list.add(new ItemBean(item));

        	if (StringUtils.isEmpty(request.getParameter("new"))){
	        	// Establecer el id del documento
	        	int documentId = currentState.getEntityRegId();
	        	request.setAttribute("documentId", String.valueOf(documentId));
        	}
        }
        else {
    		IItemCollection collection = null;

    		// Obtener los tipos de documentos asociados al trámite
    		if (currentState.getTaskCtlId() != 0){
    			collection = gendocAPI.getDocTypesFromTaskCTL(currentState.getTaskCtlId());
    		}
    		else if (currentState.getTaskPcdId() != 0){
    			collection = gendocAPI.getDocTypesFromTaskPCD(currentState.getTaskPcdId());
    		}

    		if (collection != null) {
    			list = CollectionBean.getBeanList(collection);
    		}
        }

		if (!list.isEmpty()) {

			// Obtener las plantillas para los tipos de documentos cargados
			Iterator it = list.iterator();
			while (it.hasNext()) {
				ItemBean itemBean = (ItemBean) it.next();

				IItemCollection collection = null;
				if (currentState.getState() == ManagerState.SUBPROCESSESLIST || currentState.getState() == ManagerState.PROCESSESLIST)
					collection = gendocAPI.getTemplatesInStage(Integer.parseInt(itemBean.getString("ID")), 0);
				else if (currentState.getTaskId() != 0)
					collection = gendocAPI.getTemplatesInTask(Integer.parseInt(itemBean.getString("ID")), currentState.getTaskId());
				else
					collection = gendocAPI.getTemplatesInStage(Integer.parseInt(itemBean.getString("ID")), currentState.getStageId());

				if (collection != null) {

					List ltTemplates = CollectionBean.getBeanList(collection);
					itemBean.setProperty("TEMPLATES", ltTemplates);

					//Para calcular si son plantillas especificas o genéricas y filtrar plantillas odt segun configuracion
			        ITemplateAPI templateAPI = invesFlowAPI.getTemplateAPI();
			        boolean useOdtTemplates = ConfigurationMgr.getVarGlobalBoolean(cct, ConfigurationMgr.USE_ODT_TEMPLATES, false);
			        for (Iterator iter = ltTemplates.iterator(); iter.hasNext();) {
						 ItemBean element = (ItemBean) iter.next();
						 if (StringUtils.equals(element.getString("MIMETYPE"), "application/vnd.oasis.opendocument.text") && !useOdtTemplates){
							 iter.remove();
							 continue;
						 }
						 int id = element.getItem().getInt("ID");
						 if (!templateAPI.isProcedureTemplate(id)){
							 element.setProperty("NOMBRE", element.getProperty("NOMBRE")+"(G)");
						 }
					}
				}

			}
		}

		if(StringUtils.isNotEmpty(selectParticipantes)){
			request.setAttribute("selectParticipantes", selectParticipantes);
		}
		request.setAttribute("ltDocumentTypesTemplates", list);

		return mapping.findForward("success");
	}

}
