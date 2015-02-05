package ieci.tecdoc.sgm.core.services.catastro;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de construcciones
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Construcciones {
	
	private List construcciones;

	/**
	 * Constructor de clase
	 *
	 */
	public Construcciones() {
		construcciones = new ArrayList();
	}

	public List getConstrucciones() {
		return construcciones;
	}

	public void setConstrucciones(List construcciones) {
		this.construcciones = construcciones;
	}

	/**
	 * Devuelve el número de construcciones de la colección.
	 * @return int Número de construcciones de la colección.
	 */
	public int count() {
		return construcciones.size();
	}

	/**
	 * Devuelve la construccion de la posición indicada dentro de la colección
	 * @param index Posición de la construccion a recuperar.
	 * @return Construccion.
	 */
	public Object get(int index) {
		return (Construccion) construcciones.get(index);
	}

	/**
	 * Añade una nueva construccion a la colección.
	 * @param construcciones Nueva construccion a añadir.
	 */
	public void add(Construccion construccion) {
		construcciones.add(construccion);
	}

}
