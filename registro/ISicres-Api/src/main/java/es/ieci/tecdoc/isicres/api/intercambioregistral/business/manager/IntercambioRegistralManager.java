package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.pagination.PageInfo;
import es.ieci.tecdoc.fwktd.server.pagination.PaginatedArrayList;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.TrazabilidadVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaEntradaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.BandejaSalidaItemVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIREntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CriteriosBusquedaIRSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EntidadRegistralDCO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralEntradaEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.EstadoIntercambioRegistralSalidaEnumVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.InfoAsientoRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralEntradaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.IntercambioRegistralSalidaVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadAdministrativaIntercambioRegistralVO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionDCO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.UnidadTramitacionIntercambioRegistralVO;

/**
 * Interfaz fachada de las operaciones relacionadas con el intercambio registral
 *
 */
public interface IntercambioRegistralManager {

	/**
	 * metodo que verifica si un registro tiene configurado un intercambio
	 * registral a traves de la unidad destino
	 *
	 * @param idUnidadTramitacionDestino
	 * @return
	 */
	public boolean isIntercambioRegistral(String idUnidadTramitacionDestino);

	/**
	 * metodo que verifica si un registro YA ESTÁ en la bandeja de salida de
	 * intercambio registral
	 *
	 * @param registro
	 * @return
	 */
	public boolean isInBandejasalidaIntercambioRegistral(String idRegistro,
			String idLibro);

	/**
	 * metodo que si un registro tiene como destino una unidad que tiene
	 * configurado intercambio registral, envía el registro hacia la bandeja de
	 * salida de intercambio registral
	 *
	 */
	public void toIntercambioRegistral(String idLibro, String idRegistro,
			String idOfic, String tipoOrigen,
			String idUnidadTramitacionDestino, String user);

	/**
	 * Metodo que envia a la bandeja de salida de intercambio registral N
	 * registros. Se diferencia principalmente de
	 * <code>toIntercambioRegistral</code> en que no comprueba si el destino es
	 * intercambio registral.
	 *
	 */
	public void toIntercambioRegistralManual(List<String> idRegistros,
			String idLibro, String idOfic, String tipoOrigen, String user);

	/**
	 * Guardar el intercambio registral en scr_exregaccept indicando que ya se
	 * ha aceptado el intermcabio y se ha creado el registro correspondiente
	 */
	public void guardarIntercambioRegistralEnAceptados(
			IntercambioRegistralEntradaVO intercambioRegistralEntrada);

	/**
	 * metodo que a partir de un identificador obtiene la información del
	 * intercambio registral de salida
	 *
	 * @param id
	 */
	public IntercambioRegistralSalidaVO getIntercambioRegistralSalidaById(
			String id);

	/**
	 * metodo que a partir de un identificador de intercambio faciliado por el
	 * sir obtiene del modulo intermedio la información en detalle del
	 * intercambio registral
	 *
	 * @param idIntercambio
	 */
	public AsientoRegistralVO getIntercambioRegistralByIdIntercambio(
			String idIntercambio);

	/**
	 *
	 * A partir de un identificador de intercambio faciliado por el
	 * sir obtiene del modulo intermedio la información extendida del
	 * intercambio registral
	 *
	 * @param idIntercambio
	 * @return
	 */
	public InfoAsientoRegistralVO getInfoIntercambioRegistralByIdIntercambio(String idIntercambio);

	/**
	 * Consulta para obtener el intercambio por libro, registro y
	 * estado (id_arch, id_fdr y state aceptado).
	 *
	 * @param idLibro
	 * @param idRegistro
	 * @param estado
	 * @return
	 */
	public IntercambioRegistralEntradaVO getIntercambioRegistralEntradaByRegistro(
			Integer idLibro, Integer idRegistro, Integer estado);

	public String enviarIntercambioRegistralSalida(String idLibro,
			String idRegistro, String idOfic, String username,
			String tipoOrigen,
			UnidadTramitacionIntercambioRegistralVO unidadDestino);


