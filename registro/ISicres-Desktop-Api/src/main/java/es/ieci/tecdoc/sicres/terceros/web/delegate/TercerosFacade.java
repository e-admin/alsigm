package es.ieci.tecdoc.sicres.terceros.web.delegate;

import java.util.List;

import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.TipoDocumentoIdentificativoTerceroVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType;
import es.ieci.tecdoc.isicres.terceros.business.vo.search.CriteriaVO;

/**
 *
 * @author IECISA
 *
 */
public interface TercerosFacade {

	/**
	 * Devuelve el número de terceros que cumplen con los criterios de búsqueda
	 * que se pasan en el parámetro <code>criteria</code>.
	 *
	 * @param criteria
	 * @return
	 */
	public Integer count(CriteriaVO criteria);

	/**
	 * Realiza una búsqueda de terceros haciendo uso de los criterios que se
	 * pasan en el parámetro <code>criteria</code>.
	 *
	 * @param criteria
	 * @return
	 */
	public List<TerceroValidadoVO> findByCriteria(CriteriaVO criteria);

	/**
	 *
	 * @param tercero
	 * @return
	 */
	public TerceroValidadoVO create(TerceroValidadoVO tercero);

	/**
	 *
	 * @param tercero
	 * @return
	 */
	public TerceroValidadoVO update(TerceroValidadoVO tercero);

	/**
	 *
	 * @param id
	 * @return
	 */
	public TerceroValidadoVO get(String id);

	/**
	 *
	 * @return
	 */
	public List<TipoDocumentoIdentificativoTerceroVO> getTiposDocumentosIdentificacion();

	/**
	 *
	 * @param tercero
	 * @return
	 */
	public List<BaseDireccionVO> getDirecciones(BaseTerceroVO tercero);

	/**
	 *
	 * @param tercero
	 * @param tipoDireccion
	 * @return
	 */
	public List<BaseDireccionVO> getDirecciones(BaseTerceroVO tercero,
			DireccionType tipoDireccion);

	/**
	 *
	 * @return
	 */
	public List<ProvinciaVO> getProvincias();

	/**
	 * @param provincia
	 * @return
	 */
	public List<CiudadVO> getCiudades(ProvinciaVO provincia);
}
