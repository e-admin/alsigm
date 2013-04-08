package ieci.tdw.ispac.ispaccatalog.action.procedure;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseDispatchAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;

import java.io.File;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class ExportProcedureAction extends BaseDispatchAction {
	
	public ActionForward enter(ActionMapping mapping, 
							   ActionForm form, 
							   HttpServletRequest request, 
							   HttpServletResponse response, 
							   SessionAPI session) throws ISPACException {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

	    return mapping.findForward("enter");
	}
	
	public ActionForward export(ActionMapping mapping, 
			   					ActionForm form, 
			   					HttpServletRequest request, 
			   					HttpServletResponse response, 
			   					SessionAPI session) throws ISPACException {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		ClientContext cct = session.getClientContext();
        IInvesflowAPI invesFlowAPI = session.getAPI();
        IProcedureAPI pcdAPI = invesFlowAPI.getProcedureAPI();

		DynaActionForm dform=(DynaActionForm)form;
		String pcdId = (String)dform.get("pcdId");
		
		String path = getRealPath("");
		File zipFile = null;

		try {
			// Crear el zip con los documentos
			zipFile = pcdAPI.exportProcedure(Integer.parseInt(pcdId), path);
			
			// Nombre del zip
			String zipFileName = getMessage(request, cct.getLocale(), "export.procedure.fileName.zip");
			
	    	// Devolver el zip al navegador			
			ServletOutputStream outputStream = response.getOutputStream();
			
	    	response.setHeader("Pragma", "public");
	    	response.setHeader("Cache-Control", "max-age=0");
	    	response.setContentType("application/zip");
	        response.setHeader("Content-Transfer-Encoding", "binary");
	    	response.setHeader("Content-Disposition", "attachment; filename=\"" + zipFileName + "\"");
	    	response.setContentLength((int)zipFile.length());
	    	
	    	FileUtils.copy(zipFile, outputStream);
	    	outputStream.flush();
	    	outputStream.close();
		}
        catch (Exception e) {
        	throw new ISPACInfo("exception.procedures.export", new String[] {e.getMessage()});
        }
		finally {
			
			if (zipFile != null) {   
		        // Eliminar fichero temporal del zip
		        FileUtils.deleteFile(zipFile);
			}
		}

		return null;
	}
	
}