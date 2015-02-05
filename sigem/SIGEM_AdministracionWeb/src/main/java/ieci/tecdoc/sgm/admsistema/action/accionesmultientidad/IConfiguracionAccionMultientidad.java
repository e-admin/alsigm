package ieci.tecdoc.sgm.admsistema.action.accionesmultientidad;

import ieci.tecdoc.sgm.admsistema.form.AccionMultientidadForm;
import ieci.tecdoc.sgm.admsistema.vo.AccionMultientidadVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;

/**
 * Interface para las action de configuracion de acciones
 * de multientidad
 * @author IECISA
 *
 */
public interface IConfiguracionAccionMultientidad {

	/**
	 * Devuelve el siguiente paso a partir del formulario actual y de los datos de la peticion
	 * @param mapping 
	 * @param form
	 * @param request
	 * @param response
	 * @param accionMultientidadVO VO para obtener los valores de todo el proceso de ejecucion de accion de multientidad
	 * @return siguiente paso a partir del formulario actual y de los datos de la peticion
	 */
	public String executeConfigAction(ActionMapping mapping, AccionMultientidadForm form, 
			HttpServletRequest request, HttpServletResponse response, AccionMultientidadVO accionMultientidadVO);
}
