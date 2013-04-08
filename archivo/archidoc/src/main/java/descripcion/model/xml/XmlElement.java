package descripcion.model.xml;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * Clase abstracta que deben extender todas las clases que contengan información
 * de documentos XML.
 */
public abstract class XmlElement implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final String CDATA_INI = "<![CDATA[";
	public static final String CDATA_FIN = "]]>";

	/**
	 * Obtiene una representación del objeto.
	 * 
	 * @return Representación del objeto.
	 */
	public String toString() {
		return toXML(0);
	}

	/**
	 * Obtiene una representación XML del objeto.
	 * 
	 * @param indent
	 *            Número de espacios de tabulación.
	 * @return Representación del objeto.
	 */
	public abstract String toXML(int indent);

	/**
	 * Obtiene el contenido de un tag XML dentro de un CDATA.
	 * 
	 * @param content
	 *            Contenido del tag.
	 * @return Contenido dentro de un CDATA.
	 */
	public static String getCDataContent(String content) {
		StringBuffer cadena = new StringBuffer();
		if (StringUtils.isNotBlank(content)) {
			cadena.append(XmlElement.CDATA_INI).append(content)
					.append(XmlElement.CDATA_FIN);
		}

		return cadena.toString();
	}
}
