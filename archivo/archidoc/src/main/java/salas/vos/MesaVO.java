package salas.vos;

import java.util.Date;

import common.vos.BaseVO;

/**
 * VO que representa la tabla ASGSMESA
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class MesaVO extends BaseVO {

	private static final long serialVersionUID = 1L;

	/**
	 * Identificador de la mesa
	 */
	private String id = null;

	/**
	 * Código único dentro de la sala
	 */
	private String codigo = null;

	/**
	 * Número de orden dentro de la sala
	 */
	private Integer numOrden = null;

	/**
	 * Identificador de la sala a la que pertenece
	 */
	private String idSala = null;

	/**
	 * Estado de la mesa (Libre, Ocupado o Inutilizado)
	 */
	private String estado = null;

	/**
	 * Fecha de Estado de la mesa
	 */
	private Date fechaEstado = null;

	/**
	 * Identificador del usuario de consulta de sala
	 */
	private String idUsrCSala = null;

	/* Para obtener los datos del usuario de consulta */
	private String nombreUsuario = null;
	private String apellidosUsuario = null;

	public MesaVO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Integer getNumOrden() {
		return numOrden;
	}

	public void setNumorden(Integer numOrden) {
		this.numOrden = numOrden;
	}

	public String getIdSala() {
		return idSala;
	}

	public void setIdSala(String idSala) {
		this.idSala = idSala;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	public String getIdUsrCSala() {
		return idUsrCSala;
	}

	public void setIdUsrCSala(String idUsrCSala) {
		this.idUsrCSala = idUsrCSala;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getApellidosUsuario() {
		return apellidosUsuario;
	}

	public void setApellidosUsuario(String apellidosUsuario) {
		this.apellidosUsuario = apellidosUsuario;
	}

}