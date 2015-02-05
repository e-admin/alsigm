package es.ieci.tecdoc.isicres.terceros.business.dao.impl;

import java.util.ArrayList;
import java.util.List;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.isicres.terceros.business.dao.TerceroDao;
import es.ieci.tecdoc.isicres.terceros.business.dao.TerceroFisicoDao;
import es.ieci.tecdoc.isicres.terceros.business.dao.TerceroJuridicoDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoJuridicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.CriteriaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.SearchType;

/**
 *
 * @author IECISA
 *
 */
public class TerceroDaoImpl extends
		IbatisGenericDaoImpl<TerceroValidadoVO, String> implements TerceroDao {

	public TerceroDaoImpl(Class<TerceroValidadoVO> aPersistentClass) {
		super(aPersistentClass);
	}

	public void delete(String anId) {
		BaseTerceroVO tercero = get(anId);

		if (tercero instanceof TerceroValidadoFisicoVO) {
			getTerceroFisicoDao().delete(anId);
		} else if (tercero instanceof TerceroValidadoJuridicoVO) {
			getTerceroJuridicoDao().delete(anId);
		}
	}

	public void deleteAll() {
		getTerceroFisicoDao().deleteAll();
		getTerceroJuridicoDao().deleteAll();
	}

	public TerceroValidadoVO save(TerceroValidadoVO anEntity) {
		if (anEntity instanceof TerceroValidadoFisicoVO) {
			anEntity = getTerceroFisicoDao().save(
					(TerceroValidadoFisicoVO) anEntity);
		} else if (anEntity instanceof TerceroValidadoJuridicoVO) {
			anEntity = getTerceroJuridicoDao().save(
					(TerceroValidadoJuridicoVO) anEntity);
		}
		return anEntity;
	}

	public TerceroValidadoVO update(TerceroValidadoVO anEntity) {
		if (anEntity instanceof TerceroValidadoFisicoVO) {
			anEntity = getTerceroFisicoDao().update(
					(TerceroValidadoFisicoVO) anEntity);
		} else if (anEntity instanceof TerceroValidadoJuridicoVO) {
			anEntity = getTerceroJuridicoDao().update(
					(TerceroValidadoJuridicoVO) anEntity);
		}
		return anEntity;
	}

	public List<TerceroValidadoFisicoVO> getAllTercerosFisicos() {
		return getTerceroFisicoDao().getAll();
	}

	public List<TerceroValidadoJuridicoVO> getAllTercerosJuridicos() {
		return getTerceroJuridicoDao().getAll();
	}

	public List<TerceroValidadoVO> findByCriteria(CriteriaVO criteria) {
		switch (criteria.getType().getValue()) {
		case SearchType.FISICO_VALUE:
			return getTerceroFisicoDao().findByCriteria(criteria);
		case SearchType.JURIDICO_VALUE:
			return getTerceroJuridicoDao().findByCriteria(criteria);
		default:
			return new ArrayList<TerceroValidadoVO>();
		}
	}

	public Integer countByCriteria(CriteriaVO criteria) {
		switch (criteria.getType().getValue()) {
		case SearchType.FISICO_VALUE:
			return getTerceroFisicoDao().countByCriteria(criteria);
		case SearchType.JURIDICO_VALUE:
			return getTerceroJuridicoDao().countByCriteria(criteria);
		default:
			return 0;
		}
	}

	

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.terceros.business.dao.TerceroDao#findTerceroJuridicoByDocumentNumber(java.lang.String, int)
	 */
	public List<TerceroValidadoFisicoVO> findTerceroJuridicoByDocumentNumber(String phisicalNumber,
			int typeDoc) {
		
		return terceroJuridicoDao.findTerceroJuridicoByDocumentNumber(phisicalNumber, typeDoc);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.terceros.business.dao.TerceroDao#findTerceroFisicoByDocumentNumber(java.lang.String, int)
	 */
	public List<TerceroValidadoFisicoVO> findTerceroFisicoByDocumentNumber(String phisicalNumber,
			int typeDoc) {
		
		return terceroFisicoDao.findTerceroFisicoByDocumentNumber(phisicalNumber, typeDoc);
	}

	public TerceroFisicoDao getTerceroFisicoDao() {
		return terceroFisicoDao;
	}

	public void setTerceroFisicoDao(TerceroFisicoDao terceroFisicoDao) {
		this.terceroFisicoDao = terceroFisicoDao;
	}

	public TerceroJuridicoDao getTerceroJuridicoDao() {
		return terceroJuridicoDao;
	}

	public void setTerceroJuridicoDao(TerceroJuridicoDao terceroJuridicoDao) {
		this.terceroJuridicoDao = terceroJuridicoDao;
	}

	// Members
	protected TerceroFisicoDao terceroFisicoDao;

	protected TerceroJuridicoDao terceroJuridicoDao;

}
