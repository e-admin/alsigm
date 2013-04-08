package es.ieci.tecdoc.fwktd.sir.api.manager;

import java.util.List;

import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;

/**
 * Interfaz para los gestores de recepción de ficheros de datos de intercambio y
 * de control.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface RecepcionManager {

	/**
	 * Recibe un fichero de datos de intercambio del nodo distribuido asociado.
	 *
	 * @param xmlFicheroIntercambio
	 *            XML con la información del fichero de datos de intercambio en
	 *            formato SICRES 3.0.
	 * @param documentos
	 *            Documentos del intercambio.
	 * @return Información del asiento registral creado.
	 */
	public AsientoRegistralVO recibirFicheroIntercambio(
			String xmlFicheroIntercambio, List<byte[]> documentos);

	/**
	 * Recibe un fichero de datos de control del nodo distribuido asociado.
	 *
	 * @param xmlMensaje
	 *            XML con la información del mensaje en formato SICRES 3.0.
	 */
	public void recibirMensaje(String xmlMensaje);

	/**
	 * Procesa los ficheros de datos de intercambio y de control recibidos
	 * mediante el sistema de ficheros.
	 */
	public void procesarFicherosRecibidos();
}
