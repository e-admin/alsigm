package descripcion.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.StringUtils;

import descripcion.DescripcionConstants;
import descripcion.model.TipoDescriptor;
import descripcion.vos.ListaDescrVO;

/**
 * Formulario para la información de una lista descriptora.
 */
public class ListaDescriptoraForm extends CustomForm {

	private static final long serialVersionUID = 1377791564965280914L;
	private String id = null;
	private String nombre = null;
	private int tipo = -1;
	private int tipoDescriptor = -1;
	private String idFichaDescrPref = null;
	private String nombreFichaDescrPref = null;
	private String idFichaClfDocPref = null;
	private String nombreFichaClfDocPref = null;
	private String idRepEcmPref = null;
	private String nombreRepEcmPref = null;
	private String descripcion = null;
	private String filtro = null;
	private String calificadorFiltro = null;

	private String[] ids = new String[0];

	private int filtroEstado = 0;

	/**
	 * Constructor.
	 */
	public ListaDescriptoraForm() {
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
	 * @return Returns the idFichaDescrPref.
	 */
	public String getIdFichaDescrPref() {
		return idFichaDescrPref;
	}

	/**
	 * @param idFichaDescrPref
	 *            The idFichaDescrPref to set.
	 */
	public void setIdFichaDescrPref(String idFichaDescrPref) {
		this.idFichaDescrPref = idFichaDescrPref;
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
	 * @return Returns the tipoDescriptor.
	 */
	public int getTipoDescriptor() {
		return tipoDescriptor;
	}

	/**
	 * @param tipoDescriptor
	 *            The tipoDescriptor to set.
	 */
	public void setTipoDescriptor(int tipoDescriptor) {
		this.tipoDescriptor = tipoDescriptor;
	}

	/**
	 * @return Returns the idFichaClfDocPref.
	 */
	public String getIdFichaClfDocPref() {
		return idFichaClfDocPref;
	}

	/**
	 * @param idFichaClfDocPref
	 *            The idFichaClfDocPref to set.
	 */
	public void setIdFichaClfDocPref(String idFichaClfDocPref) {
		this.idFichaClfDocPref = idFichaClfDocPref;
	}

	/**
	 * @return Returns the idRepEcmPref.
	 */
	public String getIdRepEcmPref() {
		return idRepEcmPref;
	}

	/**
	 * @param idRepEcmPref
	 *            The idRepEcmPref to set.
	 */
	public void setIdRepEcmPref(String idRepEcmPref) {
		this.idRepEcmPref = idRepEcmPref;
	}

	/**
	 * @return Returns the nombreFichaClfDocPref.
	 */
	public String getNombreFichaClfDocPref() {
		return nombreFichaClfDocPref;
	}

	/**
	 * @param nombreFichaClfDocPref
	 *            The nombreFichaClfDocPref to set.
	 */
	public void setNombreFichaClfDocPref(String nombreFichaClfDocPref) {
		this.nombreFichaClfDocPref = nombreFichaClfDocPref;
	}

	/**
	 * @return Returns the nombreFichaDescrPref.
	 */
	public String getNombreFichaDescrPref() {
		return nombreFichaDescrPref;
	}

	/**
	 * @param nombreFichaDescrPref
	 *            The nombreFichaDescrPref to set.
	 */
	public void setNombreFichaDescrPref(String nombreFichaDescrPref) {
		this.nombreFichaDescrPref = nombreFichaDescrPref;
	}

	/**
	 * @return Returns the nombreRepEcmPref.
	 */
	public String getNombreRepEcmPref() {
		return nombreRepEcmPref;
	}

	/**
	 * @param nombreRepEcmPref
	 *            The nombreRepEcmPref to set.
	 */
	public void setNombreRepEcmPref(String nombreRepEcmPref) {
		this.nombreRepEcmPref = nombreRepEcmPref;
	}

	/**
	 * Construye un VO con la información del formulario.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void populate(ListaDescrVO vo) {
		vo.setId(this.id);
		vo.setNombre(this.nombre);
		vo.setTipo(this.tipo);
		vo.setDescripcion(this.descripcion);
		vo.setTipoDescriptor(this.tipoDescriptor);
		vo.setIdFichaDescrPref(this.idFichaDescrPref);
		vo.setNombreFichaDescrPref(this.nombreFichaDescrPref);
		vo.setIdFichaClfDocPref(this.idFichaClfDocPref);
		vo.setNombreFichaClfDocPref(this.nombreFichaClfDocPref);
		vo.setIdRepEcmPref(this.idRepEcmPref);
		vo.setNombreRepEcmPref(this.nombreRepEcmPref);
	}

	/**
	 * Coge la información del VO.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void set(ListaDescrVO vo) {
		setId(vo.getId());
		setNombre(vo.getNombre());
		setTipo(vo.getTipo());
		setDescripcion(vo.getDescripcion());
		setTipoDescriptor(vo.getTipoDescriptor());
		setIdFichaDescrPref(vo.getIdFichaDescrPref());
		setNombreFichaDescrPref(vo.getNombreFichaDescrPref());
		setIdFichaClfDocPref(vo.getIdFichaClfDocPref());
		setNombreFichaClfDocPref(vo.getNombreFichaClfDocPref());
		setIdRepEcmPref(vo.getIdRepEcmPref());
		setNombreRepEcmPref(vo.getNombreRepEcmPref());
	}

	public void reset() {
		id = null;
		nombre = null;
		tipo = -1;
		tipoDescriptor = -1;
		idFichaDescrPref = null;
		nombreFichaDescrPref = null;
		idFichaClfDocPref = null;
		nombreFichaClfDocPref = null;
		idRepEcmPref = null;
		nombreRepEcmPref = null;
		descripcion = null;
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

		// Nombre
		if (StringUtils.isBlank(nombre)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DescripcionConstants.DESCRIPCION_LISTADESCRIPTORAS_NOMBRE,
									request.getLocale())));
		}

		// Tipo
		if (tipo < 0) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DescripcionConstants.DESCRIPCION_LISTADESCRIPTORAS_TIPO,
									request.getLocale())));
		}

		// Tipo de descriptor
		if (tipoDescriptor < 0) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DescripcionConstants.DESCRIPCION_LISTADESCRIPTORAS_TIPO_DESCRIPTOR,
									request.getLocale())));
		}

		// Si el tipo de descriptor es ENTIDAD, debe tener ficha de descripción
		// asociada
		if ((tipoDescriptor == TipoDescriptor.ENTIDAD)
				&& StringUtils.isBlank(idFichaDescrPref)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DescripcionConstants.DESCRIPCION_LISTADESCRIPTORAS_TIPO_NOMBRE_FICHA_DESCR_PREF,
									request.getLocale())));
		}

		return errors;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public String getCalificadorFiltro() {
		return calificadorFiltro;
	}

	public void setCalificadorFiltro(String calificadorFiltro) {
		this.calificadorFiltro = calificadorFiltro;
	}

	public void setFiltroEstado(int filtroEstado) {
		this.filtroEstado = filtroEstado;
	}

	public int getFiltroEstado() {
		return filtroEstado;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String[] getIds() {
		return ids;
	}

}
