/**
 * 
 */
package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoJuridicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.enums.TerceroType;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSStakeholder;

/**
 * @author IECISA
 * @version $Revision$
 * 
 */
public class InteresadoVOToWSStakeholderMapper implements Mapper {

	/**
	 * {@inheritDoc}
	 * 
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper#map(java.lang.Object)
	 */
	public Object map(Object obj) {
		Assert.isInstanceOf(InteresadoVO.class, obj);
		InteresadoVO interesado = (InteresadoVO) obj;
		WSStakeholder wsStakeholder = new WSStakeholder();
		wsStakeholder.setBookId(Long.valueOf(interesado.getIdLibro()));
		wsStakeholder.setFolderId(Long.valueOf(interesado.getIdRegistro()));
		wsStakeholder.setId(Long.valueOf(interesado.getId()));
		// Puede ocurrir que un interesado no tenga dirección de notificación
		if (interesado.getDireccionNotificacion() != null) {
			wsStakeholder.setIdAddress(Long.valueOf(interesado.getDireccionNotificacion().getId()));
		}
		wsStakeholder.setIdThirdParty(Long.valueOf(interesado.getTercero().getId()));
		wsStakeholder.setName(interesado.getNombre());
		wsStakeholder.setOrd(interesado.getOrden());
		wsStakeholder.setPreferred(interesado.isPrincipal());
		

		//El tipo indica si es físico o jurídico
		BaseTerceroVO tercero = interesado.getTercero();
		if (tercero != null) {
			if (tercero instanceof TerceroValidadoFisicoVO) {
				wsStakeholder.setTypeStakeholder(TerceroType.FISICO_VALUE);
			} else if (tercero instanceof TerceroValidadoJuridicoVO){
				wsStakeholder.setTypeStakeholder(TerceroType.JURIDICO_VALUE);
			}
		}
		return wsStakeholder;
	}

}
