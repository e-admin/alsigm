package es.ieci.tecdoc.isicres.terceros.business.manager;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoFisicoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.CriteriaVO;

/**
 *
 * @author IECISA
 *
 */
public interface TerceroManager extends BaseManager<TerceroValidadoVO, String> {

	/**
	 * Obtiene el numero de resultados coincidentes con el criterio indicado
	 *
	 * @param criteria - Criterio de Busqueda
	 * @return Integer del resultado de la busqueda
	 */
	public Integer countByCriteria(CriteriaVO criteria);
	/**
	 * Obtiene un listado de Terceros Validados según el criterio indicado
	 *
	 * @param criteria - Criterio de Busqueda
	 * @return Listado de objetos {@link TerceroValidadoVO}
	 */
	public List<TerceroValidadoVO> findByCriteria(CriteriaVO criteria);

	/**
	 * Actualiza los datos del tercero validado <code>anEntity</code>, así como
	 * las de sus direcciones asociadas.
	 *
	 * @param anEntity
	 * @return
	 */
	public abstract TerceroValidadoVO updateAll(TerceroValidadoVO anEntity);
	
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
}
