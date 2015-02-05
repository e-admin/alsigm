package es.ieci.tecdoc.fwktd.sir.api.manager;

import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoFormVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;

/**
 * Interfaz para los managers de interesados de asientos registrales.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface InteresadoManager extends BaseManager<InteresadoVO, String> {

	/**
	 * Crea un interesado.
	 *
	 * @param idAsientoRegistral
	 *            Identificador del asiento registral.
	 * @param interesadoForm
	 *            Información del interesado.
	 * @return Información del interesado creado.
	 */
	public InteresadoVO saveInteresado(String idAsientoRegistral,
			InteresadoFormVO interesadoForm);

	/**
	 * Elimina los interesados de un asiento registral.
	 *
	 * @param idAsientoRegistral
	 *            Identificador del asiento registral.
	 */
	public void deleteByIdAsientoRegistral(String idAsientoRegistral);

}
