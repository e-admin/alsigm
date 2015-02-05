package descripcion.vos;

import descripcion.model.xml.card.Valor;

/**
 * VO para el almacenamiento de información de un campo de tipo texto.
 */
public class CampoTextoVO extends ValorCampoGenericoVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String valor = null;

	public CampoTextoVO() {
		super();
		setTipo(TIPO_TEXTO_CORTO);
	}

	public CampoTextoVO(String valor, String idCampo, int orden,
			int tipoElemento) {
		super();
		setTipo(TIPO_TEXTO_CORTO);
		setIdCampo(idCampo);
		setOrden(orden);
		this.valor = valor;
		setTipoElemento(tipoElemento);
	}

	public CampoTextoVO(String idElementoCF, String idCampo, int orden,
			String valor, int tipoElemento) {
		this(idElementoCF, idCampo, orden, TIPO_TEXTO_CORTO, valor,
				tipoElemento);
	}

	public CampoTextoVO(String idElementoCF, String idCampo, int orden,
			short tipo, String valor, int tipoElemento) {
		super();
		setIdObjeto(idElementoCF);
		setIdCampo(idCampo);
		setOrden(orden);
		setTipo(tipo);
		setValor(valor);
		setTipoElemento(tipoElemento);
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

	public void setValue(String value) {
		setValor(value);
	}

	/**
	 * Obtiene la información del Valor.
	 * 
	 * @return Valor.
	 */
	public Valor getValorInfo() {
		Valor v = new Valor();

		v.setValor(this.valor);
		v.setOrden(this.getOrden());
		v.setTipoElemento(this.getTipoElemento());

		return v;
	}

}
