package ieci.tecdoc.sgm.admsistema.action.accionesmultientidad;

import ieci.tecdoc.sgm.admsistema.form.AccionMultientidadForm;
import ieci.tecdoc.sgm.admsistema.vo.AccionMultientidadVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;

public class ImportarProcedimientoAccionConfiguracion extends ConfiguracionAccionMultientidadBaseAccion implements
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
				return ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES;
			} else if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_ENTIDADES.equals(pasoActual)){
				return ConfiguracionAccionMultientidadConstants.PASO_SELECCION_FICHERO;
			} else if (ConfiguracionAccionMultientidadConstants.PASO_SELECCION_FICHERO.equals(pasoActual)){
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
	
	private static final Logger logger = Logger.getLogger(ImportarProcedimientoAccionConfiguracion.class);
	
}
