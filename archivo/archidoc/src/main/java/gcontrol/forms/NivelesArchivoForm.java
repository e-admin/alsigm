package gcontrol.forms;

import gcontrol.vos.NivelArchivoVO;

import common.forms.CustomForm;

public class NivelesArchivoForm extends CustomForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String nombre;
	private String descripcion;
	private Integer orden;

	/**
	 * @return el descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            el descripcion a establecer
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

	public void set(NivelArchivoVO nivelArchivoVO) {
		if (nivelArchivoVO != null) {
			this.id = nivelArchivoVO.getId();
			this.nombre = nivelArchivoVO.getNombre();
			this.descripcion = nivelArchivoVO.getDescripcion();
			this.orden = nivelArchivoVO.getOrden();
		}
	}

	public void populate(NivelArchivoVO nivelArchivoVO) {
		nivelArchivoVO.setId(this.id);
		nivelArchivoVO.setNombre(this.nombre);
		nivelArchivoVO.setDescripcion(this.descripcion);
		nivelArchivoVO.setOrden(this.orden);
	}

	public void reset() {
		this.id = null;
		this.nombre = null;
		this.descripcion = null;
		this.orden = null;
	}

}
