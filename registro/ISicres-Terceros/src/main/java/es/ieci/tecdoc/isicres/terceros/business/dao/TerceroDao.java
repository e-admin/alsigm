package es.ieci.tecdoc.isicres.terceros.business.dao;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoJuridicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.CriteriaVO;

public interface TerceroDao extends BaseDao<TerceroValidadoVO, String> {

	/**
	 * Devuelve todos los terceros físicos existentes.
	 *
	 * @return
	 */
	public List<TerceroValidadoFisicoVO> getAllTercerosFisicos();

	/**
	 * Devuelve todos los terceros jurídicos existentes.
	 *
	 * @return
	 */
	public List<TerceroValidadoJuridicoVO> getAllTercerosJuridicos();

	/**
	 *
	 * @param criteria
	 * @return
	 */
	public List<TerceroValidadoVO> findByCriteria(CriteriaVO criteria);

	
	/**
	 * Devuelve un listado de terceros jurídicos a partir del tipo de documento y el número de documento
	 * 
	 * @param phisicalNumber
	 * @param typeDoc
	 * @return
	 */
	public List<TerceroValidadoFisicoVO> findTerceroJuridicoByDocumentNumber(String phisicalNumber, int typeDoc);

	/**
	 * Devuelve un listado de terceros físicos a partir del tipo de documento y el número de documento
	 * 
	 * @param phisicalNumber
	 * @param typeDoc
	 * @return
	 */
	public List<TerceroValidadoFisicoVO> findTerceroFisicoByDocumentNumber(String phisicalNumber, int typeDoc);
	
	/**
	 *
	 * @param criteria
	 * @return
	 */
	public Integer countByCriteria(CriteriaVO criteria);
}
