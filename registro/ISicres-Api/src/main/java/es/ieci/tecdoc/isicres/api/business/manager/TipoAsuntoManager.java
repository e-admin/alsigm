package es.ieci.tecdoc.isicres.api.business.manager;

import java.util.List;
import java.util.Locale;

import es.ieci.tecdoc.isicres.api.business.vo.BaseCriterioBusquedaVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoAsuntoSqlVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaTipoAsuntoVO;
import es.ieci.tecdoc.isicres.api.business.vo.TipoAsuntoVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

public abstract class TipoAsuntoManager {

	/**
	 * Obtiene un tipoAsunto mediante su codigo
	 * @param usuario
	 * @param codigo
	 * @return un {@link TipoAsuntoVO}
	 */
	abstract public TipoAsuntoVO getTipoAsuntoByCodigo(UsuarioVO usuario,String codigo);

	/**
	 * Obtiene todos los asuntos que no estan dados de baja y validos para el usuario
	 * @param usuario
	 * @return una lista de objetos {@link TipoAsuntoVO}
	 */
	abstract public List findAll(UsuarioVO usuario, BaseCriterioBusquedaVO baseCriterioBusqueda);

	/**
	 * Obtiene el numero de asuntos que no estan dados de baja y validos para el usuario
	 * @param usuario
	 * @return int tamaño de la consulta
	 */
	abstract public int findAllCount(UsuarioVO usuario);

	/**
	 * Obtiene todos los tipos de asunto segun un criterio de busqueda
	 * @param usuario
	 * @param criterioBusqueda
	 * @return una lista de objetos tipo {@link TipoAsuntoVO}
	 */
	abstract public List findTipoAsuntoByCriterio(UsuarioVO usuario, CriterioBusquedaTipoAsuntoVO criterioBusqueda);

	/**
	 * Obtiene todos los tipos de asunto segun un criterio de busqueda SQL
	 * @param usuario
	 * @param criterioBusqueda
	 * @return una lista de objetos tipo {@link TipoAsuntoVO}
	 */
	abstract public List findTipoAsuntoByCriterioWhereSql(UsuarioVO usuario, CriterioBusquedaTipoAsuntoSqlVO criterioBusqueda);

	/**
	 * Obtiene todos los tipos de asunto segun un criterio de busqueda SQL
	 * @param usuario
	 * @param criterioBusqueda
	 * @return int tamaño de la consulta
	 */
	abstract public int findTipoAsuntoByCriterioWhereSqlCount(UsuarioVO usuario, CriterioBusquedaTipoAsuntoSqlVO criterioBusqueda);


	/**
	 * Valida un codigo de tipo de asunto
	 * @param usuario
	 * @param codigo asunto
	 * @return boolean
	 */
	abstract public boolean validateTipoAsuntoByCodigo(UsuarioVO usuario,String codigo);


	/**
	 * Obtiene TipoAsuntoVO mediante el id del asunto
	 * @param locale
	 * @param id asunto
	 * @return un {@link TipoAsuntoVO}
	 */
	abstract public TipoAsuntoVO getTipoAsuntoById(UsuarioVO usuario, String idAsunto);


}
