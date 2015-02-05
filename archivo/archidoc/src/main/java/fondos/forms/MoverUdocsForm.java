package fondos.forms;

import common.forms.CustomForm;
import common.pagination.PageInfo;

import fondos.vos.BusquedaElementosVO;
import fondos.vos.BusquedaUdocsVO;

public class MoverUdocsForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String codigo;
	String titulo;

	String fondoSerie;
	String codigoSerie;
	String tituloSerie;

	PageInfo pageInfo;
	String idSerieDestino;
	String[] udocsSeleccionadas;

	String idLCA;
	Boolean asignarNuevaLCA;

	public void resetInInit() {
		codigo = null;
		titulo = null;

		fondoSerie = null;
		codigoSerie = null;
		tituloSerie = null;

		pageInfo = null;
		idSerieDestino = null;
		udocsSeleccionadas = null;
		idLCA = null;
	}

	public String getIdSerieDestino() {
		return idSerieDestino;
	}

	public void setIdSerieDestino(String idSerieDestino) {
		this.idSerieDestino = idSerieDestino;
	}

	public String[] getUdocsSeleccionadas() {
		return udocsSeleccionadas;
	}

	public void setUdocsSeleccionadas(String[] udocsSeleccionadas) {
		this.udocsSeleccionadas = udocsSeleccionadas;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public BusquedaUdocsVO getBusquedaUdocsVO() {
		BusquedaUdocsVO vo = new BusquedaUdocsVO();
		vo.setCodigo(this.codigo);
		vo.setTitulo(this.titulo);

		return vo;
	}

	public BusquedaElementosVO getBusquedaElementosVOparaSerieDestino() {
		BusquedaElementosVO vo = new BusquedaElementosVO();
		vo.setCodigo(this.codigoSerie);
		vo.setTitulo(this.tituloSerie);
		vo.setFondo(this.fondoSerie);
		return vo;
	}

	public String getCodigoSerie() {
		return codigoSerie;
	}

	public void setCodigoSerie(String codigoSerie) {
		this.codigoSerie = codigoSerie;
	}

	public String getFondoSerie() {
		return fondoSerie;
	}

	public void setFondoSerie(String fondoSerie) {
		this.fondoSerie = fondoSerie;
	}

	public String getTituloSerie() {
		return tituloSerie;
	}

	public void setTituloSerie(String tituloSerie) {
		this.tituloSerie = tituloSerie;
	}

	public String getIdLCA() {
		return idLCA;
	}

	public void setIdLCA(String idLCA) {
		this.idLCA = idLCA;
	}

	public Boolean getAsignarNuevaLCA() {
		return asignarNuevaLCA;
	}

	public void setAsignarNuevaLCA(Boolean asignarNuevaLCA) {
		this.asignarNuevaLCA = asignarNuevaLCA;
	}

}
