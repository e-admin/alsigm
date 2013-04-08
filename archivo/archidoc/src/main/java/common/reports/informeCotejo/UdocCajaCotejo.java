package common.reports.informeCotejo;

import common.vos.BaseVO;

public class UdocCajaCotejo extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String ordenCaja;
	private String estadoCaja;
	private String signaturaCaja;
	private String devolverCaja;
	private String observacionesCaja;
	private String tituloCaja;

	/**
	 * Indica si la unidad documental es física
	 */
	private Boolean isElectronica;

	private String expediente;
	private String asunto;
	private String fechaInicio;
	private String fechaFin;
	private String signatura;
	private String estado;
	private String observaciones;

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getSignatura() {
		return signatura;
	}

	public void setSignatura(String signatura) {
		this.signatura = signatura;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getOrdenCaja() {
		return ordenCaja;
	}

	public void setOrdenCaja(String ordenCaja) {
		this.ordenCaja = ordenCaja;
	}

	/**
	 * Sobrecargado para poder pasar el orden como int
	 * 
	 * @param orden
	 */
	public void setOrdenCaja(int ordenCaja) {
		this.ordenCaja = new Integer(ordenCaja).toString();
	}

	public String getEstadoCaja() {
		return estadoCaja;
	}

	public void setEstadoCaja(String estadoCaja) {
		this.estadoCaja = estadoCaja;
	}

	public String getSignaturaCaja() {
		return signaturaCaja;
	}

	public void setSignaturaCaja(String signaturaCaja) {
		this.signaturaCaja = signaturaCaja;
	}

	public String getDevolverCaja() {
		return devolverCaja;
	}

	public void setDevolverCaja(String devolverCaja) {
		this.devolverCaja = devolverCaja;
	}

	public String getObservacionesCaja() {
		return observacionesCaja;
	}

	public void setObservacionesCaja(String observacionesCaja) {
		this.observacionesCaja = observacionesCaja;
	}

	public Boolean getIsElectronica() {
		return isElectronica;
	}

	public void setIsElectronica(Boolean isElectronica) {
		this.isElectronica = isElectronica;
	}

	public String getTituloCaja() {
		return tituloCaja;
	}

	public void setTituloCaja(String tituloCaja) {
		this.tituloCaja = tituloCaja;
	}

}
