/**
 *
 */
package salas.vos;

import util.TreeModelItem;

import common.vos.BaseVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public abstract class SalasConsultaVOBase extends BaseVO implements
		TreeModelItem {

	/**
	 * Obtiene el Tipo del Elemento 1.Edificio 2.Sala 3. Mesa
	 * 
	 * @return
	 */
	public abstract String getTipo();

	/**
	 * Obtiene el identificador del elemento
	 * 
	 * @return Identificador del Elemento
	 */
	public abstract String getId();

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return
	 */
	public abstract String getPathName();
}
