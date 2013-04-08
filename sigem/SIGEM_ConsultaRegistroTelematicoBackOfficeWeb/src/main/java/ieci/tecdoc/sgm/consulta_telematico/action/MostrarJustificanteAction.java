package ieci.tecdoc.sgm.consulta_telematico.action;

import ieci.tecdoc.sgm.consulta_telematico.utils.Defs;
import ieci.tecdoc.sgm.consulta_telematico.utils.Utils;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentoInfo;
import ieci.tecdoc.sgm.core.services.repositorio.ServicioRepositorioDocumentosTramitacion;
import ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;
import ieci.tecdoc.sgm.registro.util.Definiciones;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Clase que desconecta a un usuario del Sistema. Elimina la entrada de la tabla
 * de sesiones y los posibles ficheros temporales creados (en caso que existan)
 * @author José Antonio Nogales
 *
 */
public class MostrarJustificanteAction extends ConsultaRegistroTelematicoWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Información de la sesión del usuario
		HttpSession session = request.getSession();
		String idEntidad = (String) session.getAttribute(Defs.ENTIDAD_ID);
		String idSession = (String) session.getAttribute(Defs.SESION_ID);

		ServletOutputStream out = response.getOutputStream();

		try {
			ServicioRegistroTelematico oServicio = LocalizadorServicios.getServicioRegistroTelematico();

			String numRegistro = request.getParameter(Defs.JUSTIFICANTE);
			if (numRegistro == null || "".equals(numRegistro))
				return null;

			response.setHeader("Pragma", "public");
	    	response.setHeader("Cache-Control", "max-age=0");
            response.setContentType(Defs.MIMETYPE_JUSTIFICANTE);
            response.setHeader("Content-Transfer-Encoding", "binary");
           	response.setHeader("Content-Disposition", "attachment; filename=\"Justificante_" + numRegistro + "." + Defs.EXTENSION_JUSTIFICANTE + "\"");

           	// Obtener el justificante a mostrar
			//byte[] justificante = oServicio.obtenerJustificanteRegistro(sesionBO.getIdSesion(), numRegistro, Utils.obtenerEntidad(idEntidad));
			RegistroDocumento registroDocumento = oServicio.obtenerDocumentoRegistro(idSession, numRegistro, Definiciones.REGISTRY_RECEIPT_CODE, Utils.obtenerEntidad(idEntidad));
			ServicioRepositorioDocumentosTramitacion oServicioRepositorioDocumentosTramitacion = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
			DocumentoInfo documentoInfo = oServicioRepositorioDocumentosTramitacion.retrieveDocument(idSession, registroDocumento.getGuid(), Utils.obtenerEntidad(idEntidad));
			byte[] justificante = documentoInfo.getContent();

            response.setContentLength(justificante.length);
            out.write(justificante, 0, justificante.length);
        }
		catch(Exception e) {
        	response.setContentType("text/html");
        	out.write(e.getMessage().getBytes());
        }  finally{
        	out.flush();
        	out.close();
        }

	    return null;
   	}
}
