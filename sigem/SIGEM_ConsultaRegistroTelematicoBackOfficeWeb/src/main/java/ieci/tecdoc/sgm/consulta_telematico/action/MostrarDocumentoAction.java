package ieci.tecdoc.sgm.consulta_telematico.action;

import ieci.tecdoc.sgm.consulta_telematico.utils.Defs;
import ieci.tecdoc.sgm.consulta_telematico.utils.Utils;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentoInfo;
import ieci.tecdoc.sgm.core.services.repositorio.ServicioRepositorioDocumentosTramitacion;
import ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Clase que descargar un documento asociado a un registro
 *
 */
public class MostrarDocumentoAction extends ConsultaRegistroTelematicoWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Información de la sesión del usuario
		HttpSession session = request.getSession();
		String idEntidad = (String) session.getAttribute(Defs.ENTIDAD_ID);
		String idSession = (String) session.getAttribute(Defs.SESION_ID);

		ServletOutputStream out = response.getOutputStream();

		try {
			ServicioRegistroTelematico oServicioRegistroTelematico = LocalizadorServicios.getServicioRegistroTelematico();

			String numRegistro = request.getParameter(Defs.NUMERO_REGISTRO);
			if (numRegistro == null || "".equals(numRegistro))
				return null;

			String code = request.getParameter(Defs.CODE);
			if (code == null || "".equals(code))
				return null;

			String extension = request.getParameter(Defs.EXTENSION);

			// Obtener el documento a mostrar
			RegistroDocumento registroDocumento = oServicioRegistroTelematico.obtenerDocumentoRegistro(idSession, numRegistro, code, Utils.obtenerEntidad(idEntidad));
			ServicioRepositorioDocumentosTramitacion oServicioRepositorioDocumentosTramitacion = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
			DocumentoInfo documentoInfo = oServicioRepositorioDocumentosTramitacion.retrieveDocument(idSession, registroDocumento.getGuid(), Utils.obtenerEntidad(idEntidad));
			byte[] documento = documentoInfo.getContent();

			response.setHeader("Pragma", "public");
	    	response.setHeader("Cache-Control", "max-age=0");
            response.setContentType(documentoInfo.getMimeType());
            response.setHeader("Content-Transfer-Encoding", "binary");
           	response.setHeader("Content-Disposition", "attachment; filename=\"" + code + "_" + numRegistro + "." + extension + "\"");

            response.setContentLength(documento.length);
            out.write(documento, 0, documento.length);
        }
		catch (Exception e) {
        	response.setContentType("text/html");
        	out.write(e.getMessage().getBytes());
        }
		finally {
        	out.flush();
        	out.close();
        }

        return null;
   	}
}
