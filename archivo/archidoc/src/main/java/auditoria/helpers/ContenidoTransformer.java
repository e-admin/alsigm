package auditoria.helpers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import auditoria.ContenidoTags;

/**
 * Helper que se encarga de transformar un hash de tipos en el XML para insertar
 * en el contenido de los datos de una traza o vicerversa.
 */
public class ContenidoTransformer {

	/**
	 * Transforma un hash en el xml
	 * 
	 * @return XMl generado a partir del hash.
	 */
	public static String fromHashToString(Map map) {
		String type = null;
		String value = null;
		String resultado = null;
		StringBuffer contenido = null;

		if (map != null) {
			contenido = new StringBuffer();
			contenido.append(ContenidoTags.DETAILS_OPEN_TAG);

			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				type = (String) it.next();

				value = (String) map.get(type);

				contenido.append(ContenidoTags.DETAIL_OPEN_TAG);

				contenido.append(ContenidoTags.TYPE_OPEN_TAG);
				contenido.append(type);
				contenido.append(ContenidoTags.TYPE_CLOSE_TAG);

				contenido.append(ContenidoTags.VALUE_OPEN_TAG);
				contenido.append(value);
				contenido.append(ContenidoTags.VALUE_CLOSE_TAG);

				contenido.append(ContenidoTags.DETAIL_CLOSE_TAG);
			}

			contenido.append(ContenidoTags.DETAILS_CLOSE_TAG);
			resultado = contenido.toString();
		}

		return resultado;
	}

	/**
	 * Transforma un xml en has
	 * 
	 * @return Hash generado a partir del xml.
	 */
	public static HashMap fromStringToHash(String xml) {
		return null;
	}
}
