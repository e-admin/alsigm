package ieci.tecdoc.sgm.core.services.geolocalizacion;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de municipios
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Municipios {
	
	private List municipios;

	/**
	 * Constructor de clase
	 *
	 */
	public Municipios() {
		municipios = new ArrayList();
	}

	public List getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List municipios) {
		this.municipios = municipios;
	}

	/**
	 * Devuelve el número de municipios de la colección.
	 * @return int Número de municipios de la colección.
	 */
	public int count() {
		return municipios.size();
	}

	/**
	 * Devuelve el municipio de la posición indicada dentro de la colección
	 * @param index Posición del municipio a recuperar.
	 * @return Mapa.
	 */
	public Object get(int index) {
		return (Municipio) municipios.get(index);
	}

	/**
	 * Añade un nuevo municipio a la colección.
	 * @param mapa Nuevo municipio a añadir.
	 */
	public void add(Municipio municipio) {
		municipios.add(municipio);
	}

}
