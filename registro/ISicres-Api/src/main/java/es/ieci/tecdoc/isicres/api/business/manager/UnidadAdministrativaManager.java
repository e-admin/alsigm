package es.ieci.tecdoc.isicres.api.business.manager;


import java.util.List;
import java.util.Locale;

import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoUnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaUnidadAdministrativaByTipoVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaUnidadAdministrativaByUnidadVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaWhereSqlVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoUnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UnidadAdministrativaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */

public abstract class UnidadAdministrativaManager {
	
	/**
	 * Obtiene la unidad administrativa por su código
	 * @param usuario
	 * @param codigoUnidad
	 * @return {@link UnidadAdministrativaVO}
	 */
	abstract public UnidadAdministrativaVO getUnidadAdministrativaByCodigo(UsuarioVO usuario,String codigoUnidad);
	
	/**
	 * Obtiene las unidades administrativas de nivel superior de un determinado tipo
	 * @param usuario
	 * @param criterio
	 * @return lista de <code>UnidadAdministrativaVO</code>
	 */
	public abstract List findUnidadAdministrativaByTipo(UsuarioVO usuario,CriterioBusquedaUnidadAdministrativaByTipoVO criterio);
	
	/**
	 * Obtiene el numero de unidades administrativas de nivel superior de un determinado tipo 
	 * @param usuario
	 * @param criterio consulta a realizar
	 * @return int numero de unidades administrativas encontradas para el criterio pasado como parametro
	 */
	public abstract int findCountUnidadAdministrativaByTipo(UsuarioVO usuario, CriterioBusquedaUnidadAdministrativaByTipoVO criterio);
	
	/**
	 * Obtiene las unidades administrativas hijas  de una unidad administrativa dada 
	 * @param usuario
	 * @param criterio consulta a realizar
	 * @return lista de <code>UnidadAdministrativaVO</code>
	 */
	public abstract List findUnidadAdministrativaByUnidad(UsuarioVO usuario,CriterioBusquedaUnidadAdministrativaByUnidadVO criterio);
	
	/**
	 * Obtiene el numero de unidades administrativas hijas  de una unidad administrativa dada 
	 * @param usuario
	 * @param criterio consulta a realizar
	 * @return lista de <code>UnidadAdministrativaVO</code>
	 */
	public abstract int findCountUnidadAdministrativaByUnidad(UsuarioVO usuario,CriterioBusquedaUnidadAdministrativaByUnidadVO criterio);
	
	/**
	 * Obtiene los tipos de unidad administrativa disponibles
	 * @param usuario
	 * @param criterio consulta a realizar
	 * @return lista de objetos tipo {@link TipoUnidadAdministrativaVO}
	 */
	public abstract List findAllTipoUnidadAdministrativa(UsuarioVO usuario,CriterioBusquedaTipoUnidadAdministrativaVO criterio);

	/**
	 * Metodo que obtiene el numero de unidades administrativas disponibles
	 * @param usuario
	 * @return int numero de unidades disponibles
	 */
	public abstract int findCountAllTipoUnidadAdministrativa(UsuarioVO usuario);
	
	/**
	 * Metodo que obtiene el tipo de unidad administrativa según su idioma y el código
	 * @param code Código
	 * @param locale Locale
	 * @param criterio limites de la consulta (posicion inicial y numero maximo de datos a recuperar)
	 * @return Listado de {@link TipoUnidadAdministrativaVO}
	 */
	public abstract TipoUnidadAdministrativaVO getTipoUnidadesAdminByCode(String code, Locale locale, CriterioBusquedaTipoUnidadAdministrativaVO criterio);
	
	
	/**
	 * Metodo que obtiene las unidades administrativas que cumplen una condicion sql
	 * @param usuario
	 * @param criterio consulta a realizar
	 * @return Listado de {@link UnidadAdministrativaVO}
	 */
	public abstract List findUnidadAdministrativaByCondition(UsuarioVO usuario, CriterioBusquedaWhereSqlVO criterio);
	
	/**
	 * Metodo que obtiene el numero de unidades administrativas que cumplen una condicion sql
	 * @param usuario
	 * @param criterio consulta a realizar
	 * @return int numero de unidades encontradas que cumplen el criterio de busqueda
	 */
	public abstract int findCountUnidadAdministrativaByCondition(UsuarioVO usuario, CriterioBusquedaWhereSqlVO criterio);
	
	/**
	 * Metodo que obtiene una Unidad Administrativa segun su id
	 * @param locale
	 * @param idUnidad
	 * @return {@link UnidadAdministrativaVO}
	 */
	public abstract UnidadAdministrativaVO findUnidadById(Locale locale, Integer idUnidad);
}
