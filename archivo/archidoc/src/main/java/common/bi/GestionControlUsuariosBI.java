package common.bi;

import java.util.Date;
import java.util.List;

import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.InfoOrgano;
import se.instituciones.exceptions.GestorOrganismosException;
import se.usuarios.AppPermissions;
import xml.config.Usuario;

import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;

import fondos.vos.EntidadProductoraVO;
import gcontrol.exceptions.UsuariosNotAllowedException;
import gcontrol.model.TipoListaControlAcceso;
import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.DestinatarioPermisosListaVO;
import gcontrol.vos.GrupoVO;
import gcontrol.vos.ListaAccesoVO;
import gcontrol.vos.PermisosListaVO;
import gcontrol.vos.RolVO;
import gcontrol.vos.UsuarioExtendedVO;
import gcontrol.vos.UsuarioOrganoVO;
import gcontrol.vos.UsuarioVO;

public interface GestionControlUsuariosBI {

	/**
	 * Inserta el productor en el sistema
	 * @param productor
	 * @param entidadVO
	 * @param idArchivoReceptor
	 * @param gestorOrganismos
	 * @throws GestorOrganismosException
	 * @throws NotAvailableException
	 */
	public void insertProductorCatalogoEnSistema(InfoOrgano productor,
			EntidadProductoraVO entidadVO, String idArchivoReceptor, GestorOrganismos gestorOrganismos) throws GestorOrganismosException, NotAvailableException;

	public List getOrganosVigentesEnSistemaDependientesEntidad(
			EntidadProductoraVO entidadProductoraVO);

	/**
	 * Obtiene la información de un usuario en archivo.
	 *
	 * @param idUsuario
	 *            Identificador del usuario en la aplicación.
	 * @return Información de un usuario de archivo.
	 */
	public UsuarioVO getUsuario(String idUsuario);

	/**
	 * Obtiene la información de un superusuario en archivo.
	 *
	 * @param nombreUsuario
	 *            Nombre del usuario que se busca
	 * @return Información de un superusuario de archivo.
	 */
	public UsuarioVO getSuperusuario(String nombreUsuario);

	/**
	 * Obtiene la información referente a un conjunto de usuarios en archivo.
	 *
	 * @param idUsuario
	 *            Identificadores de usuarios en la aplicación.
	 * @return Lista con la información de cada usuario de archivo.
	 */
	public List getUsuarios(String[] idUsuario);

	/**
	 * Obtiene la información de un usuario en archivo.
	 *
	 * @param userType
	 *            Tipo de usuario.
	 * @param idUsrExtGestor
	 *            Identificador del usuario en el sistema gestor.
	 * @return Información de un usuario de archivo.
	 */
	public UsuarioVO getUsuario(String userType, String idUsrExtGestor);

	/**
	 * Obtiene los roles de un usuario.
	 *
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Roles del usuario.
	 */
	public List getRolesUsuario(String idUsuario);

	/**
	 * Obtiene los permisos de un rol.
	 *
	 * @param idRol
	 *            Identificador del rol.
	 * @return Permisos del rol @link gcontrol.vos.PermisoVO.
	 */
	public List getPermisosRol(String idRol);

	/**
	 * Obtiene los grupos del usuario.
	 *
	 * @param idUsuario
	 *            Identificador del usuario.
	 * @return Grupos del usuario.
	 */
	public List getGruposUsuario(String idUsuario);

	/**
	 * Obtiene la lista de usuarios activos.
	 *
	 * @return Lista de usuarios activos.
	 */
	public List getUsuariosActivos();

	/**
	 * Obtiene los usuarios dados de alta en el sistema
	 *
	 * @param pageInfo
	 *            Información de paginación.
	 * @return Lista de usuarios {@link UsuarioVO}
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getUsuarios(PageInfo pageInfo) throws TooManyResultsException;

	/**
	 * Obtiene un listado de los usuarios registrados en archivo que pueden
	 * solicitar un préstamo
	 *
	 * @return Listado de usuarios (@link UsuarioExtendedVO)
	 */
	public List getUsuariosExtRegistradosSolitantes();

