package se.repositorios.archigest;

import java.io.Serializable;
import java.util.Date;

/**
 * Información de un fichero.
 */
public class InfoFicheroVO implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Date m_fechaAlta;
	private FirmaVO[] m_firmas;
	private String m_nombre;

	public InfoFicheroVO() {
		super();
	}

	public InfoFicheroVO(Date _fechaAlta, FirmaVO[] _firmas, String _nombre) {
		m_fechaAlta = _fechaAlta;
		m_firmas = _firmas;
		m_nombre = _nombre;
	}

	public Date getFechaAlta() {
		return m_fechaAlta;
	}

	public void setFechaAlta(Date _fechaAlta) {
		m_fechaAlta = _fechaAlta;
	}

	public FirmaVO[] getFirmas() {
		return m_firmas;
	}

	public void setFirmas(FirmaVO[] _firmas) {
		m_firmas = _firmas;
	}

	public String getNombre() {
		return m_nombre;
	}

	public void setNombre(String _nombre) {
		m_nombre = _nombre;
	}

}
