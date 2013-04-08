package docvitales.vos;

import java.util.Date;

import common.pagination.PageInfo;
import common.vos.BaseVO;

/**
 * Información de un tipo de documento vital.
 */
public class BusquedaDocumentosVitalesVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Número de identidad del ciudadano. */
	private String numIdentidad = null;

	/** Identidad del ciudadano. */
	private String identidad = null;

	/** Tipos de documentos vitales. */
	private String[] tiposDocumentos = null;

	/** Estado del documento vital. */
	private String[] estados = null;

	/** Fecha de inicio de la caducidad. */
	private Date fechaCaducidadIni = null;

	/** Fecha de fin de la caducidad. */
	private Date fechaCaducidadFin = null;

	/** Comparador de número de accesos. */
	private String numAccesosComp = null;

	/** Número de accesos. */
	private String numAccesos = null;

	/** Fecha de inicio del último acceso. */
	private Date fechaUltimoAccesoIni = null;

	/** Fecha de fin del último acceso. */
	private Date fechaUltimoAccesoFin = null;

	/** Información de la paginación. */
	private PageInfo pageInfo = null;

	/**
	 * Constructor.
	 */
	public BusquedaDocumentosVitalesVO() {
	}

	/**
	 * @return Returns the estados.
	 */
	public String[] getEstados() {
		return estados;
	}

	/**
	 * @param estados
	 *            The estados to set.
	 */
	public void setEstados(String[] estados) {
		this.estados = estados;
	}

	/**
	 * @return Returns the fechaCaducidadFin.
	 */
	public Date getFechaCaducidadFin() {
		return fechaCaducidadFin;
	}

	/**
	 * @param fechaCaducidadFin
	 *            The fechaCaducidadFin to set.
	 */
	public void setFechaCaducidadFin(Date fechaCaducidadFin) {
		this.fechaCaducidadFin = fechaCaducidadFin;
	}

	/**
	 * @return Returns the fechaCaducidadIni.
	 */
	public Date getFechaCaducidadIni() {
		return fechaCaducidadIni;
	}

	/**
	 * @param fechaCaducidadIni
	 *            The fechaCaducidadIni to set.
	 */
	public void setFechaCaducidadIni(Date fechaCaducidadIni) {
		this.fechaCaducidadIni = fechaCaducidadIni;
	}

	/**
	 * @return Returns the fechaUltimoAccesoFin.
	 */
	public Date getFechaUltimoAccesoFin() {
		return fechaUltimoAccesoFin;
	}

	/**
	 * @param fechaUltimoAccesoFin
	 *            The fechaUltimoAccesoFin to set.
	 */
	public void setFechaUltimoAccesoFin(Date fechaUltimoAccesoFin) {
		this.fechaUltimoAccesoFin = fechaUltimoAccesoFin;
	}

	/**
	 * @return Returns the fechaUltimoAccesoIni.
	 */
	public Date getFechaUltimoAccesoIni() {
		return fechaUltimoAccesoIni;
	}

	/**
	 * @param fechaUltimoAccesoIni
	 *            The fechaUltimoAccesoIni to set.
	 */
	public void setFechaUltimoAccesoIni(Date fechaUltimoAccesoIni) {
		this.fechaUltimoAccesoIni = fechaUltimoAccesoIni;
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
	 * @return Returns the numAccesos.
	 */
	public String getNumAccesos() {
		return numAccesos;
	}

	/**
	 * @param numAccesos
	 *            The numAccesos to set.
	 */
	public void setNumAccesos(String numAccesos) {
		this.numAccesos = numAccesos;
	}

	/**
	 * @return Returns the numAccesosComp.
	 */
	public String getNumAccesosComp() {
		return numAccesosComp;
	}

	/**
	 * @param numAccesosComp
	 *            The numAccesosComp to set.
	 */
	public void setNumAccesosComp(String numAccesosComp) {
		this.numAccesosComp = numAccesosComp;
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
	 * @return Returns the tiposDocumentos.
	 */
	public String[] getTiposDocumentos() {
		return tiposDocumentos;
	}

	/**
	 * @param tiposDocumentos
	 *            The tiposDocumentos to set.
	 */
	public void setTiposDocumentos(String[] tiposDocumentos) {
		this.tiposDocumentos = tiposDocumentos;
	}

	/**
	 * @return Returns the pageInfo.
	 */
	public PageInfo getPageInfo() {
		return pageInfo;
	}

	/**
	 * @param pageInfo
	 *            The pageInfo to set.
	 */
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
