/**
 * 
 */
package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDocumentoIdentificativoTerceroVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSThirdPartyPhisical;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class WSThirdPartyPhisicalToTerceroValidadoFisicoVO implements Mapper{

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper#map(java.lang.Object)
	 */
	public Object map(Object obj) {
		Assert.isInstanceOf(WSThirdPartyPhisical.class, obj);
		WSThirdPartyPhisical wsThirdParty = (WSThirdPartyPhisical) obj;
		
		TerceroValidadoFisicoVO tercero = new TerceroValidadoFisicoVO();
		tercero.setApellido1(wsThirdParty.getSurname1());
		tercero.setApellido2(wsThirdParty.getSurname2());
		tercero.setNombre(wsThirdParty.getName());
		tercero.setNumeroDocumento(wsThirdParty.getDocumentNumber());
		TipoDocumentoIdentificativoTerceroVO tipoDoc = new TipoDocumentoIdentificativoTerceroVO();
		tipoDoc.setId(String.valueOf(wsThirdParty.getId()));
		tercero.setTipoDocumento(tipoDoc);
		return tercero;
	}

}
