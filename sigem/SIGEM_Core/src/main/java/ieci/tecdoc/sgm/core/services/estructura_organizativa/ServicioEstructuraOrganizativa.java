package ieci.tecdoc.sgm.core.services.estructura_organizativa;

import ieci.tecdoc.sgm.core.services.estructura_organizativa.EstructuraOrganizativaException;



public interface ServicioEstructuraOrganizativa {

	public Usuarios getUsuariosDepartamento(int idDept, String entidad) throws EstructuraOrganizativaException;

	public Usuario getUsuario(int idUser, String entidad) throws EstructuraOrganizativaException;

	public UsuarioLdap getUsuarioLdapByGuid(String ldapguid, String entidad) throws EstructuraOrganizativaException;

	public UsuarioLdap getUsuarioLdap(int id, String entidad) throws EstructuraOrganizativaException;

	public UsuarioLdap getUsuarioLdapByFullName(String fullName, String entidad) throws EstructuraOrganizativaException;

	public String getUsuarioLdapBasicById(int id, String entidad) throws EstructuraOrganizativaException;

	public UsuarioLdap crearUsuarioLdap(String ldapGuid, String name, int idPerfil, String entidad) throws EstructuraOrganizativaException;

	public UsuariosLdap getUsuariosLdap(String entidad) throws EstructuraOrganizativaException;

	public Usuario getUsuario(String nameUser, String entidad) throws EstructuraOrganizativaException;

	public Usuario getUsuarioByIdCert(String idCert, String entidad) throws EstructuraOrganizativaException;

	public UsuarioLdap getUsuarioLdapByIdCert(String idCert, String entidad) throws EstructuraOrganizativaException ;

	public Usuarios getUsuarios(int aplicacion, int idsUser[], boolean superusers, String entidad) throws EstructuraOrganizativaException;

	public UsuariosLdap getUsuariosLdap(int aplicacion, int idsUser[],
			boolean superusers, String entidad)
			throws EstructuraOrganizativaException;

	public Usuarios getUsuariosAsociation(int aplicacion, int deptId, boolean superusers, String entidad) throws EstructuraOrganizativaException;

	public Usuarios getUsuariosYaAsociados(int []idsUser, int idOfic, String entidad) throws EstructuraOrganizativaException;

	public UsuariosLdap getUsuariosLdapYaAsociados(int []idsUser, int idOfic, String entidad) throws EstructuraOrganizativaException;

	public Usuarios getUsuariosAplicacion(int aplicacion, boolean sinPermisos, boolean usuarios, boolean superusuarios, String entidad) throws EstructuraOrganizativaException;

	public UsuariosLdap getUsuariosLdapAplicacion(
			int aplicacion, boolean sinPermisos, boolean usuarios,
			boolean superusuarios, String entidad)
			throws EstructuraOrganizativaException;

	public Usuarios getUsuariosAplicacionPorDepartamento(int aplicacion, int deptId[], boolean sinPermisos, boolean usuarios, boolean superusuarios, String entidad) throws EstructuraOrganizativaException;

	public UsuariosLdap getUsuariosLdapAplicacionPorDepartamento(int aplicacion,
			int deptId[], boolean sinPermisos, boolean usuarios,
			boolean superusuarios, String entidad)
			throws EstructuraOrganizativaException;

	public Usuarios getUsuariosGrupo(int idGrupo, String entidad)throws EstructuraOrganizativaException;

	public Departamentos getDepartamentos(int idDeptPadre, String entidad)throws EstructuraOrganizativaException;

	public Grupos getGrupos(String entidad) throws EstructuraOrganizativaException;

	public GrupoLdap getGrupoLdap(String ldapGuid, String entidad) throws EstructuraOrganizativaException;

	public GrupoLdap getGrupoLdapById(int id, String entidad) throws EstructuraOrganizativaException;

	public GrupoLdap getGrupoLdapByDeptId(int deptId, String entidad)
			throws EstructuraOrganizativaException;

	public GrupoLdap crearGrupoLdap(String ldapGuid, String ldapDn, int type, String entidad) throws EstructuraOrganizativaException;

	public Departamentos getDepartamentos(String entidad)throws EstructuraOrganizativaException;

	public Grupo getGrupo(int idGrupo, String entidad)throws EstructuraOrganizativaException;

	public Departamento getDepartamento(int idDepto, String entidad)throws EstructuraOrganizativaException;

	public Departamento getDepartamentoLite(int idDepto, String entidad)throws EstructuraOrganizativaException;

	public void editarDepartamento(Departamento departamento, String entidad)throws EstructuraOrganizativaException;

	public void editarUsuario(Usuario usuario, String entidad)throws EstructuraOrganizativaException;

	public void editarUsuarioLdap(UsuarioLdap usuarioLdap, String entidad)throws EstructuraOrganizativaException;

	public void eliminarUsuarioLdap(int id, String entidad)throws EstructuraOrganizativaException;

	public Listas getListas (String entidad) throws EstructuraOrganizativaException;

	public int crearArchivador(Archive archive, String entidad) throws EstructuraOrganizativaException;

	public void eliminarArchivador(int userId, int archiveId, String entidad) throws EstructuraOrganizativaException;

}
