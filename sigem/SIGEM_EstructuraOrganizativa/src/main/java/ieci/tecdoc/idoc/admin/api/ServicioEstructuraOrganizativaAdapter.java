package ieci.tecdoc.idoc.admin.api;

import ieci.tecdoc.idoc.admin.api.archive.ArchiveFld;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveFlds;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveIdx;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveIdxs;
import ieci.tecdoc.idoc.admin.api.user.BasicUser;
import ieci.tecdoc.idoc.admin.api.user.BasicUsers;
import ieci.tecdoc.idoc.admin.api.user.Department;
import ieci.tecdoc.idoc.admin.api.user.Departments;
import ieci.tecdoc.idoc.admin.api.user.Group;
import ieci.tecdoc.idoc.admin.api.user.Groups;
import ieci.tecdoc.idoc.admin.api.user.LdapGroup;
import ieci.tecdoc.idoc.admin.api.user.LdapUser;
import ieci.tecdoc.idoc.admin.api.user.LdapUsers;
import ieci.tecdoc.idoc.admin.api.user.Permission;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.idoc.admin.api.user.UserProfiles;
import ieci.tecdoc.idoc.admin.api.user.Users;
import ieci.tecdoc.idoc.admin.api.volume.VolumeList;
import ieci.tecdoc.idoc.admin.api.volume.VolumeLists;
import ieci.tecdoc.idoc.admin.internal.ArchiveFldImpl;
import ieci.tecdoc.idoc.admin.internal.ArchiveIdxImpl;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.idoc.admin.internal.DepartmentsImpl;
import ieci.tecdoc.idoc.admin.internal.GenericPermsImpl;
import ieci.tecdoc.idoc.admin.internal.GenericProfilesImpl;
import ieci.tecdoc.idoc.admin.internal.GroupsImpl;
import ieci.tecdoc.idoc.admin.internal.LdapUserImpl;
import ieci.tecdoc.idoc.admin.internal.PermsImpl;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Archive;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamentos;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.DepartamentosLista;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.EstructuraOrganizativaException;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupo;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.GrupoLdap;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupos;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.GruposLista;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Lista;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Listas;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.PerfilUsuario;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.PerfilesGenericos;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.PerfilesUsuario;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Permiso;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Permisos;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.PermisosGenericos;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.ServicioEstructuraOrganizativa;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioBasico;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosBasicos;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosLdap;

import org.apache.log4j.Logger;

