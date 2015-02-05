package es.ieci.tecdoc.isicres.api.business.dao;

import java.util.List;
import java.util.Locale;

import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoUnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaUnidadAdministrativaByTipoVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaUnidadAdministrativaByUnidadVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaWhereSqlVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoUnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UnidadAdministrativaVO;

public interface UnidadAdministrativaDAO {
	
	/**
	 * Metodo que obtiene todos los tipos de unidades administrativas segun su idioma
	 * @param locale
	 * @param criterio limites de la consulta (posicion inicial y numero maximo de datos a recuperar)
	 * @return Listado de {@link TipoUnidadAdministrativaVO}
	 */
	public List findAllTipoUnidadesAdmin(Locale locale, CriterioBusquedaTipoUnidadAdministrativaVO criterio);
	
	/**
	 * Metodo que obtiene el tipo de unidad administrativa según su idioma y el código
	 * @param code Código
	 * @param locale Locale
	 * @param criterio limites de la consulta (posicion inicial y numero maximo de datos a recuperar)
	 * @return Listado de {@link TipoUnidadAdministrativaVO}
	 */
	public TipoUnidadAdministrativaVO getTipoUnidadesAdminByCode(String code, Locale locale, CriterioBusquedaTipoUnidadAdministrativaVO criterio);
	
	/**
	 * Metodo que obtiene el numero total de todos los tipos de unidades administrativas segun su idioma
	 * @param locale
	 * @return int numero de tipo de unidades administrativas
	 */
	public int findCountAllTipoUnidadesAdmin(Locale locale); 
	
	
	/**
	 * Metodo que retorna listado de unidades administrativas segun un criterio
	 * de búsqueda pasado como parametro
	 * @param locale
	 * @param criterio consulta a realizar
	 * @return Lista de {@link UnidadAdministrativaVO}
	 */
	public List findUnidadesAdmWhereSQL(Locale locale, CriterioBusquedaWhereSqlVO criterio);
	
	/**
	 * Metodo que obtiene el numero total de unidades administrativas segun el criterio de busqueda pasado como parametro
	 * @param locale
	 * @param criterio consulta a realizar
	 * @return int numero de unidades administrativas encontradas para el criterio de busqueda
	 */
	public int findCountUnidadesAdmWhereSQL(Locale locale, CriterioBusquedaWhereSqlVO criterio);
	
	/**
	 * Metodo que obtiene una Unidad Administrativa segun su codigo
	 * @param locale
	 * @param codigoUnidad
	 * @return {@link UnidadAdministrativaVO}
	 */
	public UnidadAdministrativaVO findUnidadByCode(Locale locale, String codigoUnidad);
	
	/**
	 * Metod que obtiene las unidades administrativas segun un tipo pasado como parametro
	 * @param locale
	 * @param criterio consulta a realizar
	 * @return Listado de objetos tipo {@link UnidadAdministrativaVO}
	 */
	public List findUnidadesAdmByTipo(Locale locale, CriterioBusquedaUnidadAdministrativaByTipoVO criterio);
	
	/**
	 * Metod que obtiene el numero de unidades administrativas segun un tipo pasado como parametro
	 * @param locale
	 * @param criterio consulta a realizar
	 * @return Listado de objetos tipo {@link UnidadAdministrativaVO}
	 */
	public int findCountUnidadesAdmByTipo(Locale locale, CriterioBusquedaUnidadAdministrativaByTipoVO criterio);
	
	/**
	 * Metodo que obtiene el listado de unidades administrativas segun la unidad
	 * @param locale
	 * @param criterio consulta a realizar
	 * @return Listado de objetos tipo {@link UnidadAdministrativaVO}
	 */
	public List findUnidadesAdmByUnidad(Locale locale, CriterioBusquedaUnidadAdministrativaByUnidadVO criterio);
	
	/**
	 * Metodo que obtiene el numero de listado de unidades administrativas segun la unidad
	 * @param locale
	 * @param criterio consulta a realizar
	 * @return int numero de unidades administrativas que cumplen el criterio de busqueda
	 */
	public int findCountUnidadesAdmByUnidad(Locale locale, CriterioBusquedaUnidadAdministrativaByUnidadVO criterio);
	
	/**
	 * Metodo que obtiene una Unidad Administrativa segun su id
	 * @param locale
	 * @param idUnidad
	 * @return {@link UnidadAdministrativaVO}
	 */
	public UnidadAdministrativaVO findUnidadById(Locale locale, Integer idUnidad);
}
