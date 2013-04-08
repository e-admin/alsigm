package ieci.tecdoc.sgm.certificacion.bean;

/**
 * Clase que almacena los datos relativos a una entidad
 * @author José Antonio Nogales
 */
public class Entidad {
	String codigo;
	String descripcion;
	
	/**
	 * Constructor de la clase entidad
	 * @param codigo Código de la entidad 
	 * @param descripcion Descripción de la entidad
	 */
	public Entidad(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	
	/**
	 * Constructor de la clase sin parámetros
	 */
	public Entidad() {
		this.codigo = null;
		this.descripcion = null;
	}
	
	/**
	 * Métodos get y set de la clase 
	 */
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
