package ieci.tecdoc.sgm.estructura.ws.client;

import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.EstructuraOrganizativaException;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.ServicioEstructuraOrganizativa;
import ieci.tecdoc.sgm.estructura.ws.client.axis.Archive;
import ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveFld;
import ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveFlds;
import ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveIdx;
import ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveIdxs;
import ieci.tecdoc.sgm.estructura.ws.client.axis.ArchiveMisc;
import ieci.tecdoc.sgm.estructura.ws.client.axis.Departamento;
import ieci.tecdoc.sgm.estructura.ws.client.axis.Departamentos;
import ieci.tecdoc.sgm.estructura.ws.client.axis.EstructuraOrganizativaWebService_PortType;
import ieci.tecdoc.sgm.estructura.ws.client.axis.Grupo;
import ieci.tecdoc.sgm.estructura.ws.client.axis.GrupoLdap;
import ieci.tecdoc.sgm.estructura.ws.client.axis.Grupos;
import ieci.tecdoc.sgm.estructura.ws.client.axis.Lista;
import ieci.tecdoc.sgm.estructura.ws.client.axis.Listas;
import ieci.tecdoc.sgm.estructura.ws.client.axis.PerfilUsuario;
import ieci.tecdoc.sgm.estructura.ws.client.axis.PerfilesGenericos;
import ieci.tecdoc.sgm.estructura.ws.client.axis.PerfilesUsuario;
import ieci.tecdoc.sgm.estructura.ws.client.axis.Permiso;
import ieci.tecdoc.sgm.estructura.ws.client.axis.Permisos;
import ieci.tecdoc.sgm.estructura.ws.client.axis.PermisosGenericos;
import ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoCadena;
import ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoEntero;
import ieci.tecdoc.sgm.estructura.ws.client.axis.RetornoServicio;
import ieci.tecdoc.sgm.estructura.ws.client.axis.Usuario;
import ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioBasico;
import ieci.tecdoc.sgm.estructura.ws.client.axis.UsuarioLdap;
import ieci.tecdoc.sgm.estructura.ws.client.axis.Usuarios;
import ieci.tecdoc.sgm.estructura.ws.client.axis.UsuariosBasicos;
import ieci.tecdoc.sgm.estructura.ws.client.axis.UsuariosLdap;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

public class EstructuraOrganizativaWSRemoteClient implements ServicioEstructuraOrganizativa {

	private EstructuraOrganizativaWebService_PortType service;


	public EstructuraOrganizativaWebService_PortType getService() {
		return service;
	}

