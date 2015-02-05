package valoracion.vos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Parte de la informacion referente a una unidad documental se almacena en
 * forma de xml. Para su tratamiento se genera una jerarquia de objetos que
 * representan la informacion contenida en ese xml.
 * UnidadDocumentalHistoricaXInfo sirve como contenedor de esa jerarquía de
 * objetos
 */
public class UnidadDocumentalHistoricaXInfo {

	/** Fecha Inicial de la unidad documental */
	String fechaInicial = null;

	/** Fecha Final de la unidad documental */
	String fechaFinal = null;

	/**
	 * @return el fechaInicial
	 */
	public String getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * @param fechaInicial
	 *            el fechaInicial a establecer
	 */
	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	/**
	 * @return el fechaFinal
	 */
	public String getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * @param fechaFinal
	 *            el fechaFinal a establecer
	 */
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	/** Formateador de fechas */
	static DateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");

	public String toXMLString() {

		StringBuffer asXML = new StringBuffer();

		if (getFechaInicial() != null)
			asXML.append("<FECHA_INICIAL>").append("<FECHA>")
					.append(getFechaInicial())
					.append("</FECHA></FECHA_INICIAL>");
		if (getFechaFinal() != null)
			asXML.append("<FECHA_FINAL>").append("<FECHA>")
					.append(getFechaFinal()).append("</FECHA></FECHA_FINAL>");

		return asXML.toString();
	}

}
