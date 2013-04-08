/**
 * 
 */
package es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Clase para manejar la lista de objetos rellenada por la clase
 * GenericResultMapTypeHandlerCallback.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class GenericResultMap {

	private List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

	public List<Map<String, Object>> getResult() {
		return result;
	}

	public void setResult(List<Map<String, Object>> result) {
		this.result = result;
	}
}
