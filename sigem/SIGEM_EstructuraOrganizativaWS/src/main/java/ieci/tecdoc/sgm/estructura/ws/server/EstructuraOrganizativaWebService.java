package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.EstructuraOrganizativaException;
import ieci.tecdoc.sgm.core.services.estructura_organizativa.ServicioEstructuraOrganizativa;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;

import java.util.ArrayList;

import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

public class EstructuraOrganizativaWebService {

	private static final Logger logger = Logger.getLogger(EstructuraOrganizativaWebService.class);

	private static final String SERVICE_NAME = ConstantesServicios.SERVICE_ESTRUCTURA_ORGANIZATIVA;

	private ServicioEstructuraOrganizativa getServicioEstructuraOrganizativa() throws SOAPException, SigemException{
		String cImpl = UtilAxis.getImplementacion(org.apache.axis.MessageContext.getCurrentContext());
		if((cImpl == null) ||("".equals(cImpl))){
			return LocalizadorServicios.getServicioEstructuraOrganizativa();
		}else{
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".").append(cImpl);
			return LocalizadorServicios.getServicioEstructuraOrganizativa(sbImpl.toString());
		}
	}


	public Usuarios getUsuariosDepartamento(int idDept, String entidad) {
		try {
			Usuarios usuarios = getUsuariosWS(
					getServicioEstructuraOrganizativa().getUsuariosDepartamento(idDept, entidad)
						);
			return (Usuarios)ServiciosUtils.completeReturnOK(usuarios);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener los usuarios del departamento.", e);
			return (Usuarios)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Usuarios()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los usuarios del departamento.", e);
			return (Usuarios)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Usuarios()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener los usuarios del departamento.", e);
			return (Usuarios)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Usuarios()));
		}
	}

	public Usuario getUsuarioPorId(int idUser, String entidad) {
		try {
			Usuario usuario = getUsuarioWS(
					getServicioEstructuraOrganizativa().getUsuario(idUser, entidad)
						);
			return (Usuario)ServiciosUtils.completeReturnOK(usuario);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener datos de usuario.", e);
			return (Usuario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Usuario()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener datos de usuario.", e);
			return (Usuario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Usuario()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener datos de usuario.", e);
			return (Usuario)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Usuario()));
		}
	}

	public Usuario getUsuarioPorNombre(String nameUser, String entidad) {
		try {
			Usuario usuario = getUsuarioWS(
					getServicioEstructuraOrganizativa().getUsuario(nameUser, entidad)
						);
			return (Usuario)ServiciosUtils.completeReturnOK(usuario);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener datos de usuario.", e);
			return (Usuario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Usuario()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener datos de usuario.", e);
			return (Usuario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Usuario()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener datos de usuario.", e);
			return (Usuario)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Usuario()));
		}
	}

	public Usuario getUsuarioPorIdCertificado(String idCert, String entidad) {
		try {
			Usuario usuario = getUsuarioWS(
					getServicioEstructuraOrganizativa().getUsuarioByIdCert(idCert, entidad)
						);
			return (Usuario)ServiciosUtils.completeReturnOK(usuario);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener datos de usuario.", e);
			return (Usuario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Usuario()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener datos de usuario.", e);
			return (Usuario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Usuario()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener datos de usuario.", e);
			return (Usuario)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Usuario()));
		}
	}

	public Usuarios getUsuariosAplicacion(int aplicacion, boolean sinPermisos, boolean usuarios, boolean superusuarios, String entidad) {
		try {
			Usuarios _usuarios = getUsuariosWS(
					getServicioEstructuraOrganizativa().getUsuariosAplicacion(aplicacion, sinPermisos, usuarios, superusuarios, entidad)
						);
			return (Usuarios)ServiciosUtils.completeReturnOK(_usuarios);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener los usuarios de aplicacion.", e);
			return (Usuarios)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Usuarios()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los usuarios de aplicacion.", e);
			return (Usuarios)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Usuarios()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener los usuarios de aplicacion.", e);
			return (Usuarios)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Usuarios()));
		}
	}

	public Usuarios getUsuariosGrupo(int idGrupo, String entidad) {
		try {
			Usuarios _usuarios = getUsuariosWS(
					getServicioEstructuraOrganizativa().getUsuariosGrupo(idGrupo, entidad)
						);
			return (Usuarios)ServiciosUtils.completeReturnOK(_usuarios);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener los usuarios del grupo.", e);
			return (Usuarios)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Usuarios()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los usuarios del grupo.", e);
			return (Usuarios)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Usuarios()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener los usuarios del grupo.", e);
			return (Usuarios)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Usuarios()));
		}
	}

	public Departamentos getDepartamentosPorPadre(int idDeptPadre, String entidad) {
		try {
			Departamentos departamentos = getDepartamentosWS(
					getServicioEstructuraOrganizativa().getDepartamentos(idDeptPadre, entidad)
						);
			return (Departamentos)ServiciosUtils.completeReturnOK(departamentos);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener los departamentos hijos.", e);
			return (Departamentos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Departamentos()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los departamentos hijos.", e);
			return (Departamentos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Departamentos()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener los departamentos hijos.", e);
			return (Departamentos)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Departamentos()));
		}
	}

	public Grupos getGrupos(String entidad) {
		try {
			Grupos grupos = getGruposWS(
					getServicioEstructuraOrganizativa().getGrupos(entidad)
						);
			return (Grupos)ServiciosUtils.completeReturnOK(grupos);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener los grupos.", e);
			return (Grupos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Grupos()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los grupos.", e);
			return (Grupos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Grupos()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener los grupos.", e);
			return (Grupos)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Grupos()));
		}
	}

	public Departamentos getDepartamentos(String entidad) {
		try {
			Departamentos departamentos = getDepartamentosWS(
					getServicioEstructuraOrganizativa().getDepartamentos(entidad)
						);
			return (Departamentos)ServiciosUtils.completeReturnOK(departamentos);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener los departamentos.", e);
			return (Departamentos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Departamentos()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los departamentos.", e);
			return (Departamentos)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Departamentos()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener los departamentos.", e);
			return (Departamentos)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Departamentos()));
		}
	}

	public Grupo getGrupo(int idGrupo, String entidad) {
		try {
			Grupo grupo = getGrupoWS(
					getServicioEstructuraOrganizativa().getGrupo(idGrupo, entidad)
						);
			return (Grupo)ServiciosUtils.completeReturnOK(grupo);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener grupo.", e);
			return (Grupo)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Grupo()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener grupo.", e);
			return (Grupo)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Grupo()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener grupo.", e);
			return (Grupo)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Grupo()));
		}
	}

	public Departamento getDepartamento(int idDepto, String entidad) {
		try {
			Departamento departamento = getDepartamentoWS(
					getServicioEstructuraOrganizativa().getDepartamento(idDepto, entidad)
						);
			return (Departamento)ServiciosUtils.completeReturnOK(departamento);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener departamento.", e);
			return (Departamento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Departamento()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener departamento.", e);
			return (Departamento)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Departamento()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener departamento.", e);
			return (Departamento)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Departamento()));
		}
	}

	public RetornoEntero crearArchivador(Archive archive, String entidad) {
		try {
			RetornoEntero entero = getRetornoEnteroWS(
					getServicioEstructuraOrganizativa().crearArchivador(getArchivadorServicio(archive), entidad)
				);
			return (RetornoEntero)ServiciosUtils.completeReturnOK(entero);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al crear archivador.", e);
			return (RetornoEntero)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoEntero()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al crear archivador.", e);
			return (RetornoEntero)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoEntero()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al crear archivador.", e);
			return (RetornoEntero)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RetornoEntero()));
		}
	}

	public RetornoServicio editarDepartamentoCompleto(Departamento departamento, String entidad) {
		try {
			getServicioEstructuraOrganizativa().editarDepartamento(getDepartamentoServicio(departamento), entidad);
			return ServiciosUtils.createReturnOK();
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al editar departamento.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al editar departamento.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al editar departamento.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	public RetornoServicio editarUsuario(Usuario usuario, String entidad) {
		try {
			getServicioEstructuraOrganizativa().editarUsuario(getUsuarioServicio(usuario), entidad);
			return ServiciosUtils.createReturnOK();
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al editar usuario.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al editar usuario.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al editar usuario.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	public RetornoServicio eliminarArchivador(int userId, int archiveId, String entidad) {
		try {
			getServicioEstructuraOrganizativa().eliminarArchivador(userId, archiveId, entidad);
			return ServiciosUtils.createReturnOK();
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al eliminar archivador.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al eliminar archivador.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar archivador.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	public Listas getListas(String entidad) {
		try {
			Listas listas = getListasWS(
					getServicioEstructuraOrganizativa().getListas(entidad)
				);
			return (Listas)ServiciosUtils.completeReturnOK(listas);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener listas.", e);
			return (Listas)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Listas()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener listas.", e);
			return (Listas)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new Listas()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener listas.", e);
			return (Listas)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new Listas()));
		}
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

		poUsuario.setCreationDate(oUsuario.get_creationDate());
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
		poUsuario.setUpdateDate(oUsuario.get_updateDate());
		poUsuario.setUpdaterId(oUsuario.get_updaterId());
		poUsuario.setUserConnected(oUsuario.get_userConnected());
		poUsuario.setWasAdmin(oUsuario.is_wasAdmin());

		return poUsuario;
	}

	private UsuariosLdap getUsuariosLdapWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuariosLdap oUsuarios) {
		if (oUsuarios == null || oUsuarios.get_list() == null || oUsuarios.get_list().size() == 0)
			return new UsuariosLdap();

		UsuariosLdap poUsuarios = new UsuariosLdap();
		UsuarioLdap[] poArrayUsuarios = new UsuarioLdap[oUsuarios.get_list().size()];

		for(int i=0; i<oUsuarios.get_list().size(); i++)
			poArrayUsuarios[i] = getUsuarioLdapWS((ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap)oUsuarios.get_list().get(i));
		poUsuarios.setUsuariosLdap(poArrayUsuarios);

		return poUsuarios;
	}

	private UsuarioLdap getUsuarioLdapWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.UsuarioLdap oUsuario) {
		if (oUsuario == null)
			return null;

		UsuarioLdap poUsuario = new UsuarioLdap();

		poUsuario.set_id(oUsuario.get_id());
		poUsuario.set_ldapguid(oUsuario.get_ldapguid());
		poUsuario.set_ldapfullname(oUsuario.get_ldapfullname());
		poUsuario.set_permsImpl(getPermisosGenericosWS(oUsuario.get_permsImpl()));
		poUsuario.set_profilesImpl(getPerfilesGenericosWS(oUsuario.get_profilesImpl()));

		return poUsuario;
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

	private Departamentos getDepartamentosWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamentos oDepartamentos) {
		if (oDepartamentos == null || oDepartamentos.getDepartamentosLista() == null || oDepartamentos.getDepartamentosLista().count() == 0)
			return new Departamentos();

		Departamentos poDepartamentos = new Departamentos();
		DepartamentosLista poDepartamentosLista = new DepartamentosLista();
		Departamento[] poArrayDepartamentos = new Departamento[oDepartamentos.getDepartamentosLista().count()];

		for(int i=0; i<oDepartamentos.getDepartamentosLista().count(); i++)
			poArrayDepartamentos[i] = getDepartamentoWS((ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento)oDepartamentos.getDepartamentosLista().get(i));
		poDepartamentosLista.setDepartamentos(poArrayDepartamentos);
		poDepartamentos.setDepartamentosLista(poDepartamentosLista);

		return poDepartamentos;
	}

	private Departamento getDepartamentoWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento oDepartamento) {
		if (oDepartamento == null)
			return null;

		Departamento poDepartamento = new Departamento();

		poDepartamento.setAdminUsers(getUsuariosBasicosWS(oDepartamento.get_adminUsers()));
		poDepartamento.setCreationDate(oDepartamento.get_creationDate());
		poDepartamento.setCreatorId(oDepartamento.get_creatorId());
		poDepartamento.setDescription(oDepartamento.get_description());
		poDepartamento.setId(oDepartamento.get_id());
		poDepartamento.setManagerId(oDepartamento.get_managerId());
		poDepartamento.setName(oDepartamento.get_name());
		poDepartamento.setParentId(oDepartamento.get_parentId());
		poDepartamento.setPerms(getPermisosGenericosWS(oDepartamento.get_permsImpl()));
		poDepartamento.setType(oDepartamento.get_type());
		poDepartamento.setUpdateDate(oDepartamento.get_updateDate());
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

	private Grupos getGruposWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupos oGrupos) {
		if (oGrupos == null || oGrupos.getGruposLista() == null || oGrupos.getGruposLista().count() == 0)
			return new Grupos();

		Grupos poGrupos = new Grupos();
		GruposLista poGruposLista = new GruposLista();
		Grupo[] poArrayGrupos = new Grupo[oGrupos.getGruposLista().count()];

		for(int i=0; i<oGrupos.getGruposLista().count(); i++)
			poArrayGrupos[i] = getGrupoWS((ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupo)oGrupos.getGruposLista().get(i));
		poGruposLista.setGrupos(poArrayGrupos);
		poGrupos.setGruposLista(poGruposLista);

		return poGrupos;
	}

	private Grupo getGrupoWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.Grupo oGrupo) {
		if (oGrupo == null)
			return null;

		Grupo poGrupo = new Grupo();

		poGrupo.setAdminUsers(getUsuariosBasicosWS(oGrupo.get_adminUsers()));
		poGrupo.setCreationDate(oGrupo.get_creationDate());
		poGrupo.setCreatorId(oGrupo.get_creatorId());
		poGrupo.setDescription(oGrupo.get_description());
		poGrupo.setId(oGrupo.get_id());
		poGrupo.setManagerId(oGrupo.get_managerId());
		poGrupo.setName(oGrupo.get_name());
		poGrupo.setPerms(getPermisosGenericosWS(oGrupo.get_permsImpl()));
		poGrupo.setType(oGrupo.get_type());
		poGrupo.setUpdateDate(oGrupo.get_updateDate());
		poGrupo.setUpdaterId(oGrupo.get_updaterId());
		poGrupo.setUserConnected(oGrupo.get_userConnected());
		poGrupo.setUsers(getUsuariosWS(oGrupo.get_users()));

		return poGrupo;
	}

	private GrupoLdap getGrupoLdapWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.GrupoLdap oGrupo) {
		if (oGrupo == null)
			return null;

		GrupoLdap poGrupo = new GrupoLdap();

		poGrupo.set_id(oGrupo.get_id());
		poGrupo.set_guid(oGrupo.get_guid());
		poGrupo.set_fullname(oGrupo.get_fullname());
		poGrupo.set_type(oGrupo.get_type());

		return poGrupo;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento getDepartamentoServicio (Departamento oDepartamento) {
		if (oDepartamento == null)
			return null;

		ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento poDepartamento = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Departamento();

		poDepartamento.set_adminUsers(getUsuariosBasicosServicio(oDepartamento.getAdminUsers()));
		poDepartamento.set_creationDate(oDepartamento.getCreationDate());
		poDepartamento.set_creatorId(oDepartamento.getCreatorId());
		poDepartamento.set_description(oDepartamento.getDescription());
		poDepartamento.set_id(oDepartamento.getId());
		poDepartamento.set_managerId(oDepartamento.getManagerId());
		poDepartamento.set_name(oDepartamento.getName());
		poDepartamento.set_parentId(oDepartamento.getParentId());
		poDepartamento.set_permsImpl(getPermisosGenericosServicio(oDepartamento.getPerms()));
		poDepartamento.set_type(oDepartamento.getType());
		poDepartamento.set_updateDate(oDepartamento.getUpdateDate());
		poDepartamento.set_updaterId(oDepartamento.getUpdaterId());
		poDepartamento.set_userConnected(oDepartamento.getUserConnected());
		poDepartamento.set_users(getUsuariosServicio(oDepartamento.getUsers()));

		return poDepartamento;
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

		poUsuario.set_creationDate(oUsuario.getCreationDate());
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
		poUsuario.set_updateDate(oUsuario.getUpdateDate());
		poUsuario.set_updaterId(oUsuario.getUpdaterId());
		poUsuario.set_userConnected(oUsuario.getUserConnected());
		poUsuario.set_wasAdmin(oUsuario.isWasAdmin());

		return poUsuario;
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

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.Archive getArchivadorServicio (Archive oArchivador) {
		if (oArchivador == null)
			return null;

		ieci.tecdoc.sgm.core.services.estructura_organizativa.Archive poArchivador = new ieci.tecdoc.sgm.core.services.estructura_organizativa.Archive();

		poArchivador.setAdminUserId(oArchivador.getAdminUserId());
		poArchivador.setFldsDef(getArchivadorFldsServicio(oArchivador.getFldsDef()));
		poArchivador.setFtsInContents(oArchivador.isFtsInContents());
		poArchivador.setIdxsDef(getArchivadorIdxsServicio(oArchivador.getIdxsDef()));
		poArchivador.setMiscDef(getArchivadorMiscServicio(oArchivador.getMiscDef()));
		poArchivador.setName(oArchivador.getName());
		poArchivador.setParentId(oArchivador.getParentId());
		poArchivador.setRemarks(oArchivador.getRemarks());
		poArchivador.setTypeId(oArchivador.getTypeId());

		return poArchivador;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFlds getArchivadorFldsServicio (ArchiveFlds oArchivadorFlds) {
		if (oArchivadorFlds == null || oArchivadorFlds.getArchiveFldsList() == null || oArchivadorFlds.getArchiveFldsList().length == 0)
			return new ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFlds();

		ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFlds poArchivadorFlds = new ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFlds();

		try {
			for(int i=0; i<oArchivadorFlds.getArchiveFldsList().length; i++)
				poArchivadorFlds.addFld(getArchivadorFldServicio(oArchivadorFlds.getArchiveFldsList()[i]));
		}catch(Exception e) {
			return new ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFlds();
		}

		return poArchivadorFlds;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFld getArchivadorFldServicio (ArchiveFld oArchivadorFld) {
		if (oArchivadorFld == null)
			return null;

		ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFld poArchivadorFld = new ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveFld();

		poArchivadorFld.setDoc(oArchivadorFld.isDoc());
		poArchivadorFld.setLen(oArchivadorFld.getLen());
		poArchivadorFld.setMult(oArchivadorFld.isMult());
		poArchivadorFld.setName(oArchivadorFld.getName());
		poArchivadorFld.setNullable(oArchivadorFld.isNullable());
		poArchivadorFld.setRemarks(oArchivadorFld.getRemarks());
		poArchivadorFld.setType(oArchivadorFld.getType());

		return poArchivadorFld;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveIdxs getArchivadorIdxsServicio (ArchiveIdxs oArchivadorIdxs) {
		if (oArchivadorIdxs == null || oArchivadorIdxs.getArchiveIndxsList() == null || oArchivadorIdxs.getArchiveIndxsList().length == 0)
			return new ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveIdxs();

		ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveIdxs poArchivadorIdxs = new ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveIdxs();

		for(int i=0; i<oArchivadorIdxs.getArchiveIndxsList().length; i++)
			poArchivadorIdxs.addIdx(getArchivadorIdxServicio(oArchivadorIdxs.getArchiveIndxsList()[i]));

		return poArchivadorIdxs;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveIdx getArchivadorIdxServicio (ArchiveIdx oArchivadorIdx) {
		if (oArchivadorIdx == null)
			return null;

		ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveIdx poArchivadorIdx = new ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveIdx();

		poArchivadorIdx.setFldsId(getFldsIdsServicio(oArchivadorIdx.getFldsId()));

		return poArchivadorIdx;
	}

	private ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveMisc getArchivadorMiscServicio (ArchiveMisc oArchivadorMisc) {
		if (oArchivadorMisc == null)
			return null;

		ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveMisc poArchivadorMisc =
			new ieci.tecdoc.sgm.core.services.estructura_organizativa.ArchiveMisc(
					oArchivadorMisc.getFdrName(),
					oArchivadorMisc.getVolListType(),
					oArchivadorMisc.getVolListId()
				);

		return poArchivadorMisc;
	}

	private RetornoEntero getRetornoEnteroWS (int oEntero) {
		RetornoEntero poEntero = new RetornoEntero();
		poEntero.setValor(oEntero);

		return poEntero;
	}

	private RetornoCadena getRetornoCadenaWS (String oCadena) {
		RetornoCadena poCadena = new RetornoCadena();
		poCadena.setValor(oCadena);

		return poCadena;
	}

	private Listas getListasWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.Listas oListas) {
		if (oListas == null || oListas.get_list() == null || oListas.get_list().size() == 0)
			return new Listas();

		Listas poListas = new Listas();
		Lista[] poListasArray = new Lista[oListas.get_list().size()];

		for(int i=0; i<oListas.get_list().size(); i++)
			poListasArray[i] = getListaWS((ieci.tecdoc.sgm.core.services.estructura_organizativa.Lista)oListas.get_list().get(i));
		poListas.setListas(poListasArray);

		return poListas;
	}

	private Lista getListaWS (ieci.tecdoc.sgm.core.services.estructura_organizativa.Lista oLista) {
		if (oLista == null)
			return null;

		Lista poLista = new Lista();

		poLista.setCreationDate(oLista.getCreationDate());
		poLista.setCreatorId(oLista.getCreatorId());
		poLista.setFlags(oLista.getFlags());
		poLista.setId(oLista.getId());
		poLista.setName(oLista.getName());
		poLista.setRemarks(oLista.getRemarks());
		poLista.setState(oLista.getState());
		poLista.setUpdateDate(oLista.getUpdateDate());
		poLista.setUpdaterId(oLista.getUpdaterId());
		poLista.setVolId(oLista.getVolId());

		return poLista;
	}

	private ArrayList getFldsIdsServicio (int[] oFldsIds) {
		if (oFldsIds == null || oFldsIds.length == 0)
			return new ArrayList();

		ArrayList poFldsIds = new ArrayList();

		for(int i=0; i<oFldsIds.length; i++)
			poFldsIds.add(new Integer(oFldsIds[i]));

		return poFldsIds;
	}

	public Usuarios getUsuarios(int aplicacion, int idsUser[], boolean superusers, String entidad) {
		try {
			Usuarios usuarios = getUsuariosWS(
					getServicioEstructuraOrganizativa().getUsuarios(aplicacion, idsUser, superusers, entidad)
						);
			return (Usuarios)ServiciosUtils.completeReturnOK(usuarios);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener los usuarios.", e);
			return (Usuarios)ServiciosUtils.completeReturnError((RetornoServicio)(new Usuarios()),e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los usuarios.", e);
			return (Usuarios)ServiciosUtils.completeReturnError((RetornoServicio)(new Usuarios()), e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener los usuarios.", e);
			return (Usuarios)ServiciosUtils.completeReturnError((RetornoServicio)(new Usuarios()));
		}
	}

	public Usuarios getUsuariosAsociation(int aplicacion, int deptId, boolean superusers, String entidad) {
		try {
			Usuarios usuarios = getUsuariosWS(
					getServicioEstructuraOrganizativa().getUsuariosAsociation(aplicacion, deptId, superusers, entidad)
						);
			return (Usuarios)ServiciosUtils.completeReturnOK(usuarios);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener los usuarios asociados a la oficina.", e);
			return (Usuarios)ServiciosUtils.completeReturnError((RetornoServicio)(new Usuarios()),e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los usuarios asociados a la oficina.", e);
			return (Usuarios)ServiciosUtils.completeReturnError((RetornoServicio)(new Usuarios()), e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener los usuarios asociados a la oficina.", e);
			return (Usuarios)ServiciosUtils.completeReturnError((RetornoServicio)(new Usuarios()));
		}
	}

	public Usuarios getUsuariosYaAsociados(int []idsUser, int idOfic, String entidad) {
		try {
			Usuarios usuarios = getUsuariosWS(
					getServicioEstructuraOrganizativa().getUsuariosYaAsociados(idsUser, idOfic, entidad)
						);
			return (Usuarios)ServiciosUtils.completeReturnOK(usuarios);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener los usuarios ya asociados a la oficina.", e);
			return (Usuarios)ServiciosUtils.completeReturnError((RetornoServicio)(new Usuarios()),e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los usuarios ya asociados a la oficina.", e);
			return (Usuarios)ServiciosUtils.completeReturnError((RetornoServicio)(new Usuarios()),e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener ya asociados a la oficina.", e);
			return (Usuarios)ServiciosUtils.completeReturnError((RetornoServicio)(new Usuarios()));
		}
	}

	public Usuarios getUsuariosAplicacionPorDepartamento(int aplicacion, int deptId[], boolean sinPermisos, boolean users, boolean superusuarios, String entidad) {
		try {
			Usuarios usuarios = getUsuariosWS(
					getServicioEstructuraOrganizativa().getUsuariosAplicacionPorDepartamento(aplicacion, deptId, sinPermisos, users, superusuarios, entidad)
						);
			return (Usuarios)ServiciosUtils.completeReturnOK(usuarios);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener los usuarios aplicación por departamento.", e);
			return (Usuarios)ServiciosUtils.completeReturnError((RetornoServicio)(new Usuarios()),e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener los usuarios aplicación por departamento.", e);
			return (Usuarios)ServiciosUtils.completeReturnError((RetornoServicio)(new Usuarios()), e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener los usuarios aplicación por departamento.", e);
			return (Usuarios)ServiciosUtils.completeReturnError((RetornoServicio)(new Usuarios()));
		}
	}

	public GrupoLdap crearGrupoLdap(String ldapGuid, String ldapDn, int type, String entidad){
		try {
			GrupoLdap grupoLdap = getGrupoLdapWS(
					getServicioEstructuraOrganizativa().crearGrupoLdap(ldapGuid, ldapDn, type, entidad)
				);
			return (GrupoLdap)ServiciosUtils.completeReturnOK(grupoLdap);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al crear grupo LDAP.", e);
			return (GrupoLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new GrupoLdap()), e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al crear grupo LDAP.", e);
			return (GrupoLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new GrupoLdap()), e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al crear grupo LDAP.", e);
			return (GrupoLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new GrupoLdap()));
		}
	}

	public GrupoLdap getGrupoLdap(String ldapGuid, String entidad) {
		try {
			GrupoLdap grupoLdap = getGrupoLdapWS(
					getServicioEstructuraOrganizativa().getGrupoLdap(ldapGuid, entidad)
						);
			return (GrupoLdap)ServiciosUtils.completeReturnOK(grupoLdap);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener grupo LDAP.", e);
			return (GrupoLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new GrupoLdap()),e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener grupo LDAP.", e);
			return (GrupoLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new GrupoLdap()), e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener grupo LDAP.", e);
			return (GrupoLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new GrupoLdap()));
		}
	}

	public UsuarioLdap crearUsuarioLdap(String ldapGuid, String name, int idPerfil, String entidad) {
		try {
			UsuarioLdap usuarioLdap = getUsuarioLdapWS(
					getServicioEstructuraOrganizativa().crearUsuarioLdap(ldapGuid, name, idPerfil, entidad)
				);
			return (UsuarioLdap)ServiciosUtils.completeReturnOK(usuarioLdap);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al crear usuario LDAP.", e);
			return (UsuarioLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new GrupoLdap()), e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al crear usuario LDAP.", e);
			return (UsuarioLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new GrupoLdap()), e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al crear usuario LDAP.", e);
			return (UsuarioLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new GrupoLdap()));
		}
	}

	public UsuarioLdap getUsuarioLdapByGuid(String ldapguid, String entidad){
		try {
			UsuarioLdap usuarioLdap = getUsuarioLdapWS(
					getServicioEstructuraOrganizativa().getUsuarioLdapByGuid(ldapguid, entidad)
						);
			return (UsuarioLdap)ServiciosUtils.completeReturnOK(usuarioLdap);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener usuario LDAP por GUID.", e);
			return (UsuarioLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuarioLdap()),e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener usuario LDAP por GUID.", e);
			return (UsuarioLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuarioLdap()), e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener usuario LDAP por GUID.", e);
			return (UsuarioLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuarioLdap()));
		}
	}

	public UsuarioLdap getUsuarioLdapByFullName(String fullName, String entidad){
		try {
			UsuarioLdap usuarioLdap = getUsuarioLdapWS(
					getServicioEstructuraOrganizativa().getUsuarioLdapByFullName(fullName, entidad)
						);
			return (UsuarioLdap)ServiciosUtils.completeReturnOK(usuarioLdap);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener usuario LDAP por FullName.", e);
			return (UsuarioLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuarioLdap()),e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener usuario LDAP por FullName.", e);
			return (UsuarioLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuarioLdap()), e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener usuario LDAP por FullName.", e);
			return (UsuarioLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuarioLdap()));
		}
	}

	public UsuarioLdap getUsuarioLdapPorIdCertificado(String idCert, String entidad){
		try {
			UsuarioLdap usuarioLdap = getUsuarioLdapWS(
					getServicioEstructuraOrganizativa().getUsuarioLdapByIdCert(idCert, entidad)
						);
			return (UsuarioLdap)ServiciosUtils.completeReturnOK(usuarioLdap);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener usuario LDAP por ID de certificado.", e);
			return (UsuarioLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuarioLdap()),e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener usuario LDAP por ID de certificado.", e);
			return (UsuarioLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuarioLdap()), e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener usuario LDAP por ID de certificado.", e);
			return (UsuarioLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuarioLdap()));
		}
	}

	public UsuariosLdap getUsuariosLdap(String entidad)	{
		try {
			UsuariosLdap usuariosLdap = getUsuariosLdapWS(
					getServicioEstructuraOrganizativa().getUsuariosLdap(entidad)
				);
			return (UsuariosLdap)ServiciosUtils.completeReturnOK(usuariosLdap);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener usuarios LDAP.", e);
			return (UsuariosLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuariosLdap()), e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener usuarios LDAP.", e);
			return (UsuariosLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuariosLdap()), e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener usuarios LDAP.", e);
			return (UsuariosLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuariosLdap()));
		}
	}

	public UsuarioLdap getUsuarioLdap(int id, String entidad) {
		try {
			UsuarioLdap usuarioLdap = getUsuarioLdapWS(
					getServicioEstructuraOrganizativa().getUsuarioLdap(id, entidad)
				);
			return (UsuarioLdap)ServiciosUtils.completeReturnOK(usuarioLdap);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener usuario LDAP por ID.", e);
			return (UsuarioLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuarioLdap()), e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener usuario LDAP por ID.", e);
			return (UsuarioLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuarioLdap()), e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener usuario LDAP por ID.", e);
			return (UsuarioLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuarioLdap()));
		}
	}

	public UsuariosLdap getUsuariosLdapYaAsociados(int[] idsUser, int idOfic, String entidad) {
		try {
			UsuariosLdap usuariosLdap = getUsuariosLdapWS(
					getServicioEstructuraOrganizativa().getUsuariosLdapYaAsociados(idsUser, idOfic, entidad)
				);
			return (UsuariosLdap)ServiciosUtils.completeReturnOK(usuariosLdap);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener usuarios LDAP ya asociados.", e);
			return (UsuariosLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuariosLdap()), e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener usuarios LDAP por ID ya asociados.", e);
			return (UsuariosLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuariosLdap()), e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener usuarios LDAP ya asociados.", e);
			return (UsuariosLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuariosLdap()));
		}
	}

	public RetornoServicio editarUsuarioLdap(UsuarioLdap usuarioLdap, String entidad) {
		try {
			getServicioEstructuraOrganizativa().editarUsuarioLdap(getUsuarioLdapServicio(usuarioLdap), entidad);
			return ServiciosUtils.createReturnOK();
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al editar usuario.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al editar usuario.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al editar usuario.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	public RetornoServicio eliminarUsuarioLdap(int id, String entidad) {
		try {
			getServicioEstructuraOrganizativa().eliminarUsuarioLdap(id, entidad);
			return ServiciosUtils.createReturnOK();
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al eliminar usuario LDAP.", e);
			return ServiciosUtils.createReturnError();
		} catch (SigemException e) {
			logger.error("Error al eliminar usuario LDAP.", e);
			return ServiciosUtils.createReturnError();
		} catch (Throwable e) {
			logger.error("Error inesperado al eliminar usuario LDAP.", e);
			return ServiciosUtils.createReturnError();
		}
	}

	public RetornoCadena getUsuarioLdapBasicById(int id, String entidad) {
		try {
			RetornoCadena cadena = getRetornoCadenaWS(
					getServicioEstructuraOrganizativa().getUsuarioLdapBasicById(id, entidad)
				);
			return (RetornoCadena)ServiciosUtils.completeReturnOK(cadena);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al crear archivador.", e);
			return (RetornoCadena)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoCadena()),
								e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al crear archivador.", e);
			return (RetornoCadena)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new RetornoCadena()),
								e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al crear archivador.", e);
			return (RetornoCadena)
			ServiciosUtils.completeReturnError(
						(RetornoServicio)(new RetornoEntero()));
		}
	}

	public Departamento getDepartamentoLite(int arg0, String arg1) {
		try {
			Departamento departamento = getDepartamentoWS(
					getServicioEstructuraOrganizativa().getDepartamentoLite(arg0, arg1)
						);
			return (Departamento)ServiciosUtils.completeReturnOK(departamento);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener departamento basico.", e);
			return (Departamento)ServiciosUtils.completeReturnError((RetornoServicio)(new Departamento()),e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener departamento basico.", e);
			return (Departamento)ServiciosUtils.completeReturnError((RetornoServicio)(new Departamento()),e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener departamento basico.", e);
			return (Departamento)ServiciosUtils.completeReturnError((RetornoServicio)(new Departamento()));
		}
	}

	public GrupoLdap getGrupoLdapById(int arg0, String arg1){
		try {
			GrupoLdap grupoLdap = getGrupoLdapWS(
					getServicioEstructuraOrganizativa().getGrupoLdapById(arg0, arg1)
						);
			return (GrupoLdap)ServiciosUtils.completeReturnOK(grupoLdap);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener grupo LDAP.", e);
			return (GrupoLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new GrupoLdap()),e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener grupo LDAP.", e);
			return (GrupoLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new GrupoLdap()),e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener grupo LDAP.", e);
			return (GrupoLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new GrupoLdap()));
		}
	}

	public UsuariosLdap getUsuariosLdapAplicacion(int aplicacion, int[] idsUser,
			boolean superusers, String entidad){

		try {
			UsuariosLdap usuariosLdap = getUsuariosLdapWS(
					getServicioEstructuraOrganizativa().getUsuariosLdap(aplicacion, idsUser, superusers, entidad)
						);
			return (UsuariosLdap)ServiciosUtils.completeReturnOK(usuariosLdap);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener usuarios LDAP.", e);
			return (UsuariosLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuariosLdap()),e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener usuarios LDAP.", e);
			return (UsuariosLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuariosLdap()),e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener usuarios LDAP.", e);
			return (UsuariosLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuariosLdap()));
		}
	}

	public UsuariosLdap getUsuariosLdapAplicacionPorDepartamento(int aplicacion, int[] deptId,
			boolean sinPermisos, boolean usuarios, boolean superusuarios, String entidad){

		try {
			UsuariosLdap usuariosLdap = getUsuariosLdapWS(
					getServicioEstructuraOrganizativa().
					getUsuariosLdapAplicacionPorDepartamento(aplicacion, deptId,sinPermisos,
							usuarios, superusuarios, entidad)
						);
			return (UsuariosLdap)ServiciosUtils.completeReturnOK(usuariosLdap);
		} catch (EstructuraOrganizativaException e) {
			logger.error("Error al obtener usuarios LDAP por departamento.", e);
			return (UsuariosLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuariosLdap()),e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error al obtener usuarios LDAP por departamento.", e);
			return (UsuariosLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuariosLdap()),e.getErrorCode());
		} catch (Throwable e) {
			logger.error("Error inesperado al obtener usuarios LDAP por departamento.", e);
			return (UsuariosLdap)ServiciosUtils.completeReturnError((RetornoServicio)(new UsuariosLdap()));
		}
	}
}