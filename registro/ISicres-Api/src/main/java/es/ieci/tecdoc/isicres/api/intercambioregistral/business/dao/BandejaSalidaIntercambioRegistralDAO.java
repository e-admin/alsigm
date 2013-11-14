package es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.server.pagination.PaginatedArrayList;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaSalidaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIRSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralSalidaVO;

public interface BandejaSalidaIntercambioRegistralDAO {

	/**
	 * Metodo que almacena un intercambio registral
	 * @param intecambioRegistralSalida
	 */
	public void save(IntercambioRegistralSalidaVO intecambioRegistralSalida);

	/**
	 * Metodo que obtiene un intercambio registral mediante su id
	 * @param id
	 * @return
	 */
	public IntercambioRegistralSalidaVO get(Long id);

	/**
	 * Metodo que obtiene  un intercambio registral  mediante el estado y el id de oficina
	 * @param estado
	 * @param idOficina
	 * @return Listado de objetos {@link IntercambioRegistralSalidaVO}
	 */
	public List<IntercambioRegistralSalidaVO> findByIdIntercambioRegistralSirYOficina(String idIntercambioRegistralSir, Integer idOficina);



	/**
	 * Metodo que actualiza un estado del intercambio registral
	 * @param intecambioRegistralSalida
	 * @param estado
	 */
	public void updateEstado(IntercambioRegistralSalidaVO intecambioRegistralSalida,EstadoIntercambioRegistralSalidaVO estado);


	/**
	 * Busca un listado de asientos registrales a partir del estado y unos criterios de búsqueda
	 *
	 * @param estado
	 * @param criterios
	 * @return {{@link List} - Listado de objetos IntercambioRegistralEntradaVO
	 */
	public List<BandejaSalidaItemVO> findByCriterios(EstadoIntercambioRegistralSalidaEnumVO estado, CriteriosBusquedaIRSalidaVO criterios);

	/**
	 * Busca un listado de asientos registrales a partir del estado, el
	 * identificador del libro y unos criterios de búsqueda.
	 *
	 * Si se conoce el libro sobre el que queremos buscar es más rápido este
	 * método que añadir un criterio con el libro porque no es necesario
	 * completar el resultado con los atributos del registro.
	 *
	 * @param estado
	 * @param criterios
	 * @return {{@link List} - Listado de objetos IntercambioRegistralEntradaVO
	 */
	public List<BandejaSalidaItemVO> findByCriterios(
			EstadoIntercambioRegistralSalidaEnumVO estado,
			CriteriosBusquedaIRSalidaVO criterios, Integer idLibro);

	/**
	 * Busca un listado paginado de asientos registrales a partir del estado y unos criterios de búsqueda
	 *
	 * @param estado
	 * @param criterios
	 * @param pageInfo
	 * @return {{@link PaginatedArrayList} - Listado de objetos BandejaSalidaItemVO
	 */
	public PaginatedArrayList<BandejaSalidaItemVO> findByCriterios(EstadoIntercambioRegistralSalidaEnumVO estado, CriteriosBusquedaIRSalidaVO criterios, PageInfo pageInfo);

	/**
	 *
	 * Busca un listado paginado de asientos registrales a partir del estado, el
	 * identificador del libro y unos criterios de búsqueda.
	 *
	 * Si se conoce el libro sobre el que queremos buscar es más rápido este
	 * método que añadir un criterio con el libro porque no es necesario
	 * completar el resultado con los atributos del registro.
	 *
	 * @param estado
	 * @param criterios
	 * @param idLibro
	 * @param pageInfo
	 * @return @return {{@link PaginatedArrayList} - Listado de objetos BandejaSalidaItemVO
	 */
	public PaginatedArrayList<BandejaSalidaItemVO> findByCriterios(EstadoIntercambioRegistralSalidaEnumVO estado, CriteriosBusquedaIRSalidaVO criterios, Integer idLibro, PageInfo pageInfo);


	/**
	 * Metodo que completa la información del registro
	 *
	 * @param bandejaSalidaItemVO
	 * @return {@link BandejaSalidaItemVO}
	 */
	public BandejaSalidaItemVO completarBandejaSalidaItem(BandejaSalidaItemVO bandejaSalidaItemVO);

	/**
	 * Metodo que obtiene la Bandeja de Salida mediante el estado y el id de oficina
	 * @param estado
	 * @param idOficina
	 * @return Listado de objetos {@link BandejaSalidaItemVO}
	 */
	public List<BandejaSalidaItemVO> getBandejaSalidaByEstadoYOficina(Integer estado, Integer idOficina);

	/**
	 * Metodo que obtiene la Bandeja de Salida mediante el estado y el id de oficina
	 * @param estado
	 * @param idOficina
	 * @return Listado de objetos {@link BandejaSalidaItemVO}
	 */
	public List<BandejaSalidaItemVO> getBandejaSalidaByIdIntercambioRegistralSirYOficina(String idIntercambioRegistralSir, Integer idOficina);


	/**
	 * Metodo que obtiene la Bandeja de Salida mediante el estado, el id de oficina y el id del libro
	 * @param estado
	 * @param idOficina
	 * @param idLibro
	 * @return Listado de objetos {@link BandejaSalidaItemVO}
	 */
	public List<BandejaSalidaItemVO> getBandejaSalidaByEstadoOficinaYLibro(Integer estado,Integer idOficina, Integer idLibro);

	/**
	 * Metodo que obtiene un listado con los intercambios registrales mediante su estado
	 * @param estado
	 * @return Listado de objetos {@link IntercambioRegistralSalidaVO}
	 */
	public List<IntercambioRegistralSalidaVO> getIntercambiosRegistralesSalida(Integer estado);

	/**
	 * Busca los intercambios registrales de salida para el registro con <code>idRegistro</code> e <code>idLibro</code>
	 *
	 * @param idRegistro
	 * @param idLibro
	 * @return Los intercambios realizados con este registro
	 */
	public List<IntercambioRegistralSalidaVO> getIntercambiosRegistralesSalida(Integer idRegistro, Integer idLibro, Integer idOficina);

	/**
	 * Metodo que borra un intercambio registral mediante el id del libro y el id del registro
	 * @param idLibro
	 * @param idRegistro
	 */
	public void deleteByIdArchIdFdr(Integer idLibro,Integer idRegistro, Integer idOficina);

	/**
	 * Metodo que actualiza un intercambio registral
	 *
	 * @param intercambioRegistralSalida
	 */
	public void updateIntercambioRegistralSalidaVO(IntercambioRegistralSalidaVO intercambioRegistralSalida);

	/**
	 * Metodo que almacena un cambio de estado
	 * @param estado - Objeto {@link EstadoIntercambioRegistralSalidaVO}
	 */
	public void saveDetalleEstado(EstadoIntercambioRegistralSalidaVO estado);

	/**
	 * Metodo que obtiene los diferentes estados de un intercambio registral
	 * @param idExReg
	 * @return
	 */
	public List<EstadoIntercambioRegistralSalidaVO> getDetalleEstadosIntercambioRegistralSalida(Long idExReg);
}
