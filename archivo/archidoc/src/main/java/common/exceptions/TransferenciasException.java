/*
 * Created on 10-may-2005
 *
 */
package common.exceptions;

/**
 * @author ABELRL
 * 
 */
public class TransferenciasException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public final static int OP_ENVIO = 1;
	public final static int OP_RECHAZO = 2;
	public final static int OP_ELIMINACION = 3;
	public final static int OP_VER = 4;

	public static int ERR_SIN_PERMISOS_SOBRE_ELEMENTO = 1;
	public static int ERR_NO_SE_PUEDE_REALIZAR_LA_OPERACION = 2;

	int tipo;
	int err;

	TransferenciasException(int tipo, int err) {
		super();
		this.tipo = tipo;
	}

	public int getTipo() {
		return this.tipo;
	}

	public int getError() {
		return this.err;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#toString()
	 */
	public String toString() {
		String msg = new StringBuffer().append("TransferenciasException::")
				.append("tipo operacion::" + tipo + "::")
				.append("error::" + err).toString();

		return msg;
	}

}
