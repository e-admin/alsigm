package solicitudes.vos;

import java.util.Date;

import solicitudes.SolicitudesConstants;

import common.CodigoTransferenciaUtils;
import common.vos.BaseVO;

/**
 * Clase que encapsula la información de la unidad documental no disponible.
 */
public class DetalleUdocNoDisponibleVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * private String idSolicitud = null; private String anoSolicitud = null;
	 * private int ordenSolicitud = 0;
	 */
	private String solicitanteSolicitud = null;
	private SolicitudVO solicitud = null;
	private String idUdoc = null;
	private int estadoUdoc = 0;
	private Date fechaInicialUsoUdoc = null;
	private Date fechaFinalUsoUdoc = null;
	private String observaciones = null;

	/**
	 * Constructor por defecto
	 */
	public DetalleUdocNoDisponibleVO() {
		super();
	}

	/**
	 * @return Returns the estado.
	 */
	public int getEstadoUdoc() {
		return estadoUdoc;
	}

	/**
	 * @param estado
	 *            The estado to set.
	 */
	public void setEstadoUdoc(int estado) {
		this.estadoUdoc = estado;
	}

	/**
	 * @return Returns the fechaFinalUso.
	 */
	public Date getFechaFinalUsoUdoc() {
		return fechaFinalUsoUdoc;
	}

	/**
	 * @param fechaFinalUso
	 *            The fechaFinalUso to set.
	 */
	public void setFechaFinalUsoUdoc(Date fechaFinalUso) {
		this.fechaFinalUsoUdoc = fechaFinalUso;
	}

	/**
	 * @return Returns the fechaInicialUso.
	 */
	public Date getFechaInicialUsoUdoc() {
		return fechaInicialUsoUdoc;
	}

	/**
	 * @param fechaInicialUso
	 *            The fechaInicialUso to set.
	 */
	public void setFechaInicialUsoUdoc(Date fechaInicialUso) {
		this.fechaInicialUsoUdoc = fechaInicialUso;
	}

	/**
	 * @return Returns the idudoc.
	 */
	public String getIdUdoc() {
		return idUdoc;
	}

	/**
	 * @param idudoc
	 *            The idudoc to set.
	 */
	public void setIdUdoc(String idudoc) {
		this.idUdoc = idudoc;
	}

	public SolicitudVO getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(SolicitudVO solicitud) {
		this.solicitud = solicitud;
	}

	public String getCodigoSolicitud() {
		return CodigoTransferenciaUtils.getCodigoTransferencia(
				solicitud.getAno(), solicitud.getArchivo().getCodigo(),
				new Integer(solicitud.getOrden()),
				SolicitudesConstants.FORMAT_ID_SOLICITUD);

	}

	public String getSolicitanteSolicitud() {
		return solicitanteSolicitud;
	}

	public void setSolicitanteSolicitud(String solicitanteSolicitud) {
		this.solicitanteSolicitud = solicitanteSolicitud;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}
