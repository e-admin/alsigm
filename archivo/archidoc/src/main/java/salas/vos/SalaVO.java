package salas.vos;

import salas.SalasConsultaConstants;

import common.Constants;
import common.util.StringUtils;

/**
 * VO que representa la tabla ASGSSALA
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class SalaVO extends SalasConsultaVOBase {

	private static final long serialVersionUID = 1L;

	/**
	 * Identificador de la sala
	 */
	private String id = null;

	/**
	 * Nombre único dentro del edificio
	 */
	private String nombre = null;

	/**
	 * Ubicación dentro del edificio
	 */
	private String descripcion = null;

	/**
	 * Identificador del edificio al que pertenece
	 */
	private String idEdificio = null;

	/**
	 * Indica si la sala posee equipos informáticos o no (S/N)
	 */
	private String equipoInformatico = null;

	/**
	 * Número de mesas que contiene la sala
	 */
	private int numMesas = 0;

	private String pathName;
	private String itemPath;

	public SalaVO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getIdEdificio() {
		return idEdificio;
	}

	public void setIdEdificio(String idEdificio) {
		this.idEdificio = idEdificio;
	}

	public String getEquipoInformatico() {
		return equipoInformatico;
	}

	public void setEquipoInformatico(String equipoInformatico) {
		this.equipoInformatico = equipoInformatico;
	}

	public int getNumMesas() {
		return numMesas;
	}

	public void setNumMesas(int numMesas) {
		this.numMesas = numMesas;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public void setItemPath(String itemPath) {
		this.itemPath = itemPath;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.vos.SalasConsultaVOBase#getTipo()
	 */
	public String getTipo() {
		return SalasConsultaConstants.TIPO_ELEMENTO_SALA;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.vos.SalasConsultaVOBase#getPathName()
	 */
	public String getPathName() {
		if (StringUtils.isNotEmpty(this.pathName)) {
			this.pathName = this.idEdificio + Constants.SLASH + this.nombre;
		}
		return this.pathName;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see util.TreeModelItem#getItemPath()
	 */
	public String getItemPath() {
		if (StringUtils.isEmpty(this.itemPath)) {
			this.itemPath = Constants.ITEM + this.idEdificio + Constants.SLASH
					+ Constants.ITEM + this.id;
		}

		return this.itemPath;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see util.TreeModelItem#getItemId()
	 */
	public Object getItemId() {
		return this.id;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see util.TreeModelItem#getItemName()
	 */
	public String getItemName() {
		return this.nombre;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see util.TreeModelItem#isLeaf()
	 */
	public boolean isLeaf() {
		return true;
	}
}