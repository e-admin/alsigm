package es.ieci.tecdoc.isicres.api.business.vo;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public class EstadoLibroVO extends BaseEstadoLibroVO {

	private static final long serialVersionUID = -331419309686592198L;

	/**
	 *
	 */
	protected String comentario;

	protected String idEstado;

	public String getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}

	/**
	 * @return el comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario
	 *            el comentario a fijar
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
