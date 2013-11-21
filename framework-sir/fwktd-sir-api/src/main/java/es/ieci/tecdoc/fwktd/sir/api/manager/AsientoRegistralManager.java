package es.ieci.tecdoc.fwktd.sir.api.manager;

import java.util.Date;
import java.util.List;

import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.fwktd.sir.api.vo.FicheroIntercambioVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.EstadoAsientoRegistraVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoRechazoVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InfoReenvioVO;

/**
 * Interfaz para los managers de asientos registrales.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface AsientoRegistralManager extends
		BaseManager<AsientoRegistralVO, String> {

	/**
	 * Obtiene el número de asientos registrales encontrados según los criterios
	 * de búsqueda.
	 *
	 * @param criterios
	 *            Criterios de búsqueda.
	 * @return Número de asientos registrales encontrados.
	 */
	public int countAsientosRegistrales(CriteriosVO criterios);

	/**
	 * Realiza una búsqueda de asientos registrales.
	 *
	 * @param criterios
	 *            Criterios de búsqueda.
	 * @return Asientos registrales encontrados.
	 */
	public List<AsientoRegistralVO> findAsientosRegistrales(
			CriteriosVO criterios);

	/**
	 * Guarda el asiento registral junto con los interesados y anexos.
	 *
	 * @param asientoForm
	 *            Información del asiento registral.
	 * @return Información del asiento registral creado.
	 */
	public AsientoRegistralVO saveAsientoRegistral(
			AsientoRegistralFormVO asientoForm);

	/**
	 * Guarda el asiento registral junto con los interesados y anexos del fichero de intercambio.
	 *
	 * @param ficheroIntercambio
	 *            Información del fichero de intercambio.
	 * @return Información del asiento registral creado.
	 */
	public AsientoRegistralVO saveAsientoRegistral(FicheroIntercambioVO ficheroIntercambio);
	
	/**
	 * Guarda el asiento registral junto con los interesados y anexos del fichero de intercambio.
	 * No genera el id del AsientoRegistralVO utiliza el pasado como parámetro
	 *
	 * @param ficheroIntercambio
	 *            Información del fichero de intercambio.
	 * @param idAsientoRegistralVO
	 *            Identicador a usar para guardar el AsientoRegistralVO
	 * @return Información del asiento registral creado.
	 */
	public AsientoRegistralVO saveAsientoRegistral(FicheroIntercambioVO ficheroIntercambio, String idAsientoRegistralVO);

	/**
	 * Elimina el asiento registral junto con sus interesados y anexos.
	 *
	 * @param id
	 *            Identificador del asiento registral.
	 */
	public void deleteAsientoRegistral(String id);


	/**
	 * Regenera el asiento registral, borra el existente en el sistema con el id pasaado  y guarda de nuevo el fichero de intercambio en el sistema con id
	 * Estado a recibido
	 * @param ficheroIntercambio
	 * @return
	 */
	public AsientoRegistralVO regenerateAsientoRegistral(FicheroIntercambioVO ficheroIntercambio, String id);

	/**
	 * Obtiene el código de intercambio de un asiento registral.
	 *
	 * @param id
	 *            Identificador del asiento registral.
	 * @return Código de intercambio de un asiento registral.
	 */
	public String getCodigoIntercambio(String id);

	/**
	 * Obtiene el estado de un asiento registral.
	 *
	 * @param id
	 *            Identificador del asiento registral.
	 * @return Estado de un asiento registral.
	 */
	public EstadoAsientoRegistraVO getEstado(String id);

	/**
	 * Obtener la información del asiento registral a partir de su código de
	 * entidad registral e identificador de intercambio.
	 *
	 * @param codigoEntidadRegistral
	 *            Código de la Entidad Registral.
	 * @param identificadorIntercambio
	 *            Identificador de intercambio.
	 * @return Información del asiento registral.
	 */
	public AsientoRegistralVO getAsientoRegistral(
			String codigoEntidadRegistral, String identificadorIntercambio);

	/**
	 * Envío del fichero de datos de intercambio al nodo distribuido asociado.
	 *
	 * @param asientoForm
	 *            Información del asiento registral.
	 * @return Asiento registral creado.
	 */
	public AsientoRegistralVO enviarAsientoRegistral(
			AsientoRegistralFormVO asientoForm);

	/**
	 * Envío del fichero de datos de intercambio al nodo distribuido asociado.
	 *
	 * @param id
	 *            Identificador del asiento registral.
	 */
	public void enviarAsientoRegistral(String id);

	/**
	 * Valida un asiento registral recibido.
	 *
	 * @param id
	 *            Identificador del asiento registral.
	 * @param numeroRegistro
	 *            Número de registro.
	 * @param fechaRegistro
	 *            Fecha de registro.
	 */
	public void validarAsientoRegistral(String id, String numeroRegistro,
			Date fechaRegistro);

	/**
	 * Reenvía un asiento registral recibido o devuelto.
	 *
	 * @param id
	 *            Identificador del asiento registral.
	 */
	public void reenviarAsientoRegistral(String id);

	/**
	 * Reenvía un asiento registral recibido.
	 *
	 * @param id
	 *            Identificador del asiento registral.
	 * @param infoReenvio
	 *            Información del reenvío.
	 */
	public void reenviarAsientoRegistral(String id, InfoReenvioVO infoReenvio);

	/**
	 * Rechaza un asiento registral recibido.
	 *
	 * @param id
	 *            Identificador del asiento registral.
	 * @param infoRechazo
	 *            Información sobre el rechazo.
	 */
	public void rechazarAsientoRegistral(String id,
			InfoRechazoVO infoRechazo);

	/**
	 * Reintenta la validacion de los asientos registrales que están en estado
	 * REINTENTAR_VALIDACION
	 */
	public void reintentarValidarAsientosRegistrales();

	/**
	 * Anula un asiento registral devuelto.
	 *
	 * @param id
	 *            Identificador del asiento registral.
	 */
	public void anularAsientoRegistral(String id);

	/**
	 * Comprueba el time-out de la recepción de mensajes (ACK o ERROR) tras los
	 * envíos de ficheros de datos de intercambio.
	 */
	public void comprobarTimeOutEnvios();

	/**
	 * Obtiene la descripcion del tipo de anotacion de un asiento registral.
	 *
	 * @param id
	 *            Identificador del asiento registral.
	 * @return Decripcion del tipo de anotacion
	 */
	public String getDescripcionTipoAnotacion(String id);


}
