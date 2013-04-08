package descripcion.exceptions;

import common.exceptions.CheckedArchivoException;

public class CampoNotFoundException extends CheckedArchivoException {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CampoNotFoundException(String id) {
		super();
		this.id = id;
	}
}
