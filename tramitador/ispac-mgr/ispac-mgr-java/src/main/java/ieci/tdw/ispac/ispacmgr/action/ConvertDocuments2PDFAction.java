package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.gendoc.converter.DocumentConverter;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispacmgr.action.form.DocumentsForm;

import java.io.File;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sun.star.connection.NoConnectException;
import com.sun.star.lang.DisposedException;

public class ConvertDocuments2PDFAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

		DocumentsForm frm = (DocumentsForm) form;
		String[] documentIds = frm.getMultibox();
		int[] intDocumentIds = new int[documentIds.length];
		for (int i = 0; i < documentIds.length; i++) {
			intDocumentIds[i] = Integer.parseInt(documentIds[i]);
		}
		try{
			String file = DocumentConverter.concatenate2PDF(session.getAPI(), intDocumentIds);
			File pdfFile = new File(file);
			// Devolver el pdf al navegador
	    	response.setHeader("Pragma", "public");
	    	response.setHeader("Cache-Control", "max-age=0");
	        response.setHeader("Content-Transfer-Encoding", "binary");
	    	response.setHeader("Content-Disposition","attachment; filename=\"" + pdfFile.getName() + "\"");
	    	response.setContentType("application/zip");
	    	response.setContentLength((int)pdfFile.length());
	    	
	        ServletOutputStream out = response.getOutputStream();
	    	FileUtils.copy(pdfFile, out);
	    	out.flush();
	    	out.close();
	    	
		}catch (Throwable e) {
			String message = null;
			if (e instanceof ISPACException) {
				if (e.getCause() instanceof NoConnectException) {
					message = "exception.extrainfo.documents.openoffice.off"; 
				}else if (e.getCause() instanceof DisposedException) {
					message = "exception.extrainfo.documents.openoffice.stop"; 
				}else if (e.getCause() != null) {
					message = e.getCause().getMessage();
				}else {
					message = ((ISPACException)e).getExtendedMessage(request.getLocale());
				}
			}else {
				message = e.getMessage();
			}
			throw new ISPACInfo(message,false);
		}

		return null;
	}
}