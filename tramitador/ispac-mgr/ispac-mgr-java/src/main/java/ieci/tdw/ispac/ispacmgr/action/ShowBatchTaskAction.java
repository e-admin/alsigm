package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.BatchTaskForm;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacmgr.mgr.SchemeMgr;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowBatchTaskAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form,
									   HttpServletRequest request, 
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {
				
		ClientContext cct = session.getClientContext();
		
		IInvesflowAPI invesFlowAPI = session.getAPI();
		IWorklistAPI workListAPI = invesFlowAPI.getWorkListAPI();
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		
		// Comprobación: es una llamada de refresco de interfaz??. De ser asi se construye una URL los ultimos parametros
		// seleccionados por el usuario y se hace un redirect a true a esa URL, 
		// Finalidad: Si desde la pantalla de vista de la tarea agrupada se va a la vista del exp y de la vista 
		// del exp hacia atrás, se recargue la URL con todos los últimos valores mostrados en pantalla.
		String refresher = request.getParameter(ActionsConstants.PARAM_FORM_REFRESHER);
		boolean isRefresherCall = false;
		if (!StringUtils.isBlank(refresher))
			isRefresherCall = true;
		if (isRefresherCall){
			HashSet ignoredParams = new HashSet();
			ignoredParams.add(ActionsConstants.PARAM_FORM_REFRESHER);
			return composeActionForward(form,"/showBatchTask.do",ignoredParams);
		}
		
		BatchTaskForm frm = (BatchTaskForm)form;
		String idBatchTask = frm.getBatchTaskId();
				
	    int pcdId = 0;
		//obtener la lista de tramitaciones agrupadas
		IItem batchTask = invesFlowAPI.getBatchTask(new Integer(idBatchTask).intValue());
		ItemBean batchTaskBean = new ItemBean(batchTask);
		request.setAttribute(ActionsConstants.BATCH_TASK, batchTaskBean);

		//anadir informacion extra del expediente
		IItemCollection expdsTrAgrupada = workListAPI.getExpsBatchTask(new Integer(idBatchTask).intValue());
		List expedientesDeAgrupacion = CollectionBean
				.getBeanList(expdsTrAgrupada);

		//Array de numeros de expediente
		String []numExpds = new String[expedientesDeAgrupacion.size()];
		int i = 0;
		for (Iterator iter = expedientesDeAgrupacion.iterator(); iter
				.hasNext();) {
			ItemBean itemBean  = (ItemBean) iter.next();
			numExpds[i]=itemBean.getString("NUMEXP");
			i++;
		}
		
		IItemCollection expedientesAbiertos = workListAPI.getProcesses(numExpds);
		//Una tramitación agrupada puede tener todos sus expedientes enviados a la papelera
		//Por lo que no hay que lanzar aviso de error.
		/*if (!expedientesAbiertos.next()) {
			
			throw new ISPACInfo("exception.expedients.batchTask.noExpedients", new String[]{batchTaskBean.getString("FASE")});
		}*/
		
		List expedientesAbiertosBeans = CollectionBean.getBeanList(expedientesAbiertos);
		//Obtenemos el id del procedimiento de un expediente cualquiera 
		//(todos pertenecen al mismo procedimiento) para obtener las plantillas asociadas a un tipo de documeto y procedimiento
		expedientesAbiertos.reset();
		if (expedientesAbiertos.next())
			pcdId = expedientesAbiertos.value().getInt("ID_PCD");
			
		request.setAttribute(ActionsConstants.BATCH_TASK_EXPS, expedientesAbiertosBeans);
		
		//Establecer estado de tarea agrupada
		Map params = request.getParameterMap();
		IState newState = managerAPI.enterState(getStateticket(request),ManagerState.BATCHTASK,params);
			
		try {	
			//lista de posibles tramites de la fase de la tramitación agrupada
			int idFase = batchTask.getInt("ID_FASE");
			IItemCollection tramitesDeLaFase = workListAPI.getProcedureStageTasks(idFase);
			List tramitesDeLaFaseBean = CollectionBean.getBeanList(tramitesDeLaFase);
			request.setAttribute(ActionsConstants.POSIBLES_TRAMITES_DE_FASE, tramitesDeLaFaseBean);

			//obtencion del id de catalogo del tramite seleccionado
			String idTramiteSeleccionado = ((BatchTaskForm)form).getTaskPcdId();
			if ( (idTramiteSeleccionado==null || idTramiteSeleccionado.length()==0) && tramitesDeLaFaseBean.size()>0){
				idTramiteSeleccionado=batchTaskBean.getString("ID_ULTIMO_TRAMITE");
				if (StringUtils.isEmpty(idTramiteSeleccionado))
						idTramiteSeleccionado = ((ItemBean)tramitesDeLaFaseBean.get(0)).getString("ID");
			}
			
			if (idTramiteSeleccionado != null) {
				
				// Responsabilidades del usuario conectado
				String resp = workListAPI.getRespString();
				
				for (Iterator iter = expedientesAbiertosBeans.iterator(); iter.hasNext();) {
					
					ItemBean element = (ItemBean) iter.next();
					IItemCollection taskCollection = workListAPI.getTasks(element.getString("NUMEXP"),
																		  Integer.parseInt(idTramiteSeleccionado),
																		  resp);
					
					for (Iterator itTask = taskCollection.iterator(); itTask
							.hasNext();) {
						IItem task = (IItem) itTask.next();
						if (task!=null){
							//element.setProperty(ActionsConstants.PROPERTY_OPENED_TASK, Boolean.TRUE);
							element.setProperty(ActionsConstants.PROPERTY_TASK_ID, new Integer(task.getInt("ID")));
							break;
						}else{
							//element.setProperty(ActionsConstants.PROPERTY_OPENED_TASK, Boolean.FALSE);
							element.setProperty(ActionsConstants.PROPERTY_TASK_ID, new Integer(-1));
						}
					}
				}
			
				//INFORMACION DE LOS DOCUMENTOS GENERADOS
				//Obtencion de tipos de documentos por tramite
				String idCtlTramiteSeleccionado = null;
				for (Iterator iter = tramitesDeLaFaseBean.iterator(); iter
						.hasNext();) {
					ItemBean tramite = (ItemBean) iter.next();
					if (tramite.getString("ID").equals(idTramiteSeleccionado)){
						idCtlTramiteSeleccionado = tramite.getString("ID_CTTRAMITE");
						break;
					}
				}
				
				IItemCollection listaTipoDocumentos = invesFlowAPI
						.getProcedureAPI().getTaskTpDoc(
								new Integer(idCtlTramiteSeleccionado).intValue());
				request.setAttribute(ActionsConstants.TPDOCS_LIST, CollectionBean.getBeanList(listaTipoDocumentos));
				listaTipoDocumentos.reset();
				
				//obtencion del tipo de documento por defecto
				String idTipoDocumentoSeleccionado = null;
				if (frm.getTpDocId()!=null && frm.getTpDocId().length()>0){
					idTipoDocumentoSeleccionado = frm.getTpDocId();
				}else{
					idTipoDocumentoSeleccionado = batchTaskBean.getString("ID_ULTIMO_TIPODOC");
					boolean idTipoDocumentoPorDefectoValido = false;
					if (!StringUtils.isEmpty(idTipoDocumentoSeleccionado)){
						idTipoDocumentoPorDefectoValido = (listaTipoDocumentos.toMapStringKey("TASKTPDOC:ID_TPDOC").get(idTipoDocumentoSeleccionado)!=null);
					}
					//si no hay tipo de documento por defecto selecciono el primero de los disponibles
					if (!idTipoDocumentoPorDefectoValido || StringUtils.isEmpty(idTipoDocumentoSeleccionado)){
						if (listaTipoDocumentos.iterator().hasNext()){
							idTipoDocumentoSeleccionado = ((IItem)listaTipoDocumentos.iterator().next()).getString("TASKTPDOC:ID_TPDOC");
						}
					}else{
						//si hay uno por defecto tiene q estar entre los obtenidos a partir de tramite
						
					}
				}
	
				// obtencion de las plantillas del tipo de documento
				List templatesTpDoc = new ArrayList();
				if (!StringUtils.isEmpty(idTipoDocumentoSeleccionado)) {
					
					templatesTpDoc = CollectionBean.getBeanList(invesFlowAPI.getGenDocAPI().getTemplatesInPTask(
							Integer.parseInt(idTipoDocumentoSeleccionado),
							Integer.parseInt(idTramiteSeleccionado)));
					
					//Para calcular si son plantillas especificas o genéricas
			        ITemplateAPI templateAPI = invesFlowAPI.getTemplateAPI();		        
			        for (Iterator iter = templatesTpDoc.iterator(); iter.hasNext();) {
						 ItemBean element = (ItemBean) iter.next();
						 int id = element.getItem().getInt("ID");
						 if (!templateAPI.isProcedureTemplate(id))
							 element.setProperty("NOMBRE", element.getProperty("NOMBRE")+ getResources(request).getMessage("generic.template"));	 
					}
				}
				
				request.setAttribute(ActionsConstants.TEMPLATES_LIST, templatesTpDoc);
				
				
				//insertar informacion de documentos creados en la lista de expedientes
				String idTemplateSeleccionado = null;
				if (frm.getTemplate()!=null && frm.getTemplate().length()>0){
					idTemplateSeleccionado=frm.getTemplate();
				}else{
					idTemplateSeleccionado = batchTaskBean.getString("ID_ULTIMO_TEMPLATE");
					if (StringUtils.isEmpty(idTemplateSeleccionado)){
						if (templatesTpDoc.iterator().hasNext()){
							ItemBean template = ((ItemBean)templatesTpDoc.iterator().next());
							idTemplateSeleccionado = template.getString("ID");
						}
					}
				}
				
				
				if (idTemplateSeleccionado != null){
					for (Iterator iter = expedientesAbiertosBeans.iterator(); iter
							.hasNext();) {
						ItemBean element = (ItemBean) iter.next();
						//IItemCollection docsCollection = invesFlowAPI.getEntitiesAPI().getDocuments(element.getString("NUMEXP"),Integer.valueOf(idTemplateSeleccionado).intValue());
						IItemCollection docsCollection = invesFlowAPI.getEntitiesAPI().getDocumentsOpenedTask(element.getString("NUMEXP"), Integer.valueOf(idTemplateSeleccionado));
						if (docsCollection.next()){
							element.setProperty(ActionsConstants.PROPERTY_CREATED_DOC,Boolean.TRUE);
						} else {
							element.setProperty(ActionsConstants.PROPERTY_CREATED_DOC,Boolean.FALSE);
						}
	//					for (Iterator itDocs = docsCollection.iterator(); itDocs
	//							.hasNext();) {
	//						IItem doc = (IItem) itDocs.next();
	//						if (doc != null) {
	//							element.setProperty(
	//									ActionsConstants.PROPERTY_CREATED_DOC,
	//									Boolean.TRUE);
	//						} else {
	//							element.setProperty(
	//									ActionsConstants.PROPERTY_CREATED_DOC,
	//									Boolean.FALSE);
	//						}
	//					}
					}
				}
				
				//Comprobamos si hay tramites realizados para cada expediente
				if (idTramiteSeleccionado != null){
					for (Iterator iter = expedientesAbiertosBeans.iterator(); iter .hasNext();) {
						ItemBean element = (ItemBean) iter.next();
						boolean madeTasks =  invesFlowAPI.getEntitiesAPI().countEntities(SpacEntities.SPAC_DT_TRAMITES, "WHERE NUMEXP = '" + DBUtil.replaceQuotes(element.getString("NUMEXP")) + "' AND ID_TRAM_PCD = " + idTramiteSeleccionado + " AND ID_TRAM_EXP NOT IN ( SELECT ID FROM SPAC_TRAMITES WHERE NUMEXP = '" + DBUtil.replaceQuotes(element.getString("NUMEXP")) + "' )") != 0;
						if (madeTasks){ 
							element.setProperty(ActionsConstants.PROPERTY_MADE_TASKS,Boolean.TRUE);
						} else {
							element.setProperty(ActionsConstants.PROPERTY_MADE_TASKS,Boolean.FALSE);
						}
					}
				}
				
	
				//Gestion de documentos
				IGenDocAPI documentsAPI = invesFlowAPI.getGenDocAPI();
				String sParameter = frm.getTipoAccion();
	
				if (sParameter != null && sParameter.equalsIgnoreCase("edit")) {
						String sTemplateName = documentsAPI
								.getTemporaryTemplate(Integer.parseInt(idTemplateSeleccionado));
						frm.setFile(sTemplateName);
				}
				
				// establecer valores por defecto para el formulario
				frm.setTaskPcdId(idTramiteSeleccionado);
				frm.setTemplate(idTemplateSeleccionado);
				frm.setTpDocId(idTipoDocumentoSeleccionado);
				
			} else {
				
				request.setAttribute(ActionsConstants.TPDOCS_LIST, new ArrayList());
				request.setAttribute(ActionsConstants.TEMPLATES_LIST, new ArrayList());
				
			}
		}
		catch (Exception e) {
			
			throw new ISPACInfo("exception.expedients.batchTask.error", new String[]{e.getMessage()});
		}
		
		// Guardar el estado de tramitación
		storeStateticket(newState,response);
		
        // Cargar el contexto de la cabecera (miga de pan)
        SchemeMgr.loadContextHeader(newState, request, getResources(request), session);
		
        // Establecer el menu
	    request.setAttribute("menus", MenuFactory.getBatchTaskMenu(cct,	newState, (String) servlet.getServletContext().getAttribute("ispacbase"), getResources(request)));
		
		return mapping.findForward("success");
	}

}