package ieci.tecdoc.sgm.ct.database.datatypes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contenedor de interesados asociados a un expediente.
 * 
 * @author Javier Septién Arceredillo
 *
 * Fecha de Creación: 14-may-2007
 */
public class Interesados implements Serializable {
	
	private ArrayList interesados;
	
	/**
	 * Constructor de clase
	 */
	public Interesados() {
		interesados = new ArrayList();
	}

	/**
	 * Devuelve el número de interesados de la colección.
	 * @return int Número de interesados de la colección.
	 */
	public int count() {
		return interesados.size();
	}

	/**
	 * Devuelve el interesado de la posición indicada dentro de la colección
	 * @param index Posición del interesado a recuperar.
	 * @return Interesado asociado a registro.
	 */
	public Expediente get(int index) {
		return (Expediente) interesados.get(index);
	}

	/**
	 * Añade un nuevo interesado a la colección.
	 * @param interesado Nuevo interesado a añadir.
	 */
	public void add(Interesado interesado) {
		interesados.add(interesado);
	}

}
