package salas.vos;

import java.util.Date;

import common.util.DateUtils;
import common.vos.BaseVO;

/**
 * VO que representa la tabla ASGSREGCSALA
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class RegistroConsultaSalaVO extends BaseVO {

	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del registro
	 */
	private String id = null;

	/**
	 * Identificador del usuario de consulta de sala
	 */
	private String idUsrCSala = null;

	/**
	 * Fecha de asistencia a la sala
	 */
	private Date fechaEntrada = null;

	/**
	 * Fecha de salida de la sala
	 */
	private Date fechaSalida = null;

	/**
	 * Número de documento identificativo
	 */
	private String numDocIdentificacion = null;

	/**
	 * Nombre y Apellidos de usuario de consulta
	 */
	private String nombreApellidos = null;

	/**
	 * Identificador del usuario con acceso a la aplicación
	 */
	private String idScaUsr = null;

	/**
	 * Identificador del archivo
	 */
	private String idArchivo = null;

	/**
	 * Nombre del archivo
	 */
	private String nombreArchivo = null;

	/**
	 * Ruta de la mesa asignada
	 */
	private String mesaAsignada = null;

	public RegistroConsultaSalaVO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdUsrCSala() {
		return idUsrCSala;
	}

	public void setIdUsrCSala(String idUsrCSala) {
		this.idUsrCSala = idUsrCSala;
	}

	public Date getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(Date fEntrada) {
		this.fechaEntrada = fEntrada;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fSalida) {
		this.fechaSalida = fSalida;
	}

	public String getNumDocIdentificacion() {
		return numDocIdentificacion;
	}

	public void setNumDocIdentificacion(String numDocIdentificacion) {
		this.numDocIdentificacion = numDocIdentificacion;
	}

	public String getNombreApellidos() {
		return nombreApellidos;
	}

	public void setNombreApellidos(String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}

	public String getIdScaUsr() {
		return idScaUsr;
	}

	public void setIdScaUsr(String idScaUsr) {
		this.idScaUsr = idScaUsr;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getMesaAsignada() {
		return mesaAsignada;
	}

	public void setMesaAsignada(String mesaAsignada) {
		this.mesaAsignada = mesaAsignada;
	}

	/*
	 * public String getFechaEntrada(){ if(this.getFEntrada() != null){ return
	 * DateUtils.formatDate(getFEntrada()); } return null; }
	 */

	public String getHoraEntrada() {
		if (this.getFechaEntrada() != null) {
			return DateUtils.formatTime(getFechaEntrada());
		}
		return null;
	}

	public String getHoraSalida() {
		if (this.getFechaSalida() != null) {
			return DateUtils.formatTime(getFechaSalida());
		}
		return null;
	}

	public boolean isRegistroCerrado() {
		return (getFechaSalida() != null);
	}
}