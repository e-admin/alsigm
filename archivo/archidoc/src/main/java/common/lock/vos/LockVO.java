package common.lock.vos;

import java.util.Date;

import common.vos.BaseVO;

/**
 * VO que encapsula los datos de un bloqueo.
 */
public class LockVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del objeto bloqueado. */
	private String idObj = null;

	/** Tipo de objeto. */
	private int tipoObj = 0;

	/** Módulo donde se está bloqueando. */
	private int modulo = 0;

	/** Fecha y hora del bloqueo. */
	private Date ts = null;

	/** Identificador del usuario que ha realizado el bloqueo. */
	private String idUsuario = null;

	/** Nombre del usuario que ha realizado el bloqueo. */
	private String nombre = null;

	/** Apellidos del usuario que ha realizado el bloqueo. */
	private String apellidos = null;

	/**
	 * Constructor.
	 */
	public LockVO() {
	}

	/**
	 * Constructor.
	 */
	public LockVO(String idObj, int tipoObj, int modulo) {
		this(idObj, tipoObj, modulo, new Date(), null);
	}

	/**
	 * Constructor.
	 */
	public LockVO(String idObj, int tipoObj, int modulo, String idUsuario) {
		this(idObj, tipoObj, modulo, new Date(), idUsuario);
	}

	/**
	 * Constructor.
	 */
	public LockVO(String idObj, int tipoObj, int modulo, Date ts,
			String idUsuario) {
		setIdObj(idObj);
		setTipoObj(tipoObj);
		setModulo(modulo);
		setTs(ts);
		setIdUsuario(idUsuario);
	}

	public String getIdObj() {
		return idObj;
	}

	public void setIdObj(String idObj) {
		this.idObj = idObj;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getModulo() {
		return modulo;
	}

	public void setModulo(int modulo) {
		this.modulo = modulo;
	}

	public int getTipoObj() {
		return tipoObj;
	}

	public void setTipoObj(int tipoObj) {
		this.tipoObj = tipoObj;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
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
