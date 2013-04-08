package salas.vos;

import common.vos.BaseVO;

/**
 * VO para la navegación entre elementos de la sala
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class ElementoNavegacionVO extends BaseVO {

	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del elemento
	 */
	private String id = null;

	/**
	 * Nombre del elemento
	 */
	private String nombre = null;

	/**
	 * Número de mesas libre que posee el elemento
	 */
	private int numHijos = 0;

	/**
	 * Estado en el que se encuentran las mesas
	 */
	private String estado = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumHijos() {
		return numHijos;
	}

	public void setNumHijos(int numHijos) {
		this.numHijos = numHijos;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}