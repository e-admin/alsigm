package ieci.tecdoc.sgm.core.services.geolocalizacion;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de portales
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Portales {
	
	private List portales;

	/**
	 * Constructor de clase
	 *
	 */
	public Portales() {
		portales = new ArrayList();
	}

	public List getPortales() {
		return portales;
	}

	public void setPortales(List portales) {
		this.portales = portales;
	}

	/**
	 * Devuelve el número de portales de la colección.
	 * @return int Número de portales de la colección.
	 */
	public int count() {
		return portales.size();
	}

	/**
	 * Devuelve el portal de la posición indicada dentro de la colección
	 * @param index Posición del portal a recuperar.
	 * @return Mapa.
	 */
	public Object get(int index) {
		return (Portal) portales.get(index);
	}

	/**
	 * Añade una nuevo portal a la colección.
	 * @param portal Nuevo portal a añadir.
	 */
	public void add(Portal portal) {
		portales.add(portal);
	}

}
