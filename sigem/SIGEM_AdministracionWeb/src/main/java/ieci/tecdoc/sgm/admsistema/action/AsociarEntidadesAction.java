package ieci.tecdoc.sgm.admsistema.action;

import java.util.List;

import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AsociarEntidadesAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(AsociarEntidadesAction.class);
	
	public static final String FORWARD_SUCCESS = "success";
	public static final String FORWARD_ERROR = "failure";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

			try {
				HttpSession session = request.getSession();

				String entidadesYaAsociadas = (String)request.getParameter(Defs.PARAMETRO_ENTIDADES_YA_ASOCIADAS);
				if (Utilidades.esNuloOVacio(entidadesYaAsociadas))
					entidadesYaAsociadas = "";
				
				String entidadesMarcadas = (String)request.getParameter(Defs.PARAMETRO_ENTIDADES_MARCADAS);
				if (Utilidades.esNuloOVacio(entidadesMarcadas))
					entidadesMarcadas = "";
				
				String tipoAccionEntidades = (String)request.getParameter(Defs.PARAMETRO_TIPO_ACCION_ENTIDADES);
				if (Utilidades.esNuloOVacio(tipoAccionEntidades))
					tipoAccionEntidades = "";
				
				if (Defs.ACCION_NUEVO_ASOCIAR_ENTIDAD.equals(tipoAccionEntidades)) {
					String username = (String)session.getAttribute(Defs.PARAMETRO_NOMBRE_USUARIO_TRABAJO);
					ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
					List entidades = Utilidades.entidadesAdministrar(username, oServicio.getEntidades(username));
					if (entidades != null && !Utilidades.esNuloOVacio(entidadesYaAsociadas)) {
						for(int i=entidades.size()-1; i>=0; i--) {
							if (entidadesYaAsociadas.indexOf(((Entidad)entidades.get(i)).getIdentificador() + ",") != -1) {
								entidades.remove(i);
							}						
						}
					}
					request.setAttribute(Defs.PARAMETRO_TIPO_ACCION_ENTIDADES, Defs.ACCION_NUEVO_ASOCIAR_ENTIDAD);
					request.getSession().setAttribute(Defs.PARAMETRO_ADMINISTRADOR_ENTIDADES_A_ELEGIR, entidades);
				} else if (Defs.ACCION_NUEVO_MODIFICAR_ENTIDAD.equals(tipoAccionEntidades)) {
					request.setAttribute(Defs.PARAMETRO_TIPO_ACCION_ENTIDADES, Defs.ACCION_NUEVO_MODIFICAR_ENTIDAD);
				}
				
				return mapping.findForward(FORWARD_SUCCESS);
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				return mapping.findForward(FORWARD_ERROR);
			}
	}
}
