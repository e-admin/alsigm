package ieci.tecdoc.sgm.admsistema.action.accionesmultientidad;

import ieci.tecdoc.sgm.admsistema.form.AccionMultientidadForm;
import ieci.tecdoc.sgm.admsistema.vo.AccionMultientidadVO;
import ieci.tecdoc.sgm.admsistema.vo.OpcionConfiguracionVO;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;

public class CompararImportarReglasAccionConfiguracion extends ConfiguracionAccionMultientidadBaseAccion implements
		IConfiguracionAccionMultientidad {

	/**
	 * {@inheritDoc}
	 * @see ieci.tecdoc.sgm.admsistema.action.accionesmultientidad.IConfiguracionAccionMultientidad#executeConfigAction(org.apache.struts.action.ActionMapping, ieci.tecdoc.sgm.admsistema.form.AccionMultientidadForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, ieci.tecdoc.sgm.admsistema.vo.AccionMultientidadVO)
	 */
	public String executeConfigAction(ActionMapping mapping,
			AccionMultientidadForm form, HttpServletRequest request,
			HttpServletResponse response, AccionMultientidadVO accionMultientidadVO) {
		String pasoActual = form.getPaso();
		try {
			if (StringUtils.isEmpty(pasoActual)){
				return ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES_ORIGEN;
			} else if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES_ORIGEN.equals(pasoActual)){
				return ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES_DESTINO;
			} else if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES_DESTINO.equals(pasoActual)){
				ResourceBundle rb = ResourceBundle.getBundle("ieci/tecdoc/sgm/admsistema/resources/AdministracionMessage", request.getLocale());
				OpcionConfiguracionVO[] opcionesConfiguracion = new OpcionConfiguracionVO[2];
				opcionesConfiguracion[0] = new OpcionConfiguracionVO(ID_COMPARAR, rb.getString("acciones.multientidad.accion.comparar.importar.reglas.opcion.comparar"));
				opcionesConfiguracion[1] = new OpcionConfiguracionVO(ID_IMPORTAR, rb.getString("acciones.multientidad.accion.comparar.importar.reglas.opcion.importar"));
				form.setOpcionesConfiguracion(opcionesConfiguracion);
				return ConfiguracionAccionMultientidadConstants.PASO_SELECCION_OPCION;
			} else if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_OPCION.equals(pasoActual)){
				form.setResumenConfiguracion(generateResumenConfiguracion(accionMultientidadVO, request));
				return ConfiguracionAccionMultientidadConstants.PASO_RESUMEN_CONFIGURACION;
			} else if (ConfiguracionAccionMultientidadConstants.PASO_RESUMEN_CONFIGURACION.equals(pasoActual)){
				return ConfiguracionAccionMultientidadConstants.PASO_EJECUCION_CONFIGURACION;
			}
			return null;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public static final String ID_COMPARAR = "1";
	public static final String ID_IMPORTAR = "2";

	private static final Logger logger = Logger.getLogger(CompararImportarReglasAccionConfiguracion.class);

}
