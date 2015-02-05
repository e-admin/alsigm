package ieci.tecdoc.sgm.core.services.catastro;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor de bienes inmuebles
 * 
 * @author Jose Antonio Nogales
 *
 * Fecha de Creación: 14-may-2007
 */
public class BienesInmuebles {
	
	private List bienesInmuebles;

	/**
	 * Constructor de clase
	 *
	 */
	public BienesInmuebles() {
		bienesInmuebles = new ArrayList();
	}

	public List getBienesInmuebles() {
		return bienesInmuebles;
	}

	public void setBienesInmuebles(List bienesInmuebles) {
		this.bienesInmuebles = bienesInmuebles;
	}

	/**
	 * Devuelve el número de bienes inmuebles de la colección.
	 * @return int Número de bienes inmuebles de la colección.
	 */
	public int count() {
		return bienesInmuebles.size();
	}

	/**
	 * Devuelve el bien inmueble de la posición indicada dentro de la colección
	 * @param index Posición del bien inmueble a recuperar.
	 * @return BienInmueble
	 */
	public Object get(int index) {
		return (BienInmueble) bienesInmuebles.get(index);
	}

	/**
	 * Añade un nuev bien inmueble a la colección.
	 * @param bienInmueble Nuevo bien inmueble a añadir.
	 */
	public void add(BienInmueble bienInmueble) {
		bienesInmuebles.add(bienInmueble);
	}

}
