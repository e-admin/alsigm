package docvitales.webservice.utils;

import org.apache.commons.lang.StringUtils;

import common.Constants;

import descripcion.model.xml.card.Area;
import descripcion.model.xml.card.Elemento;
import descripcion.model.xml.card.ElementoCabecera;
import descripcion.model.xml.card.ElementoEtiquetaDato;
import descripcion.model.xml.card.ElementoTabla;
import descripcion.model.xml.card.Etiqueta;
import descripcion.model.xml.card.Ficha;
import descripcion.model.xml.card.TagsFicha;
import descripcion.model.xml.card.Valor;

/**
 * Utilidad para generar la ficha ISAD(G) de un documento antecedente de un
 * tercero.
 */
public class IsadgHelper {

	/**
	 * Compone el XML de la ficha ISAD(G).
	 * 
	 * @param ficha
	 *            Información de la ficha ISAD(G).
	 * @return XML de la ficha.
	 */
	public static String toString(Ficha ficha) {
		final StringBuffer xml = new StringBuffer();

		// XML
		xml.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
		xml.append(Constants.NEWLINE);

		// Tag de inicio
		xml.append("<ISADG>");
		xml.append(Constants.NEWLINE);

		// Áreas
		if (ficha != null && ficha.getTotalAreas() > 0) {
			xml.append("  <Areas>");
			xml.append(Constants.NEWLINE);

			for (int i = 0; i < ficha.getTotalAreas(); i++)
				xml.append(toString(ficha.getArea(i), 4));

			xml.append("  </Areas>");
			xml.append(Constants.NEWLINE);
		}

		// Tag de cierre
		xml.append("</ISADG>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

	protected static String toString(Area area, int indent) {
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		// Área
		xml.append(tabs + "<Area>");
		xml.append(Constants.NEWLINE);

		// Titulo
		xml.append(toString(area.getEtiqueta(), indent + 2));

		// Elementos
		if (area.getTotalElementos() > 0) {
			xml.append(tabs + "  <Elementos>");
			xml.append(Constants.NEWLINE);

			for (int i = 0; i < area.getTotalElementos(); i++)
				xml.append(toString(area.getElemento(i), indent + 4));

			xml.append(tabs + "  </Elementos>");
			xml.append(Constants.NEWLINE);
		}

		xml.append(tabs + "</" + TagsFicha.TAG_AREA + ">");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

	protected static String toString(Elemento elemento, int indent) {
		if (elemento instanceof ElementoEtiquetaDato)
			return toString((ElementoEtiquetaDato) elemento, indent);
		else if (elemento instanceof ElementoCabecera)
			return toString((ElementoCabecera) elemento, indent);
		else if (elemento instanceof ElementoTabla)
			return toString((ElementoTabla) elemento, indent);
		else
			return "";
	}

	protected static String toString(ElementoTabla tabla, int indent) {
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		// Tag de inicio
		xml.append(tabs + "<Elemento Tipo=\"Tabla\">");
		xml.append(Constants.NEWLINE);

		// Titulo
		xml.append(toString(tabla.getEtiqueta(), indent + 2));

		// Mostrar_Cabeceras
		if (!tabla.isMostrarCabeceras()) {
			xml.append(tabs + "  <" + TagsFicha.TAG_MOSTRAR_CABECERAS + ">");
			xml.append(Constants.FALSE_STRING);
			xml.append("</" + TagsFicha.TAG_MOSTRAR_CABECERAS + ">");
			xml.append(Constants.NEWLINE);
		}

		if (tabla.getTotalElementos() > 0) {
			// Inicio del tag Elementos_Tabla
			xml.append(tabs + "  <Elementos_Tabla>");
			xml.append(Constants.NEWLINE);

			// Elementos
			for (int i = 0; i < tabla.getTotalElementos(); i++)
				xml.append(toString(tabla.getElemento(i), indent + 4));

			// Cierre del tag Elementos_Tabla
			xml.append(tabs + "  </Elementos_Tabla>");
			xml.append(Constants.NEWLINE);
		}

		// Tag de cierre
		xml.append(tabs + "</Elemento>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

	protected static String toString(ElementoCabecera cabecera, int indent) {
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		// Tag de inicio
		xml.append(tabs + "<Elemento Tipo=\"Cabecera\">");
		xml.append(Constants.NEWLINE);

		// Titulo
		xml.append(toString(cabecera.getEtiqueta(), indent + 2));

		if (cabecera.getTotalElementos() > 0) {
			// Inicio del tag Elementos_Cabecera
			xml.append(tabs + "  <Elementos_Cabecera>");
			xml.append(Constants.NEWLINE);

			// Elementos
			for (int i = 0; i < cabecera.getTotalElementos(); i++)
				xml.append(toString(cabecera.getElemento(i), indent + 4));

			// Cierre del tag Elementos_Cabecera
			xml.append(tabs + "  </Elementos_Cabecera>");
			xml.append(Constants.NEWLINE);
		}

		// Tag de cierre
		xml.append(tabs + "</Elemento>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

	protected static String toString(ElementoEtiquetaDato dato, int indent) {
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		// Tag de inicio
		xml.append(tabs + "<Elemento Tipo=\"Dato\">");
		xml.append(Constants.NEWLINE);

		// Titulo
		xml.append(toString(dato.getEtiqueta(), indent + 2));

		if (dato.getTotalValores() > 0) {
			// Valores
			xml.append(tabs + "  <Valores>");
			xml.append(Constants.NEWLINE);

			// Valores
			for (int i = 0; i < dato.getTotalValores(); i++)
				xml.append(toString(dato.getValor(i), indent + 4));

			// Cierre del tag Valores
			xml.append(tabs + "  </Valores>");
			xml.append(Constants.NEWLINE);
		}

		// Tag de cierre
		xml.append(tabs + "</Elemento>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

	protected static String toString(Valor valor, int indent) {
		final StringBuffer xml = new StringBuffer();
		final String tabs = StringUtils.repeat(" ", indent);

		xml.append(tabs + "<Valor>");
		if (valor.getValor() != null)
			xml.append(valor.getValor());
		xml.append("</Valor>");
		xml.append(Constants.NEWLINE);

		return xml.toString();
	}

	protected static String toString(Etiqueta etiqueta, int indent) {
		final StringBuffer xml = new StringBuffer();

		if (etiqueta != null && StringUtils.isNotBlank(etiqueta.getTitulo())) {
			final String tabs = StringUtils.repeat(" ", indent);

			xml.append(tabs + "<Titulo>");
			xml.append(etiqueta.getTitulo());
			xml.append("</Titulo>");
			xml.append(Constants.NEWLINE);
		}

		return xml.toString();
	}

}
