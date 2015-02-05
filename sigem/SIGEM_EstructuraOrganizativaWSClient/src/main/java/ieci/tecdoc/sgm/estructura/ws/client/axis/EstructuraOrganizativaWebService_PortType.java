/**
 * EstructuraOrganizativaWebService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.estructura.ws.client.axis;

public interface EstructuraOrganizativaWebService_PortType extends java.rmi.Remote {
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios getUsuariosDepartamento(int idDept, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuario getUsuarioPorId(int idUser, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuario getUsuarioPorNombre(java.lang.String nameUser, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuario getUsuarioPorIdCertificado(java.lang.String idCert, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios getUsuariosAplicacion(int aplicacion, boolean sinPermisos, boolean usuarios, boolean superusuarios, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios getUsuariosGrupo(int idGrupo, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Departamentos getDepartamentosPorPadre(int idDeptPadre, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Grupos getGrupos(java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Departamentos getDepartamentos(java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Grupo getGrupo(int idGrupo, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Departamento getDepartamento(int idDepto, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoEntero crearArchivador(ieci.tecdoc.sgm.estructura.ws.client.axis.Archive archive, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio editarDepartamentoCompleto(ieci.tecdoc.sgm.estructura.ws.client.axis.Departamento departamento, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio editarUsuario(ieci.tecdoc.sgm.estructura.ws.client.axis.Usuario usuario, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio eliminarArchivador(int userId, int archiveId, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Listas getListas(java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios getUsuarios(int aplicacion, int[] idsUser, boolean superusers, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios getUsuariosAsociation(int aplicacion, int deptId, boolean superusers, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios getUsuariosYaAsociados(int[] idsUser, int idOfic, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios getUsuariosAplicacionPorDepartamento(int aplicacion, int[] deptId, boolean sinPermisos, boolean users, boolean superusuarios, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.GrupoLdap crearGrupoLdap(java.lang.String ldapGuid, java.lang.String ldapDn, int type, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.GrupoLdap getGrupoLdap(java.lang.String ldapGuid, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioLdap crearUsuarioLdap(java.lang.String ldapGuid, java.lang.String name, int idPerfil, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioLdap getUsuarioLdapByGuid(java.lang.String ldapguid, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuariosLdap getUsuariosLdap(java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioLdap getUsuarioLdap(int id, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioLdap getUsuarioLdapByFullName(java.lang.String fullName, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioLdap getUsuarioLdapPorIdCertificado(java.lang.String idCert, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuariosLdap getUsuariosLdapYaAsociados(int[] idsUser, int idOfic, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio editarUsuarioLdap(ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioLdap usuarioLdap, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio eliminarUsuarioLdap(int id, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoCadena getUsuarioLdapBasicById(int id, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.Departamento getDepartamentoLite(int arg0, java.lang.String arg1) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.GrupoLdap getGrupoLdapById(int arg0, java.lang.String arg1) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuariosLdap getUsuariosLdapAplicacionPorDepartamento(int aplicacion, int[] deptId, boolean sinPermisos, boolean usuarios, boolean superusuarios, java.lang.String entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuariosLdap getUsuariosLdapAplicacion(int aplicacion, int[] idsUser, boolean superusers, java.lang.String entidad) throws java.rmi.RemoteException;
}
