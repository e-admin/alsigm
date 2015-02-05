package solicitudes.prestamos.vos;

import java.util.Date;

import common.util.DateUtils;

import solicitudes.prestamos.PrestamosConstants;

public class ProrrogaVO {

	private String id = null;

	private String idPrestamo = null;

	private int estado;

	private Date festado = null;

	private String motivoRechazo = null;

	private String idMotivo = null;

	private Date fechaFinProrroga = null;

	private String motivoProrroga = null;

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
	 * @return Returns the festado.
	 */
	public Date getFestado() {
		return festado;
	}

	/**
	 * @param festado
	 *            The festado to set.
	 */
	public void setFestado(Date festado) {
		this.festado = festado;
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
	 * @return Returns the idPrestamo.
	 */
	public String getIdPrestamo() {
		return idPrestamo;
	}

	/**
	 * @param idPrestamo
	 *            The idPrestamo to set.
	 */
	public void setIdPrestamo(String idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	/**
	 * @return Returns the motivoRechazo.
	 */
	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	/**
	 * @param motivoRechazo
	 *            The motivoRechazo to set.
	 */
	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public String getIdMotivo() {
		return idMotivo;
	}

	public void setIdMotivo(String idMotivo) {
		this.idMotivo = idMotivo;
	}

	public void setFechaFinProrroga(Date fechaFinProrroga) {
		this.fechaFinProrroga = fechaFinProrroga;
	}

	public Date getFechaFinProrroga() {
		return fechaFinProrroga;
	}

	public void setMotivoProrroga(String motivoProrroga) {
		this.motivoProrroga = motivoProrroga;
	}

	public String getMotivoProrroga() {
		return motivoProrroga;
	}

	public boolean isDenegada() {
		return PrestamosConstants.ESTADO_PRORROGA_DENEGADA == estado;
	}

	public boolean isAutorizada() {
		return PrestamosConstants.ESTADO_PRORROGA_AUTORIZADA == estado;
	}

	public boolean isSolicitada() {
		return PrestamosConstants.ESTADO_PRORROGA_SOLICITADA == estado;
	}

	public String getFechaFinProrrogaAsString() {
		return DateUtils.formatDate(fechaFinProrroga);
	}

	public String getFechaEstadoAsString() {
		return DateUtils.formatTimestamp(festado);
	}
}
