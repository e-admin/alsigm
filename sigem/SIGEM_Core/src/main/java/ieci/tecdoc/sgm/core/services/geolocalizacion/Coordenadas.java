package ieci.tecdoc.sgm.core.services.geolocalizacion;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de coordenadas
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Coordenadas {
	
	private List coordenadas;

	/**
	 * Constructor de clase
	 *
	 */
	public Coordenadas() {
		coordenadas = new ArrayList();
	}

	public List getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(List coordenadas) {
		this.coordenadas = coordenadas;
	}

	/**
	 * Devuelve el número de coordenadas de la colección.
	 * @return int Número de coordenadas de la colección.
	 */
	public int count() {
		return coordenadas.size();
	}

	/**
	 * Devuelve la via de la coordenada indicada dentro de la colección
	 * @param index Posición de la coordenada a recuperar.
	 * @return Mapa.
	 */
	public Object get(int index) {
		return (Coordenada) coordenadas.get(index);
	}

	/**
	 * Añade una nueva coordenada a la colección.
	 * @param coordenada Nueva coordenada a añadir.
	 */
	public void add(Coordenada coordenada) {
		coordenadas.add(coordenada);
	}

}
