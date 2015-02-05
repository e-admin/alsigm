package auditoria.db;

import java.util.Collection;

import auditoria.vos.BusquedaPistasVO;
import auditoria.vos.TrazaVO;

import common.db.IDBEntity;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;

/**
 * Entidad: <b>AATRAZA</b>
 * 
 * @author IECISA
 */
public interface ITrazaDBEntity extends IDBEntity {
	/**
	 * Inserta una nueva linea de traza de auditoria en la base de datos
	 * 
	 * @param traza
	 *            Linea de auditoría a insertar.
	 * @return {@link TrazaVO} insertada
	 */
	public abstract TrazaVO insertTraza(final TrazaVO traza);

	/**
	 * Obtiene un listado de las pistas de auditoría existentes que cumplen los
	 * requisitos de la búsqueda.
	 * 
	 * @param busqueda
	 *            Información del formulario de búsqueda.
	 * @param pageInfo
	 *            Informacion de paginacion de la tabla de resultados
	 * @return Lista de las pistas de auditoría que cumplen el filtro.
	 * @throws TooManyResultsException
	 */
	public abstract Collection getPistas(BusquedaPistasVO busqueda,
			PageInfo pageInfo) throws TooManyResultsException;

	/**
	 * Obtiene los datos de una pista de auditoria a partir de su identificador
	 * 
	 * @param id
	 *            Identificador de la pista de auditoria que deseamos recuperar
	 * @return Pista de auditoria asociada al id
	 */
	public abstract TrazaVO getPista(String id);

	/**
	 * Obtiene todas las pistas de auditoria para un determinado objeto
	 * 
	 * @param type
	 *            Tipo del objeto
	 * @param id
	 *            Identificador del objeto
	 * @return Listado de las pistas asociadas a ese objeto
	 */
	public abstract Collection getPistasXObject(int type, String id);
}