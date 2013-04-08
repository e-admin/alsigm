package descripcion.vos;

import common.vos.BaseVO;

/**
 * VO para los valores de una tabla de validación.
 */
public class TextoTablaValidacionVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private String valor = null;
	private String idTblVld = null;
	private String nombreTblVld = null;

	public TextoTablaValidacionVO() {
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the idTblVld.
	 */
	public String getIdTblVld() {
		return idTblVld;
	}

	/**
	 * @param idTblVld
	 *            The idTblVld to set.
	 */
	public void setIdTblVld(String idTblVld) {
		this.idTblVld = idTblVld;
	}

	/**
	 * @return Returns the valor.
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            The valor to set.
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @return Returns the nombreTblVld.
	 */
	public String getNombreTblVld() {
		return nombreTblVld;
	}

	/**
	 * @param nombreTblVld
	 *            The nombreTblVld to set.
	 */
	public void setNombreTblVld(String nombreTblVld) {
		this.nombreTblVld = nombreTblVld;
	}
}
