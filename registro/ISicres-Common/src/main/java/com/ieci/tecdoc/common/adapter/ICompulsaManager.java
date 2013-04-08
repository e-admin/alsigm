/**
 *
 */
package com.ieci.tecdoc.common.adapter;

import com.ieci.tecdoc.common.compulsa.vo.ISicresCreateCompulsaVO;
import com.ieci.tecdoc.common.compulsa.vo.ISicresReturnCompulsaVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 *          Interfaz de gestion para la compulsa de documentos
 */
public interface ICompulsaManager {

	/**
	 * Método que realiza la compulsa de documentos
	 *
	 * @param iSicresCreateCompulsaVO
	 * @return
	 */
	ISicresReturnCompulsaVO compulsarDocuments(
			ISicresCreateCompulsaVO iSicresCreateCompulsaVO);

}
