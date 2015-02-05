package common.exceptions;

import es.archigest.framework.core.exceptions.ArchigestModelException;

/**
 * Excepción generada cuando ocurre algún de seguridad en la aplicación.
 */
public class SecurityException extends ArchigestModelException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Listas de permisos que debería cumplir para realizar la acción */
	private String[][] permission = null;

	/**
	 * Constructor.
	 * 
	 * @param e
	 *            Excepción capturada.
	 * @param where
	 *            Lugar donde se ha lanzado la excepción.
	 * @param msg
	 *            Texto del mensaje.
	 */
	public SecurityException(Exception e, String where, String msg) {
		super(e, where, msg);
	}

	/**
	 * Constructor.
	 * 
	 * @param where
	 *            Lugar donde se ha lanzado la excepción.
	 * @param method
	 *            Método que lanza la excepción.
	 * @param cause
	 *            Causa de la excepción.
	 */
	public SecurityException(Class where, String method, String cause) {
		super(where, method, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param cause
	 *            Causa de la excepción.
	 */
	public SecurityException(String cause) {
		super(cause);
	}

	public String[][] getPermission() {
		return permission;
	}

	public void setPermission(String[][] permission) {
		this.permission = permission;
	}
}
