package descripcion.vos;

import common.vos.BaseVO;

public class ProductorElementoCF extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String idelementocf = null;
	private String nombre = null;

	public ProductorElementoCF() {
		super();
	}

	public ProductorElementoCF(String idelementocf, String nombre) {
		super();
		this.idelementocf = idelementocf;
		this.nombre = nombre;
	}

	public String getIdelementocf() {
		return idelementocf;
	}

	public void setIdelementocf(String idelementocf) {
		this.idelementocf = idelementocf;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
