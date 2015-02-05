/**
 *
 */
package descripcion.exceptions;

import common.exceptions.CheckedArchivoException;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class DescriptorDuplicadoException extends CheckedArchivoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public DescriptorDuplicadoException(String cause) {
		super(cause);
	}

}
