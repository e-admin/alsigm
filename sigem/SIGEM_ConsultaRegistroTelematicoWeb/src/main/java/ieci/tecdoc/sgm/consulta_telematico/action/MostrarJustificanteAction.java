package ieci.tecdoc.sgm.consulta_telematico.action;

import ieci.tecdoc.sgm.consulta_telematico.utils.Defs;
import ieci.tecdoc.sgm.consulta_telematico.utils.Utils;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.telematico.Registro;
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
 * Clase que desconecta a un usuario del Sistema. Elimina la entrada de la tabla
 * de sesiones y los posibles ficheros temporales creados (en caso que existan)
 * @author José Antonio Nogales
 *
 */
public class MostrarJustificanteAction extends ConsultaRegistroTelematicoWebAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		ServletOutputStream out = response.getOutputStream();

		try {
			ServicioRegistroTelematico oServicio = LocalizadorServicios.getServicioRegistroTelematico();

			String idEntidad = (String)session.getAttribute(Defs.ENTIDAD_ID);
	    	if (idEntidad == null || "".equals(idEntidad)) {
	    		//return mapping.findForward(Defs.LOGIN_FORWARD);
	    	}

	    	String idSesion = (String)session.getAttribute(Defs.SESION_ID);
	    	if (idSesion == null || "".equals(idSesion)) {
	    		//return mapping.findForward(Defs.LOGIN_FORWARD);
	    	}

			String numRegistro = request.getParameter(Defs.JUSTIFICANTE);
			if (numRegistro == null || "".equals(numRegistro))
				return null;

			// Comprobar que el registro pertenezca al usuario conectado
			String cnifUsuario = (String)session.getAttribute(Defs.CNIF_USUARIO);
			Registro registro = oServicio.obtenerRegistro(idSesion, numRegistro, Utils.obtenerEntidad(idEntidad));
			if (!registro.getSenderId().equalsIgnoreCase(cnifUsuario)) {

				StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_EREGISTRY_ERROR_PREFIX);
				cCodigo.append(String.valueOf(RegistroCodigosError.EC_JUSTIFICANTE_SIN_PERMISOS));
				throw new RegistroTelematicoException(Long.valueOf(cCodigo.toString()).longValue());
			}

			response.setHeader("Pragma", "public");
	    	response.setHeader("Cache-Control", "max-age=0");
            response.setContentType(Defs.MIMETYPE_JUSTIFICANTE);
            response.setHeader("Content-Transfer-Encoding", "binary");
           	response.setHeader("Content-Disposition", "attachment; filename=\"Justificante_" + numRegistro + "." + Defs.EXTENSION_JUSTIFICANTE + "\"");

           	// Obtener el justificante a mostrar
			byte[] justificante = oServicio.obtenerJustificanteRegistro(idSesion, numRegistro, Utils.obtenerEntidad(idEntidad));
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
