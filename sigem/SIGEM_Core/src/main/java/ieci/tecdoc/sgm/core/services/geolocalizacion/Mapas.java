package ieci.tecdoc.sgm.core.services.geolocalizacion;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de mapas
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Mapas {
	
	private List mapas;

	/**
	 * Constructor de clase
	 *
	 */
	public Mapas() {
		mapas = new ArrayList();
	}

	public List getMapas() {
		return mapas;
	}

	public void setMapas(List mapas) {
		this.mapas = mapas;
	}

	/**
	 * Devuelve el número de mapas de la colección.
	 * @return int Número de mapas de la colección.
	 */
	public int count() {
		return mapas.size();
	}

	/**
	 * Devuelve el mapa de la posición indicada dentro de la colección
	 * @param index Posición del mapas a recuperar.
	 * @return Mapa.
	 */
	public Object get(int index) {
		return (Mapa) mapas.get(index);
	}

	/**
	 * Añade un nuevo mapa a la colección.
	 * @param mapa Nuevo mapa a añadir.
	 */
	public void add(Mapa mapa) {
		mapas.add(mapa);
	}

}
