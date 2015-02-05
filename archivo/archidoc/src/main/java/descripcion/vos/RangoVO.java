package descripcion.vos;

import common.vos.BaseVO;

/**
 * Clase que contiene la información de rangos
 */
public class RangoVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String rangoInicial;
	private String rangoFinal;

	/**
	 * @return el rangoFinal
	 */
	public String getRangoFinal() {
		return rangoFinal;
	}

	/**
	 * @param rangoFinal
	 *            el rangoFinal a establecer
	 */
	public void setRangoFinal(String rangoFinal) {
		this.rangoFinal = rangoFinal;
	}

	/**
	 * @return el rangoInicial
	 */
	public String getRangoInicial() {
		return rangoInicial;
	}

	/**
	 * @param rangoInicial
	 *            el rangoInicial a establecer
	 */
	public void setRangoInicial(String rangoInicial) {
		this.rangoInicial = rangoInicial;
	}
}
