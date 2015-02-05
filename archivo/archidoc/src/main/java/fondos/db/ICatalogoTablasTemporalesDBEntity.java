/**
 *
 */
package fondos.db;

import common.db.IDBEntity;

import fondos.vos.CatalogoTablaTemporalVO;

/**
 * Entidad: <b>ASGFCTLGTBLTMP</b>
 * 
 * @author IECISA
 * 
 */
public interface ICatalogoTablasTemporalesDBEntity extends IDBEntity {

	/**
	 * Obtiene las tablas
	 * 
	 * @param estado
	 * @param fecha
	 * @return
	 */
	public CatalogoTablaTemporalVO getByEstadoAndUsuario(Integer estado,
			String idUsuario);

	public void insertar(CatalogoTablaTemporalVO catalogoTablaTemporalVO);

	public int actualizarEstado(CatalogoTablaTemporalVO catalogoTablaTemporalVO);

	public int reset(int numero);

	public CatalogoTablaTemporalVO getTablaTemporalFromCaducadas();

	public void bloqueaTabla() throws Exception;
}
