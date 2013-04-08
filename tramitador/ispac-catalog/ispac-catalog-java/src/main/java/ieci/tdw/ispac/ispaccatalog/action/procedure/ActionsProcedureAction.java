package ieci.tdw.ispac.ispaccatalog.action.procedure;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.ispaccatalog.action.BaseDispatchAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ActionsProcedureAction extends BaseDispatchAction {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(ActionsProcedureAction.class);

	/**
	 * Elimina todos los expedientes del procedimiento.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public ActionForward clean(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		// Eliminar los expedientes del procedimiento
		deleteProcedure(mapping, form, request, response, session, false);

		// Mostrar la ficha del procedimiento
		ActionForward forward = mapping.findForward("card");
		StringBuffer url = new StringBuffer(forward.getPath())
			.append("&entityId=").append(request.getParameter("retEntityId"))	
			.append("&regId=").append(request.getParameter("retKeyId"));
		
		// Mostrar el bloque adecuado
		String block = request.getParameter("block");
		if (StringUtils.isNotBlank(block)) {
			url.append("&block=").append(block);
		}

		return new ActionForward(forward.getName(), url.toString(), false);
	}

	/**
	 * Elimina el procedimiento y si tiene expedientes también.
	 * 
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		//EntityForm defaultForm = (EntityForm) form;
		int retEntityId = Integer.parseInt(request.getParameter("retEntityId"));
 		int groupId = Integer.parseInt(request.getParameter("groupId"));
 		int keyId = Integer.parseInt(request.getParameter("key"));
 		
 		//Si existe una versión posterior a la actual y esta es borrador
  	   //no se permite eliminar la versión actual del procedimiento
 		
 		IItem procedure=getLastPcdId(session, groupId,keyId );
 		int idLastPcd= 0;
 		boolean canDelete=true;
 		if(procedure!=null){
 			idLastPcd=procedure.getInt("ID");
 			if(procedure.getInt("ESTADO")==IProcedure.PCD_STATE_DRAFT){
 				canDelete=false;
 			}
 		}
 		if(canDelete){
 			deleteProcedure(mapping, form, request, response, session, true);
 			//Hay que comprobar si hay otra versión anterior del procedimiento y redirigir al dicha versión
 			//En caso de que no hay que redirigir a la bandeja de entrada.
 			return getActionForward(mapping, request, session, retEntityId, idLastPcd);
 		}
 		else{
 			throw new ISPACInfo(getResources(request).getMessage("exception.delete.procedure.vigente.exist.draft"));
 		}

	}

	/**
	 * Elimina todos los expedientes de un procedimiento y si el parámetro
	 * deleteProcedure es true también el procedimiento
	 * 
	 * @param cct
	 * @param keyId
	 * @param deleteProcedure
	 * @throws ISPACException
	 */
	private void deleteProcedure(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, SessionAPI session,
			boolean deleteProcedure) throws ISPACException {

		// Indentificador del registro
		int keyId = Integer.parseInt(request.getParameter("key"));

		ClientContext cct = session.getClientContext();

		// API de invesFlow

		IInvesflowAPI invesflowAPI = cct.getAPI();
		IProcedureAPI pcdAPI= invesflowAPI.getProcedureAPI();
		if (keyId > 0) {
			// Obtener todos los procesos de un determinado procedimiento
			IItemCollection procesos = invesflowAPI
					.getProcessesByProcedure(keyId);
			if (procesos.next()) {
				while (procesos.next()) {
					int idProcess = Integer.parseInt(procesos.value()
							.getString("ID"));
					invesflowAPI.getTransactionAPI().cleanProcess(idProcess);
					if (logger.isInfoEnabled()) {
						logger.info("Se ha eliminado el proceso" + idProcess);
					}
				}
			}
		} else {
			if (logger.isInfoEnabled()) {
				logger.info("No se ha eliminado ningún expediente");
			}
		}

		// Eliminamos el procedimiento
		if (deleteProcedure) {

			try{
				pcdAPI.deleteProcedure(keyId); 
				//el contador por procedimiento
			}catch(ISPACInfo e){
				throw e;
			}catch(ISPACException e){
				logger.error("Error al eliminar el procedimiento", e);
				throw new ISPACInfo("Error eliminando procedimiento. Error: " + e.getMessage());
			}
		}
	}
	
	private ActionForward getActionForward(ActionMapping mapping,
 			HttpServletRequest request, SessionAPI session, int entityId,
 			int regId) throws ISPACException {
 		

 		String url;
 		
 		if (regId > 0) {
			ActionForward fwd = mapping.findForward("pcd");
			url = new StringBuffer(request.getContextPath())
				.append(fwd.getPath())
				.append(fwd.getPath().indexOf("?") > 0 ? "&" : "?")
				.append("entityId=").append(entityId)
				.append("&regId=").append(regId)
				.toString();
 		} else {
 			String forwardName = (entityId == ICatalogAPI.ENTITY_P_PROCEDURE ? 
 					"pcds" : "subPcds");
 			
			ActionForward fwd = mapping.findForward(forwardName);
			url = new StringBuffer(request.getContextPath())
				.append(fwd.getPath()).toString();
 		}
 		
		request.setAttribute("target", "top");
		request.setAttribute("url", url);
		
		return mapping.findForward("loadOnTarget");
 		
 	}
 	
	/**
	 * Obtiene el pcd con mayor versión distinto al actual
	 * @param session
	 * @param groupId
	 * @param regId
	 * @return
	 * @throws ISPACException
	 */
 	private IItem getLastPcdId(SessionAPI session, int groupId , int regId) 
 			throws ISPACException {
 		
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		String whereClause = new StringBuffer(" WHERE NVERSION=(SELECT MAX(NVERSION) FROM SPAC_P_PROCEDIMIENTOS WHERE ID_GROUP=")
			.append(groupId).append(" AND ID!=").append(regId).append(") AND ID_GROUP=").append(groupId)
			.toString();

		IItemCollection itemcol = catalogAPI.queryCTEntities(
				ICatalogAPI.ENTITY_P_PROCEDURE, whereClause);
 		if (itemcol != null) {
 			if (itemcol.next()) {
 				 return itemcol.value();
 			}
 		}
		return null;
 		
	
 	}

}
