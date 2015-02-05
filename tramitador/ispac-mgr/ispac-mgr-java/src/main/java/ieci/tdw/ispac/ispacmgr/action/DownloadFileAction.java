package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.util.DocumentUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DownloadFileAction extends BaseAction {
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		// Identificador del documento
		String sfileid = request.getParameter("fileId");
		if (StringUtils.isNotBlank(sfileid)) {

			// Descarga del documento
			DocumentUtil.downloadDocument(response, session, Integer.parseInt(sfileid));
		}

		return null;
	}
}
