/**
 *
 */
package fondos.db;

import java.util.List;

import se.usuarios.ServiceClient;
import fondos.vos.BusquedaUdocsSerieVO;
import fondos.vos.ElementoCuadroClasificacionVistaVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public interface IElementoCuadroClasificacionVistaDBEntity {

	public ElementoCuadroClasificacionVistaVO getElementoById(
			String idelementocf);

	public List getUnidadesDocumentalesSerie(ServiceClient serviceClient,
			String idSerie, BusquedaUdocsSerieVO busquedaUdocsSerieVO);
}
