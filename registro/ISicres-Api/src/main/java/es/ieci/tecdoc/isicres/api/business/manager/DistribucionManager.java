package es.ieci.tecdoc.isicres.api.business.manager;

import java.util.List;

import com.ieci.tecdoc.common.isicres.Keys;

import es.ieci.tecdoc.isicres.api.business.vo.BaseDepartamentoVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaDistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.DistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.GrupoUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.ImplicadoDistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.ResultadoBusquedaDistribucionVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public abstract class DistribucionManager {

	/**
	 *
	 * @param usuario
	 * @param distribucion
	 * @param libro
	 */
	public abstract void acceptDistribution(UsuarioVO usuario,
			DistribucionVO distribucion, BaseLibroVO libro);

	/**
	 *
	 * @param usuario
	 * @param distribucion
	 */
	public abstract void acceptDistribution(UsuarioVO usuario,
			DistribucionVO distribucion);

	/**
	 *
	 * @param usuario
	 * @param distribucion
	 */
	public abstract void archiveDistribution(UsuarioVO usuario,
			DistribucionVO distribucion);

	/**
	 *
	 * @param usuario
	 * @param distribucion
	 */
	public abstract void rejectDistribution(UsuarioVO usuario,
			DistribucionVO distribucion);

	/**
	 *
	 * @param usuario
	 * @param distribucion
	 */
	public abstract void redistributeInputDistribution(UsuarioVO usuario,
			DistribucionVO distribucion);

	/**
	 *
	 * @param usuario
	 * @param distribucion
	 */
	public abstract void redistributeOutputDistribution(UsuarioVO usuario,
			DistribucionVO distribucion);

	/**
	 *
	 * Método que redistribuye las distribuciones de salida de un registro.
	 *
	 * La redistribución se dirige hacia el destino indicando el id, tipo y
	 * nombre.
	 *
	 * No modifica el destino del registro.
	 *
	 * @param usuario
	 *            Usuario que realiza la distribución
	 * @param numRegistro
	 *            Número del registro
	 * @param destinoDistribucion
	 *            Destino al que se dirige la distribucion
	 */
	public abstract void redistributeOutputDistributionsManual(
			UsuarioVO usuario, String numRegistro,
			ImplicadoDistribucionVO destinoDistribucion, String matter);


	/**
	 *
	 * Método que redistribuye las distribuciones de entrada de un registro.
	 *
	 * La redistribución se dirige hacia el destino indicando el id, tipo y
	 * nombre.
	 *
	 * No modifica el destino del registro.
	 *
	 * @param usuario
	 *            Usuario que realiza la distribución
	 * @param numRegistro
	 *            Número del registro
	 * @param destinoDistribucion
	 *            Destino al que se dirige la distribucion
	 */
	public abstract void redistributeInputDistributionsManual(UsuarioVO usuario,
			String numRegistro, ImplicadoDistribucionVO destinoDistribucion,
			String matter);


	/**
	 *
	 * @param usuario
	 * @param distribucion
	 * @return
	 */
	public abstract ResultadoBusquedaDistribucionVO getInputDistributions(UsuarioVO usuario,
			CriterioBusquedaDistribucionVO criterio);

	/**
	 *
	 * @param usuario
	 * @param distribucion
	 * @return
	 */
	public abstract ResultadoBusquedaDistribucionVO getOutputDistributions(
			UsuarioVO usuario, CriterioBusquedaDistribucionVO criterio);

	/**
	 * Permite obtener el contenido de la bandeja de distribución de un usuario
	 * para una condición SQL dada.
	 *
	 * @param usuario
	 * @param criterio
	 * @param query
	 * @return
	 */
	public abstract ResultadoBusquedaDistribucionVO getUserDistributionsByCondition(
			UsuarioVO usuario, CriterioBusquedaDistribucionVO criterio,
			String query);

	/**
	 * Permite obtener el contenido de la bandeja de distribución de ENTRADA de un usuario
	 * para una condición SQL dada.
	 *
	 * @param usuario
	 * @param criterio
	 * @param query
	 * @return
	 */
	public abstract ResultadoBusquedaDistribucionVO getUserInputDistributionsByCondition(
			UsuarioVO usuario, CriterioBusquedaDistribucionVO criterio,
			String query);

	/**
	 * Permite obtener el contenido de la bandeja de distribución de SALIDA de un usuario
	 * para una condición SQL dada.
	 *
	 * @param usuario
	 * @param criterio
	 * @param query
	 * @return
	 */
	public abstract ResultadoBusquedaDistribucionVO getUserOutputDistributionsByCondition(
			UsuarioVO usuario, CriterioBusquedaDistribucionVO criterio,
			String query);


	/**
	 * Permite obtener el contenido de la bandeja de distribución para una
	 * condición SQL dada.
	 *
	 * No tiene en cuenta el usuario.
	 *
	 * @param usuario
	 * @param criterio
	 * @param query
	 * @return
	 */
	public abstract ResultadoBusquedaDistribucionVO getDistributionsByCondition(
			UsuarioVO usuario, CriterioBusquedaDistribucionVO criterio,
			String query);

	/**
	 * Permite obtener las distribuciones para un registro.
	 *
	 * @param usuario
	 * @param criterio
	 *            El criterio indica los parámetros de paginación y el estado de
	 *            las distribuciones
	 * @param registryId
	 *            Identificador del registro
	 * @param tipo
	 *            Tipo del registro:
	 *
	 *            Entrada {@link Keys.DISTRIBUCION_IN_DIST} Salida
	 *            {@link Keys.DISTRIBUCION_OUT_DIST}
	 *
	 *
	 * @return
	 */
	public abstract ResultadoBusquedaDistribucionVO getDistributionsByRegistry(
			UsuarioVO usuario, CriterioBusquedaDistribucionVO criterio,
			Integer idRegistro, int tipo);

	/**
	 * Metodo que genera una nueva distribucion
	 *
	 * @param usuario
	 *            conectado
	 * @param bookId
	 *            - Id del libro
	 * @param idRegister
	 *            - Id del registro
	 * @param senderType
	 *            - Tipo del origen
	 * @param senderId
	 *            - Id del origen
	 * @param destinationType
	 *            - Tipo de destino de la distribucion
	 * @param destinationId
	 *            - Id del destino de la distribucin
	 * @param messageForUser
	 *            - Mensaje de la distribucion
	 *
	 * @return Devuelve la distribución creada
	 */
	public abstract DistribucionVO createDistribution(UsuarioVO usuario,
			Integer bookId, Integer idRegister, Integer senderType,
			Integer senderId, Integer destinationType, Integer destinationId,
			String messageForUser);

	/**
	 * Elimina una distribucion de la base de datos
	 *
	 * @param entidad
	 * @param distributionId
	 */
	public abstract void deleteDistribution(String entidad, Integer distributionId);

	/**
	 *
	 * Devuelve una distribucion a partir de su identificador
	 *
	 *
	 * @param usuario
	 *            Usuario logado
	 * @param distributionId
	 *            Identificador de la distribucion
	 * @return La @{link DistribucionVO}
	 */
	public abstract DistribucionVO getDistributionById(UsuarioVO usuario,
			Integer distributionId);

	/**
	 * Devuelve los usuarios de LDAP menos el usuario actual
	 *
	 * @param usuario
	 * 			Usuario a exlcuir de la lista
	 *
	 * @return
	 */
	public abstract List<BaseUsuarioVO> getUsuariosLdapExceptoActual(BaseUsuarioVO usuario);

	/**
	 * Devuelve los grupos a los que pertenece el usuario
	 *
	 * @param usuarioVO
	 * 		Usuario del que queremos obtener los grupos
	 *
	 * @return
	 */
	public abstract List<GrupoUsuarioVO> getGruposPertenecientesUsuario(
			Integer idUsuario);

	/**
	 * Devuelve los grupos a los que NO pertenece el usuario
	 *
	 * @param usuarioVO
	 * 		Usuario del que queremos obtener los grupos
	 *
	 * @return
	 */
	public abstract List<GrupoUsuarioVO> getGruposNoPertenecientesUsuario(
			Integer idUsuario);

	/**
	 * Devuelve un listado con los departamentos menos el departamento establecido en el parámetro
	 *
	 * @param idDepartamento
	 * 		Departamento a excluir de la lista
	 *
	 * @return
	 */
	public abstract List<BaseDepartamentoVO> getDepartamentosExceptoActual(Integer idDepartamento);

	/**
	 * Devuelve un listado con todos los departamentos
	 *
	 * @return
	 */
	public abstract List<BaseDepartamentoVO> getDepartamentos();

	/**
	 * Devuelve los departamentos asociados con un grupo LDAP.
	 *
	 * @param idGrupoLdap
	 * 		Identificador del grupo LDAP.
	 * @return
	 */
	public abstract List<BaseDepartamentoVO> getDepartamentosGrupoLdap(
			Integer idGrupoLdap);
}
