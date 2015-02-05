package transferencias.db;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASGTSNSECUDOC</b>
 * 
 * @author IECISA
 * 
 */
public interface INSecUDocDBEntity extends IDBEntity {

	/**
	 * Permite obtener el siguiente número secuencial de unidad documental
	 * 
	 * @param incremento
	 * @return
	 */
	public int incrementarNumeroSec(final int incremento);
}