package ieci.tecdoc.sgm.admsistema.action;

import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.Aplicacion;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;
import ieci.tecdoc.sgm.core.services.geolocalizacion.ServicioGeoLocalizacion;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AccionesEntidadAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(AccionesEntidadAction.class);
	
	public static final String FORWARD_SIN_ACCION = "sinAccion";
	public static final String FORWARD_ADMINISTRAR = "administrar";
	public static final String FORWARD_MODIFICAR = "modificar";
	public static final String FORWARD_BUSCAR = "buscar";
	public static final String FORWARD_EXPORTAR= "exportar";
	public static final String FORWARD_IMPORTAR = "importar";
	public static final String FORWARD_CLONAR = "clonar";
	public static final String FORWARD_SEGREGAR = "segregar";
	public static final String FORWARD_MONITORIZAR = "monitorizar";
	public static final String FORWARD_NUEVA = "nueva";
	public static final String FORWARD_BORRAR = "borrar";
	public static final String FORWARD_CONFIGURAR = "configurar";
	public static final String FORWARD_ACCIONES_MULTIENTIDAD = "accionesMultientidad";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

			try {
				HttpSession session = request.getSession();
				
				String idEntidad = request.getParameter(Defs.PARAMETRO_ENTIDAD_SELECCIONADA);
				if (Utilidades.esNuloOVacio(idEntidad))
					idEntidad = "";
				String accion = request.getParameter(Defs.PARAMETRO_ACCION_ENTIDAD);
				if (Utilidades.esNuloOVacio(accion))
					accion = "";
				String busqueda = request.getParameter(Defs.PARAMETRO_BUSQUEDA_ENTIDAD);
				if (Utilidades.esNuloOVacio(busqueda))
					busqueda = "";
				String usuario = (String)session.getAttribute(Defs.PARAMETRO_NOMBRE_USUARIO_TRABAJO);
				if (Utilidades.esNuloOVacio(usuario))
					usuario = "";
				
				if (Defs.ACCION_ADMINISTRAR_ENTIDAD.equals(accion)) {
					ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
					Aplicacion[] aplicaciones = oServicio.getAplicaciones(usuario, idEntidad);
					request.setAttribute(Defs.PARAMETRO_APLICACIONES, aplicaciones);
					request.setAttribute(Defs.PARAMETRO_ID_ENTIDAD, idEntidad);
					ServicioEntidades oServicioEntidades = LocalizadorServicios.getServicioEntidades();
					Entidad entidad = oServicioEntidades.obtenerEntidad(idEntidad);
					request.setAttribute(Defs.PARAMETRO_NOMBRE_ENTIDAD, entidad.getNombreLargo());
					return mapping.findForward(FORWARD_ADMINISTRAR);
				} else if (Defs.ACCION_MODIFICAR_ENTIDAD.equals(accion)) {
					request.setAttribute(Defs.PARAMETRO_ID_ENTIDAD, idEntidad);
					ServicioGeoLocalizacion oServicio = LocalizadorServicios.getServicioGeoLocalizacion();
					//session.setAttribute(Defs.PARAMETRO_PROVINCIAS, oServicio.obtenerProvincias());
					session.setAttribute(Defs.PARAMETRO_PROVINCIAS, new ArrayList());
					session.setAttribute(Defs.PARAMETRO_MUNICIPIOS, new ArrayList());
					return mapping.findForward(FORWARD_MODIFICAR);
				} else if (Defs.ACCION_BUSCAR_ENTIDADES.equals(accion)) {
					request.setAttribute(Defs.PARAMETRO_BUSQUEDA_ENTIDAD, busqueda);
					return mapping.findForward(FORWARD_BUSCAR);
				} else if (Defs.ACCION_EXPORTAR_ENTIDAD.equals(accion)) {
					ServicioEntidades oServicio = LocalizadorServicios.getServicioEntidades();
					Entidad entidad = oServicio.obtenerEntidad(idEntidad);
					request.setAttribute(Defs.PARAMETRO_ENTIDAD, entidad);
					return mapping.findForward(FORWARD_EXPORTAR);
				} else if (Defs.ACCION_IMPORTAR_ENTIDAD.equals(accion)) {
					return mapping.findForward(FORWARD_IMPORTAR);
				} else if (Defs.ACCION_NUEVA_ENTIDAD.equals(accion)) {
					ServicioGeoLocalizacion oServicio = LocalizadorServicios.getServicioGeoLocalizacion();
					//session.setAttribute(Defs.PARAMETRO_PROVINCIAS, oServicio.obtenerProvincias());
					session.setAttribute(Defs.PARAMETRO_PROVINCIAS, new ArrayList());
					session.setAttribute(Defs.PARAMETRO_MUNICIPIOS, new ArrayList());
					session.setAttribute(Defs.ACTION_FORMULARIO_NUEVA_ENTIDAD, Defs.ACCION_NUEVA_ENTIDAD);
					return mapping.findForward(FORWARD_NUEVA);
				} else if (Defs.ACCION_CLONAR_ENTIDAD.equals(accion)) {
					ServicioEntidades oServicio = LocalizadorServicios.getServicioEntidades();
					Entidad entidad = oServicio.obtenerEntidad(idEntidad);
					session.setAttribute(Defs.PARAMETRO_ENTIDAD_EXPORTAR, entidad);
					ServicioGeoLocalizacion oServicioGeo = LocalizadorServicios.getServicioGeoLocalizacion();
					//session.setAttribute(Defs.PARAMETRO_PROVINCIAS, oServicioGeo.obtenerProvincias());
					session.setAttribute(Defs.PARAMETRO_PROVINCIAS, new ArrayList());
					session.setAttribute(Defs.PARAMETRO_MUNICIPIOS, new ArrayList());
					session.setAttribute(Defs.ACTION_FORMULARIO_NUEVA_ENTIDAD, Defs.ACCION_CLONAR_ENTIDAD);
					return mapping.findForward(FORWARD_CLONAR);
				} else if (Defs.ACCION_SEGREGAR_ENTIDAD.equals(accion)) {
					ServicioEntidades oServicio = LocalizadorServicios.getServicioEntidades();
					Entidad entidad = oServicio.obtenerEntidad(idEntidad);
					request.setAttribute(Defs.PARAMETRO_ENTIDAD, entidad);
					return mapping.findForward(FORWARD_SEGREGAR);
				} else if (Defs.ACCION_MONITORIZAR_ENTIDAD.equals(accion)) {
					return mapping.findForward(FORWARD_MONITORIZAR);
				} else if (Defs.ACCION_BORRAR_PROCESO.equals(accion)) {
					return mapping.findForward(FORWARD_BORRAR);
				} else if (Defs.ACCION_CONFIGURAR_SERVIDOR.equals(accion)) {
					return mapping.findForward(FORWARD_CONFIGURAR);
				} else if (Defs.ACCION_ACCIONES_MULTIENTIDAD.equals(accion)) {
					return mapping.findForward(FORWARD_ACCIONES_MULTIENTIDAD);
				} 

				return mapping.findForward(FORWARD_SIN_ACCION);
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.general");
				return mapping.findForward(FORWARD_SIN_ACCION);
			}
	}
}
