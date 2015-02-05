package es.ieci.tecdoc.isicres.api.documento.electronico.business.vo;

import es.ieci.tecdoc.isicres.api.business.vo.BaseIsicresApiVO;

public class IdentificadorDocumentoElectronicoAnexoVO extends BaseIsicresApiVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4331511572177691357L;
	
	/**
	 * identificador unico, clave primaria de la tabla.
	 * otra clave primararia candidata sera: idRegistro + idLibro + idPagina
	 */
	protected Long id;
	
	/**
	 * identificador del registro
	 */
	protected Long idRegistro;
	
	/**
	 * identificador del libro
	 */
	protected Long idLibro;
	
	/**
	 * identificador de la pagina
	 */
	protected Long idPagina;
	
	
	public Long getIdRegistro() {
		return idRegistro;
	}
	public void setIdRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
	}
	public Long getIdLibro() {
		return idLibro;
	}
	public void setIdLibro(Long idLibro) {
		this.idLibro = idLibro;
	}
	public Long getIdPagina() {
		return idPagina;
	}
	public void setIdPagina(Long idPagina) {
		this.idPagina = idPagina;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
