package deposito.db;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASGDSIGNUMHUECO</b>
 * 
 * @author IECISA
 * 
 */
public interface INsecSigNumHuecoDBEntity extends IDBEntity {

	public abstract int incrementarNumeroSec(final int incremento,
			final String idArchivo);

	public abstract int getNumeroSec(final int incremento,
			final String idArchivo);

	// public abstract void setNumeroSec(final int valor, final String
	// idArchivo);
	void setNumeroSec(final long valor, final String idArchivo);

	void bloquearNumeroSec(final String idArchivo);

	long getNumeroSecLong(final int incremento, final String idArchivo);

}