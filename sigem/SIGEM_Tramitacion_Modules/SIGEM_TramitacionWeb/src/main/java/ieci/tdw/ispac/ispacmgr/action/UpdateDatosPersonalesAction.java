package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para buscar datos de padron de un participante
 * 
 * Parámetros:
 * - field => nombre del campo nif en el formulario de entidad
 *
 */
public class UpdateDatosPersonalesAction extends BaseAction {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(UpdateDatosPersonalesAction.class);

	@Override
	public ActionForward executeAction(ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response,
			SessionAPI session)
			throws Exception {
		
		throw new ISPACInfo("exception.info.not.implemented", false);
	}

}
