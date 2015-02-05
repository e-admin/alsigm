package fondos.vos;

import se.usuarios.GenericUserInfo;

import common.pagination.PageInfo;

public class BusquedaUdocsVO {

	private String serie = null;
	private String codigo = null;
	private String titulo = null;
	private GenericUserInfo serviceClient;
	private PageInfo pageInfo = null;
	private String[] estados = null;

	public GenericUserInfo getServiceClient() {
		return serviceClient;
	}

	public void setServiceClient(GenericUserInfo serviceClient) {
		this.serviceClient = serviceClient;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public void setEstados(String[] estados) {
		this.estados = estados;
	}

	public String[] getEstados() {
		return estados;
	}
}
