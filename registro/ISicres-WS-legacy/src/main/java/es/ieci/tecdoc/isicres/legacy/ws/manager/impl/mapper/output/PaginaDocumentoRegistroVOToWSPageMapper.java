package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.PaginaDocumentoRegistroVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSPage;

/**
 * Instancia de <code>Mapper</code> que transforma objetos de tipo
 * <code>PaginaDocumentoRegistroVO</code> en objetos de tipo <code>WSPage</code>
 * .
 *
 * @see PaginaDocumentoRegistroVO
 * @see WSPage
 *
 * @author IECISA
 *
 */
public class PaginaDocumentoRegistroVOToWSPageMapper implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(PaginaDocumentoRegistroVO.class, obj);

		PaginaDocumentoRegistroVO page = (PaginaDocumentoRegistroVO) obj;

		WSPage result = new WSPage();
		result.setId(Integer.valueOf(page.getId()));
		result.setLocator(page.getDocumentoFisico().getLocation());
		result.setName(page.getName());

		return result;
	}

}
