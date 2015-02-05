/*
 * Creado el 24-may-2005
 *
 */
package ieci.tdw.ispac.ispaclib.invesicres.registro.vo;

import java.util.LinkedList;

/**
 * @author RAULHC
 *
 */
public class TerceroVO {
	/** Identificador unico del Tercero **/
	private String id = null;
	/** Tipo de Persona (F -> Fisica, J -> Juridica) **/
	private String tipo = null;
	/** Numero de documento de identificacion **/
	private String documento = null;
	/** Nombre o Razon Social **/
	private String nombre = null;
	/** Tipo Documento - Solo para personas juridicas **/
	private String tipoDocumento = null;
	/** Direcciones del tercero **/
	private LinkedList direcciones = null;
	/**
	 * @return Retorna direcciones.
	 */
	public LinkedList getDirecciones() {
		return direcciones;
	}
	/**
	 * @param direcciones The direcciones to set.
	 */
	public void setDirecciones(LinkedList direcciones) {
		this.direcciones = direcciones;
	}
	/**
	 * @return Retorna documento.
	 */
	public String getDocumento() {
		return documento;
	}
	/**
	 * @param documento The documento to set.
	 */
	public void setDocumento(String documento) {
		this.documento = documento;
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
	 * @return Retorna tipoDocumento.
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param tipoDocumento The tipoDocumento to set.
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
}
