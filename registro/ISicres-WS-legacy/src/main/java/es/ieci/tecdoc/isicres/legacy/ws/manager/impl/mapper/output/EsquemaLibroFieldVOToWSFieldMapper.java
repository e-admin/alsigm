package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.EsquemaLibroFieldVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.WSField;

/**
 * Instancia de <code>Mapper</code> que transforma una instancia de
 * <code>EsquemaLibroFieldVO</code> en una de <code>WSField</code>.
 * 
 * @see EsquemaLibroFieldVO
 * @see WSField
 * 
 * @author IECISA
 * 
 */
public class EsquemaLibroFieldVOToWSFieldMapper implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(EsquemaLibroFieldVO.class, obj);

		EsquemaLibroFieldVO field = (EsquemaLibroFieldVO) obj;

		WSField result = new WSField();
		result.setId(new Integer(field.getId()));
		result.setLabel(field.getLabel());
		result.setLength(field.getLength());
		result.setName(field.getName());
		/*
		 * TODO: La propiedad Type de la clase WSField, en plataforma Microsoft
		 * devuelve valores como chaR, datetime, number, etc. en la plataforma
		 * JAVA se devuelve valor numérico que suponemos es la equivalencia.
		 */
		EsquemaLibroFieldVOTipoToWSFieldTypeMapper mapper = new EsquemaLibroFieldVOTipoToWSFieldTypeMapper();
		String type = (String) mapper.map(field.getTipo());
		result.setType(type);
		result.setHasValidation(field.isHasValidation());

		return result;
	}

}
