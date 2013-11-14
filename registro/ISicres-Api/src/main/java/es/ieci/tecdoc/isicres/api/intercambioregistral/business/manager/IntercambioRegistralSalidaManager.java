package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.server.pagination.PaginatedArrayList;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaSalidaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIRSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO;

/**
 * interface con las opereraciones relacionadas con el intercambio registral de salida
 *
 */
public interface IntercambioRegistralSalidaManager {

	/**
	 * metodo que verifica si un registro tiene configurado un intercambio registral a traves de la unidad destino
	 * @param registro
	 * @return
	 */
	public boolean isIntercambioRegistral(String idUnidadTramitacionDestino);

	/**
	 * metodo que verifica si un registro YA ESTÁ en la bandeja de salida de intercambio registral
	 * @param registro
	 * @return
	 */
	public boolean isInBandejasalidaIntercambioRegistral(String idRegistro, String idLibro);


	/**
	 * metodo que a partir de los datos pasados como parametros, crea un intercambio registral de salida con estado PENDIENTE
	 * @param idLibro
	 * @param idRegistro
	 * @param idOfic
	 * @param tipoOrigen
	 * @param idUnidadTramitacionDestino
	 * @param user
	 */
	public void toIntercambioRegistral(String idLibro, String idRegistro, String idOfic,String tipoOrigen, String idUnidadTramitacionDestino, String user);

	/**
	 * Metodo que envia a la bandeja de salida de intercambio registral N registros.
	 * Se diferencia principalmente de <code>toIntercambioRegistral</code> en que no comprueba si el destino es intercambio registral.
	 *
	 */
	public void toIntercambioRegistralManual(List<String> idRegistros, String idLibro, String idOfic,String tipoOrigen, String user);



	/**
	 * Envía el registro <code>idRegistro</code> del libro <code>idLibro</code> al destino <code>unidadDestino</code>
	 * @param idLibro
	 * @param idRegistro
	 * @param idOfic
	 * @param username
	 * @param tipoOrigen
	 * @param unidadDestino
	 * @return String el número de intermcabio registral retornado por el CIR
	 */
	public String enviarIntercambioRegistralSalida(
			String idLibro, String idRegistro, String idOfic, String username,String tipoOrigen, UnidadTramitacionIntercambioRegistralVO unidadDestino);

/**
 * metodo que reenvia un intercambio registral de salida hacia el SIR a partir de su ID
 * @param id
 * @param usuario
 * @param contacto
 * @param descripcionReenvio
 * @param nuevoDestino
 */
	public void  reenviarIntercambioRegistralSalidaById(
			String id, String usuario, String contacto, String descripcionReenvio, UnidadTramitacionIntercambioRegistralVO nuevoDestino);


	/**
	 * metodo que si el registro se encuentra en la bandeja de salida de intercambio registral en estado pendientes de envio, puede ser anulado
	 * @param id
	 */
	public void anularIntercambioRegistralSalidaById(String id);

	/**
	 * metodo que si en la bandeja salida hay algun registro en estado anulado, lo pasa a estado pendiente de envio
	 * @param id
	 */
	public void undoAnularIntercambioRegistral(String id);

	/**
	 * Metodo que obtiene la bandeja de salida para el estado y oficina
	 * @param estado
	 * @param oficina
	 * @return
	 */
	public List<BandejaSalidaItemVO> getBandejaSalidaIntercambioRegistral(Integer estado,Integer idOficina);

	/**
	 * metodo que obtiene la bandeja de salida de intercambio registral para un estado, una oficina y un libro
	 * @param estado
	 * @param idOficina
	 * @param idLibro
	 * @return
	 */
	public List<BandejaSalidaItemVO> getBandejaSalidaIntercambioRegistral(Integer estado, Integer idOficina, Integer idLibro);


	/**
	 * Metodo que a partir de un elemento de la bandeja de salida con los datos de SCR_EXREG, completa los datos
	 * necesarios de otras tablas (AXSF) como numero de registro o unidad de destino.
	 * @param bandejaSalidaItemVO
	 * @return
	 */
	public BandejaSalidaItemVO completarBandejaSalidaItem(BandejaSalidaItemVO bandejaSalidaItemVO);


	/**
	 * Metodo que obtiene un intercambio registral de salida a partir de su ID
	 * @param id
	 * @return
	 */
	public IntercambioRegistralSalidaVO getIntercambioRegistralSalidaById(
			String id);

	/**
	 * Obtiene todos los intercambios registrales segun su estado
	 * @param estado
	 * @return
	 */
	public List<IntercambioRegistralSalidaVO> getIntercambiosRegistralesSalida(Integer estado);

	/**
	 * Elimina un intercambio registral de salida. Deberá estar en estado PENDIENTE
	 * @param idLibro
	 * @param idRegistro
	 */
	public void deleteIntercambioRegistralSalida(Integer idLibro, Integer idRegistro, Integer idOficina);

	/**
	 * Actualiza el estado del intercambio registral pasado por parametro
	 * @param intecambioRegistralSalida
	 * @param estado
	 */
	public void updateEstado(IntercambioRegistralSalidaVO intecambioRegistralSalida,EstadoIntercambioRegistralSalidaVO estado);

	/**
	 * Metodo que obtiene un listado con el historial de los intercambios registrales para un registro
	 * @param idLibro
	 * @param idRegistro
	 * @param idOficina
	 */
	public List<IntercambioRegistralSalidaVO> getHistorialIntercambioRegistralSalida(String idLibro, String idRegistro, String idOficina);

	/**
	 * Busca un listado de asientos registrales a partir del estado y unos criterios de búsqueda
	 *
	 * @param estado
	 * @param criterios
	 * @return {{@link List} - Listado de objetos BandejaSalidaItemVO
	 */
	public List<BandejaSalidaItemVO> findBandejaSalidaByCriterios(EstadoIntercambioRegistralSalidaEnumVO estado, CriteriosBusquedaIRSalidaVO criterios, Integer idLibro);

	/**
	 * Busca un listado paginado de asientos registrales a partir del estado y unos criterios de búsqueda
	 *
	 * @param estado
	 * @param criterios
	 * @param pageInfo
	 * @return {{@link PaginatedArrayList} - Listado de objetos BandejaSalidaItemVO
	 */
	public PaginatedArrayList<BandejaSalidaItemVO> findBandejaSalidaByCriterios(EstadoIntercambioRegistralSalidaEnumVO estado, CriteriosBusquedaIRSalidaVO criterios, Integer idLibro, PageInfo pageInfo);

}
