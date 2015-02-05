package ieci.tdw.ispac.ispaccatalog.action.procedure;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
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

/**
 * Gestiona las dependencias entre trámites.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class TaskDependenciesAction extends BaseDispatchAction {

	/** 
	 * Logger de la clase. 
	 */
	protected static final Logger logger = Logger.getLogger(TaskDependenciesAction.class);

	
	/**
	 * Añade una dependencia sobre el trámite seleccionado.
	 * 
	 * @param mapping
	 *            El ActionMapping utilizado para seleccionar esta instancia
	 * @param form
	 *            El ActionForm bean (opcional) para esta petición
	 * @param request
	 *            La petición HTTP que se está procesando
	 * @param response
	 *            La respuesta HTTP que se está creando
	 * @param session
	 *            Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {
		
		ClientContext cct = session.getClientContext();
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

        IInvesflowAPI invesFlowAPI = cct.getAPI();
		IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();
		
		String ptaskId = request.getParameter("ptaskId");
		String parentPtaskId = request.getParameter("parentPtaskId");
		
		if (StringUtils.isNotBlank(ptaskId)
				&& StringUtils.isNotBlank(parentPtaskId)) {
			
			request.setAttribute("Refresh","true");
			procedureAPI.addTaskDependency(Integer.parseInt(ptaskId),
					Integer.parseInt(parentPtaskId));
		}

		return mapping.findForward("closeIFrame");
	}


	/**
	 * Elimina la dependencia seleccionada.
	 * 
	 * @param mapping
	 *            El ActionMapping utilizado para seleccionar esta instancia
	 * @param form
	 *            El ActionForm bean (opcional) para esta petición
	 * @param request
	 *            La petición HTTP que se está procesando
	 * @param response
	 *            La respuesta HTTP que se está creando
	 * @param session
	 *            Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

		int entityId = Integer.parseInt(request.getParameter("entityId"));
		int regId = Integer.parseInt(request.getParameter("regId"));
 		int depId = Integer.parseInt(request.getParameter("depId"));

		ClientContext cct = session.getClientContext();

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		IInvesflowAPI invesFlowAPI = cct.getAPI();
		IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();
		
		// Elimina la dependencia
		procedureAPI.deleteTaskDependency(depId);

        return new ActionForward(new StringBuffer()
			.append("/showProcedureEntity.do?method=dependencies&entityId=")
			.append(entityId)
			.append("&regId=")
			.append(regId)
			.toString(), true);
	}
}
