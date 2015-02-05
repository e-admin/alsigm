package ieci.tdw.ispac.services.ws.server.dto;

import java.io.Serializable;

/**
 * Información de la firma digital de un fichero.
 */
public class Firma implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** Autor de la firma digital del fichero. */
	protected String autor = null;

	/** Autenticidad de la firma del fichero. */
	protected boolean autenticada = false;

	/**
	 * Constructor.
	 */
	public Firma() {
		super();
	}

	/**
	 * Indica la autenticidad de la firma del fichero.
	 * @return Autenticidad de la firma del fichero.
	 */
	public boolean isAutenticada() {
		return autenticada;
	}

	/**
	 * Establece la autenticidad de la firma del fichero.
	 * @param autenticidad Autenticidad de la firma del fichero.
	 */
	public void setAutenticada(boolean autenticidad) {
		this.autenticada = autenticidad;
	}

	/**
	 * Obtiene el autor de la firma digital del fichero.
	 * @return Autor de la firma digital del fichero.
	 */
	public String getAutor() {
		return autor;
	}

	/**
	 * Establece el autor de la firma digital del fichero.
	 * @param autor Autor de la firma digital del fichero.
	 */
	public void setAutor(String autor) {
		this.autor = autor;
	}

}
