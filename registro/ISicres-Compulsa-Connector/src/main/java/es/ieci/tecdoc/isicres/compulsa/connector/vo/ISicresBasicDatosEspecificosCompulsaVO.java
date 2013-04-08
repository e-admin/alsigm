/**
 *
 */
package es.ieci.tecdoc.isicres.compulsa.connector.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 */
public class ISicresBasicDatosEspecificosCompulsaVO extends
		ISicresAbstractDatosEspecificosCompulsaVO {

	/**
	 * Mapa de valores donde:
	 *
	 * - Key es el nombre de la variable
	 *
	 * - Values es el valor de la variable
	 */
	protected Map values;

	/**
	 *
	 */
	public ISicresBasicDatosEspecificosCompulsaVO() {
		values = new HashMap();
	}

	/**
	 * @return el values
	 */
	public Map getValues() {
		return values;
	}

	/**
	 * @param values
	 *            el values a fijar
	 */
	public void setValues(Map values) {
		this.values = values;
	}

}
