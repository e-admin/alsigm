/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.manager;

import java.util.List;

import es.ieci.tecdoc.isicres.api.business.vo.BaseDepartamentoVO;
import es.ieci.tecdoc.isicres.api.business.vo.DepartamentoOficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.DepartamentoUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public abstract class DepartamentoManager {

	/**
	 * Obtiene el departamento del usuario
	 *
	 * @param usuario
	 *
	 * @return El departamento del usuario
	 */
	public abstract DepartamentoUsuarioVO getDepartamentoUsuario(UsuarioVO usuario);

	/**
	 * Obtiene el departamento de la oficina
	 *
	 * @param oficina
	 *
	 * @return
	 */
	public abstract DepartamentoOficinaVO getDepartamentoOficina (OficinaVO oficina);

	/**
	 * Devuelve un listado con todos los departamentos
	 *
	 * @return
	 */
	public abstract List<BaseDepartamentoVO> getDepartamentos();

	/**
	 * Devuelve el departamento a partir de su identificador
	 *
	 * @param idDepartamento
	 *
	 * @return
	 */
	public abstract BaseDepartamentoVO getDepartamentoById(Integer id);

	/**
	 * Devuelve los departamentos que están mapeados con un grupo ldap
	 *
	 * @param idGrupoLdap Identificador del grupo ldap
	 *
	 * @return
	 */
	public abstract List<BaseDepartamentoVO> getDepartamentosGrupoLdap(
			Integer idGrupoLdap);


}
