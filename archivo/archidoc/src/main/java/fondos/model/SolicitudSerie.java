package fondos.model;

import java.util.Date;

import fondos.vos.SolicitudSerieVO;

/**
 * Value Object para los diferentes tipos de solicitudes que se realzan en la
 * gestión de series documentales: solicitudes de alta de serie, solicitudes de
 * paso a vigente, y a histórica y solicitudes de aprobación de cambios en
 * identificación
 */
public class SolicitudSerie implements SolicitudSerieVO {
	/* Tipos de solicitudes */
	public static final int SOLICITUD_ALTA = 1;
	public static final int PASO_A_VIGENTE = 2;
	public static final int PASO_A_HISTORICA = 3;
	public static final int MOVER = 4;
	public static final int CAMBIOS_EN_IDENTIFICACION = 5;

	/* Estados en los que se puede encontrar una solicitud */
	public static final int ESTADO_SOLICITADA = 1;
	public static final int ESTADO_EJECUTADA = 2;
	public static final int ESTADO_DENEGADA = 3;

	String id;
	String idSerie;
	String etiquetaSerie;
	int tipo;
	Date fecha;
	String info;
	String motivoSolicitud;
	String idUsrSolicitante;
	int estado;
	Date fechaEstado;
	String idUsrGestor;
	String motivoRechazo;
	boolean puedeSerAutorizada = false;
	Boolean puedeSerEliminada = null;

	public SolicitudSerie(int tipo) {
		this.tipo = tipo;
	}

	public SolicitudSerie() {
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getEtiquetaSerie() {
		return etiquetaSerie;
	}

	public void setEtiquetaSerie(String etiquetaSerie) {
		this.etiquetaSerie = etiquetaSerie;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(String idSerie) {
		this.idSerie = idSerie;
	}

	public String getIdUsrGestor() {
		return idUsrGestor;
	}

	public void setIdUsrGestor(String idUsrGestor) {
		this.idUsrGestor = idUsrGestor;
	}

	public String getIdUsrSolicitante() {
		return idUsrSolicitante;
	}

	public void setIdUsrSolicitante(String idUsrSolicitante) {
		this.idUsrSolicitante = idUsrSolicitante;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public String getMotivoSolicitud() {
		return motivoSolicitud;
	}

	public void setMotivoSolicitud(String motivoSolicitud) {
		this.motivoSolicitud = motivoSolicitud;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public boolean isSolicitada() {
		return estado == ESTADO_SOLICITADA;
	}

	public boolean isEjecutada() {
		return estado == ESTADO_EJECUTADA;
	}

	public boolean isDenegada() {
		return estado == ESTADO_DENEGADA;
	}

	public boolean isTipoSolicitudAlta() {
		return getTipo() == SOLICITUD_ALTA ? true : false;
	}

	public boolean isTipoPasoAVigente() {
		return getTipo() == PASO_A_VIGENTE ? true : false;
	}

	public boolean isTipoCambiosEnIdentificacion() {
		return getTipo() == CAMBIOS_EN_IDENTIFICACION ? true : false;
	}

	public boolean getPuedeSerAutorizada() {
		return puedeSerAutorizada;
	}

	public void setPuedeSerAutorizada(boolean puedeSerAutorizada) {
		this.puedeSerAutorizada = puedeSerAutorizada;
	}

	public boolean getPuedeSerEliminada() {
		boolean returnValue = false;
		if (puedeSerEliminada == null)
			returnValue = (estado == ESTADO_DENEGADA || estado == ESTADO_EJECUTADA);
		else
			returnValue = puedeSerEliminada.booleanValue();
		return returnValue;
	}

	public void setPuedeSerEliminada(boolean puedeSerEliminada) {
		this.puedeSerEliminada = new Boolean(puedeSerEliminada);
	}

}