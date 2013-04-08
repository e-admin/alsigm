package docvitales.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.StringUtils;

import docvitales.DocumentosVitalesConstants;
import docvitales.vos.TipoDocumentoVitalVO;

/**
 * Formulario para la información de un tipo de documento vital.
 */
public class TipoDocumentoVitalForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del tipo de documento vital. */
	private String id = null;

	/** Nombre del tipo de documento vital. */
	private String nombre = null;

	/** Descripción del tipo de documento vital. */
	private String descripcion = null;

	/** Identificadores de los tipos de documentos vitales seleccionados. */
	private String[] idsSeleccionados = new String[0];

	/**
	 * Constructor.
	 */
	public TipoDocumentoVitalForm() {
		super();
	}

	/**
	 * Inicia el formulario.
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		idsSeleccionados = new String[0];
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
	 * @return Returns the idsSeleccionados.
	 */
	public String[] getIdsSeleccionados() {
		return idsSeleccionados;
	}

	/**
	 * @param idsSeleccionados
	 *            The idsSeleccionados to set.
	 */
	public void setIdsSeleccionados(String[] idsSeleccionados) {
		this.idsSeleccionados = idsSeleccionados;
	}

	/**
	 * Construye un VO con la información del formulario.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void populate(TipoDocumentoVitalVO vo) {
		vo.setId(this.id);
		vo.setNombre(this.nombre);
		vo.setDescripcion(this.descripcion);
	}

	/**
	 * Coge la información del VO.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void set(TipoDocumentoVitalVO vo) {
		if (vo != null) {
			setId(vo.getId());
			setNombre(vo.getNombre());
			setDescripcion(vo.getDescripcion());
		}
	}

	/**
	 * Valida el formulario.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		// Nombre
		if (StringUtils.isBlank(nombre)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DocumentosVitalesConstants.LABEL_DOCVITALES_DOCVITAL_TIPODOCVITAL_NOMBRE,
									request.getLocale())));
		}

		return errors;
	}

}
