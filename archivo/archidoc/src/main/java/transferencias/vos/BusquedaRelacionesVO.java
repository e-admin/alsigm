package transferencias.vos;

import java.util.Date;

import common.pagination.PageInfo;

/**
 * Criterios de búsqueda de relaciones.
 */
public class BusquedaRelacionesVO {

	private String organo = null;
	private String gestorOficina = null;
	private String gestorArchivo = null;
	private String[] estados = new String[0];
	private String anio = null;
	private String serie = null;
	private String codigoSerie = null;
	private String procedimiento = null;
	private Date fechaInicio = null;
	private Date fechaFin = null;
	private PageInfo pageInfo = null;
	private String idGestor = null;
	private String idGestorArchivo = null;
	private String[] organosUsuario = null;
	private String[] tipos = new String[0];
	private String codigo = null;
	private String[] archivosReceptores = null;
	private boolean isIngresoDirecto = false;
	private String[] idsProductores = null;
	private String idFormato = null;
	private String observaciones = null;
	private Date fechaEstadoInicio = null;
	private Date fechaEstadoFin = null;

	/**
	 * Constructor.
	 */
	public BusquedaRelacionesVO() {
	}

	/**
	 * @return Returns the anio.
	 */
	public String getAnio() {
		return anio;
	}

	/**
	 * @param anio
	 *            The anio to set.
	 */
	public void setAnio(String anio) {
		this.anio = anio;
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
	 * @return Returns the fechaFin.
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin
	 *            The fechaFin to set.
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return Returns the fechaInicio.
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio
	 *            The fechaInicio to set.
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return Returns the organo.
	 */
	public String getOrgano() {
		return organo;
	}

	/**
	 * @param organo
	 *            The organo to set.
	 */
	public void setOrgano(String organo) {
		this.organo = organo;
	}

	/**
	 * @return Returns the gestorArchivo.
	 */
	public String getGestorArchivo() {
		return gestorArchivo;
	}

	/**
	 * @param gestorArchivo
	 *            The gestorArchivo to set.
	 */
	public void setGestorArchivo(String gestorArchivo) {
		this.gestorArchivo = gestorArchivo;
	}

	/**
	 * @return Returns the gestorOficina.
	 */
	public String getGestorOficina() {
		return gestorOficina;
	}

	/**
	 * @param gestorOficina
	 *            The gestorOficina to set.
	 */
	public void setGestorOficina(String gestorOficina) {
		this.gestorOficina = gestorOficina;
	}

	/**
	 * @return Returns the procedimiento.
	 */
	public String getProcedimiento() {
		return procedimiento;
	}

	/**
	 * @param procedimiento
	 *            The procedimiento to set.
	 */
	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	/**
	 * @return Returns the serie.
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie
	 *            The serie to set.
	 */
	public void setSerie(String serie) {
		this.serie = serie;
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

	public String getIdGestor() {
		return idGestor;
	}

	public void setIdGestor(String idGestor) {
		this.idGestor = idGestor;
	}

	/**
	 * @return Returns the idsOrganos.
	 */
	public String[] getOrganosUsuario() {
		return organosUsuario;
	}

	/**
	 * @param idsOrganos
	 *            The idsOrganos to set.
	 */
	public void setOrganosUsuario(String[] idsOrganos) {
		this.organosUsuario = idsOrganos;
	}

	/**
	 * @return Returns the tipos.
	 */
	public String[] getTipos() {
		return tipos;
	}

	/**
	 * @param tipos
	 *            The tipos to set.
	 */
	public void setTipos(String[] tipos) {
		this.tipos = tipos;
	}

	/**
	 * @return Returns the codigo.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            The codigo to set.
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return Returns the idArchivoReceptor.
	 */
	public String[] getArchivosReceptores() {
		return archivosReceptores;
	}

	/**
	 * @param archivosReceptores
	 *            The idArchivoReceptor to set.
	 */
	public void setArchivosReceptores(String[] archivosReceptores) {
		this.archivosReceptores = archivosReceptores;
	}

	/**
	 * @return Returns the idGestorArchivo.
	 */
	public String getIdGestorArchivo() {
		return idGestorArchivo;
	}

	/**
	 * @param idGestorArchivo
	 *            The idGestorArchivo to set.
	 */
	public void setIdGestorArchivo(String idGestorArchivo) {
		this.idGestorArchivo = idGestorArchivo;
	}

	/**
	 * @return Returns the codigoSerie.
	 */
	public String getCodigoSerie() {
		return codigoSerie;
	}

	/**
	 * @param codigoSerie
	 *            The codigoSerie to set.
	 */
	public void setCodigoSerie(String codigoSerie) {
		this.codigoSerie = codigoSerie;
	}

	public boolean isIngresoDirecto() {
		return isIngresoDirecto;
	}

	public void setIngresoDirecto(boolean isIngresoDirecto) {
		this.isIngresoDirecto = isIngresoDirecto;
	}

	public void setIdsProductores(String[] idsProductores) {
		this.idsProductores = idsProductores;
	}

	public String[] getIdsProductores() {
		return idsProductores;
	}

	public void setIdFormato(String idFormato) {
		this.idFormato = idFormato;
	}

	public String getIdFormato() {
		return idFormato;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setFechaEstadoInicio(Date fechaEstadoInicio) {
		this.fechaEstadoInicio = fechaEstadoInicio;
	}

	public Date getFechaEstadoInicio() {
		return fechaEstadoInicio;
	}

	public void setFechaEstadoFin(Date fechaEstadoFin) {
		this.fechaEstadoFin = fechaEstadoFin;
	}

	public Date getFechaEstadoFin() {
		return fechaEstadoFin;
	}
}
