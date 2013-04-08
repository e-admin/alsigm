package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Action para mostrar la información de un nodo de sincronización.
 *
 */
public class ShowSyncNodeAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		EntityForm defaultForm = (EntityForm) form;

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		// Identificador de la entidad
		int entityId = ICatalogAPI.ENTITY_P_SINCNODE;
		
		// Obtener el identificador del nodo de sincronización
		String parameter = request.getParameter("regId");
		if (parameter == null) {
			parameter = defaultForm.getKey();
		}
		int keyId = TypeConverter.parseInt(parameter, 
				ISPACEntities.ENTITY_NULLREGKEYID);

		// Obtener el identificador del procedimiento
		int pcdId = TypeConverter.parseInt(request.getParameter("pcdId"), 
				ISPACEntities.ENTITY_NULLREGKEYID);
		if (pcdId != ISPACEntities.ENTITY_NULLREGKEYID) {
			IProcedure pcd = invesFlowAPI.getProcedure(pcdId);
			defaultForm.setProperty("NOMBRE_PCD", pcd.getNombre());
		}

		defaultForm.setEntity(Integer.toString(entityId));
		defaultForm.setKey(Integer.toString(keyId));
		defaultForm.setReadonly("false");

		EntityApp entityapp = catalogAPI.getCTDefaultEntityApp(entityId, keyId, getRealPath(""));

		if (keyId == ISPACEntities.ENTITY_NULLREGKEYID) {
			entityapp.getItem().setKey(ISPACEntities.ENTITY_NULLREGKEYID);
		}

		entityapp.getItem().set("ID_PCD", pcdId);
		
		// Visualiza los datos de la entidad
		if (defaultForm.getActions() == null
				|| defaultForm.getActions().equals("success")) {
			defaultForm.setEntityApp(entityapp);
		}

		// Página jsp asociada a la presentación de la entidad
		request.setAttribute("application", entityapp.getURL());
		request.setAttribute("EntityId", Integer.toString(entityId));
		request.setAttribute("KeyId", Integer.toString(keyId));

		return mapping.findForward("success");
	}

}