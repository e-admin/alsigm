package es.ieci.tecdoc.isicres.api.business.manager.impl.mapper;

import com.ieci.tecdoc.common.invesicres.ScrOfic;

import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;

/**
 * Clase que instancia un objeto de tipo <code>OficinaVO</code> con los
 * atributos de otro de tipo <code>ScrOfic</code>.
 * 
 * @see OficinaVO
 * @see ScrOfic
 * 
 * @author IECISA
 * 
 */
public class ScrOficToOficinaVOMapper {

	public OficinaVO map(ScrOfic scrOfic) {

		OficinaVO result = new OficinaVO();

		result.setId(scrOfic.getId().toString());
		result.setCodigoOficina(scrOfic.getCode());
		result.setAcronimoOficina(scrOfic.getAcron());
		result.setName(scrOfic.getName());
		result.setIdDepartamento(String.valueOf(scrOfic.getDeptid()));

		return result;
	}
}
