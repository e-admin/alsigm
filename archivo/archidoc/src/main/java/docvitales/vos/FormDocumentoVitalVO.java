package docvitales.vos;

import java.util.Date;

import common.vos.BaseVO;

/**
 * Información del formulario de creación de un documento vital.
 */
public class FormDocumentoVitalVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del ciudadano en la BD de Terceros. */
	private String idTercero = null;

	/** Identificador del tipo de documento vital. */
	private String idTipoDocVit = null;

	/** Fecha de caducidad. */
	private Date fechaCaducidad = null;

	/** Nombre original del fichero. */
	private String nombreFich = null;

	/** Extensión del fichero. */
	private String extFich = null;

	/** Contenido del fichero. */
	private byte[] contenidoFich = null;

	/** Observaciones. */
	private String observaciones = null;

	/**
	 * Constructor.
	 */
	public FormDocumentoVitalVO() {
		super();
	}

	/**
	 * @return Returns the contenidoFich.
	 */
	public byte[] getContenidoFich() {
		return contenidoFich;
	}

	/**
	 * @param contenidoFich
	 *            The contenidoFich to set.
	 */
	public void setContenidoFich(byte[] contenidoFich) {
		this.contenidoFich = contenidoFich;
	}

	/**
	 * @return Returns the extFich.
	 */
	public String getExtFich() {
		return extFich;
	}

	/**
	 * @param extFich
	 *            The extFich to set.
	 */
	public void setExtFich(String extFich) {
		this.extFich = extFich;
	}

	/**
	 * @return Returns the fechaCaducidad.
	 */
	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	/**
	 * @param fechaCaducidad
	 *            The fechaCaducidad to set.
	 */
	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	/**
	 * @return Returns the idTercero.
	 */
	public String getIdTercero() {
		return idTercero;
	}

	/**
	 * @param idTercero
	 *            The idTercero to set.
	 */
	public void setIdTercero(String idTercero) {
		this.idTercero = idTercero;
	}

	/**
	 * @return Returns the idTipoDocVit.
	 */
	public String getIdTipoDocVit() {
		return idTipoDocVit;
	}

	/**
	 * @param idTipoDocVit
	 *            The idTipoDocVit to set.
	 */
	public void setIdTipoDocVit(String idTipoDocVit) {
		this.idTipoDocVit = idTipoDocVit;
	}

	/**
	 * @return Returns the nombreFich.
	 */
	public String getNombreFich() {
		return nombreFich;
	}

	/**
	 * @param nombreFich
	 *            The nombreFich to set.
	 */
	public void setNombreFich(String nombreFich) {
		this.nombreFich = nombreFich;
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
}
