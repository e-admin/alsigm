/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.manager.impl.mapper;

import com.ieci.tecdoc.common.invesdoc.Iuserdepthdr;

import es.ieci.tecdoc.isicres.api.business.vo.BaseDepartamentoVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class BaseDepartamentoVOMapper {

	public BaseDepartamentoVO map(Iuserdepthdr iuserdepthdr) {
		BaseDepartamentoVO departamentoVO = null;

		if (iuserdepthdr != null) {

			departamentoVO = new BaseDepartamentoVO();
			departamentoVO.setDescripcion(iuserdepthdr.getRemarks());
			departamentoVO.setName(iuserdepthdr.getName());
			departamentoVO.setId(String.valueOf(iuserdepthdr.getId()));

		}

		return departamentoVO;

	}

}
