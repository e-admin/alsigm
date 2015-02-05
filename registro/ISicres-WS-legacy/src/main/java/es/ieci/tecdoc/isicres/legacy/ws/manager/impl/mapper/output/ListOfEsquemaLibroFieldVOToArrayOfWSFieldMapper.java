package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.EsquemaLibroFieldVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.ArrayOfWSField;
import es.ieci.tecdoc.isicres.ws.legacy.service.books.WSField;

/**
 * Instancia de <code>Mapper</code> que transforma una <code>List</code> de
 * <code>EsquemaLibroFieldVO</code> en una instancia de
 * <code>ArrayOfWSField</code>.
 * 
 * @see EsquemaLibroFieldVO
 * @see ArrayOfWSField
 * 
 * @author IECISA
 * 
 */
public class ListOfEsquemaLibroFieldVOToArrayOfWSFieldMapper implements Mapper {

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(List.class, obj);

		List<EsquemaLibroFieldVO> campos = (List<EsquemaLibroFieldVO>) obj;

		ArrayOfWSField result = new ArrayOfWSField();

		for (EsquemaLibroFieldVO campo : campos) {
			result.getWSField().add(
					(WSField) new EsquemaLibroFieldVOToWSFieldMapper()
							.map(campo));
		}

		return result;
	}

}
