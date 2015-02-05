package fondos.vos;

import java.util.Date;

import se.usuarios.ServiceClient;

import common.pagination.PageInfo;

public class BusquedaSolicitudesSerieVO {
	private String fondo = null;
	private String etiqueta = null;
	private String fechaEstado = null;
	private String[] estados = new String[0];
	private String[] niveles = new String[0];
	private Date fechaInicioEstado = null;
	private Date fechaFinEstado = null;
	private PageInfo pageInfo = null;
	private ServiceClient serviceClient = null;
	private String idUsuarioGestor = null;
	private String idUsuarioSolicitante = null;
	private String nombreUsuarioGestor = null;
	private String nombreUsuarioSolicitante = null;

	/**
	 * Constructor.
	 */
	public BusquedaSolicitudesSerieVO() {
	}

	public String getIdUsuarioGestor() {
		return idUsuarioGestor;
	}

	public void setIdUsuarioGestor(String idUsuarioGestor) {
		this.idUsuarioGestor = idUsuarioGestor;
	}

	public String getIdUsuarioSolicitante() {
		return idUsuarioSolicitante;
	}

	public void setIdUsuarioSolicitante(String idUsuarioSolicitante) {
		this.idUsuarioSolicitante = idUsuarioSolicitante;
	}

	public String getNombreUsuarioGestor() {
		return nombreUsuarioGestor;
	}

	public void setNombreUsuarioGestor(String nombreUsuarioGestor) {
		this.nombreUsuarioGestor = nombreUsuarioGestor;
	}

	public String getNombreUsuarioSolicitante() {
		return nombreUsuarioSolicitante;
	}

	public void setNombreUsuarioSolicitante(String nombreUsuarioSolicitante) {
		this.nombreUsuarioSolicitante = nombreUsuarioSolicitante;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public Date getFechaFinEstado() {
		return fechaFinEstado;
	}

	public void setFechaFinEstado(Date fechaFinEstado) {
		this.fechaFinEstado = fechaFinEstado;
	}

	public Date getFechaInicioEstado() {
		return fechaInicioEstado;
	}

	public void setFechaInicioEstado(Date fechaInicioEstado) {
		this.fechaInicioEstado = fechaInicioEstado;
	}

	public String getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(String fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	// public String getReferencia() {
	// return referencia;
	// }
	// public void setReferencia(String referencia) {
	// this.referencia = referencia;
	// }
	//
	// /**
	// * @return Returns the codigo.
	// */
	// public String getCodigo()
	// {
	// return codigo;
	// }
	// /**
	// * @param codigo The codigo to set.
	// */
	// public void setCodigo(String codigo)
	// {
	// this.codigo = codigo;
	// }
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
	 * @return Returns the niveles.
	 */
	public String[] getNiveles() {
		return niveles;
	}

	/**
	 * @param niveles
	 *            The niveles to set.
	 */
	public void setNiveles(String[] niveles) {
		this.niveles = niveles;
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

	// /**
	// * @return Returns the titulo.
	// */
	// public String getTitulo()
	// {
	// return titulo;
	// }
	// /**
	// * @param titulo The titulo to set.
	// */
	// public void setTitulo(String titulo)
	// {
	// this.titulo = titulo;
	// }
	/**
	 * @return Returns the serviceClient.
	 */
	public ServiceClient getServiceClient() {
		return serviceClient;
	}

	/**
	 * @param serviceClient
	 *            The serviceClient to set.
	 */
	public void setServiceClient(ServiceClient serviceClient) {
		this.serviceClient = serviceClient;
	}

}
