package es.ieci.tecdoc.isicres.api.business.dao;

import java.util.List;

import es.ieci.tecdoc.isicres.api.business.vo.BaseDepartamentoVO;
import es.ieci.tecdoc.isicres.api.business.vo.DepartamentoOficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.DepartamentoUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

public interface DepartamentoDAO {

	/**
	 * Obtiene el departamento del usuario
	 *
	 * @param usuario
	 *
	 * @return El departamento del usuario
	 */
	public DepartamentoUsuarioVO getDepartamentoUsuario(UsuarioVO usuario);

	/**
	 * Obtiene el departamento de la oficina
	 *
	 * @param oficina
	 *
	 * @return
	 */
	public DepartamentoOficinaVO getDepartamentoOficina (OficinaVO oficina);

	/**
	 * Devuelve un listado con todos los departamentos
	 *
	 * @return
	 */
	public List<BaseDepartamentoVO> findDepartamentos();

	/**
	 * Devuelve el departamento a partir de su identificador
	 *
	 * @param idDepartamento
	 *
	 * @return
	 */
	public BaseDepartamentoVO getDepartamentoById(Integer idDepartamento);

	/**
	 * Devuelve los departamentos que están mapeados con un grupo ldap
	 *
	 * @param idGrupoLdap Identificador del grupo ldap
	 *
	 * @return
	 */
	public List<BaseDepartamentoVO> getDepartamentosGrupoLdap(
			Integer idGrupoLdap);

}
