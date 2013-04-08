package salas.vos;

import salas.SalasConsultaConstants;

import common.Constants;
import common.util.StringUtils;

/**
 * VO que representa la tabla ASGSEDIFICIO
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class EdificioVO extends SalasConsultaVOBase {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String nombre;
	private String ubicacion;
	private String idArchivo;

	private String nombreArchivo;
	private String pathName;
	private String itemPath;

	private int numHijos;

	/**
	 * @return el id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            el id a fijar
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            el nombre a fijar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return el ubicacion
	 */
	public String getUbicacion() {
		return ubicacion;
	}

	/**
	 * @param ubicacion
	 *            el ubicacion a fijar
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * @return el idArchivo
	 */
	public String getIdArchivo() {
		return idArchivo;
	}

	/**
	 * @param idArchivo
	 *            el idArchivo a fijar
	 */
	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
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
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.vos.SalasConsultaVOBase#getTipo()
	 */
	public String getTipo() {
		return SalasConsultaConstants.TIPO_ELEMENTO_EDIFICIO;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see salas.vos.SalasConsultaVOBase#getPathName()
	 */
	public String getPathName() {
		if (StringUtils.isNotEmpty(this.pathName)) {
			this.pathName = this.nombre;
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
			this.itemPath = Constants.ITEM + this.id;
		}

		return this.itemPath;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public void setItemPath(String itemPath) {
		this.itemPath = itemPath;
	}

	public int getNumHijos() {
		return numHijos;
	}

	public void setNumHijos(int numHijos) {
		this.numHijos = numHijos;
	}
}
