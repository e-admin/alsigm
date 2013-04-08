package gcontrol.model;

import fondos.db.IProductorSerieDbEntity;
import fondos.vos.EntidadProductoraVO;
import gcontrol.db.IArchivoDbEntity;
import gcontrol.db.ICAOrganoDbEntity;
import gcontrol.db.IGrupoDBEntity;
import gcontrol.db.IGrupoUsuarioDBEntity;
import gcontrol.db.IListaControlAccesoDbEntity;
import gcontrol.db.IOrganoUsuarioDBEntity;
import gcontrol.db.IPermisoRolDBEntity;
import gcontrol.db.IPermisosListaDbEntity;
import gcontrol.db.IRolDBEntity;
import gcontrol.db.IRolUsuarioDBEntity;
import gcontrol.db.IUsuarioDBEntity;
import gcontrol.exceptions.UsuariosNotAllowedException;
import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.DestinatarioPermisosListaVO;
import gcontrol.vos.GrupoVO;
import gcontrol.vos.ListaAccesoVO;
import gcontrol.vos.PermisoVO;
import gcontrol.vos.PermisosListaVO;
import gcontrol.vos.RolVO;
import gcontrol.vos.UsuarioExtendedVO;
import gcontrol.vos.UsuarioOrganoVO;
import gcontrol.vos.UsuarioVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import salas.model.GestionSalasConsultaBI;
import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.InfoOrgano;
import se.instituciones.TipoAtributo;
import se.instituciones.exceptions.GestorOrganismosException;
import se.usuarios.AppPermissions;
import se.usuarios.ServiceClient;
import se.usuarios.TipoUsuario;
import xml.config.ConfiguracionControlAcceso;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.SistemaGestorOrganismos;
import xml.config.Usuario;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;
import auditoria.vos.CritUsuarioVO;

import common.Constants;
import common.MultiEntityConstants;
import common.bi.GestionAuditoriaBI;
import common.bi.GestionConsultasBI;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionPrestamosBI;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceBase;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.ArchivoModelException;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;
import common.security.ControlAccesoSecurityManager;
import common.util.ArrayUtils;
import common.util.ListUtils;
import common.util.VO2IDTransformer;

import descripcion.db.IDescriptorDBEntity;

