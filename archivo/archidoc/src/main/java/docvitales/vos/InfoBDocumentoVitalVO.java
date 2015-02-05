package docvitales.vos;

import java.util.Date;

import common.view.POUtils;
import common.vos.BaseVO;

import docvitales.EstadoDocumentoVital;

/**
 * Información básica de un documento vital.
 */
public class InfoBDocumentoVitalVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Identificador del documento vital. */
	private String id = null;

	/** Identificador del ciudadano en la BD de Terceros. */
	private String idBdTerceros = null;

	/** Número de identidad del ciudadano. */
	private String numIdentidad = null;

	/** Identidad del ciudadano. */
	private String identidad = null;

	/** Identificador del tipo de documento vital. */
	private String idTipoDocVit = null;

	/** Nombre del tipo de documento vital. */
	private String nombreTipoDocVit = null;

	/** Fecha de caducidad. */
	private Date fechaCad = null;

	/** Estado del documento vital. */
	private int estadoDocVit = EstadoDocumentoVital.PENDIENTE_VALIDACION;

	/** Tamaño del fichero. */
	private double tamanoFich = 0;

	/** Nombre original del fichero. */
	private String nombreOrgFich = null;

	/** Extensión del fichero. */
	private String extFich = null;

	/** Identificador del fichero. */
	private String idFich = null;

	private String idRepEcm = null;

	/**
	 * Constructor.
	 */
	public InfoBDocumentoVitalVO() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param extVO
	 *            Información básica extendidad del documento vital.
	 */
	public InfoBDocumentoVitalVO(InfoBDocumentoVitalExtVO extVO) {
		super();
		if (extVO != null)
			POUtils.copyVOProperties(this, extVO);
	}

	/**
	 * @return Returns the estadoDocVit.
	 */
	public int getEstadoDocVit() {
		return estadoDocVit;
	}

	/**
	 * @param estadoDocVit
	 *            The estadoDocVit to set.
	 */
	public void setEstadoDocVit(int estadoDocVit) {
		this.estadoDocVit = estadoDocVit;
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
	 * @return Returns the fechaCad.
	 */
	public Date getFechaCad() {
		return fechaCad;
	}

	/**
	 * @param fechaCad
	 *            The fechaCad to set.
	 */
	public void setFechaCad(Date fechaCad) {
		this.fechaCad = fechaCad;
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
	 * @return Returns the idBdTerceros.
	 */
	public String getIdBdTerceros() {
		return idBdTerceros;
	}

	/**
	 * @param idBdTerceros
	 *            The idBdTerceros to set.
	 */
	public void setIdBdTerceros(String idBdTerceros) {
		this.idBdTerceros = idBdTerceros;
	}

	/**
	 * @return Returns the identidad.
	 */
	public String getIdentidad() {
		return identidad;
	}

	/**
	 * @param identidad
	 *            The identidad to set.
	 */
	public void setIdentidad(String identidad) {
		this.identidad = identidad;
	}

	/**
	 * @return Returns the idFich.
	 */
	public String getIdFich() {
		return idFich;
	}

	/**
	 * @param idFich
	 *            The idFich to set.
	 */
	public void setIdFich(String idFich) {
		this.idFich = idFich;
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
	 * @return Returns the nombreOrgFich.
	 */
	public String getNombreOrgFich() {
		return nombreOrgFich;
	}

	/**
	 * @param nombreOrgFich
	 *            The nombreOrgFich to set.
	 */
	public void setNombreOrgFich(String nombreOrgFich) {
		this.nombreOrgFich = nombreOrgFich;
	}

	/**
	 * @return Returns the nombreTipoDocVit.
	 */
	public String getNombreTipoDocVit() {
		return nombreTipoDocVit;
	}

	/**
	 * @param nombreTipoDocVit
	 *            The nombreTipoDocVit to set.
	 */
	public void setNombreTipoDocVit(String nombreTipoDocVit) {
		this.nombreTipoDocVit = nombreTipoDocVit;
	}

	/**
	 * @return Returns the numIdentidad.
	 */
	public String getNumIdentidad() {
		return numIdentidad;
	}

	/**
	 * @param numIdentidad
	 *            The numIdentidad to set.
	 */
	public void setNumIdentidad(String numIdentidad) {
		this.numIdentidad = numIdentidad;
	}

	/**
	 * @return Returns the tamanoFich.
	 */
	public double getTamanoFich() {
		return tamanoFich;
	}

	/**
	 * @param tamanoFich
	 *            The tamanoFich to set.
	 */
	public void setTamanoFich(double tamanoFich) {
		this.tamanoFich = tamanoFich;
	}

	public String getIdRepEcm() {
		return idRepEcm;
	}

	public void setIdRepEcm(String idRepEcm) {
		this.idRepEcm = idRepEcm;
	}
}
