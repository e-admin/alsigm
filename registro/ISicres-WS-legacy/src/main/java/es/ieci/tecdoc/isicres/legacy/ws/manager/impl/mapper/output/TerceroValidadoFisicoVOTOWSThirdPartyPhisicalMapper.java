/**
 * 
 */
package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSThirdPartyPhisical;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class TerceroValidadoFisicoVOTOWSThirdPartyPhisicalMapper implements Mapper{

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper#map(java.lang.Object)
	 */
	public Object map(Object obj) {
		Assert.isInstanceOf(TerceroValidadoFisicoVO.class, obj);
		
		TerceroValidadoFisicoVO tercero = (TerceroValidadoFisicoVO) obj;
		
		WSThirdPartyPhisical thirdPartyUpdated = new WSThirdPartyPhisical();
		
		thirdPartyUpdated.setDocumentNumber(tercero.getNumeroDocumento());
		thirdPartyUpdated.setDocumentType(Integer.valueOf(tercero.getTipoDocumento().getId()));
		thirdPartyUpdated.setId(Long.valueOf(tercero.getId()));
		thirdPartyUpdated.setName(tercero.getNombre());
		thirdPartyUpdated.setSurname1(tercero.getApellido1());
		thirdPartyUpdated.setSurname2(tercero.getApellido2());
		return thirdPartyUpdated;
	}

}
