package se.repositorios.archigest;

import java.io.Serializable;

/**
 * Información de firma electrónica.
 */
public class FirmaVO implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Boolean m_autenticada;
	private String m_autor;

	public FirmaVO() {
		super();
	}

	public FirmaVO(Boolean _autenticada, String _autor) {
		m_autenticada = _autenticada;
		m_autor = _autor;
	}

	public Boolean getAutenticada() {
		return m_autenticada;
	}

	public void setAutenticada(Boolean _autenticada) {
		m_autenticada = _autenticada;
	}

	public String getAutor() {
		return m_autor;
	}

	public void setAutor(String _autor) {
		m_autor = _autor;
	}
}