	/**
	 * Busca un listado de asientos registrales a partir del estado y unos criterios de búsqueda
	 *
	 * @param estado
	 * @param criterios
	 * @return {{@link List} - Listado de objetos BandejaEntradaItemVO
	 */
	public List<BandejaEntradaItemVO> findBandejaEntradaByCriterios(EstadoIntercambioRegistralEntradaEnumVO estado, CriteriosBusquedaIREntradaVO criterios);

	/**
	 * Busca un listado paginado de asientos registrales a partir del estado y unos criterios de búsqueda
	 *
	 * @param estado
	 * @param criterios
	 * @param pageInfo
	 * @return {{@link PaginatedArrayList} - Listado de objetos BandejaEntradaItemVO
	 */
	public PaginatedArrayList<BandejaEntradaItemVO> findBandejaEntradaByCriterios(EstadoIntercambioRegistralEntradaEnumVO estado, CriteriosBusquedaIREntradaVO criterios, PageInfo pageInfo);

	/**
	 * Busca un listado de asientos registrales a partir del estado, el identificador del libro y unos criterios de búsqueda
	 *
	 * @param estado
	 * @param criterios
	 * @return {{@link List} - Listado de objetos BandejaSalidaItemVO
	 */
	public List<BandejaSalidaItemVO> findBandejaSalidaByCriterios(EstadoIntercambioRegistralSalidaEnumVO estado, CriteriosBusquedaIRSalidaVO criterios, Integer idLibro);

	/**
	 * Busca un listado paginado de asientos registrales a partir del estado, el identificador del libro y unos criterios de búsqueda
	 *
	 * @param estado
	 * @param criterios
	 * @param pageInfo
	 * @return {{@link PaginatedArrayList} - Listado de objetos BandejaSalidaItemVO
	 */
	public PaginatedArrayList<BandejaSalidaItemVO> findBandejaSalidaByCriterios(EstadoIntercambioRegistralSalidaEnumVO estado, CriteriosBusquedaIRSalidaVO criterios, Integer idLibro, PageInfo pageInfo);


	/**
	 * Busca los intercambios pendientes en el módulo SIR
	 *
	 * @param criterios
	 * @return
	 */
	public List<BandejaEntradaItemVO> findAsientosRegistrales(CriteriosVO criterios);

	/**
	 * metodo que si el registro se encuentra en la bandeja de salida de
	 * intercambio registral en estado pendientes de envio, puede ser anulado
	 *
	 * @param id
	 */
	public void anularIntercambioRegistralSalidaById(String id);

	/**
	 * metodo que si en la bandeja salida hay algun registro en estado anulado,
	 * lo pasa a estado pendiente de envio
	 *
	 * @param id
	 */
	public void undoAnularIntercambioRegistral(String id);

	/**
	 * metodo que obtiene la bandeja de salida de intercambio registral para un
	 * estado y una oficina
	 *
	 * @param estado
	 * @param idOficina
	 * @return
	 */
	public List<BandejaSalidaItemVO> getBandejaSalidaIntercambioRegistral(
			Integer estado, Integer idOficina);

	/**
	 * metodo que obtiene la bandeja de salida de intercambio registral para un
	 * estado, una oficina y un libro
	 *
	 * @param estado
	 * @param idOficina
	 * @param idLibro
	 * @return
	 */
	public List<BandejaSalidaItemVO> getBandejaSalidaIntercambioRegistral(
			Integer estado, Integer idOficina, Integer idLibro);

	/**
	 * metodo que obtiene los intercambios registrales completos (listos para
	 * envio) de una bandeja de salida
	 *
	 * @return
	 */
	public List<BandejaSalidaItemVO> getBandejaSalidaIntercambioRegistralCompletos(
			List<BandejaSalidaItemVO> bandejaSalida);

	/**
	 * metodo que obtiene los intercambios registrales sin ER destino (se pueden
	 * enviar pero no directamente) de una bandeja de salida
	 *
	 * @return
	 */
	public List<BandejaSalidaItemVO> getBandejaSalidaIntercambioRegistralSinERDestino(
			List<BandejaSalidaItemVO> bandejaSalida);

