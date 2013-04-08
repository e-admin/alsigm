package descripcion.vos;

import common.vos.BaseVO;

import descripcion.TipoListaDescriptora;

/**
 * Información de una lista descriptora
 */
public class ListaDescrVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String nombre = null;
	private int tipo;
	private int tipoDescriptor;
	private String idFichaDescrPref = null;
	private String nombreFichaDescrPref = null;
	private String idFichaClfDocPref = null;
	private String nombreFichaClfDocPref = null;
	private String idRepEcmPref = null;
	private String nombreRepEcmPref = null;
	private String descripcion = null;

	/**
	 * Constructor.
	 */
	public ListaDescrVO() {
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

	public boolean isAbierta() {
		if (TipoListaDescriptora.LISTA_ABIERTA == tipo) {
			return true;
		}

		return false;
	}

	public boolean isCerrada() {
		if (TipoListaDescriptora.LISTA_CERRADA == tipo) {
			return true;
		}
		return false;
	}
}
