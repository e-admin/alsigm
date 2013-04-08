package solicitudes.prestamos.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import solicitudes.prestamos.PrestamosConstants;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.StringUtils;

/**
 * Formulario de cesión de préstamos.
 */
public class CesionPrestamosForm extends CustomForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final int TIPO_BUSQUEDA_POR_PRESTAMO = 1;
	public static final int TIPO_BUSQUEDA_POR_GESTOR = 2;

	private String organo = null;
	private String codigo = null;
	private String gestor = null;
	private int tipoBusqueda = TIPO_BUSQUEDA_POR_PRESTAMO;
	private boolean resultado = false;
	private String prestamoSeleccionado = null;

	/**
	 * Constructor.
	 */
	public CesionPrestamosForm() {
	}

	/**
	 * Inicia el formulario.
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	}

	/**
	 * @return Returns the gestor.
	 */
	public String getGestor() {
		return gestor;
	}

	/**
	 * @param gestor
	 *            The gestor to set.
	 */
	public void setGestor(String gestor) {
		this.gestor = gestor;
	}

	/**
	 * @return Returns the organo.
	 */
	public String getOrgano() {
		return organo;
	}

	/**
	 * @param organo
	 *            The organo to set.
	 */
	public void setOrgano(String organo) {
		this.organo = organo;
	}

	/**
	 * @return Returns the tipoBusqueda.
	 */
	public int getTipoBusqueda() {
		return tipoBusqueda;
	}

	/**
	 * @param tipoBusqueda
	 *            The tipoBusqueda to set.
	 */
	public void setTipoBusqueda(int tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	/**
	 * @return Returns the hayResultado.
	 */
	public boolean isResultado() {
		return resultado;
	}

	/**
	 * @param hayResultado
	 *            The hayResultado to set.
	 */
	public void setResultado(boolean hayResultado) {
		this.resultado = hayResultado;
	}

	/**
	 * @return Returns the orden.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param orden
	 *            The orden to set.
	 */
	public void setCodigo(String orden) {
		this.codigo = orden;
	}

	/**
	 * @return Returns the prestamoSeleccionado.
	 */
	public String getPrestamoSeleccionado() {
		return prestamoSeleccionado;
	}

	/**
	 * @param prestamoSeleccionado
	 *            The prestamoSeleccionado to set.
	 */
	public void setPrestamoSeleccionado(String prestamoSeleccionado) {
		this.prestamoSeleccionado = prestamoSeleccionado;
	}

	/**
	 * Valida el formulario de busqueda.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if ((tipoBusqueda == TIPO_BUSQUEDA_POR_GESTOR)
				&& StringUtils.isBlank(gestor))
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									PrestamosConstants.LABEL_PRESTAMOS_GESTOR_PRESTAMO,
									request.getLocale())));
		return errors;
	}

	/**
	 * Valida el formulario de selección de préstamos.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public ActionErrors validateSeleccion(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if (StringUtils.isBlank(prestamoSeleccionado))
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_SELECTION_NOT_PERFORMED2,
							Messages.getString(
									PrestamosConstants.LABEL_PRESTAMOS_PRESTAMO,
									request.getLocale())));

		return errors;
	}

	/**
	 * Valida el formulario de selección de gestor.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public ActionErrors validateSeleccionGestor(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if (StringUtils.isBlank(gestor))
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_SELECTION_NOT_PERFORMED2,
							Messages.getString(
									PrestamosConstants.LABEL_PRESTAMOS_GESTOR_PRESTAMO,
									request.getLocale())));

		return errors;
	}

}
