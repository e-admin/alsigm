package ieci.tdw.ispac.ispaccatalog.action.procedure;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseDispatchAction;
import ieci.tdw.ispac.ispaccatalog.action.form.UploadForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.ProcedureElement;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class ImportProcedureAction extends BaseDispatchAction {

	public static final String IMPORT_PROCEDURE_AS_CHILD_VALUE = "1";
	public static final String IMPORT_PROCEDURE_AS_VERSION_VALUE = "2";

	public ActionForward enter(ActionMapping mapping,
							   ActionForm form,
							   HttpServletRequest request,
							   HttpServletResponse response,
							   SessionAPI session) throws ISPACException {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

	    return mapping.findForward("enter");
	}

	public ActionForward upload(ActionMapping mapping,
								ActionForm form,
								HttpServletRequest request,
								HttpServletResponse response,
								SessionAPI session) throws ISPACException {

		importProcedure(mapping, form, request, response, session, false);

		return null;
	}

	public ActionForward validate(ActionMapping mapping,
								  ActionForm form,
								  HttpServletRequest request,
								  HttpServletResponse response,
								  SessionAPI session) throws ISPACException {

		importProcedure(mapping, form, request, response, session, true);

		return null;
	}

	private void importProcedure(ActionMapping mapping,
			   					 ActionForm form,
			   					 HttpServletRequest request,
			   					 HttpServletResponse response,
			   					 SessionAPI session,
			   					 boolean test) throws ISPACException {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		ClientContext cct = session.getClientContext();
        IInvesflowAPI invesFlowAPI = session.getAPI();
        IProcedureAPI pcdAPI = invesFlowAPI.getProcedureAPI();

		UploadForm uploadForm = (UploadForm) form;

		try {
			// Obtener el fichero con el procedimiento exportado
			FormFile formFile = uploadForm.getUploadFile();

			// Fichero no vacío
			if (formFile.getFileSize() > 0) {

				// Fichero ZIP
				String mimeType = MimetypeMapping.getMimeType(formFile.getFileData());
				if(mimeType.toUpperCase().indexOf("ZIP") != -1) {

					File zipFile = null;

					try {
						// Manejador de ficheros temporales
						FileTemporaryManager ftMgr = FileTemporaryManager.getInstance();

						// Crear un fichero temporal para el zip
						zipFile = ftMgr.newFile(".zip");

						OutputStream zipOutputStream = new FileOutputStream(zipFile);
						FileUtils.copy(new ByteArrayInputStream(formFile.getFileData()), zipOutputStream);
						zipOutputStream.flush();
						zipOutputStream.close();

						// Importar como versión o familia hija
						boolean asVersion = false;

						// Procedimiento padre
						int parentPcdId = ProcedureElement.PCD_CT_PARENT_ROOT;
						String sParentPcdId = uploadForm.getProperty("ID_PCD_PARENT");
						if (StringUtils.isNotBlank(sParentPcdId)) {
							parentPcdId = Integer.parseInt(sParentPcdId);

							// Importar como versión?
							String createAs = uploadForm.getProperty("CREATE_AS");
							if (createAs.equals(IMPORT_PROCEDURE_AS_VERSION_VALUE)) {
								asVersion = true;
							}
						}

	 					// Importar el procedimiento
						String importLog = pcdAPI.importProcedure(parentPcdId, asVersion, zipFile, test);

						// Nombre del fichero
						String importLogFileName = getMessage(request, cct.getLocale(), "import.procedure.fileName.log");

						// Devolver el log de importación
				    	response.setHeader("Pragma", "public");
				    	response.setHeader("Cache-Control", "max-age=0");
				    	response.setHeader("Content-Disposition", "attachment; filename=\"" + importLogFileName + "\"");
				    	response.setContentType("text");
				    	response.setContentLength(importLog.length());

				    	ServletOutputStream outputStream = response.getOutputStream();
				    	outputStream.print(importLog);
				    	outputStream.flush();
				    	outputStream.close();
					}
					finally {

						if (zipFile != null) {
					        // Eliminar el fichero temporal del zip
					        FileUtils.deleteFile(zipFile);
						}
					}
				}
				else {
					throw new ISPACInfo("exception.procedures.import.invalidFile");
				}
			}
			else {
				throw new ISPACInfo("exception.uploadfile.empty");
			}
        }
        catch (IOException e) {
            throw new ISPACInfo("exception.uploadfile.error");
        }
	}

}