/**
 *
 */
package deposito.exceptions;

import common.exceptions.CheckedArchivoException;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class TipoElementoDepositoException extends CheckedArchivoException {

	public static final int NO_ELIMINABLE_TIENE_DESCENDIENTES = 1;
	public static final int NO_ELIMINABLE_TIPO_REFERENCIADO = 2;
	public static final int NOMBRE_DUPLICADO = 3;
	public static final int NOMBRE_ABREVIADO_DUPLICADO = 4;
	public static final int CARACTER_ORDENACION_HERMANO_DUPLICADO = 5;
	public static final int CARACTER_ORDENACION_NO_EDITABLE = 6;
	public static final int ASIGNABLE_NO_EDITABLE = 7;
	public static final int TIPO_ORDENACION_NO_EDITABLE = 8;
	public static final int CARACTER_ORDENACION_PADRE_DUPLICADO = 9;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int codError = -1;

	public TipoElementoDepositoException(int codError) {
		super();
		this.codError = codError;
	}

	public void setCodError(int codError) {
		this.codError = codError;
	}

	public int getCodError() {
		return codError;
	}
}
