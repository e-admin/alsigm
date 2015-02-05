package es.ieci.tecdoc.isicres.admin.service;

import es.ieci.tecdoc.isicres.admin.beans.Campos;
import es.ieci.tecdoc.isicres.admin.beans.Contadores;
import es.ieci.tecdoc.isicres.admin.beans.Distribuciones;
import es.ieci.tecdoc.isicres.admin.beans.DocumentoTipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Filtros;
import es.ieci.tecdoc.isicres.admin.beans.InformeBean;
import es.ieci.tecdoc.isicres.admin.beans.InformesBean;
import es.ieci.tecdoc.isicres.admin.beans.LibroBean;
import es.ieci.tecdoc.isicres.admin.beans.Libros;
import es.ieci.tecdoc.isicres.admin.beans.Oficina;
import es.ieci.tecdoc.isicres.admin.beans.OficinaBean;
import es.ieci.tecdoc.isicres.admin.beans.OficinaTipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.Oficinas;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.beans.OrganizacionBean;
import es.ieci.tecdoc.isicres.admin.beans.Organizaciones;
import es.ieci.tecdoc.isicres.admin.beans.PermisosSicres;
import es.ieci.tecdoc.isicres.admin.beans.TipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.TiposAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.Transporte;
import es.ieci.tecdoc.isicres.admin.beans.Transportes;
import es.ieci.tecdoc.isicres.admin.beans.UsuarioRegistradorBean;
import es.ieci.tecdoc.isicres.admin.beans.UsuariosRegistradores;
import es.ieci.tecdoc.isicres.admin.exception.ISicresAdminBeansException;


/**
 * Interfaz de Métodos disponibles para la Administración de Registro
 *
 */
public interface ISicresAdminService {

