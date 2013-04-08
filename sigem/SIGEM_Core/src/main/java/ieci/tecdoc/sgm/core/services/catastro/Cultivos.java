package ieci.tecdoc.sgm.core.services.catastro;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de cultivos
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Cultivos {
	
	private List cultivos;

	/**
	 * Constructor de clase
	 *
	 */
	public Cultivos() {
		cultivos = new ArrayList();
	}

	public List getCultivos() {
		return cultivos;
	}

	public void setCultivos(List cultivos) {
		this.cultivos = cultivos;
	}

	/**
	 * Devuelve el número de cultivos de la colección.
	 * @return int Número de cultivos de la colección.
	 */
	public int count() {
		return cultivos.size();
	}

	/**
	 * Devuelve el cultivo de la posición indicada dentro de la colección
	 * @param index Posición del cultivo a recuperar.
	 * @return Cultivo.
	 */
	public Object get(int index) {
		return (Cultivo) cultivos.get(index);
	}

	/**
	 * Añade un nuevo cultivo a la colección.
	 * @param cultivos Nuevo cultivo a añadir.
	 */
	public void add(Cultivo cultivo) {
		cultivos.add(cultivo);
	}

}
