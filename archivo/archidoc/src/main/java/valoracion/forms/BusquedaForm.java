package valoracion.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import valoracion.ValoracionConstants;
import valoracion.vos.BusquedaVO;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;

/**
 * Clase que encapsula la información del formulario de busquedas de
 * eliminaciones/valoraciones
 */
public class BusquedaForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String tituloSerie = null;
	private String codigo = null;
	private String fondo = null;
	private String titulo = null;
	private String[] estados = null;
	private String tituloValoracion = null;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String[] getEstados() {
		return estados;
	}

	public void setEstados(String[] estados) {
		this.estados = estados;
	}

	public String getFondo() {
		return fondo;
	}

	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTituloSerie() {
		return tituloSerie;
	}

	public void setTituloSerie(String tituloSerie) {
		this.tituloSerie = tituloSerie;
	}

	public String getTituloValoracion() {
		return tituloValoracion;
	}

	public void setTituloValoracion(String tituloValoracion) {
		this.tituloValoracion = tituloValoracion;
	}

	/**
	 * Inicia el formulario.
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		estados = new String[0];
	}

	/**
	 * Construye un VO con la información del formulario.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void populate(BusquedaVO vo) {
		try {
			BeanUtils.copyProperties(vo, this);
		} catch (Exception e) {
		}
	}

	/**
	 * Valida el formulario
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		// Estados
		if (estados.length == 0)
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									ValoracionConstants.LABEL_VALORACION_ELIMINACION_BUSCAR_ESTADO,
									request.getLocale())));

		return errors;
	}

	/**
	 * Asigna al formulatio los datos de la eliminacion
	 * 
	 * @param vo
	 *            Value Object con los datos de la eliminacion
	 */
	public void setBusqueda(BusquedaVO vo) {
		try {
			BeanUtils.copyProperties(this, vo);
		} catch (Exception e) {
		}
	}
}