	public void setService(EstructuraOrganizativaWebService_PortType service) {
		this.service = service;
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento getDepartamento(int idDepto, String entidad) throws EstructuraOrganizativaException {
		try{
			Departamento oRetorno =  getService().getDepartamento(idDepto, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getDepartamentoServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamentos getDepartamentos(int idDeptPadre, String entidad) throws EstructuraOrganizativaException {
		try{
			Departamentos oRetorno =  getService().getDepartamentosPorPadre(idDeptPadre, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getDepartamentosServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamentos getDepartamentos(String entidad) throws EstructuraOrganizativaException {
		try{
			Departamentos oRetorno =  getService().getDepartamentos(entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getDepartamentosServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupo getGrupo(int idGrupo, String entidad) throws EstructuraOrganizativaException {
		try{
			Grupo oRetorno =  getService().getGrupo(idGrupo, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getGrupoServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupos getGrupos(String entidad) throws EstructuraOrganizativaException {
		try{
			Grupos oRetorno =  getService().getGrupos(entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getGruposServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario getUsuario(int idUser, String entidad) throws EstructuraOrganizativaException {
		try{
			Usuario oRetorno =  getService().getUsuarioPorId(idUser, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuarioServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario getUsuario(String nameUser, String entidad) throws EstructuraOrganizativaException {
		try{
			Usuario oRetorno =  getService().getUsuarioPorNombre(nameUser, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuarioServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario getUsuarioByIdCert(String idCert, String entidad) throws EstructuraOrganizativaException {
		try{
			Usuario oRetorno =  getService().getUsuarioPorIdCertificado(idCert, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuarioServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios getUsuariosAplicacion(int aplicacion, boolean sinPermisos, boolean usuarios, boolean superusuarios, String entidad) throws EstructuraOrganizativaException {
		try{
			Usuarios oRetorno =  getService().getUsuariosAplicacion(aplicacion, sinPermisos, usuarios, superusuarios, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuariosServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios getUsuariosDepartamento(int idDept, String entidad) throws EstructuraOrganizativaException {
		try{
			Usuarios oRetorno =  getService().getUsuariosDepartamento(idDept, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuariosServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios getUsuariosGrupo(int idGrupo, String entidad) throws EstructuraOrganizativaException {
		try{
			Usuarios oRetorno =  getService().getUsuariosGrupo(idGrupo, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuariosServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public int crearArchivador(ieci.tecdoc.sgm.core.services.estructura_organizativa.Archive archive, String entidad) throws EstructuraOrganizativaException {
		try{
			RetornoEntero oRetorno =  getService().crearArchivador(getArchivadorWS(archive), entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRetornoEnteroServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void editarDepartamento(ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento departamento, String entidad) throws EstructuraOrganizativaException {
		try{
			RetornoServicio oRetorno =  getService().editarDepartamentoCompleto(getDepartamentoWS(departamento), entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void editarUsuario(ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario usuario, String entidad) throws EstructuraOrganizativaException {
		try{
			RetornoServicio oRetorno =  getService().editarUsuario(getUsuarioWS(usuario), entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void eliminarArchivador(int userId, int archiveId, String entidad) throws EstructuraOrganizativaException {
		try{
			RetornoServicio oRetorno =  getService().eliminarArchivador(userId, archiveId, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.Listas getListas(String entidad) throws EstructuraOrganizativaException {
		try{
			Listas oRetorno =  getService().getListas(entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getListasServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	private EstructuraOrganizativaException getEstructuraOrganizativaException(IRetornoServicio oReturn){
		return new EstructuraOrganizativaException(Long.valueOf(oReturn.getErrorCode()).longValue());
	}

	private Departamento getDepartamentoWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento oDepartamento) {
		if (oDepartamento == null)
			return null;

		Departamento poDepartamento = new Departamento();

		poDepartamento.setAdminUsers(getUsuariosBasicosWS(oDepartamento.get_adminUsers()));
		Calendar creation = Calendar.getInstance();
		if (oDepartamento.get_creationDate() != null) {
			creation.setTime(oDepartamento.get_creationDate());
			poDepartamento.setCreationDate(creation);
		} else {
			poDepartamento.setCreationDate(null);
		}
		poDepartamento.setCreatorId(oDepartamento.get_creatorId());
		poDepartamento.setDescription(oDepartamento.get_description());
		poDepartamento.setId(oDepartamento.get_id());
		poDepartamento.setManagerId(oDepartamento.get_managerId());
		poDepartamento.setName(oDepartamento.get_name());
		poDepartamento.setParentId(oDepartamento.get_parentId());
		poDepartamento.setPerms(getPermisosGenericosWS(oDepartamento.get_permsImpl()));
		poDepartamento.setType(oDepartamento.get_type());
		Calendar update = Calendar.getInstance();
		if (oDepartamento.get_updateDate() != null) {
			update.setTime(oDepartamento.get_updateDate());
			poDepartamento.setUpdateDate(update);
		} else {
			poDepartamento.setUpdateDate(null);
		}
		poDepartamento.setUpdaterId(oDepartamento.get_updaterId());
		poDepartamento.setUserConnected(oDepartamento.get_userConnected());
		poDepartamento.setUsers(getUsuariosWS(oDepartamento.get_users()));

		return poDepartamento;
	}

	private UsuariosBasicos getUsuariosBasicosWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosBasicos oUsuariosBasicos) {
		if (oUsuariosBasicos == null || oUsuariosBasicos.count() == 0)
			return new UsuariosBasicos();

		UsuariosBasicos poUsuariosBasicos = new UsuariosBasicos();
		UsuarioBasico[] poArrayUsuariosBasicos = new UsuarioBasico[oUsuariosBasicos.count()];

		for(int i=0; i<oUsuariosBasicos.count(); i++)
			poArrayUsuariosBasicos[i] = getUsuarioBasicoWS((ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioBasico)oUsuariosBasicos.get(i));
		poUsuariosBasicos.setUsuariosBasicos(poArrayUsuariosBasicos);

		return poUsuariosBasicos;
	}

	private UsuarioBasico getUsuarioBasicoWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioBasico oUsuarioBasico) {
		if (oUsuarioBasico == null)
			return null;

		UsuarioBasico poUsuarioBasico = new UsuarioBasico();

		poUsuarioBasico.setId(oUsuarioBasico.get_id());
		poUsuarioBasico.setName(oUsuarioBasico.get_name());

		return poUsuarioBasico;
	}

	private PermisosGenericos getPermisosGenericosWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.PermisosGenericos oPermisosGenericos) {
		if (oPermisosGenericos == null || oPermisosGenericos.getPermisos() == null ||
				oPermisosGenericos.getPermisos().getList() == null || oPermisosGenericos.getPermisos().getList().size() == 0)
			return new PermisosGenericos();

		PermisosGenericos poPermisosGenericos = new PermisosGenericos();
		Permisos poPermisos = new Permisos();
		Permiso[] poArrayPermisos = new Permiso[oPermisosGenericos.getPermisos().getList().size()];

		for(int i=0; i<oPermisosGenericos.getPermisos().getList().size(); i++)
			poArrayPermisos[i] = getPermisoWS((ieci.tecdoc.sgm.core.services.estructura_organizativa.Permiso)oPermisosGenericos.getPermisos().getList().get(i));
		poPermisos.setPermisos(poArrayPermisos);
		poPermisosGenericos.setPermisos(poPermisos);

		return poPermisosGenericos;
	}

	private Permiso getPermisoWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.Permiso oPermiso) {
		if (oPermiso == null)
			return null;

		Permiso poPermiso = new Permiso();

		poPermiso.setDest(oPermiso.get_dest());
		poPermiso.setId(oPermiso.get_id());
		poPermiso.setPerm(oPermiso.get_perm());
		poPermiso.setProduct(oPermiso.get_product());

		return poPermiso;
	}

	private Usuarios getUsuariosWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios oUsuarios) {
		if (oUsuarios == null || oUsuarios.get_list() == null || oUsuarios.get_list().size() == 0)
			return new Usuarios();

		Usuarios poUsuarios = new Usuarios();
		Usuario[] poArrayUsuarios = new Usuario[oUsuarios.get_list().size()];

		for(int i=0; i<oUsuarios.get_list().size(); i++)
			poArrayUsuarios[i] = getUsuarioWS((ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario)oUsuarios.get_list().get(i));
		poUsuarios.setUsuarios(poArrayUsuarios);

		return poUsuarios;
	}

	private Usuario getUsuarioWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario oUsuario) {
		if (oUsuario == null)
			return null;

		Usuario poUsuario = new Usuario();

		Calendar creation = Calendar.getInstance();
		if (oUsuario.get_creationDate() != null) {
			creation.setTime(oUsuario.get_creationDate());
			poUsuario.setCreationDate(creation);
		} else {
			poUsuario.setCreationDate(null);
		}
		poUsuario.setCreatorId(oUsuario.get_creatorId());
		poUsuario.setDeptId(oUsuario.get_deptId());
		poUsuario.setDescription(oUsuario.get_description());
		poUsuario.setId(oUsuario.get_id());
		poUsuario.setIsChange(oUsuario.is_isChange());
		poUsuario.setName(oUsuario.get_name());
		poUsuario.setOldPassword(oUsuario.get_oldPassword());
		poUsuario.setPassword(oUsuario.get_password());
		poUsuario.setPerms(getPermisosGenericosWS(oUsuario.get_permsImpl()));
		poUsuario.setProfiles(getPerfilesGenericosWS(oUsuario.get_profilesImpl()));
		poUsuario.setPwdLastUpdTs(oUsuario.get_pwdLastUpdTs());
		poUsuario.setPwdmbc(oUsuario.get_pwdmbc());
		poUsuario.setPwdminlen(oUsuario.get_pwdminlen());
		poUsuario.setPwdvpcheck(oUsuario.get_pwdvpcheck());
		poUsuario.setState(oUsuario.get_state());
		Calendar update = Calendar.getInstance();
		if (oUsuario.get_updateDate() != null) {
			update.setTime(oUsuario.get_updateDate());
			poUsuario.setUpdateDate(update);
		} else {
			poUsuario.setUpdateDate(null);
		}
		poUsuario.setUpdaterId(oUsuario.get_updaterId());
		poUsuario.setUserConnected(oUsuario.get_userConnected());
		poUsuario.setWasAdmin(oUsuario.is_wasAdmin());

		return poUsuario;
	}

	private UsuarioLdap getUsuarioLdapWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap oUsuarioLdap) {
		if (oUsuarioLdap == null)
			return null;

		UsuarioLdap poUsuarioLdap = new UsuarioLdap();

		poUsuarioLdap.set_id(oUsuarioLdap.get_id());
		poUsuarioLdap.set_ldapguid(oUsuarioLdap.get_ldapguid());
		poUsuarioLdap.set_ldapfullname(oUsuarioLdap.get_ldapfullname());
		poUsuarioLdap.set_permsImpl(getPermisosGenericosWS(oUsuarioLdap.get_permsImpl()));
		poUsuarioLdap.set_profilesImpl(getPerfilesGenericosWS(oUsuarioLdap.get_profilesImpl()));

		return poUsuarioLdap;
	}

	private PerfilesGenericos getPerfilesGenericosWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.PerfilesGenericos oPerfilesGenericos) {
		if (oPerfilesGenericos == null || oPerfilesGenericos.getPerfilesUsuario() == null ||
				oPerfilesGenericos.getPerfilesUsuario().getList() == null || oPerfilesGenericos.getPerfilesUsuario().getList().size() == 0)
			return new PerfilesGenericos();

		PerfilesGenericos poPerfilesGenericos = new PerfilesGenericos();
		PerfilesUsuario poPerfilesUsuario = new PerfilesUsuario();
		PerfilUsuario[] poArrayPerfilesUsuario = new PerfilUsuario[oPerfilesGenericos.getPerfilesUsuario().getList().size()];

		for(int i=0; i<oPerfilesGenericos.getPerfilesUsuario().getList().size(); i++)
			poArrayPerfilesUsuario[i] = getPerfilUsuarioWS((ieci.tecdoc.sgm.core.services.estructura_organizativa.PerfilUsuario)oPerfilesGenericos.getPerfilesUsuario().getList().get(i));
		poPerfilesUsuario.setPerfilesUsuario(poArrayPerfilesUsuario);
		poPerfilesGenericos.setPerfilesUsuario(poPerfilesUsuario);

		return poPerfilesGenericos;
	}

	private PerfilUsuario getPerfilUsuarioWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.PerfilUsuario oPerfilUsuario) {
		if (oPerfilUsuario == null)
			return null;

		PerfilUsuario poPerfilUsuario = new PerfilUsuario();

		poPerfilUsuario.setProduct(oPerfilUsuario.get_product());
		poPerfilUsuario.setProfile(oPerfilUsuario.get_profile());
		poPerfilUsuario.setUserId(oPerfilUsuario.get_userId());

		return poPerfilUsuario;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento getDepartamentoServicio (Departamento oDepartamento) {
		if (oDepartamento == null)
			return null;

		ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento poDepartamento = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento();

		poDepartamento.set_adminUsers(getUsuariosBasicosServicio(oDepartamento.getAdminUsers()));
		if (oDepartamento.getCreationDate() != null)
			poDepartamento.set_creationDate(oDepartamento.getCreationDate().getTime());
		else
			poDepartamento.set_creationDate(null);
		poDepartamento.set_creatorId(oDepartamento.getCreatorId());
		poDepartamento.set_description(oDepartamento.getDescription());
		poDepartamento.set_id(oDepartamento.getId());
		poDepartamento.set_managerId(oDepartamento.getManagerId());
		poDepartamento.set_name(oDepartamento.getName());
		poDepartamento.set_parentId(oDepartamento.getParentId());
		poDepartamento.set_permsImpl(getPermisosGenericosServicio(oDepartamento.getPerms()));
		poDepartamento.set_type(oDepartamento.getType());
		if (oDepartamento.getUpdateDate() != null)
			poDepartamento.set_updateDate(oDepartamento.getUpdateDate().getTime());
		else
			poDepartamento.set_updateDate(null);
		poDepartamento.set_updaterId(oDepartamento.getUpdaterId());
		poDepartamento.set_userConnected(oDepartamento.getUserConnected());
		poDepartamento.set_users(getUsuariosServicio(oDepartamento.getUsers()));

		return poDepartamento;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosBasicos getUsuariosBasicosServicio (UsuariosBasicos oUsuariosBasicos) {
		if (oUsuariosBasicos == null || oUsuariosBasicos.getUsuariosBasicos() == null || oUsuariosBasicos.getUsuariosBasicos().length == 0)
			return new ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosBasicos();

		ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosBasicos poUsuariosBasicos = new ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosBasicos();

		for(int i=0; i<oUsuariosBasicos.getUsuariosBasicos().length; i++)
			poUsuariosBasicos.add(getUsuarioBasicoServicio(oUsuariosBasicos.getUsuariosBasicos()[i]));

		return poUsuariosBasicos;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioBasico getUsuarioBasicoServicio (UsuarioBasico oUsuarioBasico) {
		if (oUsuarioBasico == null)
			return null;

		ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioBasico poUsuarioBasico = new ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioBasico();

		poUsuarioBasico.set_id(oUsuarioBasico.getId());
		poUsuarioBasico.set_name(oUsuarioBasico.getName());

		return poUsuarioBasico;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.GrupoLdap getGrupoLdapServicio (GrupoLdap oGrupo) {
		if (oGrupo == null)
			return null;

		ieci.tecdoc.sgm.core.services.estructura_organizativa.GrupoLdap poGrupo = new ieci.tecdoc.sgm.core.services.estructura_organizativa.GrupoLdap();

		poGrupo.set_id(oGrupo.get_id());
		poGrupo.set_guid(oGrupo.get_guid());
		poGrupo.set_fullname(oGrupo.get_fullname());
		poGrupo.set_type(oGrupo.get_type());

		return poGrupo;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosLdap getUsuariosLdapServicio (UsuariosLdap oUsuarios) {
		if (oUsuarios == null || oUsuarios.getUsuariosLdap() == null || oUsuarios.getUsuariosLdap().length == 0)
			return new ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosLdap();

		ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosLdap poUsuarios = new ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosLdap();

		for(int i=0; i<oUsuarios.getUsuariosLdap().length; i++)
			poUsuarios.add(getUsuarioLdapServicio(oUsuarios.getUsuariosLdap()[i]));

		return poUsuarios;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap getUsuarioLdapServicio (UsuarioLdap oUsuario) {
		if (oUsuario == null)
			return null;

		ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap poUsuario = new ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap();

		poUsuario.set_id(oUsuario.get_id());
		poUsuario.set_ldapguid(oUsuario.get_ldapguid());
		poUsuario.set_ldapfullname(oUsuario.get_ldapfullname());
		poUsuario.set_permsImpl(getPermisosGenericosServicio(oUsuario.get_permsImpl()));
		poUsuario.set_profilesImpl(getPerfilesGenericosServicio(oUsuario.get_profilesImpl()));

		return poUsuario;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios getUsuariosServicio (Usuarios oUsuarios) {
		if (oUsuarios == null || oUsuarios.getUsuarios() == null || oUsuarios.getUsuarios().length == 0)
			return new ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios();

		ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios poUsuarios = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios();

		for(int i=0; i<oUsuarios.getUsuarios().length; i++)
			poUsuarios.add(getUsuarioServicio(oUsuarios.getUsuarios()[i]));

		return poUsuarios;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario getUsuarioServicio (Usuario oUsuario) {
		if (oUsuario == null)
			return null;

		ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario poUsuario = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuario();

		if (oUsuario.getCreationDate() != null)
			poUsuario.set_creationDate(oUsuario.getCreationDate().getTime());
		else
			poUsuario.set_creationDate(null);
		poUsuario.set_creatorId(oUsuario.getCreatorId());
		poUsuario.set_deptId(oUsuario.getDeptId());
		poUsuario.set_description(oUsuario.getDescription());
		poUsuario.set_id(oUsuario.getId());
		poUsuario.set_isChange(oUsuario.isIsChange());
		poUsuario.set_name(oUsuario.getName());
		poUsuario.set_oldPassword(oUsuario.getOldPassword());
		poUsuario.set_password(oUsuario.getPassword());
		poUsuario.set_permsImpl(getPermisosGenericosServicio(oUsuario.getPerms()));
		poUsuario.set_profilesImpl(getPerfilesGenericosServicio(oUsuario.getProfiles()));
		poUsuario.set_pwdLastUpdTs(oUsuario.getPwdLastUpdTs());
		poUsuario.set_pwdmbc(oUsuario.getPwdmbc());
		poUsuario.set_pwdminlen(oUsuario.getPwdminlen());
		poUsuario.set_pwdvpcheck(oUsuario.getPwdvpcheck());
		poUsuario.set_state(oUsuario.getState());
		if (oUsuario.getUpdateDate() != null)
			poUsuario.set_updateDate(oUsuario.getUpdateDate().getTime());
		else
			poUsuario.set_updateDate(null);
		poUsuario.set_updaterId(oUsuario.getUpdaterId());
		poUsuario.set_userConnected(oUsuario.getUserConnected());
		poUsuario.set_wasAdmin(oUsuario.isWasAdmin());

		return poUsuario;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.PermisosGenericos getPermisosGenericosServicio (PermisosGenericos oPermisosGenericos) {
		if (oPermisosGenericos == null || oPermisosGenericos.getPermisos() == null ||
				oPermisosGenericos.getPermisos().getPermisos() == null || oPermisosGenericos.getPermisos().getPermisos().length == 0)
			return new ieci.tecdoc.sgm.core.services.estructura_organizativa.PermisosGenericos();

		ieci.tecdoc.sgm.core.services.estructura_organizativa.PermisosGenericos poPermisosGenericos = new ieci.tecdoc.sgm.core.services.estructura_organizativa.PermisosGenericos();
		ieci.tecdoc.sgm.core.services.estructura_organizativa.Permisos poPermisos = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Permisos();

		for(int i=0; i<oPermisosGenericos.getPermisos().getPermisos().length; i++)
			poPermisos.add(getPermisoServicio(oPermisosGenericos.getPermisos().getPermisos()[i]));
		poPermisosGenericos.setPermisos(poPermisos);

		return poPermisosGenericos;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.Permiso getPermisoServicio (Permiso oPermiso) {
		if (oPermiso == null)
			return null;

		ieci.tecdoc.sgm.core.services.estructura_organizativa.Permiso poPermiso = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Permiso();

		poPermiso.set_dest(oPermiso.getDest());
		poPermiso.set_id(oPermiso.getId());
		poPermiso.set_perm(oPermiso.getPerm());
		poPermiso.set_product(oPermiso.getProduct());

		return poPermiso;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.PerfilesGenericos getPerfilesGenericosServicio (PerfilesGenericos oPerfilesGenericos) {
		if (oPerfilesGenericos == null || oPerfilesGenericos.getPerfilesUsuario() == null ||
				oPerfilesGenericos.getPerfilesUsuario().getPerfilesUsuario() == null || oPerfilesGenericos.getPerfilesUsuario().getPerfilesUsuario().length == 0)
			return new ieci.tecdoc.sgm.core.services.estructura_organizativa.PerfilesGenericos();

		ieci.tecdoc.sgm.core.services.estructura_organizativa.PerfilesGenericos poPerfilesGenericos = new ieci.tecdoc.sgm.core.services.estructura_organizativa.PerfilesGenericos();
		ieci.tecdoc.sgm.core.services.estructura_organizativa.PerfilesUsuario poPerfilesUsuario = new ieci.tecdoc.sgm.core.services.estructura_organizativa.PerfilesUsuario();

		for(int i=0; i<oPerfilesGenericos.getPerfilesUsuario().getPerfilesUsuario().length; i++)
			poPerfilesUsuario.add(getPerfilUsuarioServicio(oPerfilesGenericos.getPerfilesUsuario().getPerfilesUsuario()[i]));
		poPerfilesGenericos.setPerfilesUsuario(poPerfilesUsuario);

		return poPerfilesGenericos;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.PerfilUsuario getPerfilUsuarioServicio (PerfilUsuario oPerfilUsuario) {
		if (oPerfilUsuario == null)
			return null;

		ieci.tecdoc.sgm.core.services.estructura_organizativa.PerfilUsuario poPerfilUsuario = new ieci.tecdoc.sgm.core.services.estructura_organizativa.PerfilUsuario();

		poPerfilUsuario.set_product(oPerfilUsuario.getProduct());
		poPerfilUsuario.set_profile(oPerfilUsuario.getProfile());
		poPerfilUsuario.set_userId(oPerfilUsuario.getUserId());

		return poPerfilUsuario;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamentos getDepartamentosServicio (Departamentos oDepartamentos) {
		if (oDepartamentos == null || oDepartamentos.getDepartamentosLista() == null ||
				oDepartamentos.getDepartamentosLista().getDepartamentos() == null || oDepartamentos.getDepartamentosLista().getDepartamentos().length == 0)
			return new ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamentos();

		ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamentos poDepartamentos = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamentos();
		ieci.tecdoc.sgm.core.services.estructura_organizativa.DepartamentosLista poDepartamentosLista = new ieci.tecdoc.sgm.core.services.estructura_organizativa.DepartamentosLista();

		for(int i=0; i<oDepartamentos.getDepartamentosLista().getDepartamentos().length; i++)
			poDepartamentosLista.add(getDepartamentoServicio(oDepartamentos.getDepartamentosLista().getDepartamentos()[i]));
		poDepartamentos.setDepartamentosLista(poDepartamentosLista);

		return poDepartamentos;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupos getGruposServicio (Grupos oGrupos) {
		if (oGrupos == null || oGrupos.getGruposLista() == null ||
				oGrupos.getGruposLista().getGrupos() == null || oGrupos.getGruposLista().getGrupos().length == 0)
			return new ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupos();

		ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupos poGrupos = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupos();
		ieci.tecdoc.sgm.core.services.estructura_organizativa.GruposLista poGruposLista = new ieci.tecdoc.sgm.core.services.estructura_organizativa.GruposLista();

		for(int i=0; i<oGrupos.getGruposLista().getGrupos().length; i++)
			poGruposLista.add(getGrupoServicio(oGrupos.getGruposLista().getGrupos()[i]));
		poGrupos.setGruposLista(poGruposLista);

		return poGrupos;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupo getGrupoServicio (Grupo oGrupo) {
		if (oGrupo == null)
			return null;

		ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupo poGrupo = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupo();

		poGrupo.set_adminUsers(getUsuariosBasicosServicio(oGrupo.getAdminUsers()));
		if (oGrupo.getCreationDate() != null)
			poGrupo.set_creationDate(oGrupo.getCreationDate().getTime());
		else
			poGrupo.set_creationDate(null);
		poGrupo.set_creatorId(oGrupo.getCreatorId());
		poGrupo.set_description(oGrupo.getDescription());
		poGrupo.set_id(oGrupo.getId());
		poGrupo.set_managerId(oGrupo.getManagerId());
		poGrupo.set_name(oGrupo.getName());
		poGrupo.set_permsImpl(getPermisosGenericosServicio(oGrupo.getPerms()));
		poGrupo.set_type(oGrupo.getType());
		if (oGrupo.getUpdateDate() != null)
			poGrupo.set_updateDate(oGrupo.getUpdateDate().getTime());
		else
			poGrupo.set_updateDate(null);
		poGrupo.set_updaterId(oGrupo.getUpdaterId());
		poGrupo.set_userConnected(oGrupo.getUserConnected());
		poGrupo.set_users(getUsuariosServicio(oGrupo.getUsers()));

		return poGrupo;
	}

	private Archive getArchivadorWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.Archive oArchivador) {
		if (oArchivador == null)
			return null;

		Archive poArchivador = new Archive();

		poArchivador.setAdminUserId(oArchivador.getAdminUserId());
		poArchivador.setFldsDef(getArchivadorFldsWS(oArchivador.getFldsDef()));
		poArchivador.setFtsInContents(oArchivador.isFtsInContents());
		poArchivador.setIdxsDef(getArchivadorIdxsWS(oArchivador.getIdxsDef()));
		poArchivador.setMiscDef(getArchivadorMiscWS(oArchivador.getMiscDef()));
		poArchivador.setName(oArchivador.getName());
		poArchivador.setParentId(oArchivador.getParentId());
		poArchivador.setRemarks(oArchivador.getRemarks());
		poArchivador.setTypeId(oArchivador.getTypeId());

		return poArchivador;
	}

	private ArchiveFlds getArchivadorFldsWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFlds oArchivadorFlds) {
		if (oArchivadorFlds == null || oArchivadorFlds.count() == 0)
			return new ArchiveFlds();

		ArchiveFlds poArchivadorFlds = new ArchiveFlds();
		ArchiveFld[] poArchivadorFldsArray = new ArchiveFld[oArchivadorFlds.count()];

		for(int i=0; i<oArchivadorFlds.count(); i++)
			poArchivadorFldsArray[i] = getArchivadorFldWS((ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFld)oArchivadorFlds.get(i));
		poArchivadorFlds.setArchiveFldsList(poArchivadorFldsArray);

		return poArchivadorFlds;
	}

	private ArchiveFld getArchivadorFldWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFld oArchivadorFld) {
		if (oArchivadorFld == null)
			return null;

		ArchiveFld poArchivadorFld = new ArchiveFld();

		poArchivadorFld.setDoc(oArchivadorFld.isDoc());
		poArchivadorFld.setLen(oArchivadorFld.getLen());
		poArchivadorFld.setMult(oArchivadorFld.isMult());
		poArchivadorFld.setName(oArchivadorFld.getName());
		poArchivadorFld.setNullable(oArchivadorFld.isNullable());
		poArchivadorFld.setRemarks(oArchivadorFld.getRemarks());
		poArchivadorFld.setType(oArchivadorFld.getType());

		return poArchivadorFld;
	}

	private ArchiveIdxs getArchivadorIdxsWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveIdxs oArchivadorIdxs) {
		if (oArchivadorIdxs == null || oArchivadorIdxs.count() == 0)
			return new ArchiveIdxs();

		ArchiveIdxs poArchivadorIdxs = new ArchiveIdxs();
		ArchiveIdx[] poArchivadorIdxsArray = new ArchiveIdx[oArchivadorIdxs.count()];

		for(int i=0; i<oArchivadorIdxs.count(); i++)
			poArchivadorIdxsArray[i] = getArchivadorIdxWS((ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveIdx)oArchivadorIdxs.get(i));
		poArchivadorIdxs.setArchiveIndxsList(poArchivadorIdxsArray);

		return poArchivadorIdxs;
	}

	private ArchiveIdx getArchivadorIdxWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveIdx oArchivadorIdx) {
		if (oArchivadorIdx == null)
			return null;

		ArchiveIdx poArchivadorIdx = new ArchiveIdx();

		poArchivadorIdx.setFldsId(getFldsIdsWS(oArchivadorIdx.getFldsId()));

		return poArchivadorIdx;
	}

	private ArchiveMisc getArchivadorMiscWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveMisc oArchivadorMisc) {
		if (oArchivadorMisc == null)
			return null;

		ArchiveMisc poArchivadorMisc = new ArchiveMisc();

		poArchivadorMisc.setFdrName(oArchivadorMisc.getFdrName());
		poArchivadorMisc.setVolListId(oArchivadorMisc.getVolListId());
		poArchivadorMisc.setVolListType(oArchivadorMisc.getVolListType());

		return poArchivadorMisc;
	}

	private int[] getFldsIdsWS (ArrayList  oFldsIds) {
		if (oFldsIds == null || oFldsIds.size() == 0)
			return new int[0];

		int[] poFldsIds = new int[oFldsIds.size()];

		for(int i=0; i<oFldsIds.size(); i++)
			poFldsIds[i] = ((Integer)oFldsIds.get(i)).intValue();

		return poFldsIds;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.Listas getListasServicio (Listas oListas) {
		if (oListas == null || oListas.getListas() == null || oListas.getListas().length == 0)
			return new ieci.tecdoc.sgm.core.services.estructura_organizativa.Listas();

		ieci.tecdoc.sgm.core.services.estructura_organizativa.Listas poListas = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Listas();

		for(int i=0; i<oListas.getListas().length; i++)
			poListas.add(getListaServicio(oListas.getListas()[i]));

		return poListas;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.Lista getListaServicio (Lista oLista) {
		if (oLista == null)
			return null;

		ieci.tecdoc.sgm.core.services.estructura_organizativa.Lista poLista = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Lista();

		poLista.setCreationDate(oLista.getCreationDate().getTime());
		poLista.setCreatorId(oLista.getCreatorId());
		poLista.setFlags(oLista.getFlags());
		poLista.setId(oLista.getId());
		poLista.setName(oLista.getName());
		poLista.setRemarks(oLista.getRemarks());
		poLista.setState(oLista.getState());
		poLista.setUpdateDate(oLista.getUpdateDate().getTime());
		poLista.setUpdaterId(oLista.getUpdaterId());
		poLista.setVolId(oLista.getVolId());

		return poLista;
	}

	private int getRetornoEnteroServicio (RetornoEntero oEntero) {
		int poEntero = oEntero.getValor();

		return poEntero;
	}

	private String getRetornoCadenaServicio (RetornoCadena oCadena) {
		String poCadena = oCadena.getValor();

		return poCadena;
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios getUsuarios(int aplicacion, int[] idsUser,
			boolean superusers, String entidad)
			throws EstructuraOrganizativaException {
		try{
			Usuarios oRetorno =  getService().getUsuarios(aplicacion, idsUser, superusers, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuariosServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios getUsuariosAplicacionPorDepartamento(int aplicacion,
			int[] deptId, boolean sinPermisos, boolean usuarios,
			boolean superusuarios, String entidad)
			throws EstructuraOrganizativaException {
		try{
			Usuarios oRetorno =  getService().getUsuariosAplicacionPorDepartamento(aplicacion, deptId, sinPermisos, usuarios, superusuarios, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuariosServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios getUsuariosAsociation(int aplicacion, int deptId,
			boolean superusers, String entidad)
			throws EstructuraOrganizativaException {
		try{
			Usuarios oRetorno =  getService().getUsuariosAsociation(aplicacion, deptId, superusers, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuariosServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.Usuarios getUsuariosYaAsociados(int[] idsUser, int idOfic,
			String entidad) throws EstructuraOrganizativaException {
		try{
			Usuarios oRetorno =  getService().getUsuariosYaAsociados(idsUser, idOfic, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuariosServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.GrupoLdap crearGrupoLdap(String ldapGuid, String ldapDn,
			int type, String entidad) throws EstructuraOrganizativaException {
		try{
			GrupoLdap oRetorno =  getService().crearGrupoLdap(ldapGuid, ldapDn, type, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getGrupoLdapServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.GrupoLdap getGrupoLdap(String ldapGuid, String entidad)
			throws EstructuraOrganizativaException {
		try{
			GrupoLdap oRetorno =  getService().getGrupoLdap(ldapGuid, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getGrupoLdapServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap crearUsuarioLdap(String ldapGuid, String name, int idPerfil, String entidad) throws EstructuraOrganizativaException {
		try{
			UsuarioLdap oRetorno =  getService().crearUsuarioLdap(ldapGuid, name, idPerfil, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuarioLdapServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap getUsuarioLdapByGuid(String ldapguid, String entidad)
			throws EstructuraOrganizativaException {
		try{
			UsuarioLdap oRetorno =  getService().getUsuarioLdapByGuid(ldapguid, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuarioLdapServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosLdap getUsuariosLdap(String entidad) throws EstructuraOrganizativaException {
		try{
			UsuariosLdap oRetorno =  getService().getUsuariosLdap(entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuariosLdapServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap getUsuarioLdap(int id, String entidad)
			throws EstructuraOrganizativaException {
		try{
			UsuarioLdap oRetorno =  getService().getUsuarioLdap(id, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuarioLdapServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap getUsuarioLdapByFullName(String fullName, String entidad)
			throws EstructuraOrganizativaException {
		try{
			UsuarioLdap oRetorno =  getService().getUsuarioLdapByFullName(fullName, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuarioLdapServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap getUsuarioLdapByIdCert(String idCert, String entidad)
			throws EstructuraOrganizativaException {
		try{
			UsuarioLdap oRetorno =  getService().getUsuarioLdapPorIdCertificado(idCert, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuarioLdapServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void editarUsuarioLdap(ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap usuarioLdap, String entidad)
			throws EstructuraOrganizativaException {
		try{
			RetornoServicio oRetorno =  getService().editarUsuarioLdap(getUsuarioLdapWS(usuarioLdap), entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public void eliminarUsuarioLdap(int id, String entidad)
			throws EstructuraOrganizativaException {
		try{
			RetornoServicio oRetorno =  getService().eliminarUsuarioLdap(id, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosLdap getUsuariosLdapYaAsociados(
			int[] idsUser, int idOfic, String entidad) throws EstructuraOrganizativaException {
		try{
			UsuariosLdap oRetorno =  getService().getUsuariosLdapYaAsociados(idsUser, idOfic, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuariosLdapServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public String getUsuarioLdapBasicById(int id, String entidad)
			throws EstructuraOrganizativaException {
		try{
			RetornoCadena oRetorno =  getService().getUsuarioLdapBasicById(id, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getRetornoCadenaServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento getDepartamentoLite(
			int idDepto, String entidad) throws EstructuraOrganizativaException {
		try{
			Departamento oRetorno =  getService().getDepartamentoLite(idDepto, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getDepartamentoServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.GrupoLdap getGrupoLdapById(
			int id, String entidad) throws EstructuraOrganizativaException {
		try{
			GrupoLdap oRetorno =  getService().getGrupoLdapById(id, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getGrupoLdapServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosLdap getUsuariosLdap(
			int aplicacion, int[] idsUser, boolean superusers, String entidad)
			throws EstructuraOrganizativaException {
		try{
			UsuariosLdap oRetorno =  getService().getUsuariosLdapAplicacion(aplicacion,
					idsUser, superusers, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuariosLdapServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosLdap getUsuariosLdapAplicacionPorDepartamento(
			int aplicacion, int[] deptId, boolean sinPermisos,
			boolean usuarios, boolean superusuarios, String entidad)
			throws EstructuraOrganizativaException {
		try{
			UsuariosLdap oRetorno =  getService().getUsuariosLdapAplicacionPorDepartamento(aplicacion,
					deptId, sinPermisos, usuarios, superusuarios, entidad);
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return getUsuariosLdapServicio(oRetorno);
			}else{
				throw getEstructuraOrganizativaException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new EstructuraOrganizativaException(EstructuraOrganizativaException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.GrupoLdap getGrupoLdapByDeptId(
			int deptId, String entidad) throws EstructuraOrganizativaException {
		// TODO Auto-generated method stub
		return null;
	}

	public ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosLdap getUsuariosLdapAplicacion(
			int aplicacion, boolean sinPermisos, boolean usuarios,
			boolean superusuarios, String entidad)
			throws EstructuraOrganizativaException {
		// TODO Auto-generated method stub
		return null;
	}




}