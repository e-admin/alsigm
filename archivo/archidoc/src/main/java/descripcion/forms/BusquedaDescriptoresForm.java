package descripcion.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.StringUtils;

import descripcion.ErrorKeys;
import descripcion.vos.BusquedaDescriptoresVO;

/**
 * Formulario para la búsqueda de descriptores.
 */
public class BusquedaDescriptoresForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador de la lista de descriptores. */
	private String idLista = null;

	/** Nombre del descriptor. */
	private String nombre = null;

	/** Estado del descriptor. */
	private int estado = 0;

	private String ref = null;
	private String refIdsLists = null;
	private String refType = null;
	private String listDescrSel = null;

	private String field = null;

	/**
	 * Constructor.
	 */
	public BusquedaDescriptoresForm() {
		super();
	}

	/**
	 * Obtiene el identificador de la lista de descriptores.
	 * 
	 * @return Identificador de la lista de descriptores.
	 */
	public String getIdLista() {
		return idLista;
	}

	/**
	 * Establece el identificador de la lista de descriptores.
	 * 
	 * @param idLista
	 *            Identificador de la lista de descriptores.
	 */
	public void setIdLista(String idLista) {
		this.idLista = idLista;
	}

	/**
	 * Obtiene el estado del descriptor.
	 * 
	 * @return Estado del descriptor.
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * Establece el estado del descriptor.
	 * 
	 * @param estado
	 *            Estado del descriptor.
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}

	/**
	 * Obtiene el nombre del descriptor.
	 * 
	 * @return Nombre del descriptor.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del descriptor.
	 * 
	 * @param nombre
	 *            Nombre del descriptor.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return Returns the listDescrSel.
	 */
	public String getListDescrSel() {
		return listDescrSel;
	}

	/**
	 * @param listDescrSel
	 *            The listDescrSel to set.
	 */
	public void setListDescrSel(String listDescrSel) {
		this.listDescrSel = listDescrSel;
	}

	/**
	 * @return Returns the ref.
	 */
	public String getRef() {
		return ref;
	}

	/**
	 * @param ref
	 *            The ref to set.
	 */
	public void setRef(String ref) {
		this.ref = ref;
	}

	/**
	 * @return Returns the refIdsLists.
	 */
	public String getRefIdsLists() {
		return refIdsLists;
	}

	/**
	 * @param refIdsLists
	 *            The refIdsLists to set.
	 */
	public void setRefIdsLists(String refIdsLists) {
		this.refIdsLists = refIdsLists;
	}

	/**
	 * @return Returns the refType.
	 */
	public String getRefType() {
		return refType;
	}

	/**
	 * @param refType
	 *            The refType to set.
	 */
	public void setRefType(String refType) {
		this.refType = refType;
	}

	/**
	 * @return el field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field
	 *            el field a establecer
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * Obtiene un VO con la información del formulario.
	 * 
	 * @return Value Object.
	 */
	public BusquedaDescriptoresVO getBusquedaDescriptoresVO() {
		BusquedaDescriptoresVO vo = new BusquedaDescriptoresVO();
		vo.setIdLista(this.idLista);
		vo.setNombre(this.nombre);
		vo.setEstado(this.estado);
		return vo;
	}

	/**
	 * Inicia el formulario.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
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

		// Identificador de la lista descriptora
		if (StringUtils.isBlank(idLista))
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									ErrorKeys.ERROR_DESCRIPCION_EXPORTAR_DEF_NO_ENCONTRADA,
									request.getLocale())));

		return errors;
	}

	/**
	 * Valida el formulario
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public ActionErrors validateCreate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = validate(mapping, request);

		// Comprobar que el nombre es obligatorio
		if (StringUtils.isEmpty(nombre)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_NOMBRE,
									request.getLocale())));
		}

		return errors;

	}

}
