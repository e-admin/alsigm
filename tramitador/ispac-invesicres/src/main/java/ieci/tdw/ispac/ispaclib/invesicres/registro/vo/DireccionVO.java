/*
 * Creado el 24-may-2005
 *
 */
package ieci.tdw.ispac.ispaclib.invesicres.registro.vo;

/**
 * @author RAULHC
 *
 */
public class DireccionVO {
	/** Identificador unico de la direccion **/
	private String id = null;
	/** Direccion (Si es postal sera el domicilio y si es telematica contendra el valor segun el tipoTelematica)**/
	private String direccion = null;
	/** Ciudad - Solo para direccion Postal**/
	private String ciudad = null;
	/** Codigo Postal - Solo para direccion Postal **/
	private String codigoPostal = null;
	/** Pais - Solo para direccion Postal **/
	private String pais = null;
	/** Tipo de Direccion (0 - Postal, 1 - Telematica) **/
	private String tipo = null;
	/** Descripcion del Tipo de Direccion **/
	private String tipoDescripcion = null;
	/** Indica si es la direccion principal **/
	private boolean principal = false;
	/** Tipo de direccion telematica **/
	private String tipoTelematica = null;
	/**
	 * @return Retorna ciudad.
	 */
	public String getCiudad() {
		return ciudad;
	}
	/**
	 * @param ciudad The ciudad to set.
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	/**
	 * @return Retorna codigoPostal.
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}
	/**
	 * @param codigoPostal The codigoPostal to set.
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	/**
	 * @return Retorna direccion.
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion The direccion to set.
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	/**
	 * @return Retorna id.
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return Retorna pais.
	 */
	public String getPais() {
		return pais;
	}
	/**
	 * @param pais The pais to set.
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}
	/**
	 * @return Retorna principal.
	 */
	public boolean isPrincipal() {
		return principal;
	}
	/**
	 * @param principal The principal to set.
	 */
	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}
	/**
	 * @return Retorna tipo.
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo The tipo to set.
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return Retorna tipoTelematica.
	 */
	public String getTipoTelematica() {
		return tipoTelematica;
	}
	/**
	 * @param tipoTelematica The tipoTelematica to set.
	 */
	public void setTipoTelematica(String tipoTelematica) {
		this.tipoTelematica = tipoTelematica;
	}
	/**
	 * @return Retorna tipoDescripcion.
	 */
	public String getTipoDescripcion() {
		return tipoDescripcion;
	}
	/**
	 * @param tipoDescripcion The tipoDescripcion to set.
	 */
	public void setTipoDescripcion(String tipoDescripcion) {
		this.tipoDescripcion = tipoDescripcion;
	}
}
