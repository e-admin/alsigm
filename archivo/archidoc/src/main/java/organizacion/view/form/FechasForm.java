package organizacion.view.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import common.forms.CustomForm;
import common.util.DateUtils;

public class FechasForm extends CustomForm {

	private static final long serialVersionUID = 8096882810005214215L;

	private String inicio;
	private String fin;

	/**
	 * @return el fin
	 */
	public String getFin() {
		return fin;
	}

	/**
	 * @param fin
	 *            el fin a establecer
	 */
	public void setFin(String fin) {
		this.fin = fin;
	}

	/**
	 * @return el inicio
	 */
	public String getInicio() {
		return inicio;
	}

	/**
	 * @param inicio
	 *            el inicio a establecer
	 */
	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		setInicio(DateUtils.formatDate(DateUtils.getFechaActual()));
		setFin(DateUtils.formatDate(DateUtils.getFechaActual()));
	}
}