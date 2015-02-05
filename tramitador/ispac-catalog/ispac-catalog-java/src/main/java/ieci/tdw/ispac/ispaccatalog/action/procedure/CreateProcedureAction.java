package ieci.tdw.ispac.ispaccatalog.action.procedure;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaccatalog.managers.NewProcedureMgr;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacweb.wizard.ItemSelectionHelper;

import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Acción para crear un procedimiento.
 *
 */
public class CreateProcedureAction extends BaseAction {

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

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IProcedureAPI pcdAPI = invesFlowAPI.getProcedureAPI();

		ItemSelectionHelper helper = new ItemSelectionHelper(request.getSession());

		List ctStages = new ArrayList();
		Map ctStagesTaskMap = new LinkedHashMap();

		List selectedStages = helper.getItemBeanList(ICatalogAPI.ENTITY_CT_STAGE);
		for (int i = 0; i < selectedStages.size(); i++) {
			ItemBean ctStage = (ItemBean) selectedStages.get(i);
			ctStages.add(createCTStageItem(ctStage));
			
			String ctStageCtId = ctStage.getString("AUX_ID");
			if (!"-1".equals(ctStageCtId)) {
				List cttasklist = helper.getItemBeanIdList(
						ICatalogAPI.ENTITY_CT_TASK, ctStageCtId);
				ctStagesTaskMap.put(new Integer(ctStageCtId), cttasklist);
			}
		}

		NewProcedureMgr pcdmgr = new NewProcedureMgr(
				session.getClientContext(), request.getSession());
		pcdmgr.getContext();
		
		int newpcdId = 0;
		
		try {
			
			if (pcdmgr.checkType(NewProcedureMgr.MODE_DRAFT)) {
				
				newpcdId = pcdAPI.createDraft(pcdmgr.getIntPcdId(), 
						pcdmgr.getName(), ctStages, ctStagesTaskMap);
				
			} else if (pcdmgr.checkType(NewProcedureMgr.MODE_CLONE)) {
				
				newpcdId = pcdAPI.cloneProcedure(pcdmgr.getIntPcdId(), 
						pcdmgr.getName(), ctStages, ctStagesTaskMap, 
						pcdmgr.getIntParent());

			} else if (pcdmgr.checkType(NewProcedureMgr.MODE_MODIFY)) {

				newpcdId = pcdAPI.modifyProcedure(pcdmgr.getIntPcdId(), 
						pcdmgr.getName(), ctStages, ctStagesTaskMap);

			} else if (pcdmgr.checkType(NewProcedureMgr.MODE_NEW)) {
				
				newpcdId = pcdAPI.createProcedure(pcdmgr.getName(), 
						ctStages, ctStagesTaskMap);
			}

			request.setAttribute("Message", "procedure.wizard.create");
			
		} catch (ISPACException e) {
			request.setAttribute("Message", "procedure.wizard.create.error");
			request.setAttribute("ErrorMessage", e.getMessage());
		} finally {
			pcdmgr.clearContext();
		}

		ActionForward fwd = mapping.findForward("shownewpcd");
		String path = new StringBuffer(fwd.getPath())
			.append(fwd.getPath().indexOf("?") > 0 ? "&" : "?")
			.append("entityId=").append(ICatalogAPI.ENTITY_P_PROCEDURE)
			.append("&regId=").append(newpcdId)
			.toString();
		request.setAttribute("NewPcdURL", path);

		return mapping.findForward("success");
	}
	
	protected IItem createCTStageItem(ItemBean bean) throws ISPACException {
		IItem ctStage = new GenericItem(getCTStageItemProperties(), "ID");
		ctStage.set("ID", TypeConverter.parseInt((String)bean.getProperty("AUX_ID"), -1));
		ctStage.set("NOMBRE", bean.getProperty("NOMBRE"));
		return ctStage;
	}
	
	protected static Properties getCTStageItemProperties() {
		Properties properties = new Properties();
		properties.add(new Property(0, "ID", Types.INTEGER));
		properties.add(new Property(1, "NOMBRE", Types.VARCHAR));
		return properties;
	}
}