public class ServicioEstructuraOrganizativaAdapter implements
		ServicioEstructuraOrganizativa {

	private static Logger logger = Logger
			.getLogger(ServicioEstructuraOrganizativaAdapter.class);

	public Usuario getUsuario(int idUser, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		Usuario usuario = null;
		try {
			User user = manager.getUsuario(idUser, entidad);
			usuario = getUsuarioServicio(user);
		} catch (Exception e) {
			logger.error("Error al obtener un usuario a partir de su id.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return usuario;
	}

	public Usuario getUsuario(String nameUser, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		Usuario usuario = null;
		try {
			User user = manager.getUsuario(nameUser, entidad);
			usuario = getUsuarioServicio(user);
		} catch (Exception e) {
			logger.error("Error al obtener un usuario a partir de su nombre.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return usuario;
	}

	public Usuario getUsuarioByIdCert(String idCert, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		Usuario usuario = null;

		User user;
		try {
			user = manager.getUsuarioByIdCert(idCert, entidad);
			usuario = getUsuarioServicio(user);
		} catch (Exception e) {
			logger.error("Error al obtener un usuario a partir de su identificador de certificado digital.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return usuario;

	}

	public UsuarioLdap getUsuarioLdap(int id, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		UsuarioLdap usuario = null;
		try {
			LdapUser user = manager.getUsuarioLdap(id, entidad);
			usuario = getUsuarioLdapServicio(user);
		} catch (Exception e) {
			logger.error("Error al obtener un usuario LDAP a partir de su id.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return usuario;
	}

	public UsuarioLdap getUsuarioLdapByGuid(String ldapguid, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		UsuarioLdap usuario = null;
		try {
			LdapUser user = manager.getUsuarioLdapByGuid(ldapguid, entidad);
			usuario = getUsuarioLdapServicio(user);
		} catch (Exception e) {
			logger.error("Error al obtener un usuario LDAP a partir de su guid.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return usuario;
	}

	public UsuarioLdap getUsuarioLdapByFullName(String fullName, String entidad) throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager=new EstructuraOrganizativaUsuarioManager();
		UsuarioLdap usuario=null;
		try {
			LdapUser user = manager.getUsuarioLdapByFullName(fullName, entidad);
			usuario=getUsuarioLdapServicio(user);
		} catch (Exception e) {
			logger.error("Error al obtener un usuario LDAP a partir de su fullName.");
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return usuario;
	}

	public UsuarioLdap getUsuarioLdapByIdCert(String idCert, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		UsuarioLdap usuario = null;
		try {
			LdapUser user = manager.getUsuarioLdapByIdCert(idCert, entidad);
			usuario = getUsuarioLdapServicio(user);
		} catch (Exception e) {
			logger.error("Error al obtener un usuario LDAP a partir de su identificador de certificado digital.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return usuario;
	}

	public UsuariosLdap getUsuariosLdap(String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		UsuariosLdap usuarios = null;
		try {
			LdapUsers users = manager.getUsuariosLdap(entidad);
			usuarios = getUsuariosLdapServicio(users);
		} catch (Exception e) {
			logger.error("Error al obtener usuarios LDAP.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return usuarios;
	}

	public String getUsuarioLdapBasicById(int id, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		try {
			return manager.getUsuarioLdapBasicById(id, entidad);
		} catch (Exception e) {
			logger.error("Error al obtener el guid del usuario a partir del id:"
					+ id);
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
	}

	public Usuarios getUsuarios(int aplicacion, int idsUser[],
			boolean superusers, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		Usuarios listaUsuarios = null;
		try {
			Users users = manager.getUsuarios(aplicacion, idsUser, superusers,
					entidad);
			listaUsuarios = getUsuariosServicio(users);
		} catch (Exception e) {
			logger.error("Error al obtener los usuarios a partir de sus ids.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return listaUsuarios;
	}

	public UsuariosLdap getUsuariosLdap(int aplicacion, int idsUser[],
			boolean superusers, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		UsuariosLdap listaUsuarios = null;
		try {
			LdapUsers users = manager.getUsuariosLdap(aplicacion, idsUser,
					superusers, entidad);
			listaUsuarios = getUsuariosLdapServicio(users);
		} catch (Exception e) {
			logger.error("Error al obtener los usuarios a partir de sus ids.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return listaUsuarios;
	}

	public Usuarios getUsuariosAsociation(int aplicacion, int deptId,
			boolean superusers, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		Usuarios listaUsuarios = null;
		try {
			Users users = manager.getUsuariosAsociacion(aplicacion, deptId,
					superusers, entidad);
			listaUsuarios = getUsuariosServicio(users);
		} catch (Exception e) {
			logger.error("Error al obtener los posibles usuarios asociados a una oficina.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return listaUsuarios;
	}

	public Usuarios getUsuariosYaAsociados(int[] idsUser, int idOfic,
			String entidad) throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		Usuarios listaUsuarios = null;
		try {
			Users users = manager.getUsuariosYaAsociados(idsUser, idOfic,
					entidad);
			listaUsuarios = getUsuariosServicio(users);
		} catch (Exception e) {
			logger.error("Error al obtener los usuarios ya asociados a una oficina.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return listaUsuarios;
	}

	public UsuariosLdap getUsuariosLdapYaAsociados(int[] idsUser, int idOfic,
			String entidad) throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		UsuariosLdap listaUsuarios = null;
		try {
			LdapUsers users = manager.getUsuariosLdapYaAsociados(idsUser,
					idOfic, entidad);
			listaUsuarios = getUsuariosLdapServicio(users);
		} catch (Exception e) {
			logger.error("Error al obtener los usuarios LDAP ya asociados a una oficina.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return listaUsuarios;
	}

	public Usuarios getUsuariosAplicacion(int aplicacion, boolean sinPermisos,
			boolean usuarios, boolean superusuarios, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		Usuarios listaUsuarios = null;
		try {
			Users users = manager.getUsuariosAplicacion(aplicacion,
					new int[] { -1 }, sinPermisos, usuarios, superusuarios,
					entidad);
			listaUsuarios = getUsuariosServicio(users);
		} catch (Exception e) {
			logger.error("Error al obtener los usuarios con acceso a una determinada aplicaci�n.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return listaUsuarios;
	}

	public UsuariosLdap getUsuariosLdapAplicacion(int aplicacion,
			boolean sinPermisos, boolean usuarios, boolean superusuarios,
			String entidad) throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		UsuariosLdap listaUsuarios = null;
		try {
			LdapUsers ldapUsers = manager.getUsuariosLdapAplicacion(aplicacion,
					new int[] { -1 }, sinPermisos, usuarios, superusuarios,
					entidad);
			listaUsuarios = getUsuariosLdapServicio(ldapUsers);
		} catch (Exception e) {
			logger.error("Error al obtener los usuarios con acceso a una determinada aplicaci�n.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return listaUsuarios;
	}

	public Usuarios getUsuariosAplicacionPorDepartamento(int aplicacion,
			int deptId[], boolean sinPermisos, boolean usuarios,
			boolean superusuarios, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		Usuarios listaUsuarios = null;
		try {
			Users users = manager.getUsuariosAplicacion(aplicacion, deptId,
					sinPermisos, usuarios, superusuarios, entidad);
			listaUsuarios = getUsuariosServicio(users);
		} catch (Exception e) {
			logger.error("Error al obtener los usuarios con acceso a una determinada aplicaci�n.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return listaUsuarios;
	}

	public UsuariosLdap getUsuariosLdapAplicacionPorDepartamento(
			int aplicacion, int deptId[], boolean sinPermisos,
			boolean usuarios, boolean superusuarios, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		UsuariosLdap listaUsuarios = null;
		try {
			LdapUsers ldapUsers = manager.getUsuariosLdapAplicacion(aplicacion,
					deptId, sinPermisos, usuarios, superusuarios, entidad);
			listaUsuarios = getUsuariosLdapServicio(ldapUsers);
		} catch (Exception e) {
			logger.error("Error al obtener los usuarios con acceso a una determinada aplicaci�n.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return listaUsuarios;
	}

	public Usuarios getUsuariosDepartamento(int idDept, String entidad)
			throws EstructuraOrganizativaException {
		Usuarios listaUsuarios = null;
		try {
			EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
			Users listaUsers = manager.getUsuariosDepartamento(idDept, entidad);
			listaUsuarios = getUsuariosServicio(listaUsers);
		} catch (Exception e) {
			logger.error("Error al obtener los usuarios de un departameneto.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return listaUsuarios;
	}

	public Usuarios getUsuariosGrupo(int idGrupo, String entidad)
			throws EstructuraOrganizativaException {
		Usuarios listaUsuarios = null;
		try {
			EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
			Users listaUsers = manager.getUsuariosGrupo(idGrupo, entidad);
			listaUsuarios = getUsuariosServicio(listaUsers);
		} catch (Exception e) {
			logger.error("Error al obtener los usuarios de un grupo.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return listaUsuarios;
	}

	public Departamento getDepartamento(int idDepto, String entidad)
			throws EstructuraOrganizativaException {
		Departamento departamento = new Departamento();
		try {
			EstructuraOrganizativaDepartamentoManager manager = new EstructuraOrganizativaDepartamentoManager();
			Department dep = manager.getDepartamento(idDepto, entidad);
			departamento = getDepartamentoServicio(dep);
		} catch (Exception e) {
			logger.error("Error al obtener un departamento.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return departamento;
	}

	public Departamento getDepartamentoLite(int idDepto, String entidad)
			throws EstructuraOrganizativaException {
		Departamento departamento = new Departamento();
		try {
			EstructuraOrganizativaDepartamentoManager manager = new EstructuraOrganizativaDepartamentoManager();
			Department dep = manager.getDepartamentoLite(idDepto, entidad);
			departamento = getDepartamentoServicio(dep);
		} catch (Exception e) {
			logger.error("Error al obtener un departamento.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return departamento;
	}

	public Departamentos getDepartamentos(int idDeptPadre, String entidad)
			throws EstructuraOrganizativaException {
		Departamentos listaDepartamentos = null;
		try {
			EstructuraOrganizativaDepartamentoManager manager = new EstructuraOrganizativaDepartamentoManager();
			Departments listaDepartments = manager.getDepartamentos(
					idDeptPadre, entidad);
			listaDepartamentos = this
					.getDepartamentosServicio(listaDepartments);
		} catch (Exception e) {
			logger.error("Error al obtener los subdepartamentos de un departamento.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return listaDepartamentos;
	}

	public Departamentos getDepartamentos(String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaDepartamentoManager manager = new EstructuraOrganizativaDepartamentoManager();
		Departamentos listaDepartamentos = null;
		try {
			Departments listaDepartments = manager.getDepartamentos(entidad);
			listaDepartamentos = this
					.getDepartamentosServicio(listaDepartments);
		} catch (Exception e) {
			logger.error("Error al obtener los departamentos.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return listaDepartamentos;
	}

	public Grupos getGrupos(String entidad)
			throws EstructuraOrganizativaException {
		Grupos grupos = null;
		try {
			EstructuraOrganizativaGrupoManager manager = new EstructuraOrganizativaGrupoManager();
			Groups groups = manager.getGrupos(entidad);
			grupos = this.getGruposServicio(groups);
		} catch (Exception e) {
			logger.error("Error al obtener los grupos de una entidad.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return grupos;
	}

	public Grupo getGrupo(int idGrupo, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaGrupoManager manager = new EstructuraOrganizativaGrupoManager();
		Grupo grupo = null;
		try {
			Group group = manager.getGrupo(idGrupo, entidad);
			grupo = this.getGrupoServicio(group);
		} catch (Exception e) {
			logger.error("Error al obtener un grupo a partir de su id.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return grupo;
	}

	public UsuarioLdap crearUsuarioLdap(String ldapGuid, String name,
			int idPerfil, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaLdapManager manager = new EstructuraOrganizativaLdapManager();
		UsuarioLdap usuario = null;
		try {

			LdapUser ldapuser = manager.createLdapUser(ldapGuid, name,
					idPerfil, entidad);
			usuario = this.getUsuarioLdapServicio(ldapuser);
		} catch (Exception e) {
			logger.error("Error al crear el usuario ldap: " + ldapGuid);
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return usuario;
	}

	public GrupoLdap getGrupoLdap(String ldapGuid, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaLdapManager manager = new EstructuraOrganizativaLdapManager();
		GrupoLdap grupo = null;
		try {

			LdapGroup ldapgroup = manager.getLdapGroup(ldapGuid, entidad);
			grupo = this.getGrupoLdapServicio(ldapgroup);

		} catch (Exception e) {
			logger.error("Error al obtener el grupo ldap: " + ldapGuid);
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return grupo;
	}

	public GrupoLdap getGrupoLdapById(int id, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaLdapManager manager = new EstructuraOrganizativaLdapManager();
		GrupoLdap grupo = null;
		try {

			LdapGroup ldapgroup = manager.getLdapGroup(id, entidad);
			grupo = this.getGrupoLdapServicio(ldapgroup);

		} catch (Exception e) {
			logger.error("Error al obtener el grupo ldap: " + id);
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return grupo;
	}

	public GrupoLdap getGrupoLdapByDeptId(int deptId, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaLdapManager manager = new EstructuraOrganizativaLdapManager();
		GrupoLdap grupo = null;
		try {

			LdapGroup ldapgroup = manager.getLdapGroupByDeptId(deptId, entidad);
			grupo = this.getGrupoLdapServicio(ldapgroup);

		} catch (Exception e) {
			logger.error("Error al obtener el grupo ldap: " + deptId);
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return grupo;
	}

	public GrupoLdap crearGrupoLdap(String ldapGuid, String ldapDn, int type,
			String entidad) throws EstructuraOrganizativaException {
		EstructuraOrganizativaLdapManager manager = new EstructuraOrganizativaLdapManager();
		GrupoLdap grupo = null;
		try {

			LdapGroup ldapgroup = manager.createLdapGroup(ldapGuid, ldapDn,
					type, entidad);
			grupo = this.getGrupoLdapServicio(ldapgroup);

		} catch (Exception e) {
			logger.error("Error al crear el grupo ldap: " + ldapDn);
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return grupo;
	}

	public void editarDepartamento(Departamento departamento, String entidad)
			throws EstructuraOrganizativaException {

		EstructuraOrganizativaDepartamentoManager manager = new EstructuraOrganizativaDepartamentoManager();
		try {
			manager.editarDepartamento(
					this.getDepartmentProduct(departamento, entidad), entidad);
		} catch (Exception e) {
			logger.error("Error al editar el departamento.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
	}

	public void editarUsuario(Usuario usuario, String entidad)
			throws EstructuraOrganizativaException {

		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		try {
			manager.editarUsuario(this.getUserProduct(usuario, entidad),
					entidad);
		} catch (Exception e) {
			logger.error("Error al editar el usuario.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
	}

	public void editarUsuarioLdap(UsuarioLdap usuarioLdap, String entidad)
			throws EstructuraOrganizativaException {

		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		try {
			manager.editarUsuarioLdap(this.getUserLdap(usuarioLdap, entidad),
					entidad);
		} catch (Exception e) {
			logger.error("Error al editar el usuario.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
	}

	// ****************** Usuarios ***************************+

	private Usuarios getUsuariosServicio(Users listaUsers) {
		Usuarios listaUsuarios = new Usuarios();
		for (int i = 0; i < listaUsers.count(); i++) {
			User userTmp = listaUsers.getUser(i);
			listaUsuarios.add(getUsuarioServicio(userTmp));
		}
		return listaUsuarios;
	}

	private Usuario getUsuarioServicio(User user) {
		if (user != null) {
			Usuario usuario = new Usuario();
			usuario.set_userConnected(user.getUserConnected());
			usuario.set_id(user.getId());
			usuario.set_name(user.getName());
			usuario.set_password(user.getPassword());
			usuario.set_oldPassword(user.getOldPassword());
			usuario.set_isChange(user.getIsChange());
			usuario.set_pwdmbc(user.get_pwdmbc());
			usuario.set_pwdvpcheck(user.get_pwdvpcheck());
			usuario.set_description(user.getDescription());
			usuario.set_deptId(user.getDeptId());
			usuario.set_state(user.getState());
			usuario.set_permsImpl(this.getPermisosGenericosServicio(user
					.get_permsImpl()));
			usuario.set_profilesImpl(this.getPerfilesGenericos(user
					.get_profilesImpl()));
			usuario.set_wasAdmin(user.get_wasAdmin());
			usuario.set_creatorId(user.getCreatorId());
			usuario.set_creationDate(user.getCreationDate());
			usuario.set_updaterId(user.getUpdaterId());
			usuario.set_updateDate(user.getUpdateDate());
			usuario.set_pwdLastUpdTs(user.get_pwdLastUpdTs());
			usuario.set_pwdminlen(user.get_pwdminlen());

			return usuario;
		} else {
			return null;
		}
	}

	private UsuariosLdap getUsuariosLdapServicio(LdapUsers users) {
		UsuariosLdap usuarios = new UsuariosLdap();
		for (int i = 0; i < users.count(); i++)
			usuarios.add(getUsuarioLdapServicio(users.getUser(i)));
		return usuarios;
	}

	private UsuarioLdap getUsuarioLdapServicio(LdapUser user) {
		if (user != null) {
			UsuarioLdap usuario = new UsuarioLdap();
			usuario.set_id(user.getId());
			usuario.set_ldapguid(user.getGuid());
			usuario.set_ldapfullname(user.getFullName());
			usuario.set_permsImpl(this.getPermisosGenericosServicio(user
					.getPermissionsImpl()));
			usuario.set_profilesImpl(this.getPerfilesGenericos(user
					.getProfilesImpl()));
			return usuario;
		} else {
			return null;
		}
	}

	private Permiso getPermisoServicio(Permission permission) {
		if (permission != null) {
			Permiso permiso = new Permiso();
			permiso.set_id(permission.getId());
			permiso.set_dest(permission.getDestination());
			permiso.set_perm(permission.getPermission());
			permiso.set_product(permission.getProduct());
			return permiso;
		} else {
			return null;
		}
	}

	private Permisos getPermisosServicio(PermsImpl permsImpl) {
		if (permsImpl != null) {
			Permisos permisos = new Permisos();
			for (int i = 0; i < permsImpl.getList().size(); i++) {
				Permission permission = permsImpl.get(i);
				permisos.add(getPermisoServicio(permission));
			}
			return permisos;
		} else {
			return null;
		}
	}

	private PermisosGenericos getPermisosGenericosServicio(
			GenericPermsImpl genericPermsImpl) {
		if (genericPermsImpl != null) {
			PermisosGenericos permisosGenericos = new PermisosGenericos();
			permisosGenericos.setPermisos(this
					.getPermisosServicio(genericPermsImpl.get_perms()));
			return permisosGenericos;
		} else {
			return null;
		}
	}

	private PerfilUsuario getPerfilUsuarioServicio(UserProfile userProfile) {
		if (userProfile != null) {
			PerfilUsuario perfil = new PerfilUsuario();
			perfil.set_userId(userProfile.getUserId());
			perfil.set_product(userProfile.getProduct());
			perfil.set_profile(userProfile.getProfile());
			return perfil;
		} else {
			return null;
		}
	}

	private PerfilesUsuario getPerfilesUsuarioServicio(UserProfiles userProfiles) {
		if (userProfiles != null) {
			PerfilesUsuario perfilesUsuario = new PerfilesUsuario();
			for (int i = 0; i < userProfiles.count(); i++) {
				UserProfile userProfile = userProfiles.get(i);
				perfilesUsuario.add(this.getPerfilUsuarioServicio(userProfile));
			}
			return perfilesUsuario;
		} else {
			return null;
		}
	}

	private PerfilesGenericos getPerfilesGenericos(
			GenericProfilesImpl genericProfilesImpl) {
		if (genericProfilesImpl != null) {
			PerfilesGenericos perfilesGenericos = new PerfilesGenericos();
			perfilesGenericos.setPerfilesUsuario(this
					.getPerfilesUsuarioServicio(genericProfilesImpl
							.getProfiles()));
			return perfilesGenericos;
		} else {
			return null;
		}
	}

	// ********************** Departamentos ***************************

	private Departamento getDepartamentoServicio(Department departament) {
		if (departament != null) {
			Departamento departamento = new Departamento();
			departamento.set_adminUsers(this
					.getUsuariosBasicosServicio(departament.get_adminUsers()));
			departamento.set_creationDate(departament.getCreationDate());
			departamento.set_creatorId(departament.getCreatorId());
			departamento.set_description(departament.getDescription());
			departamento.set_id(departament.getId());
			departamento.set_managerId(departament.getManagerId());
			departamento.set_name(departament.getName());
			departamento.set_parentId(departament.getParentId());
			departamento.set_permsImpl(this
					.getPermisosGenericosServicio(departament.get_permsImpl()));
			departamento.set_type(departament.getType());
			departamento.set_updateDate(departament.getUpdateDate());
			departamento.set_updaterId(departament.getUpdaterId());
			departamento.set_userConnected(departament.get_userConnected());
			departamento.set_users(this.getUsuariosServicio(departament
					.get_users()));

			return departamento;
		} else {
			return null;
		}
	}

	private DepartamentosLista getDepartamentosListaServicio(
			DepartmentsImpl departamentsImpl) {
		if (departamentsImpl != null) {
			DepartamentosLista departamentosLista = new DepartamentosLista();
			for (int i = 0; i < departamentsImpl.count(); i++) {
				Department departament = departamentsImpl.get(i);
				departamentosLista.add(this
						.getDepartamentoServicio(departament));
			}
			return departamentosLista;
		} else {
			return null;
		}
	}

	private Departamentos getDepartamentosServicio(Departments departments) {
		if (departments != null) {
			Departamentos departamentos = new Departamentos();
			departamentos.setDepartamentosLista(this
					.getDepartamentosListaServicio(departments
							.get_departmentsImpl()));
			return departamentos;
		} else {
			return null;
		}

	}

	// ************* UsuarioBasico ******************

	private UsuarioBasico getUsuarioBasicoServicio(BasicUser basicUser) {
		if (basicUser != null) {
			UsuarioBasico usuarioBasico = new UsuarioBasico();
			usuarioBasico.set_id(basicUser.getId());
			usuarioBasico.set_name(basicUser.getName());
			return usuarioBasico;
		} else {
			return null;
		}
	}

	private UsuariosBasicos getUsuariosBasicosServicio(BasicUsers basicUsers) {
		if (basicUsers != null) {
			UsuariosBasicos usuariosBasicos = new UsuariosBasicos();
			for (int i = 0; i < basicUsers.count(); i++) {
				BasicUser basicUser = basicUsers.get(i);
				usuariosBasicos.add(this.getUsuarioBasicoServicio(basicUser));
			}
			return usuariosBasicos;
		} else {
			return null;
		}
	}

	// ************ Grupos ************************
	private Grupo getGrupoServicio(Group group) {
		if (group != null) {
			Grupo grupo = new Grupo();
			grupo.set_adminUsers(this.getUsuariosBasicosServicio(group
					.get_adminUsers()));
			grupo.set_creationDate(group.getCreationDate());
			grupo.set_creatorId(group.getCreatorId());
			grupo.set_description(group.getDescription());
			grupo.set_id(group.getId());
			grupo.set_managerId(group.getManagerId());
			grupo.set_name(group.getName());
			grupo.set_permsImpl(getPermisosGenericosServicio(group
					.get_permsImpl()));
			grupo.set_type(group.getType());
			grupo.set_updateDate(group.getUpdateDate());
			grupo.set_updaterId(group.getUpdaterId());
			grupo.set_userConnected(group.get_userConnected());
			grupo.set_users(getUsuariosServicio(group.get_users()));

			return grupo;
		} else {
			return null;
		}

	}

	private GrupoLdap getGrupoLdapServicio(LdapGroup group) {
		if (group != null) {
			GrupoLdap grupo = new GrupoLdap();

			grupo.set_id(group.getId());
			grupo.set_guid(group.getGuid());
			grupo.set_fullname(group.getFullName());

			return grupo;
		} else {
			return null;
		}

	}

	private GruposLista getGruposListaServicio(GroupsImpl groupsImpl) {
		if (groupsImpl != null) {
			GruposLista gruposLista = new GruposLista();
			for (int i = 0; i < groupsImpl.count(); i++) {
				Group group = groupsImpl.get(i);
				gruposLista.add(this.getGrupoServicio(group));
			}
			return gruposLista;
		} else {
			return null;
		}
	}

	private Grupos getGruposServicio(Groups groups) {
		if (groups != null) {
			Grupos grupos = new Grupos();
			grupos.setGruposLista(getGruposListaServicio(groups
					.get_groupsImpl()));
			return grupos;
		} else {
			return null;
		}
	}

	/* Adaptaciones Core => Implementacion */
	private Department getDepartmentProduct(Departamento departamento,
			String entidad) throws Exception {

		Department dep = ObjFactory.createDepartment(
				departamento.get_userConnected(), Defs.NULL_ID);
		// asignamos las propiedades del objeto a editar
		dep.load(departamento.get_id(), entidad);
		dep.set_userConnected(departamento.get_userConnected());
		dep.setName(departamento.get_name());
		dep.set_id(departamento.get_id());
		dep.setParentId(departamento.get_parentId());
		dep.setManagerId(departamento.get_managerId());
		dep.set_type(departamento.get_type());
		dep.setDescription(departamento.get_description());
		dep.set_creationDate(departamento.get_creationDate());
		dep.set_creatorId(departamento.get_creatorId());

		Permisos permisos = departamento.get_permsImpl().getPermisos();
		for (int i = 0; i < permisos.count(); i++) {
			Permiso permiso = permisos.get(i);
			Permission perm = dep.get_permsImpl().get_perms()
					.getProductPermission(permiso.get_product());
			if (perm != null) {
				perm.setPermission(permiso.get_perm());
			} else {
				logger.error("Se est� intentando a�adir un permiso que no existe");
				throw new EstructuraOrganizativaException(
						EstructuraOrganizativaException.EXC_INEXISTENT_PERMISSION);
			}
		}

		return dep;
	}

	private User getUserProduct(Usuario usuario, String entidad)
			throws Exception {
		User user = ObjFactory.createUser(usuario.get_userConnected(),
				Defs.NULL_ID);
		user.load(usuario.get_id(), entidad);
		user.setDeptId(usuario.get_deptId());
		user.setDescription(usuario.get_description());
		user.setName(usuario.get_name());
		user.setState(usuario.get_state());
		Permisos permisos = usuario.get_permsImpl().getPermisos();
		for (int i = 0; i < permisos.count(); i++) {
			Permiso permiso = permisos.get(i);
			Permission perm = user.get_permsImpl().get_perms()
					.getProductPermission(permiso.get_product());
			if (perm != null) {
				perm.setPermission(permiso.get_perm());
			} else {
				logger.error("Se est� intentando a�adir un permiso que no existe");
				throw new EstructuraOrganizativaException(
						EstructuraOrganizativaException.EXC_INEXISTENT_PERMISSION);
			}
		}
		PerfilesUsuario perfiles = usuario.get_profilesImpl()
				.getPerfilesUsuario();
		for (int i = 0; i < perfiles.count(); i++) {
			PerfilUsuario perfil = perfiles.get(i);
			UserProfile profile = user.getProfiles().getProductProfile(
					perfil.get_product());
			if (profile != null) {
				profile.setProfile(perfil.get_profile());
			} else {
				logger.error("Se est� intentando a�adir un perfil que no existe");
				throw new EstructuraOrganizativaException(
						EstructuraOrganizativaException.EXC_INEXISTENT_PROFILE);
			}
		}
		return user;
	}

	private LdapUser getUserLdap(UsuarioLdap usuarioLdap, String entidad)
			throws Exception {
		LdapUser userLdap = new LdapUserImpl();
		userLdap.load(usuarioLdap.get_id(), entidad);
		Permisos permisos = usuarioLdap.get_permsImpl().getPermisos();
		for (int i = 0; i < permisos.count(); i++) {
			Permiso permiso = permisos.get(i);

			Permission perm = userLdap.getPermissions().getProductPermission(
					permiso.get_product());
			if (perm != null) {
				perm.setPermission(permiso.get_perm());
			} else {
				logger.error("Se est� intentando a�adir un permiso que no existe");
				throw new EstructuraOrganizativaException(
						EstructuraOrganizativaException.EXC_INEXISTENT_PERMISSION);
			}
		}
		PerfilesUsuario perfiles = usuarioLdap.get_profilesImpl()
				.getPerfilesUsuario();
		for (int i = 0; i < perfiles.count(); i++) {
			PerfilUsuario perfil = perfiles.get(i);
			UserProfile profile = userLdap.getProfiles().getProductProfile(
					perfil.get_product());
			if (profile != null) {
				profile.setProfile(perfil.get_profile());
			} else {
				logger.error("Se est� intentando a�adir un perfil que no existe");
				throw new EstructuraOrganizativaException(
						EstructuraOrganizativaException.EXC_INEXISTENT_PROFILE);
			}
		}
		return userLdap;
	}

	/************************** LISTAS VOLUMENES ************************/

	// No contempla cargar los vol�menes de cada una de las listas
	public Listas getListas(String entidad)
			throws EstructuraOrganizativaException {
		Listas listasAPI = null;
		try {
			EstructuraOrganizativaListaManager manager = new EstructuraOrganizativaListaManager();
			VolumeLists listasDAO = manager.getListas(entidad);
			listasAPI = this.getListasServicio(listasDAO);
		} catch (Exception e) {
			logger.error("Error al obtener las listas de vol�menes de una entidad.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
		return listasAPI;
	}

	private Listas getListasServicio(VolumeLists listasDAO) throws Exception {
		Listas listasAPI = new Listas();
		for (int i = 0; i < listasDAO.count(); i++) {
			VolumeList listaDAO = listasDAO.getVolumeList(i);
			Lista listaAPI = this.getListaServicio(listaDAO);
			listasAPI.add(listaAPI);
		}
		return listasAPI;
	}

	private Lista getListaServicio(VolumeList listaDAO) {
		Lista listaAPI = new Lista();
		listaAPI.setCreationDate(listaDAO.getCreationDate());
		listaAPI.setCreatorId(listaDAO.getCreatorId());
		listaAPI.setFlags(listaDAO.getFlags());
		listaAPI.setId(listaDAO.getId());
		listaAPI.setName(listaDAO.getName());
		listaAPI.setRemarks(listaDAO.getRemarks());
		listaAPI.setState(listaDAO.getState());
		listaAPI.setUpdateDate(listaDAO.getUpdateDate());
		listaAPI.setUpdaterId(listaDAO.getUpdaterId());
		return listaAPI;
	}

	/**************** Archivadores *******************/

	public int crearArchivador(Archive archive, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaArchiveManager archiveManager = new EstructuraOrganizativaArchiveManager();
		int archId = 0;
		try {

			archId = archiveManager.createArchive(archive.getAdminUserId(),
					archive.getParentId(), archive.getName(),
					archive.getRemarks(),
					getArchiveFields(archive.getFldsDef()),
					getArchiveIdxs(archive.getIdxsDef()), archive.getTypeId(),
					entidad);
		} catch (EstructuraOrganizativaException e) {
			throw e;
		} catch (Exception e) {
			throw new EstructuraOrganizativaException(e.getMessage());
		}
		return archId;
	}

	public void eliminarArchivador(int userId, int archiveId, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaArchiveManager archiveManager = new EstructuraOrganizativaArchiveManager();
		try {
			archiveManager.deleteArchive(userId, archiveId, entidad);
		} catch (EstructuraOrganizativaException e) {
			throw e;
		} catch (Exception e) {
			throw new EstructuraOrganizativaException(e.getMessage());
		}
	}

	private ArchiveFlds getArchiveFields(
			ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFlds doArchiveFlds)
			throws Exception {
		ArchiveFlds archiveFlds = ObjFactory.createArchiveFlds();
		for (int i = 0; i < doArchiveFlds.count(); i++) {
			ArchiveFld archiveFld = new ArchiveFldImpl(doArchiveFlds.get(i)
					.getId(), doArchiveFlds.get(i).getName(), doArchiveFlds
					.get(i).getType(), doArchiveFlds.get(i).getLen(),
					doArchiveFlds.get(i).isNullable(), doArchiveFlds.get(i)
							.getColName(), doArchiveFlds.get(i).isDoc(),
					doArchiveFlds.get(i).isMult(), doArchiveFlds.get(i)
							.getRemarks());
			archiveFlds.addFld(archiveFld);
		}
		return archiveFlds;
	}

	private ArchiveIdxs getArchiveIdxs(
			ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveIdxs doArchIdxs)
			throws Exception {
		ArchiveIdxs archiveIdxs = ObjFactory.createArchiveIdxs();
		for (int i = 0; i < doArchIdxs.count(); i++) {
			ArchiveIdx archiveidx = new ArchiveIdxImpl(doArchIdxs.get(i)
					.getId(), doArchIdxs.get(i).getName(), doArchIdxs.get(i)
					.isUnique(), doArchIdxs.get(i).getFldsId());
			archiveIdxs.addIdx(archiveidx);
		}
		return archiveIdxs;
	}

	public void eliminarUsuarioLdap(int id, String entidad)
			throws EstructuraOrganizativaException {
		EstructuraOrganizativaUsuarioManager manager = new EstructuraOrganizativaUsuarioManager();
		try {
			manager.eliminarUsuarioLdap(id, entidad);
		} catch (Exception e) {
			logger.error("Error al eliminar usuario LDAP.");
			throw new EstructuraOrganizativaException(
					EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION);
		}
	}
}