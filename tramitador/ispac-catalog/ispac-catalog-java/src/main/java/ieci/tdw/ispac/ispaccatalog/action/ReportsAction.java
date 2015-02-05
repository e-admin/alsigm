package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ReportsAction extends BaseDispatchAction {

	/**
	 * Añade el informe al procedimiento.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
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
		
		String sPcdId = request.getParameter("pcdId");
		String reportId = request.getParameter("reportId");
		String tpObj=request.getParameter("tpObj");
		
		if (StringUtils.isNotBlank(sPcdId)) {
			
			int pcdId = Integer.parseInt(sPcdId);

			request.setAttribute("Refresh","true");
			procedureAPI.addPReport(Integer.parseInt(tpObj), pcdId, Integer.parseInt(reportId));
		}

		return mapping.findForward("closeIFrame");
	}
	
	/**
	 * Elimina infrome seleccionado del procedimiento.
	 * @param mapping El ActionMapping utilizado para seleccionar esta instancia
	 * @param form El ActionForm bean (opcional) para esta petición
	 * @param request La petición HTTP que se está procesando
	 * @param response La respuesta HTTP que se está creando
	 * @param session Información de la sesión del usuario
	 * @return La redirección a la que se va a transferir el control.
	 * @throws ISPACException si ocurre algún error.
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws ISPACException {

        ClientContext cct = session.getClientContext();
        
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });
        
        IInvesflowAPI invesFlowAPI = cct.getAPI();
        IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

		int pcdId = Integer.parseInt(request.getParameter("regId"));
		String delId = request.getParameter("delId");
		String tpObj= request.getParameter("tpObj");
		request.setAttribute("Refresh","true");
		procedureAPI.deletePReport(Integer.parseInt(tpObj), pcdId, Integer.parseInt(delId) );
		
		return new ActionForward(new StringBuffer()
   	 			.append("/showProcedureEntity.do?method=reports&entityId=")
   	 			.append(request.getParameter("entityId"))
   	 			.append("&regId=")
   	 			.append(request.getParameter("regId"))
   	 			.toString(), true);
    }

}
