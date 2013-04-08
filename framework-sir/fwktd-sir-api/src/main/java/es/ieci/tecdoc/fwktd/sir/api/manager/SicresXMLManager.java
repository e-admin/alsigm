package es.ieci.tecdoc.fwktd.sir.api.manager;

import es.ieci.tecdoc.fwktd.sir.api.vo.FicheroIntercambioVO;
import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;
import es.ieci.tecdoc.fwktd.sir.core.vo.AsientoRegistralVO;

/**
 * Interfaz para los gestores de XML de SICRES.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface SicresXMLManager {

	/**
	 * Valida el contenido de un fichero de intercambio conforme a la normativa
	 * SICRES 3.0.
	 *
	 * @param ficheroIntercambio
	 *            Fichero de intercambio.
	 */
	public void validarFicheroIntercambio(FicheroIntercambioVO ficheroIntercambio);

	/**
	 * Valida el contenido de un asiento registral conforme a la normativa
	 * SICRES 3.0.
	 *
	 * @param asiento
	 *            Información del asiento registral.
	 */
	public void validarAsientoRegistral(AsientoRegistralVO asiento);

	/**
	 * Valida la información del fichero de datos de control.
	 *
	 * @param mensaje
	 *            Información de mensaje SICRES 3.0.
	 */
	public void validarMensaje(MensajeVO mensaje);

	/**
	 * Devuelve un XML con el fichero de datos de intercambio.
	 *
	 * @param asiento
	 *            Información del asiento registral.
	 * @param attached
	 *            Indica si los documentos van incluidos en el XML (attached) o
	 *            en ficheros aparte (detached).
	 * @return XML de fichero de intercambio
	 */
	public String createXMLFicheroIntercambio(AsientoRegistralVO asiento,
			boolean docsAttached);

	/**
	 * Devuelve un XML con el mensaje de propósito general con el objetivo de
	 * realizar tareas de avisos y gestión de flujo del intercambio.
	 *
	 * @param mensaje
	 *            Información del mensaje.
	 * @return XML de mensaje.
	 */
	public String createXMLMensaje(MensajeVO mensaje);

	/**
	 * Carga la información del fichero de datos de intercambio.
	 *
	 * @param xml
	 *            XML con la información del fichero de datos de intercambio.
	 * @return Información del fichero de datos de intercambio.
	 */
	public FicheroIntercambioVO parseXMLFicheroIntercambio(String xml);

	/**
	 * Carga la información del mensaje de datos de control.
	 *
	 * @param xml
	 *            XML con la información del fichero de datos de control.
	 * @return Información del mensaje.
	 */
	public MensajeVO parseXMLMensaje(String xml);

}
