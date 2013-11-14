package es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.server.pagination.PaginatedArrayList;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaEntradaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIREntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIRSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralEntradaEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralEntradaVO;

/**
 * DAO para leer y actualizar datos de la bandeja de entrada de intercambio registral
 *
 */
public interface BandejaEntradaIntercambioRegistralDAO {

	/**
	 * Guarda un intercambio registral que acabamos de aceptar
	 * @param intecambioRegistralEntrada
	 */
	public void save(IntercambioRegistralEntradaVO intecambioRegistralEntrada);

	/**
	 * borra el intercambio registral de entrada
	 * @param intercambioRegistralEntrada
	 */
	public void delete(IntercambioRegistralEntradaVO intercambioRegistralEntrada);

	/**
	 * actualiza el estado del intercambio
	 * @param intercambioRegistralEntrada
	 * @param estado
	 */
	public void updateEstado(
			IntercambioRegistralEntradaVO intercambioRegistralEntrada,
			EstadoIntercambioRegistralEntradaEnumVO estado);

	/**
	 * Metodo que obtiene la informacion del estado del intercambio
	 * @param {{@link List} - Listado de objetos IntercambioRegistralEntradaVO
	 */
	public List<IntercambioRegistralEntradaVO> getInfoEstado(IntercambioRegistralEntradaVO intecambioRegistralEntrada);

	/**
	 * Busca un listado de asientos registrales a partir del estado y unos criterios de búsqueda
	 *
	 * @param estado
	 * @param criterios
	 * @return {{@link List} - Listado de objetos BandejaEntradaItemVO
	 */
	public List<BandejaEntradaItemVO> findByCriterios(EstadoIntercambioRegistralEntradaEnumVO estado, CriteriosBusquedaIREntradaVO criterios);

	/**
	 * Busca un listado paginado de asientos registrales a partir del estado y unos criterios de búsqueda
	 *
	 * @param estado
	 * @param criterios
	 * @param pageInfo
	 * @return {{@link PaginatedArrayList} - Listado de objetos BandejaEntradaItemVO
	 */
	public PaginatedArrayList<BandejaEntradaItemVO> findByCriterios(EstadoIntercambioRegistralEntradaEnumVO estado, CriteriosBusquedaIREntradaVO criterios, PageInfo pageInfo);

	/**
	 * Obtiene la bandeja de entrada para el siguiente <code>estado</code>
	 * @param estado
	 * @return
	 */
	public List<BandejaEntradaItemVO> getBandejaEntradaByEstado(Integer estado,Integer idOficina);

	/**
	 * Completa datos del elemento <code>bandejaEntradaItemVO</code> que no se cargan
	 * al leer la bandeja de entrada completa
	 * @param bandejaEntradaItemVO
	 * @return
	 */
	public BandejaEntradaItemVO completarBandejaEntradaItem(BandejaEntradaItemVO bandejaEntradaItemVO);

	/**
	 * Consulta para obtener el intercambio por libro, registro y
	 * estado (id_arch, id_fdr y state).
	 *
	 *
	 * @param idLibro
	 * @param idRegistro
	 * @param estado
	 * @return
	 */
	public IntercambioRegistralEntradaVO getIntercambioRegistralEntradaByRegistro(
			Integer idLibro, Integer idRegistro, Integer estado);

	/**
	 * Obtiene el intercambio registral de entrada de la tabla scr_exreg_in a partir de id_ofic y id_exchange_sir
	 * @param idOficina
	 * @param idIntercambioRegistralSir
	 * @return
	 */
	public List <IntercambioRegistralEntradaVO> getIntercambioRegistralEntradaByIdIntercambioRegistralSir (
			Integer idOficina, String idIntercambioRegistralSir);
}
