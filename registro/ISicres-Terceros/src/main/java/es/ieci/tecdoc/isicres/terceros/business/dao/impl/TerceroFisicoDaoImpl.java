package es.ieci.tecdoc.isicres.terceros.business.dao.impl;

import java.util.HashMap;
import java.util.List;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.isicres.terceros.business.dao.TerceroFisicoDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.CriteriaVO;

/**
 *
 * @author IECISA
 *
 */
public class TerceroFisicoDaoImpl extends
		IbatisGenericDaoImpl<TerceroValidadoFisicoVO, String> implements
		TerceroFisicoDao {

	public TerceroFisicoDaoImpl(Class<TerceroValidadoFisicoVO> aPersistentClass) {
		super(aPersistentClass);
	}

	@SuppressWarnings("unchecked")
	public List<TerceroValidadoVO> findByCriteria(CriteriaVO criteria) {
		return getSqlMapClientTemplate().queryForList(
				"TerceroValidadoFisicoVO.findByCriteria", criteria);
	}

	public Integer countByCriteria(CriteriaVO criteria) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"TerceroValidadoFisicoVO.countByCriteria", criteria);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.terceros.business.dao.TerceroFisicoDao#findTerceroFisicoByDocumentNumber(java.lang.String, int)
	 */
	public List<TerceroValidadoFisicoVO> findTerceroFisicoByDocumentNumber(String phisicalNumber,
			int typeDoc) {
		HashMap map = new HashMap();
		map.put("type_doc", typeDoc);
		map.put("nif",phisicalNumber);
		return getSqlMapClientTemplate().queryForList("TerceroValidadoFisicoVO.findByDocumentNumber",map);
	}
	
	

}