	/**
	 * Obtiene un listado de los usuarios registrados en archivo que pueden
	 * solicitar un préstamo filtrados por el nombre de solicitante pasado como
	 * parámetro.
	 *
	 * @return Listado de usuarios (@link UsuarioVO)
	 */
	public List getUsuariosRegistradosSolitantes(String filtro);

	/**
	 * Obtiene las listas de acceso para un grupo de destinatarios.
	 *
	 * @param destinatariosPermisosLista
	 *            Lista de destinatarios ({@link DestinatarioPermisosListaVO}).
	 * @return Listas de acceso ({@link PermisosListaVO});
	 */
	public List getListasAcceso(List destinatariosPermisosLista);

	/**
	 * Obtiene las listas de control de acceso.
	 *
	 * @return Listas de control de acceso.
	 */
	public List getListasControlAcceso();

	List findListasAcceso(String nombre);

	List findListasAccesoByNombreYTipos(String nombre, int[] tipos);

	/**
	 * Obtiene las listas de control de acceso por tipo.
	 *
	 * @param tipo
	 *            Tipo de la lista de control de acceso (
	 *            {@link TipoListaControlAcceso}).
	 * @return Listas de control de acceso.
	 */
	public List getListasControlAccesoByTipo(int tipo);

	ListaAccesoVO getListaControlAccesoByNombre(String nombre);

	/**
	 * Obtiene la lista de usuarios que se encuentran en una lista de acceso
	 *
	 * @param idLista
	 * @return usuarios @link UsuarioVO
	 */
	public List getUsuariosEnListaAcceso(String idLista);

	/**
	 * Obtiene la lista de grupos que se encuentran en una lista de acceso
	 *
	 * @param idLista
	 * @return usuarios @link GrupoVO
	 */
	public List getGruposEnListaAcceso(String idLista);

	/**
	 * Obtiene la lista de grupos que se encuentran en una lista de acceso
	 *
	 * @param idLista
	 * @return usuarios @link GrupoVO
	 */
	public List getOrganosEnListaAcceso(String idLista);

	/**
	 * Obtiene la lista de órganos existentes en el sistema.
	 *
	 * @return Lista de órganos {@link CAOrganoVO}
	 */
	public List getOrganos(Boolean vigente);

	/**
	 * Obtiene la lista de órganos productores.
	 *
	 * @param vigente
	 *            Indica si los órganos deben ser vigentes o no vigentes. Si es
	 *            nulo, se devolverán todos los órganos productores.
	 * @return Lista de órganos productores.
	 */
	public List getCAOrgProductoresVOList(Boolean vigente);

	/**
	 * Permite incorporar un nuevo organo al sistema.
	 *
	 * @param organo
	 *            Informacion de organo {@link CAOrganoVO}.
	 */
	public void saveOrgano(CAOrganoVO organo) throws ActionNotAllowedException,
			GestorOrganismosException, NotAvailableException;

	/**
	 * Obtiene la información de un órgano.
	 *
	 * @param idOrgano
	 *            Identificador del órgano.
	 * @return Información de un órgano.
	 */
	public CAOrganoVO getCAOrgProductorVOXId(String idOrgano);

