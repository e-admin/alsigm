package fondos.db;

import java.util.Collection;

import common.exceptions.TooManyResultsException;

import fondos.vos.BusquedaSolicitudesSerieVO;

public interface ISolicitudSerieBusquedasDbEntity {
	/*
	 * (non-Javadoc)
	 * 
	 * @see fondos.db.ISolicitudSerieDbEntity#findSolicitudes(fondos.vos.
	 * BusquedaSolicitudesSerieVO)
	 */
	public abstract Collection findSolicitudes(
			BusquedaSolicitudesSerieVO busquedaSolicitudes)
			throws TooManyResultsException;
}