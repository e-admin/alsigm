package ieci.tecdoc.sgm.core.services.rpadmin;

/**
 * Oficina asociada con tipo de asunto
 * 
 * 
 */
public class OficinaTipoAsuntoBean {
	private int id;
	private int idTipoAsunto;
	private int idOficina;
	private String codigoOficina;
	private String nombreOficina;

	// Auxiliar
	private int estado = Estados.SIN_CAMBIOS;

	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public int getIdTipoAsunto() {
		return idTipoAsunto;
	}

	/**
	 * @param idTipoAsunto
	 */
	public void setIdTipoAsunto(int idTipoAsunto) {
		this.idTipoAsunto = idTipoAsunto;
	}

	/**
	 * @return
	 */
	public int getIdOficina() {
		return idOficina;
	}

	/**
	 * @param idOficina
	 */
	public void setIdOficina(int idOficina) {
		this.idOficina = idOficina;
	}

	/**
	 * @return
	 */
	public String getNombreOficina() {
		return nombreOficina;
	}

	/**
	 * @param nombreOficina
	 */
	public void setNombreOficina(String nombreOficina) {
		this.nombreOficina = nombreOficina;
	}

	/**
	 * @return
	 */
	public String getCodigoOficina() {
		return codigoOficina;
	}

	/**
	 * @param codigoOficina
	 */
	public void setCodigoOficina(String codigoOficina) {
		this.codigoOficina = codigoOficina;
	}

	/**
	 * @return
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}

}
