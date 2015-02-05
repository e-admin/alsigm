package docvitales.exceptions;

import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;

public class DocumentoVitalException extends ActionNotAllowedException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public final static int EXISTE_DOCUMENTO_VITAL_VIGENTE = 0;

	public DocumentoVitalException(int codError) {
		super("", codError, ArchivoModules.DOCUMENTOS_VITALES_MODULE);
	}

}
