package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GetCTAppXMLAction extends BaseAction {

 	public ActionForward executeAction(ActionMapping mapping,
 									   ActionForm form,
 									   HttpServletRequest request,
 									   HttpServletResponse response,
 									   SessionAPI session) throws Exception {
 		
 		ClientContext cct = session.getClientContext();

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_COMP_REPORTS_READ,
				ISecurityAPI.FUNC_COMP_REPORTS_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
		
 		// Obtener el identificador del informe
 		String id = request.getParameter("reportId");

 		IItem item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_INFORMES, Integer.parseInt(id));
		if (item != null) {
			
			//Obtener la propiedad que queremos que sea descargada
			String property = item.getString("XML");
			if (StringUtils.isNotBlank(property)) {
				
				String fileName = null;
				String fileData = null;
			
				// Obtener el formulario JSP
		 		
				fileName = getMessage(request, cct.getLocale(), "entityManager.report.unload.code.fileName");
				if (StringUtils.isEmpty(fileName)) {
					fileName = "report.jrxml";
				}
				
				fileData = property;
		 		
		 		// Descargar el informe
		 		ServletOutputStream outputStream = response.getOutputStream();
		 		
		    	response.setHeader("Pragma", "public");
		    	response.setHeader("Cache-Control", "max-age=0");
				response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
				response.setContentLength(fileData.length());
				
				outputStream.print(fileData);
				outputStream.flush();
				outputStream.close();
			}
			else {
				 throw new ISPACInfo("exception.entities.report.empty");
			}
		}

		return null;
	}
 	
}