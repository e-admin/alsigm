package valoracion.vos;

import common.pagination.PageInfo;
import common.vos.BaseVO;

/**
 * Clase que encapsula la información del formulario de busqueda
 */
public class BusquedaVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String tituloSerie = null;
	private String codigo = null;
	private String fondo = null;
	private String titulo = null;
	private String[] estados = null;
	private String tituloValoracion = null;
	private String idGestor = null;
	private PageInfo pageInfo = null;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String[] getEstados() {
		return estados;
	}

	public void setEstados(String[] estados) {
		this.estados = estados;
	}

	public String getFondo() {
		return fondo;
	}

	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTituloSerie() {
		return tituloSerie;
	}

	public void setTituloSerie(String tituloSerie) {
		this.tituloSerie = tituloSerie;
	}

	public String getTituloValoracion() {
		return tituloValoracion;
	}

	public void setTituloValoracion(String tituloValoracion) {
		this.tituloValoracion = tituloValoracion;
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

	/**
	 * @return Returns the idGestor.
	 */
	public String getIdGestor() {
		return idGestor;
	}

	/**
	 * @param idGestor
	 *            The idGestor to set.
	 */
	public void setIdGestor(String idGestor) {
		this.idGestor = idGestor;
	}
}
