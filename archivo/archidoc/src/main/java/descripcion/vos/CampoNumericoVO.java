package descripcion.vos;

import common.util.NumberUtils;

import descripcion.model.xml.card.Valor;

/**
 * VO para el almacenamiento de información de un campo de tipo numérico.
 */
public class CampoNumericoVO extends ValorCampoGenericoVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private double valor;
	private int tipoMedida;
	private String unidadMedida;

	public CampoNumericoVO(double valor, String idCampo, int orden,
			int tipoElemento) {
		setTipo(TIPO_NUMERICO);
		setIdCampo(idCampo);
		setOrden(orden);
		setValor(valor);
		setTipoElemento(tipoElemento);
	}

	public CampoNumericoVO() {
		super();
		setTipo(TIPO_NUMERICO);
	}

	public CampoNumericoVO(String idElementoCF, String idCampo, int orden,
			double valor, int tipoElemento) {
		this(idElementoCF, idCampo, orden, valor, 0, null, tipoElemento);
	}

	public CampoNumericoVO(String idElementoCF, String idCampo, int orden,
			double valor, int tipoMedida, String unidadMedida, int tipoElemento) {
		super();
		setTipo(TIPO_NUMERICO);
		setIdObjeto(idElementoCF);
		setIdCampo(idCampo);
		setOrden(orden);
		setValor(valor);
		setTipoMedida(tipoMedida);
		setUnidadMedida(unidadMedida);
		setTipoElemento(tipoElemento);
	}

	/**
	 * @return Returns the tipoMedida.
	 */
	public int getTipoMedida() {
		return tipoMedida;
	}

	/**
	 * @param tipoMedida
	 *            The tipoMedida to set.
	 */
	public void setTipoMedida(int tipoMedida) {
		this.tipoMedida = tipoMedida;
	}

	/**
	 * @return Returns the unidadMedida.
	 */
	public String getUnidadMedida() {
		return unidadMedida;
	}

	/**
	 * @param unidadMedida
	 *            The unidadMedida to set.
	 */
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public String getValorAsString() {
		return NumberUtils
				.formatea(valor, NumberUtils.FORMATO_CUATRO_DECIMALES);
	}

	/**
	 * @return Returns the valor.
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            The valor to set.
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}

	public void setValue(String value) {
		try {
			setValor(Double.parseDouble(value));
		} catch (Exception e) {
		}
	}

	/**
	 * Obtiene la información del Valor.
	 * 
	 * @return Valor.
	 */
	public Valor getValorInfo() {
		Valor v = new Valor();

		v.setValor("" + this.valor);
		v.setTipoMedida(this.tipoMedida);
		v.setUnidadMedida(this.unidadMedida);
		v.setOrden(this.getOrden());
		v.setTipoElemento(this.getTipoElemento());

		return v;
	}

}
