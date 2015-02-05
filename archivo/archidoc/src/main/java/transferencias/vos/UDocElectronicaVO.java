package transferencias.vos;

import java.util.Date;

import common.vos.BaseVO;

/**
 * Representa las Unidades Documentales Electrónicas Para Transferencias.
 */
public class UDocElectronicaVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String idRelEntrega;
	private int posicion;
	private int estadoCotejo;
	private String notasCotejo;

	// Campos que no pertenecen a la tabla, son la unión de la tabla
	// ASGTUNIDADDOCRE
	private String asunto;
	private String expediente;
	private Date fechaInicio;
	private Date fechaFin;
	private boolean validada;
	private int marcasBloqueo;

	/**
	 * Constructor para la creación de nuevos registros en la base de datos.
	 * 
	 * @param id
	 *            Identificador de la Unidad Documental
	 * @param idRelEntrega
	 *            Identificador de la Relación de Entrega
	 * @param posicion
	 *            Posición que ocupa en la relación. (No Tiene por que ser
	 *            correlativo)
	 * @param estadoCotejo
	 *            Estado de Cotejo.
	 */
	public UDocElectronicaVO(String id, String idRelEntrega, int posicion,
			int estadoCotejo) {
		this.id = id;
		this.idRelEntrega = idRelEntrega;
		this.posicion = posicion;
		this.estadoCotejo = estadoCotejo;
	}

	public UDocElectronicaVO() {

	}

	public int getEstadoCotejo() {
		return estadoCotejo;
	}

	public void setEstadoCotejo(int estadoCotejo) {
		this.estadoCotejo = estadoCotejo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdRelEntrega() {
		return idRelEntrega;
	}

	public void setIdRelEntrega(String idRelEntrega) {
		this.idRelEntrega = idRelEntrega;
	}

	public String getNotasCotejo() {
		return notasCotejo;
	}

	public void setNotasCotejo(String notasCotejo) {
		this.notasCotejo = notasCotejo;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return el validada
	 */
	public boolean isValidada() {
		return validada;
	}

	/**
	 * @param validada
	 *            el validada a establecer
	 */
	public void setValidada(boolean validada) {
		this.validada = validada;
	}

	public int getMarcasBloqueo() {
		return marcasBloqueo;
	}

	public void setMarcasBloqueo(int marcasBloqueo) {
		this.marcasBloqueo = marcasBloqueo;
	}
}
