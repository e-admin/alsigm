package transferencias.db;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASGTSNSEC</b>
 * 
 * @author IECISA
 * 
 */

public interface INSecTransferenciasDBEntity extends IDBEntity {
	public int incrementarNumeroSecPrevision(String ano, String idarchivo);

	public int incrementarNumeroSecRelacion(String ano, String idarchivo);

	public int incrementarNumeroSecIngresoDirecto(String ano, String idarchivo);

	public int incrementarNumeroSecRegistro(String ano, String idarchivo);

	public int incrementarNumeroSecRegistro(String ano, int inc,
			String idarchivo);
}