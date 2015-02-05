/**
 *
 */
package fondos.db;

import java.util.HashMap;

import common.db.IDBEntity;

import fondos.vos.TablaTemporalFondosVO;

/**
 * Gestion de Tablas Temporales
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface ITablaTemporalDBEntity extends IDBEntity{

	public void insertAsSelect(TablaTemporalFondosVO tablaTemporal, String[] ids);

	public void createTable(int numeroTabla) throws Exception;

	public void truncateTable(String nombreTabla) throws Exception;

	public HashMap getPairsIdCodigo(TablaTemporalFondosVO tablaTemporalFondosVO);

	public void deleteByUsuarioAndIdPadre(TablaTemporalFondosVO tablaTemporalFondosVO);
}
