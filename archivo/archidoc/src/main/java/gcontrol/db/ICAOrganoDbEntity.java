package gcontrol.db;

import gcontrol.vos.CAOrganoVO;

import java.util.List;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASCAORGANO</b>
 * 
 * @author IECISA
 * 
 */
public interface ICAOrganoDbEntity extends IDBEntity {
	/**
	 * Obtiene la lista de órganos productores.
	 * 
	 * @param vigente
	 *            Indica si los órganos deben ser vigentes o no vigentes. Si es
	 *            nulo, se devolverán todos los órganos productores.
	 * @return Lista de órganos productores.
	 */
	public List getCAOrgProductoresVOList(Boolean vigente);

	/**
	 * Obtiene la información de un órgano.
	 * 
	 * @param idOrgano
	 *            Identificador del órgano.
	 * @return Información de un órgano.
	 */
	public CAOrganoVO getCAOrgProductorVOXId(String idOrgano);

	/**
	 * Recupera de la base de datos la informacion correspondiente a un conjunto
	 * de organos
	 * 
	 * @param idOrgano
	 *            Lista de identificadores de organo. Puede ser null
	 * @return Lista de organos {@link CAOrganoVO}
	 */
	public List getCAOrgProductorVOXId(String[] idOrgano);

	/**
	 * Obtiene la información de un órgano.
	 * 
	 * @param sistExtGestor
	 *            Sistema Gestor de Organismos externo.
	 * @param idEnSistExt
	 *            Identificador del organismo en el Sistema Gestor de Organismos
	 *            externo.
	 * @return Información de un órgano.
	 */
	public abstract CAOrganoVO getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
			final String sistExtGestor, final String idEnSistExt);

	public abstract List getCAOrgProductorVOXSistExtGestorYVigencia(
			final String sistExtGestor, final boolean vigente);

	public CAOrganoVO getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestorYVigencia(
			final String sistExtGestor, final String idEnSistExt,
			final boolean vigente);

	/**
	 * Obtiene la lista de órganos a partir del Sistema Gestor Externo y una
	 * lista de identificadores en ese sistema.
	 * 
	 * @param sistExtGestor
	 *            Sistema Gestor de Organismos Externo.
	 * @param idsEnSistExt
	 *            Lista de identificadores en el Sistema Gestor de Organismos
	 *            Externo.
	 * @return Lista de órganos.
	 */
	public List getCAOrgProductorVOListXSistExtGestorYIdOrgsExtGestor(
			final String sistExtGestor, final Object idsEnSistExt);

	/**
	 * Inserta en la base de datos la informacion correspondiente a un nuevo
	 * organo
	 * 
	 * @param caOrganoVO
	 *            datos del organo a insertar
	 * @return Organo insertado
	 */
	public CAOrganoVO insertCAOrgVO(CAOrganoVO caOrganoVO);

	/**
	 * Actualiza la informacion de un organo
	 * 
	 * @param organo
	 *            Datos del organo a modificar
	 */
	public void updateCAOrgVO(CAOrganoVO organo);

	/**
	 * Eliminar un organo de la tabla de organos
	 * 
	 * @param idOrgano
	 *            Identificador del organo a eliminar
	 */
	public void eliminarOrgano(String idOrgano);

	/**
	 * Recupera los órganos por código y/o nombre.
	 * 
	 * @param code
	 *            Código del órgano.
	 * @param name
	 *            Nombre del órgano.
	 * @param vigente
	 *            Indicador de vigente
	 * @return Lista de órganos {@link CAOrganoVO}.
	 */
	public List findByCodeAndName(String code, String name, String vigente);

	/**
	 * Recupera los órganos cuyo nombre contiene la cadena suministrada
	 * 
	 * @param query
	 *            Patrón de búsqueda a localizar en el nombre del órgano
	 * @param externalSystem
	 *            Identificador del sistema externo del que se ha importado el
	 *            órgano. Puede ser nulo
	 * @return Lista de órganos cuyo nombre contiene el patrón indicado
	 *         {@link CAOrganoVO}
	 */
	public List findByName(String query, String externalSystem);

	/**
	 * Obtiene la información de un órgano por su nombre largo
	 * 
	 * @param nombreLargo
	 *            Cadena que contiene el nombre largo del órgano
	 * @return Información de un órgano.
	 */
	public CAOrganoVO getCAOrgProductorVOByNombreLargo(String nombreLargo);
}