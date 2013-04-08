package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.impl.SessionAPI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UploadRepoFilesAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {

		//Establecer el tipo de documento si previamente se ha seleccionado
		String documentTypeId = request.getParameter("documentTypeId");
		if (documentTypeId != null) {
			request.setAttribute("documentTypeId", documentTypeId);
		}

		return mapping.findForward("success");
	}
	
}
