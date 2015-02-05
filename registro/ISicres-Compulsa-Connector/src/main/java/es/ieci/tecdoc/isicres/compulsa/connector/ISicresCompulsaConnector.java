package es.ieci.tecdoc.isicres.compulsa.connector;

import es.ieci.tecdoc.isicres.compulsa.connector.vo.ISicresAbstractCompulsaVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

public interface ISicresCompulsaConnector {

	/**
	 * Método para compulsar documentos
	 *
	 * @param iSicresAbstractCompulsaVO
	 * @return
	 */
	public ISicresAbstractCompulsaVO compulsar(
			ISicresAbstractCompulsaVO iSicresAbstractCompulsaVO);

	/**
	 * Método para generar el localizador del documento
	 *
	 * @param compulsaVO
	 * @return
	 */
	public ISicresAbstractCompulsaVO generateLocator(
			ISicresAbstractCompulsaVO compulsaVO);

	/**
	 * Método para generar el fichero .xades necesario para la compulsa
	 *
	 * @param compulsaVO
	 */
	public void generateXadesDocument(ISicresAbstractCompulsaVO compulsaVO);

}
