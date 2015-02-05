/**
 *
 */
package salas.vos;

import common.vos.BaseVO;
import common.vos.IKeyValue;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class UsuarioArchivoSalasConsultaVO extends BaseVO implements IKeyValue {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idArchivo == null) ? 0 : idArchivo.hashCode());
		result = prime * result
				+ ((idusrcsala == null) ? 0 : idusrcsala.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioArchivoSalasConsultaVO other = (UsuarioArchivoSalasConsultaVO) obj;
		if (idArchivo == null) {
			if (other.idArchivo != null)
				return false;
		} else if (!idArchivo.equals(other.idArchivo))
			return false;
		if (idusrcsala == null) {
			if (other.idusrcsala != null)
				return false;
		} else if (!idusrcsala.equals(other.idusrcsala))
			return false;
		return true;
	}

	/**
	 * Identificador del Usuario
	 */
	private String idusrcsala;

	/**
	 * Identificador del Archivo
	 */
	private String idArchivo;

	/**
	 * Nombre del Archivo (Solo Consulta)
	 */
	private String nombreArchivo;

	public UsuarioArchivoSalasConsultaVO() {
		super();
	}

	public UsuarioArchivoSalasConsultaVO(String idusrcsala, String idArchivo,
			String nombreArchivo) {
		super();
		this.idusrcsala = idusrcsala;
		this.idArchivo = idArchivo;
		this.nombreArchivo = nombreArchivo;
	}

	public UsuarioArchivoSalasConsultaVO(String idusrcsala, String idArchivo) {
		super();
		this.idusrcsala = idusrcsala;
		this.idArchivo = idArchivo;
	}

	/**
	 * @return el idUsrcsala
	 */
	public String getIdusrcsala() {
		return idusrcsala;
	}

	/**
	 * @param idUsrcsala
	 *            el idUsrcsala a fijar
	 */
	public void setIdUsrcsala(String idusrcsala) {
		this.idusrcsala = idusrcsala;
	}

	/**
	 * @return el idArchivo
	 */
	public String getIdArchivo() {
		return idArchivo;
	}

	/**
	 * @param idArchivo
	 *            el idArchivo a fijar
	 */
	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.vos.IKeyValue#getKey()
	 */
	public String getKey() {
		return this.idArchivo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.vos.IKeyValue#getValue()
	 */
	public String getValue() {
		return this.nombreArchivo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.vos.IKeyValue#getId()
	 */
	public String getId() {
		return this.idArchivo;
	}
}