	/**
	 * metodo que obtiene los intercambios registrales sin ER de origen (no se
	 * pueden enviar) de una bandeja de salida
	 *
	 * @return
	 */
	public List<BandejaSalidaItemVO> getBandejaSalidaIntercambioRegistralSinEROrigen(
			List<BandejaSalidaItemVO> bandejaSalida);

	/**
	 * metodo que acepta un intercambio registral de entrada
	 *
	 * @param idIntercambioRegistralEntrada
	 * @param idLibro
	 * @param user
	 * @param idOficina
	 * @param codOficina
	 * @param llegoDocFisica
	 */
	public void aceptarIntercambioRegistralEntradaById(
			String idIntercambioRegistralEntrada, String idLibro, String user,
			Integer idOficina, String codOficina, boolean llegoDocFisica);

	/**
	 * Método que rechaza un intercambio registral de entrada
	 *
	 * @param idIntercambioRegistralEntrada
	 * @param tipoREchazo
	 * @param observaciones
	 */
	public void rechazarIntercambioRegistralEntradaById(
			String idIntercambioRegistralEntrada, String tipoRechazo,
			String observaciones);

	/**
	 * Método que reenvia un intercambio registral hacia el SIR a partir de su
	 * ID.
	 *
	 * También lo almacena en la tabla de intercambios de los asientos
	 * registrales
	 *
	 * @param id
	 */
	public void reenviarIntercambioRegistralEntradaById(
			String idIntercambioRegistralEntrada,
			UnidadTramitacionIntercambioRegistralVO nuevoDestino,
			String observaciones);

	/**
	 * Metodo que obtiene la bandeja de entrada para el usuario y siguiendo los
	 * criterios que se le facilitan
	 *
	 * @param estado
	 * @param idOficina
	 * @return
	 */
	public List<BandejaEntradaItemVO> getBandejaEntradaIntercambioRegistral(
			Integer estado, Integer idOficina);

	/**
	 * Metodo que a partir de un elemento de la bandeja de salida con los datos
	 * de SCR_EXREG, completa los datos necesarios de otras tablas (AXSF) como
	 * numero de registro o unidad de destino.
	 *
	 * @param bandejaSalidaItemVO
	 * @return
	 */
	public BandejaSalidaItemVO completarBandejaSalidaItem(
			BandejaSalidaItemVO bandejaSalidaItemVO);

	/**
	 * Metodo que a partir de un elemento de la bandeja de entrada con los datos
	 * de SCR_EXREGACCEPT, completa los datos necesarios de otras tablas (AXSF)
	 * como numero de registro o unidad de origen.
	 *
	 * @param bandejaEntradaItemVO
	 * @return
	 */
	public BandejaEntradaItemVO completarBandejaEntradaItem(
			BandejaEntradaItemVO bandejaEntradaItemVO);

	/**
	 * Elimina un intercambio registral de salida. Deberá estar en estado
	 * PENDIENTE
	 *
	 * @param idLibro
	 * @param idRegistro
	 * @param idOficina
	 */
	public void deleteIntercambioRegistralSalida(Integer idLibro,
			Integer idRegistro, Integer idOficina);

	/**
	 * Obtiene la unidad administrativa a la que pertenecen los codigos del
	 * directorio comun pasados como parametro En caso de que este mapeada la
	 * unidad de tramitacion, se obtiene ese, si no, el de entidad registral.
	 *
	 * @param codeEntidadRegistral
	 * @param codeUnidadTramitacion
	 * @return
	 */
	public UnidadAdministrativaIntercambioRegistralVO getUnidadAdministrativaByCodigosComunes(
			String codeEntidadRegistral, String codeUnidadTramitacion);

	/**
	 * Obtiene la entidad registral mapeada en el modulo comun para este
	 * <code>idScrOfic</code>
	 *
	 * @param idSrcOfic
	 * @return
	 */
	public EntidadRegistralVO getEntidadRegistralVOByIdScrOfic(String idOfic);

