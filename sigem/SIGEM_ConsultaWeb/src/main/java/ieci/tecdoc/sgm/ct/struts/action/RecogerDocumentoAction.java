package ieci.tecdoc.sgm.ct.struts.action;
/*
 *  $Id: RecogerDocumentoAction.java,v 1.2.2.1 2008/02/05 13:31:39 jconca Exp $
 */

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.consulta.InfoDocumento;
import ieci.tecdoc.sgm.core.services.consulta.ServicioConsultaExpedientes;
import ieci.tecdoc.sgm.ct.utilities.Misc;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RecogerDocumentoAction extends ConsultaWebAction {

	/**
	 * Se sobrescribe el metodo execute de la clase Action
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward executeAction(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ServletOutputStream output = null;

		try {
			ServicioConsultaExpedientes oServicio = LocalizadorServicios.getServicioConsultaExpedientes();

			InfoDocumento documento = oServicio.recogerDocumento(request.getParameter("guid"), Misc.obtenerEntidad(request));

			byte[] receipt = documento.getContent();
			String mimeType = documento.getMimeType();
			response.setHeader("Content-disposition", "attachment; filename=\"Documento_" + request.getParameter("guid") + "." + documento.getExtension() + "\"");
			response.setContentLength(receipt.length);
			response.setContentType(mimeType);

			output = response.getOutputStream();
			output.write(receipt, 0, receipt.length);

		} catch (Exception ex) {

			request.setAttribute(Misc.MENSAJE_ERROR, ex.getMessage());
			return mapping.findForward("Failure");
		} finally {
			if (output != null) {
				output.flush();
				output.close();
			}
		}

		return null;
	}

}
