package descripcion.model.xml.card;

import java.text.SimpleDateFormat;
import java.util.Date;

import common.Constants;

import descripcion.model.xml.XmlElement;

public class UdocFechaFormater extends XmlElement {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String tipo;
	private Date fecha;
	private String separador;
	private String separadorDefecto;

	/**
	 * 
	 * @param tipo
	 * @param fecha
	 * @param separador
	 * @param separadorDefecto
	 *            ConfigConstants.getInstance().
	 *            getSeparadorDefectoFechasRelacion();
	 */
	public UdocFechaFormater(String tipo, Date fecha, String separador,
			String separadorDefecto) {
		super();
		this.tipo = tipo;
		this.fecha = fecha;
		this.separador = separador;
	}

	public UdocFechaFormater(String tipo, Date fecha, String separadorDefecto) {
		super();
		this.tipo = tipo;
		this.fecha = fecha;
		this.separadorDefecto = separadorDefecto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String toXML(int indent) {
		String valor = getValorString();

		StringBuffer strBuff = new StringBuffer("<FECHA FMT=\"").append(tipo)
				.append("\" SEP=\"").append(separador).append("\" CALIF=\"\">")
				.append(valor).append("</FECHA>");

		return strBuff.toString();
	}

	public String getSeparador() {
		return separador;
	}

	public void setSeparador(String separador) {
		this.separador = separador;
	}

	public String getValorString() {
		String valorFecha = new String();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					Constants.FORMATO_FECHA_DIA + separador
							+ Constants.FORMATO_FECHA_MES + separador
							+ Constants.FORMATO_FECHA_ANIO);
			valorFecha = sdf.format(fecha);
		} catch (Exception e) {
			this.setSeparador(getSeparadorDefecto());
			valorFecha = Constants.dateFormater.format(fecha);
		}
		return valorFecha;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setSeparadorDefecto(String separadorDefecto) {
		this.separadorDefecto = separadorDefecto;
	}

	public String getSeparadorDefecto() {
		return separadorDefecto;
	}

}