	/**
	 * Obtiene la unidad de tramitacion mapeada en el modulo comun para este
	 * <code>idScrOrgs</code>
	 *
	 * @param idScrOrgs
	 * @return
	 */
	public UnidadTramitacionIntercambioRegistralVO getUnidadTramitacionIntercambioRegistralVOByIdScrOrgs(
			String idScrOrgs);

	/**
	 * Obtiene la unidad de tramitacion mapeada en el modulo comun para este
	 * <code>codeScrOrgs</code>
	 *
	 * @param codeScrOrgs
	 * @return
	 */
	public UnidadTramitacionIntercambioRegistralVO getUnidadTramitacionIntercambioRegistralVOByCodeScrOrgs(
			String codeScrOrgs);

	/**
	 * Metodo que obtiene el historial del intercambio registral de Salida para
	 * un registro
	 *
	 * @param idLibro
	 * @param idRegistro
	 * @return Listado de objetos {@link IntercambioRegistralSalidaVO}
	 *
	 * @deprecated
	 */
	public List<IntercambioRegistralSalidaVO> getHistorialIntercambioRegistralSalida(
			String idLibro, String idRegistro, String idOficina);

	/**
	 * Metodo que obtiene el historial del intercambio registral de Salida para
	 * un registro
	 *
	 * @param idLibro
	 * @param idRegistro
	 * @param incluyeTrazas
	 * 		true: Incluye las trazas del intercambio
	 * 		false: No incluye las trazas del intercambio
	 * @return Listado de objetos {@link IntercambioRegistralSalidaVO}
	 */
	public List<IntercambioRegistralSalidaVO> getHistorialIntercambioRegistralSalida(
			String idLibro, String idRegistro, String idOficina, boolean incluyeTrazas);

	/**
	 *
	 *
	 * Metodo que obtiene el historial del intercambio registral de Entrada para
	 * un registro
	 *
	 * @param idLibro
	 * @param idRegistro
	 * @return Listado de objetos {@link IntercambioRegistralEntradaVO}
	 *
	 * @deprecated
	 */
	public List<IntercambioRegistralEntradaVO> getHistorialIntercambioRegistralEntrada(
			String idLibro, String idRegistro, String idOficina);

	/**
	 * Metodo que obtiene el historial del intercambio registral de Entrada para
	 * un registro
	 *
	 * @param idLibro
	 * @param idRegistro
	 * @param incluyeTrazas
	 * 		true: Incluye las trazas del intercambio
	 * 		false: No incluye las trazas del intercambio
	 * @return Listado de objetos {@link IntercambioRegistralEntradaVO}
	 */
	public List<IntercambioRegistralEntradaVO> getHistorialIntercambioRegistralEntrada(
			String idLibro, String idRegistro, String idOficina, boolean incluyeTrazas);


	public List<EntidadRegistralDCO> buscarEntidadesRegistralesDCO(String code,
			String nombre);

	public List<UnidadTramitacionDCO> buscarUnidadesTramitacionDCO(String code,
			String nombre);

	public List<UnidadTramitacionDCO> buscarUnidadesTramitacionDCOByEntidad(String codeEntity, String code,
			String nombre);

	/**
	 * Método que devuelve el contenido de un fichero anexo de un intercambio
	 * registral
	 *
	 * @param idAnexo
	 * @return
	 */
	public byte[] getContenidoAnexo(String idAnexo);

	/**
	 * Devuelve un listado del histórico completo del asiento registral.
	 * Incluidas las trazas de mensajes
	 *
	 * @param id
	 * @return
	 */
	public List<TrazabilidadVO> getTrazasIntercambioRegistral(String id);

	/**
	 * Devuelve un listado del histórico del asiento registral
	 *
	 * @param id
	 * @return
	 */
	public List<TrazabilidadVO> getHistoricoCompletoAsientoRegistral(String id);



}