	/**
	 * Método que obtiene todos los usuario de la aplicacion de Registro
	 *
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public UsuariosRegistradores obtenerUsuarios(Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene todos los usuario de la aplicacion de Registro de una
	 * estructura de datos LDAP
	 *
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public UsuariosRegistradores obtenerUsuariosLdap(Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Metodo que obtiene los datos de un usuario
	 *
	 * @param id:
	 *            Identificador del usuario
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public UsuarioRegistradorBean obtenerUsuario(int id, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Metodo que obtiene los datos de un usuario de un sistema LDAP
	 *
	 * @param id:
	 *            Identificador del usuario
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public UsuarioRegistradorBean obtenerUsuarioLdap(int id, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método para obtener los usuario relacionados con una oficina
	 *
	 *
	 * @param idOficina:
	 *            Identificador de la oficina que buscamos
	 * @param usuarios:
	 *            Indica si en el resultado de la busqueda se incluyen los
	 *            usuario que pertenencen a la oficina o no
	 * @param agregados:
	 *            Indica si en el resultado de la busqueda se incluyen los
	 *            usuario agregados a la oficina o no
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public UsuariosRegistradores obtenerUsuariosOficina(int idOficina,
			boolean usuarios, boolean agregados, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método para obtener los usuario relacionados con una oficina
	 *
	 *
	 * @param idOficina:
	 *            Identificador de la oficina que buscamos
	 * @param usuarios:
	 *            Indica si en el resultado de la busqueda se incluyen los
	 *            usuario que pertenencen a la oficina o no
	 * @param agregados:
	 *            Indica si en el resultado de la busqueda se incluyen los
	 *            usuario agregados a la oficina o no
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public UsuariosRegistradores obtenerUsuariosOficinaLdap(int deptId,
			boolean usuarios, boolean agregados, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene los usuario que están disponibles para ser asociados a
	 * una oficina
	 *
	 *
	 * @param deptId:
	 *            Identificador del departamento al que pretendemos asociar los
	 *            usuarios
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public UsuariosRegistradores obtenerUsuariosAsociacion(int deptId,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que obtiene los usuario que están disponibles para ser asociados a
	 * una oficina de una estructura de datos LDAP
	 *
	 * @param deptId:
	 *            Identificador del departamento al que pretendemos asociar los
	 *            usuarios
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public UsuariosRegistradores obtenerUsuariosLdapAsociacion(int deptId,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que añade la información de un usuario a la base de datos
	 *
	 *
	 * @param usuario:
	 *            Información del usuario
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public int crearUsuario(UsuarioRegistradorBean usuario, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que añade la información de un usuario de un sistema LDAP a la
	 * base de datos
	 *
	 * @param usuario:
	 *            Información del usuario
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public int crearUsuarioLdap(UsuarioRegistradorBean usuario, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que actualiza los datos de un usuario.
	 *
	 * @param usuario:
	 *            Información del usuario
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void editarUsuario(UsuarioRegistradorBean usuario, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que actualiza los datos de un usuario.
	 *
	 * @param usuario:
	 *            Información del usuario
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void editarUsuarioLdap(UsuarioRegistradorBean usuario,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que elimina de la base de datos las entradas relacionadas con el
	 * usuario
	 *
	 * @param id:
	 *            Itentificador del usuario
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void eliminarUsuario(int id, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que elimina de la base de datos las entradas relacionadas con el
	 * usuario
	 *
	 * @param id:
	 *            Itentificador del usuario
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void eliminarUsuarioLdap(int id, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Obtenemos la lista de perfiles que puede tener un usuario
	 *
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public OptionsBean obtenerPerfilesCombo(Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene la lista de oficinas
	 *
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public OptionsBean obtenerOficinasCombo(Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método para obtener todas las oficinas.
	 *
	 *
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public Oficinas obtenerOficinas(Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que obtiene la lista de oficinas
	 *
	 *
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public Oficinas obtenerOficinasParaLista(Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Metodo que obtiene una oficina por su identificador
	 *
	 *
	 * @param id:
	 *            Identificador de la oficina
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public OficinaBean obtenerOficina(int id, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que inserta una oficina en la base de datos.
	 *
	 *
	 * @param oficina:
	 *            Datos de la oficina
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public int crearOficina(OficinaBean oficina, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que actualiza los datos de una oficina
	 *
	 *
	 * @param oficina:
	 *            Datos de la oficina
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void editarOficina(OficinaBean oficina, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que elimina una oficina y la información relacionada con esta
	 *
	 * @param id:
	 *            Identificador de la oficina
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void eliminarOficina(int id, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene los tipos de oficinas.
	 *
	 *
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public OptionsBean obtenerTipoOficinasCombo(Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene todas las entidades registrales
	 *
	 *
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public OptionsBean obtenerEntidadesRegistralesCombo(Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene la lista de departamentos.
	 *
	 * @param oficinas:
	 *            Con este parametros indicamos si en la lista se mostrarán o no
	 *            los depertamentos que ya tienen oficina
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public OptionsBean obtenerDepartamentosCombo(boolean oficinas,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método para asociar una lista de usuario a una oficina
	 *
	 *
	 * @param idsUser:
	 *            Lista de identificadores de los usuarios a asociar
	 * @param idOfic:
	 *            Identificador de la oficina a la que se van a asociar los
	 *            usuarios
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void asociarUsuarioAOficinas(String[] idsUser, int idOfic,
			String entidad) throws ISicresAdminBeansException;

	/**
	 * Método que obtiene todos los libro de un tipo especificado
	 *
	 *
	 * @param tipoLibro:
	 *            Tipo de los libros que buscamos (1.- Libros de Entrada; 2.-
	 *            Libros de Salida)
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public Libros obtenerLibros(int tipoLibro, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene los permisos de las oficinas que tiene asociadas el
	 * libro
	 *
	 *
	 * @param bookId:
	 *            Identificador del libro con el que estamos trabajando
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public PermisosSicres obtenerPermisosOficinasLibro(int bookId,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que devuelve una lista de oficinas que no están relacionadas con
	 * un libro
	 *
	 *
	 * @param bookId:
	 *            Identificador del libro
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public Oficinas obtenerOficinasDesasociadasALibro(int bookId,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que relaciona un libro con una lista de oficinas
	 *
	 *
	 * @param bookId:
	 *            Identificador del libro
	 * @param destId:
	 *            Identificadores de las oficinas
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void asociarOficinaALibro(int bookId, int destId[], Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que elimina la relación entre una oficina y un libro
	 *
	 *
	 * @param bookId:
	 *            Identificador del libro
	 * @param destId:
	 *            Identificador de la oficina
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void desasociarOficinaALibro(int bookId, int destId, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que modifica los permisos de un usuario, oficina o grupo sobre un
	 * libro
	 *
	 *
	 * @param permisos:
	 *            Permisos a modicar
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void modificarPermisos(PermisosSicres permisos, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene los permmisos de los usuarios que están asociados al
	 * libro
	 *
	 * @param bookId
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public PermisosSicres obtenerPermisosUsuarios(int bookId, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene los permmisos de los usuarios que están asociados al
	 * libro
	 *
	 * @param bookId
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public PermisosSicres obtenerPermisosUsuariosLdap(int bookId,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que obtiene los permisos de los usuario cuando una de las oficinas
	 * con las que esta relacionada esta asociada a un libro
	 *
	 * @param bookId:
	 *            Identificador del libro
	 * @param idsOfic:
	 *            Lista de oficinas relacionadas con el usuario
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public PermisosSicres obtenerPermisosUsuariosOficinas(int bookId,
			int idsOfic[], Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que obtiene los permisos de los usuario cuando una de las oficinas
	 * con las que esta relacionada esta asociada a un libro
	 *
	 * @param bookId:
	 *            Identificador del libro
	 * @param idsOfic:
	 *            Lista de oficinas relacionadas con el usuario
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public PermisosSicres obtenerPermisosUsuariosLdapOficinas(int bookId,
			int idsOfic[], Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que crea un libro en la base de datos.
	 *
	 * @param libro
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public int crearLibro(LibroBean libro, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Metodo que obtiene los datos de un libro
	 *
	 *
	 * @param idBook:
	 *            Identificador del libro que buscamos
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public LibroBean obtenerLibroBean(int idBook, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que actualiza los datos de un libro
	 *
	 *
	 * @param libroBean:
	 *            Información del libro
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void editarLibro(LibroBean libroBean, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que modifica el estado de un libro
	 *
	 *
	 * @param idBook:
	 *            Identificador del libro
	 * @param usuario:
	 *            Usuario que realiza la modificacion del libro
	 * @param idEstado:
	 *            Estado al que pasa el libro
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void modificarEstadoLibro(int idBook, String usuario, int idEstado,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que elimina un libro y su archivador
	 *
	 *
	 *
	 * @param idBook:
	 *            Identificador del libro que se va a eliminar
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void eliminarLibro(int idBook, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método para obtener el contador de identificadores
	 *
	 * @param anyo:
	 *            Año de que queremos obtener el contador
	 * @param tipo:
	 *            Tipo de libro (1.- Libro de Entrada; 2.- Libro de Salida)
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public int obtenerContadorCentral(int anyo, int tipo, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que actualiza los valores del contador central.
	 *
	 *
	 * @param anyo:
	 *            Año de que queremos obtener el contador
	 * @param tipo:
	 *            Tipo de libro (1.- Libro de Entrada; 2.- Libro de Salida)
	 * @param contador:
	 *            Identificador del contador a modificar
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void editarContadorCentral(int anyo, int tipo, int contador,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método para obtener los contadores de las oficinas
	 *
	 * @param anyo:
	 *            Año de que queremos obtener el contador
	 * @param tipo:
	 *            Tipo de libro (1.- Libro de Entrada; 2.- Libro de Salida)
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public Contadores obtenerContadoresOficinas(int anyo, int tipo,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método para actualizar los contadores de las oficinas
	 *
	 * @param tipo:
	 *            Tipo de libro (1.- Libro de Entrada; 2.- Libro de Salida)
	 * @param contadores:
	 *            Lista de contadores a actualizar
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void editarContadoresOficinas(int tipo, Contadores contadores,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que actualiza el tipo de numeración de una oficina para un libro
	 *
	 * @param idBook:
	 *            Identificador del libro
	 * @param idOfic:
	 *            Identificador de la oficina
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void actualizarNumeracionOficinaAsociadaALibro(int idBook,
			int idOfic, Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que te devuelte los tipos de Organizaciones existentes a partir de
	 * las cuales "cuelgan" el resto
	 *
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public Organizaciones obtenerOrganizacionesPadre(Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método para obtener los organimos a partir de un nodo indicado
	 *
	 * @param orgId:
	 *            Identificador del organismo a partir del cual buscaremos
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public Organizaciones obtenerHijosOrganizacion(int orgId, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que crea una organización.
	 *
	 * @param esPrimerNivel:
	 *            En el caso de que sea <code>true</code> se creara un tipo de
	 *            organización. En caso contrario una organización normar y
	 *            corriente
	 * @param organizacion:
	 *            Información de la organización
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public int crearOrganizacion(boolean esPrimerNivel,
			OrganizacionBean organizacion, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene los datos de una organización o tipo de organismo
	 *
	 *
	 * @param orgId:
	 *            Identificador del organismo
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public OrganizacionBean obtenerOrganizacion(int orgId, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene los datos de una organización con un determinado
	 * codigo
	 *
	 *
	 * @param code:
	 *            Código del organismo
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public OrganizacionBean obtenerOrganizacionByCode(String code,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que actualiza los datos de una organización o tipo de organismo
	 *
	 *
	 * @param organizacion:
	 *            Información de la organización
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void editarOrganizacion(OrganizacionBean organizacion,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que elimina una organización
	 *
	 * @param orgId:
	 *            Identificador de la organización a eliminar
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void eliminarOrganizacion(int orgId, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene las listas de distribucion asociadas a un organimo
	 *
	 *
	 * @param orgId:
	 *            Identificador del organismo
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public Distribuciones obtenerDistribuciones(int orgId, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que crea una nueva lista de distribución.
	 *
	 *
	 * @param idOrg:
	 *            Identificador del organismo al que pertenece la lista de
	 *            distribución
	 * @param idTipo:
	 *            Tipo de destino de la distribución
	 *            (1.-Usuario;2.-Departamento;3.-Grupo;4.-UnidadAdministrativa)
	 * @param ids:
	 *            Identificador de los destinatarios de la distribución
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void crearDistribuciones(int idOrg, int idTipo, int ids[],
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que elimina una lista de distribución
	 *
	 *
	 * @param id:
	 *            Identificador de la lista de distribución que deseamos
	 *            eliminar
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void eliminarDistribucion(int id, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que busca todos los departamentos que son raiz
	 *
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public OptionsBean obtenerDepartamentosRaiz(Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que busca todos los departamentos que son descendientes de un
	 * "padre"
	 *
	 *
	 * @param i:
	 *            Nodo padre
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public OptionsBean obtenerDepartamentosHijos(int i, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene todos los grupos que son "padre"
	 *
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public OptionsBean obtenerGruposRaiz(Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene los grupos a partir de un grupo determinado
	 *
	 * @param i:
	 *            Identificador del grupo
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public OptionsBean obtenerGruposHijos(int i, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene los usuario de un grupo o departamento.
	 *
	 *
	 * @param i:
	 *            Identificador del grupo o departamento del que queremos
	 *            obtener los usuarios
	 * @param tipo:
	 *            Tipo de busqueda (2.- Departamento, 3.- Grupo)
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public OptionsBean obtenerUsuarios(int i, int tipo, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método para obtener las listas de volumenes
	 *
	 *
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public OptionsBean obtenerListas(Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que asocia una lista de volumen a un libro
	 *
	 *
	 * @param idBook:
	 *            Identificador del libro con el que estamos trabajando
	 * @param idLista:
	 *            Identificador de la lista de volumen que queremos asociar
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void asociarListaALibro(int idBook, int idLista, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene los campos que se pueden aplicar a un filtro
	 *
	 * @param tipoFiltro:
	 *            1.- Usuario; 2.- Oficina de Registro
	 * @param tipoLibro:
	 *            1,. Libro de entrada; 2.- Libro de salida
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public Campos obtenerCampos(int tipoFiltro, int tipoLibro, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Obtenemos los operadores que se pueden aplicar a un campo.
	 *
	 * @param tipoCampo:
	 *            Tipo del campo para el se obtienen los posibles operadores
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public OptionsBean obtenerOperadores(String tipoCampo, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que guarda (crea o actualiza) los datos de los filtros a aplicar
	 * sobre un libro
	 *
	 * @param tipoFiltro:
	 *            1.- Usuario; 2.- Oficina de Registro
	 * @param tipoLibro:
	 *            1,. Libro de entrada; 2.- Libro de salida
	 * @param idLibro:
	 *            Identificador del libro al que se aplicarán los filtros
	 * @param idUserOfic:
	 *            Identificador del usuario u oficina
	 * @param filtros:
	 *            Valor de los filtros a guardar
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void configurarFiltro(int tipoFiltro, int tipoLibro, int idLibro,
			int idUserOfic, Filtros filtros, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene los filtros de busqueda de un usuario u oficna de
	 * registro sobre un libro.
	 *
	 *
	 * @param tipoFiltro:
	 *            1.- Usuario; 2.- Oficina de Registro
	 * @param tipoLibro:
	 *            1,. Libro de entrada; 2.- Libro de salida
	 * @param idLibro:
	 *            Identificador del libro al que se aplicarán los filtros
	 * @param idUserOfic:
	 *            Identificador del usuario u oficina
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public Filtros obtenerFiltros(int tipoFiltro, int tipoLibro, int idLibro,
			int idUserOfic, Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Obtenemos las oficinas a las que está asociado un usuario.
	 *
	 *
	 * @param idUser:
	 *            Identificador del usuario
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public Oficinas obtenerOficinasAsociadasAUsuario(int idUser, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Obtenemos las oficinas a las que está asociado un usuario.
	 *
	 * @param ldapguid:
	 *            Identificador del usuario
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public Oficinas obtenerOficinasUsuarioLdap(String ldapguid, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene las oficinas que no estan relacionadas con el usuario.
	 *
	 *
	 * @param idUser:
	 *            Identificador del usuario
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public Oficinas obtenerOficinasDesasociadasAUsuario(int idUser,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que obtiene las oficinas que no estan relacionadas con el usuario.
	 *
	 * @param ldapguid:
	 *            Identificador del usuario
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public Oficinas obtenerOficinasDesasociadasAUsuarioLdap(String ldapguid,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método con el que asociamos una oficina a un usuario.
	 *
	 * @param idUser:
	 *            Identificador del usuario
	 * @param idOfic:
	 *            Identificador de la oficina que asociaremos
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void asociarOficinasAUsuario(int idUser, int idOfic, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que desasocia un usuario de una oficina.
	 *
	 *
	 * @param idUser:
	 *            Identificador del usuario
	 * @param idOfic:
	 *            Identiticador de la oficina
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void desasociarOficinaAUsuario(int idUser, int idOfic,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que obtiene la Oficina que está asociada al Departamento del
	 * Usuario.
	 *
	 * @param idUser
	 *            Identificador del Usuario
	 * @param entidad
	 *            Entidad
	 * @return {@link Oficina} Oficina del Usuario.
	 * @throws ISicresAdminBeansException
	 */
	public Oficina obtenerOficinaAsociadaADeptoUsuario(int idUser,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Asocia al Usuario la Oficina Preferente
	 *
	 * @param userId
	 *            Identificador del Usuario
	 * @param idOficPref
	 *            Identificador de la Oficina Preferente
	 * @param entidad
	 *            Entidad
	 * @throws ISicresAdminBeansException
	 */
	public void asociarOficinaPreferenteAUsuario(int userId, int idOficPref,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Obtiene el identificador de la Oficina Preferente del Usuario
	 *
	 * @param userId
	 *            Identificador del Usuario
	 * @param entidad
	 *            Entidad
	 * @return Identificador de la Oficina Preferente del Usuario. Null si no no
	 *         tiene ninguna asociada.
	 * @throws ISicresAdminBeansException
	 */
	public Integer obtenerIdOficinaPreferenteUsuario(int userId, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene los tipos de transporte
	 *
	 *
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public Transportes obtenerTransportes(Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que devuelve la información de un tipo de transporte en concreto
	 *
	 * @param id:
	 *            Indentificador del tipo de transporte
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public Transporte obtenerTransporte(int id, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que almacena un tipo de transporte
	 *
	 * @param transporte:
	 *            Información del tipo de transporte
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public int crearTransporte(Transporte transporte, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Metodo que actualiza la información de un tipo de transporte
	 *
	 * @param transporte:
	 *            Información del tipo de transporte a actualizar
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void editarTransporte(Transporte transporte, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que elimina un tipo de transporte
	 *
	 * @param id:
	 *            Identificador del tipo de transporte a eliminar
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void eliminarTransporte(int id, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene la lista de los tipos de asunto
	 *
	 *
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public TiposAsuntoBean obtenerTiposAsunto(Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene los datos de un tipo de asunto determinado
	 *
	 * @param id:
	 *            Identificador del tipo de asunto
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public TipoAsuntoBean obtenerTipoAsunto(int id, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que crea un tipo de asunto y toda la información relacionada
	 *
	 *
	 * @param tipoAsunto:
	 *            Información del Tipo de Asunto
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public int crearTipoAsunto(TipoAsuntoBean tipoAsunto, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que actualiza los datos del tipo de asunto
	 *
	 *
	 * @param tipoAsunto:
	 *            Información del tipo de asunto
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void editarTipoAsunto(TipoAsuntoBean tipoAsunto, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que elimina un tipo de asunto y toda la información relacionada
	 *
	 *
	 * @param id:
	 *            Identificador del tipo de asunto
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void eliminarTipoAsunto(int id, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que asocia un tipo de asunto a una oficina
	 *
	 * @param oficina:
	 *            Informacion del tipo de asunto y oficina que se van a
	 *            relacionar
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void asociarOficinaTipoAsunto(OficinaTipoAsuntoBean oficina,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que elimina la relación entre un tipo de asunto y una oficina
	 *
	 * @param oficina:
	 *            Informacion del tipo de asunto y oficina que se van a tratar
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void desasociarOficinaTipoAsunto(OficinaTipoAsuntoBean oficina,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que asocia un documento a un tipo de asunto
	 *
	 * @param documento:
	 *            Información del documento
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void asociarDocumentoTipoAsunto(DocumentoTipoAsuntoBean documento,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que elimina la relacion entre un documento y un tipo de asunto
	 *
	 * @param documento:
	 *            Información del documento y tipo de asunto que se van a tratar
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void desasociarDocumentoTipoAsunto(
			DocumentoTipoAsuntoBean documento, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que modifica la relación entre un documento y un tipo de asunto
	 *
	 * @param documento:
	 *            Información del documento y tipo de asunto que se van a tratar
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void editarDocumentoTipoAsunto(DocumentoTipoAsuntoBean documento,
			Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método para importar departamentos de la estructura organizativa a las
	 * unidades administrativas
	 *
	 * @param deptId:
	 *            Identificador del departamento que deseamos importar
	 * @param isSelected:
	 *            Este parametro indica si el departamento seleccionado no se
	 *            importa (false). Si vale true, se importan no solo sus hijos
	 *            sino él también
	 * @param idUnidad:
	 *            Identificador de la unidad administrativa bajo la que se va a
	 *            importar
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void importarDepartamentos(int deptId, boolean isSelected,
			String idUnidad, Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método para importar grupos de un sistema ldap a las unidades
	 * administrativas
	 *
	 * @param nodeDn:
	 *            Nodo de inicio
	 * @param maxChildrenLdap:
	 *            Numero máximo de hijos a importar
	 * @param treeType:
	 *            Tipo de Arbol
	 * @param isSelected:
	 *            Este parametro indica si el grupo seleccionado no se importa
	 *            (false). Si vale true, se importan no solo sus hijos sino él
	 *            también
	 * @param idUnidad:
	 *            Identificador de la unidad administrativa bajo la que se va a
	 *            importar
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void importarGruposLdap(String nodeDn, int maxChildrenLdap,
			int treeType, boolean isSelected, String idUnidad, Entidad entidad)
			throws ISicresAdminBeansException;



	/**
	 * Obtenemos la lista de tipos de informes
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public OptionsBean obtenerTiposInformesCombo(Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que actualiza los datos del Informe
	 *
	 *
	 * @param Informe:
	 *            Información del Informe
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void editarInforme(InformeBean informe, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene la lista de los informes
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public InformesBean obtenerInformes(Entidad entidad) throws ISicresAdminBeansException;

	/**
	 * Método que obtiene los datos de un informe determinado
	 *
	 * @param id:
	 *            Identificador del informe
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public InformeBean obtenerInforme(int id, Entidad entidad, OptionsBean perfiles)
			throws ISicresAdminBeansException;

	/**
	 * Método que obtiene un informe determinado
	 *
	 * @param id:
	 *            Identificador del informe
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public InformeBean descargarInforme(int id, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que crea un informe y toda la información relacionada
	 *
	 *
	 * @param informeBean:
	 *            Información del Informe
	 * @param entidad
	 * @return
	 * @throws ISicresAdminBeansException
	 */
	public int crearInforme(InformeBean informeBean, Entidad entidad)
			throws ISicresAdminBeansException;

	/**
	 * Método que elimina un informe y toda la información relacionada
	 *
	 *
	 * @param id:
	 *            Identificador informe
	 * @param entidad
	 * @throws ISicresAdminBeansException
	 */
	public void eliminarInforme(int id, Entidad entidad)
			throws ISicresAdminBeansException;

}