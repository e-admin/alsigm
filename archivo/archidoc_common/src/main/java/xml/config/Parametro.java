package xml.config;

import common.vos.BaseVO;

public class Parametro extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String valor;
	/**
	 * @return el id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id el id a establecer
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return el valor
	 */
	public String getValor() {
		return valor;
	}
	/**
	 * @param valor el valor a establecer
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
}
