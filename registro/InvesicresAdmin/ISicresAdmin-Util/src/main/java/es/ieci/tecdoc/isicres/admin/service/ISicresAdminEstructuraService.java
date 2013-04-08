package es.ieci.tecdoc.isicres.admin.service;

import es.ieci.tecdoc.isicres.admin.estructura.beans.Archive;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Departamento;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Departamentos;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Grupo;
import es.ieci.tecdoc.isicres.admin.estructura.beans.GrupoLdap;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Grupos;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Listas;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Usuario;
import es.ieci.tecdoc.isicres.admin.estructura.beans.UsuarioLdap;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Usuarios;
import es.ieci.tecdoc.isicres.admin.estructura.beans.UsuariosLdap;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminEstructuraException;





public interface ISicresAdminEstructuraService {

	public Usuarios getUsuariosDepartamento(int idDept, String entidad) throws ISicresAdminEstructuraException;

	public Usuario getUsuario(int idUser, String entidad) throws ISicresAdminEstructuraException;

	public UsuarioLdap getUsuarioLdapByGuid(String ldapguid, String entidad) throws ISicresAdminEstructuraException;

	public UsuarioLdap getUsuarioLdap(int id, String entidad) throws ISicresAdminEstructuraException;

	public String getUsuarioLdapBasicById(int id, String entidad) throws ISicresAdminEstructuraException;

	public UsuarioLdap crearUsuarioLdap(String ldapGuid, String name, int idPerfil, String entidad) throws ISicresAdminEstructuraException;

	public UsuariosLdap getUsuariosLdap(String entidad) throws ISicresAdminEstructuraException;

	public Usuario getUsuario(String nameUser, String entidad) throws ISicresAdminEstructuraException;

	public Usuarios getUsuarios(int aplicacion, int idsUser[], boolean superusers, String entidad) throws ISicresAdminEstructuraException;

	public UsuariosLdap getUsuariosLdap(int aplicacion, int idsUser[],
			boolean superusers, String entidad)
			throws ISicresAdminEstructuraException;

	public Usuarios getUsuariosAsociation(int aplicacion, int deptId, boolean superusers, String entidad) throws ISicresAdminEstructuraException;

	public Usuarios getUsuariosYaAsociados(int []idsUser, int idOfic, String entidad) throws ISicresAdminEstructuraException;

	public UsuariosLdap getUsuariosLdapYaAsociados(int []idsUser, int idOfic, String entidad) throws ISicresAdminEstructuraException;

	public Usuarios getUsuariosAplicacion(int aplicacion, boolean sinPermisos, boolean usuarios, boolean superusuarios, String entidad) throws ISicresAdminEstructuraException;

	public UsuariosLdap getUsuariosLdapAplicacion(
			int aplicacion, boolean sinPermisos, boolean usuarios,
			boolean superusuarios, String entidad)
			throws ISicresAdminEstructuraException;

	public Usuarios getUsuariosAplicacionPorDepartamento(int aplicacion, int deptId[], boolean sinPermisos, boolean usuarios, boolean superusuarios, String entidad) throws ISicresAdminEstructuraException;

	public UsuariosLdap getUsuariosLdapAplicacionPorDepartamento(int aplicacion,
			int deptId[], boolean sinPermisos, boolean usuarios,
			boolean superusuarios, String entidad)
			throws ISicresAdminEstructuraException;

	public Usuarios getUsuariosGrupo(int idGrupo, String entidad)throws ISicresAdminEstructuraException;

	public Departamentos getDepartamentos(int idDeptPadre, String entidad)throws ISicresAdminEstructuraException;

	public Grupos getGrupos(String entidad) throws ISicresAdminEstructuraException;

	public GrupoLdap getGrupoLdap(String ldapGuid, String entidad) throws ISicresAdminEstructuraException;

	public GrupoLdap getGrupoLdapById(int id, String entidad) throws ISicresAdminEstructuraException;

	public GrupoLdap getGrupoLdapByDeptId(int deptId, String entidad)
			throws ISicresAdminEstructuraException;

	public GrupoLdap crearGrupoLdap(String ldapGuid, String ldapDn, int type, String entidad) throws ISicresAdminEstructuraException;

	public Departamentos getDepartamentos(String entidad)throws ISicresAdminEstructuraException;

	public Grupo getGrupo(int idGrupo, String entidad)throws ISicresAdminEstructuraException;

	public Departamento getDepartamento(int idDepto, String entidad)throws ISicresAdminEstructuraException;

	public Departamento getDepartamentoLite(int idDepto, String entidad)throws ISicresAdminEstructuraException;

	public void editarDepartamento(Departamento departamento, String entidad)throws ISicresAdminEstructuraException;

	public void editarUsuario(Usuario usuario, String entidad)throws ISicresAdminEstructuraException;

	public void editarUsuarioLdap(UsuarioLdap usuarioLdap, String entidad)throws ISicresAdminEstructuraException;

	public void eliminarUsuarioLdap(int id, String entidad)throws ISicresAdminEstructuraException;

	public Listas getListas (String entidad) throws ISicresAdminEstructuraException;

	public int crearArchivador(Archive archive, String entidad) throws ISicresAdminEstructuraException;

	public void eliminarArchivador(int userId, int archiveId, String entidad) throws ISicresAdminEstructuraException;

}
