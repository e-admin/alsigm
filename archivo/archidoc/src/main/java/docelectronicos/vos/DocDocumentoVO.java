package docelectronicos.vos;

import java.util.ArrayList;
import java.util.Collection;

import common.vos.IKeyId;

import docelectronicos.EstadoDocumento;
import docelectronicos.TipoNodo;
import docelectronicos.TipoObjeto;

/**
 * Información de un documento electrónico.
 */
public class DocDocumentoVO extends DocumentosTreeModelItemVO implements IKeyId {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del documento. */
	private String id = null;

	/** Nombre del documento. */
	private String nombre = null;

	/** Identificador del clasificador padre. */
	private String idClfPadre = null;

	/** Tamaño del fichero. */
	private double tamanoFich = 0;

	/** Nombre original del fichero. */
	private String nombreOrgFich = null;

	/** Extensión del fichero. */
	private String extFich = null;

	/** Identificador externo del depósito electrónico. */
	private String idExtDeposito = null;

	/** Identificador del fichero. */
	private String idFich = null;

	/** Estado del clasificador. */
	private int estado = EstadoDocumento.SIN_VALIDAR;

	/** Descripción del clasificador. */
	private String descripcion = null;

	/** Identificador del objeto que contiene el documento. */
	private String idObjeto = null;

	/** Tipo de objeto que contiene el documento. */
	private int tipoObjeto = TipoObjeto.DESCRIPTOR;

	/** nombre del deposito donde se encuentra el documento */
	private String nombreDeposito = null;

	/**
	 * Campos que se actualizaran para cada campo en las tablas correspondientes
	 **/
	public static String[] camposDocumento = new String[] { "idFich", "nombre",
			"descripcion" };
	/**
	 * Campo necesario en el properties para poder obtener el mayor orden de la
	 * tabla
	 **/
	public static String campoIdTablaDesc = "idTablaDescripcion";
	/**
	 * Campo necesario en el properties para poder eliminar los campos
	 * descriptivos del documento
	 **/
	public static String campoIdInterno = "idInterno";

	private String idRepEcm;

	/**
	 * Constructor.
	 */
	public DocDocumentoVO() {
		super(TipoNodo.DOCUMENTO);
	}

	public String getNombreDeposito() {
		return nombreDeposito;
	}

	public void setNombreDeposito(String nombreDeposito) {
		this.nombreDeposito = nombreDeposito;
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
	 * @return Returns the extFich.
	 */
	public String getExtFich() {
		return extFich;
	}

	/**
	 * @param extFich
	 *            The extFich to set.
	 */
	public void setExtFich(String extFich) {
		this.extFich = extFich;
	}

	/**
	 * @return Returns the idFich.
	 */
	public String getIdFich() {
		return idFich;
	}

	/**
	 * @param idFich
	 *            The idFich to set.
	 */
	public void setIdFich(String idFich) {
		this.idFich = idFich;
	}

	/**
	 * @return Returns the nombreOrgFich.
	 */
	public String getNombreOrgFich() {
		return nombreOrgFich;
	}

	/**
	 * @param nombreOrgFich
	 *            The nombreOrgFich to set.
	 */
	public void setNombreOrgFich(String nombreOrgFich) {
		this.nombreOrgFich = nombreOrgFich;
	}

	/**
	 * @return Returns the tamanoFich.
	 */
	public double getTamanoFich() {
		return tamanoFich;
	}

	/**
	 * @param tamanoFich
	 *            The tamanoFich to set.
	 */
	public void setTamanoFich(double tamanoFich) {
		this.tamanoFich = tamanoFich;
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
	 * @return Returns the idDeposito.
	 */
	public String getIdExtDeposito() {
		return idExtDeposito;
	}

	/**
	 * @param idDeposito
	 *            The idDeposito to set.
	 */
	public void setIdExtDeposito(String idDeposito) {
		this.idExtDeposito = idDeposito;
	}

	/**
	 * @return Returns the childItems.
	 */
	public Collection childItems() {
		return new ArrayList();
	}

	/**
	 * Indica si se trata de un nodo hoja.
	 *
	 * @return true si se trata de un nodo hoja.
	 */
	public boolean isLeaf() {
		return true;
	}

	public void setIdRepEcm(String idRepEcm) {
		this.idRepEcm = idRepEcm;
	}

	public String getIdRepEcm() {
		return idRepEcm;
	}
}