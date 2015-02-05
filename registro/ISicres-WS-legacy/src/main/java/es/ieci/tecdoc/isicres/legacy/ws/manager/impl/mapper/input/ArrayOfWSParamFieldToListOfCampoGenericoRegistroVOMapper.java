package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.CampoGenericoRegistroVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamField;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamField;

/**
 * Instancia de <code>Mapper</code> que transforma una instancia de
 * <code>ArrayOfWSParamField</code> en una <code>List</code> de
 * <code>CampoGenericoRegistroVO</code>
 * 
 * @see ArrayOfWSParamField
 * @see CampoGenericoRegistroVO
 * 
 * @author IECISA
 * 
 */
public class ArrayOfWSParamFieldToListOfCampoGenericoRegistroVOMapper implements
		Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(ArrayOfWSParamField.class, obj);

		ArrayOfWSParamField fields = (ArrayOfWSParamField) obj;

		List<CampoGenericoRegistroVO> result = new ArrayList<CampoGenericoRegistroVO>();

		for (WSParamField field : fields.getWSParamField()) {
			result
					.add((CampoGenericoRegistroVO) new WSParamFieldToCampoGenericoRegistroVOMapper()
							.map(field));
		}

		return result;
	}
}
