package ieci.tecdoc.sgm.ct.database.datatypes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contenedor de Hitos de uno o mas expedientes.
 * 
 * @author IECISA
 *
 */
public class HitosExpediente extends ArrayList implements Serializable {
	
	/**
	 * Constructor de clase
	 */

	public HitosExpediente() {
		
	}

	/**
	 * Devuelve el número de hitos de la colección.
	 * @return int Número de hitos de la colección.
	 */
	public int count() {
		return super.size();
	}

	/**
	 * Devuelve el hito de la posición indicada dentro de la colección
	 * @param index Posición del hito a recuperar.
	 * @return Hito asociado a registro.
	 */
	public Object get(int index) {
		return (HitoExpediente)super.get(index);
	}

	/**
	 * Añade un nuevo hito a la colección.
	 * @param hito Nuevo expediente a añadir.
	 */
	public void add(HitoExpediente hito) {
		super.add(hito);
	}

}
