package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.CampoAdicionalRegistroVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSAddField;

/**
 * Instancia de <code>Mapper</code> que transforma objetos de tipo
 * <code>CampoAdicionalRegistroVO</code> en objetos de tipo
 * <code>WSAddField</code>.
 * 
 * @see CampoAdicionalRegistroVO
 * @see WSAddField
 * 
 * @author IECISA
 * 
 */
public class CampoAdicionalRegistroVOToWSAddFieldMapper implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(CampoAdicionalRegistroVO.class, obj);

		CampoAdicionalRegistroVO campo = (CampoAdicionalRegistroVO) obj;

		WSAddField result = new WSAddField();
		result.setFieldId(Integer.valueOf(campo.getName()).intValue());
		result.setValue(campo.getValue());

		return result;
	}

}
