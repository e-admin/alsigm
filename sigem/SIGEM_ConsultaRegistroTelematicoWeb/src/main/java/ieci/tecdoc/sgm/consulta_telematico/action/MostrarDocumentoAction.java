package ieci.tecdoc.sgm.consulta_telematico.action;

import ieci.tecdoc.sgm.consulta_telematico.utils.Defs;
import ieci.tecdoc.sgm.consulta_telematico.utils.Utils;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentoInfo;
import ieci.tecdoc.sgm.core.services.repositorio.ServicioRepositorioDocumentosTramitacion;
import ieci.tecdoc.sgm.core.services.telematico.Registro;
import ieci.tecdoc.sgm.core.services.telematico.RegistroDocumento;
import ieci.tecdoc.sgm.core.services.telematico.RegistroTelematicoException;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;
import ieci.tecdoc.sgm.registro.exception.RegistroCodigosError;

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

		HttpSession session = request.getSession();

		ServletOutputStream out = response.getOutputStream();

		try {
			ServicioRegistroTelematico oServicioRegistroTelematico = LocalizadorServicios.getServicioRegistroTelematico();

			String idEntidad = (String)session.getAttribute(Defs.ENTIDAD_ID);
	    	if (idEntidad == null || "".equals(idEntidad)) {
	    		//return mapping.findForward(Defs.LOGIN_FORWARD);
	    	}

	    	String idSesion = (String)session.getAttribute(Defs.SESION_ID);
	    	if (idSesion == null || "".equals(idSesion)) {
	    		//return mapping.findForward(Defs.LOGIN_FORWARD);
	    	}

			String numRegistro = request.getParameter(Defs.NUMERO_REGISTRO);
			if (numRegistro == null || "".equals(numRegistro))
				return null;

			String code = request.getParameter(Defs.CODE);
			if (code == null || "".equals(code))
				return null;

			String extension = request.getParameter(Defs.EXTENSION);

			// Obtener el registro del documento a mostrar
			Registro registro = oServicioRegistroTelematico.obtenerRegistro(idSesion, numRegistro, Utils.obtenerEntidad(idEntidad));

			// Comprobar que el registro pertenezca al usuario conectado
			String cnifUsuario = (String)session.getAttribute(Defs.CNIF_USUARIO);
			if (!registro.getSenderId().equalsIgnoreCase(cnifUsuario)) {

				StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_EREGISTRY_ERROR_PREFIX);
				cCodigo.append(String.valueOf(RegistroCodigosError.EC_DOCUMENTO_SIN_PERMISOS));
				throw new RegistroTelematicoException(Long.valueOf(cCodigo.toString()).longValue());
			}

			// Obtener el documento a mostrar
			RegistroDocumento registroDocumento = oServicioRegistroTelematico.obtenerDocumentoRegistro(idSesion, numRegistro, code, Utils.obtenerEntidad(idEntidad));
			ServicioRepositorioDocumentosTramitacion oServicioRepositorioDocumentosTramitacion = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
			DocumentoInfo documentoInfo = oServicioRepositorioDocumentosTramitacion.retrieveDocument(idSesion, registroDocumento.getGuid(), Utils.obtenerEntidad(idEntidad));
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
