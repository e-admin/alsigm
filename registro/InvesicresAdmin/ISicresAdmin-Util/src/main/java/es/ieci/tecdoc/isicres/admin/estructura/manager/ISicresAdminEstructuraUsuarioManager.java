package es.ieci.tecdoc.isicres.admin.estructura.manager;

import es.ieci.tecdoc.isicres.admin.estructura.dao.LdapUser;
import es.ieci.tecdoc.isicres.admin.estructura.dao.LdapUsers;
import es.ieci.tecdoc.isicres.admin.estructura.dao.User;
import es.ieci.tecdoc.isicres.admin.estructura.dao.Users;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.UsersImpl;
import es.ieci.tecdoc.isicres.admin.estructura.factory.ISicresAdminObjectFactory;

public class ISicresAdminEstructuraUsuarioManager {


	public User getUsuario(int id, String entidad) throws Exception{
		User user= ISicresAdminObjectFactory.createUser();
		user.load(id, entidad);

		return user;
	}

	public User getUsuario(String nameUser, String entidad) throws Exception{
		User user= ISicresAdminObjectFactory.createUser();
		user.load(nameUser, entidad);

		return user;
	}

	public LdapUser getUsuarioLdapByGuid(String ldapguid, String entidad) throws Exception{
		LdapUser user= ISicresAdminObjectFactory.createLdapUser();
		user.loadFromGuid(ldapguid, entidad);

		return user;
	}

	public LdapUser getUsuarioLdap(int id, String entidad) throws Exception{
		LdapUser user= ISicresAdminObjectFactory.createLdapUser();
		user.load(id, entidad);
		return user;
	}

	public String getUsuarioLdapBasicById(int id, String entidad) throws Exception {
		LdapUser user= ISicresAdminObjectFactory.createLdapUser();
		user.loadBasic(id, entidad);
		return user.getGuid();
	}

	public LdapUsers getUsuariosLdap(String entidad) throws Exception{
		LdapUsers users = new LdapUsers();
		users.loadFull(entidad);
		return users;
	}

	public void eliminarUsuarioLdap(int id, String entidad) throws Exception{
		LdapUser user = getUsuarioLdap(id, entidad);
		user.delete(entidad);
	}

	public Users getUsuarios(int aplicacion, int ids[], boolean superusers, String entidad) throws Exception{
		Users usersList = new UsersImpl();
		usersList.loadByIdsUser(aplicacion, ids, superusers, entidad);

		return usersList;
	}

	public LdapUsers getUsuariosLdap(int aplicacion, int ids[], boolean superusers, String entidad) throws Exception{
		LdapUsers usersList = new LdapUsers();
		usersList.loadByIdsUser(aplicacion, ids, superusers, entidad);

		return usersList;
	}

	public Users getUsuariosAsociacion(int aplicacion, int deptId, boolean superusers, String entidad) throws Exception{
		Users usersList = new UsersImpl();
		usersList.loadUsersAsociation(aplicacion, deptId, superusers, entidad);

		return usersList;
	}

	public Users getUsuariosYaAsociados(int []idsUser, int idOfic, String entidad) throws Exception{
		Users usersList = new UsersImpl();
		usersList.loadUsersAssociated(idsUser, idOfic, entidad);
		return usersList;
	}

	public LdapUsers getUsuariosLdapYaAsociados(int []idsUser, int idOfic, String entidad) throws Exception{
		LdapUsers usersList = new LdapUsers();
		usersList.loadUsersLdapAssociated(idsUser, idOfic, entidad);
		return usersList;
	}

	public Users getUsuariosAplicacion(int aplicacion, int deptId[], boolean sinPermisos, boolean usuarios, boolean superusuarios, String entidad) throws Exception{
		Users usersList= new UsersImpl();
		usersList.loadByAplicacion(aplicacion, deptId, sinPermisos, usuarios, superusuarios, entidad);

		return usersList;
	}

	public LdapUsers getUsuariosLdapAplicacion(int aplicacion, int deptId[],
			boolean sinPermisos, boolean usuarios, boolean superusuarios,
			String entidad) throws Exception {
		LdapUsers ldapUsersList = new LdapUsers();
		ldapUsersList.loadByAplicacion(aplicacion, deptId, sinPermisos,
				usuarios, superusuarios, entidad);

		return ldapUsersList;
	}

	public Users getUsuariosDepartamento(int id, String entidad) throws Exception{
		Users usersList= new UsersImpl();
		usersList.loadByDept(id, entidad);

		return usersList;
	}



	public Users getUsuariosGrupo(int id, String entidad) throws Exception{
		Users usersList= new UsersImpl();
		usersList.loadByGroup(id, entidad);

		return usersList;
	}

	public void editarUsuario(User user,
			String entidad) throws Exception{

		//primero comprobamos que el usuario tiene permiso para modificar el departamento
//		UserAccess userAccess = ObjFactory.createUserAccess();
//		boolean userCanEditDept = userAccess.userCanEditDept(department.get_userConnected(), department.getId(), entidad);
//		if(userCanEditDept)
		user.store(entidad);
	}

	public void editarUsuarioLdap(LdapUser userLdap, String entidad) throws Exception{

		userLdap.store(entidad);
	}
}
