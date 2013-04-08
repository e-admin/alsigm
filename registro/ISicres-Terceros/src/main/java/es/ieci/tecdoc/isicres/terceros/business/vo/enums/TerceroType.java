/**
 * 
 */
package es.ieci.tecdoc.isicres.terceros.business.vo.enums;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class TerceroType extends ValuedEnum {

	public static final int FISICO_VALUE = 0;

	public static final int JURIDICO_VALUE = 1;

	public static final TerceroType FISICO = new TerceroType("FISICO", FISICO_VALUE);

	public static final TerceroType JURIDICO = new TerceroType("JURIDICO", JURIDICO_VALUE);

	/**
	 * @param name
	 * @param value
	 */
	protected TerceroType(String name, int value) {
		super(name, value);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1084386252242304449L;

}
