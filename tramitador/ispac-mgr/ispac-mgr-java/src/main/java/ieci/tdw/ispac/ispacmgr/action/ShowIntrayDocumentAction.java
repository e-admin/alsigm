package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInboxAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.sicres.vo.Annexe;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.BaseAction;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Acción que muestra un documento de un registro distribuido.
 *
 */
public class ShowIntrayDocumentAction extends BaseAction {

	/**
	 * Logger de la clase.
	 */
	private static Logger logger = Logger.getLogger(ShowIntrayDocumentAction.class);
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		
		IInboxAPI inboxAPI = session.getClientContext().getAPI().getInboxAPI();
		
		// Identificador de la distribución
		String intrayId = request.getParameter("intrayId");
		if (StringUtils.isBlank(intrayId)) {
			logger.error("Obligatorio indicar el id de intray");
			throw new ISPACInfo("Obligatorio indicar el id de intray");
		}
		
		// Identificador del documento
		String documentId = request.getParameter("documentId");
		if (StringUtils.isBlank(documentId)) {
			logger.error("Obligatorio indicar el id de documento");
			throw new ISPACException("Obligatorio indicar el id de documento");
		}
		
		Annexe[] anexos = inboxAPI.getAnnexes(intrayId);
		boolean found = false;
		
		String mimetype = "";
		Annexe anexo = null;
		for (int i=0; i<anexos.length && !found; i++){
			Annexe auxAnexo = anexos[i];
			if (auxAnexo.getId().equals(documentId)){
				found = true;
				anexo = auxAnexo;
				if (auxAnexo.getExt()!=null){
					mimetype = MimetypeMapping.getMimeType(auxAnexo.getExt());
				}
			}
		}
		
		if (anexo == null){
			logger.error("No se ha encontrado anexo con id = " + documentId);
			throw new ISPACInfo("No se ha encontrado anexo con id = "+documentId);
		}
		
		ServletOutputStream out = response.getOutputStream();
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0");
		response.setContentType(mimetype);
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + anexo.getName() + "\"");
		
		try {
			inboxAPI.getAnnexe(intrayId, documentId, out);
		} catch(ISPACException e) {
        	//Se saca el mensaje de error en la propia ventana, que habra sido lanzada con un popup
        	response.setContentType("text/html");
        	out.write(e.getCause().getMessage().getBytes());
		} finally{
			out.close();
		}
		
		return null;
	}

}
