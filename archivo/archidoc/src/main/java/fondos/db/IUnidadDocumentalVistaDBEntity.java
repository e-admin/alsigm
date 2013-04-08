/**
 *
 */
package fondos.db;

import java.util.List;

import se.usuarios.ServiceClient;
import fondos.vos.BusquedaUdocsSerieVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public interface IUnidadDocumentalVistaDBEntity {

	/**
	 * 
	 * @param serviceClient
	 * @param idSerie
	 * @param busquedaUdocsSerieVO
	 * @return
	 */
	public List getUnidadesDocumentalesSerie(ServiceClient serviceClient,
			String idSerie, BusquedaUdocsSerieVO busquedaUdocsSerieVO);
}
