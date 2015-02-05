/**
 * EstructuraOrganizativaWebService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService;

public interface EstructuraOrganizativaWebService_PortType extends java.rmi.Remote {
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios getUsuariosDepartamento(int idDept, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario getUsuarioPorId(int idUser, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario getUsuarioPorNombre(java.lang.String nameUser, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario getUsuarioPorIdCertificado(java.lang.String idCert, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios getUsuariosAplicacion(int aplicacion, boolean sinPermisos, boolean usuarios, boolean superusuarios, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios getUsuariosGrupo(int idGrupo, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamentos getDepartamentosPorPadre(int idDeptPadre, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Grupos getGrupos(java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamentos getDepartamentos(java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Grupo getGrupo(int idGrupo, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento getDepartamento(int idDepto, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoEntero crearArchivador(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Archive archive, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio editarDepartamentoCompleto(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento departamento, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio editarUsuario(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuario usuario, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio eliminarArchivador(int userId, int archiveId, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Listas getListas(java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios getUsuarios(int aplicacion, int[] idsUser, boolean superusers, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios getUsuariosAsociation(int aplicacion, int deptId, boolean superusers, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios getUsuariosYaAsociados(int[] idsUser, int idOfic, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Usuarios getUsuariosAplicacionPorDepartamento(int aplicacion, int[] deptId, boolean sinPermisos, boolean users, boolean superusuarios, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap crearGrupoLdap(java.lang.String ldapGuid, java.lang.String ldapDn, int type, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap getGrupoLdap(java.lang.String ldapGuid, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap crearUsuarioLdap(java.lang.String ldapGuid, java.lang.String name, int idPerfil, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap getUsuarioLdapByGuid(java.lang.String ldapguid, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap getUsuarioLdapByFullName(java.lang.String fullName, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap getUsuarioLdapPorIdCertificado(java.lang.String idCert, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap getUsuariosLdap(java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap getUsuarioLdap(int id, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap getUsuariosLdapYaAsociados(int[] idsUser, int idOfic, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio editarUsuarioLdap(es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuarioLdap usuarioLdap, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoServicio eliminarUsuarioLdap(int id, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.RetornoCadena getUsuarioLdapBasicById(int id, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.Departamento getDepartamentoLite(int arg0, java.lang.String arg1) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.GrupoLdap getGrupoLdapById(int arg0, java.lang.String arg1) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap getUsuariosLdapAplicacionPorDepartamento(int aplicacion, int[] deptId, boolean sinPermisos, boolean usuarios, boolean superusuarios, java.lang.String entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.EstructuraOrganizativaWebService.UsuariosLdap getUsuariosLdapAplicacion(int aplicacion, int[] idsUser, boolean superusers, java.lang.String entidad) throws java.rmi.RemoteException;
}