	/**
	 * Obtiene la información de un conjunto de órganos.
	 *
	 * @param idOrgano
	 *            Lista de dentificadores de órgano.
	 * @return Lista de organos {@link CAOrganoVO}.
	 */
	public List getCAOrgProductorVOXId(String[] idOrgano);

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
			String sistExtGestor, String idEnSistExt, String vigencia);

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
			String sistExtGestor, Object idsEnSistExt);

	// /**
	// * Obtiene los roles de un usuario.
	// * @param idUsuario Identificador del usuario.
	// * @return Roles del usuario.
	// */
	// public CAOrganoVO getOrganoUsuarioValido(String idUsuario);

	/**
	 * Obtiene la lista de identificadores de usuarios de órganos.
	 *
	 * @param idOrgs
	 *            Lista de identificadores de órganos.
	 * @return Lista de identificadores de usuarios.
	 */
	public List getUsuariosOrganos(List idOrgs);

	/**
	 * Obtiene el órgano de un usuario dado de alta en Archivo. mirar
	 * getOrganoUsuarioEnArchivo(String idUsuario)
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
			String tipoUsuario, String idUsuarioOrg);

	/**
	 * Obtiene el órgano de un usuario dado de alta en Archivo.
	 *
	 * @param idUsuario
	 * @return
	 */
	public CAOrganoVO getOrganoUsuarioEnArchivo(String idUsuario);

	/**
	 * Obtiene los archivos de custodia asociados a un usuario.
	 *
	 * @param idUsuario
	 *            Identificador del usuario en Archivo.
	 * @return Lista con informacion de los archivos de custodia
	 *         {@link gcontrol.vos.ArchivoVO}.
	 */
	public List getArchivosCustodia(String idUsaurio);

	/**
	 * Obtiene los usuarios activos de la aplicacion que tienen alguno de los
	 * permisos indicados
	 *
	 * @param permisosBuscados
	 *            Listado de los permisos que al menos uno debe contener el
	 *            usuario
	 * @return Listado de usuarios activos en la aplicación con alguno de los
	 *         permisos
	 */
	public List getUsuariosExtConPermisos(String[] permisosBuscados);

	/**
	 * Devuelve la lista de usuarios que pueden gestionar transferencias
	 * destinadas a un determinado archivo
	 *
	 * @param idArchivo
	 *            Identificador de archivo
	 * @return Lista de usuarios {@link gcontrol.vos.UsuarioVO}
	 */
	public List getGestoresTransferencia(String idArchivo);

	/**
	 *
	 * @return Lista de usuario a los que se les puede asignar tareas de captura
	 */
	public List getCapturadores();

	/**
	 * Devuelve la lista de usuarios que pueden gestionar series
	 *
	 * @return Lista de usuarios {@link gcontrol.vos.UsuarioVO}
	 */
	public List getGestoresSerie();

	/**
	 * Obtiene los usuarios que tienen unos determinados permisos
	 *
	 * @param permisos
	 *            Conjunto de permisos
	 * @return Colleccion de usuario {@link UsuarioVO}
	 */
	public List getUsuariosConPermisos(String[] permisos);

	/**
	 * Obtiene los usuarios que tienen unos determinados permisos y por un
	 * determinado filtro para el nombre y los apellidos del usuario
	 *
	 * @param permisos
	 *            Conjunto de permisos
	 * @param filtro
	 *            filtro a aplicar en la busqueda
	 * @return
	 */
	public List getUsuariosConPermisos(String[] permisos, String filtro);

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
			String[] idsArchivo);

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
			String[] idsArchivo);

	/**
	 * Elimina un organo del sistema
	 *
	 * @param idOrgano
	 *            Identificador del organo a eliminar
	 */
	public void eliminarOrgano(String idOrgano)
			throws ActionNotAllowedException;

	/**
	 * Obtiene los diferentes tipos de usuario que soporta el sistema
	 *
	 * @return Lista de los diferentes tipos de usuario que interactuan con el
	 *         sistema {@link se.usuarios.TipoUsuario}
	 */
	public List getTiposUsuarioConSistemaOrganizacion();

	/**
	 * Obtiene los permisos de los que dispone un usuario
	 *
	 * @param idUsuario
	 *            Identificador de usuario del sistema
	 * @return Lista de permisos {@link gcontrol.vos.PermisoVO}
	 */
	public List getPermisosUsuario(String idUsuario);

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
	public boolean userHasPermission(String idUsuario, String permiso);

	/**
	 * Obtiene los grupos que estan asociados a un determinado archivo
	 *
	 * @param idArchivo
	 *            Identiicador del archivo al que estan asociados los grupos
	 * @return
	 */
	public abstract List getGruposArchivo(String idArchivo);

	/**
	 * Obtiene los grupos existentes
	 *
	 * @return Listado de grupos existentes
	 */
	public abstract List getGrupos();

	/**
	 *
	 * @param idUsrGestor
	 * @return TRUE si el usuario es gestor de serie , FALSE en caso contrario
	 */
	// public boolean isGestorSerie(String idUsrGestor);

	/**
	 * Obtiene un grupo a partir de su identificador
	 *
	 * @param id
	 *            Identificador del grupo
	 * @return Grupo asociado al id
	 */
	public GrupoVO getGrupo(String id);

	/**
	 * Insercion de un usuarios en con sistema interno de organizacion
	 *
	 * @param user
	 */
	public void insertUsuarioConSistOrgInterno(UsuarioVO user,
			String idOrganoEnArchivo, Date fechaIni, Date fechaFin)
			throws UsuariosNotAllowedException;

	/**
	 *
	 * @param user
	 * @param idSistGestor
	 * @param idOrganoEnSistGestor
	 * @throws UsuariosNotAllowedException
	 */
	public void insertUsuario(UsuarioVO user, String idSistGestor,
			String idOrganoEnSistGestor) throws UsuariosNotAllowedException;

	/**
	 * Crea un usuario externo.
	 *
	 * @param user
	 *            Información del usuario.
	 * @throws UsuariosNotAllowedException
	 *             si ocurre algún error.
	 */
	public UsuarioVO crearUsuarioExterno(UsuarioVO user)
			throws UsuariosNotAllowedException;

	/**
	 * Permite crear un usuario de administración
	 *
	 * @param username
	 *            Nombre del usuario
	 * @return UsuarioVO
	 */
	public UsuarioVO crearUsuarioAdministracion(String username);

	/**
	 * Actualizacion del usuario y la relacion con su organo
	 *
	 * @param user
	 * @throws UsuariosNotAllowedException
	 */
	public void updateUsuario(UsuarioVO user)
			throws UsuariosNotAllowedException;

	/**
	 * Actualziacion de un usuario con sist de organizacion interno
	 *
	 * @param user
	 * @param idOrganoEnSistInterno
	 * @param fechaIni
	 * @param fechaFin
	 * @throws UsuariosNotAllowedException
	 */
	public void updateUsuarioConSistOrgInterno(UsuarioVO user,
			String idOrganoEnSistInterno, Date fechaIni, Date fechaFin)
			throws UsuariosNotAllowedException;

	/**
	 * Obtiene la lista de roles asociados a alguno de los modulos indicados
	 *
	 * @param modules
	 *            Lista de identificadores de modulo
	 * @return Lista de roles {@link gcontrol.vos.RolVO}
	 */
	public List getRoles(int[] modules);

	/**
	 * Obtiene la informacion de un rol de usuario
	 *
	 * @param idRol
	 *            Identificador de rol
	 * @return Datos de rol {@link RolVO}
	 */
	public RolVO getRol(String idRol);

	/**
	 * Pone el rol solicitado a disposicion del usuario que invoca el metodo de
	 * manera que unicamente dicho usuario puede llevar a cabo acciones sobre el
	 * rol
	 *
	 * @param idRol
	 *            Identificador de rol
	 * @return Datos de rol {@link RolVO}
	 */
	public RolVO abrirRol(String idRol);

	/**
	 * Almacena la informacion referente a un rol
	 *
	 * @param rol
	 *            Datos del rol
	 * @param permisoRol
	 *            Lista de permisos para el rol
	 */
	public void guardarRol(RolVO rol, String[] permisoRol)
			throws ActionNotAllowedException;

	/**
	 * Quita un conjunto de permisos de un rol
	 *
	 * @param idRol
	 *            Identificador de rol
	 * @param permisoRol
	 *            Lista de permisos a eliminar
	 */
	public void quitarPermisosRol(String idRol, String[] permisoRol);

	/**
	 * Asocia un conjunto de permisos a un rol
	 *
	 * @param idRol
	 *            Identificador de rol
	 * @param permisoRol
	 *            Lista de permisos
	 */
	public void agregarPermisosRol(String idRol, String[] permisoRol);

	/**
	 * Elimina del sistema los roles indicados
	 *
	 * @param roles
	 *            Lista de identificadores de rol a eliminar
	 */
	public void eliminarRol(String[] roles) throws ActionNotAllowedException;

	/**
	 * Obtiene los usuarios que tienen asociado un determinado rol
	 *
	 * @param idRol
	 *            Identificador de rol
	 * @return Lista de usuarios {@link UsuarioVO}
	 */
	public List getUsuariosConRol(String idRol);

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
			throws ActionNotAllowedException;

	/**
	 * Añade roles a la lista de roles asociados a una serie de usuarios
	 *
	 * @param roles
	 *            Roles a añadir {@link RolVO}
	 * @param usuarios
	 *            Identificadores de los usurios a los que se les añade el rol
	 */
	public void agregarRolesUsuario(List roles, String[] usuarios)
			throws ActionNotAllowedException;

	/**
	 * Establece los roles de un usuario
	 *
	 * @param roles
	 *            Lista de roles {@link RolVO}
	 * @param idUsuario
	 *            Identificador de usuario
	 */
	public void setRolesUsuario(List roles, String idUsuario)
			throws ActionNotAllowedException;

	/**
	 * Busqueda de usuarios por nombre
	 *
	 * @param query
	 *            Cadena que debe estar contenida en el nombre o apellidos de
	 *            los usuarios
	 * @return Lista de usuarios {@link UsuarioVO}
	 */
	public List findUsersByName(String query);

	/**
	 * Obtiene los órganos por código y/o nombre.
	 *
	 * @param codigo
	 *            Código del órgano.
	 * @param nombre
	 *            Nombre del órgano.
	 * @return Lista de órganos {@link CAOrganoVO}.
	 */
	public List findOrganos(String codigo, String nombre, String vigente);

	/**
	 * Obtiene los órganos cuyo nombre contiene la cadena suministrada
	 *
	 * @param query
	 *            Patrón de búsqueda a localizar en el nombre del órgano
	 * @return Lista de órganos cuyo nombre contiene el patrón indicado
	 *         {@link CAOrganoVO}
	 */
	public List findOrganosByName(String query);

	public List findGruposByName(String query);

	/**
	 * Obtiene un list de organos ascendentes del organo idORgano en el sistema
	 * de organizacion isSistemaOrganizacion
	 *
	 * @param idSistemaOrganizacion
	 * @param idOrganoEnSistOrganizacion
	 * @return
	 */
	public List getOrganizationInfoInArchivo(String idSistemaOrganizacion,
			String idOrganoEnSistOrganizacion)
			throws GestorOrganismosException, NotAvailableException;

	/**
	 * Obtiene un string con la concatenacion de organos ascendentes del organo
	 * idORgano en el sistema de organizacion isSistemaOrganizacion
	 *
	 * @param idSistemaOrganizacion
	 * @param idOrgano
	 * @return
	 */
	public String getOrganizationInfoString(String idSistemaOrganizacion,
			String idOrgano);

	public boolean isUsuarioDeSistemaOrganizacionInterno(String tipoUsuario);

	public boolean isUsuarioSinSistemaOrganizacion(String tipoUsuario);

	/**
	 * Lista de @link CAOrganoVO q tengan el campo idSistExtGestor =
	 * idSistExtGestor
	 *
	 * @param idSisExtGestor
	 * @return
	 */
	public List getOrganosXIdSistExtGestor(String idSisExtGestor);

	/**
	 * Obtien la configuracion de un tipo de usuario
	 *
	 * @param tipoUsuario
	 * @return
	 */
	public Usuario getConfigTipoUsuarioByIdTipo(String tipoUsuario);

	public UsuarioOrganoVO getInfoUsuarioEnOrgano(String idUsuarioEnArchivo);

	/**
	 * Incorpora al sistema un grupo de usuarios
	 *
	 * @param grupo
	 *            Datos de grupo de usuarios
	 */
	public void guardarGrupo(GrupoVO grupo) throws ActionNotAllowedException;

	/**
	 * Elimina del sistema los grupos indicados
	 *
	 * @param grupos
	 *            Lista de identificadores de grupo
	 */
	public void eliminarGrupo(String[] grupos) throws ActionNotAllowedException;

	/**
	 * Comprueba si el nombre del Grupo está duplicado y cuyo id de grupo no sea
	 * el que se la pasa pro parámetro.
	 *
	 * @param idgrupo
	 *            Id del grupo a excluir
	 * @param nombre
	 *            Nombre del grupo a comprobar.
	 * @return true si el nombre del grupo está duplicado.
	 */
	public boolean isNombreGrupoDuplicado(String idgrupo, String nombre);

	/**
	 * Obtiene la lista de usuarios que pertenecen a un grupo
	 *
	 * @param idGrupo
	 *            Identificador de grupo
	 * @return Lista de usuarios {@link UsuarioVO}
	 */
	public List getUsuariosEnGrupo(String idGrupo);

	/**
	 * Elimina un conjunto de usuarios de los grupos indicados
	 *
	 * @param idGrupo
	 *            Identificadores de grupo
	 * @param idUsuario
	 *            Identificadores de usuario
	 */
	public void quitarUsuariosDeGrupo(String[] idGrupo, String[] idUsuario)
			throws ActionNotAllowedException;

	/**
	 * Incorpora usuarios a grupos
	 *
	 * @param idGrupo
	 *            Identificadores de grupo
	 * @param idUsuario
	 *            Identificadores de usuario
	 */
	public void agregarUsuariosAGrupo(String[] idGrupo, String[] idUsuario)
			throws ActionNotAllowedException;

	/**
	 * Obtiene la informacion de una lista de acceso
	 *
	 * @param idListaAcceso
	 *            Identificador de lista de acceso
	 * @return Datos de lista de acceso {@link ListaAccesoVO}
	 */
	public ListaAccesoVO getListaAcceso(String idListaAcceso);

	/**
	 * Guarda en el sistema una lista de acceso
	 *
	 * @param listaAcceso
	 *            Datos de lista de acceso
	 */
	public void guardarListaAcceso(ListaAccesoVO listaAcceso)
			throws ActionNotAllowedException;

	/**
	 * Permite eliminar los órganos de un usuario
	 *
	 * @param idUsuario
	 *            Id del usuario
	 */
	public void eliminarOrganosUsuario(String idUsuario);

	/**
	 * Elimina el conjunto de usuarios indicado del sistema
	 *
	 * @param idsUsuarios
	 *            Conjunto de identificadores de usuario
	 * @throws ActionNotAllowedException
	 *             Caso de que la eliminación de alguno de los usuarios no esté
	 *             permitida
	 */
	public void eliminarUsuarios(String[] idsUsuarios)
			throws ActionNotAllowedException;

	/**
	 * Elimina un conjunto de listas de acceso del sistema
	 *
	 * @param listasAcceso
	 *            Conjunto de identificadores de lista de acceso
	 */
	public void eliminarListasAcceso(String[] listasAcceso)
			throws UsuariosNotAllowedException;

	/**
	 * Elimina un conjunto de listas de acceso del sistema
	 *
	 * @param idLCA
	 *            Identificador de lista de acceso
	 */
	public void eliminarListaAccesoNoTransaccional(String idLCA);

	/**
	 * Obtiene la información del usuario mas la informacion de su organo y
	 * archivo.
	 *
	 * @param idUsuario
	 *            Identificador del usuario en la aplicacion
	 * @return Informacion del usuario
	 */
	public UsuarioExtendedVO getUsuarioExtendido(String idUsuario);

	/**
	 * Incorpora un conjunto de permisos a una lista de control de acceso
	 *
	 * @param idLista
	 *            Identificador de la lista de contol de acceso a la que se
	 *            incorporan los permisos
	 * @param tipoElementos
	 *            Tipo de destinatorio para el que se conceden los permisos que
	 *            se indican {@link gcontrol.model.TipoDestinatario}
	 * @param idElementos
	 *            Identificadores de los destinatarios integrantes de la lista
	 *            de control de acceso a los que se conceden los permisos
	 *            indicados
	 * @param permisos
	 *            Conjunto de permisos
	 * @throws UsuariosNotAllowedException
	 *             Caso de que la incorporación de permisos a la lista de
	 *             control de acceso no esté permitida
	 */
	public void agregarPermisosALista(String idLista, int tipoElementos,
			String[] idElementos, int[] permisos)
			throws UsuariosNotAllowedException;

	/**
	 * Elimina un conjunto de integrantes de una lista de control de acceso
	 *
	 * @param listaID
	 *            Identificador de lista de control de acceso
	 * @param tipoElementos
	 *            Tipo de los integrantes de la lista de control de acceso a
	 *            eliminar
	 * @param idDestinatarios
	 *            Conjunto de identificadores de integrantes de lista de control
	 *            de acceso
	 * @throws UsuariosNotAllowedException
	 *             Caso de que no esté permitida la eliminación de alguno de los
	 *             integrantes de la lista de control de acceso indicados
	 */
	public void eliminarElementosDeLista(String listaID, int tipoElementos,
			String[] idDestinatarios) throws UsuariosNotAllowedException;

	/**
	 * Obtiene el conjunto de permisos que pueden ser asignados a los
	 * integrantes de una lista de control de acceso
	 *
	 * @param tipo
	 *            Tipo de lista de control de acceso
	 *            {@link TipoListaControlAcceso}
	 * @return lista de Integer con los permisos que puede tener una lista de
	 *         control de acceso del tipo
	 */
	public List getPermisosListaAcceso(int tipo);

	public CAOrganoVO getOrganosXIdEnArchivo(String idEnArchivo);

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
			InfoOrgano organoUsuarioExt, List organosAntecesoresExt);

	/**
	 * Comprueba la existencia de los órganos antecesores del órgano.
	 *
	 * @param organo
	 *            Informacion de organo {@link CAOrganoVO}.
	 */
	public void checkOrganosAntecesores(CAOrganoVO organo)
			throws GestorOrganismosException, NotAvailableException;

	/**
	 * Busqueda de usuarios por nombre y apellidos
	 *
	 * @param query
	 *            Cadena con el nombre y los apellidos de los usuarios
	 * @return Lista de usuarios {@link UsuarioVO}
	 */
	// public List findByNameSurname(String nusrconsultor);
	public List findByNameSurname(String name, String surname);

	/**
	 * Incorpora al sistema un nuevo órgano procedente de un sistema externo
	 * gestor de estructura orgánica
	 *
	 * @param sistemaExterno
	 *            Identificador del sistema externo del que se importa el órgano
	 * @param organo
	 *            Datos del órgano a incorporar
	 * @param antecesores
	 *            Lista de antecesores del órgano en la jerarquía orgánica
	 * @param idArchivo
	 *            Identificador del archivo con el que se asocia el órgano
	 * @return Información de órgano
	 */
	public CAOrganoVO crearOrgano(String sistemaGestor, InfoOrgano organo,
			List antecesores, String idArchivo);

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
	public List getUsuarios(String idOrgano, String[] permisos);

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
			String searchTokenApellidos);

	List getPermisosListaAcceso(String idListaAcceso,
			List destinatariosPermisosLista);

	/**
	 * Obtiene los usuarios que tienen todos los permisos de la lista pasada
	 * como parámetro y que pertenecen al archivo pasado como parámetro
	 *
	 * @param permisos
	 *            Conjunto de permisos
	 * @param idArchivo
	 *            Identificador del Archivo
	 * @return Colleccion de usuario {@link UsuarioVO}
	 */
	public List getUsuariosConTodosPermisosYArchivo(String[] permisos,
			String idArchivo);

	/**
	 * Comprueba si se puede eliminar una lista de Acceso.
	 *
	 * @param lista
	 *            Lista de Acceso
	 * @throws UsuariosNotAllowedException
	 */
	public void checkRestriccionesBorrado(ListaAccesoVO listaAcceso)
			throws UsuariosNotAllowedException;

	/**
	 * Comprueba si se una lista de Acceso no tiene elementos
	 *
	 * @param idLista
	 *            Identificador de la lista de Acceso.
	 * @return
	 */
	public boolean isListaAccesoSinElementos(String idLista);

	/**
	 * Vincula el órgano de archivo con un órgano del sistema de organización,
	 * cuyo código coincida.
	 *
	 * @param organo
	 *            Organo actual
	 * @param organoExterno
	 *            Órgano nuevo a vincular
	 * @throws ActionNotAllowedException
	 * @throws NotAvailableException
	 * @throws GestorOrganismosException
	 */
	public void vincularOrganizacion(CAOrganoVO organo, InfoOrgano organoExterno)
			throws GestorOrganismosException, NotAvailableException,
			ActionNotAllowedException;

	public List<UsuarioVO> getUsuariosVigentesAsociadosAOrgano(String idOrgano);
}