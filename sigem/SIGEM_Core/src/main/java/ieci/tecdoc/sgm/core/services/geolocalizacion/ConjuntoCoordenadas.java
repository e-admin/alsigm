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
public class ConjuntoCoordenadas {
	
	private List conjuntoCoordenadas;

	/**
	 * Constructor de clase
	 *
	 */
	public ConjuntoCoordenadas() {
		conjuntoCoordenadas = new ArrayList();
	}

	public List getConjuntoCoordenadas() {
		return conjuntoCoordenadas;
	}

	public void setConjuntoCoordenadas(List conjuntoCoordenadas) {
		this.conjuntoCoordenadas = conjuntoCoordenadas;
	}

	/**
	 * Devuelve el número de conjunto coordenadas de la colección.
	 * @return int Número de conjunto Coordenadas  de la colección.
	 */
	public int count() {
		return conjuntoCoordenadas.size();
	}

	/**
	 * Devuelve el conjunto Coordenadas indicada dentro de la colección
	 * @param index Posición del conjunto Coordenadas a recuperar.
	 * @return Coorodenadas.
	 */
	public Object get(int index) {
		return (Coordenadas) conjuntoCoordenadas.get(index);
	}

	/**
	 * Añade un nuevo conjunto Coordenadas a la colección.
	 * @param coordenadas Nuevas coordenadas a añadir.
	 */
	public void add(Coordenadas coordenadas) {
		conjuntoCoordenadas.add(coordenadas);
	}

}
