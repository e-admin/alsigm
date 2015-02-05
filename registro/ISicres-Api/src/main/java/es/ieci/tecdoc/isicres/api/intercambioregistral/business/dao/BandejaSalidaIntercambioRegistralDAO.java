package es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao;

import java.util.List;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaSalidaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriterioBusquedaBandejaSalidaVO;
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
	 * Metodo que actualiza un estado del intercambio registral
	 * @param intecambioRegistralSalida
	 * @param estado
	 */
	public void updateEstado(IntercambioRegistralSalidaVO intecambioRegistralSalida,EstadoIntercambioRegistralSalidaVO estado);

	/**
	 * Metodo que obtiene un listado  con los intercambios registrales de salida que cumplen el criterio pasado como parametro
	 * @param criterios
	 *
	 * @return Listado de objetos {@link BandejaSalidaItemVO}
	 */
	public List<BandejaSalidaItemVO> findByCriterios(List<CriterioBusquedaBandejaSalidaVO> criterios);

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
