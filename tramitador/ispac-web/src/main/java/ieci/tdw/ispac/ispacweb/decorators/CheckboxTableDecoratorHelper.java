package ieci.tdw.ispac.ispacweb.decorators;

import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletRequest;

/**
 * Utilidad para el decorador de tablas que muestra el checkbox.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class CheckboxTableDecoratorHelper {

	/**
	 * Obtiene la lista de identificadores seleccionados.
	 * 
	 * @param request
	 *            Request.
	 * @param fieldName
	 *            Nombre del checkbox.
	 * @return Lista de identificadores seleccionados.
	 */
	public static List getCheckedIdList(ServletRequest request, String fieldName) {
		String params[] = request.getParameterValues(fieldName);
		return (params == null ? ((List) (new ArrayList(0)))
				: ((List) (new ArrayList(Arrays.asList(params)))));
	}

	/**
	 * Obtiene el código HTML para pintar el checkbox
	 * @param checkedIdList Lista de identificadores seleccionados.
	 * @param fieldName Nombre del checkbox.
	 * @param id Identificador del checkbox.
	 * @return Código HTML del checkbox.
	 */
	public static String getCheckbox(List checkedIdList, String fieldName,
			String id) {

		StringBuffer buffer = new StringBuffer();
		
		if (StringUtils.isNotBlank(id)) {
			buffer.append("<input type=\"checkbox\" name=\"");
			buffer.append(fieldName);
			buffer.append("\" value=\"");
			buffer.append(id);
			buffer.append("\"");
			if (!CollectionUtils.isEmpty(checkedIdList)
					&& checkedIdList.contains(id)) {
				checkedIdList.remove(id);
				buffer.append(" checked=\"checked\"");
			}
			buffer.append("/>");
		}

		return buffer.toString();
	}

	/**
	 * Obtiene el código HTML para los campos ocultos con los identificadores seleccionados no visibles.
	 * @param checkedIdList Lista de identificadores seleccionados.
	 * @param fieldName Nombre del checkbox.
	 */
	public static String getHiddenFields(List checkedIdList, String fieldName) {

		StringBuffer buffer = new StringBuffer();

		if (!CollectionUtils.isEmpty(checkedIdList)) {
			for (Iterator it = checkedIdList.iterator(); it.hasNext();) {
				buffer.append("<input type=\"hidden\" name=\"");
				buffer.append(fieldName);
				buffer.append("\" value=\"");
				buffer.append((String) it.next());
				buffer.append("\">");
			}
		}

		return buffer.toString();
	}
}
