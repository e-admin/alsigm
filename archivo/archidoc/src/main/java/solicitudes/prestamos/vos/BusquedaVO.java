package solicitudes.prestamos.vos;

import java.util.Date;

import common.pagination.PageInfo;
import common.vos.BaseVO;

/**
 * VO para la busqueda de prestamos
 */
public class BusquedaVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String organo = null;
	private String solicitante = null;
	private String[] estados = new String[0];
	private String[] notas = new String[0];
	private Date fechaInicioEntrega = null;
	private Date fechaFinEntrega = null;
	private String gestor = null;
	private String codigo = null;
	private String[] organosUsuarioSolicitante = new String[0];

	private String datosautorizado = null;
	private String tipoentrega = null;
	private String expedienteudoc = null;

	private String idAppUser = null;
	private String[] idsArchivosUser = null;

	/** Información sobre la paginacion en la busqueda */
	private PageInfo pageInfo = null;

	/**
	 * Obtiene las notas asociadas al prestamo.
	 * 
	 * @return Notas asociadas al prestamos.
	 */
	public String[] getNotas() {
		return notas;
	}

	/**
	 * Establece las notas asociadas al préstamo.
	 * 
	 * @param notas
	 *            Notas asociadas al préstamo.
	 */
	public void setNotas(String[] notas) {
		this.notas = notas;
	}

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

	public String getGestor() {
		return gestor;
	}

	public void setGestor(String gestor) {
		this.gestor = gestor;
	}

	public String getOrgano() {
		return organo;
	}

	public void setOrgano(String organo) {
		this.organo = organo;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
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
	 * @return Returns the organosUsuarioGestor.
	 */
	public String[] getOrganosUsuarioSolicitante() {
		return organosUsuarioSolicitante;
	}

	/**
	 * @param organosUsuarioGestor
	 *            The organosUsuarioGestor to set.
	 */
	public void setOrganosUsuarioSolicitante(String[] organosUsuarioGestor) {
		this.organosUsuarioSolicitante = organosUsuarioGestor;
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
