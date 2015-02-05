package gcontrol.db;

import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.UsuarioOrganoVO;

import java.util.List;

import common.db.IDBEntity;

/**
 * Entidad: <b>ASCAUSRORG</b>
 * 
 * @author IECISA
 * 
 */
public interface IOrganoUsuarioDBEntity extends IDBEntity {
	// /**
	// * Obtiene los roles de un usuario.
	// * @param idUsuario Identificador del usuario.
	// * @return Roles del usuario.
	// */
	// public abstract CAOrganoVO getCAOrganoUsuarioValido(String idUsuario);

	public CAOrganoVO getCAOrganoUsuario(String idUsuario);

	/**
	 * Obtiene la lista de identificadores de usuarios de órganos.
	 * 
	 * @param idOrgs
	 *            Lista de identificadores de órganos.
	 * @return Lista de identificadores de usuarios.
	 */
	public List getUsuariosValidosEnOrganos(List idOrgs);

	/**
	 * Insert relacion usuario organo
	 * 
	 * @param usuarioOrganoVO
	 */
	public void insertUsuarioOrgano(UsuarioOrganoVO usuarioOrganoVO);

	/**
	 * 
	 * @param usuarioOrganoVO
	 */
	public void updateUsuarioOrgano(UsuarioOrganoVO usuarioOrganoVO);

	/**
	 * 
	 * @param idUsuario
	 * @param idOrg
	 * @return
	 */
	public UsuarioOrganoVO getUsuarioOrgano(String idUsuario);

	public List getOrganos(String[] ids);

	/**
	 * Borra los registros de un usuario en esta tabla
	 * 
	 * @param id
	 */
	public abstract void deleteOrganosUsuario(String id);

}