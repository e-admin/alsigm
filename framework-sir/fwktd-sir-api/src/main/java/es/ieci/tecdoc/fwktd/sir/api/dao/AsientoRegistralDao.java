package es.ieci.tecdoc.fwktd.sir.api.dao;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.CriteriosVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.EstadoAsientoRegistraVO;

/**
 * Interfaz de los DAOs de asientos registrales.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface AsientoRegistralDao extends
		BaseDao<AsientoRegistralVO, String> {

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
	 * Obtiene la descripcion del tipo de anotacion de un asiento registral.
	 *
	 * @param id
	 *            Identificador del asiento registral.
	 * @return Decripcion del tipo de anotacion
	 */
	public String getDescripcionTipoAnotacion(String id);

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

}
