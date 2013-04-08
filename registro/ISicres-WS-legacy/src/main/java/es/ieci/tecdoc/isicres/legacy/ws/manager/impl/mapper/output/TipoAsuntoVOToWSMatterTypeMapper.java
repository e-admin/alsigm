package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.TipoAsuntoVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.matters.WSMatterType;

/**
 * Instancia de <code>Mapper</code> que transforma un objeto de tipo
 * <code>TipoAsuntoVO</code> en uno de tipo <code>WsMatterType</code>.
 * 
 * @see TipoAsuntoVO
 * @see WSMatterType
 * 
 * @author IECISA
 * 
 */
public class TipoAsuntoVOToWSMatterTypeMapper implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(TipoAsuntoVO.class, obj);

		TipoAsuntoVO tipoAsunto = (TipoAsuntoVO) obj;

		WSMatterType result = new WSMatterType();

		result.setCode(tipoAsunto.getCodigo());
		result.setEnabled(tipoAsunto.isHabilitado());
		result.setInputMatterType(tipoAsunto.isDisponibleLibroEntrada());
		result.setOutputMatterType(tipoAsunto.isDisponibleLibroSalida());
		result.setId(Long.parseLong(tipoAsunto.getId()));
		result.setName(tipoAsunto.getDescripcion());

		return result;
	}

}
