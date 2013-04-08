package ieci.tecdoc.sgm.admsistema.action.accionesmultientidad;

import ieci.tecdoc.sgm.admsistema.action.AdministracionWebAction;
import ieci.tecdoc.sgm.admsistema.form.AccionMultientidadForm;
import ieci.tecdoc.sgm.admsistema.proceso.accionmultientidad.EjecutarAccion;
import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;
import ieci.tecdoc.sgm.admsistema.vo.AccionMultientidadVO;
import ieci.tecdoc.sgm.admsistema.vo.OpcionConfiguracionVO;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.AccionMultientidad;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.util.file.FileUtils;

/**
 *
 * Action para ejecutar la configuracion de las acciones de multientidad
 * @author IECISA
 *
 */
public class EjecutarConfiguracionAccionMultientidadAction extends
		AdministracionWebAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		AccionMultientidadForm accionMultientidadForm = (AccionMultientidadForm) form;
		IConfiguracionAccionMultientidad configuracionAccionMultientidad;
		try {
			configuracionAccionMultientidad = instanceConfiguracionAccion(accionMultientidadForm);
			preProcessPasoFormData(accionMultientidadForm, request, response);
			AccionMultientidadVO accionMultientidadVO = (AccionMultientidadVO) request.getSession().getAttribute(Defs.ACCION_MULTIENTIDAD_WIZARD);
			String paso = configuracionAccionMultientidad.executeConfigAction(mapping, accionMultientidadForm, request, response, accionMultientidadVO);
			if (paso==null){
				request.setAttribute(Defs.MENSAJE_ERROR, "acciones.multientidad.mensaje.error.configuracion.accion");
				logger.error("La acción de configuración ha devuelto un paso nulo.");
			} else {
				accionMultientidadForm.setPaso(paso);
				postProcessPasoFormData(accionMultientidadForm, request, response);

				if (ConfiguracionAccionMultientidadConstants.PASO_EJECUCION_CONFIGURACION.equals(paso)){
					boolean ejecucion = executeAccionMultientidad(request, accionMultientidadVO);
					if (!ejecucion){
						request.setAttribute(Defs.MENSAJE_ERROR, "acciones.multientidad.mensaje.error.ejecucion.accion");
						return mapping.findForward(ConfiguracionAccionMultientidadConstants.FORWARD_ERROR_EJECUCION);
					} else {
						request.setAttribute(Defs.MENSAJE_INFORMATIVO, "mensaje.informativo.proceso.accion.multientidad.correcto");
					}
				}

				return getForwardFromPaso(mapping, request, paso);
			}
		} catch (Exception e) {
			logger.error(e);
			request.setAttribute(Defs.MENSAJE_ERROR, "acciones.multientidad.mensaje.error.configuracion.accion");
		}

		return mapping.findForward(ConfiguracionAccionMultientidadConstants.FORWARD_ERROR_CONFIGURACION);
	}

	/**
	 * Permite instanciar la accion de multientidad necesaria
	 * @param accionMultientidadForm
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private IConfiguracionAccionMultientidad instanceConfiguracionAccion(AccionMultientidadForm accionMultientidadForm) throws Exception{
		ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
		AccionMultientidad accionMultientidad = oServicio.getAccionMultientidad(accionMultientidadForm.getIdAccion());
		Class claseConfiguradora = Class.forName(accionMultientidad.getClaseConfiguradora());
		return (IConfiguracionAccionMultientidad) claseConfiguradora.newInstance();
	}

	/**
	 * Preprocesa el paso actual para almacenar los valores necesarios
	 *
	 * @param accionMultientidadForm
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected void preProcessPasoFormData(AccionMultientidadForm accionMultientidadForm, HttpServletRequest request, HttpServletResponse response) throws Exception{
		AccionMultientidadVO accionMultientidadVO = null;
		if (StringUtils.isEmpty(accionMultientidadForm.getPaso())){
			ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
			AccionMultientidad accionMultientidad = oServicio.getAccionMultientidad(accionMultientidadForm.getIdAccion());
			accionMultientidadVO = new AccionMultientidadVO();
			accionMultientidadVO.setIdAccion(accionMultientidadForm.getIdAccion());
			accionMultientidadVO.setNombreAccion(accionMultientidad.getNombre());
			accionMultientidadVO.setClaseConfiguradora(accionMultientidad.getClaseConfiguradora());
			accionMultientidadVO.setClaseEjecutora(accionMultientidad.getClaseEjecutora());
			request.getSession().setAttribute(Defs.ACCION_MULTIENTIDAD_WIZARD, accionMultientidadVO);
		} else {
			accionMultientidadVO = (AccionMultientidadVO) request.getSession().getAttribute(Defs.ACCION_MULTIENTIDAD_WIZARD);
		}

		if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES.equals(accionMultientidadForm.getPaso())){
			accionMultientidadVO.setEntidades(accionMultientidadForm.getEntidades());
		} else if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES_ORIGEN.equals(accionMultientidadForm.getPaso())){
			accionMultientidadVO.setEntidadesOrigen(accionMultientidadForm.getEntidades());
		} else if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES_DESTINO.equals(accionMultientidadForm.getPaso())){
			accionMultientidadVO.setEntidadesDestino(accionMultientidadForm.getEntidades());
		} else if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_FICHERO.equals(accionMultientidadForm.getPaso())){
			File ficheroTemporal = File.createTempFile(accionMultientidadVO.getIdAccion()+"-temp", ".tmp");

			OutputStream tmpFileOutputStream = null;
			try {
				tmpFileOutputStream = new FileOutputStream(ficheroTemporal);
				FileUtils.copy(accionMultientidadForm.getFichero().getInputStream(), tmpFileOutputStream);
			} finally {
				if (tmpFileOutputStream != null) {
					try {
						tmpFileOutputStream.flush();
						tmpFileOutputStream.close();
					} catch (Exception e){
					}
				}
			}

			accionMultientidadVO.setFicheroTemporal(ficheroTemporal.getAbsolutePath());
			accionMultientidadVO.setNombreFicheroTemporal(accionMultientidadForm.getFichero().getFileName());
		} else if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_OPCION.equals(accionMultientidadForm.getPaso())){
			accionMultientidadVO.setOpcion(accionMultientidadForm.getOpcion());
		}

	}

	/**
	 * Carga los datos necesarios para el siguiente forward
	 *
	 * @param accionMultientidadForm
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	protected void postProcessPasoFormData(AccionMultientidadForm accionMultientidadForm, HttpServletRequest request, HttpServletResponse response) throws Exception{
		if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES.equals(accionMultientidadForm.getPaso())||
				ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES_ORIGEN.equals(accionMultientidadForm.getPaso())||
				ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES_DESTINO.equals(accionMultientidadForm.getPaso())){
			ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
			String usuario = (String)request.getSession().getAttribute(Defs.PARAMETRO_NOMBRE_USUARIO_TRABAJO);
			List entidades = Utilidades.entidadesAdministrar(usuario, oServicio.getEntidades(usuario));

			request.setAttribute(Defs.LISTADO_ENTIDADES, entidades);
			if ((entidades!=null)&&(entidades.size()>0)){
				Entidad entidad = (Entidad) entidades.get(0);
				accionMultientidadForm.setEntidades(new String [] {entidad.getIdentificador()});
			}

			if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES.equals(accionMultientidadForm.getPaso())){
				request.setAttribute(Defs.TIPO_LISTADO_ENTIDADES, Defs.TIPO_LISTADO_ENTIDADES_STANDARD);
			} else if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES_ORIGEN.equals(accionMultientidadForm.getPaso())){
				request.setAttribute(Defs.TIPO_LISTADO_ENTIDADES, Defs.TIPO_LISTADO_ENTIDADES_ORIGEN);
			} else if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES_DESTINO.equals(accionMultientidadForm.getPaso())){
				request.setAttribute(Defs.TIPO_LISTADO_ENTIDADES, Defs.TIPO_LISTADO_ENTIDADES_DESTINO);
			}
		} else if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_OPCION.equals(accionMultientidadForm.getPaso())){
			AccionMultientidadVO accionMultientidadVO = (AccionMultientidadVO) request.getSession().getAttribute(Defs.ACCION_MULTIENTIDAD_WIZARD);
			accionMultientidadVO.setOpcionesConfiguracion(accionMultientidadForm.getOpcionesConfiguracion());
			if ((accionMultientidadForm.getOpcionesConfiguracion()!=null)&&(accionMultientidadForm.getOpcionesConfiguracion().length>0)){
				OpcionConfiguracionVO opcion = (OpcionConfiguracionVO) accionMultientidadForm.getOpcionesConfiguracion()[0];
				accionMultientidadForm.setOpcion(opcion.getId());
			}
		}
	}

	/**
	 * Permite obtener el siguiente forward a partir del paso actual
	 * @param mapping
	 * @param paso
	 * @return siguiente forward a partir del paso actual
	 */
	protected ActionForward getForwardFromPaso(ActionMapping mapping, HttpServletRequest request, String paso){
		String forward = null;
		if (!StringUtils.isEmpty(paso)){
			if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES.equals(paso)){
				forward = ConfiguracionAccionMultientidadConstants.FORWARD_SELECCION_ENTIDADES;
			} else if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES_ORIGEN.equals(paso)){
				forward = ConfiguracionAccionMultientidadConstants.FORWARD_SELECCION_ENTIDADES_ORIGEN;
			} else if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES_DESTINO.equals(paso)){
				forward = ConfiguracionAccionMultientidadConstants.FORWARD_SELECCION_ENTIDADES_DESTINO;
			} else if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_FICHERO.equals(paso)){
				forward = ConfiguracionAccionMultientidadConstants.FORWARD_SELECCION_FICHERO;
			} else if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_OPCION.equals(paso)){
				forward = ConfiguracionAccionMultientidadConstants.FORWARD_SELECCION_OPCION;
			} else if (ConfiguracionAccionMultientidadConstants.PASO_RESUMEN_CONFIGURACION.equals(paso)){
				forward = ConfiguracionAccionMultientidadConstants.FORWARD_RESUMEN_CONFIGURACION;
			} else if (ConfiguracionAccionMultientidadConstants.PASO_EJECUCION_CONFIGURACION.equals(paso)){
				forward = ConfiguracionAccionMultientidadConstants.FORWARD_EJECUCION_CONFIGURACION;
			} else {
				forward = ConfiguracionAccionMultientidadConstants.FORWARD_ERROR_CONFIGURACION;
				request.setAttribute(Defs.MENSAJE_ERROR, "acciones.multientidad.mensaje.error.configuracion.accion");
				logger.error("La acción de configuración ha devuelto un paso no válido");
			}
		} else {
			forward = ConfiguracionAccionMultientidadConstants.FORWARD_ERROR_CONFIGURACION;
			request.setAttribute(Defs.MENSAJE_ERROR, "acciones.multientidad.mensaje.error.configuracion.accion");
			logger.error("La acción de configuración ha devuelto un paso vacío");
		}
		return mapping.findForward(forward);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected boolean executeAccionMultientidad(HttpServletRequest request, AccionMultientidadVO accionMultientidadVO){
		Map params = new HashMap();
		params.put(EjecutarAccion.PARAM_ACCION_MULTIENTIDAD_VO, accionMultientidadVO);
		params.put(EjecutarAccion.PARAM_ACCION_MULTIENTIDAD_NOMBRE_CLASE_EJECUTORA, accionMultientidadVO.getClaseEjecutora());
		params.put(EjecutarAccion.PARAM_SESION_APP_ADMINISTRACION, AutenticacionAdministracion.obtenerDatos(request));
		return EjecutarAccion.ejecutarAccion(params);
	}

	private static final Logger logger = Logger.getLogger(EjecutarConfiguracionAccionMultientidadAction.class);
}
