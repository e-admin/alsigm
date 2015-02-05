package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.CampoGenericoRegistroVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamField;

/**
 * Instancia de <code>Mapper</code> que transforma un objeto de tipo
 * <code>WSParamField</code> en uno de tipo <code>CampoGenericoRegistroVO</code>
 * .
 * 
 * @see WSParamField
 * @see CampoGenericoRegistroVO
 * 
 * @author IECISA
 * 
 */
public class WSParamFieldToCampoGenericoRegistroVOMapper implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(WSParamField.class, obj);

		WSParamField field = (WSParamField) obj;

		CampoGenericoRegistroVO result = new CampoGenericoRegistroVO();
		result.setName(String.valueOf(field.getFldId()));
		result.setValue(field.getValue());

		field.getIndex(); // TODO: ¿destinatarios con fld9?

		return result;
	}

}
