/**
 *
 */
package transferencias.exceptions;

import common.exceptions.CheckedArchivoException;

/**
 * Una relación de entrega entre archivos con reencajado. No se permite tener
 * varias Fracciones de Serie en una misma unidad de instalación
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class RelacionEntregaNoEnviableUIsConVariasFSException extends
		CheckedArchivoException {

	private static final long serialVersionUID = -4782853517649190712L;

}
