package es.ieci.tecdoc.isicres.api.business.dao;

import java.util.List;
import java.util.Locale;

import es.ieci.tecdoc.isicres.api.business.vo.BaseCriterioBusquedaVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoAsuntoSqlVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoAsuntoVO;


public interface TipoAsuntoDAO {

	/**
	 * Metodo que obtiene todos los asuntos que estas habilitados o bien validos
	 * para la oficina pasada como parametro
	 * @param locale
	 * @param idOficina
	 * @param baseCriterioBusqueda
	 * @return List de objetos tipo {@link TipoAsuntoVO}
	 */
	public List findAllAsuntos(Locale locale, String idOficina, BaseCriterioBusquedaVO baseCriterioBusqueda);
	
	/**
	 * Metodo que obtiene el numero de los asuntos que estas habilitados o bien validos
	 * para la oficina pasada como parametro
	 * 
	 * @param locale
	 * @param idOficina
	 * @return int tamaño de la consulta
	 */
	public int findAllAsuntosCount(Locale locale, String idOficina);
	
	/**
	 * Obtiene TipoAsuntoVO mediante el id del asunto
	 * @param locale
	 * @param id asunto
	 * @return un {@link TipoAsuntoVO}
	 */
	public TipoAsuntoVO getTipoAsuntoById(Locale locale, String id);
	
	/**
	 * Obtiene TipoAsuntoVO mediante el codigo del asunto
	 * @param locale
	 * @param idOficina
	 * @param codigoAsunto
	 * @return un {@link TipoAsuntoVO}
	 */
	public TipoAsuntoVO getTipoAsuntoByCodigo(Locale locale,String idOficina, String codigoAsunto);
	
	/**
	 * Obtiene un {@link TipoAsuntoVO} mediante el codigo asunto es valido para
	 * todas las oficinas o por lo menos para la que se pasa por parametro
	 * 
	 * @param locale
	 * @param codigoAsunto
	 * @param idOficina
	 * @return TipoAsuntoVO
	 */
	public TipoAsuntoVO getTipoAsuntoAllOficOrIdOfic(Locale locale,String codigoAsunto, String idOficina);
	
	/**
	 * Obtiene los asuntos mediante un criterio de busqueda pasado como parametro
	 * @param locale
	 * @param idOficina
	 * @param criterioBusqueda
	 * @return List de objetos {@link TipoAsuntoVO}
	 */
	public List findTipoAsuntoByCriterioWhereSql(Locale locale,String idOficina, CriterioBusquedaTipoAsuntoSqlVO criterioBusqueda);
	
	/**
	 * Obtiene el numero de asuntos mediante un criterio de busqueda pasado como parametro
	 * @param locale
	 * @param idOficina
	 * @param where
	 * @return int tamaño de la consulta
	 */
	public int findTipoAsuntoByCriterioWhereSqlCount(Locale locale,String idOficina, String where);
}
