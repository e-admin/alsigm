package ieci.tecdoc.sgm.core.services.geolocalizacion;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de tipos de vias
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class TiposVia {
	
	private List tipoVias;

	/**
	 * Constructor de clase
	 *
	 */
	public TiposVia() {
		tipoVias = new ArrayList();
	}

	public List getTipoVias() {
		return tipoVias;
	}

	public void setTipoVias(List tipoVias) {
		this.tipoVias = tipoVias;
	}

	/**
	 * Devuelve el número de tipos de vias de la colección.
	 * @return int Número de tipos de vias de la colección.
	 */
	public int count() {
		return tipoVias.size();
	}

	/**
	 * Devuelve el tipo de via de la posición indicada dentro de la colección
	 * @param index Posición del tipo de via a recuperar.
	 * @return Mapa.
	 */
	public Object get(int index) {
		return (TipoVia) tipoVias.get(index);
	}

	/**
	 * Añade un nuevo tipo de via a la colección.
	 * @param mapa Nuevo tipo de via a añadir.
	 */
	public void add(TipoVia tipoVia) {
		tipoVias.add(tipoVia);
	}

}
