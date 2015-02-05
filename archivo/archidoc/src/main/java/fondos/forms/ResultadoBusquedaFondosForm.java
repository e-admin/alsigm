package fondos.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.ArrayUtils;
import common.util.StringUtils;

public class ResultadoBusquedaFondosForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String[] ids = new String[0];
	private String accion = null;

	/**
	 * @return the ids
	 */
	public String[] getIds() {
		return ids;
	}

	/**
	 * @param ids
	 *            the ids to set
	 */
	public void setIds(String[] ids) {
		this.ids = ids;
	}

	/**
	 * @return the accion
	 */
	public String getAccion() {
		return accion;
	}

	/**
	 * @param accion
	 *            the accion to set
	 */
	public void setAccion(String accion) {
		this.accion = accion;
	}

	public ActionErrors validar(HttpServletRequest request, ActionErrors errors) {

		// boolean permitirVariosElementos = true;

		// Validar que se ha seleccionado accion
		if (StringUtils.isNotEmpty(this.accion)) {
			if (ArrayUtils.isEmpty(this.ids)) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
						Constants.ERROR_NECESARIO_SELECCIONAR_UN_ELEMENTO));
			}
		} else {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ACCION_KEY,
									request.getLocale())));
		}

		return errors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.archigest.framework.web.action.ArchigestActionForm#reset(org.apache
	 * .struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.ids = new String[0];
		this.accion = null;

		super.reset(mapping, request);
	}

}
