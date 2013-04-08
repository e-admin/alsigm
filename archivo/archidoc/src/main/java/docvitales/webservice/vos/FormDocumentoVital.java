package docvitales.webservice.vos;

import java.io.Serializable;
import java.util.Date;

import common.view.POUtils;

import docvitales.vos.FormDocumentoVitalVO;

/**
 * Información del formulario de creación de un documento vital.
 */
public class FormDocumentoVital implements Serializable {

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

	/**
	 * Constructor.
	 */
	public FormDocumentoVital() {
	}

	/**
	 * Constructor.
	 * 
	 * @param form
	 *            Información del formulario.
	 */
	public FormDocumentoVital(FormDocumentoVitalVO form) {
		this();
		POUtils.copyVOProperties(this, form);
	}

	/**
	 * Obtiene la información del formulario.
	 * 
	 * @return Información del formulario
	 */
	public FormDocumentoVitalVO getVO() {
		FormDocumentoVitalVO vo = new FormDocumentoVitalVO();
		POUtils.copyVOProperties(vo, this);
		return vo;
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
}
