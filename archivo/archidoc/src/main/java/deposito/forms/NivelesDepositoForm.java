package deposito.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.StringUtils;

import deposito.DepositoConstants;
import deposito.vos.TipoElementoVO;

/**
 * Formulario para la recogida de datos en la creación y edición de niveles
 */
public class NivelesDepositoForm extends CustomForm {

	private static final long serialVersionUID = 1L;
	protected String id;
	protected String nombre;
	protected String nombreAbreviado;
	protected Boolean asignable = Boolean.FALSE;
	protected String idPadre;
	protected String descripcion;
	protected String caracterOrden;
	protected Integer tipoOrd;

	private String icono;

	/** descendientes **/
	String[] descendientes = null;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombreTipoElemento() {
		return nombre;
	}

	public void setNombreTipoElemento(String nombreTipoElemento) {
		this.nombre = nombreTipoElemento;
	}

	public String getNombreAbreviado() {
		return nombreAbreviado;
	}

	public void setNombreAbreviado(String nombreAbreviado) {
		this.nombreAbreviado = nombreAbreviado;
	}

	public Boolean isAsignable() {
		return asignable;
	}

	public boolean isTipoAsignable() {
		if (isAsignable() != null && isAsignable().booleanValue()) {
			return true;
		}
		return false;

	}

	public void setAsignable(Boolean asignable) {
		this.asignable = asignable;
	}

	public String getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCaracterOrden() {
		return caracterOrden;
	}

	public void setCaracterOrden(String caracterOrden) {
		this.caracterOrden = caracterOrden;
	}

	public Integer getTipoOrd() {
		return tipoOrd;
	}

	public void setTipoOrd(Integer tipoOrd) {
		this.tipoOrd = tipoOrd;
	}

	public String[] getDescendientes() {
		return descendientes;
	}

	public void setDescendientes(String[] descendientes) {
		this.descendientes = descendientes;
	}

	public Boolean getAsignable() {
		return asignable;
	}

	public void set(TipoElementoVO vo) {
		reset();
		if (vo != null) {

			this.id = vo.getId();
			this.nombre = vo.getNombre();
			this.nombreAbreviado = vo.getNombreAbreviado();
			this.asignable = BooleanUtils.toBooleanObject(vo.getAsignable());
			this.idPadre = vo.getIdpadre();
			this.descripcion = vo.getDescripcion();
			this.caracterOrden = vo.getCaracterorden();
			this.tipoOrd = vo.getTipoord();
		}
	}

	public void populate(TipoElementoVO vo) {
		vo.setId(id);
		vo.setNombre(nombre);
		vo.setNombreAbreviado(nombreAbreviado);
		vo.setAsignable(BooleanUtils.toInteger(isTipoAsignable()));
		vo.setIdpadre(idPadre);
		vo.setDescripcion(descripcion);

		if (caracterOrden != null)
			caracterOrden = caracterOrden.toUpperCase();

		vo.setCaracterOrden(caracterOrden);

		if (vo.isTipoAsignable()) {
			vo.setTipoord(tipoOrd);
		} else {
			vo.setTipoord(DepositoConstants.ORDENACION_PROFUNDIDAD);
		}
	}

	public void reset() {
		this.id = null;
		this.nombre = null;
		this.nombreAbreviado = null;
		this.asignable = null;
		this.idPadre = null;
		this.descripcion = null;
		this.caracterOrden = null;
		this.tipoOrd = null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();

		if (GenericValidator.isBlankOrNull(this.getNombre())) {
			errors.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DepositoConstants.LABEL_TIPO_ELEMENTO_NOMBRE,
									request.getLocale())));
		}

		if (GenericValidator.isBlankOrNull(this.getNombreAbreviado())) {
			errors.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DepositoConstants.LABEL_TIPO_ELEMENTO_NOMBRE_ABREVIADO,
									request.getLocale())));
		}

		if (GenericValidator.isBlankOrNull(this.getCaracterOrden())) {
			errors.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DepositoConstants.LABEL_TIPO_ELEMENTO_CARACTER_ORDENACION,
									request.getLocale())));
		}

		if (!StringUtils.isLetra(this.caracterOrden)) {
			errors.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_GENERAL_MESSAGE,
							Messages.getString(
									DepositoConstants.ERROR_CARACTER_INCORRECTO,
									request.getLocale())));
		}

		return errors;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public String getIcono() {
		return icono;
	}

}