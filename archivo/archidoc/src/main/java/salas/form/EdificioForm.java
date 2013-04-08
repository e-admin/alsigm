package salas.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import salas.SalasConsultaConstants;
import salas.vos.EdificioVO;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class EdificioForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del Edificio
	 */
	String idEdificio = null;

	/**
	 * Nombre del Edificio
	 */
	String nombre = null;

	/**
	 * Ubicación del Edificio
	 */
	String ubicacion = null;

	/**
	 * Identificador del Archivo
	 */
	String idArchivo = null;

	/**
	 * Nombre del Archivo
	 */
	String nombreArchivo = null;

	/**
	 * @return el idEdificio
	 */
	public String getIdEdificio() {
		return idEdificio;
	}

	/**
	 * @param idEdificio
	 *            el idEdificio a fijar
	 */
	public void setIdEdificio(String idEdificio) {
		this.idEdificio = idEdificio;
	}

	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            el nombre a fijar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return el ubicacion
	 */
	public String getUbicacion() {
		return ubicacion;
	}

	/**
	 * @param ubicacion
	 *            el ubicacion a fijar
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * @return el idArchivo
	 */
	public String getIdArchivo() {
		return idArchivo;
	}

	/**
	 * @param idArchivo
	 *            el idArchivo a fijar
	 */
	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	/**
	 * @return el nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	/**
	 * @param nombreArchivo
	 *            el nombreArchivo a fijar
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public void populate(EdificioVO edificioVO) throws Exception {
		edificioVO.setIdArchivo(this.getIdArchivo());
		edificioVO.setId(this.getIdEdificio());
		edificioVO.setNombre(this.getNombre());
		edificioVO.setUbicacion(this.getUbicacion());
		edificioVO.setNombreArchivo(this.getNombreArchivo());
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
									SalasConsultaConstants.ETIQUETA_NOMBRE_EDIFICIO,
									request.getLocale())));
		}

		if (GenericValidator.isBlankOrNull(this.getIdArchivo())) {
			errors.add(
					ActionMessages.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									SalasConsultaConstants.ETIQUETA_ARCHIVO_EDIFICIO,
									request.getLocale())));
		}

		return errors;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.ServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.idArchivo = null;
		this.idEdificio = null;
		this.nombre = null;
		this.ubicacion = null;
		this.nombreArchivo = null;
	}

	public void set(EdificioVO edificioVO) throws Exception {
		this.setIdArchivo(edificioVO.getIdArchivo());
		this.setIdEdificio(edificioVO.getId());
		this.setNombre(edificioVO.getNombre());
		this.setUbicacion(edificioVO.getUbicacion());
		this.setNombreArchivo(edificioVO.getNombreArchivo());
	}

}
