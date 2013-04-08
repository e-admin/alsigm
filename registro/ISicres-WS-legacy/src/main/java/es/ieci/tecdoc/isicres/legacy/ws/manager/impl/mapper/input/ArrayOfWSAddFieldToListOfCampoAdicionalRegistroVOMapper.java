package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.CampoAdicionalRegistroVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSAddField;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSAddField;

/**
 * Instancia de <code>Mapper</code> que transforma una instancia de
 * <code>ArrayOfWSAddField</code> en una <code>List</code> de
 * <code>CampoAdicionalRegistroVO</code>.
 * 
 * @see ArrayOfWSAddField
 * @see CampoAdicionalRegistroVO
 * 
 * @author IECISA
 * 
 */
public class ArrayOfWSAddFieldToListOfCampoAdicionalRegistroVOMapper implements
		Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(ArrayOfWSAddField.class, obj);

		ArrayOfWSAddField fields = (ArrayOfWSAddField) obj;

		List<CampoAdicionalRegistroVO> result = new ArrayList<CampoAdicionalRegistroVO>();

		for (WSAddField field : fields.getWSAddField()) {
			result
					.add((CampoAdicionalRegistroVO) new WSAddFieldToCampoAdicionalRegistroMapper()
							.map(field));
		}

		return result;
	}

}
