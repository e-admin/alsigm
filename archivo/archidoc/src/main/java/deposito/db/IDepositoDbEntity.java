package deposito.db;

import java.util.List;

import common.db.IDBEntity;

import deposito.vos.DepositoVO;
import deposito.vos.ResumenOcupacionVO;

/**
 * Métodos de acceso a datos referentes a ubicaciones del fondo físico Entidad:
 * <b>ASGDDEPOSITO</b>
 */
public interface IDepositoDbEntity extends IDBEntity {

	/**
	 * Recupera la lista de ubicaciones
	 * 
	 * @return Lista de ubicaciones {@link deposito.vos.DepositoVO}
	 * @throws Exception
	 */
	public List getDepositos();

	public List getDepositos(String nombre);

	public void insertDeposito(DepositoVO depositoVO);

	public DepositoVO loadDeposito(String id);

	public void updateDeposito(DepositoVO depositoVO);

	public void delete(String id);

	/**
	 * Recupera la ocupación de una ubicación
	 * 
	 * @param idUbicacion
	 *            Identificador de ubicación del depósito físico
	 * @return Resumen de ocupación de la ubicación con número de huecos libres,
	 *         ocupados, reservados e inutilizados.
	 */
	public ResumenOcupacionVO getOcupacion(String idUbicacion);

	/**
	 * Recupera la ocupación de una ubicación
	 * 
	 * @param idUbicacion
	 *            Identificador de ubicación del depósito físico
	 * @return Resumen de ocupación de la ubicación con número de huecos libres,
	 *         ocupados, reservados e inutilizados.
	 */
	public ResumenOcupacionVO getOcupacionByIdsUbicacion(List idsUbicaciones);

	/**
	 * Obtiene las ubicaciones que pertencen a un archivo/s
	 * 
	 * @param idsArchivo
	 *            Identificadores de los archivos
	 * @return Lista de Ubicaciones que pertenecen a un archivo.
	 */
	public List getUbicacionesXIdsArchivo(String[] idsArchivo);

	/**
	 * Obtiene las ubicaciones que pertenecen al archivo.
	 * 
	 * @param idArchivo
	 *            Identificador del archivo
	 * @return List {depositoVO}
	 */
	public List getUbicacionXIdArchivo(String idArchivo);

	/**
	 * Devuelve el tipo de signaturacion del archivo al que pertenece el
	 * deposito cuyo id coincide con el pasado como parametro.
	 * 
	 * @param idDeposito
	 * @return
	 */
	public int getTipoSignaturacionDeposito(String idDeposito);

	/**
	 * Comprueba que el numero de orden no exista ya en la base de datos
	 */
	public boolean isNumOrdenEnUso(int numOrden);
}