package descripcion.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import common.Constants;
import common.Messages;
import common.bi.GestionDescripcionBI;
import common.forms.CustomForm;
import common.util.StringUtils;
import common.util.TypeConverter;

import descripcion.DescripcionConstants;
import descripcion.vos.TablaValidacionVO;

/**
 * Formulario para la información de una tabla de validación.
 */
public class TablaValidacionForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String nombre = null;
	private String descripcion = null;
	private boolean usointerno = false;

	/**
	 * Constructor.
	 */
	public TablaValidacionForm() {
		super();
	}

	/**
	 * @return Returns the descripcion.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            The descripcion to set.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return Returns the usointerno.
	 */
	public boolean isUsointerno() {
		return usointerno;
	}

	/**
	 * @param usointerno
	 *            The usointerno to set.
	 */
	public void setUsointerno(boolean usointerno) {
		this.usointerno = usointerno;
	}

	/**
	 * Construye un VO con la información del formulario.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void populate(TablaValidacionVO vo) {
		vo.setId(this.id);
		vo.setNombre(this.nombre);
		vo.setDescripcion(this.descripcion);
		vo.setUsointerno(TypeConverter.toString(this.usointerno));
	}

	/**
	 * Coge la información del VO.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void set(TablaValidacionVO vo) {
		setId(vo.getId());
		setNombre(vo.getNombre());
		setDescripcion(vo.getDescripcion());
		setUsointerno(vo.isTablaDeSistema());
	}

	public void reset() {
		id = null;
		nombre = null;
		descripcion = null;
		usointerno = false;
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
		return validateFrm(mapping, request, null);
	}

	public ActionErrors validateFrm(ActionMapping mapping,
			HttpServletRequest request, GestionDescripcionBI descripcionBI) {
		ActionErrors errors = new ActionErrors();

		// Nombre
		if (StringUtils.isBlank(nombre)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DescripcionConstants.DESCRIPCION_TABLASVALIDACION_FORM_NOMBRE,
									request.getLocale())));
		}

		if (descripcionBI != null) { // se esta creando
			TablaValidacionVO tablaVal = descripcionBI
					.getTablaValidacionByNombre(nombre);
			if (tablaVal != null
					&& (StringUtils.isBlank(id) || !tablaVal.getId().equals(id))) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
						Constants.ERROR_CREAR_ELEMENTO_YA_EXISTE));
			}
		}

		return errors;
	}

}
