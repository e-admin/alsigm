/*
 * Creado el 24-may-2005
 *
 */
package ieci.tdw.ispac.ispaclib.invesicres.registro.vo;

/**
 * @author RAULHC
 *
 */
public class InteresadoVO {
	/** Identificador unico del interesado **/
	private String id = null;
	/** Nombre del Interesado **/
	private String nombre = null;
	/** Identificador del Tercero **/
	private String idTercero = null;
	/** Identificador de la direccion de notificacion del tercero **/
	private String idDireccion = null;
	/** Orden que ocupa el interesado en el registro. La primera posicion se indica con 1 **/
	private String orden = null;
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
	 * @return Retorna idDireccion.
	 */
	public String getIdDireccion() {
		return idDireccion;
	}
	/**
	 * @param idDireccion The idDireccion to set.
	 */
	public void setIdDireccion(String idDireccion) {
		this.idDireccion = idDireccion;
	}
	/**
	 * @return Retorna idTercero.
	 */
	public String getIdTercero() {
		return idTercero;
	}
	/**
	 * @param idTercero The idTercero to set.
	 */
	public void setIdTercero(String idTercero) {
		this.idTercero = idTercero;
	}
	/**
	 * @return Retorna nombre.
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre The nombre to set.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return Retorna orden.
	 */
	public String getOrden() {
		return orden;
	}
	/**
	 * @param orden The orden to set.
	 */
	public void setOrden(String orden) {
		this.orden = orden;
	}
}
