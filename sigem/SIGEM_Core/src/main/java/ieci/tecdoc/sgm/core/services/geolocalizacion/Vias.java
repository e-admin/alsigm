package ieci.tecdoc.sgm.core.services.geolocalizacion;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de vias
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Vias {
	
	private List vias;

	/**
	 * Constructor de clase
	 *
	 */
	public Vias() {
		vias = new ArrayList();
	}

	public List getVias() {
		return vias;
	}

	public void setVias(List vias) {
		this.vias = vias;
	}

	/**
	 * Devuelve el número de vias de la colección.
	 * @return int Número de vias de la colección.
	 */
	public int count() {
		return vias.size();
	}

	/**
	 * Devuelve la via de la posición indicada dentro de la colección
	 * @param index Posición de la via a recuperar.
	 * @return Mapa.
	 */
	public Object get(int index) {
		return (Via) vias.get(index);
	}

	/**
	 * Añade una nueva via a la colección.
	 * @param via Nueva via a añadir.
	 */
	public void add(Via via) {
		vias.add(via);
	}

}