public class GestionControlUsuariosBIImpl extends ServiceBase implements
		GestionControlUsuariosBI {
	private final static Logger logger = Logger
			.getLogger(GestionControlUsuariosBIImpl.class);

	class RolAuthorizationHelper {
		int errorCode = -1;

		boolean configureRol(RolVO rol) {
			rol.setPermitidaModificacionPermisos(permitidaModificacionPermisos(rol));
			return errorCode == -1;
		}

		boolean permitidaModificacionPermisos(RolVO rol) {
			boolean permitidaModificacionPermisos = true;
			List usuariosConRol = getUsuariosConRol(rol.getId());
			if (usuariosConRol == null || usuariosConRol.size() == 0)
				permitidaModificacionPermisos = false;
			return permitidaModificacionPermisos;
		}
	}

	IRolDBEntity rolDBEntity = null;
	IArchivoDbEntity archivoDbEntity = null;
	ICAOrganoDbEntity caOrganoDbEntity = null;
	IUsuarioDBEntity usuarioDBEntity = null;
	IRolUsuarioDBEntity rolUsuarioDBEntity = null;
	IPermisoRolDBEntity permisoRolDBEntity = null;
	IGrupoUsuarioDBEntity grupoUsuarioDBEntity = null;
	IPermisosListaDbEntity permisosListaDbEntity = null;
	IOrganoUsuarioDBEntity organoUsuarioDBEntity = null;
	IGrupoDBEntity grupoDBEntity = null;
	IListaControlAccesoDbEntity listaControlAccesoDBEntity = null;
	IProductorSerieDbEntity productorSerie = null;
	IDescriptorDBEntity descriptorDBEntity = null;

	public GestionControlUsuariosBIImpl(IRolDBEntity rolDBEntity,
			IArchivoDbEntity archivoDbEntity,
			ICAOrganoDbEntity caOrganoDbEntity,
			IUsuarioDBEntity usuarioDBEntity,
			IRolUsuarioDBEntity rolUsuarioDBEntity,
			IPermisoRolDBEntity permisoRolDBEntity,
			IGrupoUsuarioDBEntity grupoUsuarioDBEntity,
			IPermisosListaDbEntity permisosListaDbEntity,
			IOrganoUsuarioDBEntity organoUsuarioDBEntity,
			IGrupoDBEntity grupoDBEntity,
			IListaControlAccesoDbEntity listaControlAccesoDBEntity,
			IProductorSerieDbEntity productorSerie,
			IDescriptorDBEntity descriptorDBEntity) {
		super();
		this.rolDBEntity = rolDBEntity;
		this.archivoDbEntity = archivoDbEntity;
		this.caOrganoDbEntity = caOrganoDbEntity;
		this.usuarioDBEntity = usuarioDBEntity;
		this.rolUsuarioDBEntity = rolUsuarioDBEntity;
		this.permisoRolDBEntity = permisoRolDBEntity;
		this.grupoUsuarioDBEntity = grupoUsuarioDBEntity;
		this.permisosListaDbEntity = permisosListaDbEntity;
		this.organoUsuarioDBEntity = organoUsuarioDBEntity;
		this.grupoDBEntity = grupoDBEntity;
		this.listaControlAccesoDBEntity = listaControlAccesoDBEntity;
		this.productorSerie = productorSerie;
		this.descriptorDBEntity = descriptorDBEntity;
	}

	private RolAuthorizationHelper getRolAuthorizationHelper() {
		return new RolAuthorizationHelper();
	}

	public LoggingEvent getLogginEvent(int action) {
		return null;
	}

	/**
	 * Obtiene de entre los tipos de usuario soportados por el sistema aquellos
	 * que disponen de un sistema que mantenga la dependencia orgánica de los
	 * usuarios de dichos tipos
	 *
	 * @return Tipos de usuario que disponen de un sistema que mantiene la
	 *         dependencia orgánica de los usuarios de dichos tipos
	 *         {@link Usuario}
	 */
	public List getTiposUsuarioConSistemaOrganizacion() {
		List tiposUsuarios = new ArrayList();

		List tipos = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo()
				.getConfiguracionControlAcceso().getUsuarios();

		Usuario usuario;
		for (int i = 0; i < tipos.size(); i++) {
			usuario = (Usuario) tipos.get(i);
			if (StringUtils.isNotBlank(usuario.getIdSistGestorOrg()))
				tiposUsuarios.add(usuario);
		}

		return tiposUsuarios;
	}

	/**
	 * Obtiene la configuracion de un tipo de usuario
	 *
	 * @param idTipo
	 * @return
	 */
	public Usuario getConfigTipoUsuarioByIdTipo(String idTipo) {
		// return Arrays.asList(new String[]{TipoUsuario.INTERNO,
		// TipoUsuario.ASOCIADO, TipoUsuario.INVESTIGADOR});
		return ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo()
				.getConfiguracionControlAcceso().findUsuarioByTipo(idTipo);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionControlUsuariosBI#insertProductorCatalogoEnSistema(se
	 * .procedimientos.ProductorCatalogoVO)
	 */
	public void insertProductorCatalogoEnSistema(InfoOrgano productor,
			EntidadProductoraVO entidadVO, String idArchivoReceptor, GestorOrganismos gestorOrganismos) throws GestorOrganismosException, NotAvailableException{
		// Id: Identificador único
		// Código: Información aportada por el sistema externo
		// Nombre: Información aportada por el sistema externo
		// IdArchivoReceptor: identificador del archivo de custodia de la serie
		// SistExtGestor identificador del sistema externo del que procede el
		// órgano SERA EL DE LA ENTIDAD
		// IdOrgSExtGestor: Información aportada por el sistema externo
		// Vigente: ‘S’
		checkPermission(ControlAccesoSecurityManager.ALTA_ORGANO);
		CAOrganoVO caOrganoVO = new CAOrganoVO();
		caOrganoVO.setIdArchivoReceptor(idArchivoReceptor);
		caOrganoVO.setSistExtGestor(entidadVO.getSistGestorOrg());
		caOrganoVO.setIdOrgSExtGestor(productor.getId());
		caOrganoVO.setCodigo(productor.getCodigo());
		caOrganoVO.setNombre(productor.getNombre());
		caOrganoVO.setNombreLargo(NombreOrganoFormat.formatearNombreLargo(
					productor,
					gestorOrganismos.recuperarOrganosAntecesores(
							productor.getId(), 0)));


		caOrganoVO.setVigente(Constants.TRUE_STRING);
		caOrganoDbEntity.insertCAOrgVO(caOrganoVO);
	}

	public List getOrganosVigentesEnSistemaDependientesEntidad(
			EntidadProductoraVO entidadProductoraVO) {
		// Filas de la tabla ASCAORGANO donde SISTEXTGESTOR sea el mismo que el
		// de la institución y VIGENTE = ‘S’
		return caOrganoDbEntity.getCAOrgProductorVOXSistExtGestorYVigencia(
				entidadProductoraVO.getSistGestorOrg(), true);
	}

	/**
	 * Obtiene la información de un usuario en archivo.
	 *
	 * @param idUsuario
	 *            Identificador del usuario en la aplicación.
	 * @return Información de un usuario de archivo.
	 */
	public UsuarioVO getUsuario(String idUsuario) {
		return usuarioDBEntity.getUsuario(idUsuario);
	}

	/**
	 * Obtiene la información de un superusuario en archivo.
	 *
	 * @param nombreUsuario
	 *            Nombre del usuario que se busca
	 * @return Información de un superusuario de archivo.
	 */
	public UsuarioVO getSuperusuario(String nombreUsuario) {
		return usuarioDBEntity.getSuperusuario(nombreUsuario);
	}

	/**
	 * Obtiene la información del usuario mas la informacion de su organo y
	 * archivo.
	 *
	 * @param idUsuario
	 *            Identificador del usuario en la aplicacion
	 * @return Informacion del usuario
	 */
	public UsuarioExtendedVO getUsuarioExtendido(String idUsuario) {
		CAOrganoVO organo = null;
		UsuarioVO user = this.getUsuario(idUsuario);
		UsuarioExtendedVO ue = new UsuarioExtendedVO(user);
		ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		Usuario usuario = csa.getConfiguracionControlAcceso()
				.findUsuarioByTipo("" + user.getTipo());
		if (((usuario != null) && (StringUtils.isNotBlank(usuario
				.getIdSistGestorOrg())))
				|| (TipoUsuario.ADMINISTRADOR.equals(user.getTipo()))) {
			// Obtenemos los datos del órgano del usuario y si lo tiene lo
			// añadimos
			organo = this.getOrganoUsuarioEnArchivo(ue.getId(), ue.getTipo(),
					ue.getIdUsrSistOrg());
			if (organo != null) {
				ue.setNombreOrgPertenece(organo.getNombre());
				ue.setIdOrgPertenece(organo.getIdOrg());
				ue.setIdArchivoReceptor(organo.getIdArchivoReceptor());
			}
		}
		return ue;
	}

	/**
	 * Obtiene la información referente a un conjunto de usuarios en archivo.
	 *
	 * @param idUsuario
	 *            Identificadores de usuarios en la aplicación.
	 * @return Lista con la información de cada usuario de archivo.
	 */
	public List getUsuarios(String[] idUsuario) {
		if (idUsuario == null || idUsuario.length == 0)
			return null;
		return usuarioDBEntity.getUsuarios(idUsuario);
	}

	/**
	 * Obtiene la información de un usuario en archivo.
	 *
	 * @param userType
	 *            Tipo de usuario.
	 * @param idUsrExtGestor
	 *            Identificador del usuario en el sistema gestor.
	 * @return Información de un usuario de archivo.
	 */
	public UsuarioVO getUsuario(String userType, String idUsrExtGestor) {
		return usuarioDBEntity.getUsuario(userType, idUsrExtGestor);
	}

	/**
	 * Obtiene los roles de un usuario.
	 *
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Roles del usuario.
	 */
	public List getRolesUsuario(String idUsuario) {
		return rolUsuarioDBEntity.getRolesUsuario(idUsuario);
	}

	/**
	 * Obtiene los permisos de un rol.
	 *
	 * @param idRol
	 *            Identificador del rol.
	 * @return Permisos del rol.
	 */
	public List getPermisosRol(String idRol) {
		return permisoRolDBEntity.getPermisosRol(idRol);
	}

	/**
	 * Obtiene los grupos del usuario.
	 *
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Grupos del usuario.
	 */
	public List getGruposUsuario(String idUsuario) {
		return grupoUsuarioDBEntity.getGruposUsuario(idUsuario);
	}

	/**
	 * Obtiene la lista de usuarios activos.
	 *
	 * @return Lista de usuarios activos.
	 */
	public List getUsuariosActivos() {
		return usuarioDBEntity.getUsuariosActivos();
	}

	/**
	 * Obtiene los usuarios dados de alta en el sistema
	 *
	 * @param pageInfo
	 *            Información de paginación.
	 * @return Lista de usuarios {@link UsuarioVO}
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getUsuarios(PageInfo pageInfo) throws TooManyResultsException {
		return usuarioDBEntity.getUsuarios(pageInfo);
	}

	/**
	 * Obtiene un listado de los usuarios internos que pueden solicitar un
	 * préstamo
	 *
	 * @return Listado de usuarios {@link UsuarioVO}
	 */
	public List getUsuariosExtRegistradosSolitantes() {
		final String permisos[] = {
				AppPermissions.ESTANDAR_SOLICITUD_PRESTAMOS,
				AppPermissions.AMPLIADO_SOLICITUD_PRESTAMOS,
				AppPermissions.ADMINISTRACION_TOTAL_SISTEMA };

		return getUsuariosExtConPermisos(permisos);
	}

	public List getUsuariosRegistradosSolitantes(String filtro) {
		final String permisos[] = {
				AppPermissions.ESTANDAR_SOLICITUD_PRESTAMOS,
				AppPermissions.AMPLIADO_SOLICITUD_PRESTAMOS,
				AppPermissions.ADMINISTRACION_TOTAL_SISTEMA };
		return getUsuariosConPermisos(permisos, filtro);
	}

	/**
	 * Obtiene los usuarios que tienen unos determinados permisos filtrados a su
	 * vez por el nombre de solicitante pasado como parámetro.
	 *
	 * @param permisos
	 *            Conjunto de permisos
	 * @param filtro
	 *            Nombre de solicitante a filtrar
	 * @return Colleccion de usuario {@link UsuarioVO}
	 */
	public List getUsuariosConPermisos(String[] permisos, String filtro) {
		return usuarioDBEntity.getUsuariosWithPermissions(permisos, filtro);
	}

	/**
	 * Obtiene los usuarios que tienen unos determinados permisos
	 *
	 * @param permisos
	 *            Conjunto de permisos
	 * @return Colleccion de usuario {@link UsuarioVO}
	 */
	public List getUsuariosConPermisos(String[] permisos) {
		return usuarioDBEntity.getUsuariosWithPermissions(permisos);
	}

	/**
	 * Obtiene los usuarios que tienen unos determinados permisos y pertenecen a
	 * determinados grupos con los archivos de custodia indicados
	 *
	 * @param permisos
	 *            Conjunto de permisos
	 * @param idsArchivo
	 * @return Colleccion de usuario {@link UsuarioVO}
	 */
	public List getUsuariosConPermisosYArchivos(String[] permisos,
			String[] idsArchivo) {
		return usuarioDBEntity.getUsuariosWithPermissionsAndArchive(permisos,
				idsArchivo);
	}

	/**
	 * Obtiene los usuarios que tienen unos determinados permisos y pertenecen a
	 * determinados grupos con todos los archivos de custodia indicados
	 *
	 * @param permisos
	 *            Conjunto de permisos
	 * @param idsArchivo
	 * @return Colleccion de usuario {@link UsuarioVO}
	 */
	public List getUsuariosConPermisosYAllArchivos(String[] permisos,
			String[] idsArchivo) {

		List usuariosConTodosLosArchivos = new ArrayList();
		if (!ArrayUtils.isEmpty(idsArchivo)) {

			List usuarios = usuarioDBEntity
					.getUsuariosWithPermissionsAndArchive(permisos, idsArchivo);

			if (!ListUtils.isEmpty(usuarios)) {
				ListIterator it = usuarios.listIterator();
				while (it.hasNext()) {
					UsuarioVO usuarioVO = (UsuarioVO) it.next();

					if (TipoUsuario.ADMINISTRADOR.equals(usuarioVO.getTipo())) {
						usuariosConTodosLosArchivos.add(usuarioVO);
					} else {
						List grupos = getGruposUsuario(usuarioVO.getId());
						List archivosCustodia = new ArrayList();
						if (!ListUtils.isEmpty(grupos)) {
							for (Iterator i = grupos.iterator(); i.hasNext();) {
								GrupoVO unGrupo = (GrupoVO) i.next();
								if (StringUtils.isNotEmpty(unGrupo
										.getIdArchivo()))
									archivosCustodia
											.add(unGrupo.getIdArchivo());
							}
						}

						if (archivosCustodia.containsAll(Arrays
								.asList(idsArchivo))) {
							usuariosConTodosLosArchivos.add(usuarioVO);
						}
					}
				}
			}
		}
		return usuariosConTodosLosArchivos;
	}

	/**
	 * Obtiene los usuarios activos de la aplicacion que tienen alguno de los
	 * permisos indicados
	 *
	 * @param permisosBuscados
	 *            Listado de los permisos(enteros) que al menos uno debe
	 *            contener el usuario
	 * @return Listado de usuarios activos en la aplicación con alguno de los
	 *         permisos
	 */
	public List getUsuariosExtConPermisos(String[] permisosBuscados) {
		List usuariosWithPermission = getUsuariosConPermisos(permisosBuscados);
		List usuariosExtendedWithPermission = new ArrayList();

		if (!util.CollectionUtils.isEmpty(usuariosWithPermission)) {
			UsuarioVO user = null;
			CAOrganoVO organo = null;
			UsuarioExtendedVO ue = null;

			ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();
			for (int i = 0; i < usuariosWithPermission.size(); i++) {
				user = (UsuarioVO) usuariosWithPermission.get(i);
				// Obtenemos el sistema gestor para el tipo del usuario(del XML)
				Usuario usuario = csa.getConfiguracionControlAcceso()
						.findUsuarioByTipo("" + user.getTipo());
				if (StringUtils.isNotBlank(usuario.getIdSistGestorOrg())) {
					ue = new UsuarioExtendedVO(user);
					// Obtenemos los datos del órgano del usuario y si lo tiene
					// lo
					// añadimos
					organo = this.getOrganoUsuarioEnArchivo(ue.getId(),
							ue.getTipo(), ue.getIdUsrSistOrg());
					if (organo != null) {
						ue.setNombreOrgPertenece(organo.getNombre());
						ue.setIdOrgPertenece(organo.getIdOrg());
						ue.setIdArchivoReceptor(organo.getIdArchivoReceptor());
						usuariosExtendedWithPermission.add(ue);
					}
				}
			}
		}

		return usuariosExtendedWithPermission;
	}

	/**
	 * Devuelve la lista de usuarios que pueden gestionar transferencias
	 * destinadas a un determinado archivo
	 *
	 * @param idArchivo
	 *            Identificador de archivo
	 * @return Lista de usuarios {@link gcontrol.vos.UsuarioVO}
	 */
	public List getGestoresTransferencia(String idArchivo) {
		final String[] permisos = {
				AppPermissions.GESTOR_RELACION_ENTREGA_ARCHIVO_RECEPTOR,
				AppPermissions.ADMINISTRACION_TOTAL_SISTEMA };

		return usuarioDBEntity.getUsuariosWithPermissionsAndArchive(permisos,
				new String[] { idArchivo });
	}

	/**
	 * Obtiene las listas de acceso para un grupo de destinatarios.
	 *
	 * @param destinatariosPermisosLista
	 *            Lista de destinatarios ({@link DestinatarioPermisosListaVO}).
	 * @return Listas de acceso ({@link PermisosListaVO});
	 */
	public List getListasAcceso(List destinatariosPermisosLista) {
		return permisosListaDbEntity
				.getListasAcceso(destinatariosPermisosLista);
	}

	/**
	 * Obtiene las listas de control de acceso.
	 *
	 * @return Listas de control de acceso.
	 */
	public List getListasControlAcceso() {
		return listaControlAccesoDBEntity.getListasControlAcceso();
	}

	/**
	 * Obtiene las listas de control de acceso por tipo.
	 *
	 * @param tipo
	 *            Tipo de la lista de control de acceso (
	 *            {@link TipoListaControlAcceso}).
	 * @return Listas de control de acceso.
	 */
	public List getListasControlAccesoByTipo(int tipo) {
		return listaControlAccesoDBEntity.getListasControlAccesoByTipo(tipo);
	}

	public ListaAccesoVO getListaControlAccesoByNombreYTipo(String nombre,
			int tipo) {
		return listaControlAccesoDBEntity.getListaControlAccesoByNombreYTipo(
				nombre, tipo);
	}

	public ListaAccesoVO getListaControlAccesoByNombre(String nombre) {
		return listaControlAccesoDBEntity.getListaControlAccesoByNombre(nombre);
	}

	/**
	 * Obtiene las listas de control de acceso.
	 *
	 * @return Listas de control de acceso.
	 */
	public List findListasAcceso(String nombre) {
		return listaControlAccesoDBEntity
				.getListadoListasControlAccesoByNombre(nombre);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionControlUsuariosBI#findListasAccesoByNombreYTipos(java
	 * .lang.String, int[])
	 */
	public List findListasAccesoByNombreYTipos(String nombre, int[] tipos) {
		return listaControlAccesoDBEntity
				.getListadoListasControlAccesoByNombreYTipos(nombre, tipos);
	}

	/**
	 * Obtiene la lista de órganos productores.
	 *
	 * @param vigente
	 *            Indica si los órganos deben ser vigentes o no vigentes. Si es
	 *            nulo, se devolverán todos los órganos productores.
	 * @return Lista de órganos productores.
	 */
	public List getCAOrgProductoresVOList(Boolean vigente) {
		return caOrganoDbEntity.getCAOrgProductoresVOList(vigente);
	}

	/**
	 * Permite incorporar un nuevo organo al sistema.
	 *
	 * @param organo
	 *            Informacion de organo {@link CAOrganoVO}.
	 * @throws GestorOrganismosException
	 *             En caso de que el órgano para el que se realza la
	 *             comprobación provenga de un sistema externo y ese sistema
	 *             externo no esté disponible
	 * @throws NotAvailableException
	 *             En caso de que el órgano para el que se realza la
	 *             comprobación provenga de un sistema externo y ese sistema
	 *             externo no disponga de la funcionalidad necesaria para
	 *             realizar la conprobación
	 */
	public void saveOrgano(CAOrganoVO organo) throws ActionNotAllowedException,
			GestorOrganismosException, NotAvailableException {
		iniciarTransaccion();
		if (organo.getIdOrg() == null) {
			checkPermission(ControlAccesoSecurityManager.ALTA_ORGANO);
			organo.setVigente(Constants.TRUE_STRING);
			caOrganoDbEntity.insertCAOrgVO(organo);
		} else {
			checkPermission(ControlAccesoSecurityManager.MODIFICACION_ORGANO);
			caOrganoDbEntity.updateCAOrgVO(organo);
		}

		checkOrganosAntecesores(organo);

		commit();
	}

	/**
	 * Obtiene la lista de órganos existentes en el sistema.
	 *
	 * @return Lista de órganos {@link CAOrganoVO}
	 */
	public List getOrganos(Boolean vigente) {
		return caOrganoDbEntity.getCAOrgProductoresVOList(vigente);
	}

	/**
	 * Obtiene la información de un órgano.
	 *
	 * @param idOrgano
	 *            Identificador del órgano.
	 * @return Información de un órgano.
	 */
	public CAOrganoVO getCAOrgProductorVOXId(String idOrgano) {
		return caOrganoDbEntity.getCAOrgProductorVOXId(idOrgano);
	}

	/**
	 * Obtiene la información de un conjunto de órganos.
	 *
	 * @param idOrgano
	 *            Lista de dentificadores de órgano.
	 * @return Lista de organos {@link CAOrganoVO}.
	 */
	public List getCAOrgProductorVOXId(String[] idOrgano) {
		return caOrganoDbEntity.getCAOrgProductorVOXId(idOrgano);
	}

	/**
	 * Obtiene la información de un órgano.
	 *
	 * @param sistExtGestor
	 *            Sistema Gestor de Organismos externo.
	 * @param idEnSistExt
	 *            Identificador del organismo en el Sistema Gestor de Organismos
	 *            externo.
	 * @return Información de un órgano.
	 */
	public CAOrganoVO getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
			String sistExtGestor, String idEnSistExt, String vigente) {

		if (Constants.TRUE_STRING.equalsIgnoreCase(vigente)) {
			return caOrganoDbEntity
					.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestorYVigencia(
							sistExtGestor, idEnSistExt, true);
		} else {
			return caOrganoDbEntity
					.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
							sistExtGestor, idEnSistExt);
		}
	}

	/**
	 * Obtiene la lista de órganos a partir del Sistema Gestor Externo y una
	 * lista de identificadores en ese sistema.
	 *
	 * @param sistExtGestor
	 *            Sistema Gestor de Organismos Externo.
	 * @param idsEnSistExt
	 *            Lista de identificadores en el Sistema Gestor de Organismos
	 *            Externo.
	 * @return Lista de órganos.
	 */
	public List getCAOrgProductorVOListXSistExtGestorYIdOrgsExtGestor(
			String sistExtGestor, Object idsEnSistExt) {
		return caOrganoDbEntity
				.getCAOrgProductorVOListXSistExtGestorYIdOrgsExtGestor(
						sistExtGestor, idsEnSistExt);
	}

	/**
	 * Obtiene el organo de un usuario con validez en el organo(comprueba fecha
	 * ini y fecha fin)
	 *
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Roles del usuario.
	 */
	// public CAOrganoVO getOrganoUsuarioValido(String idUsuario) {
	// return organoUsuarioDBEntity.getCAOrganoUsuarioValido(idUsuario);
	// }

	public CAOrganoVO getCAOrganoUsuario(String idUsuario) {
		return organoUsuarioDBEntity.getCAOrganoUsuario(idUsuario);
	}

	/**
	 * Obtiene la lista de identificadores de usuarios de órganos.
	 *
	 * @param idOrgs
	 *            Lista de identificadores de órganos.
	 * @return Lista de identificadores de usuarios.
	 */
	public List getUsuariosOrganos(List idOrgs) {
		return organoUsuarioDBEntity.getUsuariosValidosEnOrganos(idOrgs);
	}

	/**
	 * Obtiene el órgano de un usuario dado de alta en Archivo.
	 *
	 * @param idUsuario
	 *            Identificador del usuario en Archivo.
	 * @param tipoUsuario
	 *            Tipo de usuario.
	 * @param idUsuarioOrg
	 *            Identificador del usuario en el Sistema Gestor de Organismos.
	 * @return Órgano en Archivo.
	 */
	public CAOrganoVO getOrganoUsuarioEnArchivo(String idUsuario,
			String tipoUsuario, String idUsuarioOrg) {
		CAOrganoVO organoEnArchivo = null;
		try {
			if (TipoUsuario.ADMINISTRADOR.equals(tipoUsuario)) {
				// mirar en la tabla de organousuario
				organoEnArchivo = organoUsuarioDBEntity
						.getCAOrganoUsuario(idUsuario);
			} else {
				// Información del Sistema Gestor de Organismos en la
				// configuración
				// de Archivo
				ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo();
				SistemaGestorOrganismos sgo = csa
						.findSistemaGestorOrganismosByUserType(tipoUsuario);
				if (sgo != null) {

					// Obtener información de la entidad
					ServiceClient serviceClient = getServiceClient();

					// Obtener la entidad para el usuario conectado
					Properties params = null;

					if ((serviceClient != null)
							&& (StringUtils.isNotEmpty(serviceClient
									.getEntity()))) {
						params = new Properties();
						params.put(MultiEntityConstants.ENTITY_PARAM,
								serviceClient.getEntity());
					}

					// Cargar el conector con el Sistema Gestor de Organización
					GestorOrganismos oc = GestorOrganismosFactory
							.getConnectorByUserType(tipoUsuario, params);
					if (oc != null) {
						if (!sgo.isInterno()) {
							// Recuperar el órgano al que pertenece el usuario
							InfoOrgano organo = oc
									.recuperarOrganodeUsuario(idUsuarioOrg);
							if (organo != null) {
								// Añadir el órgano del usuario en archivo
								organoEnArchivo = getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
										sgo.getId(), organo.getId(), null);
							}
						} else {
							// mirar en la tabla de organousuario
							organoEnArchivo = organoUsuarioDBEntity
									.getCAOrganoUsuario(idUsuarioOrg);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ArchivoModelException(e, this.getClass().getName(),
					"Error al obtener el \u00F3rgano de un usuario en Archivo.");
		}
		return organoEnArchivo;
	}

	public List getArchivosCustodia(String idUsaurio) {
		List grupos = getGruposUsuario(idUsaurio);
		List archivosCustodia = null;
		if (grupos != null && grupos.size() > 0) {
			archivosCustodia = new ArrayList();
			for (Iterator i = grupos.iterator(); i.hasNext();) {
				GrupoVO unGrupo = (GrupoVO) i.next();
				if (unGrupo.getIdArchivo() != null)
					archivosCustodia.add(unGrupo.getIdArchivo());
			}
			if (archivosCustodia.size() > 0)
				return archivoDbEntity
						.getArchivosXId((String[]) archivosCustodia
								.toArray(ArrayUtils.EMPTY_STRING_ARRAY));
		}
		return null;
	}

	public void eliminarOrgano(String idOrgano)
			throws ActionNotAllowedException {
		checkPermission(ControlAccesoSecurityManager.ELIMINAR_ORGANO);
		iniciarTransaccion();
		caOrganoDbEntity.eliminarOrgano(idOrgano);
		commit();
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionControlUsuariosBI#isNombreGrupoDuplicado(java.lang.String
	 * , java.lang.String)
	 */
	public boolean isNombreGrupoDuplicado(String idgrupo, String nombre) {
		List listaGrupos = grupoDBEntity.getGruposXNombreConIdDistinto(idgrupo,
				nombre);
		if (!ListUtils.isEmpty(listaGrupos)) {
			return true;
		}

		return false;
	}

	public List getGestoresSerie() {
		return getUsuariosConPermisos(new String[] {
				AppPermissions.GESTOR_SERIE,
				AppPermissions.ADMINISTRACION_TOTAL_SISTEMA });
	}

	// public boolean isGestorSerie(String idUser) {
	// if (userHasPermission(idUser, AppPermissions.GESTOR_SERIE))
	// return true;
	// if (userHasPermission(idUser,
	// AppPermissions.ADMINISTRACION_TOTAL_SISTEMA))
	// return true;
	// return false;
	// }

	/**
	 * Obtiene los permisos de los que dispone un usuario
	 *
	 * @param idUsuario
	 *            Identificador de usuario del sistema
	 * @return Lista de permisos {@link gcontrol.vos.PermisoVO}
	 */
	public List getPermisosUsuario(String idUsuario) {
		List listaPermisos = new ArrayList();
		// Obtener los roles del usuario
		List roles = getRolesUsuario(idUsuario);
		// Añadir los permisos de los roles
		if (roles != null) {
			for (int i = 0; i < roles.size(); i++) {
				// Recuperar el rol
				RolVO rol = (RolVO) roles.get(i);
				if (logger.isDebugEnabled())
					logger.debug("Rol:" + Constants.NEWLINE + rol);
				// Obtener los permisos del rol
				List permisos = getPermisosRol(rol.getId());
				for (int j = 0; (permisos != null) && (j < permisos.size()); j++) {
					PermisoVO permiso = (PermisoVO) permisos.get(j);
					if (logger.isDebugEnabled())
						logger.debug("Permiso:" + Constants.NEWLINE + permiso);
					// Añadir el permiso
					listaPermisos.add(permiso);
				}
			}
		}
		return listaPermisos;
	}

	/**
	 * Comprueba si un usario dispone de un permiso
	 *
	 * @param idUsuario
	 *            Identificador de usuario
	 * @param permiso
	 *            Permiso {@link AppPermissions}
	 * @return true en caso de que el usuario diponga del permiso indicado y
	 *         false en caso contrario
	 */
	public boolean userHasPermission(String idUsuario, String permiso) {

		boolean hasPermission = false;

		// El usuario debe existir
		UsuarioVO usuario = getUsuario(idUsuario);
		if (usuario != null) {
			if (TipoUsuario.ADMINISTRADOR.equals(usuario.getTipo())) {
				hasPermission = true;
			} else {
				// Obtener los roles del usuario
				List roles = getRolesUsuario(idUsuario);
				// Añadir los permisos de los roles
				if (roles != null) {
					List permissions = new ArrayList();
					for (int i = 0; i < roles.size(); i++) {
						// Recuperar el rol
						RolVO rol = (RolVO) roles.get(i);
						// Obtener los permisos del rol
						List permisos = getPermisosRol(rol.getId());
						for (int j = 0; (permisos != null)
								&& (j < permisos.size()); j++)
							permissions.add(String
									.valueOf(((PermisoVO) permisos.get(j))
											.getPerm()));
					}
					// Ordenar la lista de permisos
					String[] permissionsArray = (String[]) permissions
							.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
					Arrays.sort(permissionsArray);
					hasPermission = (Arrays.binarySearch(permissionsArray,
							permiso) >= 0)
							|| (Arrays
									.binarySearch(
											permissionsArray,
											AppPermissions.ADMINISTRACION_TOTAL_SISTEMA) >= 0);
				}
			}
		}
		return hasPermission;
	}

	/**
	 * Obtiene los grupos que estan asociados a un determinado archivo
	 *
	 * @param idArchivo
	 *            Identiicador del archivo al que estan asociados los grupos
	 * @return
	 */
	public List getGruposArchivo(String idArchivo) {
		return grupoDBEntity.getGruposArchivo(idArchivo);
	}

	/**
	 * Obtiene los grupos existentes
	 *
	 * @return Listado de grupos existentes
	 */
	public List getGrupos() {
		return grupoDBEntity.getGrupos();
	}

	/**
	 * Obtiene un grupo a partir de su identificador
	 *
	 * @param id
	 *            Identificador del grupo
	 * @return Grupo asociado al id
	 */
	public GrupoVO getGrupo(String id) {
		return grupoDBEntity.getGrupo(id);
	}

	public List getGrupos(String[] ids) {
		return grupoDBEntity.getGrupos(ids);
	}

	private void checkAltaUsuario(UsuarioVO user)
			throws UsuariosNotAllowedException {
		if (usuarioDBEntity.getUsuario(user.getTipo(),
				user.getIdUsrsExtGestor()) != null)
			throw new UsuariosNotAllowedException(
					UsuariosNotAllowedException.XUSUARIO_YA_EXISTE);
	}

	private void checkEdicionUsuario(UsuarioVO user)
			throws UsuariosNotAllowedException {
		// comporbaciones de valores
		return;
	}

	public void insertUsuario(UsuarioVO user, String idSistGestor,
			String idOrganoEnSistGestor) throws UsuariosNotAllowedException {
		LoggingEvent event = AuditUsuarios.getLogginEventAltaUsuario(this);
		Locale locale = getServiceClient().getLocale();
		checkPermission(ControlAccesoSecurityManager.ALTA_USUARIO);
		checkAltaUsuario(user);
		iniciarTransaccion();
		usuarioDBEntity.insert(user);
		AuditUsuarios.addDataLogInfoUsuario(locale, event, user);
		// esta comprobacion es por si el usuario no tiene sistema gestor
		if (idOrganoEnSistGestor != null) {
			// comproabr que el organo esta en el sistema(si es interno tamb
			// valdra esta busqueda)
			CAOrganoVO organoEnSistema = caOrganoDbEntity
					.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
							idSistGestor, idOrganoEnSistGestor);
			if (organoEnSistema == null)
				throw new UsuariosNotAllowedException(
						UsuariosNotAllowedException.XORGANO_NO_EXISTE_EL_SISTEMA_ES_NECESARIO_CREARLO);
		}
		commit();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionControlUsuariosBI#insertUsuario(gcontrol.vos.UsuarioVO)
	 */
	public void insertUsuarioConSistOrgInterno(UsuarioVO user,
			String idOrganoEnArchivo, Date fechaIni, Date fechaFin)
			throws UsuariosNotAllowedException {
		// AUDITORIA
		LoggingEvent event = AuditUsuarios.getLogginEventAltaUsuario(this);
		Locale locale = getServiceClient().getLocale();
		checkPermission(ControlAccesoSecurityManager.ALTA_USUARIO);
		checkAltaUsuario(user);
		iniciarTransaccion();
		usuarioDBEntity.insert(user);
		user.setIdUsrSistOrg(user.getId());
		usuarioDBEntity.updateUsuario(user);
		// AUDITORIA
		DataLoggingEvent dataEvent = AuditUsuarios.addDataLogInfoUsuario(
				locale, event, user);
		// obtener el organo interno
		CAOrganoVO organoEnSistema = getOrganosXIdEnArchivo(idOrganoEnArchivo);
		if (organoEnSistema == null)
			throw new UsuariosNotAllowedException(
					UsuariosNotAllowedException.XORGANO_NO_EXISTE_EL_SISTEMA_ES_NECESARIO_CREARLO);
		UsuarioOrganoVO usuarioOrganoVO = new UsuarioOrganoVO();
		usuarioOrganoVO.setIdUsuario(user.getId());
		usuarioOrganoVO.setIdOrgano(organoEnSistema.getIdOrg());
		usuarioOrganoVO.setFechaIni(fechaIni);
		usuarioOrganoVO.setFechaFin(fechaFin);
		// AUDITORIA
		AuditUsuarios.addDetalleInfoOrgano(locale, dataEvent, user);
		organoUsuarioDBEntity.insertUsuarioOrgano(usuarioOrganoVO);
		commit();
	}

	/**
	 * Crea un usuario externo.
	 *
	 * @param user
	 *            Información del usuario.
	 * @throws UsuariosNotAllowedException
	 *             si ocurre algún error.
	 */
	public UsuarioVO crearUsuarioExterno(UsuarioVO user)
			throws UsuariosNotAllowedException {
		LoggingEvent event = AuditUsuarios.getLogginEventAltaUsuario(this);
		Locale locale = getServiceClient().getLocale();

		checkAltaUsuario(user);

		iniciarTransaccion();

		user = usuarioDBEntity.insert(user);
		AuditUsuarios.addDataLogInfoUsuario(locale, event, user);

		LoggingEvent event2 = AuditUsuarios.getLogginEventAsignacionRole(this);

		ConfiguracionControlAcceso sca = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo()
				.getConfiguracionControlAcceso();
		RolVO rol = getRol(sca.getPerfilUsuariosExternos());
		List roles = new ArrayList();
		roles.add(rol);

		rolUsuarioDBEntity.insertRolUsuario(user.getId(), rol);
		AuditUsuarios.addDataLogInfoRoles(locale, event2, roles);

		commit();

		return user;
	}

	/**
	 * Permite crear un usuario de administración
	 *
	 * @param username
	 *            Nombre del usuario
	 * @return UsuarioVO
	 */
	public UsuarioVO crearUsuarioAdministracion(String username) {

		Locale locale = getServiceClient().getLocale();

		// Si no existe el usuario crearlo
		UsuarioVO usuario = new UsuarioVO();
		usuario.setNombre(username);
		usuario.setApellidos(null);
		usuario.setEmail(null);
		usuario.setDireccion(null);
		usuario.setTipo(ConfiguracionControlAcceso.SUPERUSER_TYPE);
		usuario.setHabilitado(Constants.TRUE_STRING);
		usuario.setFMaxVigencia(null);
		usuario.setIdUsrsExtGestor(Constants.STRING_SPACE);
		usuario.setIdUsrSistOrg(null);
		usuario.setDescripcion(null);

		LoggingEvent event = AuditUsuarios.getLogginEventAltaUsuario(this);

		iniciarTransaccion();

		usuario = usuarioDBEntity.insert(usuario);
		AuditUsuarios.addDataLogInfoUsuario(locale, event, usuario);

		usuario.setIdUsrsExtGestor(usuario.getId());
		usuarioDBEntity.updateUsuario(usuario);

		commit();

		return usuario;
	}

	// /**
	// * @param user
	// */
	// private void checkUserNoExist(UsuarioVO user) throws
	// UsuariosNotAllowedException{
	// UsuarioVO userToFind = usuarioDBEntity.getUsuario(user.getTipo(),
	// user.getIdUsrsExtGestor());
	// if (userToFind!=null)
	// throw new
	// UsuariosNotAllowedException(UsuariosNotAllowedException.XUSUARIO_YA_EXISTE);
	// return;
	// }
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionControlUsuariosBI#updateUsuarioConSistOrgInterno(gcontrol
	 * .vos.UsuarioVO, java.lang.String, java.util.Date, java.util.Date)
	 */
	public void updateUsuario(UsuarioVO user)
			throws UsuariosNotAllowedException {
		updateUsuarioConSistOrgInterno(user, null, null, null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionControlUsuariosBI#updateUsuario(gcontrol.vos.UsuarioVO)
	 */
	public void updateUsuarioConSistOrgInterno(UsuarioVO user,
			String idOrganoEnSistInterno, Date fechaIni, Date fechaFin)
			throws UsuariosNotAllowedException {
		// AUDITORIA
		Locale locale = getServiceClient().getLocale();
		LoggingEvent event = AuditUsuarios
				.getLogginEventModificacionUsuario(this);
		checkPermission(ControlAccesoSecurityManager.MODIFICACION_USUARIO);
		checkEdicionUsuario(user);
		iniciarTransaccion();
		usuarioDBEntity.updateUsuario(user);
		// AUDITORIA
		DataLoggingEvent dataEvent = AuditUsuarios.addDataLogInfoUsuario(
				locale, event, user);
		if (idOrganoEnSistInterno != null) {
			UsuarioOrganoVO usuarioOrgano = organoUsuarioDBEntity
					.getUsuarioOrgano(user.getId());
			if (usuarioOrgano == null) {
				usuarioOrgano = new UsuarioOrganoVO();
				usuarioOrgano.setIdUsuario(user.getId());
				usuarioOrgano.setIdOrgano(idOrganoEnSistInterno);
				usuarioOrgano.setFechaIni(fechaIni);
				usuarioOrgano.setFechaFin(fechaFin);
				organoUsuarioDBEntity.insertUsuarioOrgano(usuarioOrgano);
			} else {
				usuarioOrgano.setIdOrgano(idOrganoEnSistInterno);
				usuarioOrgano.setFechaIni(fechaIni);
				usuarioOrgano.setFechaFin(fechaFin);
				organoUsuarioDBEntity.updateUsuarioOrgano(usuarioOrgano);
			}
			// AUDITORIA
			AuditUsuarios.addDetalleInfoOrgano(locale, dataEvent, user);
		}
		commit();
	}

	/**
	 * Obtiene la jerarquia de un organo. Esta información se obtiene
	 * consultando el sistema externo que mantiene la estructura de la
	 * organización.
	 *
	 * @param idSistemaOrganizacion
	 *            Identificador del sistema externo que mantiene la estructura
	 *            de la organización
	 * @param idOrganoSExternoGestor
	 *            Identificador del organo en ese sistema externo
	 * @return Lista de órganos que forman parte de la jerarquía del organo en
	 *         la estructura organizativa {@link CAOrganoVO}
	 * @throws GestorOrganismosException
	 *             Caso de que se produzca un error en la comunicación con el
	 *             sistema externo que mantiene la estructura de la organización
	 * @throws NotAvailableException
	 *             Cuando el sistema externo de gestión de organización no puede
	 *             proveer la información que se le solicita
	 *
	 */
	public List getOrganizationInfoInArchivo(String idSistemaOrganizacion,
			String idOrganoSExternoGestor) throws GestorOrganismosException,
			NotAvailableException {
		if (logger.isDebugEnabled())
			logger.debug("Obteniendo jerarquia en la estructura de organización para organo: "
					+ idOrganoSExternoGestor
					+ " en sistema externo: "
					+ idSistemaOrganizacion);
		List ret = null;
		// try {

		// Obtener información de la entidad
		ServiceClient serviceClient = getServiceClient();

		// Obtener la entidad para el usuario conectado
		Properties params = null;

		if ((serviceClient != null)
				&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
			params = new Properties();
			params.put(MultiEntityConstants.ENTITY_PARAM,
					serviceClient.getEntity());
		}

		GestorOrganismos oc = GestorOrganismosFactory.getConnectorById(
				idSistemaOrganizacion, params);
		if (oc != null) {
			InfoOrgano organo = oc.recuperarOrgano(
					TipoAtributo.IDENTIFICADOR_ORGANO, idOrganoSExternoGestor);
			if (organo != null) {
				// Información del Sistema Gestor de Organismos en la
				// configuración de Archivo
				ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo();
				SistemaGestorOrganismos sgo = csa
						.findSistemaGestorOrganismosById(idSistemaOrganizacion);
				if (!sgo.isInterno()) {
					ret = new ArrayList();
					// Obtener los organos antecesores
					List orgAntExtList = oc.recuperarOrganosAntecesores(
							organo.getId(), 0);
					if (!util.CollectionUtils.isEmptyCollection(orgAntExtList)) {
						for (int i = 0; i < orgAntExtList.size(); i++)
							ret.add(getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
									sgo.getId(),
									((InfoOrgano) orgAntExtList.get(i)).getId(),
									null));
					}
					ret.add(getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
							sgo.getId(), organo.getId(), null));
				}
			} else
				throw new ArchivoModelException(getClass(),
						"getOrganizationInfoInArchivo",
						"Organo no encontrado en sistema externo");
		} else
			throw new ArchivoModelException(getClass(),
					"getOrganizationInfoInArchivo",
					"Sistema externo de gestión de organización no localizado "
							+ idSistemaOrganizacion);
		// } catch (GestorOrganismosException goe) {
		// logger.error("Error al recuperar la información de organizaci\u00F3n
		// ", goe);
		// throw new UncheckedArchivoException(goe);
		// } catch (NotAvailableException nae) {
		// logger.error("Error al recuperar la información de organizaci\u00F3n
		// ", nae);
		// throw new UncheckedArchivoException(nae);
		// }
		return ret;
	}

	// TODO Pasar este metodo a algo de presentación
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionControlUsuariosBI#getOrganizationInfoString(java.lang
	 * .String, java.lang.String)
	 */
	public String getOrganizationInfoString(String idSistemaOrganizacion,
			String idOrgano) {
		// obtener string de organizacion
		StringBuffer stringOrganizacion = new StringBuffer();
		try {
			List organizacion = getOrganizationInfoInArchivo(
					idSistemaOrganizacion, idOrgano);
			if (!util.CollectionUtils.isEmptyCollection(organizacion)) {
				for (Iterator itOrganizacion = organizacion.iterator(); itOrganizacion
						.hasNext();) {
					CAOrganoVO organo = (CAOrganoVO) itOrganizacion.next();
					stringOrganizacion.append(
							Constants.SEPARADOR_ANTECESORES_ORGANO).append(
							organo.getNombre());
				}
			}
		} catch (GestorOrganismosException goe) {
		} catch (NotAvailableException nae) {
		}
		return stringOrganizacion.toString();
	}

	protected class FinderTipoUsuario implements Predicate {
		String tipoToFind = null;

		FinderTipoUsuario(String tipoToFind) {
			this.tipoToFind = tipoToFind;
		}

		public boolean evaluate(Object arg0) {
			return tipoToFind.equalsIgnoreCase(((Usuario) arg0).getTipo());
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionControlUsuariosBI#isUsuarioDeSistemaOrganizacionInterno
	 * ()
	 */
	public boolean isUsuarioDeSistemaOrganizacionInterno(String tipoUsuario) {
		// obtencion de los datos de tipo de usuario
		/*
		 * List tiposUsuarios = getTiposUsuario(); Usuario objTipoUsuario =
		 * (Usuario) CollectionUtils.find(tiposUsuarios, new
		 * FinderTipoUsuario(tipoUsuario)); ConfiguracionSistemaArchivo
		 * sistemaGestorOrganismo =
		 * ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo();
		 * String idSisgestorOrganismoInterno =
		 * sistemaGestorOrganismo.getSistemaInternoGestorOrganismos().getId();
		 * if (objTipoUsuario.getIdSistGestorOrg() != null) return
		 * objTipoUsuario
		 * .getIdSistGestorOrg().equalsIgnoreCase(idSisgestorOrganismoInterno);
		 */

		SistemaGestorOrganismos sgo = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo()
				.findSistemaGestorOrganismosByUserType(tipoUsuario);

		return ((sgo != null) && sgo.isInterno());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionControlUsuariosBI#getOrganosXIdSistExtGestor(java.lang
	 * .String)
	 */
	public List getOrganosXIdSistExtGestor(String idSisExtGestor) {
		return caOrganoDbEntity.getCAOrgProductorVOXSistExtGestorYVigencia(
				idSisExtGestor, true);
	}

	/**
	 * Obtiene la lista de roles asociados a alguno de los modulos indicados
	 *
	 * @param modules
	 *            Lista de identificadores de modulo
	 * @return Lista de roles {@link gcontrol.vos.RolVO}
	 */
	public List getRoles(int[] modules) {
		return rolDBEntity.getRoles(modules);
	}

	/**
	 * Obtiene la informacion de un rol de usuario
	 *
	 * @param idRol
	 *            Identificador de rol
	 * @return Datos de rol {@link RolVO}
	 */
	public RolVO getRol(String idRol) {
		return rolDBEntity.getRolById(idRol);
	}

	/**
	 * Pone el rol solicitado a disposicion del usuario que invoca el metodo de
	 * manera que unicamente dicho usuario puede llevar a cabo acciones sobre el
	 * rol
	 *
	 * @param idRol
	 *            Identificador de rol
	 * @return Datos de rol {@link RolVO}
	 */
	public RolVO abrirRol(String idRol) {
		RolVO rol = rolDBEntity.getRolById(idRol);
		getRolAuthorizationHelper().configureRol(rol);
		return rol;
	}

	/**
	 * Almacena la informacion referente a un rol
	 *
	 * @param rol
	 *            Datos del rol
	 * @param permisoRol
	 *            Lista de permisos para el rol
	 * @throws ActionNotAllowedException
	 *             La creacion o actualizacion del rol no puede ser llevada a
	 *             cabo
	 */
	public void guardarRol(RolVO rol, String[] permisosRol)
			throws ActionNotAllowedException {
		iniciarTransaccion();
		if (rol.getId() == null) {
			checkPermission(ControlAccesoSecurityManager.ALTA_ROL);
			rolDBEntity.insertRol(rol);
		} else {
			checkPermission(ControlAccesoSecurityManager.MODIFICACION_ROL);
			rolDBEntity.updateRol(rol);
		}
		if (permisosRol != null)
			permisoRolDBEntity.setPermisosRol(rol, permisosRol);
		commit();
	}

	/**
	 * Quita un conjunto de permisos de un rol
	 *
	 * @param idRol
	 *            Identificador de rol
	 * @param permisoRol
	 *            Lista de permisos a eliminar
	 */
	public void quitarPermisosRol(String idRol, String[] permisoRol) {
		iniciarTransaccion();
		permisoRolDBEntity.quitarPermisosRol(idRol, permisoRol);
		commit();
	}

	/**
	 * Asocia un conjunto de permisos a un rol
	 *
	 * @param idRol
	 *            Identificador de rol
	 * @param permisoRol
	 *            Lista de permisos
	 */
	public void agregarPermisosRol(String idRol, String[] permisoRol) {
		iniciarTransaccion();
		RolVO rol = getRol(idRol);
		permisoRolDBEntity.agregarPermisosRol(rol, permisoRol);
		commit();
	}

	/**
	 * Elimina del sistema los roles indicados
	 *
	 * @param roles
	 *            Lista de identificadores de rol a eliminar
	 */
	public void eliminarRol(String[] roles) throws ActionNotAllowedException {
		iniciarTransaccion();
		checkPermission(ControlAccesoSecurityManager.ELIMINAR_ROL);
		rolUsuarioDBEntity.clearUsuariosRol(roles);
		permisoRolDBEntity.clearPermisosRol(roles);
		rolDBEntity.eliminarRoles(roles);
		commit();
	}

	/**
	 * Obtiene los usuarios que tienen asociado un determinado rol
	 *
	 * @param idRol
	 *            Identificador de rol
	 * @return Lista de usuarios {@link UsuarioVO}
	 */
	public List getUsuariosConRol(String idRol) {
		List usuarios = rolUsuarioDBEntity.getUsuariosByRol(idRol);
		return usuarios;
	}

	/**
	 * Elimina los roles indicados de la lista de roles de un conjunto de
	 * usuarios
	 *
	 * @param roles
	 *            Roles a eliminar {@link RolVO}
	 * @param usuarios
	 *            Identificadores de los usurios a los que se les quita el rol
	 */
	public void quitarRolesUsuario(List roles, String[] usuarios)
			throws ActionNotAllowedException {
		checkPermission(ControlAccesoSecurityManager.MODIFICACION_USUARIO);
		Locale locale = getServiceClient().getLocale();
		// AUDITORIA
		LoggingEvent event = AuditUsuarios.getLogginEventDeasignacionRole(this);
		if (roles != null && roles.size() > 0) {
			List listaRoles = new ArrayList(roles);
			// AUDITORIA
			AuditUsuarios.addDataLogInfoRoles(locale, event, listaRoles);
			CollectionUtils.transform(listaRoles,
					VO2IDTransformer.getInstance());
			String[] roleIDs = (String[]) listaRoles
					.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
			iniciarTransaccion();
			int nUsuarios = usuarios.length;
			for (int i = 0; i < nUsuarios; i++) {
				rolUsuarioDBEntity.removeRolesUsuario(usuarios[i], roleIDs);
			}
			commit();
		}
	}

	/**
	 * Añade roles a la lista de roles asociados a una serie de usuarios
	 *
	 * @param roles
	 *            Roles a añadir {@link RolVO}
	 * @param usuarios
	 *            Identificadores de los usurios a los que se les añade el rol
	 */
	public void agregarRolesUsuario(List roles, String[] usuarios) {

		checkPermission(ControlAccesoSecurityManager.MODIFICACION_USUARIO);
		Locale locale = getServiceClient().getLocale();

		// AUDITORIA
		LoggingEvent event = AuditUsuarios.getLogginEventAsignacionRole(this);

		if (roles != null && usuarios != null) {

			// AUDITORIA
			AuditUsuarios.addDataLogInfoRoles(locale, event, roles);

			iniciarTransaccion();

			// UsuarioVO usuario = null;
			int nUsuarios = usuarios.length;
			for (int i = 0; i < nUsuarios; i++) {
				List rolesDelUsuario = rolUsuarioDBEntity
						.getRolesUsuario(usuarios[i]);
				for (Iterator j = roles.iterator(); j.hasNext();) {
					// comprobar que el usuario no se encuentra ya en un role
					final RolVO rolAInsertar = (RolVO) j.next();

					RolVO rolEncontradoVO = (RolVO) CollectionUtils.find(
							rolesDelUsuario, new Predicate() {
								public boolean evaluate(Object arg0) {
									if (((RolVO) arg0).getId()
											.equalsIgnoreCase(
													rolAInsertar.getId()))
										return true;
									return false;
								}
							});

					// TODO REVISAR SI ESTO ES MEJOR ASI : solo añado si no lo
					// esta ya(no doy excepcion)
					if (rolEncontradoVO == null)
						rolUsuarioDBEntity.insertRolUsuario(usuarios[i],
								rolAInsertar);
				}
			}

			commit();

		}
	}

	/**
	 * Establece los roles de un usuario
	 *
	 * @param roles
	 *            Lista de roles {@link RolVO}
	 * @param idUsuario
	 *            Identificador de usuario
	 */
	public void setRolesUsuario(List roles, String idUsuario) {
		checkPermission(ControlAccesoSecurityManager.MODIFICACION_USUARIO);
		Locale locale = getServiceClient().getLocale();
		iniciarTransaccion();
		// AUDITORIA
		LoggingEvent event1 = AuditUsuarios
				.getLogginEventDeasignacionRole(this);
		AuditUsuarios.addDataLogInfoRoles(locale, event1, roles);
		rolUsuarioDBEntity.removeRolesUsuario(idUsuario, null);
		String[] usuario = { idUsuario };
		// AUDITORIA
		LoggingEvent event2 = AuditUsuarios.getLogginEventAsignacionRole(this);
		AuditUsuarios.addDataLogInfoRoles(locale, event2, roles);
		agregarRolesUsuario(roles, usuario);
		commit();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see common.bi.GestionControlUsuariosBI#getInfoUsuarioEnOrgano()
	 */
	public UsuarioOrganoVO getInfoUsuarioEnOrgano(String idUsuarioEnArchivo) {
		return organoUsuarioDBEntity.getUsuarioOrgano(idUsuarioEnArchivo);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionControlUsuariosBI#isUsuarioSinSistemaOrganizacion(java
	 * .lang.String)
	 */
	public boolean isUsuarioSinSistemaOrganizacion(String tipoUsuario) {
		SistemaGestorOrganismos sgo = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo()
				.findSistemaGestorOrganismosByUserType(tipoUsuario);

		return (sgo == null);
	}

	public CAOrganoVO getOrganoUsuarioEnArchivo(String idUsuario) {
		UsuarioVO usuario = getUsuario(idUsuario);
		return getOrganoUsuarioEnArchivo(idUsuario, usuario.getTipo(),
				usuario.getIdUsrSistOrg());
	}

	/**
	 * Busqueda de usuarios por nombre
	 *
	 * @param query
	 *            Cadena que debe estar contenida en el nombre o apellidos de
	 *            los usuarios
	 * @return Lista de usuarios {@link UsuarioVO}
	 */
	public List findUsersByName(String query) {
		return usuarioDBEntity.findByName(query);
	}

	public List findGruposByName(String query) {
		return grupoDBEntity.findByName(query);
	}

	/**
	 * Obtiene los órganos por código y/o nombre.
	 *
	 * @param codigo
	 *            Código del órgano.
	 * @param nombre
	 *            Nombre del órgano.
	 * @return Lista de órganos {@link CAOrganoVO}.
	 */
	public List findOrganos(String codigo, String nombre, String vigente) {
		return caOrganoDbEntity.findByCodeAndName(codigo, nombre, vigente);
	}

	/**
	 * Obtiene los órganos cuyo nombre contiene la cadena suministrada
	 *
	 * @param query
	 *            Patrón de búsqueda a localizar en el nombre del órgano
	 * @return Lista de órganos cuyo nombre contiene el patrón indicado
	 *         {@link CAOrganoVO}
	 */
	public List findOrganosByName(String query) {
		return caOrganoDbEntity.findByName(query, null);
	}

	/**
	 * Incorpora al sistema un grupo de usuarios
	 *
	 * @param grupo
	 *            Datos de grupo de usuarios
	 */
	public void guardarGrupo(GrupoVO grupo) throws ActionNotAllowedException {
		iniciarTransaccion();
		if (grupo.getId() == null) {
			checkPermission(ControlAccesoSecurityManager.ALTA_GRUPO);
			grupoDBEntity.insertGrupo(grupo);
		} else {
			checkPermission(ControlAccesoSecurityManager.MODIFICACION_GRUPO);
			grupoDBEntity.updateGrupo(grupo);
		}
		commit();
	}

	/**
	 * Elimina del sistema los grupos indicados
	 *
	 * @param grupos
	 *            Lista de identificadores de grupo
	 */
	public void eliminarGrupo(String[] grupos) throws ActionNotAllowedException {
		checkPermission(ControlAccesoSecurityManager.ELIMINAR_GRUPO);
		iniciarTransaccion();
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionAuditoriaBI auditoriaBI = services.lookupGestionAuditoriaBI();
		grupoDBEntity.eliminarGrupos(grupos);
		auditoriaBI.removeTipoAuditado(CritUsuarioVO.TIPO_GRUPO, grupos);
		commit();
	}

	/**
	 * Obtiene la lista de usuarios que pertenecen a un grupo
	 *
	 * @param idGrupo
	 *            Identificador de grupo
	 * @return Lista de usuarios {@link UsuarioVO}
	 */
	public List getUsuariosEnGrupo(String idGrupo) {
		return grupoUsuarioDBEntity.getUsuariosGrupo(idGrupo);
	}

	/**
	 * Incorpora usuarios a grupos
	 *
	 * @param idGrupo
	 *            Identificadores de grupo
	 * @param idUsuario
	 *            Identificadores de usuario
	 */
	public void agregarUsuariosAGrupo(String[] idGrupo, String[] idUsuario)
			throws ActionNotAllowedException {
		checkPermission(ControlAccesoSecurityManager.MODIFICACION_GRUPO);
		Locale locale = getServiceClient().getLocale();
		List gruposVO = getGrupos(idGrupo);
		// AUDITORIA
		LoggingEvent event1 = AuditUsuarios.getLogginEventAsignacionGrupo(this);
		AuditUsuarios.addDataLogInfoGrupos(locale, event1, gruposVO);
		iniciarTransaccion();
		int nGrupos = idGrupo.length;
		int nUsuarios = idUsuario.length;
		for (int i = 0; i < nGrupos; i++)
			for (int j = 0; j < nUsuarios; j++)
				grupoUsuarioDBEntity.insertGrupoUsuario(idGrupo[i],
						idUsuario[j]);
		commit();
	}

	/**
	 * Elimina un conjunto de usuarios de los grupos indicados
	 *
	 * @param idGrupo
	 *            Identificadores de grupo
	 * @param idUsuario
	 *            Identificadores de usuario
	 */
	public void quitarUsuariosDeGrupo(String[] idGrupo, String[] idUsuario)
			throws ActionNotAllowedException {
		checkPermission(ControlAccesoSecurityManager.MODIFICACION_GRUPO);
		Locale locale = getServiceClient().getLocale();
		// AUDITORIA
		List gruposVO = getGrupos(idGrupo);
		LoggingEvent event1 = AuditUsuarios
				.getLogginEventDeasignacionGrupo(this);
		AuditUsuarios.addDataLogInfoGrupos(locale, event1, gruposVO);
		int nGrupos = idGrupo.length;
		iniciarTransaccion();
		for (int i = 0; i < nGrupos; i++)
			grupoUsuarioDBEntity.removeGrupoUsuario(idGrupo[i], idUsuario);
		commit();
	}

	/**
	 * Obtiene la informacion de una lista de acceso
	 *
	 * @param idListaAcceso
	 *            Identificador de lista de acceso
	 * @return Datos de lista de acceso {@link ListaAccesoVO}
	 */
	public ListaAccesoVO getListaAcceso(String idListaAcceso) {
		return listaControlAccesoDBEntity.getListaAccesoVOXId(idListaAcceso);
	}

	/**
	 * Guarda en el sistema una lista de acceso
	 *
	 * @param listaAcceso
	 *            Datos de lista de acceso
	 */
	public void guardarListaAcceso(ListaAccesoVO listaAcceso)
			throws ActionNotAllowedException {
		iniciarTransaccion();
		if (StringUtils.isEmpty(listaAcceso.getId())) {
			checkPermission(ControlAccesoSecurityManager.ALTA_LISTA_ACCESO);
			listaControlAccesoDBEntity.insertListaAccesoVO(listaAcceso);
		} else {
			checkPermission(ControlAccesoSecurityManager.MODIFICACION_LISTA_ACCESO);
			listaControlAccesoDBEntity.updateListaAccesoVO(listaAcceso);
		}
		commit();
	}

	/**
	 * Permite eliminar los órganos de un usuario
	 *
	 * @param idUsuario
	 *            Id del usuario
	 */
	public void eliminarOrganosUsuario(String idUsuario) {
		organoUsuarioDBEntity.deleteOrganosUsuario(idUsuario);
	}

	public void eliminarUsuarios(String[] idsUsuarios)
			throws ActionNotAllowedException {
		List usuariosABorrar = getUsuarios(idsUsuarios);
		checkPermission(ControlAccesoSecurityManager.ELIMINAR_USUARIO);
		Locale locale = getServiceClient().getLocale();
		// AUDITORIA
		// List usuariosVO = getUsuarios(idsUsuarios);
		LoggingEvent event1 = AuditUsuarios
				.getLogginEventEliminacionUsuario(this);
		// AuditUsuarios.addDataLogInfoUsuarios(event1, usuariosVO);
		AuditUsuarios.addDataLogInfoUsuarios(locale, event1, usuariosABorrar);
		iniciarTransaccion();
		if (!util.CollectionUtils.isEmptyCollection(usuariosABorrar)) {
			for (Iterator itUsuariosABorrar = usuariosABorrar.iterator(); itUsuariosABorrar
					.hasNext();) {
				UsuarioVO usuario = (UsuarioVO) itUsuariosABorrar.next();
				int errorCode = permitidoEliminarUsuario(usuario);
				if (errorCode < 0) {
					// eliminar relaciones con grupos
					List idGruposUsuario = getGruposUsuario(usuario.getId());
					for (Iterator itGrupos = idGruposUsuario.iterator(); itGrupos
							.hasNext();) {
						GrupoVO grupo = (GrupoVO) itGrupos.next();
						grupoUsuarioDBEntity.removeGrupoUsuario(grupo.getId(),
								new String[] { usuario.getId() });
					}
					// eliminar relaciones con roles
					List idRolesUsuario = getRolesUsuario(usuario.getId());
					String roles[] = new String[idRolesUsuario.size()];
					int i = 0;
					for (Iterator itRoles = idRolesUsuario.iterator(); itRoles
							.hasNext();) {
						RolVO rol = (RolVO) itRoles.next();
						roles[i++] = rol.getId();
					}
					if (roles.length > 0)
						rolUsuarioDBEntity.removeRolesUsuario(usuario.getId(),
								roles);
					// eliminar relaciones con organos en caso de q sea interno
					if (isUsuarioDeSistemaOrganizacionInterno(usuario.getTipo())
							|| (TipoUsuario.ADMINISTRADOR.equals(usuario
									.getTipo()))) {
						organoUsuarioDBEntity.deleteOrganosUsuario(usuario
								.getId());
					}
					// eliminar relaciones con listas de acceso
					permisosListaDbEntity.deletePermisosLista(null,
							TipoDestinatario.USUARIO, idsUsuarios);

					usuarioDBEntity.deleteUsuario(usuario);
				} else
					throw new UsuariosNotAllowedException(errorCode);
			}
		}
		commit();
	}

	private int permitidoEliminarUsuario(UsuarioVO usuario) {
		int returnValue = -1;
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionPrevisionesBI previsionBI = services
				.lookupGestionPrevisionesBI();
		List previsionesEnElaboracion = previsionBI
				.getPrevisionesEnElaboracion(usuario.getId());
		if (previsionesEnElaboracion != null
				&& previsionesEnElaboracion.size() > 0)
			returnValue = UsuariosNotAllowedException.USUARIO_TIENE_PREVISIONES_EN_ELABORACION;
		else {
			GestionRelacionesEntregaBI relacionBI = services
					.lookupGestionRelacionesBI();
			Collection relacionesEnElaboracion = relacionBI
					.getRelacionesEnElaboracionXUser(usuario.getId());
			if (relacionesEnElaboracion != null
					&& relacionesEnElaboracion.size() > 0)
				returnValue = UsuariosNotAllowedException.USUARIO_TIENE_RELACIONES_EN_ELABORACION;
			else {
				// Comprobar que no tiene relaciones Finalizadas
				// Collection relacionesFinalizadas =
				// relacionBI.getRelacionesFinalizadasXUser(usuario.getId());
				int relacionesUsuario = relacionBI
						.getCountRelacionesXUser(usuario.getId());
				if (relacionesUsuario > 0) {
					returnValue = UsuariosNotAllowedException.USUARIO_TIENE_RELACIONES;
				} else {
					GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
					List seriesUsuario = serieBI.findSeriesByGestor(usuario
							.getId());
					if (seriesUsuario != null && seriesUsuario.size() > 0)
						returnValue = UsuariosNotAllowedException.USUARIO_ES_GESTOR_SERIES;
					else {
						GestionPrestamosBI prestamoBI = services
								.lookupGestionPrestamosBI();
						if (prestamoBI.hasPrestamosEnCurso(usuario.getId()))
							returnValue = UsuariosNotAllowedException.USUARIO_TIENE_PRESTAMOS_EN_CURSO;
						else {
							GestionConsultasBI consultasBI = services
									.lookupGestionConsultasBI();
							if (consultasBI
									.hasConsultasEnCurso(usuario.getId())) {
								returnValue = UsuariosNotAllowedException.USUARIO_TIENE_CONSULTAS_EN_CURSO;
							} else {
								GestionSalasConsultaBI salasBI = services
										.lookupGestionSalasBI();
								if (salasBI
										.isUsuarioAsociadoAUsuarioSalasConsulta(usuario
												.getId())) {
									returnValue = UsuariosNotAllowedException.USUARIO_TIENE_ASOCIADO_USUARIO_SALA_CONSULTA;
								}
							}
						}
					}
				}
			}
		}

		return returnValue;
	}

	/**
	 * Elimina un conjunto de listas de acceso del sistema
	 *
	 * @param listasAcceso
	 *            Conjunto de identificadores de lista de acceso
	 */
	public void eliminarListasAcceso(String[] listasAcceso)
			throws UsuariosNotAllowedException {
		iniciarTransaccion();
		eliminarListasAccesoNoTransaccional(listasAcceso);
		commit();
	}

	public void eliminarListasAccesoNoTransaccional(String[] listasAcceso)
			throws UsuariosNotAllowedException {
		// obtener todas las listas de acceso
		List listasAEliminar = listaControlAccesoDBEntity
				.getListasControlAcceso(listasAcceso);
		for (Iterator itListasAEliminar = listasAEliminar.iterator(); itListasAEliminar
				.hasNext();) {
			ListaAccesoVO lista = (ListaAccesoVO) itListasAEliminar.next();

			checkRestriccionesBorrado(lista);

			// eliminar permisos
			permisosListaDbEntity
					.deletePermisosListaVOXIdListaCA(lista.getId());
			// eliminar lista

		}
		listaControlAccesoDBEntity.eliminarListasAcceso(listasAcceso);

	}

	public void eliminarListaAccesoNoTransaccional(String idLCA) {
		try {
			logger.debug("inicio Eliminación lista de acceso: " + idLCA);
			eliminarListasAccesoNoTransaccional(new String[] { idLCA });
			logger.debug("fin Eliminación lista de acceso: " + idLCA);
		} catch (UsuariosNotAllowedException e) {
			logger.debug("Lista de acceso no eliminable", e);
		}
	}

	public void checkRestriccionesBorrado(ListaAccesoVO lista)
			throws UsuariosNotAllowedException {

		if (lista.getTipo() == TipoListaControlAcceso.ELEMENTO_CUADRO_CLASIFICACION) {
			GestionCuadroClasificacionBI cuadroService = getServiceRepository()
					.lookupGestionCuadroClasificacionBI();
			List elementosEnLista = cuadroService
					.getElementoCuadroClasificacionXListaControlAcceso(lista
							.getId());
			if (!util.CollectionUtils.isEmptyCollection(elementosEnLista)) {
				throw new UsuariosNotAllowedException(
						UsuariosNotAllowedException.XLISTA_NO_BORRABLE_TIENE_ELEMENTOS_DEL_CUADRO_ASOCIADOS);

			}
		} else if (lista.getTipo() == TipoListaControlAcceso.DESCRIPTOR) {
			// ADDESCRIPTOR
			GestionDescripcionBI descripcionService = getServiceRepository()
					.lookupGestionDescripcionBI();
			List elementosEnLista = descripcionService
					.getDescriptoresXListaAcceso(lista.getId());
			if (!util.CollectionUtils.isEmptyCollection(elementosEnLista))
				throw new UsuariosNotAllowedException(
						UsuariosNotAllowedException.XLISTA_NO_BORRABLE_TIENE_DESCRIPTORES_ASOCIADOS);
		} else if (lista.getTipo() == TipoListaControlAcceso.FORMATO_FICHA) {
			GestionDescripcionBI descripcionService = getServiceRepository()
					.lookupGestionDescripcionBI();
			List elementosEnLista = descripcionService
					.getFmtsFichasXListaAcceso(lista.getId());
			if (!util.CollectionUtils.isEmptyCollection(elementosEnLista))
				throw new UsuariosNotAllowedException(
						UsuariosNotAllowedException.XLISTA_NO_BORRABLE_TIENE_FORMATOS_DE_FICHA_ASOCIADOS);
		}

		// Comprobar si se está utilizando en algún productor de la serie
		if (productorSerie.countProductoresByIdListaAcceso(lista.getId()) > 0) {
			throw new UsuariosNotAllowedException(
					UsuariosNotAllowedException.XLISTA_NO_BORRABLE_PERTENECE_A_PRODUCTOR_SERIE);
		}

	}

	public boolean isListaAccesoSinElementos(String idLista) {

		if (permisosListaDbEntity.getCountPermisosXIdLista(idLista) > 0) {
			return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionControlUsuariosBI#getUsuariosEnListaAcceso(java.lang
	 * .String)
	 */
	public List getUsuariosEnListaAcceso(String idLista) {
		List listaPermisos = permisosListaDbEntity
				.getPermisosXIdListaAccesoYTipo(idLista,
						TipoDestinatario.USUARIO);
		if (!util.CollectionUtils.isEmptyCollection(listaPermisos)) {
			String ids[] = new String[listaPermisos.size()];
			int i = 0;
			for (Iterator itListaPermiso = listaPermisos.iterator(); itListaPermiso
					.hasNext();) {
				PermisosListaVO permisosLista = (PermisosListaVO) itListaPermiso
						.next();
				ids[i++] = permisosLista.getIdDest();
			}
			return usuarioDBEntity.getUsuarios(ids);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionControlUsuariosBI#getGruposEnListaAcceso(java.lang.String
	 * )
	 */
	public List getGruposEnListaAcceso(String idLista) {
		List listaPermisos = permisosListaDbEntity
				.getPermisosXIdListaAccesoYTipo(idLista, TipoDestinatario.GRUPO);
		if (!util.CollectionUtils.isEmptyCollection(listaPermisos)) {
			String ids[] = new String[listaPermisos.size()];
			int i = 0;
			for (Iterator itListaPermiso = listaPermisos.iterator(); itListaPermiso
					.hasNext();) {
				PermisosListaVO permisosLista = (PermisosListaVO) itListaPermiso
						.next();
				ids[i++] = permisosLista.getIdDest();
			}
			return grupoDBEntity.getGrupos(ids);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionControlUsuariosBI#getOrganosEnListaAcceso(java.lang.
	 * String)
	 */
	public List getOrganosEnListaAcceso(String idLista) {
		List listaPermisos = permisosListaDbEntity
				.getPermisosXIdListaAccesoYTipo(idLista,
						TipoDestinatario.ORGANO);
		if (!util.CollectionUtils.isEmptyCollection(listaPermisos)) {
			String ids[] = new String[listaPermisos.size()];
			int i = 0;
			for (Iterator itListaPermiso = listaPermisos.iterator(); itListaPermiso
					.hasNext();) {
				PermisosListaVO permisosLista = (PermisosListaVO) itListaPermiso
						.next();
				ids[i++] = permisosLista.getIdDest();
			}
			return caOrganoDbEntity.getCAOrgProductorVOXId(ids);
		}
		return null;
	}

	public void agregarPermisosALista(String idLista, int tipoElementos,
			String[] idElementos, int[] permisos) {
		// construir permisos a insertar en la lista
		iniciarTransaccion();
		// borrar los permisos en la lista que existen para los elementos
		// pasados por parametro
		permisosListaDbEntity.deletePermisosLista(idLista, tipoElementos,
				idElementos);
		for (int i = 0; i < idElementos.length; i++) {
			for (int j = 0; j < permisos.length; j++) {
				int permiso = permisos[j];
				PermisosListaVO perm = new PermisosListaVO();
				perm.setIdDest(idElementos[i]);
				perm.setIdListCA(idLista);
				perm.setPerm(permiso);
				perm.setTipoDest(tipoElementos);
				permisosListaDbEntity.insertPermisosListaVO(perm);
			}
		}
		commit();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see common.bi.GestionControlUsuariosBI#getPermisosListaAcceso(int)
	 */
	public List getPermisosListaAcceso(int tipo) {
		List ret = new ArrayList();
		if (tipo == TipoListaControlAcceso.DESCRIPTOR) {
			// 604 Consulta de la descripción del descriptor
			// 605 Edición de descripción del descriptor
			// 900 Consulta de los documentos electrónicos
			// 901 Edición de los documentos electrónicos

			ret.add(new Integer(AppPermissions.CONSULTA_DESCRIPCION_DESCRIPTOR));
			ret.add(new Integer(AppPermissions.EDICION_DESCRIPTOR));
			ret.add(new Integer(
					AppPermissions.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION));
			ret.add(new Integer(
					AppPermissions.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION));
		} else if (tipo == TipoListaControlAcceso.ELEMENTO_CUADRO_CLASIFICACION) {
			// 500 Consulta del cuadro de clasificacion
			// 501 Edición del cuadro de clasificación
			// 600 Consulta de la descripción
			// 601 Edición de descripción
			// 900 Consulta de los documentos electrónicos
			// 901 Edición de los documentos electrónicos
			ret.add(new Integer(AppPermissions.CONSULTA_CUADRO_CLASIFICACION));
			ret.add(new Integer(
					AppPermissions.MODIFICACION_CUADRO_CLASIFICACION));
			ret.add(new Integer(
					AppPermissions.CONSULTA_DESCRIPCION_CUADRO_CLASIFICACION));
			ret.add(new Integer(
					AppPermissions.EDICION_DESCRIPCION_CUADRO_CLASIFICACION));
			ret.add(new Integer(
					AppPermissions.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION));
			ret.add(new Integer(
					AppPermissions.EDICION_DOCUMENTOS_CUADRO_CLASIFICACION));
			// ret.add(new
			// Integer(AppPermissions.CONSULTA_CUADRO_CLASIFICACION));
		} else if (tipo == TipoListaControlAcceso.FORMATO_FICHA) {
			// 1100 Visible
			ret.add(new Integer(AppPermissions.VISIBLE));
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionControlUsuariosBI#agregarUsuariosALista(java.lang.String
	 * , java.lang.String[], java.util.List)
	 */
	// public void agregarUsuariosALista(String listaID, String[] usuarios,
	// int[] permisos) throws UsuariosNotAllowedException {
	// agregarPermisosALista(listaID, TipoDestinatario.USUARIO, usuarios,
	// permisos);
	// }
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionControlUsuariosBI#agregarOrganosALista(java.lang.String,
	 * java.lang.String[], java.util.List)
	 */
	// public void agregarOrganosALista(String listaID, String[] organos, int[]
	// permisos) throws UsuariosNotAllowedException {
	// agregarPermisosALista(listaID, TipoDestinatario.ORGANO, organos,
	// permisos);
	// }
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionControlUsuariosBI#getOrganosXIdEnArchivo(java.lang.String
	 * )
	 */
	public CAOrganoVO getOrganosXIdEnArchivo(String idEnArchivo) {
		List organo = caOrganoDbEntity
				.getCAOrgProductorVOXId(new String[] { idEnArchivo });
		if (!util.CollectionUtils.isEmptyCollection(organo)) {
			return (CAOrganoVO) organo.iterator().next();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionControlUsuariosBI#eliminarElementosDeLista(java.lang
	 * .String, java.lang.String, java.lang.String[])
	 */
	public void eliminarElementosDeLista(String listaID, int tipoDestinatarios,
			String[] idDestinatarios) throws UsuariosNotAllowedException {
		permisosListaDbEntity.deletePermisosLista(listaID, tipoDestinatarios,
				idDestinatarios);
	}

	/**
	 * Comprobar y actualizar, si fuera necesario, el nombre largo del órgano
	 * del usuario.
	 *
	 * @param organoUsuario
	 *            Órgano del usuario en archivo.
	 * @param organoUsuarioExt
	 *            Órgano del usuario en el sistema externo.
	 * @param organosAntecesoresExt
	 *            Órganos antecesores del órgano del usuario en el sistema
	 *            externo.
	 */
	public void checkNombreLargoOrgano(CAOrganoVO organoUsuario,
			InfoOrgano organoUsuarioExt, List organosAntecesoresExt) {
		final String nombreLargo = NombreOrganoFormat.formatearNombreLargo(
				organoUsuarioExt, organosAntecesoresExt);
		if ((organoUsuario != null)
				&& (!StringUtils.equals(organoUsuario.getNombreLargo(),
						nombreLargo))) {
			if (logger.isDebugEnabled())
				logger.debug("Actualizando nombre largo del organo:"
						+ Constants.NEWLINE + organoUsuario.toString());
			organoUsuario.setNombreLargo(nombreLargo);
			// Actualizar la información del órgano
			iniciarTransaccion();
			caOrganoDbEntity.updateCAOrgVO(organoUsuario);
			commit();
		}
	}

	/**
	 * Comprueba la existencia de los órganos antecesores del órgano.
	 *
	 * @param organo
	 *            Informacion de organo {@link CAOrganoVO}.
	 * @throws GestorOrganismosException
	 *             En caso de que el órgano para el que se realza la
	 *             comprobación provenga de un sistema externo y ese sistema
	 *             externo no esté disponible
	 * @throws NotAvailableException
	 *             En caso de que el órgano para el que se realza la
	 *             comprobación provenga de un sistema externo y ese sistema
	 *             externo no disponga de la funcionalidad necesaria para
	 *             realizar la conprobación
	 */
	public void checkOrganosAntecesores(CAOrganoVO organo)
			throws GestorOrganismosException, NotAvailableException {
		iniciarTransaccion();

		// Obtener información de la entidad
		ServiceClient serviceClient = getServiceClient();

		// Obtener la entidad para el usuario conectado
		Properties params = null;

		if ((serviceClient != null)
				&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
			params = new Properties();
			params.put(MultiEntityConstants.ENTITY_PARAM,
					serviceClient.getEntity());
		}

		// Gestor Externo de Organismos
		GestorOrganismos go = GestorOrganismosFactory.getConnectorById(
				organo.getSistExtGestor(), params);

		// Obtener los organos antecesores
		final List orgAntExtList = go.recuperarOrganosAntecesores(
				organo.getIdOrgSExtGestor(), 0);

		if (orgAntExtList != null) {
			InfoOrgano orgAntExt;
			CAOrganoVO orgAntArch;
			for (int i = 0; i < orgAntExtList.size(); i++) {
				orgAntExt = (InfoOrgano) orgAntExtList.get(i);
				orgAntArch = getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
						organo.getSistExtGestor(), orgAntExt.getId(),
						Constants.TRUE_STRING);

				if (orgAntArch == null) {
					final int INDEX = i;

					// Crear el órgano
					orgAntArch = new CAOrganoVO();
					orgAntArch.setCodigo(orgAntExt.getCodigo());
					orgAntArch.setNombre(orgAntExt.getNombre());
					orgAntArch.setNombreLargo(NombreOrganoFormat
							.formatearNombreLargo(orgAntExt,
									(List) CollectionUtils.select(
											orgAntExtList, new Predicate() {
												public boolean evaluate(
														Object obj) {
													if (orgAntExtList
															.indexOf(obj) < INDEX)
														return true;
													else
														return false;
												}
											})));
					orgAntArch.setIdArchivoReceptor(organo
							.getIdArchivoReceptor());
					orgAntArch.setSistExtGestor(organo.getSistExtGestor());
					orgAntArch.setIdOrgSExtGestor(orgAntExt.getId());
					orgAntArch.setVigente(Constants.TRUE_STRING);

					caOrganoDbEntity.insertCAOrgVO(orgAntArch);
				}
			}
		}

		commit();
	}

	/**
	 * Incorpora al sistema un nuevo órgano procedente de un sistema externo
	 * gestor de estructura orgánica
	 *
	 * @param sistemaExterno
	 *            Identificador del sistema externo del que se importa el órgano
	 * @param infoOrgano
	 *            Datos del órgano a incorporar
	 * @param antecesores
	 *            Lista de antecesores del órgano en la jerarquía orgánica
	 * @param idArchivo
	 *            Identificador del archivo con el que se asocia el órgano
	 * @return Información de órgano
	 */
	public CAOrganoVO crearOrgano(String sistemaGestor, InfoOrgano infoOrgano,
			List antecesores, String idArchivo) {
		CAOrganoVO organo = new CAOrganoVO();
		organo.setCodigo(infoOrgano.getCodigo());
		organo.setIdArchivoReceptor(idArchivo);
		organo.setIdOrgSExtGestor(infoOrgano.getId());
		organo.setNombre(infoOrgano.getNombre());
		// GestorOrganismos gestorOrganismos =
		// GestorOrganismosFactory.getConnectorById(sistemaGestor);
		organo.setNombreLargo(NombreOrganoFormat.formatearNombreLargo(
				infoOrgano, antecesores));
		organo.setSistExtGestor(sistemaGestor);
		organo.setVigente(Constants.TRUE_STRING);
		iniciarTransaccion();
		caOrganoDbEntity.insertCAOrgVO(organo);
		commit();
		return organo;
	}

	/**
	 * Busqueda de usuarios por nombre y apellidos
	 *
	 * @param query
	 *            Cadena con el nombre y los apellidos de los usuarios
	 * @return Lista de usuarios {@link UsuarioVO}
	 */
	public List findByNameSurname(String name, String surname) {
		return usuarioDBEntity.findByNameSurname(name, surname);
	}

	/**
	 * Obtiene los identificadores de los usuarios del órgano tanto en el
	 * sistema externo como interno.
	 *
	 * @param idOrgano
	 *            Identificador del órgano.
	 * @return Lista de identificadores de usuarios.
	 */
	private List getUsuariosTotalesEnOrgano(String idOrgano) {
		List idsUsuarios = new ArrayList();

		// Obtener la información del órgano
		CAOrganoVO organo = getCAOrgProductorVOXId(idOrgano);
		if (organo != null) {
			try {
				// Obtener el Sistema Gestor de Organismos
				SistemaGestorOrganismos sgo = ConfiguracionSistemaArchivoFactory
						.getConfiguracionSistemaArchivo()
						.findSistemaGestorOrganismosById(
								organo.getSistExtGestor());

				// Si el SGO no es interno, añadir los usuarios del órgano
				// en el sistema externo

				// Obtener información de la entidad
				ServiceClient serviceClient = getServiceClient();

				// Obtener la entidad para el usuario conectado
				Properties params = null;

				if ((serviceClient != null)
						&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
					params = new Properties();
					params.put(MultiEntityConstants.ENTITY_PARAM,
							serviceClient.getEntity());
				}

				if (!sgo.isInterno()) {
					GestorOrganismos go = GestorOrganismosFactory
							.getConnectorById(organo.getSistExtGestor(), params);
					idsUsuarios.addAll(go
							.recuperarUsuariosDeOrganos(util.CollectionUtils
									.createList(new String[] { organo
											.getIdOrgSExtGestor() })));
				}

				// Añade los usuarios del órgano en el sistema interno
				GestorOrganismos go = GestorOrganismosFactory.getConnectorById(
						ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo()
								.getSistemaInternoGestorOrganismos().getId(),
						params);
				idsUsuarios.addAll(go
						.recuperarUsuariosDeOrganos(util.CollectionUtils
								.createList(new String[] { idOrgano })));
			} catch (Exception e) {
				throw new ArchivoModelException(e, this.getClass().getName(),
						"Error al obtener los usuarios de un órgano con unos permisos determinados");
			}
		}

		return idsUsuarios;
	}

	/**
	 * Obtiene los identificadores de los usuarios del órgano tanto en el
	 * sistema externo como interno.
	 *
	 * @param idOrgano
	 *            Identificador del órgano.
	 * @return Lista de identificadores de usuarios.
	 */
	private List getUsuariosVigentesEnOrgano(String idOrgano) {
		List usuarios = new ArrayList();

		usuarios = usuarioDBEntity
				.getUsuariosVigentesAsociadosAOrgano(idOrgano);

		return usuarios;
	}

	/**
	 * Obtiene los usuarios pertenecientes a un órgano que tengan los permisos
	 * especificados.
	 *
	 * @param idOrgano
	 *            Identificador del órgano de los usuarios.
	 * @param permisos
	 *            Lista de permisos.
	 * @return Lista de usuarios ({@link UsuarioVO}).
	 */
	public List getUsuarios(String idOrgano, String[] permisos) {
		List usuarios = new ArrayList();

		List idsUsuariosOrgano = getUsuariosTotalesEnOrgano(idOrgano);
		if (!util.CollectionUtils.isEmpty(idsUsuariosOrgano))
			usuarios.addAll(usuarioDBEntity.getUsuariosXIdsEnSistOrg(
					(String[]) idsUsuariosOrgano
							.toArray(new String[idsUsuariosOrgano.size()]),
					permisos));

		return usuarios;
	}

	/**
	 * Busca los usuarios que verifican los criterios que se indican
	 *
	 * @param tipoUsuario
	 *            Tipo de usuario. Puede ser nulo.
	 * @param searchTokenNombre
	 *            . Texto que debe estar contenido en el nombre del usuario.
	 *            Puede ser nulo
	 * @param searchTokenApellidos
	 *            . Texto que debe estar contenido en los apellidos del usuario.
	 *            Puede ser nulo
	 * @return Lista de usurios dados de alta en el sistema que verifican los
	 *         criterios indicados {@link UsuarioVO}
	 */
	public List findUsuarios(String tipoUsuario, String searchTokenNombre,
			String searchTokenApellidos) {
		return usuarioDBEntity.findUsuarios(tipoUsuario, searchTokenNombre,
				searchTokenApellidos);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see common.bi.GestionControlUsuariosBI#getCapturadores()
	 */
	public List getCapturadores() {
		final String[] permisos = { AppPermissions.CAPTURA_DOCUMENTOS,
				AppPermissions.ADMINISTRACION_TOTAL_SISTEMA };

		return usuarioDBEntity.getUsuariosWithPermissions(permisos);
	}

	public List getPermisosListaAcceso(String idListaAcceso,
			List destinatariosPermisosLista) {
		return permisosListaDbEntity.getPermisosListaAcceso(idListaAcceso,
				destinatariosPermisosLista);
	}

	/**
	 * Obtiene los usuarios que tienen todos los permisos de la lista pasada
	 * como parámetro
	 *
	 * @param permisos
	 *            Conjunto de permisos
	 * @return Colleccion de usuario {@link UsuarioVO}
	 */
	public List getUsuariosConTodosPermisosYArchivo(String[] permisos,
			String idArchivo) {

		List usuarios = new ArrayList();

		if (ArrayUtils.isNotEmpty(permisos)) {
			for (int i = 0; i < permisos.length; i++) {
				List usuariosAux = usuarioDBEntity
						.getUsuariosWithPermissionsAndArchive(
								new String[] { permisos[i] },
								new String[] { idArchivo });

				if (i == 0 && usuariosAux != null && usuariosAux.size() > 0)
					usuarios.addAll(usuariosAux);

				usuarios = ListUtils.intersection(usuarios, usuariosAux);
			}
		}

		return usuarios;
	}

	public void vincularOrganizacion(CAOrganoVO organo, InfoOrgano organizacion)
			throws GestorOrganismosException, NotAvailableException,
			ActionNotAllowedException {
		iniciarTransaccion();

		/*
		 * ConfiguracionSistemaArchivo config =
		 * ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo();
		 *
		 * //Comprobar si existe un descriptor de tipo órgano
		 *
		 * String listaDescriptoresOrgano = config.getConfiguracionGeneral()
		 * .getListaDescriptoresEntidad(
		 * TipoProductor.BDORGANIZACION.getIdentificador()) .getId();
		 *
		 *
		 * DescriptorVO descriptor =
		 * descriptorDBEntity.getDescriptorXIdEnSistemaExterno
		 * (organo.getIdOrgSExtGestor(), listaDescriptoresOrgano);
		 *
		 * if(descriptor != null){
		 * descriptor.setIdDescrSistExt(organizacion.getId());
		 * descriptorDBEntity.updateDescriptorVO(descriptor); }
		 *
		 * //Comprobar si existe un descriptor de tipo institución
		 * listaDescriptoresOrgano = config.getConfiguracionGeneral()
		 * .getListaDescriptoresEntidad(
		 * TipoProductor.INSTITUCION.getIdentificador()) .getId();
		 *
		 *
		 * descriptor =
		 * descriptorDBEntity.getDescriptorXIdEnSistemaExterno(organo
		 * .getIdOrgSExtGestor(), listaDescriptoresOrgano);
		 *
		 * if(descriptor != null){
		 * descriptor.setIdDescrSistExt(organizacion.getId());
		 * descriptorDBEntity.updateDescriptorVO(descriptor); }
		 */
		organo.setIdOrgSExtGestor(organizacion.getId());
		saveOrgano(organo);

		commit();
	}

	public List<UsuarioVO> getUsuariosVigentesAsociadosAOrgano(String idOrgano) {
		return usuarioDBEntity.getUsuariosVigentesAsociadosAOrgano(idOrgano);
	}

}