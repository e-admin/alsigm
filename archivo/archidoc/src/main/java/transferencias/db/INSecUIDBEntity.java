package transferencias.db;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASGTSNSECUI</b>
 * 
 * @author IECISA
 * 
 */
public interface INSecUIDBEntity extends IDBEntity {
	public long incrementarNumeroSec(final long incremento,
			final String idArchivoReceptor);
}