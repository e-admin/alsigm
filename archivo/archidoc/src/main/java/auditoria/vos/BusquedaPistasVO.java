package auditoria.vos;

import java.util.Date;

import common.vos.BaseVO;

/**
 * VO para la busqueda de pistas de auditoría
 */
public class BusquedaPistasVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Modulo al que pertenece el evento */
	private int modulo = 0;
	/** Accion */
	private int accion = 0;
	/** Grupo de usuarios */
	private String grupo = null;
	/** Direccion IP desde la que se realizo el evento */
	private String ip = null;
	/** Fecha de inicio/fin en que se produjo el evento */
	private Date fechaIni = null;
	private Date fechaFin = null;

	public int getAccion() {
		return accion;
	}

	public void setAccion(int accion) {
		this.accion = accion;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getModulo() {
		return modulo;
	}

	public void setModulo(int modulo) {
		this.modulo = modulo;
	}
}
