package ieci.tecdoc.sgm.ct.database.datatypes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contenedor de expedientes asociados a un registro.
 * 
 * @author IECISA
 *
 */
public class Expedientes extends ArrayList implements Serializable {
	
	 /**
     * Constructor de clase
     */

	public Expedientes() {
		//expedientes = new ArrayList();
	}

	/**
	 * Devuelve el número de expedientes de la colección.
	 * @return int Número de expedientes de la colección.
	 */
	public int count() {
		//return expedientes.size();
		return size();
	}

	/**
	 * Devuelve el expediente de la posición indicada dentro de la colección
	 * @param index Posición del expediente a recuperar.
	 * @return Expediente asociado a registro.
	 */
	public Object get(int index) {
		//return (Expediente) expedientes.get(index);
		return (ExpedienteImpl)super.get(index);
	}

	/**
	 * Añade un nuevo expediente a la colección.
	 * @param expediente Nuevo expediente a añadir.
	 */
	public void add(Expediente expediente) {
		//expedientes.add(expediente);
		super.add(expediente);
	}

}
