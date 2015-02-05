package es.ieci.tecdoc.fwktd.sir.api.manager;

import es.ieci.tecdoc.fwktd.sir.api.types.BandejaEnum;
import es.ieci.tecdoc.fwktd.sir.api.vo.MensajeVO;

/**
 * Interfaz para los managers de auditoría de ficheros de datos de control.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface AuditoriaMensajeManager {

	/**
	 * Audita la información de un fichero de datos de control
	 *
	 * @param mensaje
	 *            Información del mensaje.
	 * @param bandeja
	 *            Bandeja del mensaje.
	 */
	public void audita(MensajeVO mensaje, BandejaEnum bandeja);

	/**
	 * Audita la información de un fichero de datos de control
	 *
	 * @param mensaje
	 *            Información del mensaje.
	 * @param xml
	 *            XML del mensaje.
	 * @param bandeja
	 *            Bandeja del mensaje.
	 */
	public void audita(MensajeVO mensaje, String xml, BandejaEnum bandeja);
}
