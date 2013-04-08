package docelectronicos.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.StringUtils;

import docelectronicos.DocumentosConstants;
import docelectronicos.TipoObjeto;
import docelectronicos.vos.DocClasificadorVO;

/**
 * Formulario para la información de un clasificador de documentos electrónicos.
 */
public class ClasificadorForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del clasificador. */
	private String id = null;

	/** Nombre del clasificador. */
	private String nombre = null;

	/** Identificador del clasificador padre. */
	private String idClfPadre = null;

	/** Marcas del clasificador. */
	private int marcas = -1;

	/** Estado del clasificador. */
	private int estado = -1;

	/** Descripción del clasificador. */
	private String descripcion = null;

	/** Identificador del objeto que contiene el clasificador. */
	private String idObjeto = null;

	/** Tipo de objeto que contiene el documento. */
	private int tipoObjeto = TipoObjeto.DESCRIPTOR;

	/** Flag que indica si el clasificador es el ROOT. */
	private boolean root = false;

	/** Flag que indica si su padre es de tipo fijo. */
	private boolean tienePadreFijo = false;

	/** Flag que indica si tiene un hijo de tipo fijo. */
	private boolean tieneHijoFijo = false;

	/**
	 * Constructor.
	 */
	public ClasificadorForm() {
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
	 * @return Returns the estado.
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            The estado to set.
	 */
	public void setEstado(int estado) {
		this.estado = estado;
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
	 * @return Returns the idClfPadre.
	 */
	public String getIdClfPadre() {
		return idClfPadre;
	}

	/**
	 * @param idClfPadre
	 *            The idClfPadre to set.
	 */
	public void setIdClfPadre(String idClfPadre) {
		this.idClfPadre = idClfPadre;
	}

	/**
	 * @return Returns the idObjeto.
	 */
	public String getIdObjeto() {
		return idObjeto;
	}

	/**
	 * @param idObjeto
	 *            The idObjeto to set.
	 */
	public void setIdObjeto(String idObjeto) {
		this.idObjeto = idObjeto;
	}

	/**
	 * @return Returns the marcas.
	 */
	public int getMarcas() {
		return marcas;
	}

	/**
	 * @param marcas
	 *            The marcas to set.
	 */
	public void setMarcas(int marcas) {
		this.marcas = marcas;
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
	 * @return Returns the tipoObjeto.
	 */
	public int getTipoObjeto() {
		return tipoObjeto;
	}

	/**
	 * @param tipoObjeto
	 *            The tipoObjeto to set.
	 */
	public void setTipoObjeto(int tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

	/**
	 * @return Returns the root.
	 */
	public boolean isRoot() {
		return root;
	}

	/**
	 * @param root
	 *            The root to set.
	 */
	public void setRoot(boolean root) {
		this.root = root;
	}

	/**
	 * @return Returns the tieneHijoFijo.
	 */
	public boolean isTieneHijoFijo() {
		return tieneHijoFijo;
	}

	/**
	 * @param tieneHijoFijo
	 *            The tieneHijoFijo to set.
	 */
	public void setTieneHijoFijo(boolean tieneHijoFijo) {
		this.tieneHijoFijo = tieneHijoFijo;
	}

	/**
	 * @return Returns the tienePadreFijo.
	 */
	public boolean isTienePadreFijo() {
		return tienePadreFijo;
	}

	/**
	 * @param tienePadreFijo
	 *            The tienePadreFijo to set.
	 */
	public void setTienePadreFijo(boolean tienePadreFijo) {
		this.tienePadreFijo = tienePadreFijo;
	}

	/**
	 * Construye un VO con la información del formulario.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void populate(DocClasificadorVO vo) {
		vo.setId(this.id);
		vo.setNombre(this.nombre);
		// vo.setIdClfPadre(this.idClfPadre);

		// Añadido porque Postgre no reconoce '' como null
		if (this.idClfPadre.equals(Constants.STRING_EMPTY))
			vo.setIdClfPadre(null);
		else
			vo.setIdClfPadre(this.idClfPadre);

		vo.setIdObjeto(this.idObjeto);
		vo.setTipoObjeto(this.tipoObjeto);
		vo.setMarcas(this.marcas);
		vo.setEstado(this.estado);
		vo.setDescripcion(this.descripcion);
	}

	/**
	 * Coge la información del VO.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void set(DocClasificadorVO vo) {
		if (vo != null) {
			setId(vo.getId());
			setNombre(vo.getNombre());
			setIdClfPadre(vo.getIdClfPadre());
			setIdObjeto(vo.getIdObjeto());
			setTipoObjeto(vo.getTipoObjeto());
			setMarcas(vo.getMarcas());
			setEstado(vo.getEstado());
			setDescripcion(vo.getDescripcion());
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

		// Nombre
		if (StringUtils.isBlank(nombre)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									DocumentosConstants.LABEL_DOCUMENTOS_CLASIFICADOR_NOMBRE,
									request.getLocale())));
		}

		// Marcas
		// if (marcas < 0){
		// errors.add(ActionErrors.GLOBAL_MESSAGE,
		// new ActionError(Constants.ERROR_REQUIRED,
		// Messages.getString("archigest.archivo.documentos.clasificador.form.marcas")));
		// }

		// Estado
		// if (estado < 0){
		// errors.add(ActionErrors.GLOBAL_MESSAGE,
		// new ActionError(Constants.ERROR_REQUIRED,
		// Messages.getString("archigest.archivo.documentos.clasificador.form.estado")));
		// }

		return errors;
	}

}
