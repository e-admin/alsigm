package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.CampoAdicionalRegistroVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSAddField;

/**
 * Instancia de <code>Mapper</code> que transforma una instancia de
 * <code>WSAddField</code> en una de <code>CampoAdicionalRegistro</code>.
 * 
 * @see WSAddField
 * @see CampoAdicionalRegistroVO
 * 
 * @author IECISA
 * 
 */
public class WSAddFieldToCampoAdicionalRegistroMapper implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(WSAddField.class, obj);

		WSAddField field = (WSAddField) obj;

		CampoAdicionalRegistroVO result = new CampoAdicionalRegistroVO();
		result.setName(String.valueOf(field.getFieldId()));
		// TODO: Intentar descubrir el tipo del Object para traducir
		// correctamente el toString()
		result.setValue(field.getValue().toString());

		return result;
	}

}
