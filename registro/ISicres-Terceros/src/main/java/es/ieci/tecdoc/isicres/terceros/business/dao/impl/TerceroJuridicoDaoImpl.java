package es.ieci.tecdoc.isicres.terceros.business.dao.impl;

import java.util.HashMap;
import java.util.List;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.isicres.terceros.business.dao.TerceroJuridicoDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoJuridicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.CriteriaVO;

/**
 *
 * @author IECISA
 *
 */
public class TerceroJuridicoDaoImpl extends
		IbatisGenericDaoImpl<TerceroValidadoJuridicoVO, String> implements
		TerceroJuridicoDao {

	public TerceroJuridicoDaoImpl(
			Class<TerceroValidadoJuridicoVO> aPersistentClass) {
		super(aPersistentClass);
	}

	public List<TerceroValidadoVO> findByCriteria(CriteriaVO criteria) {
		return getSqlMapClientTemplate().queryForList(
				"TerceroValidadoJuridicoVO.findByCriteria", criteria);
	}

	public Integer countByCriteria(CriteriaVO criteria) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				"TerceroValidadoJuridicoVO.countByCriteria", criteria);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.terceros.business.dao.TerceroJuridicoDao#findTerceroJuridicoByDocumentNumber(java.lang.String, int)
	 */
	public List<TerceroValidadoFisicoVO> findTerceroJuridicoByDocumentNumber(String phisicalNumber,
			int typeDoc) {
		HashMap map = new HashMap();
		map.put("type_doc", typeDoc);
		map.put("nif",phisicalNumber);
		return getSqlMapClientTemplate().queryForList("TerceroValidadoJuridicoVO.findByDocumentNumber",map);
	}
	
	
}
