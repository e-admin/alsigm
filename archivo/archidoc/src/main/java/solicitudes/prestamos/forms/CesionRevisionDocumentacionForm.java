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
public class CesionRevisionDocumentacionForm extends CustomForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final int TIPO_BUSQUEDA_POR_REVISION = 1;
	public static final int TIPO_BUSQUEDA_POR_GESTOR = 2;

	private String gestor = null;
	private int tipoBusqueda = TIPO_BUSQUEDA_POR_REVISION;
	private boolean resultado = false;
	private String revDocSeleccionada = null;

	private String titulo;
	private String signatura;
	private String expediente;
	private String observaciones;

	/**
	 * Constructor.
	 */
	public CesionRevisionDocumentacionForm() {
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

		if (StringUtils.isBlank(revDocSeleccionada))
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_SELECTION_NOT_PERFORMED2,
							Messages.getString(
									PrestamosConstants.LABEL_PRESTAMOS_REVISION_DOC,
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
					new ActionError(Constants.ERROR_SELECTION_NOT_PERFORMED2,
							Messages.getString(Constants.ETIQUETA_GESTOR,
									request.getLocale())));

		return errors;
	}

	public String getRevDocSeleccionada() {
		return revDocSeleccionada;
	}

	public void setRevDocSeleccionada(String revDocSeleccionada) {
		this.revDocSeleccionada = revDocSeleccionada;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSignatura() {
		return signatura;
	}

	public void setSignatura(String signatura) {
		this.signatura = signatura;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

}
