package valoracion.db;

import common.db.IDBEntity;

/**
 * Interface para la generacion de numeros de secuencia de las valoraciones
 */
public interface INSecValoracionDBEntity extends IDBEntity {
	/**
	 * Incrementa el numero de secuencia para la tabla de registros de
	 * valoracion.
	 * 
	 * @return Nuevo numero de la secuencia.
	 */
	public abstract int incrementarNumeroSec();
}