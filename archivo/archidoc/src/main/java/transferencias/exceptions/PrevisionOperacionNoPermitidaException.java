package transferencias.exceptions;

import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;

/**
 * Excepción que se produce cuando se trata de realizar alguna acción sobre
 * presiones de transferencia y dicha acción no está permitida.
 */
public class PrevisionOperacionNoPermitidaException extends
		ActionNotAllowedException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Código que especifica el motivo por el que se produce la excepción
	 * 
	 * @see auditoria.ArchivoErrorCodes
	 */
	int motivo;

	public PrevisionOperacionNoPermitidaException(int motivo, String causa) {
		super(causa, motivo, ArchivoModules.TRANSFERENCIAS_MODULE);
	}

	public PrevisionOperacionNoPermitidaException(int motivo) {
		super(null, motivo, ArchivoModules.TRANSFERENCIAS_MODULE);
	}

}