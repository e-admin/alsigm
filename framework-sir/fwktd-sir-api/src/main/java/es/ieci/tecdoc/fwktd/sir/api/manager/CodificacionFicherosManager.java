package es.ieci.tecdoc.fwktd.sir.api.manager;

import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;

/**
 * Interfaz para los managers de codificación de ficheros.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface CodificacionFicherosManager {

	/**
	 * Obtiene el identificador de intercambio único.
	 *
	 * @param asiento
	 *            Información del asiento registral.
	 * @return Identificador de intercambio único.
	 */
	public String getIdentificadorIntercambio(AsientoRegistralVO asiento);

	/**
	 * Obtiene la codificación para un fichero de datos de control.
	 *
	 * @param mensaje
	 *            Información del mensaje.
	 * @return Codificación del fichero.
	 */
	public String getCodificacionMensaje(MensajeVO mensaje);
}
