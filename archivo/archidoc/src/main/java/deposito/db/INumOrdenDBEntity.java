package deposito.db;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASGDSIGNUMORDEN</b>
 * 
 * @author IECISA
 * 
 */
public interface INumOrdenDBEntity extends IDBEntity {
	public int incrementarNumeroOrden(final String idPadre,
			final String tipoPadre, final String tipoElemento,
			final int cantidad);

	public int getNumeroOrden(final String idPadre, final String tipoPadre,
			final String tipoElemento);
}