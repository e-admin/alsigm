package organizacion.persistence.db;

import java.util.List;

import organizacion.model.vo.UserOrganoVO;

/**
 * Métodos para la recuperación y actualización de los datos referentes a
 * usuarios mantenidos por el sistema
 */
public interface IUserOrganoDBEntity {

	public UserOrganoVO insertUsuarioOrgano(final UserOrganoVO userOrganoVO);

	public void updateUsuarioOrgano(UserOrganoVO userOrganoVO);

	public void deleteUsuarioOrgano(final String idUsuario);

	public UserOrganoVO getUsuarioOrganoById(final String idUsuario);

	public UserOrganoVO getUsuarioOrgano(final String idUsuario,
			final String idOrgano);

	public List getUsuarios();

	public List getUsuariosByOrgano(final String idOrgano);

	/**
	 * 
	 * @param userOrganoVO
	 * @return Lista de {@link UserOrganoVO}
	 */
	public List searchUsuariosOrganos(final UserOrganoVO userOrganoVO);

	public void deleteUsuariosOrgano(final String idOrgano);
}