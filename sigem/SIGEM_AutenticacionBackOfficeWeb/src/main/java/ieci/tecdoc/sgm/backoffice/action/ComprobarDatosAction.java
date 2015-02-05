package ieci.tecdoc.sgm.backoffice.action;

import ieci.tecdoc.sgm.backoffice.form.EntidadAccesoForm;
import ieci.tecdoc.sgm.backoffice.utils.Defs;
import ieci.tecdoc.sgm.backoffice.utils.Utilidades;
import ieci.tecdoc.sgm.core.admin.web.AdministracionHelper;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ServicioGestionUsuariosBackOffice;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ComprobarDatosAction extends Action{

	private static final Logger logger = Logger.getLogger(ComprobarDatosAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		try{
			EntidadAccesoForm formEntidad =  (EntidadAccesoForm)form;

			HttpSession session = request.getSession();
			String idEntidad = null, idAplicacion = null, usuario = null, password = null, key = null, idEntidadAlmacenada = null;

			key = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO);
			if (Utilidades.isNuloOVacio(key)) {
				key = new String("");
			}

			idEntidadAlmacenada = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD_ALMACENADA);
			if (Utilidades.isNuloOVacio(idEntidadAlmacenada)) {
				idEntidadAlmacenada = new String("");
			}

			if (formEntidad != null)
				idEntidad = formEntidad.getEntidadId();
			if (Utilidades.isNuloOVacio(idEntidad)) {
				idEntidad = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD);
				if (Utilidades.isNuloOVacio(idEntidad)) {
					idEntidad = new String("");
				}
			}

			idAplicacion = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_APLICACION);
			if (Utilidades.isNuloOVacio(idAplicacion)) {
				idAplicacion = new String("");
			}

			session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD, idEntidad);

			if (!Utilidades.isNuloOVacio(key)) {
				if ((!Utilidades.isNuloOVacio(idEntidad) || !Utilidades.isNuloOVacio(idEntidadAlmacenada)) && !Utilidades.isNuloOVacio(idAplicacion)) {
					if (idEntidadAlmacenada.equals(idEntidad)) {
						session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD, idEntidad);
						ServicioGestionUsuariosBackOffice oServicio = LocalizadorServicios.getServicioAutenticacionUsuariosBackOffice();
						String url = oServicio.obtenerDireccionAplicacion(idAplicacion);
						url = AutenticacionBackOffice.comprobarURL(request, url);
						request.setAttribute(Defs.PARAMETRO_URL, url);
						return mapping.findForward("redirigir");
					} else {
						return mapping.findForward("success");
					}
				}
			}

			session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD, idEntidad);
			session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_APLICACION, idAplicacion);

			if (Utilidades.isNuloOVacio(idEntidad)){
				List oLista = AdministracionHelper.obtenerListaEntidades();
				if(oLista.size()==1) {
					Entidad oEntidad = (Entidad)oLista.get(0);
					session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD, oEntidad.getIdentificador());
				} else {
					request.setAttribute("entidades", oLista);
			   		return mapping.findForward("entidades");
				}
			}

			return mapping.findForward("success");
		}catch(Exception e){
			logger.error("Se ha producido un error en el login", e.fillInStackTrace());
			request.setAttribute("invalid_user", "true");
			return mapping.findForward("success");
	   	}

	}


}
