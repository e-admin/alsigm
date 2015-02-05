package es.ieci.tecdoc.isicres.api.business.manager.impl.mapper;

import com.ieci.tecdoc.common.utils.ScrRegisterInter;

import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;

/**
 * Clase que se encarga de mapear las propiedades de objetos de tipo
 * <code>ScrRegisterInter</code> en objetos de tipo <code>BaseTerceroVO</code>.
 *
 * @author IECISA
 *
 */
public class ScrRegisterInterToInteresadoVOMapper {

	public InteresadoVO map(ScrRegisterInter scrRegisterInter) {
		InteresadoVO result = new InteresadoVO();

		result.setId(scrRegisterInter.getId().toString());

		boolean interPrincipal = false;
		if (scrRegisterInter.getOrder().intValue() == 1) {
			interPrincipal = true;
		}
		result.setPrincipal(interPrincipal);

		result.setNombre(scrRegisterInter.getName());
		result.setOrden(scrRegisterInter.getOrder());

		return result;
	}

}
