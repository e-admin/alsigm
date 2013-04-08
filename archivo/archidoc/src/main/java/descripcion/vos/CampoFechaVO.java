package descripcion.vos;

import java.util.Date;

import common.Constants;
import common.util.DateUtils;

import descripcion.model.xml.card.Valor;
import descripcion.model.xml.definition.DefTipos;

/**
 * VO para el almacenamiento de información de un campo de tipo fecha.
 */
public class CampoFechaVO extends ValorCampoGenericoVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String valor = null;
	private Date fechaIni = null;
	private Date fechaFin = null;
	private String calificador = null;
	private String formato = null;
	private String sep = null;

	public CampoFechaVO() {
		setTipo(TIPO_FECHA);
	}

	public CampoFechaVO(String idElementoCF, String idCampo, Date valor,
			int tipoElemento) {
		this(idElementoCF, idCampo, 1, valor, tipoElemento);
	}

	public CampoFechaVO(String idElementoCF, String idCampo, int orden,
			Date valor, int tipoElemento) {
		super();
		setIdObjeto(idElementoCF);
		setTipo(TIPO_FECHA);
		setValor(DateUtils.formatDate(valor));
		setIdCampo(idCampo);
		setFechaIni(valor);
		setFechaFin(valor);
		setFormato(DefTipos.TIPO_FORMATO_FECHA_DDMMAAAA);
		setSep(Constants.SLASH);
		setCalificador(null);
		setTipoElemento(tipoElemento);
		setOrden(orden);
	}

	public CampoFechaVO(String valor, String idCampo, Date fechaIni,
			Date fechaFin, String formato, String sep, String calificador,
			int orden, int tipoElemento) {
		super();
		setTipo(TIPO_FECHA);
		setValor(valor);
		setIdCampo(idCampo);
		setFechaIni(fechaIni);
		setFechaFin(fechaFin);
		setFormato(formato);
		setSep(sep);
		setCalificador(calificador);
		setOrden(orden);
		setTipoElemento(tipoElemento);
	}

	public CampoFechaVO(String idElementoCF, String idCampo, int orden,
			String valor, Date fechaIni, Date fechaFin, String formato,
			String sep, String calificador, int tipoElemento) {
		super();
		setTipo(TIPO_FECHA);
		setIdObjeto(idElementoCF);
		setIdCampo(idCampo);
		setOrden(orden);
		setValor(valor);
		setFechaIni(fechaIni);
		setFechaFin(fechaFin);
		setFormato(formato);
		setSep(sep);
		setCalificador(calificador);
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

	/**
	 * @return Returns the calificador.
	 */
	public String getCalificador() {
		return calificador;
	}

	/**
	 * @param calificador
	 *            The calificador to set.
	 */
	public void setCalificador(String calificador) {
		this.calificador = calificador;
	}

	/**
	 * @return Returns the fechaFin.
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin
	 *            The fechaFin to set.
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return Returns the fechaIni.
	 */
	public Date getFechaIni() {
		return fechaIni;
	}

	/**
	 * @param fechaIni
	 *            The fechaIni to set.
	 */
	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	/**
	 * @return Returns the formato.
	 */
	public String getFormato() {
		return formato;
	}

	/**
	 * @param formato
	 *            The formato to set.
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}

	/**
	 * @return Returns the sep.
	 */
	public String getSep() {
		return sep;
	}

	/**
	 * @param sep
	 *            The sep to set.
	 */
	public void setSep(String sep) {
		this.sep = sep;
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
		v.setFormato(this.formato);
		v.setSeparador(this.sep);
		v.setCalificador(this.calificador);
		v.setOrden(this.getOrden());
		v.setTipoElemento(this.getTipoElemento());

		return v;
	}

}
