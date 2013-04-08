package ieci.tecdoc.sgm.core.services.geolocalizacion;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de provincias
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class Provincias {
	
	private List provincias;

	/**
	 * Constructor de clase
	 *
	 */
	public Provincias() {
		provincias = new ArrayList();
	}

	public List getProvincias() {
		return provincias;
	}

	public void setProvincias(List provincias) {
		this.provincias = provincias;
	}

	/**
	 * Devuelve el número de provincias de la colección.
	 * @return int Número de provincias de la colección.
	 */
	public int count() {
		return provincias.size();
	}

	/**
	 * Devuelve el provincia de la posición indicada dentro de la colección
	 * @param index Posición del provincia a recuperar.
	 * @return Mapa.
	 */
	public Object get(int index) {
		return (Provincia) provincias.get(index);
	}

	/**
	 * Añade un nuevo provincia a la colección.
	 * @param mapa Nuevo provincia a añadir.
	 */
	public void add(Provincia provincia) {
		provincias.add(provincia);
	}

}
