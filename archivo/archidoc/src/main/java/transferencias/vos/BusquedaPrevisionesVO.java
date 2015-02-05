package transferencias.vos;

import java.util.Date;

import common.pagination.PageInfo;

/**
 * Criterios de búsqueda de previsiones.
 */
public class BusquedaPrevisionesVO {

	private String organo = null;
	private String usuario = null;
	private String fondo = null;
	private String[] tipos = new String[0];
	private String anio = null;
	private String[] estados = new String[0];
	private Date fechaInicio = null;
	private Date fechaFin = null;
	private PageInfo pageInfo = null;
	private String idGestor = null;
	private String[] organosUsuario = null;
	private String codigo = null;
	private String[] archivosReceptores = null;

	/**
	 * Constructor.
	 */
	public BusquedaPrevisionesVO() {
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
	 * @return Returns the fondo.
	 */
	public String getFondo() {
		return fondo;
	}

	/**
	 * @param fondo
	 *            The fondo to set.
	 */
	public void setFondo(String fondo) {
		this.fondo = fondo;
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
	 * @return Returns the usuario.
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            The usuario to set.
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
	 * @return Returns the archivosReceptores.
	 */
	public String[] getArchivosReceptores() {
		return archivosReceptores;
	}

	/**
	 * @param archivosReceptores
	 *            The archivosReceptores to set.
	 */
	public void setArchivosReceptores(String[] archivosReceptores) {
		this.archivosReceptores = archivosReceptores;
	}
}
