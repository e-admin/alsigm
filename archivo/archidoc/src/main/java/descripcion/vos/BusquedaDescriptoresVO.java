package descripcion.vos;

import common.pagination.PageInfo;
import common.vos.BaseVO;

/**
 * VO para el almacenamiento de información del formulario de búsqueda de
 * descriptores.
 */
public class BusquedaDescriptoresVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador de la lista de descriptores.. */
	private String idLista = null;

	/** Nombre del descriptor. */
	private String nombre = null;
	private String cualificadorNombre = null;

	/** Estado del descriptor. */
	private int estado = 0;

	private boolean ocultarDescriptoresSistemaExterno = false;

	/** Información de la paginación. */
	private PageInfo pageInfo = null;

	/**
	 * Constructor.
	 */
	public BusquedaDescriptoresVO() {
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
	 * Obtiene la información de la paginación.
	 * 
	 * @return Información de la paginación.
	 */
	public PageInfo getPageInfo() {
		return pageInfo;
	}

	/**
	 * Establece la información de la paginación.
	 * 
	 * @param pageInfo
	 *            Información de la paginación.
	 */
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public String getCualificadorNombre() {
		return cualificadorNombre;
	}

	public void setCualificadorNombre(String cualificadorNombre) {
		this.cualificadorNombre = cualificadorNombre;
	}

	public void setOcultarDescriptoresSistemaExterno(
			boolean ocultarDescriptoresSistemaExterno) {
		this.ocultarDescriptoresSistemaExterno = ocultarDescriptoresSistemaExterno;
	}

	public boolean isOcultarDescriptoresSistemaExterno() {
		return ocultarDescriptoresSistemaExterno;
	}
}
