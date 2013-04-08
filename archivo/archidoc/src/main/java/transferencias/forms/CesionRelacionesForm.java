package transferencias.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import transferencias.TransferenciasConstants;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.ArrayUtils;
import common.util.StringUtils;

import fondos.FondosConstants;

/**
 * Formulario de cesión de relaciones.
 */
public class CesionRelacionesForm extends CustomForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final int TIPO_BUSQUEDA_POR_RELACION = 1;
	public static final int TIPO_BUSQUEDA_POR_GESTOR = 2;

	private String organo = null;
	private String codigo = null;
	private String gestor = null;
	private String archivo = null;

	private int tipo = 0;
	private int tipoBusqueda = TIPO_BUSQUEDA_POR_RELACION;
	private boolean resultado = false;
	private String[] relacionesSeleccionadas = new String[0];

	/**
	 * Constructor.
	 */
	public CesionRelacionesForm() {
	}

	/**
	 * Inicia el formulario.
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		relacionesSeleccionadas = new String[0];
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
	 * @return Returns the tipo.
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            The tipo to set.
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
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
	 * @return Returns the previsionesSeleccionadas.
	 */
	public String[] getRelacionesSeleccionadas() {
		return relacionesSeleccionadas;
	}

	/**
	 * @param previsionesSeleccionadas
	 *            The previsionesSeleccionadas to set.
	 */
	public void setRelacionesSeleccionadas(String[] previsionesSeleccionadas) {
		this.relacionesSeleccionadas = previsionesSeleccionadas;
	}

	/**
	 * Permite obtener el archivo receptor
	 * 
	 * @return Archivo receptor
	 */
	public String getArchivo() {
		return archivo;
	}

	/**
	 * Permite establecer el archivo receptor
	 * 
	 * @param archivo
	 *            Archivo receptor
	 */
	public void setArchivo(String archivo) {
		this.archivo = archivo;
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
									TransferenciasConstants.LABEL_TRANSFERENCIAS_USER_GESTOR,
									request.getLocale())));
		return errors;
	}

	/**
	 * Valida el formulario de selección de relación/es.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public ActionErrors validateSeleccion(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if (ArrayUtils.isEmpty(relacionesSeleccionadas))
			if (tipo != 3) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_SELECTION_NOT_PERFORMED,
								Messages.getString(
										TransferenciasConstants.LABEL_TRANSFERENCIAS_RELACIONES_BUSCAR_REL,
										request.getLocale())));
			} else {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_SELECTION_NOT_PERFORMED2,
								Messages.getString(
										FondosConstants.LABEL_FONDOS_INGRESO_BUSCAR_INGRESO,
										request.getLocale())));
			}
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
							Constants.ERROR_SELECTION_NOT_PERFORMED,
							Messages.getString(
									TransferenciasConstants.LABEL_TRANSFERENCIAS_USER_GESTOR,
									request.getLocale())));

		return errors;
	}

}
