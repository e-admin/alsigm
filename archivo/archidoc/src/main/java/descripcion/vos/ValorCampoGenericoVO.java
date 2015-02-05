package descripcion.vos;

import java.util.Date;

/**
 * VO genérico para el almacenamiento de información de un campo.
 */
public abstract class ValorCampoGenericoVO extends ValorCampoGenericoVOBase {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// private String idElementoCF = null;
	private String idObjeto = null;

	private Date timestamp = null;

	protected ValorCampoGenericoVO(String idCampo, int orden, int tipoElemento) {
		super(idCampo, orden, tipoElemento);
	}

	/**
	 * Constructor.
	 */
	public ValorCampoGenericoVO() {
		this.timestamp = new Date();
	}

	/**
	 * Constructor.
	 */
	public ValorCampoGenericoVO(short tipo) {
		this();
		setTipo(tipo);
	}

	/**
	 * @return Returns the timestamp.
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            The timestamp to set.
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getIdObjeto() {
		return idObjeto;
	}

	public void setIdObjeto(String idObjeto) {
		this.idObjeto = idObjeto;
	}

}
