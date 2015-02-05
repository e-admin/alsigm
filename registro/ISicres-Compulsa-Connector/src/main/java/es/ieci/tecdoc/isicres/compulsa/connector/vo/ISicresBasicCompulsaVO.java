/**
 *
 */
package es.ieci.tecdoc.isicres.compulsa.connector.vo;

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
public class ISicresBasicCompulsaVO extends ISicresAbstractCompulsaVO {

	/**
	 *
	 */
	public ISicresBasicCompulsaVO() {
		super();
		this.datosEspecificos = new ISicresBasicDatosEspecificosCompulsaVO();
	}

}
