package es.ieci.tecdoc.isicres.terceros.business.dao;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.CriteriaVO;

public interface TerceroFisicoDao extends
		BaseDao<TerceroValidadoFisicoVO, String> {

	/**
	 *
	 * @param criteria
	 * @return
	 */
	List<TerceroValidadoVO> findByCriteria(CriteriaVO criteria);

	/**
	 *
	 * @param criteria
	 * @return
	 */
	Integer countByCriteria(CriteriaVO criteria);

	/**
	 * Devuelve un listado de terceros físicos a partir del tipo de documento y el número de documento
	 * 
	 * @param phisicalNumber
	 * @param typeDoc
	 * @return
	 */
	public List<TerceroValidadoFisicoVO> findTerceroFisicoByDocumentNumber(String phisicalNumber, int typeDoc);

}
