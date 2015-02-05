package es.ieci.tecdoc.isicres.admin.business.dao;

import java.util.List;

import es.ieci.tecdoc.isicres.admin.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.admin.business.vo.UnidadRegistralVO;

public interface IntercambioRegistralDAO {

	/**
	 * Metodo que obtiene un listado de Entidades Registrales
	 * @return Listado de objetos {@link EntidadRegistralVO}
	 */
	public List getEntidadesRegistrales();

	/**
	 * Metodo que añade una Entidad Registral
	 *
	 * @param entidadRegistral
	 * @return {@link EntidadRegistralVO}
	 */
	public EntidadRegistralVO addEntidadRegistral(EntidadRegistralVO entidadRegistral);

	/**
	 * Metodo que añade una Unidad Registral
	 *
	 * @param unidadRegistral
	 * @return {@link UnidadRegistralVO}
	 */
	public UnidadRegistralVO addUnidadRegistral(UnidadRegistralVO unidadRegistral);

	/**
	 * Metood que borra una Entidad Registral pasada como parametro
	 * @param entidadRegistral
	 */
	public void deleteEntidadRegistral(EntidadRegistralVO entidadRegistral);

	/**
	 * Metodo que borra la Unidad Registral pasada como parametro
	 * @param unidadRegistral
	 */
	public void deleteUnidadRegistral(UnidadRegistralVO unidadRegistral);

	/**
	 * Metodo que obtiene una Entidad Registral a partir de su ID
	 * @param id - ID de la Entidad Registral a buscar
	 * @return {@link EntidadRegistralVO}
	 */
	public EntidadRegistralVO getEntidadRegistral(int id);

	/**
	 * Metodo que obtiene una Unidad Registral a partir de su ID
	 * @param id - ID de la Unidad Registral a buscar
	 * @return {@link UnidadRegistralVO}
	 */
	public UnidadRegistralVO getUnidadRegistral(int id);

	/**
	 * Metodo que obtiene una listado de {@link UnidadRegistralVO} a partir del
	 * ID de la Unidad Administrativa pasada como parametro
	 *
	 * @param idOrgs - ID de la Unidad Administrativa
	 * @return Listado de {@link UnidadRegistralVO}
	 */
	public List getUnidadRegistralByIdOrgs(int idOrgs);

	/**
	 * Metodo que actualiza una Entidad Registral
	 * @param entidadRegistral ha actualizar
	 */
	public void updateEntidadRegistral(EntidadRegistralVO entidadRegistral);

	/**
	 * Metodo que actualiza una Unidad Registral
	 * @param unidadRegistral ha actualizar
	 */
	public void updateUnidadRegistral(UnidadRegistralVO unidadRegistral);

	/**
	 * Metodo que busca las Entidades Registrales a partir de los datos que se pasan como parametro
	 * @param entidadEjemplo - Entidad Registral con los valores a buscar
	 * @return Listado de {@link EntidadRegistralVO}
	 */
	public List findEntidadesRegistrales(EntidadRegistralVO entidadEjemplo);

	/**
	 * Metodo que busca las Unidades Registrales a partir de los datos que se pasan como parametro
	 * @param unidadEjemplo - Unidad Registral con los valores a buscar
	 * @return Listado de {@link UnidadRegistralVO}
	 */
	public List findUnidadesRegistrales(UnidadRegistralVO unidadEjemplo);

	/**
	 * Metodo que busca las Unidades Registrales a partir del ID de la oficina
	 * @param idOfic - ID de la oficina a buscar
	 * @return Listado de {@link UnidadRegistralVO}
	 */
	public List findEntitadaRegistralByOficina(int idOfic);
}
