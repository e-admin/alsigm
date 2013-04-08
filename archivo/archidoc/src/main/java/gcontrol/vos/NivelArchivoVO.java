package gcontrol.vos;

import common.vos.BaseVO;

/**
 * VO que representa el Nivel de Archivo (TABLA: AGNIVELARCHIVO)
 * 
 * @author Lucas Alvarez
 * 
 */
public class NivelArchivoVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador único del nivel de Archivo
	 */
	String id;

	/**
	 * Nombre del Nivel de Archivo.
	 */
	String nombre;

	/**
	 * Descripción del Nivel de Archivo
	 */
	String descripcion;

	/**
	 * Número de Orden que tiene
	 */
	Integer orden;

	boolean nuevoElemento = false;

	boolean archivoAsociado;

	/**
	 * @return el descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            La descripcion a establecer
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return el id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            el id a establecer
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            el nombre a establecer
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return el orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @param orden
	 *            el orden a establecer
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	/**
	 * @return el nuevoElemento
	 */
	public boolean isNuevoElemento() {
		return nuevoElemento;
	}

	/**
	 * @param nuevoElemento
	 *            el nuevoElemento a establecer
	 */
	public void setNuevoElemento(boolean nuevoElemento) {
		this.nuevoElemento = nuevoElemento;
	}

	public boolean isArchivoAsociado() {
		return archivoAsociado;
	}

	public void setArchivoAsociado(boolean archivoAsociado) {
		this.archivoAsociado = archivoAsociado;
	}

}
