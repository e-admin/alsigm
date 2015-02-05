package auditoria.vos;

import java.util.Date;

import common.vos.BaseVO;

/**
 * VO que encapsula los datos de una traza.
 */
public class TrazaVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Identificador de la traza */
	private String id = null;
	/** Identificador del módulo */
	private int modulo = 0;
	/** Nombre del modulo */
	private String moduloName = null;
	/** Identificador de la accion */
	private int accion = 0;
	/** Nombre de la accion */
	private String accionName = null;
	/** Hora de generación de la traza */
	private Date timeStamp = null;
	/** Direccion IP de generacion de la traza */
	private String dirIP = null;
	/** Identificador del usuario */
	private String idUsuario = null;
	/** Nombre del usuario. */
	private String nombre = null;
	/** Apellidos del usuario. */
	private String apellidos = null;
	/** Codigo de error producido */
	private int codError = 0;
	/** Descripcion del error producido */
	private String error = null;

	public int getCodError() {
		return codError;
	}

	public void setCodError(int codError) {
		this.codError = codError;
	}

	public String getDirIP() {
		return dirIP;
	}

	public void setDirIP(String dirIP) {
		this.dirIP = dirIP;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAccion() {
		return accion;
	}

	public void setAccion(int idAccion) {
		this.accion = idAccion;
	}

	public int getModulo() {
		return modulo;
	}

	public void setModulo(int idModulo) {
		this.modulo = idModulo;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getAccionName() {
		return accionName;
	}

	public void setAccionName(String accion) {
		this.accionName = accion;
	}

	public String getModuloName() {
		return moduloName;
	}

	public void setModuloName(String modulo) {
		this.moduloName = modulo;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return Returns the apellidos.
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos
	 *            The apellidos to set.
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
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

	public String getUsuario() {
		return new StringBuffer(getApellidos() == null ? "" : getApellidos())
				.append(getApellidos() == null ? "" : ", ")
				.append(getNombre() == null ? "" : getNombre()).toString();
	}

}
