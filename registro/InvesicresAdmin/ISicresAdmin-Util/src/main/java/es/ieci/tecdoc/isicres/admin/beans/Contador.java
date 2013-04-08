package es.ieci.tecdoc.isicres.admin.beans;

/**
 * Contador de registros.
 * 
 */
public class Contador {

	public int id;
	public String nombre;
	public int contador;
	public int anyo;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the contador
	 */
	public int getContador() {
		return contador;
	}

	/**
	 * @param contador
	 *            the contador to set
	 */
	public void setContador(int contador) {
		this.contador = contador;
	}

	/**
	 * @return the anyo
	 */
	public int getAnyo() {
		return anyo;
	}

	/**
	 * @param anyo
	 *            the anyo to set
	 */
	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}

}
