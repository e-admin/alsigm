package docelectronicos.vos;

import java.util.ArrayList;
import java.util.Collection;

import docelectronicos.EstadoClasificador;
import docelectronicos.MarcasClasificador;
import docelectronicos.TipoNodo;
import docelectronicos.TipoObjeto;

/**
 * Información de un clasificador de documentos.
 */
public class DocClasificadorVO extends DocumentosTreeModelItemVO {

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
	private int marcas = MarcasClasificador.SIN_MARCAS;

	/** Estado del clasificador. */
	private int estado = EstadoClasificador.SIN_VALIDAR;

	/** Descripción del clasificador. */
	private String descripcion = null;

	/** Identificador del objeto que contiene el clasificador. */
	private String idObjeto = null;

	/** Tipo de objeto que contiene el documento. */
	private int tipoObjeto = TipoObjeto.DESCRIPTOR;

	private Collection childItems = null;

	/** Tamaño del fichero. */
	private double tamanoFich = 0;

	/**
	 * Constructor.
	 */
	public DocClasificadorVO() {
		super(TipoNodo.CLASIFICADOR);
		childItems = new ArrayList();
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
	 * @return Returns the childItems.
	 */
	public Collection childItems() {
		return childItems;
	}

	public void addChildItem(DocumentosTreeModelItemVO child) {
		child.setParent(this);
		childItems.add(child);
	}

	/**
	 * Indica si se trata de un nodo hoja.
	 * 
	 * @return true si se trata de un nodo hoja.
	 */
	public boolean isLeaf() {
		return (childItems.size() == 0);
	}

	public double getTamanoFich() {
		return tamanoFich;
	}

	public void setTamanoFich(double tamanoFich) {
		this.tamanoFich = tamanoFich;
	}
}
