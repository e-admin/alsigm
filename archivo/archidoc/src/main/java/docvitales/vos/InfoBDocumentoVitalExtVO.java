package docvitales.vos;

import java.util.Date;

/**
 * Información básica extendida de un documento vital.
 */
public class InfoBDocumentoVitalExtVO extends InfoBDocumentoVitalVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Fecha de creación. */
	private Date fechaCr = null;

	/** Identificador del usuario de creación. */
	private String idUsuarioCr = null;

	/** Nombre del usuario de creación. */
	private String nombreUsuarioCr = null;

	/** Fecha de paso a vigente. */
	private Date fechaVig = null;

	/** Identificador del usuario de paso a vigente. */
	private String idUsuarioVig = null;

	/** Nombre del usuario de paso a vigente. */
	private String nombreUsuarioVig = null;

	/** Número de accesos. */
	private int numAccesos = 0;

	/** Fecha del último acceso. */
	private Date fechaUltAcceso = null;

	/** Observaciones. */
	private String observaciones = null;

	/**
	 * Constructor.
	 */
	public InfoBDocumentoVitalExtVO() {
		super();
	}

	/**
	 * @return Returns the fechaCr.
	 */
	public Date getFechaCr() {
		return fechaCr;
	}

	/**
	 * @param fechaCr
	 *            The fechaCr to set.
	 */
	public void setFechaCr(Date fechaCr) {
		this.fechaCr = fechaCr;
	}

	/**
	 * @return Returns the fechaUltAcceso.
	 */
	public Date getFechaUltAcceso() {
		return fechaUltAcceso;
	}

	/**
	 * @param fechaUltAcceso
	 *            The fechaUltAcceso to set.
	 */
	public void setFechaUltAcceso(Date fechaUltAcceso) {
		this.fechaUltAcceso = fechaUltAcceso;
	}

	/**
	 * @return Returns the fechaVig.
	 */
	public Date getFechaVig() {
		return fechaVig;
	}

	/**
	 * @param fechaVig
	 *            The fechaVig to set.
	 */
	public void setFechaVig(Date fechaVig) {
		this.fechaVig = fechaVig;
	}

	/**
	 * @return Returns the idUsuarioCr.
	 */
	public String getIdUsuarioCr() {
		return idUsuarioCr;
	}

	/**
	 * @param idUsuarioCr
	 *            The idUsuarioCr to set.
	 */
	public void setIdUsuarioCr(String idUsuarioCr) {
		this.idUsuarioCr = idUsuarioCr;
	}

	/**
	 * @return Returns the idUsuarioVig.
	 */
	public String getIdUsuarioVig() {
		return idUsuarioVig;
	}

	/**
	 * @param idUsuarioVig
	 *            The idUsuarioVig to set.
	 */
	public void setIdUsuarioVig(String idUsuarioVig) {
		this.idUsuarioVig = idUsuarioVig;
	}

	/**
	 * @return Returns the numAccesos.
	 */
	public int getNumAccesos() {
		return numAccesos;
	}

	/**
	 * @param numAccesos
	 *            The numAccesos to set.
	 */
	public void setNumAccesos(int numAccesos) {
		this.numAccesos = numAccesos;
	}

	/**
	 * @return Returns the observaciones.
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones
	 *            The observaciones to set.
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return Returns the nombreUsuarioCr.
	 */
	public String getNombreUsuarioCr() {
		return nombreUsuarioCr;
	}

	/**
	 * @param nombreUsuarioCr
	 *            The nombreUsuarioCr to set.
	 */
	public void setNombreUsuarioCr(String nombreUsuarioCr) {
		this.nombreUsuarioCr = nombreUsuarioCr;
	}

	/**
	 * @return Returns the nombreUsuarioVig.
	 */
	public String getNombreUsuarioVig() {
		return nombreUsuarioVig;
	}

	/**
	 * @param nombreUsuarioVig
	 *            The nombreUsuarioVig to set.
	 */
	public void setNombreUsuarioVig(String nombreUsuarioVig) {
		this.nombreUsuarioVig = nombreUsuarioVig;
	}

}
