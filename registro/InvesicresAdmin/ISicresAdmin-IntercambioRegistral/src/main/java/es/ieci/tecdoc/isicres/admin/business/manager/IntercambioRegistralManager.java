package es.ieci.tecdoc.isicres.admin.business.manager;

import java.util.List;

import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioUnidadOrganicaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.isicres.admin.business.exception.ISicresAdminIntercambioRegistralException;
import es.ieci.tecdoc.isicres.admin.business.vo.DatosBasicosOficinaDCVO;
import es.ieci.tecdoc.isicres.admin.business.vo.DatosBasicosUnidadOrganicaDCVO;
import es.ieci.tecdoc.isicres.admin.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.admin.business.vo.UnidadRegistralVO;

/**
 * Manager para las operaciones de intercambio registral de administración. (Mapeo Directorio Común - DIR3)
 * @author iecisa
 *
 */
public interface IntercambioRegistralManager {

	/**
	 * Metodo que obtiene las Entidades Registrales
	 *
	 * @return Listado de Objetos {@link EntidadRegistralVO}
	 *
	 * @throws ISicresAdminIntercambioRegistralException
	 */
	public List getEntidadesRegistrales() throws ISicresAdminIntercambioRegistralException;

	/**
	 * Metodo que crea una Entidad Registral
	 * @param entidadRegistral
	 * @return {@link EntidadRegistralVO}
	 * @throws ISicresAdminIntercambioRegistralException
	 */
	public EntidadRegistralVO addEntidadRegistral(EntidadRegistralVO entidadRegistral) throws ISicresAdminIntercambioRegistralException;

	/**
	 * Metodo que crea una Unidad Registral
	 * @param unidadRegistral
	 * @return {@link UnidadRegistralVO}
	 * @throws ISicresAdminIntercambioRegistralException
	 */
	public UnidadRegistralVO addUnidadRegistral(UnidadRegistralVO unidadRegistral) throws ISicresAdminIntercambioRegistralException;

	/**
	 * Metodo que borra una Entidad Registral
	 * @param entidadRegistral
	 * @throws ISicresAdminIntercambioRegistralException
	 */
	public void deleteEntidadRegistral(EntidadRegistralVO entidadRegistral) throws ISicresAdminIntercambioRegistralException;

	/**
	 * Metodo que borra una Unidad Registral
	 * @param unidadRegistral
	 * @throws ISicresAdminIntercambioRegistralException
	 */
	public void deleteUnidadRegistral(UnidadRegistralVO unidadRegistral) throws ISicresAdminIntercambioRegistralException;

	/**
	 * Metodo que obtiene una Entidad Registral mediante su ID
	 * @param idOfic - ID de la Entidad Registral a buscar
	 * @return {@link EntidadRegistralVO}
	 * @throws ISicresAdminIntercambioRegistralException
	 */
	public EntidadRegistralVO getEntidadRegistral(int id) throws ISicresAdminIntercambioRegistralException;

	/**
	 * Metodo que obtiene una Entidad Registral mediante el ID de la oficina
	 * @param idOfic - ID de la oficina
	 * @return {@link EntidadRegistralVO}
	 * @throws ISicresAdminIntercambioRegistralException
	 */
	public EntidadRegistralVO getEntidadRegistralByIdOficina (int idOfic) throws ISicresAdminIntercambioRegistralException;

	/**
	 * Metodo que obtiene una Unidad Registral mediante su id
	 * @param id - ID de la Unidad Registral a buscar
	 * @return {@link UnidadRegistralVO}
	 * @throws ISicresAdminIntercambioRegistralException
	 */
	public UnidadRegistralVO getUnidadRegistral(int id) throws ISicresAdminIntercambioRegistralException;

	/**
	 * Metodo que actualiza una Entidad Registral
	 * @param entidadRegistral
	 * @return {@link EntidadRegistralVO}
	 * @throws ISicresAdminIntercambioRegistralException
	 */
	public EntidadRegistralVO updateEntidadRegistral(EntidadRegistralVO entidadRegistral) throws ISicresAdminIntercambioRegistralException;

	/**
	 * Metodo que actualiza una Unidad Registral
	 * @param unidadRegistral
	 * @return {@link UnidadRegistralVO}
	 * @throws ISicresAdminIntercambioRegistralException
	 */
	public UnidadRegistralVO updateUnidadRegistral(UnidadRegistralVO unidadRegistral) throws ISicresAdminIntercambioRegistralException;

	/**
	 * Metodo que obtiene las unidades registrales mediante el id de la unidad administrativa
	 * @param idOrgs
	 * @return {@link UnidadRegistralVO}
	 * @throws ISicresAdminIntercambioRegistralException
	 */
	public UnidadRegistralVO getUnidadRegistralByIdOrgs(int idOrgs) throws ISicresAdminIntercambioRegistralException;

	/**
	 * Busca oficinas en la réplica del directorio común (DIR3) según los <code>criteriosBusqueda</code>
	 * @param criteriosBusqueda
	 * @return Listado de objetos {@link DatosBasicosOficinaDCVO}
	 */
	public List<DatosBasicosOficinaDCVO> findOficinasDirectorioComun(Criterios<CriterioOficinaEnum> criteriosBusqueda);

	/**
	 * Busca unidades orgánicas en la réplica del directorio común (DIR3) según los <code>criteriosBusqueda</code>
	 * @param criteriosBusqueda
	 * @return Listado de objetos {@link DatosBasicosUnidadOrganicaDCVO}
	 */
	public List<DatosBasicosUnidadOrganicaDCVO> findUnidadesOrganicasDirectorioComun(Criterios<CriterioUnidadOrganicaEnum> criteriosBusqueda);


	public List<DatosBasicosUnidadOrganicaDCVO> findUnidadesOrganicasDirectorioComunByCodEntidad(
			String codEntidad, String codUnidad, String nombreUnidad);

	public List<UnidadRegistralVO> findUnidadesRegistrales(UnidadRegistralVO unidad);
}
