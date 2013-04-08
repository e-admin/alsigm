package solicitudes.consultas.vos;

import java.util.Date;

import common.pagination.PageInfo;
import common.vos.BaseVO;

/**
 * VO para la busqueda de consultas
 */
public class BusquedaVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String codigo = null;
	private String organo = null;
	private String solicitante = null;
	private String[] estados = new String[0];
	private Date fechaInicioEntrega = null;
	private Date fechaFinEntrega = null;

	private String idSolicitante = null;

	/** Informacion sobre la paginacion de busqueda */
	private PageInfo pageInfo = null;

	private String datosautorizado = null;
	private String tipoentrega = null;
	private String expedienteudoc = null;

	private String idAppUser = null;
	private String[] idsArchivosUser = null;

	/**
	 * Obtiene el solicitante del préstamo
	 * 
	 * @return solicitante del préstamo
	 */
	public String getSolicitante() {
		return solicitante;
	}

	/**
	 * Establece el solicitante del prestamo.
	 * 
	 * @param solicitante
	 *            solicitante del prestamo.
	 */
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	/**
	 * Obtiene los estados del prestamo
	 * 
	 * @return Listado de identificadores de estaod
	 */
	public String[] getEstados() {
		return estados;
	}

	/**
	 * Establece los estados del prestamos
	 * 
	 * @param estados
	 *            Listado de los identificadores de los estados
	 */
	public void setEstados(String[] estados) {
		this.estados = estados;
	}

	public Date getFechaFinEntrega() {
		return fechaFinEntrega;
	}

	public void setFechaFinEntrega(Date fechaFinEntrega) {
		this.fechaFinEntrega = fechaFinEntrega;
	}

	public Date getFechaInicioEntrega() {
		return fechaInicioEntrega;
	}

	public void setFechaInicioEntrega(Date fechaInicioEntrega) {
		this.fechaInicioEntrega = fechaInicioEntrega;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public String getOrgano() {
		return organo;
	}

	public void setOrgano(String organo) {
		this.organo = organo;
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
	 * @return Returns the idGestor.
	 */
	public String getIdSolicitante() {
		return idSolicitante;
	}

	/**
	 * @param idGestor
	 *            The idGestor to set.
	 */
	public void setIdSolicitante(String idGestor) {
		this.idSolicitante = idGestor;
	}

	public String getDatosautorizado() {
		return datosautorizado;
	}

	public void setDatosautorizado(String datosautorizado) {
		this.datosautorizado = datosautorizado;
	}

	public String getTipoentrega() {
		return tipoentrega;
	}

	public void setTipoentrega(String tipoentrega) {
		this.tipoentrega = tipoentrega;
	}

	public String getExpedienteudoc() {
		return expedienteudoc;
	}

	public void setExpedienteudoc(String expedienteudoc) {
		this.expedienteudoc = expedienteudoc;
	}

	public void setIdAppUser(String idAppUser) {
		this.idAppUser = idAppUser;
	}

	public String getIdAppUser() {
		return idAppUser;
	}

	public void setIdsArchivosUser(String[] idsArchivosUser) {
		this.idsArchivosUser = idsArchivosUser;
	}

	public String[] getIdsArchivosUser() {
		return idsArchivosUser;
	}

}
