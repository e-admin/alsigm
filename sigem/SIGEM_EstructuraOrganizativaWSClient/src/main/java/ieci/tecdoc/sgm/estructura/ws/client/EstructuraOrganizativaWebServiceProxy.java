package ieci.tecdoc.sgm.estructura.ws.client;

import ieci.tecdoc.sgm.estructura.ws.client.axis.EstructuraOrganizativaWebService_PortType;
import ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioLdap;

import java.rmi.RemoteException;

public class EstructuraOrganizativaWebServiceProxy implements EstructuraOrganizativaWebService_PortType {
  private String _endpoint = null;
  private EstructuraOrganizativaWebService_PortType estructuraOrganizativaWebService = null;

  public EstructuraOrganizativaWebServiceProxy() {
    _initEstructuraOrganizativaWebServiceProxy();
  }

  private void _initEstructuraOrganizativaWebServiceProxy() {
    try {
      estructuraOrganizativaWebService = (new ieci.tecdoc.sgm.estructura.ws.client.axis.EstructuraOrganizativaWebServiceServiceLocator()).getEstructuraOrganizativaWebService();
      if (estructuraOrganizativaWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)estructuraOrganizativaWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)estructuraOrganizativaWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }

    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }

  public String getEndpoint() {
    return _endpoint;
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (estructuraOrganizativaWebService != null)
      ((javax.xml.rpc.Stub)estructuraOrganizativaWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

  }

  public ieci.tecdoc.sgm.estructura.ws.client.axis.EstructuraOrganizativaWebService_PortType getEstructuraOrganizativaWebService() {
    if (estructuraOrganizativaWebService == null)
      _initEstructuraOrganizativaWebServiceProxy();
    return estructuraOrganizativaWebService;
  }

  public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios getUsuariosDepartamento(int idDept, java.lang.String entidad) throws java.rmi.RemoteException{
    if (estructuraOrganizativaWebService == null)
      _initEstructuraOrganizativaWebServiceProxy();
    return estructuraOrganizativaWebService.getUsuariosDepartamento(idDept, entidad);
  }

  public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuario getUsuarioPorId(int idUser, java.lang.String entidad) throws java.rmi.RemoteException{
    if (estructuraOrganizativaWebService == null)
      _initEstructuraOrganizativaWebServiceProxy();
    return estructuraOrganizativaWebService.getUsuarioPorId(idUser, entidad);
  }

  public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuario getUsuarioPorNombre(java.lang.String nameUser, java.lang.String entidad) throws java.rmi.RemoteException{
    if (estructuraOrganizativaWebService == null)
      _initEstructuraOrganizativaWebServiceProxy();
    return estructuraOrganizativaWebService.getUsuarioPorNombre(nameUser, entidad);
  }

  public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuario getUsuarioPorIdCertificado(java.lang.String idCert, java.lang.String entidad) throws java.rmi.RemoteException{
	    if (estructuraOrganizativaWebService == null)
	      _initEstructuraOrganizativaWebServiceProxy();
	    return estructuraOrganizativaWebService.getUsuarioPorIdCertificado(idCert, entidad);
	  }

  public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios getUsuariosAplicacion(int aplicacion, boolean sinPermisos, boolean usuarios, boolean superusuarios, java.lang.String entidad) throws java.rmi.RemoteException{
    if (estructuraOrganizativaWebService == null)
      _initEstructuraOrganizativaWebServiceProxy();
    return estructuraOrganizativaWebService.getUsuariosAplicacion(aplicacion, sinPermisos, usuarios, superusuarios, entidad);
  }

  public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios getUsuariosGrupo(int idGrupo, java.lang.String entidad) throws java.rmi.RemoteException{
    if (estructuraOrganizativaWebService == null)
      _initEstructuraOrganizativaWebServiceProxy();
    return estructuraOrganizativaWebService.getUsuariosGrupo(idGrupo, entidad);
  }

  public ieci.tecdoc.sgm.estructura.ws.client.axis.Departamentos getDepartamentosPorPadre(int idDeptPadre, java.lang.String entidad) throws java.rmi.RemoteException{
    if (estructuraOrganizativaWebService == null)
      _initEstructuraOrganizativaWebServiceProxy();
    return estructuraOrganizativaWebService.getDepartamentosPorPadre(idDeptPadre, entidad);
  }

  public ieci.tecdoc.sgm.estructura.ws.client.axis.Departamentos getDepartamentos(java.lang.String entidad) throws java.rmi.RemoteException{
    if (estructuraOrganizativaWebService == null)
      _initEstructuraOrganizativaWebServiceProxy();
    return estructuraOrganizativaWebService.getDepartamentos(entidad);
  }

  public ieci.tecdoc.sgm.estructura.ws.client.axis.Grupos getGrupos(java.lang.String entidad) throws java.rmi.RemoteException{
    if (estructuraOrganizativaWebService == null)
      _initEstructuraOrganizativaWebServiceProxy();
    return estructuraOrganizativaWebService.getGrupos(entidad);
  }

  public ieci.tecdoc.sgm.estructura.ws.client.axis.Grupo getGrupo(int idGrupo, java.lang.String entidad) throws java.rmi.RemoteException{
    if (estructuraOrganizativaWebService == null)
      _initEstructuraOrganizativaWebServiceProxy();
    return estructuraOrganizativaWebService.getGrupo(idGrupo, entidad);
  }

  public ieci.tecdoc.sgm.estructura.ws.client.axis.Departamento getDepartamento(int idDepto, java.lang.String entidad) throws java.rmi.RemoteException{
    if (estructuraOrganizativaWebService == null)
      _initEstructuraOrganizativaWebServiceProxy();
    return estructuraOrganizativaWebService.getDepartamento(idDepto, entidad);
  }

  public ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoEntero crearArchivador(ieci.tecdoc.sgm.estructura.ws.client.axis.Archive archive, java.lang.String entidad) throws java.rmi.RemoteException{
    if (estructuraOrganizativaWebService == null)
      _initEstructuraOrganizativaWebServiceProxy();
    return estructuraOrganizativaWebService.crearArchivador(archive, entidad);
  }

  public ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio editarDepartamentoCompleto(ieci.tecdoc.sgm.estructura.ws.client.axis.Departamento departamento, java.lang.String entidad) throws java.rmi.RemoteException{
    if (estructuraOrganizativaWebService == null)
      _initEstructuraOrganizativaWebServiceProxy();
    return estructuraOrganizativaWebService.editarDepartamentoCompleto(departamento, entidad);
  }

  public ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio editarUsuario(ieci.tecdoc.sgm.estructura.ws.client.axis.Usuario usuario, java.lang.String entidad) throws java.rmi.RemoteException{
    if (estructuraOrganizativaWebService == null)
      _initEstructuraOrganizativaWebServiceProxy();
    return estructuraOrganizativaWebService.editarUsuario(usuario, entidad);
  }

  public ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio eliminarArchivador(int userId, int archiveId, java.lang.String entidad) throws java.rmi.RemoteException{
    if (estructuraOrganizativaWebService == null)
      _initEstructuraOrganizativaWebServiceProxy();
    return estructuraOrganizativaWebService.eliminarArchivador(userId, archiveId, entidad);
  }

  public ieci.tecdoc.sgm.estructura.ws.client.axis.Listas getListas(java.lang.String entidad) throws java.rmi.RemoteException{
    if (estructuraOrganizativaWebService == null)
      _initEstructuraOrganizativaWebServiceProxy();
    return estructuraOrganizativaWebService.getListas(entidad);
  }

	public ieci.tecdoc.sgm.estructura.ws.client.axis.GrupoLdap crearGrupoLdap(String ldapGuid, String ldapDn,
			int type, String entidad) throws java.rmi.RemoteException	{
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.crearGrupoLdap(ldapGuid, ldapDn, type, entidad);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.GrupoLdap getGrupoLdap(String ldapGuid, String entidad) throws java.rmi.RemoteException{
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.getGrupoLdap(ldapGuid, entidad);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioLdap crearUsuarioLdap(String ldapGuid, String name, int idPerfil, String entidad)throws java.rmi.RemoteException {
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.crearUsuarioLdap(ldapGuid, name, idPerfil, entidad);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioLdap getUsuarioLdapPorIdCertificado(String idCert, String entidad)throws java.rmi.RemoteException{
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.getUsuarioLdapPorIdCertificado(idCert, entidad);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioLdap getUsuarioLdapByGuid(String ldapguid, String entidad)throws java.rmi.RemoteException{
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.getUsuarioLdapByGuid(ldapguid, entidad);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioLdap getUsuarioLdapByFullName(String fullName, String entidad)throws java.rmi.RemoteException{
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.getUsuarioLdapByFullName(fullName, entidad);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuariosLdap getUsuariosLdap(String entidad) throws java.rmi.RemoteException{
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.getUsuariosLdap(entidad);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioLdap getUsuarioLdap(int id, String entidad)throws java.rmi.RemoteException {
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.getUsuarioLdap(id, entidad);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio editarUsuarioLdap(UsuarioLdap usuarioLdap, String entidad)throws java.rmi.RemoteException{
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.editarUsuarioLdap(usuarioLdap, entidad);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio eliminarUsuarioLdap(int id, String entidad)throws java.rmi.RemoteException{
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.eliminarUsuarioLdap(id, entidad);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuariosLdap getUsuariosLdapYaAsociados(
			int[] idsUser, int idOfic, String entidad)throws java.rmi.RemoteException{
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.getUsuariosLdapYaAsociados(idsUser, idOfic, entidad);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoCadena getUsuarioLdapBasicById(int id, String entidad)throws java.rmi.RemoteException{
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.getUsuarioLdapBasicById(id, entidad);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios getUsuarios(int aplicacion, int[] idsUser,
			boolean superusers, String entidad) throws java.rmi.RemoteException{
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.getUsuarios(aplicacion, idsUser, superusers, entidad);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios getUsuariosAplicacionPorDepartamento(int aplicacion,
			int[] deptId, boolean sinPermisos, boolean users, boolean superusuarios, String entidad)throws java.rmi.RemoteException {
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.getUsuariosAplicacionPorDepartamento(aplicacion,
					deptId, sinPermisos, users, superusuarios, entidad);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios getUsuariosAsociation(int aplicacion, int deptId,
			boolean superusers, String entidad) throws java.rmi.RemoteException{
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.getUsuariosAsociation(aplicacion, deptId, superusers, entidad);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios getUsuariosYaAsociados(int[] idsUser, int idOfic, String entidad)throws java.rmi.RemoteException {
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.getUsuariosYaAsociados(idsUser, idOfic, entidad);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.Departamento getDepartamentoLite(int arg0, String arg1)
			throws RemoteException {
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.getDepartamentoLite(arg0, arg1);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.GrupoLdap getGrupoLdapById(int arg0, String arg1)
			throws RemoteException {
		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.getGrupoLdapById(arg0, arg1);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuariosLdap getUsuariosLdapAplicacion(
			int aplicacion, int[] idsUser, boolean superusers, String entidad)
			throws RemoteException {

		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.getUsuariosLdapAplicacion(aplicacion,
				idsUser, superusers, entidad);
	}

	public ieci.tecdoc.sgm.estructura.ws.client.axis.UsuariosLdap getUsuariosLdapAplicacionPorDepartamento(
			int aplicacion, int[] deptId, boolean sinPermisos,
			boolean usuarios, boolean superusuarios, String entidad)
			throws RemoteException {

		if (estructuraOrganizativaWebService == null)
			_initEstructuraOrganizativaWebServiceProxy();
		return estructuraOrganizativaWebService.getUsuariosLdapAplicacionPorDepartamento(aplicacion,
				deptId, sinPermisos, usuarios, superusuarios, entidad);
	}

}