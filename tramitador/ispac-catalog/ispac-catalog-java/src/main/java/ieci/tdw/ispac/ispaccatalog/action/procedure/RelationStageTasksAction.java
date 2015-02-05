package ieci.tdw.ispac.ispaccatalog.action.procedure;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.BatchForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacweb.wizard.ItemSelectionHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Acción para seleccionar los trámites asociados a una fase.
 */
public class RelationStageTasksAction extends BaseAction {

	/**
	 * Ejecuta la lógica de la acción.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		BatchForm batchForm = (BatchForm) form;
		String[] selectTasksIds = batchForm.getMultibox();

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		ItemSelectionHelper itemSelHelper = new ItemSelectionHelper(
				request.getSession());

		// Establecer el retorno
		String forward = request.getParameter("forward");

		// Identificador de la fase
		String stageId = request.getParameter("itemId");
//		if (StringUtils.isNotBlank(itemId)) {
//			itemSelHelper.setCurrentEntityItemId(ICatalogAPI.ENTITY_CT_STAGE,
//					itemId);
//		} else {
//			itemId = itemSelHelper.getCurrentEntityItemId(
//					ICatalogAPI.ENTITY_CT_STAGE);
//		}

		// Obtener el identificador de la fase en el catálogo
		ItemBean stage = itemSelHelper.findItemBean(ICatalogAPI.ENTITY_CT_STAGE, TypeConverter.parseInt(stageId));
		int ctStageId = TypeConverter.parseInt((String)stage.getProperty("AUX_ID"), 0);

		// Establece la información de la fase
		IItem ctstage = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_STAGE,
				ctStageId);
		request.setAttribute("CTStage", new ItemBean(ctstage));

		//Establece la lista de trámites de la fase en el catálogo
		List ctTasks = getCTStageTasks(session, ctstage.getKeyInt());
		request.setAttribute("ItemsList", ctTasks);

		// Establecer el formateador para los trámites de la fase en el catálogo
		setFormatter(request, "ItemsListFormatter", 
				"/formatters/procedure/stagetasksformatter.xml");

		String pAdd = request.getParameter("add");
		String pDel = request.getParameter("del");
		String pModOrder = request.getParameter("modOrder");
		
		if (StringUtils.isNotBlank(pAdd)) {
			
			if (!ArrayUtils.isEmpty(selectTasksIds)) {
				for (int i = 0; i < selectTasksIds.length; i++) {
					String ctTaskId = selectTasksIds[i].split("-")[1];
					itemSelHelper.addItemBean(ICatalogAPI.ENTITY_CT_TASK,
							String.valueOf(ctStageId), 
							getCTTaskName(ctTasks, ctTaskId), 
							ctTaskId);
				}
			}

			batchForm.setMultibox(null);
		}
		else if (StringUtils.isNotBlank(pDel)) {

			if (!ArrayUtils.isEmpty(selectTasksIds)) {
				for (int i = 0; i < selectTasksIds.length; i++) {
					String [] ids = selectTasksIds[i].split("-");
					if (StringUtils.isNumeric(ids[0])){
						itemSelHelper.removeItemBean(
								ICatalogAPI.ENTITY_CT_TASK,
								TypeConverter.parseInt(ids[0], 0));
					}
				}
			}
			
			batchForm.setMultibox(null);
		}
		else if (StringUtils.isNotBlank(pModOrder)) {
			
			if (!ArrayUtils.isEmpty(selectTasksIds)) {
				if ("INC".equalsIgnoreCase(pModOrder)) {
					int prevItemId = 0;
					for (int i = 0; i < selectTasksIds.length; i++) {
						String [] ids = selectTasksIds[i].split("-");
						if (StringUtils.isNumeric(ids[0])){
							int itemId = TypeConverter.parseInt(ids[0], 0);
							itemSelHelper.moveItemBean(
									ICatalogAPI.ENTITY_CT_TASK,
									itemId,
									prevItemId,
									pModOrder);
							
							prevItemId = itemId;
						}
					}
				} else if ("DEC".equalsIgnoreCase(pModOrder)) {
					int prevItemId = 0;
					for (int i = selectTasksIds.length-1; i >= 0; i--) {
						String [] ids = selectTasksIds[i].split("-");
						if (StringUtils.isNumeric(ids[0])){
							int itemId = TypeConverter.parseInt(ids[0], 0);
							itemSelHelper.moveItemBean(
									ICatalogAPI.ENTITY_CT_TASK,
									itemId,
									prevItemId,
									pModOrder);
							
							prevItemId = itemId;
						}
					}
				}
			}
		}

		// Establecer los trámites seleccionados
		List selectedTasks = itemSelHelper.getItemBeanList(
				ICatalogAPI.ENTITY_CT_TASK, String.valueOf(ctStageId)); 
		request.setAttribute("SelItemsList", selectedTasks);
		
		//batchForm.setMultibox(new String[ctTasks.size()]);
		removeSelected(ctTasks, selectedTasks, itemSelHelper);

		// Establece el formateador para los trámites 
		setFormatter(request, "SelItemsListFormatter",
				"/formatters/procedure/selstagetasksformatter.xml");

		return mapping.findForward(StringUtils.defaultIfEmpty(forward, 
				"success"));
	}
        
	
	/**
	 * Se encarga de borrar de la lista de trámites aquellos que ya han sido seleccionados.
	 * @param ctStages
	 * @param selectedStages
	 * @param itemSelHelper
	 * @throws ISPACException
	 */
	private void removeSelected(List ctTasks, List selectedTasks, ItemSelectionHelper itemSelHelper) throws ISPACException {
		List aux=new ArrayList();		
		for(Iterator i=ctTasks.iterator(); i.hasNext();){
			ItemBean item = (ItemBean)i.next();
			if(itemSelHelper.containsItemBean(selectedTasks, item.getString("SPAC_CT_TRAMITES:NOMBRE")))
				aux.add(item);			
		}
		ctTasks.removeAll(aux);
	}

	/**
	 * Obtiene la lista de trámites de una fase en el catálogo.
	 * @param session Información de la sesión del usuario.
	 * @param ctStageId Identificador de la fase en el catálogo.
	 * @return Lista de trámites de la fase.
	 * @throws ISPACException si ocurre algún error.
	 */
	protected List getCTStageTasks(SessionAPI session, int ctStageId) 
			throws ISPACException {

		ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();
		IItemCollection itemcol = catalogAPI.getCTStageTasks(ctStageId);
		
		List stages = CollectionBean.getBeanList(itemcol);
        for (Iterator iter = stages.iterator(); iter.hasNext();) {
			ItemBean element = (ItemBean) iter.next();
			element.setProperty("ADD_ID", "add-" + element.getProperty("SPAC_CT_TRAMITES:ID"));
		}
        
        return stages;
	}

	/**
	 * Obtiene el nombre del trámite en el catálogo.
	 * @param ctTasks Lista de trámites en el catálogo.
	 * @param ctTaskId Identificador del trámite en el catálogo.
	 * @return Nombre del trámite.
	 * @throws ISPACException si ocurre algún error.
	 */
	protected String getCTTaskName(List ctTasks, String ctTaskId) 
			throws ISPACException {
		if ((ctTasks != null) && StringUtils.isNotBlank(ctTaskId)) {
			for (int i = 0; i < ctTasks.size(); i++) {
				ItemBean task = (ItemBean) ctTasks.get(i);
				if (ctTaskId.equals(task.getString("SPAC_CT_TRAMITES:ID"))) {
					return task.getString("SPAC_CT_TRAMITES:NOMBRE");
				}
			}
		}
		
		return "";
	}

}
