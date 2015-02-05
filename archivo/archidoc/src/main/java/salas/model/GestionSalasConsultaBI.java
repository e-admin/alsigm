package salas.model;

import gcontrol.exceptions.UsuariosNotAllowedException;
import gcontrol.vos.UsuarioVO;

import java.util.List;

import salas.exceptions.SalasConsultaException;
import salas.vos.BusquedaRegistroConsultaSalaVO;
import salas.vos.BusquedaUsuarioSalaConsultaVO;
import salas.vos.EdificioVO;
import salas.vos.ElementoNavegacionVO;
import salas.vos.MesaVO;
import salas.vos.RegistroConsultaSalaVO;
import salas.vos.SalaVO;
import salas.vos.UsuarioArchivoSalasConsultaVO;
import salas.vos.UsuarioSalasConsultaVO;
import se.usuarios.exceptions.AppUserException;
import solicitudes.consultas.vos.ConsultaVO;

import common.exceptions.ActionNotAllowedException;
import common.exceptions.SecurityException;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public interface GestionSalasConsultaBI {

	/**
	 * Obtiene el árbol de estructura de salas de consulta
	 * 
	 * @return
	 */
	public EstructuraSalasConsulta getEstructuraSalasConsulta();

	/**
	 * Obtiene el árbol de estructura de salas de consulta a partir de un nodo.
	 * 
	 * @param subtreeNode
	 * @return
	 */
	public EstructuraSalasConsulta getEstructuraSalasConsulta(String subtreeNode);

	/**
	 * Obtiene la lista de Edificios por Ids de Archivo
	 * 
	 * @param idsArchivo
	 *            Array de cadenas que definen los identificadores de los
	 *            archivos
	 * @return Lista de {@link EdificioVO} que pertenecen a los archivos
	 *         especificados
	 * @throws SecurityException
	 */
	public List getEdificiosByIdsArchivo(final String[] idsArchivo)
			throws SecurityException;

	/**
	 * Obtiene el Edificio por su Identificador
	 * 
	 * @param idEdificio
	 *            Identificador del Edificio
	 * @return Objeto {@link EdificioVO}
	 * @throws SecurityException
	 */
	public EdificioVO getEdificioById(final String idEdificio)
			throws SecurityException;

	/**
	 * Inserta un nuevo edificio
	 * 
	 * @param edificioVO
	 *            Datos del edificio a insertar
	 * @throws SalasConsultaException
	 * @throws SecurityException
	 */
	public void insertarEdificio(final EdificioVO edificioVO)
			throws SalasConsultaException, SecurityException;

	/**
	 * Actualiza los valores del edificio
	 * 
	 * @param edificioVO
	 *            Objeto que contiene los datos del edificio a actualizar
	 * @throws SalasConsultaException
	 * @throws SecurityException
	 */
	public void actualizarEdificio(final EdificioVO edificioVO)
			throws SalasConsultaException, SecurityException;

	/**
	 * Elimina el edificio especificado por su id
	 * 
	 * @param id
	 *            Cadena que define el Identificador del edificio
	 * @throws EdificioConSalasException
	 * @throws SecurityException
	 */
	public void eliminarEdificio(final EdificioVO edificioVO)
			throws SalasConsultaException, SecurityException;

	/**
	 * Comprueba si un archivo tiene edificios
	 * 
	 * @param idArchivo
	 *            Cadena que define el Identificador del archivo
	 */
	public boolean isArchivoConEdificios(final String idArchivo);

	/**
	 * Obtiene la lista de edificios del archivo especificado y las mesas libres
	 * que contiene
	 * 
	 * @param idArchivo
	 *            Identificador de archivo
	 * @param salasConEquipoInformatico
	 *            Indica si queremos buscar en salas con equipo informático o
	 *            no.
	 * @return Lista de objetos {@link ElementoNavegacionVO}
	 */
	public List getEdificiosNumHijosByArchivo(String idArchivo,
			String salasConEquipoInformatico);

	/**
	 * Lista de mesas libres del edificio que pertenece a un determinado
	 * archivo.
	 * 
	 * @param idEdificio
	 *            Identificador del edificio
	 * @param idArchivo
	 *            Identificador del archivo
	 * @param salasConEquipoInformatico
	 *            Busqueda en salas con equipo informatico o no
	 * @return Lista de Objetos {@link MesaVO}
	 */
	public List getMesasLibresByEdificio(String idEdificio, String idArchivo,
			String salasConEquipoInformatico);

	/**
	 * Inserta una nueva sala y sus mesas
	 * 
	 * @param salaVO
	 *            Datos de la sala a insertar
	 * @param codigoMesa
	 *            Codigos de las mesas
	 * @throws SalasConsultaException
	 * @throws SecurityException
	 */
	public void insertarSala(final SalaVO salaVO, final String[] codigosMesa)
			throws SalasConsultaException, SecurityException;

	/**
	 * Elimina la sala especificada por su id
	 * 
	 * @param salaVO
	 *            Objeto que contiene los datos de la sala a eliminar
	 * @throws SalasConsultaException
	 * @throws SecurityException
	 */
	public void eliminarSala(final SalaVO salaVO)
			throws SalasConsultaException, SecurityException;

	/**
	 * Actualiza los valores de la sala
	 * 
	 * @param salaVO
	 *            Objeto que contiene los datos de la sala a actualizar
	 * @throws SalasConsultaException
	 * @throws SecurityException
	 */
	public void actualizarSala(final SalaVO salaVO)
			throws SalasConsultaException, SecurityException;

	/**
	 * Obtiene la sala por su identificador
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 * @return Objeto {@link SalaVO}
	 * @throws SecurityException
	 */
	public SalaVO getSalaById(final String idSala) throws SecurityException;

	/**
	 * Obtiene la lista de Salas por Ids de Edificio
	 * 
	 * @param idEdificio
	 *            Identificador de Edificio
	 * @return Lista de {@link SalaVO} que pertenecen a los edificios
	 *         especificados
	 * @throws SecurityException
	 */
	public List getSalas(final String idEdificio) throws SecurityException;

	/**
	 * Comprueba si el edificio tiene salas
	 * 
	 * @param idEdificio
	 *            Identificador del edificio
	 * @return boolean
	 * @throws SecurityException
	 */
	public boolean isEdificioConSalas(final String idEdificio);

	/**
	 * Comprueba si existe ya una sala con el mismo nombre para un determinado
	 * edificio.
	 * 
	 * @param nombre
	 *            de la sala
	 * @param idEdificio
	 *            Identificador del Edificio
	 * @return boolean
	 * @throws SecurityException
	 */
	public boolean existeSala(final String nombre, final String idEdificio);

	/**
	 * Obtiene la sala por el nombre y el identificador del edificio
	 * 
	 * @param nombre
	 *            de la sala
	 * @param idEdificio
	 *            Identificador del edificio
	 * @return Objeto {@link SalaVO}
	 * @throws SecurityException
	 */
	public SalaVO getSalaByNombreAndEdificio(final String nombre,
			final String idEdificio) throws SecurityException;

	/**
	 * Lista de salas con sus mesas libres de un determinado edificio
	 * 
	 * @param idEdificio
	 *            Identificador de edificio
	 * @param salasConEquipoInformatico
	 *            Indica si queremos buscar salas con equipo informático o no.
	 * @return Lista de objetos {@link ElementoNavegacionVO}
	 */
	public List getSalasNumHijos(final String idEdificio,
			final String salasConEquipoInformatico);

	/**
	 * Lista de mesas libres de la sala que pertenece a un determinado archivo
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 * @param idArchivo
	 *            Identificador de archivo
	 * @param salasConEquipoInformatico
	 *            busqueda de salas con equipo informatico o no
	 * @return Lista de Objetos {@link MesaVO}
	 */
	public List getMesasLibresBySala(final String idSala,
			final String idArchivo, final String salasConEquipoInformatico);

	/**
	 * Inserta una nueva mesa
	 * 
	 * @param mesaVO
	 *            Datos de la mesa a insertar
	 * @throws Exception
	 */
	public void insertarMesa(MesaVO mesaVO) throws Exception;

	/**
	 * Elimina la mesa especificada
	 * 
	 * @param mesaVO
	 *            Datos de la mesa a eliminar
	 */
	public void eliminarMesa(final MesaVO mesaVO);

	/**
	 * Actualiza los valores de la mesa
	 * 
	 * @param mesaVO
	 *            Objeto que contiene los datos de la mesa a actualizar
	 */
	public void actualizarMesa(MesaVO mesaVO);

	/**
	 * Obtiene la mesa por su identificador
	 * 
	 * @param idMesa
	 *            Identificador de la sala
	 * @return Objeto {@link MesaVO}
	 */
	public MesaVO getMesaById(final String idMesa);

	/**
	 * Obtiene la lista de Mesas por Sala
	 * 
	 * @param idSala
	 *            Identificador de Sala
	 * @return Lista de {@link MesaVO} que pertenecen a las salas especificadas
	 */
	public List getMesas(final String idSala);

	/**
	 * Comprueba si la sala tiene mesas
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 * @return boolean
	 */
	public boolean isSalaConMesas(final String idSala);

	/**
	 * Obtiene el número de mesas por sala
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 * @return int Número de Mesas
	 */
	public int getNumeroMesas(final String idSala);

	/**
	 * Obtiene el número de mesas ocupadas por sala
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 * @return boolean
	 */
	public boolean existenMesasOcupadas(final String idSala);

	/**
	 * Obtiene el mayor número de orden de las mesas en la sala
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 * @return Número de Orden Máximo
	 */
	public int getMaxNumOrden(final String idSala);

	/**
	 * Obtiene la Mesa por sala y codigo
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 * @param codigo
	 *            de la mesa
	 * @return {@link MesaVO}
	 */
	public MesaVO getMesaBySalaAndCodigo(final String idSala,
			final String codigo);

	/**
	 * Se encarga de modificar el estado de una mesa
	 * 
	 * @param idsMesa
	 *            Identificadores de las Mesas a modificar
	 * @param estadoAEstablecer
	 *            Estado a establecer a las mesas
	 */
	public void cambiarEstado(final String[] idsMesa,
			final String estadoAEstablecer);

	/**
	 * Obtiene el número de mesas ocupadas de un edificio
	 * 
	 * @param idEdificio
	 *            Identificador del Edificio
	 * @return Entero que define el número de mesas ocupadas del edificio
	 */
	public int getCountMesasOcupadasByIdEdificio(final String idEdificio);

	/**
	 * Obtiene el número de mesas libres de un edificio
	 * 
	 * @param idEdificio
	 *            Identificador del Edificio
	 * @return Entero que define el número de mesas libres del edificio
	 */
	public int getCountMesasLibresByIdEdificio(final String idEdificio);

	/**
	 * Obtiene una lista de mesas
	 * 
	 * @param idsMesa
	 *            Identificadores de las mesas
	 * @return Lista de {@link MesaVO}
	 */
	public List getMesasById(final String[] idsMesa);

	/**
	 * Obtiene el número de mesas por edificio
	 * 
	 * @param idEdificio
	 *            Identificador del edificio
	 * @return número total de mesas
	 */
	public int getCountMesasByIdEdificio(final String idEdificio);

	/**
	 * Elimina las mesas de la sala
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 * @param idsMesa
	 *            Identificadores de las mesas a eliminar
	 */
	public void eliminarMesas(final String idSala, final String[] idsMesa);

	/**
	 * Obtiene el número de mesas libres por sala
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 * @return Número de mesas libres
	 */
	public int getCountMesasLibresByIdSala(final String idSala);

	/**
	 * Obtiene el número de mesas ocupadas por sala
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 * @return Número de mesas ocupadas
	 */
	public int getCountMesasOcupadasByIdSala(final String idSala);

	/**
	 * Actualiza los codigos de la mesas
	 * 
	 * @param idsMesa
	 *            Identificadores de las mesas
	 * @param codigosMesa
	 *            nuevos codigos de las mesas
	 */
	public void actualizarCodigosMesa(String[] idsMesa, String[] codigosMesa);

	/**
	 * Obtiene la lista de mesas de la sala para la navegación en registro
	 * 
	 * @param idSala
	 *            Identificador de la sala
	 * @return Lista de Objetos {@link ElementoNavegacionVO}
	 */
	public List getMesasNavegacionPorSala(final String idSala);

	/**
	 * Obtiene la lista de archivos del usuario de consulta
	 * 
	 * @param idUsuarioConsulta
	 *            Identificador de usuario de consulta de sala
	 * @param idsArchivo
	 *            Identificadores de archivo del usuario conectado
	 * @return Lista de objetos {@link ArchivoVO}
	 */
	// public List getArchivosUsuario(String idUsuarioConsulta, String[]
	// idsArchivo);

	/**
	 * Comprueba si el usuario ya esta registrado en la sala
	 * 
	 * @param idUsuarioConsulta
	 *            Identificador del usuario
	 * @return True si el usuario esta registrado en la sala y False en caso
	 *         contrario
	 */
	public boolean isUsuarioRegistrado(String idUsuarioConsulta);

	/**
	 * Inserta el registro de consulta en sala
	 * 
	 * @param registroConsultaSalaVO
	 *            Registro de consulta a insertar
	 * @param mesaVO
	 *            Mesa seleccionada para actualizar el estado
	 * @throws SalasConsultaException
	 * @throws SecurityException
	 */
	public void insertarRegistroConsultaSala(
			final RegistroConsultaSalaVO registroConsultaSalaVO,
			final MesaVO mesaVO) throws SalasConsultaException,
			SecurityException;

	/**
	 * Obtiene el registro de consulta en sala por el identificador del usuario
	 * 
	 * @param idUsuarioConsulta
	 *            Identificador del usuario
	 * @return Objeto {@link RegistroConsultaSalaVO}
	 */
	public RegistroConsultaSalaVO getRegistroConsultaSalaByUsuario(
			final String idUsuarioConsulta);

	/**
	 * Obtiene la lista de usuarios de consulta registrados en la sala
	 * 
	 * @return Lista de objetos {@link RegistroConsultaSalaVO}
	 */
	public List getUsuariosRegistrados();

	/**
	 * Obtiene el listado de consultas del usuario de consulta en sala
	 * 
	 * @param idUsrCSala
	 *            identificador del usuario de consulta en sala
	 * @return Lista de objetos {@link ConsultaVO}
	 */
	public List getConsultasUsuarioSala(String idUsrCSala);

	/**
	 * Indica si el usuario de consulta en sala tiene unidades documentales
	 * pendientes de devolver
	 * 
	 * @param idUsrCSala
	 *            Identificador del usuario en sala
	 * @return boolean
	 */
	public boolean isUsuarioConUnidadesPendientes(String idUsrCSala);

	/**
	 * Obtiene el registro de consulta en sala
	 * 
	 * @param idRegistro
	 *            Identificador de registro en sala
	 * @return registro de consulta en sala {@link RegistroConsultaSalaVO}
	 */
	public RegistroConsultaSalaVO getRegistroConsultaSala(String idRegistro);

	/**
	 * Obtiene los registros de consulta en sala
	 * 
	 * @param idsRegistro
	 *            Identificadores de registro en sala
	 * @return Lista de objetos {@link RegistroConsultaSalaVO}
	 */
	public List getRegistrosConsultaSala(String[] idsRegistro);

	/**
	 * Registra la salida de los usuarios de la sala
	 * 
	 * @param idRegistro
	 *            Identificador del registro de consulta
	 * @param idUsuario
	 *            Identificador del usuario de consulta en sala.
	 */
	public void registrarSalidaSala(String idRegistro, String idUsuario);

	/**
	 * Busca los registros de consulta en sala
	 * 
	 * @param busquedaRegistroConsultaSalaVO
	 *            Datos de busqueda del Registro
	 * @return Lista de Objetos {@link RegistroConsultaSalaVO}
	 * @throws SecurityException
	 */
	public List buscarRegistros(
			BusquedaRegistroConsultaSalaVO busquedaRegistroConsultaSalaVO)
			throws SecurityException;

	/**
	 * Obtiene una lista de registros abiertos
	 * 
	 * @return Lista de objetos {@link RegistroConsultaSalaVO}
	 */
	public List getRegistrosAbiertos();

	/**
	 * Obtiene el número de registros abiertos para el usuario conectado
	 * 
	 * @return Número de registros abiertos
	 */
	public int getCountRegistrosAbiertos();

	/**
	 * Obtiene un registro de consulta en sala por los datos pasados como
	 * parámetro
	 * 
	 * @param registroConsultaSala
	 * @return
	 */
	public RegistroConsultaSalaVO getRegistroConsultaSala(
			RegistroConsultaSalaVO registroConsultaSala);

	// GESTION DE USUARIOS
	/**
	 * Elimina el usuario especificado
	 * 
	 * @param idUsuario
	 *            Cadena que define el Identificador del Usuario
	 * @throws ActionNotAllowedException
	 */
	public void eliminarUsuario(String idUsuario) throws SecurityException,
			ActionNotAllowedException;

	/**
	 * Busca los usuario de salas de consutla
	 * 
	 * @param busquedaUsuarioSalaConsultaVO
	 *            Datos del filtro
	 * @return Lista de {@link UsuarioSalasConsultaVO}
	 */
	public List buscarUsuarios(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSalaConsultaVO)
			throws SecurityException;

	public List buscarUsuariosPorArchivo(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSalaConsultaVO)
			throws SecurityException;

	/**
	 * Obtiene el Usuario por su id
	 * 
	 * @param id
	 *            Cadena que define el Identificador del Usuario
	 * @return
	 */
	public UsuarioSalasConsultaVO getUsuarioById(String id,
			boolean cargarArchivos) throws SecurityException;

	/**
	 * Obtiene el Usuario por su id
	 * 
	 * @param id
	 * @return
	 */
	public UsuarioSalasConsultaVO getUsuarioSalaById(String id);

	/**
	 * Obtiene un usuario de consulta en sala por el identificador del usuario
	 * de aplicacion
	 * 
	 * @param idScaUsr
	 *            Identificador del usuario de aplicaicon
	 * @return Objeto de {@link UsuarioSalasConsultaVO}
	 */
	public UsuarioSalasConsultaVO getUsuarioExternoById(String idScaUsr,
			boolean cargarArchivos);

	/**
	 * Obtiene los Archivos a los que pertenece el Usuario y estan en en los ids
	 * especificados
	 * 
	 * @param idUsuario
	 *            Cadena que define el identificador del usuario
	 * @param idsArchivo
	 *            Array de Cadenas que define los identificador es de los
	 *            archivos.
	 * @return Lista de {@link UsuarioArchivoSalasConsultaVO}
	 */
	public List getArchivosByIdUsuarioSalaConsultaInArchivos(String idUsuario,
			String[] idsArchivo);

	/**
	 * Obtiene la lista de Usuarios Vigentes
	 * 
	 * @return Lista de {@link UsuarioSalasConsultaVO}
	 */
	public List getUsuariosVigentes();

	/**
	 * Obtiene la lista de Usuarios No Vigentes
	 * 
	 * @return Lista de {@link UsuarioSalasConsultaVO}
	 */
	public List getUsuariosNoVigentes();

	/**
	 * Obtiene la lista de Usuarios
	 * 
	 * @return Lista de {@link UsuarioSalasConsultaVO}
	 */
	public List getUsuarios();

	/**
	 * Inserta el Usuario de Consulta
	 * 
	 * @param usuarioSalaConsultaVO
	 *            Datos a insertar
	 */
	public void insertarUsuario(UsuarioSalasConsultaVO usuarioSalaConsultaVO)
			throws SecurityException, SalasConsultaException;

	/**
	 * Actualiza los datos del usuario
	 * 
	 * @param usuarioSalaConsultaVO
	 *            Datos a actualizazar
	 */
	public void actualizarUsuario(UsuarioSalasConsultaVO usuarioSalaConsultaVO)
			throws SecurityException, SalasConsultaException;

	/**
	 * Desasocia los Archivos de un usuario de salas de consulta
	 * 
	 * @param idUsuario
	 *            Cadena que define el identificador del usuario de sala de
	 *            consulta
	 * @param idsArchivo
	 *            Cadena que define los identificadores de los archivos a
	 *            desasociar
	 */
	public void desasociarArchivos(String idUsuario, String[] idsArchivo)
			throws SecurityException, SalasConsultaException;

	/**
	 * Asocia los Archivos a un usuario de salas de consulta
	 * 
	 * @param idUsuario
	 *            Cadena que define el identificador del usuario de sala de
	 *            consulta
	 * @param idsArchivo
	 *            Cadena que define los identificadores de los archivos a
	 *            asociar
	 */
	public void asociarArchivos(String idUsuario, String[] idsArchivo)
			throws SecurityException;

	/**
	 * Obtiene los usuarios externos de la aplicación
	 * 
	 * @return Lista de {@link UsuarioVO}
	 */
	public List getUsuariosExternos(String filtro);

	/**
	 * Obtiene los usuarios investigadores con permiso de consulta en sala que
	 * están vigentes y cuyo identificador de archivo coincide con el
	 * seleccionado
	 * 
	 * @param idArchivo
	 *            Identificador de archivo
	 * @return Lista de objetos {@link UsuarioSalasConsultaVO}
	 */
	public List getUsuariosConPermisoConsultaSala(String idArchivo);

	/**
	 * Obtiene los usuarios investigadores con permiso de consulta en sala que
	 * están vigentes, filtrando por nombre y apellidos y cuyo identificador de
	 * archivo coincide con el seleccionado
	 * 
	 * @param idArchivo
	 *            Identificador de archivo
	 * @return Lista de objetos {@link UsuarioSalasConsultaVO}
	 */
	public List getUsuariosConPermisoConsultaSala(String idArchivo,
			String filtro);

	/**
	 * Obtiene los datos del usuario externo
	 * 
	 * @param idUsuario
	 * @return
	 */
	public UsuarioVO getDatosUsuario(String idUsuario);

	public UsuarioVO crearUsuarioExterno(String idUsrSisExtGestor)
			throws AppUserException, UsuariosNotAllowedException;

	public boolean isUsuarioAsociadoAUsuarioSalasConsulta(
			String idUsuarioExterno);

	/**
	 * Obtiene el numero de usuarios de consulta en sala por los criterios de
	 * busqueda seleccionados
	 * 
	 * @param busquedaUsuarioSala
	 * @return
	 */
	public int getCountUsuariosConsulta(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSala);

	/**
	 * Obtiene el numero de unidades consultadas en sala
	 * 
	 * @param busquedaUsuarioSala
	 * @return
	 */
	public int getCountUnidadesConsultadas(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSala);

	/**
	 * Obtiene el numero de registros de consulta en sala
	 * 
	 * @param busquedaUsuarioSala
	 * @return
	 */
	public int getCountRegistros(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSala);

	/**
	 * Obtiene los expedientes devueltos o entregados de un usuario de consulta
	 * en sala
	 * 
	 * @param busquedaUsuarioConsulta
	 * @return
	 */
	public List getExpedientesUsuarioConsulta(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioConsulta);

	/**
	 * Obtiene las unidades consultadas más de N veces por usuarios de consulta
	 * en sala
	 * 
	 * @param busquedaUsuarioSala
	 * @return
	 */
	public List getUnidadesConsultadas(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSala);

	/**
	 * Obtiene el listado de temas por usuario de consulta en sala
	 * 
	 * @param busquedaUsuarioSala
	 * @return Lista de objetos {@link UsuarioSalasConsultaVO}
	 */
	public List getTemasUsuarioConsulta(
			BusquedaUsuarioSalaConsultaVO busquedaUsuarioSala);

	// public boolean isUsuarioAsociadoAUsuarioSalasConsultaVigente(String
	// idUsuarioExterno);
	/* FIN USUARIOS SALAS DE CONSULTA */

}