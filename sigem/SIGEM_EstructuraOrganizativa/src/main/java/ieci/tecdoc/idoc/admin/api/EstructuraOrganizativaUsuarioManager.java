package ieci.tecdoc.idoc.admin.api;

import ieci.tecdoc.idoc.admin.api.user.LdapUser;
import ieci.tecdoc.idoc.admin.api.user.LdapUsers;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.api.user.Users;

public class EstructuraOrganizativaUsuarioManager {


	public User getUsuario(int id, String entidad) throws Exception{
		User user= ObjFactory.createUser();
		user.load(id, entidad);

		return user;
	}

	public User getUsuario(String nameUser, String entidad) throws Exception{
		User user= ObjFactory.createUser();
		user.load(nameUser, entidad);

		return user;
	}

	public User getUsuarioByIdCert(String idCert, String entidad)throws Exception{
		User user= ObjFactory.createUser();
		user.loadFromIdCert(idCert, entidad);

		return user;
	}

	public LdapUser getUsuarioLdapByGuid(String ldapguid, String entidad) throws Exception{
		LdapUser user= ObjFactory.createLdapUser();
		user.loadFromGuid(ldapguid, entidad);

		return user;
	}

	public LdapUser getUsuarioLdapByFullName(String fullName, String entidad) throws Exception{
		LdapUser user= ObjFactory.createLdapUser();
		user.loadFromFullName(fullName, entidad);

		return user;
	}

	public LdapUser getUsuarioLdap(int id, String entidad) throws Exception{
		LdapUser user= ObjFactory.createLdapUser();
		user.load(id, entidad);
		return user;
	}

	public LdapUser getUsuarioLdapByIdCert(String idCert, String entidad) throws Exception{
		LdapUser user = ObjFactory.createLdapUser();
		user.loadFromIdCert(idCert, entidad);

		return user;
	}

	public String getUsuarioLdapBasicById(int id, String entidad) throws Exception {
		LdapUser user= ObjFactory.createLdapUser();
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
		Users usersList = new Users();
		usersList.loadByIdsUser(aplicacion, ids, superusers, entidad);

		return usersList;
	}

	public LdapUsers getUsuariosLdap(int aplicacion, int ids[], boolean superusers, String entidad) throws Exception{
		LdapUsers usersList = new LdapUsers();
		usersList.loadByIdsUser(aplicacion, ids, superusers, entidad);

		return usersList;
	}

	public Users getUsuariosAsociacion(int aplicacion, int deptId, boolean superusers, String entidad) throws Exception{
		Users usersList = new Users();
		usersList.loadUsersAsociation(aplicacion, deptId, superusers, entidad);

		return usersList;
	}

	public Users getUsuariosYaAsociados(int []idsUser, int idOfic, String entidad) throws Exception{
		Users usersList = new Users();
		usersList.loadUsersAssociated(idsUser, idOfic, entidad);
		return usersList;
	}

	public LdapUsers getUsuariosLdapYaAsociados(int []idsUser, int idOfic, String entidad) throws Exception{
		LdapUsers usersList = new LdapUsers();
		usersList.loadUsersLdapAssociated(idsUser, idOfic, entidad);
		return usersList;
	}

	public Users getUsuariosAplicacion(int aplicacion, int deptId[], boolean sinPermisos, boolean usuarios, boolean superusuarios, String entidad) throws Exception{
		Users usersList= new Users();
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
		Users usersList= new Users();
		usersList.loadByDept(id, entidad);

		return usersList;
	}



	public Users getUsuariosGrupo(int id, String entidad) throws Exception{
		Users usersList= new Users();
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
