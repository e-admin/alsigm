/**
 * 
 */
package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.ArrayOfWSStakeholder;
import es.ieci.tecdoc.isicres.ws.legacy.service.thirdparties.WSStakeholder;

/**
 * @author IECISA
 * @version $Revision$
 *
 */
public class ListOfInteresadoVOToArrayOfWSStakeholderMapper implements Mapper{

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper#map(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(List.class, obj);
		List<InteresadoVO> interesados = (List<InteresadoVO>)obj;
		
		ArrayOfWSStakeholder arrayOfWSStakeholder = new ArrayOfWSStakeholder();
		InteresadoVOToWSStakeholderMapper mapper = new InteresadoVOToWSStakeholderMapper();
		for(InteresadoVO interesado:interesados){
						
			WSStakeholder wsStakeholder = (WSStakeholder) mapper.map(interesado);
			arrayOfWSStakeholder.getArrayOfWSStakeholder().add(wsStakeholder);
		}
		
		return arrayOfWSStakeholder;
	}

}
